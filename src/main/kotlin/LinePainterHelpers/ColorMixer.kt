package LinePainterHelpers

import Tools.ColorMixException
import kotlin.math.max
import kotlin.math.min

class ColorMixer {
    companion object {
        fun Mix(originalColor : FloatArray, additionalColor : FloatArray, saturation : Float) : FloatArray
        {
            if (saturation < 0f || saturation > 1f || saturation.isNaN()){
                throw ColorMixException.invalidSaturation(saturation)
            }

            val newColor = floatArrayOf(0f, 0f, 0f)
            for (i : Int in 0..2){
                newColor[i] = max(0f, min(1f, originalColor[i] * (1 - saturation) + additionalColor[i] * saturation))
            }

            return newColor
        }
    }
}