package ColorSpaces

import Interfaces.IColorSpace
import Tools.ColorSpaceException
import kotlin.math.abs

class HSL : IColorSpace {

    override fun ToRGB(values: FloatArray): FloatArray {
        val diff = values[1] * (1 - abs(2 * values[2] - 1))
        var m = 255 * (values[2] - diff / 2)
        val x = diff * (1 - abs(values[0] / 60 % 2 - 1))


        m /= 255

        when (values[0] % 360) {
            in 0f..60f -> {
                return floatArrayOf(diff + m, x + m, m)
            }

            in 60f..120f -> {
                return floatArrayOf(x + m, diff + m, m)
            }

            in 120f..180f -> {
                return floatArrayOf(m, diff + m, x + m)
            }

            in 180f..240f -> {
                return floatArrayOf(m, x + m, diff + m)
            }

            in 240f..300f -> {
                return floatArrayOf(x + m, m, diff + m)
            }

            in 300f..360f -> {
                return floatArrayOf(diff + m, m, x + m)
            }
            else -> throw ColorSpaceException("Error. Wrong HSL format.")
        }
    }

    override fun ToCMY(values: FloatArray): FloatArray {
        val rgbValues = ToRGB(values)
        return ColorSpace.RGB.GetService().ToCMY(rgbValues)
    }

    override fun ToHSL(values: FloatArray): FloatArray {
        return values
    }

    override fun ToHSV(values: FloatArray): FloatArray {
        val rgbValues = ToRGB(values)
        return ColorSpace.RGB.GetService().ToHSV(rgbValues)
    }

    override fun ToYCbCr601(values: FloatArray): FloatArray {
        val rgbValues = ToRGB(values)
        return ColorSpace.RGB.GetService().ToYCbCr601(rgbValues)
    }

    override fun ToYCbCr709(values: FloatArray): FloatArray {
        val rgbValues = ToRGB(values)
        return ColorSpace.RGB.GetService().ToYCbCr709(rgbValues)
    }

    override fun ToYCoCg(values: FloatArray): FloatArray {
        val rgbValues = ToRGB(values)
        return ColorSpace.RGB.GetService().ToYCoCg(rgbValues)
    }
}