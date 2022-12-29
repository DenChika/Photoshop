package Scaling

import ColorSpaces.ColorSpace
import Configurations.ImageConfiguration
import androidx.compose.ui.geometry.Offset
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sin

class Lanczos3Algorithm {
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
                    val pixel = floatArrayOf(0f, 0f, 0f)
                    for (columnDiff in -2..2) {
                        for (rowDiff in -2..2) {
                            if (originalOffset.y.toInt() + columnDiff >= image.height
                                || originalOffset.y.toInt() + columnDiff < 0
                            ) continue
                            if (originalOffset.x.toInt() + rowDiff >= image.width
                                || originalOffset.x.toInt() + rowDiff < 0
                            ) continue

                            val kernel = getKernel(columnDiff.toFloat(), 2f) *
                                    getKernel(rowDiff.toFloat(), 2f)
                            val p = image.getPixel(
                                originalOffset.x.toInt() + rowDiff,
                                originalOffset.y.toInt() + columnDiff
                            )
                            pixel[0] += p.firstShade * kernel
                            pixel[1] += p.secondShade * kernel
                            pixel[2] += p.thirdShade * kernel
                        }
                    }
                    newPixels[i * newWidth + j].UpdateValues(pixel)
                }
            }
            return ImageConfiguration(image.format, newWidth, newHeight, image.maxShade, newPixels)
        }

        private fun getKernel(value: Float, a: Float): Float {
            return when {
                value == 0f -> 1f
                abs(value) > a -> 0f
                else -> (sin(PI * value) * sin(PI * value / 3f) / (PI * PI * value * value / 3f)).toFloat()
            }
        }
    }
}