package Formats

import Interfaces.IFormat
import androidx.compose.ui.graphics.ImageBitmap

class P6 : IFormat {
    override fun HandleReader(width: Int, height: Int, maxShade: Int, byteArray: ByteArray) : ImageBitmap? {
        //TODO: implement parser p6 body
        return null
    }
    override fun HandleWriter(width: Int, height: Int, maxShade: Int, byteArray: ByteArray?) : ByteArray? {
        return null
    }
}