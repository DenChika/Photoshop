package Configurations

import App.HeaderDropdownButton
import ColorSpaces.ColorSpace
import App.Components.ComponentMode
import androidx.compose.foundation.layout.Box
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf

class SpaceConfiguration {
    val expanded = mutableStateOf(false)
    val expandedButton = mutableStateOf(false)
    private val _selected = mutableStateOf(ColorSpace.RGB)
    var selected: ColorSpace
        get() {
            return _selected.value
        }
        set(value) {
            _selected.value = value
            AppConfiguration.Image.changeColorSpace(value)
            AppConfiguration.Component.selected = ComponentMode.ALL
            AppConfiguration.updateBitmap()
        }

    @Composable
    fun ShowTool() {
        if (expandedButton.value) {
            Box {
                HeaderDropdownButton(
                    onClick = {
                        expanded.value = true
                    },
                    text = selected.GetName()
                )
                DropdownSpaces()
            }
        }
    }
    @Composable
    fun DropdownSpaces() {
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            DropdownMenuItem(onClick = {
                selected = ColorSpace.RGB
                expanded.value = false
            }) { Text(ColorSpace.RGB.GetName()) }
            DropdownMenuItem(onClick = {
                selected = ColorSpace.CMY
                expanded.value = false
            }) { Text(ColorSpace.CMY.GetName()) }
            DropdownMenuItem(onClick = {
                selected = ColorSpace.HSL
                expanded.value = false
            }) { Text(ColorSpace.HSL.GetName()) }
            DropdownMenuItem(onClick = {
                selected = ColorSpace.HSV
                expanded.value = false
            }) { Text(ColorSpace.HSV.GetName()) }
            DropdownMenuItem(onClick = {
                selected = ColorSpace.YCbCr601
                expanded.value = false
            }) { Text(ColorSpace.YCbCr601.GetName()) }
            DropdownMenuItem(onClick = {
                selected = ColorSpace.YCbCr709
                expanded.value = false
            }) { Text(ColorSpace.YCbCr709.GetName()) }
            DropdownMenuItem(onClick = {
                selected = ColorSpace.YCoCg
                expanded.value = false
            }) { Text(ColorSpace.YCoCg.GetName()) }
        }
    }
}