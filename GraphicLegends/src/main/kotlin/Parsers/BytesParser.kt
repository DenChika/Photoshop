package Parsers

import Configuration.MutableConfigurationsState
import Formats.Modes
import Formats.P5
import Formats.P6
import androidx.compose.ui.graphics.ImageBitmap
import java.io.File
import kotlin.math.pow

class BytesParser {
    companion object {

        val fileTypeSet = hashSetOf<Char>('1', '2', '3', '4', '5', '6', '7')

        fun ParseBytesForFile(file: File): ImageBitmap? { //TODO check return type
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
        fun ParseFileInBytes(path: String, width: Int, height: Int, maxShade: Int, byteArray: ByteArray?) {
            val file = File(path)
            File(path).createNewFile()
            when (MutableConfigurationsState.mode) {
                Modes.P5 -> {
                    P5().HandleWriter(width, height, maxShade, byteArray)?.let { file.writeBytes(it)
                    }
                }
                Modes.P6 -> {
                P6().HandleWriter(width, height, maxShade, byteArray)?.let { file.writeBytes(it) }
                }
            }
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

            private fun ParseBytesForValue(byteArray: ByteArray): Int {
                byteArray.reverse()
                var value = 0
                for (i in byteArray.indices) {
                    value += (byteArray[i].toInt() - '0'.code) * 10.0.pow(i).toInt()
                }
                return value
            }
    }
}