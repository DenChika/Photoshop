package Parsers

import Configurations.ImageConfiguration
import Tools.PNGException

class PNGParser {
    companion object {

        const val pngStringLength = 8
        const val chunkNameSize = 4
        const val widthHeightSize = 4
        const val headerLength = 13
        private val power256 = intArrayOf(256 * 256 * 256, 256 * 256, 256, 1)

        fun Parse(byteArray: ByteArray): ImageConfiguration? {
            var index = pngStringLength
            var foundEnd = false

            var headerRead = false

            var pngHeader : PNGHeader

            while (!foundEnd) {

                try {
                    if (byteArray[index].toUByte().toInt().toChar() == 'I') {
                        if (byteArray[index + 1].toUByte().toInt().toChar() == 'H' &&
                            byteArray[index + 2].toUByte().toInt().toChar() == 'D' &&
                            byteArray[index + 3].toUByte().toInt().toChar() == 'R'
                        ) {
                            if (headerRead) {
                                throw PNGException("There cannot be 2 header chunks.")
                            }

                            index += chunkNameSize

                            pngHeader = readHeader(byteArray, index)

                            if (pngHeader.getColorType() !in intArrayOf(0, 2, 3)) {
                                throw PNGException.wrongFormat("Unsupported color type.")
                            }

                            index += headerLength

                            headerRead = true
                            foundEnd = true
                        }
                    }
                } catch (e: IndexOutOfBoundsException) {
                    throw PNGException.wrongFormat()
                }

                ++index
            }

            return ImageConfiguration()
        }

        fun readHeader(byteArray: ByteArray, currentIndex: Int): PNGHeader {
            var index = currentIndex
            val widthBytes = byteArray.slice(index until index + widthHeightSize)
            var width = 0
            for (i in 0..3) {
                width += widthBytes[i].toUByte().toInt() * power256[i]
            }

            index += 4

            val heightBytes = byteArray.slice(index until index + widthHeightSize)
            var height = 0
            for (i in 0..3) {
                height += heightBytes[i].toUByte().toInt() * power256[i]
            }

            index += 4

            val bitDepth = byteArray[index++].toUByte().toInt()

            val colorType = byteArray[index++].toUByte().toInt()

            index += 3

            return PNGHeader(width, height, bitDepth, colorType)
        }
    }
}