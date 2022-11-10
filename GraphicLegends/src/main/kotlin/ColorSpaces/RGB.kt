package ColorSpaces

import Interfaces.IColorSpace

class RGB() : IColorSpace {

    override fun ToRGB(values: FloatArray): FloatArray {
        return values
    }

    override fun ToCMY(values: FloatArray): FloatArray {
        val c = 1 - values[0]
        val m = 1 - values[1]
        val y = 1 - values[2]
        return floatArrayOf(c, m, y)
    }

    override fun ToHSL(values: FloatArray): FloatArray {
        TODO("Not yet implemented")
    }

    override fun ToHSV(values: FloatArray): FloatArray {
        TODO("Not yet implemented")
    }

    override fun ToYCbCr601(values: FloatArray): FloatArray {
        val a = 0.299F; val b = 0.587F; val c = 0.114F; val d = 1.772F; val e = 1.402F
        val y = a * values[0] + b * values[1] + c * values[2]
        val cb = (values[2] - y) / d + 0.5F
        val cr = (values[0] - y) / e + 0.5F
        return floatArrayOf(y, cb, cr)
    }

    override fun ToYCbCr709(values: FloatArray): FloatArray {
        val a = 0.2126F; val b = 0.7152F; val c = 0.0722F; val d = 1.8556F; val e = 1.5748F
        val y = a * values[0] + b * values[1] + c * values[2]
        val cb = (values[2] - y) / d + 0.5F
        val cr = (values[0] - y) / e + 0.5F
        return floatArrayOf(y, cb, cr)
    }

    override fun ToYCoCg(values: FloatArray): FloatArray {
        TODO("Not yet implemented")
    }
}