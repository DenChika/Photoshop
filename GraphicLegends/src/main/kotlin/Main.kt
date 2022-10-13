// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import Parsers.BytesParser
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.Alignment
import androidx.compose.ui.ComposeScene
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeDialog
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.CompletableDeferred
import java.awt.Dialog
import java.awt.FileDialog
import java.sql.Savepoint
import javax.swing.JWindow

@Composable
fun App() {
    val state = remember { mutableStateOf(false) }
    val image = remember { mutableStateOf(Bitmap.imageFromBuffer(null)) }
    var onResult: CompletableDeferred<Path?>? by mutableStateOf(null)
    MaterialTheme {
        Box(
            modifier = Modifier.background(Color.DarkGray)
        ) {
            Row(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
                Button(
                    modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp),
                    onClick = {
                        val fd = FileDialog(ComposeWindow())
                        fd.isVisible = true
                        if (fd.files.isNotEmpty())
                        {
                            val file = fd.files[0]
                            image.value = BytesParser.ParseFile(file)!!
                            state.value = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color.Green)
                ) {
                    Text("Open")
                }

                Button(
                    onClick = {
                        onResult = CompletableDeferred()
                    },
                    colors = ButtonDefaults.buttonColors(Color.Green)
                ) {
                    Text("Save")
                }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (state.value) {
                    image.let {
                        Card(
                            elevation = 10.dp
                        ) {
                            it.value?.let { it1 ->
                                Image(
                                    bitmap = it1,
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
}
fun main() = application {
    Window(onCloseRequest = ::exitApplication,
    title = "Compose for Desktop",
    state = rememberWindowState(width = 700.dp, height = 700.dp)
    ) {
        App()
    }
}
