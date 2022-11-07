package ColorSpaces

import Converters.ColorSpaceConverter

class ColorSpaceInstance(_kind : ColorSpace) {
    private var kind = _kind
    var Kind : ColorSpace
        get() {
            return kind
        }
        set(value) {
            val convertedValues = ColorSpaceConverter.convert(kind, value, GetDoubleArrayOfValues())
            firstShade = convertedValues[0].toInt()
            secondShade = convertedValues[1].toInt()
            thirdShade = convertedValues[2].toInt()
            kind = value
        }
    var firstShade: Int = 0
    var secondShade: Int = 0
    var thirdShade: Int = 0

    fun GetRGBPixelValue(): IntArray {
        val convertedValues = ColorSpaceConverter.convert(kind, ColorSpace.RGB, GetDoubleArrayOfValues())
        return intArrayOf(convertedValues[0].toInt(), convertedValues[1].toInt(), convertedValues[2].toInt())
    }

    fun GetBytes(): ByteArray {
        return byteArrayOf(firstShade.toByte(), secondShade.toByte(), thirdShade.toByte())
    }

    private fun GetDoubleArrayOfValues() : DoubleArray
    {
        return doubleArrayOf(firstShade.toDouble(), secondShade.toDouble(), thirdShade.toDouble())
    }
}