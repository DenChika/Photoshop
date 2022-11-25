package Interfaces

interface IColorSpace {
    fun ToRGB(values : FloatArray) : FloatArray
    fun ToCMY(values : FloatArray) : FloatArray
    fun ToHSL(values : FloatArray) : FloatArray
    fun ToHSV(values: FloatArray) : FloatArray
    fun ToYCbCr601(values: FloatArray) : FloatArray
    fun ToYCbCr709(values: FloatArray) : FloatArray
    fun ToYCoCg(values: FloatArray) : FloatArray
}