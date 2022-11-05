package Interfaces

interface ColorSpace {
    fun ToRGB() : ColorSpace
    fun GetPixelValue() : IntArray
    fun GetBytes() : ByteArray
}