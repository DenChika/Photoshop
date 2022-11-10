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