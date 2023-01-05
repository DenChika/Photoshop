package Parsers

import ColorSpaces.ColorSpaceInstance
import Configurations.AppConfiguration
import Configurations.ImageConfiguration
import java.io.File

class BytesParser {
    companion object {

        const val pngString = "" + 137.toChar() + 80.toChar() + 78.toChar() + 71.toChar() + 13.toChar() +
                10.toChar() + 26.toChar() + 10.toChar()

        fun ParseBytesForFile(file: File) : ImageConfiguration? {
            val byteArray = file.readBytes()

            if (byteArray.size > 7) {

                var fileString = ""
                for (i in 0..7) {
                    fileString += byteArray[i].toUByte().toInt().toChar()
                }

                if (fileString == pngString) {
                    return PNGParser.Parse(byteArray)
                }
            }

            return PNMParser.Parse(byteArray)
        }

        fun ParseFileToBytes(path: String, width: Int, height: Int, maxShade: Int, pixels : Array<ColorSpaceInstance>) {
            val file = File(path)
            File(path).createNewFile()
            AppConfiguration.Component.selected.GetFormat().write(width, height, maxShade, pixels).let { file.writeBytes(it) }
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

        fun GetByteValueFromShade(value: Float) : Byte {
            return (value * AppConfiguration.Image.maxShade).toInt().toByte()
        }
    }
}