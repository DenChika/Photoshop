package Interfaces

import androidx.compose.ui.graphics.ImageBitmap

interface IFormat {
    fun Handle(width: Int, height: Int, maxShade: UInt, byteArray: ByteArray) : ImageBitmap?
}