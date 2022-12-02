package App.TextFieldActivity

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
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.key.utf16CodePoint
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(
    label: String = "",
    placeholder: String = "",
    defaultValue: String = "",
) {
    val text = remember { mutableStateOf(defaultValue) }

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
                    true
                }
                false
            },
        label = { Text(text = label, fontSize = 10.sp) },
        placeholder = { Text(text = placeholder, fontSize = 13.sp) },
        trailingIcon = {
            IconButton(onClick = {
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
            },
        ),
        isError = false,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.White,
            errorBorderColor = Color.Red,
            errorLabelColor = Color.Red,
            errorCursorColor = Color.Red
        )
    )
}