package Scaling

import ColorSpaces.ColorSpace
import Configurations.ImageConfiguration
import androidx.compose.ui.geometry.Offset
import kotlin.math.abs

class BCSplinesAlgorithm {
    companion object {
        fun getNewImage(
            colorSpace: ColorSpace,
            image: ImageConfiguration,
            scaledOffset: Offset,
            newWidth: Int,
            newHeight: Int,
            bCoefficient: Float,
            cCoefficient: Float
        ): ImageConfiguration {
            val newPixels = Array(newHeight * newWidth) { colorSpace.GetDefault() }
            for (i in 0 until newHeight) {
                for (j in 0 until newWidth) {
                    val originalOffset = PositionFinder.getOriginalFromScaled(
                        Offset(
                            (j + scaledOffset.x), (i + scaledOffset.y)
                        ), image.width, image.height, newWidth, newHeight
                    )
                    val pixel = floatArrayOf(0f, 0f, 0f)
                    for (columnDiff in -2..2) {
                        for (rowDiff in -2..2) {
                            if (originalOffset.y.toInt() + columnDiff >= image.height ||
                                originalOffset.y.toInt() + columnDiff < 0) continue
                            if (originalOffset.x.toInt() + rowDiff >= image.width ||
                                originalOffset.x.toInt() + rowDiff < 0) continue

                            val kernel = getKernel(
                                columnDiff.toFloat(),
                                bCoefficient,
                                cCoefficient
                            ) * getKernel(
                                rowDiff.toFloat(),
                                bCoefficient,
                                cCoefficient
                            )
                            val p = image.getPixel(
                                originalOffset.x.toInt() + rowDiff, originalOffset.y.toInt() + columnDiff
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

        private fun getKernel(value: Float, B: Float, C: Float): Float {
            val valueAbs = abs(value)
            return when {
                valueAbs < 1 -> ((12 - 9 * B - 6 * C) * valueAbs * valueAbs * valueAbs +
                        (-18 + 12 * B + 6 * C) * valueAbs * valueAbs +
                        (6 - 2 * B)) / 6

                1 <= valueAbs && valueAbs < 2 -> ((-B - 6 * C) * valueAbs * valueAbs * valueAbs +
                        (6 * B + 30 * C) * valueAbs * valueAbs +
                        (-12 * B - 48 * C) * valueAbs +
                        (8 * B + 24 * C)) / 6

                else -> 0f
            }
        }
    }
}