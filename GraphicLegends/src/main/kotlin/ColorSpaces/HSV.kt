package ColorSpaces

import Interfaces.IColorSpace
import Tools.ColorSpaceException
import kotlin.math.abs

class HSV : IColorSpace {

    override fun ToRGB(values: FloatArray): FloatArray {
        var max = 255 * values[2]
        var min = max * (1 - values[1])
        var z = (max - min) * (1 - abs(values[0] * 360 / 60 % 2 - 1))

        max /= 255
        min /= 255
        z /= 255
        when (values[0] * 360) {
            in 0f..60f -> {
                return floatArrayOf(max, z + min, min)
            }

            in 60f..120f -> {
                return floatArrayOf(z + min, max, min)
            }

            in 120f..180f -> {
                return floatArrayOf(min, max, z + min)
            }

            in 180f..240f -> {
                return floatArrayOf(min, z + min, max)
            }

            in 240f..300f -> {
                return floatArrayOf(z + min, min, max)
            }

            in 300f..360f -> {
                return floatArrayOf(max, min, z + min)
            }
            else -> {
                throw ColorSpaceException("Error. Wrong HSV format.")
            }
        }
    }

    override fun ToCMY(values: FloatArray): FloatArray {
        val rgbValues = ToRGB(values)
        return ColorSpace.RGB.GetService().ToCMY(rgbValues)
    }

    override fun ToHSL(values: FloatArray): FloatArray {
        val rgbValues = ToRGB(values)
        return ColorSpace.RGB.GetService().ToHSL(rgbValues)
    }

    override fun ToHSV(values: FloatArray): FloatArray {
        return values
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