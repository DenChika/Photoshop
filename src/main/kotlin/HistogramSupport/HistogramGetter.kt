package HistogramSupport

import Converters.Bitmap
import Tools.GraphicLegendsException
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import java.awt.image.BufferedImage

class HistogramGetter {
    companion object {
        fun fromInfo(info : List<Int>) : ImageBitmap {
            val bufferedImage = BufferedImage(256, 100, BufferedImage.TYPE_INT_RGB)
            val maxValue = (info.maxOrNull() ?: throw GraphicLegendsException("Undefined exception")).toFloat()
            for (posX in 0 until 256) {
                for (posY in 0 until 100) {
                    bufferedImage.setRGB(
                        posX,
                        posY,
                        (if ((100 - posY) > (info[posX] / maxValue) * 100) Color.White else Color.Black).toArgb()
                    )
                }
            }
            return Bitmap.imageFromBuffer(bufferedImage)
        }
    }
}