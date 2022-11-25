package Configurations

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset

class LineConfiguration {
    private val color = mutableStateOf(floatArrayOf(0f, 0f, 0f))
    private val maxSaturation = mutableStateOf(1.0f)
    private val startOffset = mutableStateOf(Offset(0f, 0f))
    private val endOffset = mutableStateOf(Offset(0f, 0f))
    private val eventStarted = mutableStateOf(false)
    private val thickness = mutableStateOf(0)
    var FirstShape : Float
        get() {
            return color.value[0]
        }
        set(value) {
            color.value[0] = value
        }
    var SecondShape : Float
        get() {
            return color.value[1]
        }
        set(value) {
            color.value[1] = value
        }
    var ThirdShape : Float
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
            eventStarted.value = true
            startOffset.value = value
        }
    var End : Offset
        get() {
            return endOffset.value
        }
        set(value) {
            eventStarted.value = false
            endOffset.value = value
        }
    var Thickness : Int
        get() {
            return thickness.value
        }
        set(value) {
            thickness.value = value
        }
    fun IsPainting() : Boolean {
        return eventStarted.value
    }
    fun GetColor() : FloatArray
    {
        return color.value
    }


}