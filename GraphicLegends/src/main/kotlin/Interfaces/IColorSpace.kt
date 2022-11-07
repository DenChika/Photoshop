package Interfaces

interface IColorSpace {
    fun ToRGB(values : DoubleArray) : DoubleArray
    fun ToCMY(values : DoubleArray) : DoubleArray
    fun ToHSL(values : DoubleArray) : DoubleArray
    fun ToHSV(values: DoubleArray) : DoubleArray
    fun ToYCbCr601(values: DoubleArray) : DoubleArray
    fun ToYCbCr709(values: DoubleArray) : DoubleArray
    fun ToYCoCg(values: DoubleArray) : DoubleArray
}