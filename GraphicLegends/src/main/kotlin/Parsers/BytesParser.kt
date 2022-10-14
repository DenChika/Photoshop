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
        fun ParseBytesForFile(file: File) : ImageBitmap? {
            val byteArray = file.readBytes()
            if (byteArray[0].toInt().toChar() != 'P') {
                println("not pnm format")
                return null
            }

            val arrayWithoutFormat = byteArray.slice(3 until byteArray.size).toByteArray()

            val spaceIndex = arrayWithoutFormat.indexOf(32.toByte())
            var enterIndex = arrayWithoutFormat.indexOf(10.toByte())
            val width = ParseBytesForValue(arrayWithoutFormat.slice(0 until spaceIndex).toByteArray())
            val height = ParseBytesForValue(arrayWithoutFormat.slice(spaceIndex + 1 until enterIndex).toByteArray())
            val bodyWithShadeValue = arrayWithoutFormat.slice(enterIndex + 1 until arrayWithoutFormat.size).toByteArray()

            enterIndex = bodyWithShadeValue.indexOf(10.toByte())
            val maxShadeValue = ParseBytesForValue(bodyWithShadeValue.slice(0 until enterIndex).toByteArray())

            val body = bodyWithShadeValue.slice(enterIndex + 1 until bodyWithShadeValue.size).toByteArray()

            when(byteArray[1].toInt().toChar()) {
                '5' -> {
                    val handler = P5()
                    return handler.HandleReader(width, height, maxShadeValue, body)
                }
                '6' -> {
                    val handler = P6()
                    return handler.HandleReader(width, height, maxShadeValue, body)
                }
            }
            return null
        }

        fun ParseFileInBytes(path: String, width: Int, height: Int, maxShade: Int, byteArray: ByteArray?) {
            val file = File(path)
            File(path).createNewFile()
            when(MutableConfigurationsState.mode) {
                Modes.P5 -> {
                    P5().HandleWriter(width, height, maxShade, byteArray)?.let { file.writeBytes(it) }
                }
                Modes.P6 -> {
                    P6().HandleWriter(width, height, maxShade, byteArray)?.let { file.writeBytes(it) }
                }
            }
        }
        fun ParseValueForBytes(value: Int) : ByteArray {
            var newByteArray = byteArrayOf()
            var valueCopy = value
            while(valueCopy != 0) {
                newByteArray += ((valueCopy % 10) + '0'.code).toByte()
                valueCopy /= 10
            }
            newByteArray.reverse()
            return newByteArray
        }
        private fun ParseBytesForValue(byteArray: ByteArray) : Int {
            byteArray.reverse()
            var value = 0
            for (i in byteArray.indices) {
                value += (byteArray[i].toInt() - '0'.code) * 10.0.pow(i).toInt()
            }
            return value
        }
    }

}