// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import Parsers.BytesParser
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    val count = remember { mutableStateOf(0) }
    MaterialTheme {
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    count.value++
                }) {
                Text(if (count.value == 0) "Hello World" else "Clicked ${count.value}!")
            }
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    count.value = 0
                }) {
                Text("Reset")
            }
        }
    }
}

fun main() = application {
    /*Window(onCloseRequest = ::exitApplication,
    title = "Compose for Desktop",
    state = rememberWindowState(width = 300.dp, height = 300.dp)
    ) {
        App()
    }*/
    val file = BytesParser("src\\main\\kotlin\\sample_640x426.pgm",
        "src\\main\\kotlin\\Storage\\test.bmp")
    file.ParseFile()
}
