package Configurations

import androidx.compose.runtime.mutableStateOf

class SpaceConfiguration {
    val expanded = mutableStateOf(false)
    val selected = mutableStateOf("RGB")
}