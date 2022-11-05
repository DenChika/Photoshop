package Formats

import Configuration.MutableConfigurationsState
import Converters.Bitmap
import Interfaces.Format
import Parsers.BytesParser
import Tools.InvalidHeaderException
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import java.awt.image.BufferedImage

class P6 : Format {
    private val colorsByPixel = 3
    override fun HandleReader(width: Int, height: Int, maxShade: Int, byteArray: ByteArray): ImageBitmap? {
        val img = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        try {
            for (posY in 0 until height) {
                for (posX in 0 until width) {
                    val shadeR = (if (byteArray[posY * width * colorsByPixel + posX * colorsByPixel] < 0)
                        byteArray[posY * width * colorsByPixel + posX * colorsByPixel] + 256 else byteArray[posY * width * colorsByPixel + posX * colorsByPixel]).toInt()

                    val shadeG = (if (byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 1] < 0)
                        byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 1] + 256 else byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 1]).toInt()

                    val shadeB = (if (byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 2] < 0)
                        byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 2] + 256 else byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 2]).toInt()
                    if (shadeB > maxShade || shadeG > maxShade || shadeR > maxShade)
                    {
                        throw InvalidHeaderException("Shade of pixel can't be greater than max shade")
                    }
                    val finalShadeR = shadeR * 255 / maxShade
                    val finalShadeG = shadeG * 255 / maxShade
                    val finalShadeB = shadeB * 255 / maxShade

                    img.setRGB(posX, posY, Color(finalShadeR, finalShadeG, finalShadeB).toArgb())
                }
            }
        }
        catch (e : IndexOutOfBoundsException) {
            throw InvalidHeaderException("Byte array doesn't match width and height")
        }

        MutableConfigurationsState.mode = Modes.P6
        MutableConfigurationsState.bufferedImage = img
        MutableConfigurationsState.byteArray = byteArray
        MutableConfigurationsState.shade = maxShade

        return Bitmap.imageFromBuffer(img)
    }

    override fun HandleWriter(width: Int, height: Int, maxShade: Int, byteArray: ByteArray?): ByteArray {
        var newByteArray = byteArrayOf('P'.code.toByte(), (6 + '0'.code).toByte(),
            10.toByte())
        newByteArray += BytesParser.ParseValueForBytes(width) + byteArrayOf(32.toByte()) + BytesParser.ParseValueForBytes(
            height) + 10.toByte() + BytesParser.ParseValueForBytes(maxShade) + 10.toByte() + byteArray!!
        return newByteArray
    }
}