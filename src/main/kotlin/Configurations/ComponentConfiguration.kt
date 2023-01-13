package Configurations

import App.HeaderDropdownButton
import App.Components.ComponentMode
import androidx.compose.foundation.layout.Box
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf

class ComponentConfiguration {
    val expanded = mutableStateOf(false)
    val expandedButton = mutableStateOf(false)
    private var _selected = mutableStateOf(ComponentMode.ALL)
    var selected: ComponentMode
        get() {
            return _selected.value
        }
        set(value) {
            _selected.value = value
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
                DropdownComponents()
            }
        }
    }
    @Composable
    fun DropdownComponents() {
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            DropdownMenuItem(onClick = {
                selected = ComponentMode.ALL
                expanded.value = false
            }) { Text(ComponentMode.ALL.GetName()) }
            DropdownMenuItem(onClick = {
                selected = ComponentMode.OnlyFirst
                expanded.value = false
            }) { Text(ComponentMode.OnlyFirst.GetName()) }
            DropdownMenuItem(onClick = {
                selected = ComponentMode.OnlySecond
                expanded.value = false
            }) { Text(ComponentMode.OnlySecond.GetName()) }
            DropdownMenuItem(onClick = {
                selected = ComponentMode.OnlyThird
                expanded.value = false
            }) { Text(ComponentMode.OnlyThird.GetName()) }
        }
    }
}