package App

import Configurations.AppConfiguration
import java.awt.Dialog
import java.awt.FileDialog

fun SaveActivity() {
    if (AppConfiguration.HasContent()) {
        val dialog: Dialog? = null
        val fd = FileDialog(dialog, "Write the name of file", FileDialog.SAVE)
        fd.isVisible = true
        if (fd.files.isNotEmpty()) {
            val file = fd.files[0]
            AppConfiguration.SaveScript.InFile(file)
        }
    }
}