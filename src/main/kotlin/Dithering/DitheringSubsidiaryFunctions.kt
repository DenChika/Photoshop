package Dithering

import ColorSpaces.ColorSpaceInstance
import kotlin.math.pow
import kotlin.math.round

class DitheringSubsidiaryFunctions {
    companion object {
        fun findClosestPaletteColor(oldR: Float, oldG: Float, oldB: Float, shapeBitsCount: Int): FloatArray {
            val bits = 2.0.pow(9 - shapeBitsCount.toDouble())
            val newR = minOf(1.0f, maxOf(0.0f, (round(oldR * 255 / (bits - 1)) * (bits - 1) / 255).toFloat()))
            val newG = minOf(1.0f, maxOf(0.0f, (round(oldG * 255 / (bits - 1)) * (bits - 1) / 255).toFloat()))
            val newB = minOf(1.0f, maxOf(0.0f, (round(oldB * 255 / (bits - 1)) * (bits - 1) / 255).toFloat()))
            return floatArrayOf(newR, newG, newB)
        }

        fun applyError(pixels: Array<ColorSpaceInstance>, nextX: Int, nextY: Int, width: Int, height: Int, errors: FloatArray) {
            if (nextX in 0 until width && nextY in 0 until height) {
                pixels[nextY * width + nextX].ApplyError(errors)
            }
        }
    }
}