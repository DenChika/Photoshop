package App

import Configurations.AppConfiguration
import Gammas.GammaModes
import Parsers.BytesParser
import androidx.compose.ui.awt.ComposeWindow
import java.awt.FileDialog

fun OpenActivity() {
    val fd = FileDialog(ComposeWindow())
    fd.isVisible = true
    if (fd.files.isNotEmpty()) {
        val file = fd.files[0]
        AppConfiguration.Image = BytesParser.ParseBytesForFile(file)!!
        AppConfiguration.Gamma.ResetSettings()
        AppConfiguration.Line.ResetSettings()
    }
}