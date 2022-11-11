package Formats

import ColorSpaces.ColorSpaceInstance
import Configurations.AppConfiguration
import Configurations.ImageConfiguration
import Interfaces.IFormat
import Parsers.BytesParser
import Tools.HeaderDiscrepancyException

class P6 : IFormat {
    private val colorsByPixel = 3
    override fun HandleReader(width: Int, height: Int, maxShade: Int, byteArray: ByteArray): ImageConfiguration {
        val pixels = Array(height * width) {
            AppConfiguration.ColorSpace.GetDefault() }
        try {
            for (posY in 0 until height) {
                for (posX in 0 until width) {
                    val shadeFirst = (if (byteArray[posY * width * colorsByPixel + posX * colorsByPixel] < 0)
                        byteArray[posY * width * colorsByPixel + posX * colorsByPixel] + 256 else byteArray[posY * width * colorsByPixel + posX * colorsByPixel]).toFloat()

                    val shadeSecond = (if (byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 1] < 0)
                        byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 1] + 256 else byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 1]).toFloat()

                    val shadeThird = (if (byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 2] < 0)
                        byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 2] + 256 else byteArray[posY * width * colorsByPixel + posX * colorsByPixel + 2]).toFloat()
                    if (shadeThird > maxShade || shadeSecond > maxShade || shadeFirst > maxShade)
                    {
                        throw HeaderDiscrepancyException.wrongMaxShade()
                    }
                    val finalShadeFirst = shadeFirst / maxShade
                    val finalShadeSecond = shadeSecond / maxShade
                    val finalShadeThird = shadeThird / maxShade

                    pixels[posY * width + posX].firstShade = finalShadeFirst
                    pixels[posY * width + posX].secondShade = finalShadeSecond
                    pixels[posY * width + posX].thirdShade = finalShadeThird
                }
            }
        }
        catch (e : IndexOutOfBoundsException) {
            throw HeaderDiscrepancyException.wrongSize()
        }

        return ImageConfiguration(Format.P6, width, height, maxShade, pixels)
    }

    override fun HandleWriter(width: Int, height: Int, maxShade: Int, pixels: Array<ColorSpaceInstance>): ByteArray {
        var newByteArray = byteArrayOf('P'.code.toByte(), (6 + '0'.code).toByte(),
            10.toByte())
        newByteArray += BytesParser.ParseValueForBytes(width) + byteArrayOf(32.toByte()) + BytesParser.ParseValueForBytes(
            height) + 10.toByte() + BytesParser.ParseValueForBytes(maxShade) + 10.toByte() + ByteArrayFromPixels(pixels)
        return newByteArray
    }

    override fun ByteArrayFromPixels(pixels: Array<ColorSpaceInstance>): ByteArray {
        val array = ByteArray(pixels.size * 3)
        for (pixel in pixels.indices)
        {
            val pixelBytes = pixels[pixel].GetBytes()
            array[pixel * 3] = pixelBytes[0]
            array[pixel * 3 + 1] = pixelBytes[1]
            array[pixel * 3 + 2] = pixelBytes[2]
        }
        return array
    }
}