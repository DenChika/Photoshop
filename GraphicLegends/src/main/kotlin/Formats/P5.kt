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

class P5 : Format {
    override fun HandleReader(width: Int, height: Int, maxShade: Int, byteArray: ByteArray) : ImageBitmap? {
        val img = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        try {
            for (posY in 0 until height) {
                for (posX in 0 until width) {
                    val shade = (if (byteArray[posY * width + posX] < 0) byteArray[posY * width + posX] + 256 else byteArray[posY * width + posX]).toInt()
                    if (shade > maxShade)
                    {
                        throw InvalidHeaderException("Shade of pixel can't be greater than max shade")
                    }
                    val finalShade = shade * 255 / maxShade
                    img.setRGB(posX, posY, Color(finalShade, finalShade, finalShade).toArgb())
                }
            }
        }
        catch (e : IndexOutOfBoundsException)
        {
            throw InvalidHeaderException("Byte array doesn't match width and height")
        }

        MutableConfigurationsState.mode = Modes.P5
        MutableConfigurationsState.bufferedImage = img
        MutableConfigurationsState.byteArray = byteArray
        MutableConfigurationsState.shade = maxShade

        return Bitmap.imageFromBuffer(img)
    }

    override fun HandleWriter(width: Int, height: Int, maxShade: Int, byteArray: ByteArray?) : ByteArray {
        var newByteArray = byteArrayOf('P'.code.toByte(), (5 + '0'.code).toByte(),
            10.toByte())
        newByteArray += BytesParser.ParseValueForBytes(width) + byteArrayOf(32.toByte()) + BytesParser.ParseValueForBytes(
            height) + 10.toByte() + BytesParser.ParseValueForBytes(maxShade) + 10.toByte() + byteArray!!
        return newByteArray
    }
}