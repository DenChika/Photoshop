// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import App.HeaderButton
import App.HeaderDropdownButton
import App.OpenActivity
import App.SaveActivity
import App.TextFieldActivity.CustomTextField
import App.TextFieldActivity.GammaTextField
import Configurations.AppConfiguration
import Formats.Format
import Gammas.GammaModes
import Gammas.GammaPurpose
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
                        onClick = { expandedFilesActivity.value = true; AppConfiguration.HideButtons() },
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
                if (AppConfiguration.HasContent()) {
                    Box {
                        val expandedToolsActivity = remember { mutableStateOf(false) }
                        HeaderButton(
                            onClick = { expandedToolsActivity.value = true; AppConfiguration.HideButtons() },
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
                                    AppConfiguration.Scaling.expandedButton.value = true
                                    expandedToolsActivity.value = false
                                }
                            ) { Text("Scaling") }
                            DropdownMenuItem(
                                onClick = {
                                    AppConfiguration.Filtration.expandedButton.value = true
                                    expandedToolsActivity.value = false
                                }
                            ) { Text("Filtration") }
                            DropdownMenuItem(
                                onClick = {
                                    AppConfiguration.Histogram.updateInfo()
                                    AppConfiguration.Histogram.expandedButton.value = true
                                    expandedToolsActivity.value = false
                                }
                            ) { Text("Histograms") }
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
                AppConfiguration.Scaling.ShowTool()
                AppConfiguration.Filtration.ShowTool()
                AppConfiguration.Histogram.ShowTool()
                AppConfiguration.Generation.ImageGeneration()
            }
            Box(
                modifier = Modifier.fillMaxWidth().height(800.dp),
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
            AppConfiguration.Histogram.ShowHistograms()
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose for Desktop",
        state = rememberWindowState(width = 1600.dp, height = 900.dp)
    ) {
        App()
    }
}