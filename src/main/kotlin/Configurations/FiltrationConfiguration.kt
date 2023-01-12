package Configurations

import App.HeaderDropdownButton
import App.TextFieldActivity.CustomTextField
import Dithering.DitheringAlgorithm
import Filtration.FiltrationAlgorithm
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class FiltrationConfiguration {
    val expandedButton = mutableStateOf(false)
    val expandedAlgorithms = mutableStateOf(false)
    val expandedThresholdTextField = mutableStateOf(false)
    val expandedRadiusTextField = mutableStateOf(false)
    val expandedSigmaTextField = mutableStateOf(false)
    val expandedSharpnessTextField = mutableStateOf(false)
    val thresholdValue = mutableStateOf(0)
    val radiusValue = mutableStateOf(0)
    val sigmaValue = mutableStateOf(0.0f)
    val sharpnessValue = mutableStateOf(0.0f)
    private val _selected = mutableStateOf(FiltrationAlgorithm.Threshold)
    var selected: FiltrationAlgorithm
        get() {
            return _selected.value
        }
        set(value) {
            _selected.value = value
        }

    fun Apply() {
        AppConfiguration.Image.useFiltration()
        AppConfiguration.updateBitmap()
    }
    @Composable
    fun ShowTool() {
        if (expandedButton.value) {
            FiltrationMenu()
        }
    }
    @Composable
    fun FiltrationMenu() {
        Box {
            HeaderDropdownButton(
                onClick = {
                    expandedAlgorithms.value = true
                    expandedThresholdTextField.value = false
                    expandedRadiusTextField.value = false
                    expandedSigmaTextField.value = false
                    expandedSharpnessTextField.value = false
                },
                text = "Filtration algorithm"
            )
            DropdownMenu(
                expanded = expandedAlgorithms.value,
                onDismissRequest = { expandedAlgorithms.value = false }
            ) {
                DropdownMenuItem(onClick = {
                    selected = FiltrationAlgorithm.Threshold
                    expandedAlgorithms.value = false
                    expandedThresholdTextField.value = true
                }) { Text(FiltrationAlgorithm.Threshold.name) }
                DropdownMenuItem(onClick = {
                    selected = FiltrationAlgorithm.OtsuThreshold
                    expandedAlgorithms.value = false
                    Apply()
                }) { Text(FiltrationAlgorithm.OtsuThreshold.name) }
                DropdownMenuItem(onClick = {
                    selected = FiltrationAlgorithm.Median
                    expandedAlgorithms.value = false
                    expandedRadiusTextField.value = true
                }) { Text(FiltrationAlgorithm.Median.name) }
                DropdownMenuItem(onClick = {
                    selected = FiltrationAlgorithm.Gaussian
                    expandedAlgorithms.value = false
                    expandedSigmaTextField.value = true
                }) { Text(FiltrationAlgorithm.Gaussian.name) }
                DropdownMenuItem(onClick = {
                    selected = FiltrationAlgorithm.BoxBlur
                    expandedAlgorithms.value = false
                    expandedRadiusTextField.value = true
                }) { Text(FiltrationAlgorithm.BoxBlur.name) }
                DropdownMenuItem(onClick = {
                    selected = FiltrationAlgorithm.SobelFilter
                    expandedAlgorithms.value = false
                    Apply()
                }) { Text(FiltrationAlgorithm.SobelFilter.name) }
                DropdownMenuItem(onClick = {
                    selected = FiltrationAlgorithm.ContrastAdaptiveSharpening
                    expandedAlgorithms.value = false
                    expandedSharpnessTextField.value = true
                }) { Text(FiltrationAlgorithm.ContrastAdaptiveSharpening.name) }
            }
        }
        if (expandedThresholdTextField.value) {
            CustomTextField(
                label = "Set threshold",
                placeholder = "Your value",
                defaultValue = thresholdValue.value.toString(),
                onClickFunc = { value -> thresholdValue.value = value.toInt(); Apply() },
            )
        }
        if (expandedRadiusTextField.value) {
            CustomTextField(
                label = "Set radius",
                placeholder = "Your value",
                defaultValue = radiusValue.value.toString(),
                onClickFunc = { value -> radiusValue.value = value.toInt(); Apply() }
            )
        }
        if (expandedSigmaTextField.value) {
            CustomTextField(
                label = "Set sigma",
                placeholder = "Your value",
                defaultValue = sigmaValue.value.toString(),
                onClickFunc = { value -> sigmaValue.value = value.toFloat(); Apply() }
            )
        }
        if (expandedSharpnessTextField.value) {
            CustomTextField(
                label = "Set sharpness",
                placeholder = "Your value",
                defaultValue = sharpnessValue.value.toString(),
                onClickFunc = { value -> sharpnessValue.value = value.toFloat(); Apply() }
            )
        }
    }
}