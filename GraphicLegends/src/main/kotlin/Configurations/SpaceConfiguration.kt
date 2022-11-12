package Configurations

import ColorSpaces.ColorSpace
import Filtration.FiltrationMode
import androidx.compose.runtime.mutableStateOf

class SpaceConfiguration {
    val expanded = mutableStateOf(false)
    private val _selected = mutableStateOf(ColorSpace.RGB)
    var selected: ColorSpace
        get() {
            return _selected.value
        }
        set(value) {
            _selected.value = value
            AppConfiguration.Image.changeColorSpace(value)
            AppConfiguration.Component.selected = FiltrationMode.ALL
            AppConfiguration.updateBitmap()
        }
}