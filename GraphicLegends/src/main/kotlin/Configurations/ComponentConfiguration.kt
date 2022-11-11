package Configurations

import Filtration.FiltrationMode
import androidx.compose.runtime.mutableStateOf

class ComponentConfiguration {
    val expanded = mutableStateOf(false)
    private var _selected = mutableStateOf(FiltrationMode.ALL)
    var selected: FiltrationMode
        get() {
            return _selected.value
        }
        set(value) {
            _selected.value = value
            AppConfiguration.updateBitmap()
        }
}