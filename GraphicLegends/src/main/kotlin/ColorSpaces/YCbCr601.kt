package ColorSpaces

import Interfaces.IColorSpace

class YCbCr601 : IColorSpace {

    override fun ToRGB(values: FloatArray): FloatArray {
        TODO("Not yet implemented")
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