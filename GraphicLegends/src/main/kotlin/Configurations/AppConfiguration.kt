package Configurations

import ColorSpaces.ColorSpace
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap

class AppConfiguration() {
    val imageConfiguration = mutableStateOf(ImageConfiguration())
    val spaceConfiguration = mutableStateOf(SpaceConfiguration())
    val componentConfiguration = mutableStateOf(ComponentConfiguration())
    val bitmap : MutableState<ImageBitmap?> = mutableStateOf(null)
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
                configuration.bitmap.value = value.getImageBitmap()
                configuration.hasContent.value = true
                configuration.imageConfiguration.value = value
            }
        var Space : SpaceConfiguration = configuration.spaceConfiguration.value
        var Component : ComponentConfiguration = configuration.componentConfiguration.value
        fun HasContent() : Boolean {
            return configuration.hasContent.value
        }
        fun GetBitmap() : ImageBitmap
        {
            return configuration.bitmap.value!!
        }
        var ColorSpace : ColorSpace
            get() {
                return configuration.colorSpace.value
            }
            set(value)
            {
                Image.changeColorSpace(value)
                configuration.bitmap.value = Image.getImageBitmap()
                configuration.colorSpace.value = value
            }
    }
}