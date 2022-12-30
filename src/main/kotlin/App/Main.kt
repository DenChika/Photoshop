// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import App.HeaderButton
import App.HeaderDropdownButton
import App.TextFieldActivity.GammaTextField
import App.OpenActivity
import App.SaveActivity
import App.TextFieldActivity.CustomTextField
import Configurations.AppConfiguration
import Filtration.FiltrationMode
import Formats.Format
import Gammas.GammaModes
import Gammas.GammaPurpose
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
            Row(Modifier.fillMaxWidth().height(50.dp)) {
                Box {
                    val expandedFilesActivity = remember { mutableStateOf(false) }
                    HeaderButton(
                        onClick = { expandedFilesActivity.value = true },
                        text = "File"
                    )
                    DropdownMenu(
                        expanded = expandedFilesActivity.value,
                        onDismissRequest = { expandedFilesActivity.value = false }
                    ) {
                        DropdownMenuItem(
                            onClick = { OpenActivity(); expandedFilesActivity.value = false }
                        ) { Text("Open") }
                        DropdownMenuItem(
                            onClick = { SaveActivity(); expandedFilesActivity.value = false }
                        ) { Text("Save") }
                    }
                }
                val expandedScaling = remember { mutableStateOf(false) }
                if (AppConfiguration.HasContent()) {
                    Box {
                        val expandedToolsActivity = remember { mutableStateOf(false) }
                        HeaderButton(
                            onClick = { expandedToolsActivity.value = true; AppConfiguration.HideTextFields() },
                            text = "Tools"
                        )
                        DropdownMenu(
                            expanded = expandedToolsActivity.value,
                            onDismissRequest = { expandedToolsActivity.value = false }
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    AppConfiguration.Space.expandedButton.value = true
                                    expandedToolsActivity.value = false
                                }
                            ) { Text("Color Spaces") }
                            if (AppConfiguration.Image.format != Format.P5) {
                                DropdownMenuItem(
                                    onClick = {
                                        AppConfiguration.Component.expandedButton.value = true
                                        expandedToolsActivity.value = false
                                    }
                                ) { Text("Filter channels") }
                            }
                            DropdownMenuItem(
                                onClick = {
                                    AppConfiguration.Gamma.assignExpandedButton.value = true
                                    expandedToolsActivity.value = false
                                }
                            ) { Text("Assign gamma") }
                            DropdownMenuItem(
                                onClick = {
                                    AppConfiguration.Gamma.convertExpandedButton.value = true
                                    expandedToolsActivity.value = false
                                }
                            ) { Text("Convert gamma") }
                            DropdownMenuItem(
                                onClick = {
                                    AppConfiguration.Line.lineSettingsExpandedButton.value = true
                                    expandedToolsActivity.value = false
                                }
                            ) { Text("Line settings") }
                            DropdownMenuItem(
                                onClick = {
                                    AppConfiguration.Dithering.expandedButton.value = true
                                    expandedToolsActivity.value = false
                                }
                            ) { Text("Dithering") }
                            DropdownMenuItem(
                                onClick = {
                                    expandedScaling.value = true
                                    expandedToolsActivity.value = false
                                }
                            ) { Text("Scaling") }
                        }
                    }
                }
                AppConfiguration.Space.ShowTool()
                AppConfiguration.Component.ShowTool()
                if (AppConfiguration.Gamma.assignExpandedButton.value) {
                    AppConfiguration.Gamma.GammaMenu(GammaPurpose.Assign)
                    if (AppConfiguration.Gamma.AssignMode == GammaModes.Custom &&
                        !AppConfiguration.Gamma.assignTextFieldHidden.value
                    ) {
                        Box(
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            GammaTextField(GammaPurpose.Assign)
                        }
                    }
                }
                if (AppConfiguration.Gamma.convertExpandedButton.value) {
                    AppConfiguration.Gamma.GammaMenu(GammaPurpose.Convert)
                    if (AppConfiguration.Gamma.ConvertMode == GammaModes.Custom &&
                        !AppConfiguration.Gamma.convertTextFieldHidden.value
                    ) {
                        Box(
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            GammaTextField(GammaPurpose.Convert)
                        }
                    }
                }
                AppConfiguration.Line.ShowTool()
                AppConfiguration.Dithering.ShowTool()
                if (expandedScaling.value) {
                    val expandedAlgorithm = remember { mutableStateOf(false) }
                    Box {
                        HeaderDropdownButton(
                            onClick = {
                                expandedAlgorithm.value = true
                            },
                            text = "Scaling algorithm"
                        )
                        DropdownMenu(
                            expanded = expandedAlgorithm.value,
                            onDismissRequest = { expandedAlgorithm.value = false }
                        ) {
                            DropdownMenuItem(onClick = {
                                expandedAlgorithm.value = false
                            }) { Text("Nearest neighbour") }
                            DropdownMenuItem(onClick = {
                                expandedAlgorithm.value = false
                            }) { Text("Bilinear") }
                            DropdownMenuItem(onClick = {
                                expandedAlgorithm.value = false
                            }) { Text("Lanczos3") }
                            DropdownMenuItem(onClick = {
                                expandedAlgorithm.value = false
                            }) { Text("BC-splines") }
                        }
                    }
                    CustomTextField(
                        label = "Set width",
                        placeholder = "Your value",
                        defaultValue = AppConfiguration.Image.width.toString(),
                        onClickFunc = {}
                    )
                    CustomTextField(
                        label = "Set height",
                        placeholder = "Your value",
                        defaultValue = AppConfiguration.Image.height.toString(),
                        onClickFunc = {}
                    )
                }
                AppConfiguration.Generation.ImageGeneration()
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

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose for Desktop",
        state = rememberWindowState(width = 700.dp, height = 700.dp)
    ) {
        App()
    }
}