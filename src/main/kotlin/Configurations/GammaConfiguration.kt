package Configurations

import Gammas.GammaAssignModes
import androidx.compose.runtime.mutableStateOf

class GammaConfiguration {
    val expanded = mutableStateOf(false)
    private val assignValue = mutableStateOf(GammaAssignModes.SRGB)
    private val assignFloatValue = mutableStateOf(2.2f)
    private val convertValue = mutableStateOf(1.0f)
    var ConvertValue: Float
        get() {
            return convertValue.value
        }
        set(value) {
            AppConfiguration.Image.changeGamma(value)
            convertValue.value = value
        }
    var AssignMode: GammaAssignModes
        get() {
            return assignValue.value
        }
        set(value) {
            assignValue.value = value
            AppConfiguration.updateBitmap()
        }
    var AssignCustomValue: Float
        get() {
            return assignFloatValue.value
        }
        set(value) {
            assignFloatValue.value = value
            AppConfiguration.updateBitmap()
        }
}