package Parsers

import Configurations.ImageConfiguration
import Formats.P5
import Formats.P6
import Tools.FormatException
import Tools.InvalidHeaderException

class PNMParser {
    companion object {

        private val PNMFileTypeSet = hashSetOf<Char>('1', '2', '3', '4', '5', '6', '7')

        fun Parse(byteArray: ByteArray): ImageConfiguration? {
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

                        if (magicNumber.length != 2 || magicNumber[0] != 'P' || !PNMFileTypeSet.contains(magicNumber[1])) {
                            throw FormatException.undefined()
                        }

                    } else if (width == -1) {

                        try {
                            width = nextString.toInt()
                        } catch (e: NumberFormatException) {
                            throw InvalidHeaderException.invalidWidthFormat()
                        }
                        if (width <= 0)
                        {
                            throw InvalidHeaderException.widthInvalidValue(width)
                        }
                    } else if (height == -1) {

                        try {
                            height = nextString.toInt()
                        } catch (e: NumberFormatException) {
                            throw InvalidHeaderException.invalidHeightFormat()
                        }
                        if (height <= 0)
                        {
                            throw InvalidHeaderException.heightInvalidValue(height)
                        }
                    } else {

                        try {
                            maxShade = nextString.toInt()
                            bodyStartIndex = index + 1
                        } catch (e: NumberFormatException) {
                            throw InvalidHeaderException.invalidMaxShadeFormat()
                        }
                        if (maxShade <= 0)
                        {
                            throw InvalidHeaderException.maxShadeInvalidValue(maxShade)
                        }
                        if (maxShade > 255)
                        {
                            throw InvalidHeaderException.maxShadeInvalidValue(maxShade)
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
    }
}