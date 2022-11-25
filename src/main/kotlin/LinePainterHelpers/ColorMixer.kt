package LinePainterHelpers

import Tools.ColorMixException

class ColorMixer {
    companion object {
        fun Mix(originalColor : FloatArray, additionalColor : FloatArray, saturation : Float) : FloatArray
        {
            if (saturation < 0f || saturation > 1f){
                ColorMixException.invalidSaturation(saturation)
            }

            val newColor = floatArrayOf(0f, 0f, 0f)
            for (i : Int in 0..2){
                newColor[i] = originalColor[i] * (1 - saturation) + additionalColor[i] * saturation
            }

            return newColor
        }
    }
}