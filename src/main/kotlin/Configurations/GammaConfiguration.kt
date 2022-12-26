package Configurations

import App.GammaActivity.GammaActionsDropdownButton
import App.HeaderDropdownButton
import App.TextFieldActivity.GammaTextField
import Gammas.GammaModes
import Gammas.GammaPurpose
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

class GammaConfiguration {
    val assignTextFieldHidden = mutableStateOf(true)
    val convertTextFieldHidden = mutableStateOf(true)
    val assignExpanded = mutableStateOf(false)
    val convertExpanded = mutableStateOf(false)
    val assignExpandedButton = mutableStateOf(false)
    val convertExpandedButton = mutableStateOf(false)
    private val assignMode = mutableStateOf(GammaModes.SRGB)
    private val convertMode = mutableStateOf(GammaModes.SRGB)
    private val assignCustomValue = mutableStateOf(2.2f)
    private val convertCustomValue = mutableStateOf(2.2f)
    var ConvertCustomValue: Float
        get() {
            return convertCustomValue.value
        }
        set(value) {
            convertCustomValue.value = value
        }
    var AssignMode: GammaModes
        get() {
            return assignMode.value
        }
        set(value) {
            assignMode.value = value
            AppConfiguration.updateBitmap()
        }
    var ConvertMode: GammaModes
        get() {
            return convertMode.value
        }
        set(value) {
            convertMode.value = value
        }
    var AssignCustomValue: Float
        get() {
            return assignCustomValue.value
        }
        set(value) {
            assignCustomValue.value = value
            AppConfiguration.updateBitmap()
        }

    fun ResetSettings() {
        assignTextFieldHidden.value = true
        convertTextFieldHidden.value = true
        AssignMode = GammaModes.SRGB
        ConvertMode = GammaModes.SRGB
        AssignCustomValue = 2.2f
        ConvertCustomValue = 2.2f
    }
    @Composable
    fun GammaMenu(purpose: GammaPurpose) {
        Box {
            val selected = remember { mutableStateOf("${purpose.name} gamma") }
            HeaderDropdownButton(
                onClick = {
                    purpose.Show()
                },
                text = selected.value
            )
            GammaActionsDropdownButton(purpose)
        }
    }
}