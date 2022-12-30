package App

import Configurations.AppConfiguration
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HeaderButton(
    onClick: () -> Unit,
    text: String = ""
) {
    Button(
        modifier = Modifier.padding(start = 15.dp),
        onClick = { onClick.invoke() },
        colors = ButtonDefaults.buttonColors(Color.Green)
    ) {
        Text(text)
    }
}