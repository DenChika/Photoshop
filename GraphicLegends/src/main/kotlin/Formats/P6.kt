package Formats

import Configuration.MutableConfigurationsState
import Interfaces.IFormat
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import java.awt.Dimension
import java.awt.image.BufferedImage

class P6 : IFormat {
    private val colorsByPixel = 3
    override fun HandleReader(width: Int, height: Int, maxShade: Int, byteArray: ByteArray): ImageBitmap? {
        val img = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        for (posY in 0 until height) {
            for (posX in 0 until width) {
                val shadeR = (if (byteArray[posY * width * colorsByPixel + posX * colorsByPixel] < 0)
                    byteArray[posY * width * colorsByPixel + posX * colorsByPixel] + 256 else byteArray[posY * width * colorsByPixel + posX * colorsByPixel]).toInt()

                val shadeG = (if (byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 1] < 0)
                    byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 1] + 256 else byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 1]).toInt()

                val shadeB = (if (byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 2] < 0)
                    byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 2] + 256 else byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 2]).toInt()

                val finalShadeR = shadeR * 255 / maxShade
                val finalShadeG = shadeG * 255 / maxShade
                val finalShadeB = shadeB * 255 / maxShade

                img.setRGB(posX, posY, Color(finalShadeR, finalShadeG, finalShadeB).toArgb())
            }
        }

        MutableConfigurationsState.mode = Modes.P6
        MutableConfigurationsState.bufferedImage = img
        MutableConfigurationsState.byteArray = byteArray
        MutableConfigurationsState.shade = maxShade

        return Bitmap.imageFromBuffer(img)
    }

    override fun HandleWriter(width: Int, height: Int, maxShade: Int, byteArray: ByteArray?): ByteArray? {
        return null
    }
}