// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import Configurations.AppConfiguration
import Parsers.BytesParser
import Tools.GraphicLegendsException
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
    val app_back_file = File("src\\main\\kotlin\\Resources\\app_background.jpg")
    val background: ImageBitmap = remember(app_back_file) {
        loadImageBitmap(app_back_file.inputStream())
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
                Button(
                    modifier = Modifier.padding(start = 15.dp),
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
                    modifier = Modifier.padding(start = 15.dp),
                    onClick = {
                        if (AppConfiguration.HasContent()) {
                            val dialog : Dialog? = null
                            val fd = FileDialog(dialog, "Write the name of file", SAVE)
                            fd.isVisible = true
                            if (fd.files.isNotEmpty())
                            {
                                val file = fd.files[0]
                                BytesParser.ParseFileToBytes(
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

                Box {
                    Button(
                        modifier = Modifier.padding(start = 15.dp),
                        onClick = {
                            AppConfiguration.Space.expanded.value = true
                        },
                        colors = ButtonDefaults.buttonColors(Color.Green)
                    ) {
                        Text(text = AppConfiguration.Space.selected.value)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "", modifier = Modifier.width(20.dp).height(20.dp))
                    }
                    DropdownMenu(
                        expanded = AppConfiguration.Space.expanded.value,
                        onDismissRequest = { AppConfiguration.Space.expanded.value = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            AppConfiguration.Space.selected.value = "RGB"
                            AppConfiguration.Space.expanded.value = false }) { Text("RGB") }
                        DropdownMenuItem(onClick = {
                            AppConfiguration.Space.selected.value = "CMY"
                            AppConfiguration.Space.expanded.value = false }) { Text("CMY") }
                        DropdownMenuItem(onClick = {
                            AppConfiguration.Space.selected.value = "HSL"
                            AppConfiguration.Space.expanded.value = false }) { Text("HSL") }
                        DropdownMenuItem(onClick = {
                            AppConfiguration.Space.selected.value = "HSV"
                            AppConfiguration.Space.expanded.value = false }) { Text("HSV") }
                        DropdownMenuItem(onClick = {
                            AppConfiguration.Space.selected.value = "YCbCr_601"
                            AppConfiguration.Space.expanded.value = false }) { Text("YCbCr_601") }
                        DropdownMenuItem(onClick = {
                            AppConfiguration.Space.selected.value = "YCbCr_709"
                            AppConfiguration.Space.expanded.value = false }) { Text("YCbCr_709") }
                        DropdownMenuItem(onClick = {
                            AppConfiguration.Space.selected.value = "YCoCg"
                            AppConfiguration.Space.expanded.value = false }) { Text("YCoCg") }
                    }
                }

                Box {
                    Button(
                        modifier = Modifier.padding(start = 15.dp),
                        onClick = {
                            AppConfiguration.Component.expanded.value = true
                        },
                        colors = ButtonDefaults.buttonColors(Color.Green)
                    ) {
                        Text(text = AppConfiguration.Component.selected.value)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "", modifier = Modifier.width(20.dp).height(20.dp))
                    }
                    DropdownMenu(
                        expanded = AppConfiguration.Component.expanded.value,
                        onDismissRequest = { AppConfiguration.Component.expanded.value = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            AppConfiguration.Component.selected.value = "Without filtration"
                            AppConfiguration.Component.expanded.value = false }) { Text("Without filtration") }
                        DropdownMenuItem(onClick = {
                            AppConfiguration.Component.selected.value = "1st channel"
                            AppConfiguration.Component.expanded.value = false }) { Text("1st channel") }
                        DropdownMenuItem(onClick = {
                            AppConfiguration.Component.selected.value = "2nd channel"
                            AppConfiguration.Component.expanded.value = false }) { Text("2nd channel") }
                        DropdownMenuItem(onClick = {
                            AppConfiguration.Component.selected.value = "3rd channel"
                            AppConfiguration.Component.expanded.value = false }) { Text("3rd channel") }
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