package App

import Configurations.AppConfiguration
import Parsers.BytesParser
import java.awt.Dialog
import java.awt.FileDialog

fun SaveActivity() {
    if (AppConfiguration.HasContent()) {
        val dialog: Dialog? = null
        val fd = FileDialog(dialog, "Write the name of file", FileDialog.SAVE)
        fd.isVisible = true
        if (fd.files.isNotEmpty()) {
            val file = fd.files[0]
            BytesParser.ParseFileToBytes(
                file.absolutePath,
                AppConfiguration.Image.width,
                AppConfiguration.Image.height,
                AppConfiguration.Image.maxShade,
                AppConfiguration.Image.getPixels()
            )
        }
    }
}