package ColorSpaces

import Interfaces.IColorSpace
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.sqrt

class RGB() : IColorSpace {

    override fun ToRGB(values: FloatArray): FloatArray {
        return values
    }

    override fun ToCMY(values: FloatArray): FloatArray {
        val c = 1 - values[0]
        val m = 1 - values[1]
        val y = 1 - values[2]
        return floatArrayOf(c, m, y)
    }

    override fun ToHSL(values: FloatArray): FloatArray {
        val max = maxOf(values[0], maxOf(values[1], values[2]))
        val min = minOf(values[0], minOf(values[1], values[2]))
        val diff = max - min

        val lightness = (max + min) / 2

        var saturation = 0f

        if (lightness != 0f) {
            saturation = diff / (1 - abs(2 * lightness - 1))
        }

        val r = values[0] * 255
        val g = values[1] * 255
        val b = values[2] * 255

        val hue = if (g >= b) {
            acos((r - g / 2 - b / 2 ) / sqrt(r * r + g * g + b * b - r * g - r * b - g * b)) / Math.PI.toFloat() * 180f
        } else {
            360 - acos((r - g / 2 - b / 2 ) / sqrt(r * r + g * g + b * b - r * g - r * b - g * b)) / Math.PI.toFloat() * 180f
        }

        return floatArrayOf(hue, saturation, lightness)
    }

    override fun ToHSV(values: FloatArray): FloatArray {
        val max = maxOf(values[0], maxOf(values[1], values[2]))
        val min = minOf(values[0], minOf(values[1], values[2]))
        val value = max

        var saturation = 0f

        if (max != 0f) {
            saturation = 1 - min / max
        }

        val r = values[0] * 255
        val g = values[1] * 255
        val b = values[2] * 255

        val hue = if (g >= b) {
            acos((r - g / 2 - b / 2 ) / sqrt(r * r + g * g + b * b - r * g - r * b - g * b)) / Math.PI.toFloat() * 180f
        } else {
            360 - acos((r - g / 2 - b / 2 ) / sqrt(r * r + g * g + b * b - r * g - r * b - g * b)) / Math.PI.toFloat() * 180f
        }

        return floatArrayOf(hue, saturation, value)
    }

    override fun ToYCbCr601(values: FloatArray): FloatArray {
        TODO("Not yet implemented")
    }

    override fun ToYCbCr709(values: FloatArray): FloatArray {
        TODO("Not yet implemented")
    }

    override fun ToYCoCg(values: FloatArray): FloatArray {
        TODO("Not yet implemented")
    }
}