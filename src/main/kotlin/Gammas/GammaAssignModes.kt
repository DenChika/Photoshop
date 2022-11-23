package Gammas

import Configurations.AppConfiguration
import Converters.GammaConverter

enum class GammaAssignModes {
    SRGB {
        override fun Apply(shapeValues: FloatArray) : FloatArray {
            return GammaConverter.ConvertToSRGB(shapeValues)
        }

    },
    Custom {
        override fun Apply(shapeValues: FloatArray) : FloatArray{
            return GammaConverter.ConvertToVisualize(shapeValues, AppConfiguration.Gamma.AssignCustomValue)
        }

    };
    abstract fun Apply(shapeValues: FloatArray) : FloatArray
    fun GetName(): String {
        return this.name
    }
}