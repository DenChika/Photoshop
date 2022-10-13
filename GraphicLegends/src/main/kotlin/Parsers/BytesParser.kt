package Parsers

import Formats.P5
import Formats.P6
import androidx.compose.ui.graphics.ImageBitmap
import java.io.File
import kotlin.math.pow

class BytesParser {
    companion object {
        fun ParseFile(file: File) : ImageBitmap? {
            val byteArray = file.readBytes()
            if (byteArray[0].toInt().toChar() != 'P') {
                println("not pnm format")
                return null
            }

            val arrayWithoutFormat = byteArray.slice(3 until byteArray.size).toByteArray()

            val spaceIndex = arrayWithoutFormat.indexOf(32.toByte())
            var enterIndex = arrayWithoutFormat.indexOf(10.toByte())
            val width = ParseValue(arrayWithoutFormat.slice(0 until spaceIndex).toByteArray())
            val height = ParseValue(arrayWithoutFormat.slice(spaceIndex + 1 until enterIndex).toByteArray())
            val bodyWithShadeValue = arrayWithoutFormat.slice(enterIndex + 1 until arrayWithoutFormat.size).toByteArray()

            enterIndex = bodyWithShadeValue.indexOf(10.toByte())
            val maxShadeValue = ParseValue(bodyWithShadeValue.slice(0 until enterIndex).toByteArray())

            val body = bodyWithShadeValue.slice(enterIndex + 1 until bodyWithShadeValue.size).toByteArray()

            when(byteArray[1].toInt().toChar()) {
                '5' -> {
                    val handler = P5()
                    return handler.Handle(width, height, body)
                }
                '6' -> {
                    val handler = P6()
                    return handler.Handle(width, height, body)
                }
            }
            return null
        }

        private fun ParseValue(byteArray: ByteArray) : Int {
            byteArray.reverse()
            var height = 0
            for (i in byteArray.indices) {
                height += (byteArray[i].toInt() - '0'.code) * 10.0.pow(i).toInt()
            }
            return height
        }
    }

}