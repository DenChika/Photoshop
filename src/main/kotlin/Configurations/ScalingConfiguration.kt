package Configurations

import App.HeaderButton
import App.HeaderDropdownButton
import App.TextFieldActivity.CustomTextField
import Scaling.ScalingMode
import androidx.compose.foundation.layout.Box
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset

class ScalingConfiguration {
    val expandedButton = mutableStateOf(false)
    val expanded = mutableStateOf(false)
    val expandedTextField = mutableStateOf(false)
    val neededWidth = mutableStateOf(500)
    val neededHeight = mutableStateOf(500)
    private val centerOffset = mutableStateOf(Offset(0f, 0f))
    private var _selected = mutableStateOf(ScalingMode.NearestNeighbor)
    val bValue = mutableStateOf(0f)
    val cValue = mutableStateOf(0.5f)
    var Width: Int
        get() {
            return neededWidth.value
        }
        set(value) {
            neededWidth.value = value
        }

    var Height: Int
        get() {
            return neededHeight.value
        }
        set(value) {
            neededHeight.value = value
        }
    var BValue: Float
        get() {
            return bValue.value
        }
        set(value) {
            bValue.value = value
        }
    var CValue: Float
        get() {
            return cValue.value
        }
        set(value) {
            cValue.value = value
        }
    var Selected: ScalingMode
        get() {
            return _selected.value
        }
        set(value) {
            _selected.value = value
        }
    var Center : Offset
        get() {
            return centerOffset.value
        }
        set(value) {
            centerOffset.value = value
        }

    @Composable
    fun ShowTool() {
        if (expandedButton.value) {
            ScalingMenu()
        }
    }

    @Composable
    fun ScalingMenu() {
        Box {
            HeaderDropdownButton(
                onClick = {
                    expanded.value = true
                    if (expandedTextField.value) expandedTextField.value = false
                },
                text = "Scaling"
            )
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                DropdownMenuItem(onClick = {
                    Selected = ScalingMode.NearestNeighbor
                    expandedTextField.value = true
                    expanded.value = false
                }) { Text(ScalingMode.NearestNeighbor.name) }
                DropdownMenuItem(onClick = {
                    Selected = ScalingMode.Bilinear
                    expandedTextField.value = true
                    expanded.value = false
                }) { Text(ScalingMode.Bilinear.name) }
                DropdownMenuItem(onClick = {
                    Selected = ScalingMode.Lanczos3
                    expandedTextField.value = true
                    expanded.value = false
                }) { Text(ScalingMode.Lanczos3.name) }
                DropdownMenuItem(onClick = {
                    Selected = ScalingMode.BCSplines
                    expandedTextField.value = true
                    expanded.value = false
                }) { Text(ScalingMode.BCSplines.name) }
            }
        }
        if (expandedTextField.value) {
            CustomTextField(
                label = "Needed Width",
                placeholder = "Your value",
                defaultValue = Width.toString(),
                onClickFunc = { value -> Width = value.toInt() }
            )
            CustomTextField(
                label = "Needed Height",
                placeholder = "Your value",
                defaultValue = Height.toString(),
                onClickFunc = { value -> Height = value.toInt() }
            )
            if (Selected == ScalingMode.BCSplines) {
                CustomTextField(
                    label = "B coefficient value",
                    placeholder = "Your value",
                    defaultValue = BValue.toString(),
                    onClickFunc = { value -> BValue = value.toFloat() }
                )
                CustomTextField(
                    label = "C coefficient value",
                    placeholder = "Your value",
                    defaultValue = CValue.toString(),
                    onClickFunc = { value -> CValue = value.toFloat() }
                )
            }
            HeaderButton(
                onClick = {
                    expandedTextField.value = false
                    AppConfiguration.Image = Selected.Do()
                    AppConfiguration.updateBitmap()
                },
                text = "Scale"
            )
        }
    }
}