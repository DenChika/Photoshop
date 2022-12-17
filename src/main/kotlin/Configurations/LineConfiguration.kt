package Configurations

import App.HeaderDropdownButton
import App.TextFieldActivity.LineSettingsTextField
import LinePainterHelpers.LineSettings
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset

class LineConfiguration {
    val colorExpanded = mutableStateOf(false)
    val saturationExpanded = mutableStateOf(false)
    val thicknessExpanded = mutableStateOf(false)
    private val menuExpanded = mutableStateOf(false)
    private val color = mutableStateOf(floatArrayOf(0f, 0f, 0f))
    private val maxSaturation = mutableStateOf(1.0f)
    private val startOffset = mutableStateOf(Offset(0f, 0f))
    private val endOffset = mutableStateOf(Offset(0f, 0f))
    private val eventStarted = mutableStateOf(false)
    private val thickness = mutableStateOf(1)
    var FirstShade : Float
        get() {
            return color.value[0]
        }
        set(value) {
            color.value[0] = value
        }
    var SecondShade : Float
        get() {
            return color.value[1]
        }
        set(value) {
            color.value[1] = value
        }
    var ThirdShade : Float
        get() {
            return color.value[2]
        }
        set(value) {
            color.value[2] = value
        }
    var MaxSaturation : Float
        get() {
            return maxSaturation.value
        }
        set(value) {
            maxSaturation.value = value
        }
    var Start : Offset
        get() {
            return startOffset.value
        }
        set(value) {
            startOffset.value = value
        }
    var End : Offset
        get() {
            return endOffset.value
        }
        set(value) {
            endOffset.value = value
        }
    var Thickness : Int
        get() {
            return thickness.value
        }
        set(value) {
            thickness.value = value
        }

    var IsPainting : Boolean = eventStarted.value

    fun GetColor() : FloatArray
    {
        return color.value
    }
    fun ResetSettings() {
        saturationExpanded.value = false
        colorExpanded.value = false
        thicknessExpanded.value = false
        color.value = floatArrayOf(0f, 0f, 0f)
        maxSaturation.value = 1.0f
        startOffset.value = Offset(0f, 0f)
        endOffset.value = Offset(0f, 0f)
        eventStarted.value = false
        thickness.value = 1
    }
    @Composable
    fun DropdownLineSettings() {
        HeaderDropdownButton(
            onClick = {
                menuExpanded.value = true
            },
            text = "Line Settings"
        )
        DropdownMenu(
            expanded = menuExpanded.value,
            onDismissRequest = { menuExpanded.value = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    colorExpanded.value = true
                    saturationExpanded.value = false
                    thicknessExpanded.value = false
                    menuExpanded.value = false
                }
            ) { Text("Configure color") }
            DropdownMenuItem(
                onClick = {
                    saturationExpanded.value = true
                    colorExpanded.value = false
                    thicknessExpanded.value = false
                    menuExpanded.value = false
                }
            ) { Text("Configure saturation") }
            DropdownMenuItem(
                onClick = {
                    thicknessExpanded.value = true
                    colorExpanded.value = false
                    saturationExpanded.value = false
                    menuExpanded.value = false
                }
            ) { Text("Configure thickness") }
        }
    }

    @Composable
    fun GetFirstShadeTextField() {
        LineSettingsTextField(
            settings = LineSettings.FirstShade,
            label = "1st channel"
        )
    }

    @Composable
    fun GetSecondShadeTextField() {
        LineSettingsTextField(
            settings = LineSettings.SecondShade,
            label = "2nd channel"
        )
    }

    @Composable
    fun GetThirdShadeTextField() {
        LineSettingsTextField(
            settings = LineSettings.ThirdShade,
            label = "3rd channel"
        )
    }
}