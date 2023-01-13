package Formats

import ColorSpaces.ColorSpaceInstance
import Configurations.AppConfiguration
import Configurations.ImageConfiguration
import Gammas.GammaModes
import Interfaces.IFormat
import java.util.zip.Deflater
import java.util.zip.Deflater.FULL_FLUSH

class PNG : IFormat {
    private val pngStringLength = 8
    private val chunkLengthSize = 4
    private val chunkNameSize = 4
    private val widthHeightSize = 4
    private val headerLength = 13
    private val paletteBytesPerColor = 3
    private val gammaLength = 4
    private val power256 = intArrayOf(256 * 256 * 256, 256 * 256, 256, 1)
    override fun HandleReader(width: Int, height: Int, maxShade: Int, byteArray: ByteArray): ImageConfiguration {
        return ImageConfiguration()
    }

    override fun HandleWriter(width: Int, height: Int, maxShade: Int, pixels: Array<ColorSpaceInstance>): ByteArray {
        var newByteArray = byteArrayOf(137.toByte(), 80.toByte(), 78.toByte(), 71.toByte(), 13.toByte(),  10.toByte(), 26.toByte(), 10.toByte())

        newByteArray += writeHeader(width, height)

        newByteArray += writeGamma()

        newByteArray += writeData(width, height, pixels)

        newByteArray += writeEnd()

        return newByteArray
    }

    override fun ByteArrayFromPixels(pixels: Array<ColorSpaceInstance>): ByteArray {
        return ByteArray(0)
    }

    fun writeHeader(width: Int, height: Int): ByteArray {
        // Header
        var newByteArray = ByteArray(0)
        newByteArray += byteArrayOf(0.toByte(), 0.toByte(), 0.toByte(), headerLength.toByte())
        newByteArray += byteArrayOf('I'.code.toByte(), 'H'.code.toByte(), 'D'.code.toByte(), 'R'.code.toByte())

        //width: 4 bytes
        val widthBytes = ByteArray(widthHeightSize)
        var currentWidth = width
        for (i in 0 until widthHeightSize) {
            widthBytes[i] = (currentWidth / power256[i]).toByte()
            currentWidth %= power256[i]
        }
        newByteArray += widthBytes

        //height: 4 bytes
        val heightBytes = ByteArray(widthHeightSize)
        var currentHeight = height
        for (i in 0 until widthHeightSize) {
            heightBytes[i] = (currentHeight / power256[i]).toByte()
            currentHeight %= power256[i]
        }
        newByteArray += heightBytes

        //   Bit depth:          1 byte
        //   Color type:         1 byte
        //   Compression method: 1 byte
        //   Filter method:      1 byte
        //   Interlace method:   1 byte
        newByteArray += byteArrayOf(8.toByte(), 2.toByte(), 0.toByte(), 0.toByte(), 0.toByte())

        //CRC
        newByteArray += byteArrayOf(0.toByte(), 0.toByte(), 0.toByte(), 0.toByte())

        return newByteArray
    }

    fun writeGamma(): ByteArray {
        var newByteArray  = ByteArray(0)

        newByteArray += byteArrayOf(0.toByte(), 0.toByte(), 0.toByte(), gammaLength.toByte())
        newByteArray += byteArrayOf('g'.code.toByte(), 'A'.code.toByte(), 'M'.code.toByte(), 'A'.code.toByte())

        var gamma: Float
        if (AppConfiguration.Gamma.AssignMode == GammaModes.SRGB) {
            gamma = 1 / 2.2f
        } else {
            gamma = 1 / AppConfiguration.Gamma.AssignCustomValue
        }

        gamma *= 100000
        val gammaInt = gamma.toInt()

        val gammaBytes = ByteArray(gammaLength)
        var currentGamma = gammaInt
        for (i in 0 until gammaLength) {
            gammaBytes[i] = (currentGamma / power256[i]).toByte()
            currentGamma %= power256[i]
        }
        newByteArray += gammaBytes

        //CRC
        newByteArray += byteArrayOf(0.toByte(), 0.toByte(), 0.toByte(), 0.toByte())

        return newByteArray
    }

    fun writeData(width: Int, height: Int, pixels: Array<ColorSpaceInstance>): ByteArray {
        var newByteArray = ByteArray(0)

        val compressedData = deflateData(width, height, pixels)

        val dataLength = compressedData.size
        val dataLengthBytes = ByteArray(chunkLengthSize)
        var currentDataLength = dataLength
        for (i in 0 until 4) {
            dataLengthBytes[i] = (currentDataLength / power256[i]).toByte()
            currentDataLength %= power256[i]
        }

        //IDAT
        newByteArray += dataLengthBytes

        newByteArray += byteArrayOf('I'.code.toByte(), 'D'.code.toByte(), 'A'.code.toByte(), 'T'.code.toByte())

        //compressed data
        newByteArray += compressedData

        //CRC
        newByteArray += byteArrayOf(0.toByte(), 0.toByte(), 0.toByte(), 0.toByte())

        return newByteArray
    }

    fun deflateData(width: Int, height: Int, pixels: Array<ColorSpaceInstance>): ByteArray {
        var data = ByteArray(0)

        for (posY in 0 until height) {
            var currentData = byteArrayOf(0)
            for (posX in 0 until width) {
                currentData += byteArrayOf((pixels[posY * width + posX].firstShade * 255).toInt().toByte(),
                    (pixels[posY * width + posX].secondShade * 255).toInt().toByte(),
                    (pixels[posY * width + posX].thirdShade * 255).toInt().toByte())
            }

            data += currentData
        }

        val deflater = Deflater(9)
        deflater.setInput(data)

        var compressedData = ByteArray(data.size)
        val ans = deflater.deflate(compressedData, 0, compressedData.size, FULL_FLUSH)
        compressedData = compressedData.slice(0 until ans).toByteArray()

        return compressedData
    }

    fun writeEnd(): ByteArray {
        // End
        var newByteArray = ByteArray(0)
        newByteArray += byteArrayOf(0.toByte(), 0.toByte(), 0.toByte(), 0.toByte())
        newByteArray += byteArrayOf('I'.code.toByte(), 'E'.code.toByte(), 'N'.code.toByte(), 'D'.code.toByte())

        //CRC
        newByteArray += byteArrayOf(0.toByte(), 0.toByte(), 0.toByte(), 0.toByte())

        return newByteArray
    }
}