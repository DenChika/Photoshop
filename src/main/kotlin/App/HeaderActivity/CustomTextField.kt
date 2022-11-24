package App.HeaderActivity

import Configurations.GammaConfiguration
import Tools.GraphicLegendsException
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.lang.Exception

@Composable
fun CustomTextField(
    currentGamma: GammaConfiguration,
    label: String = "",
    placeholder: String = ""
) {
    val text = remember { mutableStateOf(currentGamma.AssignCustomValue.toString()) }
    TextField(
        value = text.value,
        onValueChange = {
            text.value = it
        },
        singleLine = true,
        modifier =
        Modifier
            .padding(start = 15.dp)
            .onKeyEvent {
                if (it.type == KeyEventType.KeyUp && it.utf16CodePoint == 10) {
                    try {
                        currentGamma.AssignCustomValue = text.value.toFloat()
                    } catch (e: Exception) {
                        throw GraphicLegendsException("Error occurred while parsing new gamma value from the text field.")
                    }

                    true
                }
                false
            },
        label = { Text(text = label, fontSize = 10.sp) },
        placeholder = { Text(text = placeholder, fontSize = 13.sp) },
        trailingIcon = {
            IconButton(onClick = {
                println(text.value)

                try {
                    currentGamma.AssignCustomValue = text.value.toFloat()
                } catch (e: Exception) {
                    throw GraphicLegendsException("Error occurred while parsing new gamma value from the text field.")
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = ""
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                println(text.value)
                try {
                    currentGamma.AssignCustomValue = text.value.toFloat()
                } catch (e: Exception) {
                    throw GraphicLegendsException("Error occurred while parsing new gamma value from the text field.")
                }
            },
        ),
        isError = text.value.toFloatOrNull() == null,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.White,
            errorBorderColor = Color.Red,
            errorLabelColor = Color.Red,
            errorCursorColor = Color.Red
        )
    )
}

fun TextField(
    value: String,
    onValueChange: () -> Unit,
    singleLine: Boolean,
    modifier: Modifier,
    onKeyEvent: Modifier,
    label: () -> Unit,
    placeholder: () -> Unit,
    trailingIcon: () -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    isError: Boolean,
    colors: TextFieldColors
) {

}
