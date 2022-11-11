package Formats

import ColorSpaces.ColorSpaceInstance
import Configurations.AppConfiguration
import Configurations.ImageConfiguration
import Interfaces.IFormat
import Parsers.BytesParser
import Tools.InvalidHeaderException

class P5 : IFormat {
    override fun HandleReader(width: Int, height: Int, maxShade: Int, byteArray: ByteArray) : ImageConfiguration {
        val pixels = Array(height * width) {
            AppConfiguration.ColorSpace.GetDefault()}
        try {
            for (posY in 0 until height) {
                for (posX in 0 until width) {
                    val shade = (if (byteArray[posY * width + posX] < 0) byteArray[posY * width + posX] + 256 else byteArray[posY * width + posX]).toFloat()
                    if (shade > maxShade)
                    {
                        throw InvalidHeaderException("Shade of pixel can't be greater than max shade")
                    }
                    val finalShade = shade / maxShade
                    pixels[posY * width + posX].firstShade = finalShade
                    pixels[posY * width + posX].secondShade = finalShade
                    pixels[posY * width + posX].thirdShade = finalShade
                }
            }
        }
        catch (e : IndexOutOfBoundsException)
        {
            throw InvalidHeaderException("Byte array doesn't match width and height")
        }

        return ImageConfiguration(Format.P5, width, height, maxShade, pixels)
    }

    override fun HandleWriter(width: Int, height: Int, maxShade: Int, pixels: Array<ColorSpaceInstance>) : ByteArray {
        var newByteArray = byteArrayOf('P'.code.toByte(), (5 + '0'.code).toByte(),
            10.toByte())
        newByteArray += BytesParser.ParseValueForBytes(width) + byteArrayOf(32.toByte()) + BytesParser.ParseValueForBytes(
            height) + 10.toByte() + BytesParser.ParseValueForBytes(maxShade) + 10.toByte() + ByteArrayFromPixels(pixels)
        return newByteArray
    }

    override fun ByteArrayFromPixels(pixels: Array<ColorSpaceInstance>): ByteArray {
        val array = ByteArray(pixels.size)
        for (pixel in pixels.indices)
        {
            val pp = pixels[pixel].GetBytes()[0]
            array[pixel] = pp
        }
        return array
    }
}