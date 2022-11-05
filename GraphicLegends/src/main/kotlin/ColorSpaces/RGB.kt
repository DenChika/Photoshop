package ColorSpaces

import Interfaces.IColorSpace

class RGB(r : Int, g : Int, b : Int) : IColorSpace {
    private val R = r
    private val G = g
    private val B = b
    override fun ToRGB(): IColorSpace {
        return this
    }

    override fun GetPixelValue(): IntArray {
        return intArrayOf(R, G, B)
    }

    override fun GetBytes(): ByteArray {
        return byteArrayOf(R.toByte(), G.toByte(), B.toByte())
    }
}