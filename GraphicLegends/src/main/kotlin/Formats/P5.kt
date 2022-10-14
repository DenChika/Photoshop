package Formats

import Converters.Bitmap
import Configuration.MutableConfigurationsState
import Interfaces.IFormat
import Parsers.BytesParser
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import java.awt.Dimension
import java.awt.image.BufferedImage

class P5 : IFormat {
    override fun HandleReader(width: Int, height: Int, maxShade: Int, byteArray: ByteArray) : ImageBitmap? {
        val size = Dimension(width, height)
        val img = BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB)
        for (posX in 0 until height) {
            for (posY in 0 until width) {
                val shade = byteArray[posX * width + posY].toUInt()
                img.setRGB(posY, posX, Color(shade.toInt(), shade.toInt(), shade.toInt()).toArgb())
            }
        }
        MutableConfigurationsState.mode = Modes.P5
        MutableConfigurationsState.bufferedImage = img
        MutableConfigurationsState.byteArray = byteArray
        MutableConfigurationsState.shade = maxShade
        return Bitmap.imageFromBuffer(img)
    }
    override fun HandleWriter(width: Int, height: Int, maxShade: Int, byteArray: ByteArray?) : ByteArray? {
        var newByteArray = byteArrayOf('P'.code.toByte(), (5 + '0'.code).toByte(),
            10.toByte())
        newByteArray += BytesParser.ParseValueForBytes(width) + byteArrayOf(32.toByte()) + BytesParser.ParseValueForBytes(
            height) + 10.toByte() + BytesParser.ParseValueForBytes(maxShade) + 10.toByte() + byteArray!!
        return newByteArray
    }
}