package Parsers

import Configurations.AppConfiguration
import Configurations.ImageConfiguration
import Formats.P5
import Formats.P6
import Tools.FormatException
import Tools.InvalidHeaderException
import java.io.File

class BytesParser {
    companion object {

        val fileTypeSet = hashSetOf<Char>('1', '2', '3', '4', '5', '6', '7')

        fun ParseBytesForFile(file: File): ImageConfiguration? { //TODO check return type
            val byteArray = file.readBytes()

            println(byteArray)

            var magicNumber: String? = null
            var width = -1
            var height = -1
            var maxShade = -1
            var bodyStartIndex = -1

            var index = 0
            while (maxShade == -1) {
                if (index >= byteArray.size) {
                    break
                }

                val byte = byteArray[index]

                if (byte != 32.toByte() && byte != 10.toByte()) {

                    val nextString = NextString(byteArray, index)
                    index += nextString.length

                    if (magicNumber == null) {

                        magicNumber = nextString

                        if (magicNumber.length != 2 || magicNumber[0] != 'P' || !fileTypeSet.contains(magicNumber[1])) {
                            throw FormatException("File format is not P5 or P6")
                        }

                    } else if (width == -1) {

                        try {
                            width = nextString.toInt()
                        } catch (e: NumberFormatException) {
                            throw InvalidHeaderException("Invalid format for width")
                        }
                        if (width <= 0)
                        {
                            throw InvalidHeaderException("Width can't be then zero ($width)")
                        }
                    } else if (height == -1) {

                        try {
                            height = nextString.toInt()
                        } catch (e: NumberFormatException) {
                            throw InvalidHeaderException("Invalid format for height")
                        }
                        if (height <= 0)
                        {
                            throw InvalidHeaderException("Height can't be then zero ($height)")
                        }
                    } else {

                        try {
                            maxShade = nextString.toInt()
                            bodyStartIndex = index + 1
                        } catch (e: NumberFormatException) {
                            throw InvalidHeaderException("Invalid format for max shade")
                        }
                        if (maxShade <= 0)
                        {
                            throw InvalidHeaderException("Max shade can't be then zero ($maxShade)")
                        }
                        if (maxShade > 255)
                        {
                            throw InvalidHeaderException("Max shade can't be then zero ($maxShade)")
                        }
                    }

                } else {
                    ++index
                }
            }

            val body = byteArray.slice(bodyStartIndex until byteArray.size).toByteArray()

            when(magicNumber?.get(1)) {
                '5' -> {
                    return P5().HandleReader(width, height, maxShade, body)
                }

                '6' -> {
                    return P6().HandleReader(width, height, maxShade, body)
                }
            }

            return null
        }

        private fun NextString(byteArray: ByteArray, index: Int): String {
            var str = ""
            var tempIndex = index
            while (byteArray[tempIndex] != 32.toByte() && byteArray[tempIndex] != 10.toByte()) {
                str += byteArray[tempIndex].toInt().toChar()
                ++tempIndex
            }

            return str
        }

        fun ParseFileToBytes(path: String, width: Int, height: Int, maxShade: Int, byteArray: ByteArray?) {
            val file = File(path)
            File(path).createNewFile()
            AppConfiguration.Image.format.write(width, height, maxShade, byteArray).let { file.writeBytes(it) }
        }

        fun ParseValueForBytes(value: Int): ByteArray {
            var newByteArray = byteArrayOf()
            var valueCopy = value
            while (valueCopy != 0) {
                newByteArray += ((valueCopy % 10) + '0'.code).toByte()
                valueCopy /= 10
            }
            newByteArray.reverse()
            return newByteArray
        }
    }
}