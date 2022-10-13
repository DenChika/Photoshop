package Formats

import Interfaces.IFormat
import androidx.compose.ui.graphics.ImageBitmap

class P6 : IFormat {
    override fun Handle(width: Int, height: Int, byteArray: ByteArray) : ImageBitmap? {
        //TODO: implement parser p6 body
        return null
    }
}