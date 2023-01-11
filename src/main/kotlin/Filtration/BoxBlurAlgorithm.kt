package Filtration

import ColorSpaces.ColorSpaceInstance
import Configurations.AppConfiguration
import Interfaces.IFiltrationAlgorithm

class BoxBlurAlgorithm : IFiltrationAlgorithm {
    override fun Apply(pixels: Array<ColorSpaceInstance>) {
        val width = AppConfiguration.Image.width
        val height = AppConfiguration.Image.height
        val extendedPixels = pixels.map{it.GetFloatArrayOfValues()}.toTypedArray()
        val radius = AppConfiguration.Filtration.radiusValue.value
        for (y in 0 until height) {
            for (x in 0 until width) {
                var sumR = 0.0f
                var sumG = 0.0f
                var sumB = 0.0f
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
                        sumR += extendedPixels[newJ * width + newI][0]
                        sumG += extendedPixels[newJ * width + newI][1]
                        sumB += extendedPixels[newJ * width + newI][2]
                    }
                }
                val newColor = floatArrayOf(
                    sumR / ((radius * 2 + 1) * (radius * 2 + 1)),
                    sumG / ((radius * 2 + 1) * (radius * 2 + 1)),
                    sumB / ((radius * 2 + 1) * (radius * 2 + 1))
                )
                pixels[y * width + x].UpdateValues(newColor)
            }
        }
    }
}