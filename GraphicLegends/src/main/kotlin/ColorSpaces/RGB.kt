package ColorSpaces

import Interfaces.ColorSpace

class RGB(r : Int, g : Int, b : Int) : ColorSpace {
    private val R = r
    private val G = g
    private val B = b
    override fun ToRGB(): ColorSpace {
        return this
    }

    override fun GetPixelValue(): IntArray {
        return intArrayOf(R, G, B)
    }

    override fun GetBytes(): ByteArray {
        return byteArrayOf(R.toByte(), G.toByte(), B.toByte())
    }
}