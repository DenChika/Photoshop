package Configurations

import ColorSpaces.ColorSpace
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import java.io.File

class AppConfiguration() {
    val imageConfiguration = mutableStateOf(ImageConfiguration())
    val spaceConfiguration = mutableStateOf(SpaceConfiguration())
    val componentConfiguration = mutableStateOf(ComponentConfiguration())
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
        var Space : SpaceConfiguration = configuration.spaceConfiguration.value
        var Component : ComponentConfiguration = configuration.componentConfiguration.value
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