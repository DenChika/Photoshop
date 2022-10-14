package Formats

import Interfaces.IFormat
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import java.awt.Dimension
import java.awt.image.BufferedImage

class P6 : IFormat {
    private val colorsByPixel = 3
    override fun HandleReader(width: Int, height: Int, maxShade: UInt, byteArray: ByteArray) : ImageBitmap? {
        val size = Dimension(width, height)
        val img = BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB)
        for (posY in 0 until height) {
            for (posX in 0 until width) {
                val shadeR = byteArray[posY * width + posX * colorsByPixel].toUInt()
                val shadeG = byteArray[posY * width + posX * colorsByPixel + 1].toUInt()
                val shadeB = byteArray[posY * width + posX * colorsByPixel + 2].toUInt()
                val finalShadeR = shadeR * 255u / maxShade
                val finalShadeG = shadeG * 255u / maxShade
                val finalShadeB = shadeB * 255u / maxShade
                img.setRGB(posX, posY, Color(finalShadeR.toInt(), finalShadeG.toInt(), finalShadeB.toInt()).toArgb())
            }
        }
        return Bitmap.imageFromBuffer(img)
    }
    override fun HandleWriter(width: Int, height: Int, maxShade: Int, byteArray: ByteArray?) : ByteArray? {
        return null
    }
}