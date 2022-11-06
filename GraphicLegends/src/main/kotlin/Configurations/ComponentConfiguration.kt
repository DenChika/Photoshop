package Configurations

import androidx.compose.runtime.mutableStateOf

class ComponentConfiguration {
    val expanded = mutableStateOf(false)
    val selected = mutableStateOf("Without filtration")
}