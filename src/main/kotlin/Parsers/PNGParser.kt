package Parsers

import ColorSpaces.ColorSpaceInstance
import Configurations.AppConfiguration
import Configurations.ImageConfiguration
import Formats.Format
import Tools.HeaderDiscrepancyException
import Tools.PNGException
import java.util.zip.Inflater
import kotlin.math.pow

class PNGParser {
    companion object {

        private const val pngStringLength = 8
        private const val chunkLengthSize = 4
        private const val chunkNameSize = 4
        private const val widthHeightSize = 4
        private const val headerLength = 13
        private const val paletteBytesPerColor = 3
        private val power256 = intArrayOf(256 * 256 * 256, 256 * 256, 256, 1)

        fun Parse(byteArray: ByteArray): ImageConfiguration {
            var index = pngStringLength
            var headerRead = false
            var paletteRead = false

            var pngHeader: PNGHeader? = null
            var palette: ArrayList<FloatArray>
            val dataIndices: ArrayList<Int> = ArrayList()

            while (true) {

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
                            byteArray[index + 1].toUByte().toInt().toChar() == 'D' &&
                            byteArray[index + 2].toUByte().toInt().toChar() == 'A' &&
                            byteArray[index + 3].toUByte().toInt().toChar() == 'T'
                        ) {
                            if (pngHeader != null && (pngHeader.getColorType() == 3 && !paletteRead)) {
                                throw PNGException.wrongFormat("PNG with color type 3 must contain PLTE chunk.")
                            }

                            dataIndices.add(index)
                        } else if (
                            byteArray[index + 1].toUByte().toInt().toChar() == 'E' &&
                            byteArray[index + 2].toUByte().toInt().toChar() == 'N' &&
                            byteArray[index + 3].toUByte().toInt().toChar() == 'D'
                        ) {
                            val pixels = readData(byteArray, dataIndices, pngHeader)

                            if (pngHeader != null) {
                                return ImageConfiguration(Format.PNG, pngHeader.getWidth(),pngHeader.getHeight(), 255, pixels)
                            } else {
                                throw PNGException.wrongFormat("IHDR is missing.")
                            }
                        }
                    } else if (byteArray[index].toUByte().toInt().toChar() == 'P') {
                        if (byteArray[index + 1].toUByte().toInt().toChar() == 'L' &&
                            byteArray[index + 2].toUByte().toInt().toChar() == 'T' &&
                            byteArray[index + 3].toUByte().toInt().toChar() == 'E'
                        ) {
                            if (paletteRead) {
                                throw PNGException.wrongFormat("There must not be more than one PLTE chunk.")
                            }

                            palette = readPalette(byteArray, index, pngHeader)
                            paletteRead = true
                        }
                    }
                } catch (e: IndexOutOfBoundsException) {
                    throw PNGException.wrongFormat("There is no IEND.")
                } catch (e: PNGException) {
                    throw e
                } catch (e: Exception) {
                    throw PNGException.wrongFormat("Unknown error. ${e.printStackTrace()}")
                }

                ++index
            }
        }

        fun readHeader(byteArray: ByteArray, currentIndex: Int): PNGHeader {
            var index = currentIndex
            val widthBytes = byteArray.slice(index until index + widthHeightSize)
            var width = 0
            for (i in 0 until widthHeightSize) {
                width += widthBytes[i].toUByte().toInt() * power256[i]
            }

            index += 4

            val heightBytes = byteArray.slice(index until index + widthHeightSize)
            var height = 0
            for (i in 0 until widthHeightSize) {
                height += heightBytes[i].toUByte().toInt() * power256[i]
            }

            index += 4

            val bitDepth = byteArray[index++].toUByte().toInt()

            val colorType = byteArray[index++].toUByte().toInt()

            index += 2

            if (byteArray[index].toUByte().toInt() != 0) {
                throw PNGException.unsupported("interlaced data")
            }

            if (colorType !in intArrayOf(0, 2, 3)) {
                throw PNGException.unsupported("color type $colorType")
            }

            if (width <= 0 || height <= 0) {
                throw PNGException.wrongFormat("Width and height have to be positive.")
            }

            if (bitDepth != 8) {
                throw PNGException.unsupported("bit depth $bitDepth")
            }

            return PNGHeader(width, height, bitDepth, colorType)
        }

        fun readPalette(byteArray: ByteArray, currentIndex: Int, pngHeader: PNGHeader?): ArrayList<FloatArray> {
            if (pngHeader == null) {
                throw PNGException.wrongFormat("IHDR is missing.")
            }

            if (pngHeader.getColorType() != 2 && pngHeader.getColorType() != 3) {
                throw PNGException.wrongFormat("Palette chunk can appear only if color type is 2 or 3.")
            }

            var index = currentIndex
            val paletteLengthBytes = byteArray.slice(index - chunkLengthSize until index)
            var paletteLength = 0
            for (i in 0 until chunkLengthSize) {
                paletteLength += paletteLengthBytes[i].toUByte().toInt() * power256[i]
            }

            if (paletteLength % 3 != 0) {
                throw PNGException.wrongFormat("PLTE length must be divisible by 3.")
            }
            index += chunkNameSize

            val paletteEntries = paletteLength / paletteBytesPerColor

            val palette = ArrayList<FloatArray>(paletteEntries)
            val colorDepth = 2.0.pow(pngHeader.getBitDepth().toDouble()).toFloat()
            for (i in 0 until paletteEntries) {
                val color = FloatArray(paletteBytesPerColor)

                for (j in 0 until paletteBytesPerColor) {
                    color[j] = (byteArray[index + i * paletteBytesPerColor + j].toUByte().toInt() / colorDepth).coerceIn(0.0f, 1.0f)
                }

                palette.add(color)
            }

            return palette
        }

        fun readData(byteArray: ByteArray, dataIndices: ArrayList<Int>, pngHeader: PNGHeader?): Array<ColorSpaceInstance> {
            if (pngHeader == null) {
                throw PNGException.wrongFormat("IHDR is missing.")
            }

            val width = pngHeader.getWidth()
            val height = pngHeader.getHeight()

            var data = ByteArray(0)
            val inflater = Inflater(false)
            var uncompressedDataTotal = ByteArray(0)

            for (i in 0 until dataIndices.size) {
                val index = dataIndices[i]
                val dataLengthBytes = byteArray.slice(index - chunkLengthSize until index)
                var dataLength = 0
                for (j in 0 until chunkLengthSize) {
                    dataLength += dataLengthBytes[j].toUByte().toInt() * power256[j]
                }

                data += byteArray.slice(index + chunkNameSize until index + chunkNameSize + dataLength).toByteArray()
            }

            inflater.setInput(data)
            val uncompressedData = ByteArray(width * height * 3 + height)
            val ans = inflater.inflate(uncompressedData)
            uncompressedDataTotal += uncompressedData

            println("Uncompressed: $ans")
            println("Bytes lost: " + inflater.remaining)
            println("Size: " + uncompressedDataTotal.size)

            val pixels = Array(width * height) {
                AppConfiguration.Space.selected.GetDefault()
            }

            val colorsByPixel = if (pngHeader.getColorType() == 0) 1 else 3
            val maxShade = 255

            for (posY in 0 until height) {
                for (posX in 0 until width) {
                    val shadeFirst = uncompressedDataTotal[(1 + posY) + posY * width * colorsByPixel + posX * colorsByPixel].toUByte().toFloat()

                    val shadeSecond = uncompressedDataTotal[(1 + posY) + posY * width * colorsByPixel + posX * colorsByPixel + 1].toUByte().toFloat()

                    val shadeThird = uncompressedDataTotal[(1 + posY) + posY * width * colorsByPixel + posX * colorsByPixel + 2].toUByte().toFloat()

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

            return pixels
        }
    }
}