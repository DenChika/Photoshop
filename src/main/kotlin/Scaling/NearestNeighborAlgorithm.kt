package Scaling

import ColorSpaces.ColorSpace
import Configurations.ImageConfiguration
import androidx.compose.ui.geometry.Offset

class NearestNeighborAlgorithm {
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
                        image.getPixel(
                            PositionFinder.getValidValue(originalOffset.x, image.width - 1).toInt(),
                            PositionFinder.getValidValue(originalOffset.y, image.height - 1).toInt()
                        ).GetFloatArrayOfValues()
                    )
                }
            }
            return ImageConfiguration(image.format, newWidth, newHeight, image.maxShade, newPixels)
        }
    }
}