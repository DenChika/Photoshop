package Interfaces

import androidx.compose.ui.graphics.ImageBitmap

interface IFormat {
    fun Handle(width: Int, height: Int, maxShade: Int, byteArray: ByteArray) : ImageBitmap?
}