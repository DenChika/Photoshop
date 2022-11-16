package App

import Configurations.AppConfiguration
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HeadingDropdownButton(
    onClick: () -> Unit,
    text: String = ""
) {
    Button(
        modifier = Modifier.padding(start = 15.dp),
        onClick = { onClick.invoke() },
        colors = ButtonDefaults.buttonColors(Color.Green)
    ) {
        Text(text)
        Icon(
            Icons.Default.ArrowDropDown,
            contentDescription = "",
            modifier = Modifier.width(20.dp).height(20.dp)
        )
    }
}