package App.TextFieldActivity

import Generation.GenerationFieldPurpose
import androidx.compose.runtime.Composable

@Composable
fun GenerationTextField(purpose: GenerationFieldPurpose) {
    CustomTextField(
        label = "Set ${purpose.GetName()}",
        placeholder = "Your value",
        defaultValue = purpose.GetCurrent(),
        onClickFunc = purpose.onClickEvent()
    )
}
