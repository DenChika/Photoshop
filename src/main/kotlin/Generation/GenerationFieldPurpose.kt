package Generation

import Configurations.AppConfiguration

enum class GenerationFieldPurpose {
    Width {
        override fun onClickEvent(): (value: String) -> Unit {
            return { value -> AppConfiguration.Generation.width = value.toInt() }
        }

        override fun GetCurrent(): String {
            return AppConfiguration.Generation.width.toString()
        }
    },
    Height {
        override fun onClickEvent(): (value: String) -> Unit {
            return { value -> AppConfiguration.Generation.height = value.toInt() }
        }
        override fun GetCurrent(): String {
            return AppConfiguration.Generation.height.toString()
        }
    };
    abstract fun onClickEvent() : (value: String) -> Unit
    abstract fun GetCurrent() : String
    fun GetName() = this.name.lowercase()
}