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
        TODO("Not yet implemented")
    }

    override fun ToYCbCr709(values: FloatArray): FloatArray {
        TODO("Not yet implemented")
    }

    override fun ToYCoCg(values: FloatArray): FloatArray {
        TODO("Not yet implemented")
    }
}