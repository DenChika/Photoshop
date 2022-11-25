package LinePainterHelpers

import Configurations.AppConfiguration
import androidx.compose.ui.geometry.Offset

class OffsetCounter {
    companion object {
        fun getActualOffset(offset: Offset) : Offset {
            val maxVisualisationOffset = Offset(
                minOf(1500, AppConfiguration.Image.width).toFloat(),
                minOf(900, AppConfiguration.Image.height).toFloat())
            return Offset(offset.x / maxVisualisationOffset.x * AppConfiguration.Image.width,
                offset.y / maxVisualisationOffset.y * AppConfiguration.Image.height)
        }
        fun checkOffSetValidity(offset: Offset) : Boolean {
            return offset.x >= 0 &&
                    offset.y >= 0 &&
                    offset.x <= AppConfiguration.Image.width &&
                    offset.y <= AppConfiguration.Image.height
        }
    }
}