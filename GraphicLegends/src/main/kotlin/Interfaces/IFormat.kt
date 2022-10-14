package Interfaces

import androidx.compose.ui.graphics.ImageBitmap

interface IFormat {
    fun HandleReader(width: Int, height: Int, maxShade: Int, byteArray: ByteArray) : ImageBitmap?
    fun HandleWriter(width: Int, height: Int, maxShade: Int, byteArray: ByteArray?) : ByteArray
}