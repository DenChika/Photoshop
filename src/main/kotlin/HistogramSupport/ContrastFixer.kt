package HistogramSupport

import Configurations.ImageConfiguration
import kotlin.math.min

class ContrastFixer {
    private data class Borders(val left : Float, val right : Float)
    companion object {
        fun fix(
            image: ImageConfiguration,
            info: List<List<Int>>,
            ignoreThresholdProportion: Float
        ) {
            val (leftBorder,  rightBorder) = findEdges(info,
                ignoreThresholdProportion * image.width * image.height)
            for (y in 0 until image.height) {
                for (x in 0 until image.width) {
                    val pixel = image.getPixel(x, y)
                    val firstShade = (pixel.firstShade - leftBorder) / (rightBorder - leftBorder)
                    val secondShade = (pixel.secondShade - leftBorder) / (rightBorder - leftBorder)
                    val thirdShade = (pixel.thirdShade - leftBorder) / (rightBorder - leftBorder)
                    image.setPixel(x, y, floatArrayOf(firstShade, secondShade, thirdShade))
                }
            }

        }

        private fun findEdges(info: List<List<Int>>, ignoreThreshold: Float) : Borders {
            val firstShade = findShadeEdges(info[0], ignoreThreshold)
            val secondShade = findShadeEdges(info[0], ignoreThreshold)
            val thirdShade = findShadeEdges(info[0], ignoreThreshold)
            return Borders(
                left = min(min(firstShade.left, secondShade.left), thirdShade.left),
                right = min(min(firstShade.right, secondShade.right), thirdShade.right)
            )
        }

        private fun findShadeEdges(info: List<Int>, ignoreThreshold: Float) : Borders {
            var current = 0
            var breakIndex = 0
            for (i in 0..255)
            {
                current += info[i]

                if (current >= ignoreThreshold) {
                    breakIndex = i
                    break
                }
            }

            val left = breakIndex / 255f

            current = 0
            for (i in 255 downTo 0)
            {
                current += info[i]

                if (current >= ignoreThreshold) {
                    breakIndex = i
                    break
                }
            }

            val right = breakIndex / 255f

            return Borders(left, right)
        }
    }
}