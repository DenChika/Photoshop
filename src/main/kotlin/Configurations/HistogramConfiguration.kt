package Configurations

import App.HeaderButton
import App.TextFieldActivity.CustomTextField
import HistogramSupport.ContrastFixer
import HistogramSupport.HistogramGetter
import HistogramSupport.InfoCollector
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

class HistogramConfiguration {
    private val firstShapeHistogram = mutableStateOf<ImageBitmap?>(null)
    private val secondShapeHistogram = mutableStateOf<ImageBitmap?>(null)
    private val thirdShapeHistogram = mutableStateOf<ImageBitmap?>(null)

    val histogramInfo = List(3) { MutableList(256) {0} }
    private val ignoreThresholdProportion = mutableStateOf(0f)
    val expandedButton = mutableStateOf(false)

    var IgnoreThresholdProportion : Float
        get() {
            return ignoreThresholdProportion.value
        }
        set(value) {
            ignoreThresholdProportion.value = value
        }

    fun updateInfo() {
        histogramInfo.forEach {
            for (i in 0 until it.size) {
                it[i] = 0
            }
        }

        InfoCollector.collect()
        firstShapeHistogram.value = HistogramGetter.fromInfo(histogramInfo[0])
        secondShapeHistogram.value = HistogramGetter.fromInfo(histogramInfo[1])
        thirdShapeHistogram.value = HistogramGetter.fromInfo(histogramInfo[2])
    }

    @Composable
    fun First() {
        firstShapeHistogram.value!!.let {
            Image(
                bitmap = it,
                modifier = (if (it.height > 100 && it.width > 256) Modifier.height(100.dp)
                    .width(256.dp)
                else if (it.height > 100) Modifier.height(100.dp)
                else if (it.width > 256) Modifier.width(256.dp)
                else Modifier).padding(15.dp, 0.dp),
                contentDescription = "firstShapeHistogram",
                contentScale = ContentScale.Crop
            )
        }
    }

    @Composable
    fun Second() {
        secondShapeHistogram.value!!.let {
            Image(
                bitmap = it,
                modifier = (if (it.height > 100 && it.width > 256) Modifier.height(100.dp)
                    .width(256.dp)
                else if (it.height > 100) Modifier.height(100.dp)
                else if (it.width > 256) Modifier.width(256.dp)
                else Modifier).padding(15.dp, 0.dp),
                contentDescription = "secondShapeHistogram",
                contentScale = ContentScale.Crop
            )
        }

    }

    @Composable
    fun Third() {
        thirdShapeHistogram.value!!.let {
            Image(
                bitmap = it,
                modifier = (if (it.height > 100 && it.width > 256) Modifier.height(100.dp)
                    .width(256.dp)
                else if (it.height > 100) Modifier.height(100.dp)
                else if (it.width > 256) Modifier.width(256.dp)
                else Modifier).padding(15.dp, 0.dp),
                contentDescription = "thirdShapeHistogram",
                contentScale = ContentScale.Crop
            )
        }
    }


    @Composable
    fun ShowTool() {
        if (expandedButton.value) {
            HistogramMenu()
        }
    }

    @Composable
    fun ShowHistograms() {
        if (expandedButton.value) {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                ) {
                    AppConfiguration.Component.selected.GetHistograms()
                }
            }

        }
    }

    @Composable
    fun HistogramMenu() {
        Box {
            CustomTextField(
                label = "Ignore Border",
                placeholder = "Your value",
                defaultValue = IgnoreThresholdProportion.toString(),
                onClickFunc = { value -> IgnoreThresholdProportion = value.toFloat() }
            )
        }
        HeaderButton(
            onClick = {
                ContrastFixer.fix(AppConfiguration.Image, histogramInfo, IgnoreThresholdProportion)
                updateInfo()
                AppConfiguration.updateBitmap()
            },
            text = "Correct Image"
        )
    }
}