package Interfaces

interface IColorSpace {
    fun ToRGB() : IColorSpace
    fun GetPixelValue() : IntArray
    fun GetBytes() : ByteArray
}