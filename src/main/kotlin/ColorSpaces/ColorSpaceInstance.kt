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

            firstShade = minOf(maxOf(convertedValues[0], 0f), 1f)
            secondShade = minOf(maxOf(convertedValues[1], 0f), 1f)
            thirdShade = minOf(maxOf(convertedValues[2], 0f), 1f)
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
            minOf(maxOf(convertedValues[0], 0f), 1f),
            minOf(maxOf(convertedValues[1], 0f), 1f),
            minOf(maxOf(convertedValues[2], 0f), 1f)
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
        firstShade = minOf(maxOf(shapeValues[0], 0f), 1f)
        secondShade = minOf(maxOf(shapeValues[1], 0f), 1f)
        thirdShade = minOf(maxOf(shapeValues[2], 0f), 1f)
    }

    fun ApplyError(errors: FloatArray) {
        firstShade += errors[0]
        secondShade += errors[1]
        thirdShade += errors[2]
    }
}