package Converters

import ColorSpaces.ColorSpace
import Tools.ColorSpaceException

class ColorSpaceConverter {
    companion object {
        fun convert(start : ColorSpace, end : ColorSpace, value : FloatArray) : FloatArray
        {
            return when(end) {
                ColorSpace.CMY -> start.GetService().ToCMY(value)
                ColorSpace.YCoCg -> start.GetService().ToYCoCg(value)
                ColorSpace.YCbCr601 -> start.GetService().ToYCbCr601(value)
                ColorSpace.YCbCr709 -> start.GetService().ToYCbCr709(value)
                ColorSpace.HSV -> start.GetService().ToHSV(value)
                ColorSpace.HSL -> start.GetService().ToRGB(value)
                ColorSpace.RGB -> start.GetService().ToRGB(value)
                else -> throw ColorSpaceException.undefined()
            }
        }
    }

}