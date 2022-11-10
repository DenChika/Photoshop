package ColorSpaces

import Interfaces.IColorSpace

class YCbCr601 : IColorSpace {

    override fun ToRGB(values: FloatArray): FloatArray {
        val a = 0.299f
        val b = 0.587f

        val c = 1 - a - b
        val d = 2 * (a + b)
        val e = 2 * (1 - a)

        val red = values[0] + e * values[2]
        val green = values[0] - (a * e / b) * values[2] - (c * d / b) * values[1]
        val blue = values[0] + d * values[1]

        return floatArrayOf(red, green, blue)
    }

    override fun ToCMY(values: FloatArray): FloatArray {
        TODO("Not yet implemented")
    }

    override fun ToHSL(values: FloatArray): FloatArray {
        TODO("Not yet implemented")
    }

    override fun ToHSV(values: FloatArray): FloatArray {
        TODO("Not yet implemented")
    }

    override fun ToYCbCr601(values: FloatArray): FloatArray {
        return values
    }

    override fun ToYCbCr709(values: FloatArray): FloatArray {
        TODO("Not yet implemented")
    }

    override fun ToYCoCg(values: FloatArray): FloatArray {
        TODO("Not yet implemented")
    }
}