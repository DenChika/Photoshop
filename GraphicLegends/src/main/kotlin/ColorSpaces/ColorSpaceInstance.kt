package ColorSpaces

import Configurations.AppConfiguration
import Converters.ColorSpaceConverter
import Tools.ColorSpaceException

class ColorSpaceInstance(_kind : ColorSpace) {
    private var kind = _kind
    var Kind : ColorSpace
        get() {
            return kind

        }
        set(value) {
            val convertedValues = ColorSpaceConverter.convert(
                kind,
                value,
                GetFloatArrayOfValues(
                    isFirstNeeded = true,
                    isSecondNeeded = true,
                    isThirdNeeded = true))

            if (convertedValues[0].isNaN() || convertedValues[1].isNaN() || convertedValues[2].isNaN()) {
                throw ColorSpaceException.shadeIsNan()
            }

            firstShade = convertedValues[0]
            secondShade = convertedValues[1]
            thirdShade = convertedValues[2]
            kind = value
        }
    var firstShade: Float = 0.0F
    var secondShade: Float = 0.0F
    var thirdShade: Float = 0.0F

    fun GetRGBPixelValue(isFirstNeeded : Boolean, isSecondNeeded : Boolean, isThirdNeeded : Boolean):  FloatArray {
        val convertedValues = ColorSpaceConverter.convert(
            kind,
            ColorSpace.RGB,
            GetFloatArrayOfValues(
                isFirstNeeded,
                isSecondNeeded,
                isThirdNeeded))

        if (convertedValues[0].isNaN() || convertedValues[1].isNaN() || convertedValues[2].isNaN()) {
            throw ColorSpaceException.shadeIsNan()
        }

        return floatArrayOf(
            convertedValues[0],
            convertedValues[1],
            convertedValues[2])
    }
    fun GetBytes(): ByteArray {
        return byteArrayOf(
            GetFirstByte(),
            GetSecondByte(),
            GetThirdByte())
    }
    fun GetFirstByte() : Byte{
        return (firstShade * AppConfiguration.Image.maxShade).toInt().toByte()
    }
    fun GetSecondByte() : Byte{
        return (secondShade * AppConfiguration.Image.maxShade).toInt().toByte()
    }
    fun GetThirdByte() : Byte{
        return (thirdShade * AppConfiguration.Image.maxShade).toInt().toByte()
    }

    private fun GetFloatArrayOfValues(isFirstNeeded : Boolean, isSecondNeeded : Boolean, isThirdNeeded : Boolean) : FloatArray
    {
        return floatArrayOf(
            if (isFirstNeeded) firstShade else 0.0f,
            if (isSecondNeeded) secondShade else 0.0f,
            if (isThirdNeeded) thirdShade else 0.0f)
    }
}