package Converters

import kotlin.math.pow

class GammaConverter {
    companion object {
        fun ConvertToVisualize(shapeValues : FloatArray, gammaValue: Float) : FloatArray {
            for (i in 0..2) {
                shapeValues[i] = shapeValues[i].pow(2.2f / gammaValue)
            }

            return shapeValues
        }
        fun ConvertToSRGB(shapeValues : FloatArray) : FloatArray {
            for (i in 0..2) {
                if (shapeValues[i] <= 0.0031308f) {
                    shapeValues[i] = (12.92f * shapeValues[i]).pow(2.2f)
                } else {
                    shapeValues[i] = ((1f + 0.055f) * shapeValues[i].pow(1 / 2.4f) - 0.055f).pow(2.2f)
                }
            }

            return shapeValues
        }
        fun ConvertToSave(shapeValues : FloatArray, oldValue: Float, newValue: Float) : FloatArray {
            return shapeValues
            //TODO("Add implementation")
        }
    }
}