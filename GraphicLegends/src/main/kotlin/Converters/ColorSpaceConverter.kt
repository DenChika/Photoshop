package Converters

import ColorSpaces.ColorSpace
import Tools.ColorSpaceException

class ColorSpaceConverter {
    companion object {
        fun convert(spaceFrom : ColorSpace, spaceTo : ColorSpace, value : FloatArray) : FloatArray
        {
            return when(spaceTo) {
                ColorSpace.RGB -> spaceFrom.GetService().ToRGB(value)
                ColorSpace.CMY -> spaceFrom.GetService().ToCMY(value)
                ColorSpace.HSL -> spaceFrom.GetService().ToHSL(value)
                ColorSpace.HSV -> spaceFrom.GetService().ToHSV(value)
                ColorSpace.YCbCr601 -> spaceFrom.GetService().ToYCbCr601(value)
                ColorSpace.YCbCr709 -> spaceFrom.GetService().ToYCbCr709(value)
                ColorSpace.YCoCg -> spaceFrom.GetService().ToYCoCg(value)
                else -> throw ColorSpaceException.undefined()
            }
        }
    }
}