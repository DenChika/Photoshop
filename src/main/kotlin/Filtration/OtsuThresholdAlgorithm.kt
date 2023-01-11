package Filtration

import ColorSpaces.ColorSpaceInstance
import Configurations.AppConfiguration
import Interfaces.IFiltrationAlgorithm

class OtsuThresholdAlgorithm : IFiltrationAlgorithm {
    private fun calculateHist(pixels: Array<ColorSpaceInstance>): IntArray {
        val width = AppConfiguration.Image.width
        val height = AppConfiguration.Image.height
        val hist = IntArray(256) { 0 }
        for (y in 0 until height) {
            for (x in 0 until width) {
                val intensity = (pixels[y * width + x].GetFloatArrayOfValues()[0] * 255).toInt()
                hist[intensity]++
            }
        }
        return hist
    }
    private fun calculateIntensitySum(pixels: Array<ColorSpaceInstance>): Int {
        val width = AppConfiguration.Image.width
        val height = AppConfiguration.Image.height
        var sum = 0
        for (y in 0 until height) {
            for (x in 0 until width) {
                val intensity = (pixels[y * width + x].GetFloatArrayOfValues()[0] * 255).toInt()
                sum += intensity
            }
        }
        return sum
    }
    override fun Apply(pixels: Array<ColorSpaceInstance>) {
        val width = AppConfiguration.Image.width
        val height = AppConfiguration.Image.height
        val hist = calculateHist(pixels)
        val intensitySum = calculateIntensitySum(pixels)
        var bestThreshold = 0
        var bestSigma = 0.0f
        var firstClassPixelsCount = 0
        var firstClassIntensitySum = 0
        for (thresh in 0..254) {
            firstClassPixelsCount += hist[thresh]
            firstClassIntensitySum += thresh * hist[thresh]
            val firstClassProb = firstClassPixelsCount.toFloat() / (height * width)
            val secondClassProb = 1.0f - firstClassProb
            val firstClassMean = firstClassIntensitySum.toFloat() / firstClassPixelsCount
            val secondClassMean = (intensitySum - firstClassIntensitySum) /
                    (height * width - firstClassPixelsCount).toFloat()
            val meanDelta = firstClassMean - secondClassMean
            val sigma = firstClassProb * secondClassProb * meanDelta * meanDelta
            if (sigma > bestSigma) {
                bestSigma = sigma
                bestThreshold = thresh
            }
        }
        ThresholdAlgorithm().Use(pixels, bestThreshold.toFloat() / 255)
    }
}