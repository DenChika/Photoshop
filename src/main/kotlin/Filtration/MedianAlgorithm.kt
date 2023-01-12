package Filtration

import ColorSpaces.ColorSpaceInstance
import Configurations.AppConfiguration
import Dithering.DitheringSubsidiaryFunctions
import Interfaces.IFiltrationAlgorithm

class MedianAlgorithm : IFiltrationAlgorithm {
    override fun Apply(pixels: Array<ColorSpaceInstance>) {
        val width = AppConfiguration.Image.width
        val height = AppConfiguration.Image.height
        val extendedPixels = pixels.map { it.GetFloatArrayOfValues() }.toTypedArray()
        val radius = AppConfiguration.Filtration.radiusValue.value
        for (y in 0 until height) {
            for (x in 0 until width) {
                var subMatrixR = arrayOf<Float>()
                var subMatrixG = arrayOf<Float>()
                var subMatrixB = arrayOf<Float>()
                for (j in y - radius..y + radius) {
                    for (i in x - radius..x + radius) {
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
                        subMatrixR += extendedPixels[newJ * width + newI][0]
                        subMatrixG += extendedPixels[newJ * width + newI][1]
                        subMatrixB += extendedPixels[newJ * width + newI][2]
                    }
                }
                val newColor = floatArrayOf(
                    subMatrixR.sortedArray()[subMatrixR.size / 2],
                    subMatrixG.sortedArray()[subMatrixG.size / 2],
                    subMatrixB.sortedArray()[subMatrixB.size / 2]
                )
                pixels[y * width + x].UpdateValues(newColor)
            }
        }
    }
}