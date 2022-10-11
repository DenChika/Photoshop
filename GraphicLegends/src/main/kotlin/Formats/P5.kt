package Formats

import Interfaces.IFormat
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import java.awt.Dimension
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class P5(private val fileToPath: String) : IFormat {
    override fun Handle(width: Int, height: Int, byteArray: ByteArray) {
        val size = Dimension(width, height)
        val img = BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB)
        for (posX in 0 until height) {
            for (posY in 0 until width) {
                val shade = byteArray[posX * width + posY].toUInt()
                img.setRGB(posY, posX, Color(shade.toInt(), shade.toInt(), shade.toInt()).toArgb())
            }
        }
        ImageIO.write(img, "BMP", File(fileToPath))
    }
}