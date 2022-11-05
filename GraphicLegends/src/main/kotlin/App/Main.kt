// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import Configurations.AppConfiguration
import Parsers.BytesParser
import Tools.GraphicLegendsException
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dialog
import java.awt.FileDialog
import java.awt.FileDialog.SAVE

@Composable
fun App() {
    MaterialTheme {
        Box(
            modifier = Modifier.background(Color.DarkGray)
        ) {
            Row(Modifier.fillMaxSize(), Arrangement.spacedBy(15.dp)) {
                Button(
                    modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp),
                    onClick = {
                        val fd = FileDialog(ComposeWindow())
                        fd.isVisible = true
                        if (fd.files.isNotEmpty())
                        {
                            val file = fd.files[0]
                            try {
                                AppConfiguration.Image = BytesParser.ParseBytesForFile(file)!!
                            }
                            catch (e: GraphicLegendsException)
                            {
                                println(e.message)
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color.Green)
                ) {
                    Text("Open")
                }

                Button(
                    onClick = {
                        if (AppConfiguration.HasContent()) {
                            val dialog : Dialog? = null
                            val fd = FileDialog(dialog, "Write the name of file", SAVE)
                            fd.isVisible = true
                            if (fd.files.isNotEmpty())
                            {
                                val file = fd.files[0]
                                BytesParser.ParseFileInBytes(
                                    file.absolutePath,
                                    AppConfiguration.Image.width,
                                    AppConfiguration.Image.height,
                                    AppConfiguration.Image.maxShade,
                                    AppConfiguration.Image.getByteArray())
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color.Green)
                ) {
                    Text( "Save")
                }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (AppConfiguration.HasContent()) {
                    Card(
                        elevation = 10.dp
                    ) {
                        AppConfiguration.Image.getImageBitmap().let {
                            Image(
                                bitmap = it,
                                contentDescription = "image",
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication,
        title = "Compose for Desktop",
        state = rememberWindowState(width = 700.dp, height = 700.dp)
    ) {
        App()
    }
}