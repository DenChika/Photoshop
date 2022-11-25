package LinePainterHelpers

import Configurations.AppConfiguration

enum class LineSettings {
    FirstShade {
        override fun IsError(value: String): Boolean {
            return value.toFloatOrNull() == null || value.toFloat() > 1.0F || value.toFloat() < 0.0F
        }

        override fun GetDefaultValue(): String {
            return "0.0"
        }

        override fun ChangeValue(value: String) {
            AppConfiguration.Line.FirstShade = value.toFloat()
        }
    },
    SecondShade {
        override fun IsError(value: String): Boolean {
            return value.toFloatOrNull() == null || value.toFloat() > 1.0F || value.toFloat() < 0.0F
        }

        override fun GetDefaultValue(): String {
            return "0.0"
        }

        override fun ChangeValue(value: String) {
            AppConfiguration.Line.SecondShade = value.toFloat()
        }
    },
    ThirdShade {
        override fun IsError(value: String): Boolean {
            return value.toFloatOrNull() == null || value.toFloat() > 1.0F || value.toFloat() < 0.0F
        }

        override fun GetDefaultValue(): String {
            return "1.0"
        }

        override fun ChangeValue(value: String) {
            AppConfiguration.Line.ThirdShade = value.toFloat()
        }
    },
    Saturation {
        override fun IsError(value: String): Boolean {
            return value.toFloatOrNull() == null || value.toFloat() > 1.0F || value.toFloat() < 0.0F
        }

        override fun GetDefaultValue(): String {
            return "0.0"
        }

        override fun ChangeValue(value: String) {
            AppConfiguration.Line.MaxSaturation = value.toFloat()
        }
    },
    Thickness {
        override fun IsError(value: String): Boolean {
            return value.toIntOrNull() == null || value.toInt() < 1
        }

        override fun GetDefaultValue(): String {
            return "1"
        }

        override fun ChangeValue(value: String) {
            AppConfiguration.Line.Thickness = value.toInt()
        }
    };
    abstract fun IsError(value: String) : Boolean

    abstract fun GetDefaultValue() : String

    abstract fun ChangeValue(value: String)
}