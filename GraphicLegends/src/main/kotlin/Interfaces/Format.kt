package Interfaces

import Configurations.ImageConfiguration

interface Format {
    fun HandleReader(width: Int, height: Int, maxShade: Int, byteArray: ByteArray) : ImageConfiguration
    fun HandleWriter(width: Int, height: Int, maxShade: Int, byteArray: ByteArray?) : ByteArray
}