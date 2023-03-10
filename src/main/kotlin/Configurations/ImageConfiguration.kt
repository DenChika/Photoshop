package Configurations

import ColorSpaces.ColorSpace
import ColorSpaces.ColorSpaceInstance
import Converters.Bitmap
import Formats.Format
import LinePainterHelpers.OffsetCounter
import Gammas.GammaPurpose
import LinePainterHelpers.Painter
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import java.awt.image.BufferedImage

class ImageConfiguration(
    _format: Format,
    _width: Int,
    _height: Int,
    _maxShade: Int,
    _pixels: Array<ColorSpaceInstance>
) {
    val format: Format = _format
    val width = _width
    val height = _height
    val maxShade = _maxShade
    private var pixels = _pixels

    constructor() : this(Format.P6, 0, 0, 0, arrayOf())

    fun getImageBitmap(): ImageBitmap {
        val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        for (posY in 0 until height) {
            for (posX in 0 until width) {
                val pixel = AppConfiguration.Gamma.AssignMode.Apply(
                    AppConfiguration.Component.selected.GetRGBPixelValues(pixels[posY * width + posX]), GammaPurpose.Assign)
                bufferedImage.setRGB(
                    posX,
                    posY,
                    Color((pixel[0] * 255).toInt(), (pixel[1] * 255).toInt(), (pixel[2] * 255).toInt()).toArgb()
                )
            }
        }
        return Bitmap.imageFromBuffer(bufferedImage)
    }

    fun getPixels(): Array<ColorSpaceInstance> {
        return pixels
    }

    fun getPixel(x : Int, y : Int) : ColorSpaceInstance {
        return pixels[x + y * width]
    }

    fun setPixel(x : Int, y : Int, value: FloatArray) {
        pixels[x + y * width].UpdateValues(value)
    }

    fun changeColorSpace(colorSpace: ColorSpace) {
        for (pixel in pixels) {
            pixel.Kind = colorSpace
        }
    }

    fun useDithering() {
        AppConfiguration.Dithering.selected.Use(pixels, AppConfiguration.Dithering.ShadeBitesCount)
    }

    fun useFiltration() {
        AppConfiguration.Filtration.selected.Apply(pixels)
    }
    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun ImageView() {
        AppConfiguration.GetBitmap().let {
            Image(
                bitmap = it,
                modifier = (if (it.height > 900 && it.width > 1500) Modifier.height(700.dp)
                    .width(1500.dp)
                else if (it.height > 900) Modifier.height(700.dp)
                else if (it.width > 1500) Modifier.width(1500.dp)
                else Modifier)
                    .onPointerEvent(PointerEventType.Press) {
                        if (AppConfiguration.Line.lineSettingsExpandedButton.value) {
                            if (!AppConfiguration.Line.IsPainting)
                            {
                                val position = OffsetCounter.getActualOffset(it.changes.first().position)
                                if (OffsetCounter.checkOffSetValidity(position)) {
                                    AppConfiguration.Line.Start = position
                                    AppConfiguration.Line.IsPainting = true
                                }
                            }
                        }
                        if (AppConfiguration.Scaling.expandedButton.value) {
                            var position = OffsetCounter.getActualOffset(it.changes.first().position)
                            if (OffsetCounter.checkOffSetValidity(position)) {
                                position = Offset(
                                    position.x - width.toFloat() / 2,
                                    position.y - height.toFloat() / 2
                                )
                                AppConfiguration.Scaling.Center = position
                            }
                        }
                    }
                    .onPointerEvent(PointerEventType.Release) {
                        if (AppConfiguration.Line.lineSettingsExpandedButton.value) {
                            if (AppConfiguration.Line.IsPainting)
                            {
                                val position = OffsetCounter.getActualOffset(it.changes.first().position)
                                AppConfiguration.Line.IsPainting = false
                                if (OffsetCounter.checkOffSetValidity(position)) {
                                    AppConfiguration.Line.End = position
                                    Painter.drawLine(pixels, AppConfiguration.Line)
                                    AppConfiguration.updateBitmap()
                                }
                            }
                        }
                    },
                contentDescription = "image",
                contentScale = ContentScale.Crop
            )
        }
    }
}