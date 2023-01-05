package Parsers

import Configurations.ImageConfiguration
import Formats.PNG
import Tools.PNGException

class PNGParser {
    companion object {

        private const val pngStringLength = 8
        private const val chunkNameSize = 4
        private const val widthHeightSize = 4
        private const val headerLength = 13
        private val power256 = intArrayOf(256 * 256 * 256, 256 * 256, 256, 1)

        fun Parse(byteArray: ByteArray): ImageConfiguration? {
            var index = pngStringLength
            var foundEnd = false
            var headerRead = false
            var paletteRead = false

            var pngHeader: PNGHeader? = null

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
                            index += headerLength
                            headerRead = true

                        } else if (
                            byteArray[index + 1].toUByte().toInt().toChar() == 'E' &&
                            byteArray[index + 2].toUByte().toInt().toChar() == 'N' &&
                            byteArray[index + 3].toUByte().toInt().toChar() == 'D'
                        ) {
                            foundEnd = true

                        } else if (
                            byteArray[index + 1].toUByte().toInt().toChar() == 'D' &&
                            byteArray[index + 2].toUByte().toInt().toChar() == 'A' &&
                            byteArray[index + 3].toUByte().toInt().toChar() == 'T'
                        ) {
                            readData(byteArray, pngHeader)

                        }
                    } else if (byteArray[index].toUByte().toInt().toChar() == 'P') {
                        if (byteArray[index + 1].toUByte().toInt().toChar() == 'L' &&
                            byteArray[index + 2].toUByte().toInt().toChar() == 'T' &&
                            byteArray[index + 3].toUByte().toInt().toChar() == 'E'
                        ) {
                            if (paletteRead) {
                                throw PNGException.wrongFormat("There must not be more than one PLTE chunk.")
                            }

                            readPalette(byteArray, pngHeader)
                            paletteRead = true
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

            if (colorType !in intArrayOf(0, 2, 3)) {
                throw PNGException.wrongFormat("Unsupported color type.")
            }

            if (width <= 0 || height <= 0) {
                throw PNGException.wrongFormat("Width and height have to be positive.")
            }

            if (bitDepth !in intArrayOf(1, 2, 4, 8, 16) ||
                colorType == 2 && bitDepth !in intArrayOf(8, 16) ||
                colorType == 3 && bitDepth !in intArrayOf(1, 2, 4, 8)
            ) {
                throw PNGException.wrongFormat("Invalid bit depth value: $bitDepth")
            }

            return PNGHeader(width, height, bitDepth, colorType)
        }

        fun readPalette(byteArray: ByteArray, pngHeader: PNGHeader?) {
            if (pngHeader != null && (pngHeader.getColorType() != 2 || pngHeader.getColorType() != 3)) {
                throw PNGException.wrongFormat("Palette chunk can appear only if color type is 2 or 3.")
            }
        }

        fun readData(byteArray: ByteArray, pngHeader: PNGHeader?) {

        }
    }
}