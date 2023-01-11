package Filtration

import ColorSpaces.ColorSpaceInstance
import Configurations.AppConfiguration
import Interfaces.IFiltrationAlgorithm
import kotlin.math.sqrt

class SobelFilterAlgorithm : IFiltrationAlgorithm {
    override fun Apply(pixels: Array<ColorSpaceInstance>) {
        val width = AppConfiguration.Image.width
        val height = AppConfiguration.Image.height
        val extendedPixels = pixels.map{it.GetFloatArrayOfValues()}.toTypedArray()
        for (y in 0 until height) {
            for (x in 0 until width) {
                var maskR = floatArrayOf()
                for (j in y - 1..y + 1) {
                    for (i in x - 1..x + 1) {
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
                        maskR += extendedPixels[newJ * width + newI][0]
                    }
                }
                val gx = maskR[0] * -1 + maskR[3] * -2 + maskR[6] * -1 + maskR[2] + maskR[5] * 2 + maskR[8]
                val gy = maskR[0] * -1 + maskR[1] * -2 + maskR[2] * -1 + maskR[6] + maskR[7] * 2 + maskR[8]
                val g = sqrt(gx * gx + gy * gy).coerceIn(0.0f, 1.0f)
                val newColor = floatArrayOf(g, g, g)
                pixels[y * width + x].UpdateValues(newColor)
            }
        }
    }
}