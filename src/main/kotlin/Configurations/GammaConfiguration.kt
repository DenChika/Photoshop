package Configurations

import androidx.compose.runtime.mutableStateOf

class GammaConfiguration {
    private val visualisationValue = mutableStateOf(2.2f)
    private val fileValue = mutableStateOf(2.2f)
    var forFile : Float
        get() {
            return fileValue.value
        }
        set(value) {
            AppConfiguration.Image.changeGamma(value)
            fileValue.value = value
        }
    var forVisualization : Float
        get() {
            return visualisationValue.value
        }
        set(value) {
            AppConfiguration.updateBitmap(value)
            fileValue.value = value
        }
}