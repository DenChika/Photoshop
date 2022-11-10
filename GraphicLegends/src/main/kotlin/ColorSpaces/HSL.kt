package ColorSpaces

import Interfaces.IColorSpace
import Tools.ColorSpaceException
import kotlin.math.abs

class HSL : IColorSpace {

    override fun ToRGB(values: FloatArray): FloatArray {
        val diff = values[1] * (1 - abs(2 * values[2] - 1))
        val m = 255 * (values[2] - diff / 2)
        val x = diff * (1 - abs(values[0] / 60 % 2 - 1))


        when (values[0]) {
            in 0f..60f -> {
                return floatArrayOf(diff + m / 255, x + m / 255, m / 255)
            }

            in 60f..120f -> {
                return floatArrayOf(x + m / 255, diff + m / 255, m / 255)
            }

            in 120f..180f -> {
                return floatArrayOf(m / 255, diff + m / 255, x + m / 255)
            }

            in 180f..240f -> {
                return floatArrayOf(m / 255, x + m / 255, diff + m / 255)
            }

            in 240f..300f -> {
                return floatArrayOf(x + m / 255, m / 255, diff + m / 255)
            }

            in 300f..360f -> {
                return floatArrayOf(diff + m / 255, m / 255, x + m / 255)
            }
            else -> throw ColorSpaceException("Error. Wrong HSL format.")
        }
    }

    override fun ToCMY(values: FloatArray): FloatArray {
        TODO("Not yet implemented")
    }

    override fun ToHSL(values: FloatArray): FloatArray {
        return values
    }

    override fun ToHSV(values: FloatArray): FloatArray {
        TODO("Not yet implemented")
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