package App.GammaActivity

import Gammas.GammaModes
import Gammas.GammaPurpose
import androidx.compose.material.*
import androidx.compose.runtime.Composable

@Composable
fun GammaActionsDropdownButton(
    purpose: GammaPurpose,
) {
    DropdownMenu(
        expanded = purpose.Expanded(),
        onDismissRequest = { purpose.Hide() }
    ) {
        DropdownMenuItem(
            onClick = {
                purpose.ApplyMode(GammaModes.Custom)
                purpose.Hide()
            }
        ) { Text(GammaModes.Custom.GetName()) }
        DropdownMenuItem(
            onClick = {
                purpose.ApplyMode(GammaModes.SRGB)
                purpose.Hide()
            }
        ) { Text(GammaModes.SRGB.GetName()) }
    }
}