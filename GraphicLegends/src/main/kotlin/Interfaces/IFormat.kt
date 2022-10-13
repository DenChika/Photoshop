package Interfaces

import androidx.compose.ui.graphics.ImageBitmap

interface IFormat {
    fun Handle(width: Int, height: Int, byteArray: ByteArray) : ImageBitmap?
}