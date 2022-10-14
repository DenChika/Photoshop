package Parsers

import Formats.P5
import Formats.P6
import androidx.compose.ui.graphics.ImageBitmap
import java.io.File

class BytesParser {
    companion object {

        val fileTypeSet = hashSetOf<Char>('1', '2', '3', '4', '5', '6', '7')

        fun ParseFile(file: File): ImageBitmap? { //TODO check return type
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
                            //TODO exception
                        }

                    } else if (width == -1) {

                        try {
                            width = nextString.toInt()
                        } catch (e: NumberFormatException) {
                            //TODO exception
                        }

                    } else if (height == -1) {

                        try {
                            height = nextString.toInt()
                        } catch (e: NumberFormatException) {
                            //TODO exception
                        }

                    } else {

                        try {
                            maxShade = nextString.toInt()
                            bodyStartIndex = index + 1
                        } catch (e: NumberFormatException) {
                            //TODO exception
                        }
                    }

                } else {
                    ++index
                }
            }

            println("magicNumber: $magicNumber")
            println("width: $width")
            println("height: $height")
            println("maxShade: $maxShade")

            val body = byteArray.slice(bodyStartIndex until byteArray.size).toByteArray()

            when(magicNumber?.get(1)) {
                '5' -> {
                    return P5().Handle(width, height, maxShade.toUInt(), body)
                }

                '6' -> {
                    return P6().Handle(width, height, maxShade.toUInt(), body)
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