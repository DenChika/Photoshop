// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import App.TextFieldActivity.GammaTextField
import App.HeaderButton
import App.HeaderDropdownButton
import App.OpenActivity
import App.SaveActivity
import App.TextFieldActivity.CustomTextField
import App.TextFieldActivity.LineSettingsTextField
import Configurations.AppConfiguration
import Formats.Format
import Gammas.GammaModes
import Gammas.GammaPurpose
import LinePainterHelpers.LineSettings
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
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
                    Button(
                        modifier = Modifier.padding(start = 15.dp),
                        onClick = { expandedFilesActivity.value = true },
                        colors = ButtonDefaults.buttonColors(Color.Green)
                    ) {Text("File")}
                    DropdownMenu(
                        expanded = expandedFilesActivity.value,
                        onDismissRequest = { expandedFilesActivity.value = false }
                    ) {
                        DropdownMenuItem(
                            onClick = { OpenActivity(); expandedFilesActivity.value = false }
                        ) {Text("Open")}
                        DropdownMenuItem(
                            onClick = { SaveActivity(); expandedFilesActivity.value = false }
                        ) {Text("Save")}
                    }
                }
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

                    AppConfiguration.Gamma.GammaMenu(GammaPurpose.Assign)
                    if (AppConfiguration.Gamma.AssignMode == GammaModes.Custom &&
                        !AppConfiguration.Gamma.assignTextFieldHidden.value) {
                        Box (
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ){
                            GammaTextField(
                                GammaPurpose.Assign,
                                "Assign Gamma",
                                "Your gamma")
                        }
                    }

                    AppConfiguration.Gamma.GammaMenu(GammaPurpose.Convert)
                    if (AppConfiguration.Gamma.ConvertMode == GammaModes.Custom &&
                        !AppConfiguration.Gamma.convertTextFieldHidden.value) {
                        Box (
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ){
                            GammaTextField(
                                GammaPurpose.Convert,
                                "Convert Gamma",
                                "Your gamma")
                        }
                    }

                    Box {
                        AppConfiguration.Line.DropdownLineSettings()
                    }
                    if (AppConfiguration.Line.colorExpanded.value) {
                        AppConfiguration.Component.selected.GetLineColorTextFields()
                    }

                    if (AppConfiguration.Line.saturationExpanded.value) {
                        LineSettingsTextField(
                            settings = LineSettings.Saturation,
                            label = "Saturation",
                            placeholder = "Your value"
                        )
                    }
                    if (AppConfiguration.Line.thicknessExpanded.value) {
                        LineSettingsTextField(
                            settings = LineSettings.Thickness,
                            label = "Thickness",
                            placeholder = "Your value"
                        )
                    }
                    val isBits = remember { mutableStateOf(false) }
                    Box {
                        val expanded = remember { mutableStateOf(false) }
                        HeaderDropdownButton(
                            onClick = {
                                expanded.value = true
                                if (isBits.value) isBits.value = false
                            },
                            text = "Dithering"
                        )
                        DropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false }
                        ) {
                            Box {
                                val expandedAlgorithms = remember { mutableStateOf(false) }
                                Button(
                                    onClick = { expandedAlgorithms.value = true },
                                    colors = ButtonDefaults.buttonColors(Color.White),
                                    border = BorderStroke(0.dp, Color.White)
                                ) {
                                    Text("Algorithm")
                                }
                                DropdownMenu(
                                    expanded = expandedAlgorithms.value,
                                    onDismissRequest = { expandedAlgorithms.value = false }
                                ) {
                                    DropdownMenuItem(onClick = {
                                        expandedAlgorithms.value = false
                                    }) { Text("Ordered (8x8)") }
                                    DropdownMenuItem(onClick = {
                                        expandedAlgorithms.value = false
                                    }) { Text("Random") }
                                    DropdownMenuItem(onClick = {
                                        expandedAlgorithms.value = false
                                    }) { Text("Floyd-Steinberg") }
                                    DropdownMenuItem(onClick = {
                                        expandedAlgorithms.value = false
                                    }) { Text("Atkinson") }
                                }
                            }
                            DropdownMenuItem(onClick = {
                                expanded.value = false
                                isBits.value = true
                            }) { Text("Bits") }
                        }
                    }
                    if (isBits.value) {
                        CustomTextField(
                            label = "Bits Dithering",
                            placeholder = "Your value",
                            defaultValue = "8.0"
                        )
                    }
                    val isGenerate = remember { mutableStateOf(false) }
                    HeaderButton(
                        onClick = { isGenerate.value = !isGenerate.value },
                        text = "Generate"
                    )
                    if (isGenerate.value) {
                        CustomTextField(
                            label = "Set width",
                            placeholder = "Your value",
                            defaultValue = "100"
                        )
                        CustomTextField(
                            label = "Set height",
                            placeholder = "Your value",
                            defaultValue = "100"
                        )
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