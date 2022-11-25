package Configurations

import Dithering.DitheringAlgorithm
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf

class DitheringConfiguration {
    val expanded = mutableStateOf(false)
    val expandedTextField = mutableStateOf(false)
    val shadeBitesCount = mutableStateOf(8)
    private val _selected = mutableStateOf(DitheringAlgorithm.Ordered)
    var selected: DitheringAlgorithm
        get() {
            return _selected.value
        }
        set(value) {
            _selected.value = value
        }
    var ShadeBitesCount : Int
        get() {
            return shadeBitesCount.value
        }
        set(value) {
            shadeBitesCount.value = value
        }
    @Composable
    fun DropdownComponents() {
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            DropdownMenuItem(onClick = {
                selected = DitheringAlgorithm.Ordered
                expandedTextField.value = true
                expanded.value = false
            }) { Text(DitheringAlgorithm.Ordered.name) }
            DropdownMenuItem(onClick = {
                selected = DitheringAlgorithm.Random
                expandedTextField.value = true
                expanded.value = false
            }) { Text(DitheringAlgorithm.Random.name) }
            DropdownMenuItem(onClick = {
                selected = DitheringAlgorithm.FloydSteinberg
                expandedTextField.value = true
                expanded.value = false
            }) { Text(DitheringAlgorithm.FloydSteinberg.name) }
            DropdownMenuItem(onClick = {
                selected = DitheringAlgorithm.Atkinson
                expandedTextField.value = true
                expanded.value = false
            }) { Text(DitheringAlgorithm.Atkinson.name) }
        }
    }
}