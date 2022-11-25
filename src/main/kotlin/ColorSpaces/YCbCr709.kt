package ColorSpaces

import Interfaces.IColorSpace

class YCbCr709 : IColorSpace {

    override fun ToRGB(values: FloatArray): FloatArray {
        val a = 0.2126f
        val b = 0.7152f

        val c = 1 - a - b
        val d = 2 * (a + b)
        val e = 2 * (1 - a)

        var red = values[0] + e * (values[2] - 0.5f)
        var green = values[0] - (a * e / b) * (values[2] - 0.5f) - (c * d / b) * (values[1] - 0.5f)
        var blue = values[0] + d * (values[1] - 0.5f)

        red = minOf(maxOf(red, 0f), 1f)
        green = minOf(maxOf(green, 0f), 1f)
        blue = minOf(maxOf(blue, 0f), 1f)

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