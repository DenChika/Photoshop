package Configuration

import Formats.Modes
import java.awt.image.BufferedImage

class MutableConfigurationsState {
    companion object {
        var bufferedImage: BufferedImage? = null
        lateinit var byteArray: ByteArray
        var shade = 255
        var mode = Modes.P6
    }
}