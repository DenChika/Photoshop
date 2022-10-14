package Parsers

import Formats.P5
import Formats.P6
import androidx.compose.ui.graphics.ImageBitmap
import java.io.File

class BytesParser {
    companion object {

//        private fun ParseValue(byteArray: ByteArray) : Int {
//            byteArray.reverse()
//            var height = 0
//            for (i in byteArray.indices) {
//                height += (byteArray[i].toInt() - '0'.code) * 10.0.pow(i).toInt()
//            }
//            return height
//        }

        val fileTypeSet = hashSetOf<Char>('1', '2', '3', '4', '5', '6', '7')

        fun ParseFile(file: File): ImageBitmap? { //TODO check return type
            val byteArray = file.readBytes()

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

                    } else if (maxShade == -1) {

                        try {
                            maxShade = nextString.toInt()
                        } catch (e: NumberFormatException) {
                            //TODO exception
                        }
                    }

                } else {
                    ++index

                    if (maxShade != -1) {
                        bodyStartIndex = index
                        break
                    }
                }
            }

            val body = byteArray.slice(bodyStartIndex until byteArray.size).toByteArray()

            when(magicNumber?.get(1)) {
                '5' -> {
                    return P5().Handle(width, height, maxShade, body)
                }

                '6' -> {
                    return P6().Handle(width, height, maxShade, body)
                }
            }

            return ImageBitmap(width, height)
        }

        private fun NextString(byteArray: ByteArray, index: Int): String {
            var str = ""
            while (byteArray[index] != 32.toByte() && byteArray[index] != 10.toByte()) {
                str += byteArray[index].toInt().toChar()
            }

            return str
        }

    }
}