package App.TextFieldActivity

import Configurations.AppConfiguration
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
fun GammaTextField(purpose: GammaPurpose) {
    val openDialog = remember { mutableStateOf(false) }

    fun submitGamma(value: String) {
        try {
            if (value.toFloat() < 0f) {
                throw GraphicLegendsException("Error. Gamma has to be non-negative.")
            }

            if (value.toFloat() == 0f) {
                purpose.ApplyMode(GammaModes.SRGB)
                purpose.Hide()
            } else {
                purpose.ChangeCustomValue(value.toFloat())
            }
        } catch (e: Exception) {
            openDialog.value = true
        }
    }

    CustomTextField(
        label = "${purpose.GetName()} Gamma",
        placeholder = "Your Value",
        defaultValue = purpose.GetCustomValue().toString(),
        onClickFunc = { value -> submitGamma(value); purpose.HideTextField() }
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
