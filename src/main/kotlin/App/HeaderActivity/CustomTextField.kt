package App.HeaderActivity

import Configurations.GammaConfiguration
import Gammas.GammaModes
import Gammas.GammaPurpose
import Tools.GraphicLegendsException
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomTextField(
    purpose: GammaPurpose,
    label: String = "",
    placeholder: String = ""
) {
    val text = remember { mutableStateOf(purpose.GetCustomValue().toString()) }
    val openDialog = remember { mutableStateOf(false) }

    fun submitGamma() {
        try {
            if (text.value.toFloat() < 0f) {
                throw GraphicLegendsException("Error. Gamma has to be non-negative.")
            }

            if (text.value.toFloat() == 0f) {
                purpose.ApplyMode(GammaModes.SRGB)
                purpose.Hide()
            } else {
                purpose.ChangeCustomValue(text.value.toFloat())
            }
        } catch (e: Exception) {
            openDialog.value = true
            text.value = purpose.GetCustomValue().toString()
        }
    }

    TextField(
        value = text.value,
        onValueChange = {
            text.value = it
        },
        singleLine = true,
        modifier =
        Modifier
            .padding(start = 15.dp)
            .width(150.dp)
            .onKeyEvent {
                if (it.type == KeyEventType.KeyUp && it.utf16CodePoint == 10) {
                    submitGamma()
                    true
                }

                false
            },
        label = { Text(text = label, fontSize = 10.sp) },
        placeholder = { Text(text = placeholder, fontSize = 13.sp) },
        trailingIcon = {
            IconButton(onClick = {
                submitGamma()
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
                submitGamma()
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

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            modifier = Modifier
                .width(300.dp)
                .height(200.dp),
            title = { Text(text = "Gamma parsing error") },
            text = { Text("Gamma has to be a non-negative real number.") },
            buttons = {
                Button(
                    modifier = Modifier.padding(start = 20.dp),
                    onClick = { openDialog.value = false },
                ) {
                    Text("OK", fontSize = 22.sp)
                }
            }
        )
    }
}
