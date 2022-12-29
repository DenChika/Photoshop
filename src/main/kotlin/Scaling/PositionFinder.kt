package Scaling

import androidx.compose.ui.geometry.Offset

class PositionFinder {
    companion object {
        fun getOriginalFromScaled(
            scaledOffset: Offset,
            originalWidth: Int,
            originalHeight: Int,
            scaledWidth: Int,
            scaledHeight: Int
        ): Offset {
            return Offset(
                scaledOffset.x * originalWidth / scaledWidth,
                scaledOffset.y * originalHeight / scaledHeight
            )
        }

        fun getScaledFromOriginal(
            originalOffset: Offset,
            originalWidth: Int,
            originalHeight: Int,
            scaledWidth: Int,
            scaledHeight: Int
        ) : Offset {
            return Offset(
                originalOffset.x * scaledWidth / originalWidth,
                originalOffset.y * scaledHeight / originalHeight
            )
        }

        fun getValidValue(value: Float, border: Int) : Float {
            return if (value < 0) 0f else if (value > border) border.toFloat() else value
        }
    }
}