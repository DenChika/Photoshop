package Configurations

import Filtration.FiltrationMode
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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

    @Composable
    fun DropdownComponents() {
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            DropdownMenuItem(onClick = {
                selected = FiltrationMode.ALL
                expanded.value = false
            }) { Text(FiltrationMode.ALL.GetName()) }
            DropdownMenuItem(onClick = {
                selected = FiltrationMode.OnlyFirst
                expanded.value = false
            }) { Text(FiltrationMode.OnlyFirst.GetName()) }
            DropdownMenuItem(onClick = {
                selected = FiltrationMode.OnlySecond
                expanded.value = false
            }) { Text(FiltrationMode.OnlySecond.GetName()) }
            DropdownMenuItem(onClick = {
                selected = FiltrationMode.OnlyThird
                expanded.value = false
            }) { Text(FiltrationMode.OnlyThird.GetName()) }
        }
    }
}