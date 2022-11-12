package ColorSpaces

import Interfaces.IColorSpace

class CMY() : IColorSpace {

    override fun ToRGB(values: FloatArray): FloatArray {
        val r = 1 - values[0]
        val g = 1 - values[1]
        val b = 1 - values[2]
        return floatArrayOf(r, g, b)
    }

    override fun ToCMY(values: FloatArray): FloatArray {
        return values
    }

    override fun ToHSL(values: FloatArray): FloatArray {
        val rgbValues = ToRGB(values)
        return ColorSpace.RGB.GetService().ToHSL(rgbValues)
    }

    override fun ToHSV(values: FloatArray): FloatArray {
        val rgbValues = ToRGB(values)
        return ColorSpace.RGB.GetService().ToHSV(rgbValues)
    }

    override fun ToYCbCr601(values: FloatArray): FloatArray {
        val rgbValues = ToRGB(values)
        return ColorSpace.RGB.GetService().ToYCbCr601(rgbValues)
    }

    override fun ToYCbCr709(values: FloatArray): FloatArray {
        val rgbValues = ToRGB(values)
        return ColorSpace.RGB.GetService().ToYCbCr709(rgbValues)
    }

    override fun ToYCoCg(values: FloatArray): FloatArray {
        val rgbValues = ToRGB(values)
        return ColorSpace.RGB.GetService().ToYCoCg(rgbValues)
    }
}