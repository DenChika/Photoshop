package Formats

import Configurations.AppConfiguration
import Configurations.ImageConfiguration
import Interfaces.IColorSpace
import Interfaces.IFormat
import Parsers.BytesParser
import Tools.InvalidHeaderException

class P6 : IFormat {
    private val colorsByPixel = 3
    override fun HandleReader(width: Int, height: Int, maxShade: Int, byteArray: ByteArray): ImageConfiguration {
        val pixels = Array(height * width) {
            AppConfiguration.ColorSpace.GetInstance(0, 0, 0) }
        try {
            for (posY in 0 until height) {
                for (posX in 0 until width) {
                    val shadeFirst = (if (byteArray[posY * width * colorsByPixel + posX * colorsByPixel] < 0)
                        byteArray[posY * width * colorsByPixel + posX * colorsByPixel] + 256 else byteArray[posY * width * colorsByPixel + posX * colorsByPixel]).toInt()

                    val shadeSecond = (if (byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 1] < 0)
                        byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 1] + 256 else byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 1]).toInt()

                    val shadeThird = (if (byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 2] < 0)
                        byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 2] + 256 else byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 2]).toInt()
                    if (shadeThird > maxShade || shadeSecond > maxShade || shadeFirst > maxShade)
                    {
                        throw InvalidHeaderException("Shade of pixel can't be greater than max shade")
                    }
                    val finalShadeFirst = shadeFirst * 255 / maxShade
                    val finalShadeSecond = shadeSecond * 255 / maxShade
                    val finalShadeThird = shadeThird * 255 / maxShade

                    pixels[posY * width + posX] = AppConfiguration.ColorSpace
                                                                  .GetInstance(finalShadeFirst,
                                                                      finalShadeSecond,
                                                                      finalShadeThird)
                }
            }
        }
        catch (e : IndexOutOfBoundsException) {
            throw InvalidHeaderException("Byte array doesn't match width and height")
        }

        return ImageConfiguration(Format.P6, width, height, maxShade, pixels)
    }

    override fun HandleWriter(width: Int, height: Int, maxShade: Int, pixels: Array<IColorSpace>): ByteArray {
        var newByteArray = byteArrayOf('P'.code.toByte(), (6 + '0'.code).toByte(),
            10.toByte())
        newByteArray += BytesParser.ParseValueForBytes(width) + byteArrayOf(32.toByte()) + BytesParser.ParseValueForBytes(
            height) + 10.toByte() + BytesParser.ParseValueForBytes(maxShade) + 10.toByte() + ByteArrayFromPixels(pixels)
        return newByteArray
    }

    override fun ByteArrayFromPixels(pixels: Array<IColorSpace>): ByteArray {
        val array = ByteArray(pixels.size * 3)
        for (pixel in pixels.indices)
        {
            val pp = pixels[pixel].GetBytes()
            array[pixel * 3] = pp[0]
            array[pixel * 3 + 1] = pp[1]
            array[pixel * 3 + 2] = pp[2]
        }
        return array
    }
}