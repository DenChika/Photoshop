package Configurations

import ColorSpaces.ColorSpaceInstance
import Converters.Bitmap
import Formats.Format
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import java.awt.image.BufferedImage

class ImageConfiguration(_format : Format, _width : Int, _height : Int, _maxShade : Int, _pixels : Array<ColorSpaceInstance>) {
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
                val pixel = pixels[posY * width + posX].GetRGBPixelValue()
                bufferedImage.setRGB(posX, posY, Color((pixel[0] * 255).toInt(), (pixel[1] * 255).toInt(), (pixel[2] * 255).toInt()).toArgb())
            }
        }
        return Bitmap.imageFromBuffer(bufferedImage)
    }
    fun getPixels() : Array<ColorSpaceInstance> {
        return pixels
    }
}