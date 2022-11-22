package App.HeaderActivity

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(
    defaultValue: Float = 1.0F,
    label: String = "",
    placeholder: String = ""
) {
    val text = remember { mutableStateOf(defaultValue.toString()) }
    TextField(
        value = text.value,
        onValueChange = {
            text.value = it
        },
        singleLine = true,
        modifier = Modifier.padding(start = 15.dp),
        label = { Text(text = label, fontSize = 10.sp) },
        placeholder = { Text(text = placeholder, fontSize = 13.sp) },
        trailingIcon = {
            IconButton(onClick = { println(text.value) }) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = ""
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(
            onSend = {},
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