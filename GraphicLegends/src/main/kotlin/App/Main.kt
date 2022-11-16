// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import App.HeadingButton
import ColorSpaces.ColorSpace
import Configurations.AppConfiguration
import Filtration.FiltrationMode
import Formats.Format
import Parsers.BytesParser
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dialog
import java.awt.FileDialog
import java.awt.FileDialog.SAVE
import java.io.File

@Composable
fun App() {
    val appBackgroundPic = File("GraphicLegends/src/main/kotlin/Resources/app_background.jpg")

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
                HeadingButton(
                    onClick = {
                        val fd = FileDialog(ComposeWindow())
                        fd.isVisible = true
                        if (fd.files.isNotEmpty()) {
                            val file = fd.files[0]
                            AppConfiguration.Image = BytesParser.ParseBytesForFile(file)!!
                        }
                    },
                    text = "Open"
                )

                HeadingButton(
                    onClick = {
                        if (AppConfiguration.HasContent()) {
                            val dialog: Dialog? = null
                            val fd = FileDialog(dialog, "Write the name of file", SAVE)
                            fd.isVisible = true
                            if (fd.files.isNotEmpty()) {
                                val file = fd.files[0]
                                BytesParser.ParseFileToBytes(
                                    file.absolutePath,
                                    AppConfiguration.Image.width,
                                    AppConfiguration.Image.height,
                                    AppConfiguration.Image.maxShade
                                )
                            }
                        }
                    },
                    text = "Save"
                )
                if (AppConfiguration.HasContent()){
                    Box {
                        Button(
                            modifier = Modifier.padding(start = 15.dp),
                            onClick = {
                                AppConfiguration.Space.expanded.value = true
                            },
                            colors = ButtonDefaults.buttonColors(Color.Green)
                        ) {
                            Text(text = AppConfiguration.Space.selected.GetName())
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = "",
                                modifier = Modifier.width(20.dp).height(20.dp)
                            )
                        }
                        DropdownMenu(
                            expanded = AppConfiguration.Space.expanded.value,
                            onDismissRequest = { AppConfiguration.Space.expanded.value = false }
                        ) {
                            DropdownMenuItem(onClick = {
                                AppConfiguration.Space.selected = ColorSpace.RGB
                                AppConfiguration.Space.expanded.value = false
                            }) { Text(ColorSpace.RGB.GetName()) }
                            DropdownMenuItem(onClick = {
                                AppConfiguration.Space.selected = ColorSpace.CMY
                                AppConfiguration.Space.expanded.value = false
                            }) { Text(ColorSpace.CMY.GetName()) }
                            DropdownMenuItem(onClick = {
                                AppConfiguration.Space.selected = ColorSpace.HSL
                                AppConfiguration.Space.expanded.value = false
                            }) { Text(ColorSpace.HSL.GetName()) }
                            DropdownMenuItem(onClick = {
                                AppConfiguration.Space.selected = ColorSpace.HSV
                                AppConfiguration.Space.expanded.value = false
                            }) { Text(ColorSpace.HSV.GetName()) }
                            DropdownMenuItem(onClick = {
                                AppConfiguration.Space.selected = ColorSpace.YCbCr601
                                AppConfiguration.Space.expanded.value = false
                            }) { Text(ColorSpace.YCbCr601.GetName()) }
                            DropdownMenuItem(onClick = {
                                AppConfiguration.Space.selected = ColorSpace.YCbCr709
                                AppConfiguration.Space.expanded.value = false
                            }) { Text(ColorSpace.YCbCr709.GetName()) }
                            DropdownMenuItem(onClick = {
                                AppConfiguration.Space.selected = ColorSpace.YCoCg
                                AppConfiguration.Space.expanded.value = false
                            }) { Text(ColorSpace.YCoCg.GetName()) }
                        }
                    }

                    if (AppConfiguration.Image.format != Format.P5) {
                        Box {
                            Button(
                                modifier = Modifier.padding(start = 15.dp),
                                onClick = {
                                    AppConfiguration.Component.expanded.value = true
                                },
                                colors = ButtonDefaults.buttonColors(Color.Green)
                            ) {
                                Text(text = AppConfiguration.Component.selected.GetName())
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = "",
                                    modifier = Modifier.width(20.dp).height(20.dp)
                                )
                            }
                            DropdownMenu(
                                expanded = AppConfiguration.Component.expanded.value,
                                onDismissRequest = { AppConfiguration.Component.expanded.value = false }
                            ) {
                                DropdownMenuItem(onClick = {
                                    AppConfiguration.Component.selected = FiltrationMode.ALL
                                    AppConfiguration.Component.expanded.value = false
                                    AppConfiguration.Component
                                }) { Text(FiltrationMode.ALL.GetName()) }
                                DropdownMenuItem(onClick = {
                                    AppConfiguration.Component.selected = FiltrationMode.OnlyFirst
                                    AppConfiguration.Component.expanded.value = false
                                }) { Text(FiltrationMode.OnlyFirst.GetName()) }
                                DropdownMenuItem(onClick = {
                                    AppConfiguration.Component.selected = FiltrationMode.OnlySecond
                                    AppConfiguration.Component.expanded.value = false
                                }) { Text(FiltrationMode.OnlySecond.GetName()) }
                                DropdownMenuItem(onClick = {
                                    AppConfiguration.Component.selected = FiltrationMode.OnlyThird
                                    AppConfiguration.Component.expanded.value = false
                                }) { Text(FiltrationMode.OnlyThird.GetName()) }
                            }
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
                        AppConfiguration.GetBitmap().let {
                            Image(
                                bitmap = it,
                                modifier = if (it.height > 900 && it.width > 1500) Modifier.height(700.dp)
                                    .width(1500.dp)
                                else if (it.height > 900) Modifier.height(700.dp)
                                else if (it.width > 1500) Modifier.width(1500.dp)
                                else Modifier,
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
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose for Desktop",
        state = rememberWindowState(width = 700.dp, height = 700.dp)
    ) {
        App()
    }
}