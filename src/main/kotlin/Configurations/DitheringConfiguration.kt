package Configurations

import App.HeaderDropdownButton
import App.TextFieldActivity.CustomTextField
import Dithering.DitheringAlgorithm
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class DitheringConfiguration {
    val expanded = mutableStateOf(false)
    val expandedButton = mutableStateOf(false)
    val expandedAlgorithms = mutableStateOf(false)
    val expandedTextField = mutableStateOf(false)
    val shadeBitesCount = mutableStateOf(8)
    private val _selected = mutableStateOf(DitheringAlgorithm.Ordered)
    var selected: DitheringAlgorithm
        get() {
            return _selected.value
        }
        set(value) {
            AppConfiguration.Image.useDithering()
            AppConfiguration.updateBitmap()
            shadeBitesCount.value = 8
        }
    var ShadeBitesCount : Int
        get() {
            return shadeBitesCount.value
        }
        set(value) {
            shadeBitesCount.value = value
        }

    @Composable
    fun ShowTool() {
        if (expandedButton.value) {
            DitheringMenu()
        }
    }
    @Composable
    fun DitheringMenu() {
        Box {
            HeaderDropdownButton(
                onClick = {
                    expanded.value = true
                    if (expandedTextField.value) expandedTextField.value = false
                },
                text = "Dithering"
            )
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                Box {
                    Button(
                        onClick = { expandedAlgorithms.value = true },
                        colors = ButtonDefaults.buttonColors(Color.White),
                        border = BorderStroke(0.dp, Color.White)
                    ) {
                        Text("Algorithm")
                    }
                    DropdownMenu(
                        expanded = expandedAlgorithms.value,
                        onDismissRequest = { expandedAlgorithms.value = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            selected = DitheringAlgorithm.Ordered
                            expandedAlgorithms.value = false
                            expanded.value = false
                        }) { Text(DitheringAlgorithm.Ordered.name) }
                        DropdownMenuItem(onClick = {
                            selected = DitheringAlgorithm.Random
                            expandedAlgorithms.value = false
                            expanded.value = false
                        }) { Text(DitheringAlgorithm.Random.name) }
                        DropdownMenuItem(onClick = {
                            selected = DitheringAlgorithm.FloydSteinberg
                            expandedAlgorithms.value = false
                            expanded.value = false
                        }) { Text(DitheringAlgorithm.FloydSteinberg.name) }
                        DropdownMenuItem(onClick = {
                            selected = DitheringAlgorithm.Atkinson
                            expandedAlgorithms.value = false
                            expanded.value = false
                        }) { Text(DitheringAlgorithm.Atkinson.name) }
                    }
                }
                DropdownMenuItem(onClick = {
                    expanded.value = false
                    expandedTextField.value = true
                }) { Text("Bits") }
            }
        }
        if (expandedTextField.value) {
            CustomTextField(
                label = "Bits Dithering",
                placeholder = "Your value",
                defaultValue = ShadeBitesCount.toString(),
                onClickFunc = {value ->  ShadeBitesCount = value.toInt()}
            )
        }
    }
}