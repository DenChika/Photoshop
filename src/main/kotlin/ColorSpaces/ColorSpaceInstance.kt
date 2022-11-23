package ColorSpaces

import Converters.ColorSpaceConverter
import Tools.ColorSpaceException

class ColorSpaceInstance(_kind: ColorSpace) {
    private var kind = _kind
    var Kind: ColorSpace
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
                    isThirdNeeded = true
                )
            )

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

    fun GetRGBPixelValue(isFirstNeeded: Boolean, isSecondNeeded: Boolean, isThirdNeeded: Boolean): FloatArray {
        val convertedValues = ColorSpaceConverter.convert(
            kind,
            ColorSpace.RGB,
            GetFloatArrayOfValues(
                isFirstNeeded,
                isSecondNeeded,
                isThirdNeeded
            )
        )

        if (convertedValues[0].isNaN() || convertedValues[1].isNaN() || convertedValues[2].isNaN()) {
            throw ColorSpaceException.shadeIsNan()
        }

        return floatArrayOf(
            convertedValues[0],
            convertedValues[1],
            convertedValues[2]
        )
    }

    fun GetFloatArrayOfValues(
        isFirstNeeded: Boolean,
        isSecondNeeded: Boolean,
        isThirdNeeded: Boolean
    ): FloatArray {
        return floatArrayOf(
            if (isFirstNeeded) firstShade else 0.0f,
            if (isSecondNeeded) secondShade else 0.0f,
            if (isThirdNeeded) thirdShade else 0.0f
        )
    }

    fun GetFloatArrayOfValues(): FloatArray {
        return floatArrayOf(
            firstShade,
            secondShade,
            thirdShade
        )
    }
    fun UpdateValues(shapeValues: FloatArray) {
        firstShade = shapeValues[0]
        secondShade = shapeValues[1]
        thirdShade = shapeValues[2]
    }
}