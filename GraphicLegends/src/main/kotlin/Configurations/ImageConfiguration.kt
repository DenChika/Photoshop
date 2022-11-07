package Configurations

import Converters.Bitmap
import Formats.Format
import Interfaces.IColorSpace
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import java.awt.image.BufferedImage

class ImageConfiguration(_format : Format, _width : Int, _height : Int, _maxShade : Int, _pixels : Array<IColorSpace>) {
    val format : Format = _format
    val width = _width
    val height = _height
    val maxShade = _maxShade
    private var pixels = _pixels

    constructor() : this(Format.P6, 0, 0, 0, arrayOf())

    fun getImageBitmap() : ImageBitmap {
        val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        for (posY in 0 until height) {
            for (posX in 0 until width) {
                val pixel = pixels[posY * width + posX].ToRGB().GetPixelValue()
                bufferedImage.setRGB(posX, posY, Color(pixel[0], pixel[1], pixel[2]).toArgb())
            }
        }
        return Bitmap.imageFromBuffer(bufferedImage)
    }
    fun getPixels() : Array<IColorSpace> {
        return pixels
    }
}