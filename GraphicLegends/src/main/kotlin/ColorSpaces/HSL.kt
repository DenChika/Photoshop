package ColorSpaces

import Interfaces.IColorSpace

class HSL : IColorSpace {

    override fun ToRGB(values: DoubleArray): DoubleArray {
        TODO("Not yet implemented")
    }

    override fun ToCMY(values: DoubleArray): DoubleArray {
        TODO("Not yet implemented")
    }

    override fun ToHSL(values: DoubleArray): DoubleArray {
        return values
    }

    override fun ToHSV(values: DoubleArray): DoubleArray {
        TODO("Not yet implemented")
    }

    override fun ToYCbCr601(values: DoubleArray): DoubleArray {
        TODO("Not yet implemented")
    }

    override fun ToYCbCr709(values: DoubleArray): DoubleArray {
        TODO("Not yet implemented")
    }

    override fun ToYCoCg(values: DoubleArray): DoubleArray {
        TODO("Not yet implemented")
    }
}