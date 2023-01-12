package App

import Configurations.AppConfiguration
import Parsers.BytesParser
import androidx.compose.ui.awt.ComposeWindow
import java.awt.FileDialog

fun OpenActivity() {
    val fd = FileDialog(ComposeWindow())
    fd.isVisible = true
    if (fd.files.isNotEmpty()) {
        val file = fd.files[0]
        AppConfiguration.Line.ResetSettings()
        AppConfiguration.Image = BytesParser.ParseBytesForFile(file)!!
    }
}