package Scaling

import ColorSpaces.ColorSpace
import Configurations.ImageConfiguration
import androidx.compose.ui.geometry.Offset
import kotlin.math.ceil
import kotlin.math.floor

class BilinearAlgorithm {
    companion object {
        fun getNewImage(
            colorSpace: ColorSpace,
            image: ImageConfiguration,
            scaledOffset: Offset,
            newWidth: Int,
            newHeight: Int
        ): ImageConfiguration {
            val newPixels = Array(newHeight * newWidth) { colorSpace.GetDefault() }
            for (i in 0 until newHeight) {
                for (j in 0 until newWidth) {
                    val originalOffset =
                        PositionFinder.getOriginalFromScaled(
                            Offset(
                                (j + scaledOffset.x),
                                (i + scaledOffset.y)
                            ),
                            image.width,
                            image.height,
                            newWidth,
                            newHeight
                        )
                    newPixels[i * newWidth + j].UpdateValues(
                        interpolateNearestPixels(
                            image,
                            PositionFinder.getValidValue(originalOffset.x, image.width - 1),
                            PositionFinder.getValidValue(originalOffset.y, image.height - 1)
                        )
                    )
                }
            }
            return ImageConfiguration(image.format, newWidth, newHeight, image.maxShade, newPixels)
        }

        private fun interpolateNearestPixels(
            image: ImageConfiguration,
            x: Float,
            y: Float,
        ): FloatArray {
            val minX = PositionFinder.getValidValue(floor(x), image.width - 1).toInt()
            val maxX = PositionFinder.getValidValue(ceil(x), image.width - 1).toInt()
            val minY = PositionFinder.getValidValue(floor(y), image.height - 1).toInt()
            val maxY = PositionFinder.getValidValue(ceil(y), image.height - 1).toInt()

            if (minX != maxX && minY != maxY) {
                val p11 = image.getPixel(minX, minY)
                val p12 = image.getPixel(minX, maxY)
                val p21 = image.getPixel(maxX, minY)
                val p22 = image.getPixel(maxX, maxY)

                val w11 = (maxX - x) * (maxY - y)
                val w12 = (maxX - x) * (y - minY)
                val w21 = (x - minX) * (maxY - y)
                val w22 = (x - minX) * (y - minY)

                return floatArrayOf(
                    p11.firstShade * w11 + p12.firstShade * w12 + p21.firstShade * w21 + p22.firstShade * w22,
                    p11.secondShade * w11 + p12.secondShade * w12 + p21.secondShade * w21 + p22.secondShade * w22,
                    p11.thirdShade * w11 + p12.thirdShade * w12 + p21.thirdShade * w21 + p22.thirdShade * w22
                )
            }
            else if (minX == maxX && minY == maxY) {
                return image.getPixel(minX, minY).GetFloatArrayOfValues()
            } else if (minX == maxX) {
                val p1 = image.getPixel(minX, minY)
                val p2 = image.getPixel(minX, maxY)

                val w1 = maxY - y
                val w2 = y - minY

                return floatArrayOf(
                    p1.firstShade * w1 + p2.firstShade * w2,
                    p1.secondShade * w1 + p2.secondShade * w2,
                    p1.thirdShade * w1 + p2.thirdShade * w2
                )
            } else {
                val p1 = image.getPixel(minX, minY)
                val p2 = image.getPixel(maxX, minY)

                val w1 = maxX - x
                val w2 = x - minX

                return floatArrayOf(
                    p1.firstShade * w1 + p2.firstShade * w2,
                    p1.secondShade * w1 + p2.secondShade * w2,
                    p1.thirdShade * w1 + p2.thirdShade * w2
                )
            }
        }
    }
}