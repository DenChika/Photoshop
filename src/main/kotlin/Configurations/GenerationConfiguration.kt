package Configurations

import App.HeaderButton
import App.TextFieldActivity.GenerationTextField
import Generation.GenerationFieldPurpose
import Generation.ImageGenerator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf

class GenerationConfiguration {
    private val _textFieldsVisibility = mutableStateOf(false)
    private val _width = mutableStateOf(500)
    private val _height = mutableStateOf(500)

    var TextFieldsVisibility = mutableStateOf(_textFieldsVisibility.value)
    var width : Int
        get() {
            return _width.value
        }
        set(value) {
            _width.value = value
            AppConfiguration.Image = ImageGenerator.Gradient(width, height)
        }
    var height : Int
        get() {
            return _height.value
        }
        set(value) {
            _height.value = value
            AppConfiguration.Image = ImageGenerator.Gradient(width, height)
        }

    @Composable
    fun ImageGeneration() {
        HeaderButton(
            onClick = {
                TextFieldsVisibility.value = true
                AppConfiguration.Image = ImageGenerator.Gradient(width, height)
            },
            text = "Generate"
        )
        if (TextFieldsVisibility.value) {
            GenerationTextField(GenerationFieldPurpose.Width)
            GenerationTextField(GenerationFieldPurpose.Height)
        }
    }
}