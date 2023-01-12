package Filtration

import ColorSpaces.ColorSpaceInstance
import Configurations.AppConfiguration
import Interfaces.IFiltrationAlgorithm
import kotlin.math.pow

class GaussianAlgorithm : IFiltrationAlgorithm {
    private fun gaussianMatrix(sigma: Float): FloatArray {
        val radius = (2 * sigma).toInt()
        var array = floatArrayOf()
        for (x in - radius..radius) {
            for (y in radius downTo - radius) {
                array += ((1 / (2 * Math.PI * sigma * sigma)) *
                        Math.E.pow((-(x * x + y * y) / (2 * sigma * sigma)).toDouble())).toFloat()
            }
        }
        return array
    }
    override fun Apply(pixels: Array<ColorSpaceInstance>) {
        val width = AppConfiguration.Image.width
        val height = AppConfiguration.Image.height
        val extendedPixels = pixels.map{it.GetFloatArrayOfValues()}.toTypedArray()
        val sigma = AppConfiguration.Filtration.sigmaValue.value
        val radius = (2 * sigma).toInt()
        val matrix = gaussianMatrix(sigma)
        for (y in 0 until height) {
            for (x in 0 until width) {
                var sumR = 0.0f
                var sumG = 0.0f
                var sumB = 0.0f
                var sum = 0.0f
                var index = 0
                for (j in y - radius .. y + radius) {
                    for (i in x - radius .. x + radius) {
                        var newI = i
                        var newJ = j
                        if (newI < 0) {
                            newI = 0
                        }
                        if (newI > width - 1) {
                            newI = width - 1
                        }
                        if (newJ < 0) {
                            newJ = 0
                        }
                        if (newJ > height - 1) {
                            newJ = height - 1
                        }
                        sumR += extendedPixels[newJ * width + newI][0] * matrix[index]
                        sumG += extendedPixels[newJ * width + newI][1] * matrix[index]
                        sumB += extendedPixels[newJ * width + newI][2] * matrix[index]
                        sum += matrix[index]
                        index++
                    }
                }
                val newColor = floatArrayOf(
                    sumR / sum,
                    sumG / sum,
                    sumB / sum,
                )
                pixels[y * width + x].UpdateValues(newColor)
            }
        }
    }
}