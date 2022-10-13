package Formats

import Bitmap
import Interfaces.IFormat
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import java.awt.Dimension
import java.awt.image.BufferedImage

class P5 : IFormat {
    override fun Handle(width: Int, height: Int, byteArray: ByteArray) : ImageBitmap? {
        val size = Dimension(width, height)
        val img = BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB)
        for (posX in 0 until height) {
            for (posY in 0 until width) {
                val shade = byteArray[posX * width + posY].toUInt()
                img.setRGB(posY, posX, Color(shade.toInt(), shade.toInt(), shade.toInt()).toArgb())
            }
        }
        return Bitmap.imageFromBuffer(img)
    }
}