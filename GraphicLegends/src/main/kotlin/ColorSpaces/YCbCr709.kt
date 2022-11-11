package ColorSpaces

import Interfaces.IColorSpace

class YCbCr709 : IColorSpace {

    override fun ToRGB(values: FloatArray): FloatArray {
        val a = 0.2126f
        val b = 0.7152f

        val c = 1 - a - b
        val d = 2 * (a + b)
        val e = 2 * (1 - a)

        val red = values[0] + e * values[2]
        val green = values[0] - (a * e / b) * values[2] - (c * d / b) * values[1]
        val blue = values[0] + d * values[1]

        return floatArrayOf(red, green, blue)
    }

    override fun ToCMY(values: FloatArray): FloatArray {
        val rgbValues = ToRGB(values)
        return ColorSpace.RGB.GetService().ToCMY(rgbValues)
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
        return values
    }

    override fun ToYCoCg(values: FloatArray): FloatArray {
        val rgbValues = ToRGB(values)
        return ColorSpace.RGB.GetService().ToYCoCg(rgbValues)
    }
}