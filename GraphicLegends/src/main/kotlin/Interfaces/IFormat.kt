package Interfaces

import Configurations.ImageConfiguration

interface IFormat {
    fun HandleReader(width: Int, height: Int, maxShade: Int, byteArray: ByteArray) : ImageConfiguration
    fun HandleWriter(width: Int, height: Int, maxShade: Int, pixels: Array<IColorSpace>) : ByteArray
    fun ByteArrayFromPixels(pixels: Array<IColorSpace>) : ByteArray
}