package Configurations

import ColorSpaces.ColorSpace
import androidx.compose.runtime.mutableStateOf

class AppConfiguration() {
    val imageConfiguration = mutableStateOf(ImageConfiguration())
    val hasContent =  mutableStateOf(false)
    val colorSpace = mutableStateOf(ColorSpaces.ColorSpace.RGB)
    companion object{
        private var configuration : AppConfiguration = AppConfiguration()
        var Image : ImageConfiguration
            get() {
                return configuration.imageConfiguration.value
            }
            set(value)
            {
                configuration.hasContent.value = true
                configuration.imageConfiguration.value = value
            }
        fun HasContent() : Boolean {
            return configuration.hasContent.value
        }
        var ColorSpace : ColorSpace
            get() {
                return configuration.colorSpace.value
            }
            set(value)
            {
                configuration.colorSpace.value = value
            }
    }
}