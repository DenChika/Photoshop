package Filtration

import ColorSpaces.ColorSpaceInstance
import Configurations.AppConfiguration
import Interfaces.IFiltrationAlgorithm

class ThresholdAlgorithm : IFiltrationAlgorithm {
    fun Use(pixels: Array<ColorSpaceInstance>, threshold: Float) {
        for (pixel in pixels) {
            val (oldR: Float, oldG: Float, oldB: Float) = pixel.GetFloatArrayOfValues()
            var (newR: Float, newG: Float, newB: Float) = floatArrayOf(0.0f, 0.0f, 0.0f)
            if (oldR >= threshold) {
                newR = 1.0f; newG = 1.0f; newB = 1.0f
            }
            pixel.UpdateValues(floatArrayOf(newR, newG, newB))
        }
    }
    override fun Apply(pixels: Array<ColorSpaceInstance>) {
        val threshold = AppConfiguration.Filtration.thresholdValue.value / 255f
        Use(pixels, threshold)
    }
}