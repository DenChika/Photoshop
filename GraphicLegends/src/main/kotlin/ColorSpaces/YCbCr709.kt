package ColorSpaces

import Interfaces.IColorSpace

class YCbCr709 : IColorSpace {

    override fun ToRGB(values: FloatArray): FloatArray {
        val a = 0.2126F; val b = 0.7152F; val c = 0.0722F; val d = 1.8556F; val e = 1.5748F
        val R = values[0] + e * (values[2] - 0.5F)
        val G = values[0] - (a * e / b) * (values[2] - 0.5F) - (c * d / b) * (values[1] - 0.5F)
        val B = values[0] + d * (values[1] - 0.5F)
        return floatArrayOf(R, G, B)
    }

    override fun ToCMY(values: FloatArray): FloatArray {
        val rgbValues = ToRGB(values)
        return ColorSpace.RGB.GetService().ToCMY(rgbValues)
    }

    override fun ToHSL(values: FloatArray): FloatArray {
        TODO("Not yet implemented")
    }

    override fun ToHSV(values: FloatArray): FloatArray {
        TODO("Not yet implemented")
    }

    override fun ToYCbCr601(values: FloatArray): FloatArray {
        val rgbValues = ToRGB(values)
        return ColorSpace.RGB.GetService().ToYCbCr601(rgbValues)
    }

    override fun ToYCbCr709(values: FloatArray): FloatArray {
        return values
    }

    override fun ToYCoCg(values: FloatArray): FloatArray {
        TODO("Not yet implemented")
    }
}