package Configurations

import App.Components.ComponentMode
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap

class AppConfiguration() {
    val imageConfiguration = mutableStateOf(ImageConfiguration())
    val spaceConfiguration = mutableStateOf(SpaceConfiguration())
    val componentConfiguration = mutableStateOf(ComponentConfiguration())
    val gammaConfiguration = mutableStateOf(GammaConfiguration())
    val lineConfiguration = mutableStateOf(LineConfiguration())
    val ditheringConfiguration = mutableStateOf(DitheringConfiguration())
    val generationConfiguration = mutableStateOf(GenerationConfiguration())
    val scalingConfiguration = mutableStateOf(ScalingConfiguration())
    val filtrationConfiguration = mutableStateOf(FiltrationConfiguration())
    val histogramConfiguration = mutableStateOf(HistogramConfiguration())
    val bitmap : MutableState<ImageBitmap?> = mutableStateOf(null)
    val hasContent =  mutableStateOf(false)

    companion object{
        private var configuration : AppConfiguration = AppConfiguration()
        var Image : ImageConfiguration
            get() {
                return configuration.imageConfiguration.value
            }
            set(value)
            {
                configuration.hasContent.value = true
                configuration.imageConfiguration.value = value
                Component.selected = ComponentMode.ALL
                updateBitmap()
            }
        var Space : SpaceConfiguration = configuration.spaceConfiguration.value
        var Component : ComponentConfiguration = configuration.componentConfiguration.value
        var Gamma : GammaConfiguration = configuration.gammaConfiguration.value
        var Line : LineConfiguration = configuration.lineConfiguration.value
        var Dithering : DitheringConfiguration = configuration.ditheringConfiguration.value
        var Generation : GenerationConfiguration = configuration.generationConfiguration.value
        var Filtration : FiltrationConfiguration = configuration.filtrationConfiguration.value
        var Scaling : ScalingConfiguration = configuration.scalingConfiguration.value
        var Histogram : HistogramConfiguration = configuration.histogramConfiguration.value
        fun HasContent() : Boolean {
            return configuration.hasContent.value
        }
        fun GetBitmap() : ImageBitmap
        {
            return configuration.bitmap.value!!
        }

        fun updateBitmap(){
            configuration.bitmap.value = Image.getImageBitmap()
        }

        fun HideButtons() {
            Space.expandedButton.value = false
            Component.expandedButton.value = false
            Gamma.assignExpandedButton.value = false
            Gamma.convertExpandedButton.value = false
            Line.lineSettingsExpandedButton.value = false
            Dithering.expandedButton.value = false
            Scaling.expandedButton.value = false
            Histogram.expandedButton.value = false
        }
    }

}