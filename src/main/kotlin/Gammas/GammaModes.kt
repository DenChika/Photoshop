package Gammas

import Configurations.AppConfiguration
import Converters.GammaConverter

enum class GammaModes {
    SRGB {
        override fun Apply(shapeValues: FloatArray, purpose: GammaPurpose) : FloatArray {
            return GammaConverter.ConvertToSRGB(shapeValues)
        }

    },
    Custom {
        override fun Apply(shapeValues: FloatArray, purpose: GammaPurpose) : FloatArray{
            return GammaConverter.ConvertToCustom(shapeValues, purpose.GetCustomValue())
        }

    };
    abstract fun Apply(shapeValues: FloatArray, purpose: GammaPurpose) : FloatArray
    fun GetName(): String {
        return this.name
    }
}