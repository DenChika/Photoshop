package Formats

import ColorSpaces.ColorSpaceInstance
import Configurations.ImageConfiguration
import Interfaces.IFormat

class PNG : IFormat {
    override fun HandleReader(width: Int, height: Int, maxShade: Int, byteArray: ByteArray): ImageConfiguration {
        TODO("Not yet implemented")
    }

    override fun HandleWriter(width: Int, height: Int, maxShade: Int, pixels: Array<ColorSpaceInstance>): ByteArray {
        TODO("Not yet implemented")
    }

    override fun ByteArrayFromPixels(pixels: Array<ColorSpaceInstance>): ByteArray {
        TODO("Not yet implemented")
    }
}