package App.GammaActivity

import Configurations.AppConfiguration
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
                purpose.ShowTextField()
                purpose.Hide()
            }
        ) { Text(GammaModes.Custom.GetName()) }
        DropdownMenuItem(
            onClick = {
                purpose.ApplyMode(GammaModes.SRGB)
                purpose.ShowTextField()
                purpose.Hide()
            }
        ) { Text(GammaModes.SRGB.GetName()) }
    }
}