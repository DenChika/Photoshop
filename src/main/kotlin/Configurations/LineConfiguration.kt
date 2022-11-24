package Configurations

import androidx.compose.runtime.mutableStateOf

class LineConfiguration {
    val color = mutableStateOf(AppConfiguration.Space.selected.GetDefault())
    val maxSaturation = mutableStateOf(1.0f)
    var FirstShape : Float
        get() {
            return color.value.firstShade
        }
        set(value) {
            color.value.firstShade = value
        }
    var SecondShape : Float
        get() {
            return color.value.secondShade
        }
        set(value) {
            color.value.secondShade = value
        }
    var ThirdShape : Float
        get() {
            return color.value.thirdShade
        }
        set(value) {
            color.value.thirdShade = value
        }
    var MaxSaturation : Float
        get() {
            return maxSaturation.value
        }
        set(value) {
            maxSaturation.value = value
        }
}