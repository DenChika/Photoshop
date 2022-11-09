package Interfaces

import ColorSpaces.ColorSpaceInstance
import Configurations.ImageConfiguration

interface IFormat {
    fun HandleReader(width: Int, height: Int, maxShade: Int, byteArray: ByteArray) : ImageConfiguration
    fun HandleWriter(width: Int, height: Int, maxShade: Int, pixels: Array<ColorSpaceInstance>) : ByteArray
    fun ByteArrayFromPixels(pixels: Array<ColorSpaceInstance>) : ByteArray
}