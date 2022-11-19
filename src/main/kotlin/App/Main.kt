// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import App.HeaderButton
import App.HeaderDropdownButton
import App.OpenActivity
import App.SaveActivity
import Configurations.AppConfiguration
import Formats.Format
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.io.File

@Composable
fun App() {
    val appBackgroundPic = File("src/main/kotlin/Resources/app_background.jpg")

    val background: ImageBitmap = remember(appBackgroundPic) {
        loadImageBitmap(appBackgroundPic.inputStream())
    }
    MaterialTheme {
        Box(
            modifier = Modifier.background(Color.DarkGray)
        ) {
            Image(
                painter = BitmapPainter(image = background),
                contentDescription = "image",
                contentScale = ContentScale.Crop
            )
            Row(Modifier.fillMaxSize()) {
                HeaderButton(
                    onClick = {
                        OpenActivity()
                    },
                    text = "Open"
                )

                HeaderButton(
                    onClick = {
                        SaveActivity()
                    },
                    text = "Save"
                )
                if (AppConfiguration.HasContent()){
                    Box {
                        HeaderDropdownButton(
                            onClick = {
                                AppConfiguration.Space.expanded.value = true
                            },
                            text = AppConfiguration.Space.selected.GetName()
                        )
                        AppConfiguration.Space.DropdownSpaces()
                    }

                    if (AppConfiguration.Image.format != Format.P5) {
                        Box {
                            HeaderDropdownButton(
                                onClick = {
                                    AppConfiguration.Component.expanded.value = true
                                },
                                text = AppConfiguration.Component.selected.GetName()
                            )
                            AppConfiguration.Component.DropdownComponents()
                        }
                    }
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
                        AppConfiguration.Image.ImageView()
                    }
                }
            }
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose for Desktop",
        state = rememberWindowState(width = 700.dp, height = 700.dp)
    ) {
        App()
    }
}