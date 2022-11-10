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

    private fun ToYCbCr(a: Float, b: Float, values: FloatArray): FloatArray{
        val c = 1 - a - b
        val d = 2 * (a + b)
        val e = 2 * (1 - a)

        val y  = a * values[0] + b * values[1] + c * values[2]
        val cb = (values[2] - y) / d
        val cr = (values[0] - y) / e

        return floatArrayOf(y, cb, cr)
    }

    override fun ToYCbCr601(values: FloatArray): FloatArray {
        return ToYCbCr(0.299f, 0.587f, values)
    }

    override fun ToYCbCr709(values: FloatArray): FloatArray {
        return ToYCbCr(0.2126f, 0.7152f, values)
    }

    override fun ToYCoCg(values: FloatArray): FloatArray {
        val y = values[0] / 4 + values[1] / 2 + values[2] / 4
        val co = values[0]/ 2 - values[2] / 2
        val cg = -values[0] / 4 + values[1] / 2 - values[2] / 4

        return floatArrayOf(y, co, cg)
    }
}