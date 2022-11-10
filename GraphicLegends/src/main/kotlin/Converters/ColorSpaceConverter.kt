package Converters

import ColorSpaces.ColorSpace
import Tools.ColorSpaceException

class ColorSpaceConverter {
    companion object {
        fun convert(spaceFrom : ColorSpace, spaceTo : ColorSpace, value : FloatArray) : FloatArray
        {
            return when(spaceTo) {
                ColorSpace.CMY -> spaceFrom.GetService().ToCMY(value)
                ColorSpace.YCoCg -> spaceFrom.GetService().ToYCoCg(value)
                ColorSpace.YCbCr601 -> spaceFrom.GetService().ToYCbCr601(value)
                ColorSpace.YCbCr709 -> spaceFrom.GetService().ToYCbCr709(value)
                ColorSpace.HSV -> spaceFrom.GetService().ToHSV(value)
                ColorSpace.HSL -> spaceFrom.GetService().ToRGB(value)
                ColorSpace.RGB -> spaceFrom.GetService().ToRGB(value)
                else -> throw ColorSpaceException.undefined()
            }
        }
    }

}