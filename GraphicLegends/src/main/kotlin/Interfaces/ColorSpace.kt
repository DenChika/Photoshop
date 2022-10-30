package Interfaces

import androidx.compose.ui.graphics.ImageBitmap

interface ColorSpace {
    fun ToRGB(bodyArray: DoubleArray) : ImageBitmap?
}