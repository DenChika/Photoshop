package Configurations

import ColorSpaces.ColorSpace
import ColorSpaces.ColorSpaceInstance
import Converters.Bitmap
import Converters.GammaConverter
import Formats.Format
import LinePainterHelpers.ColorMixer
import LinePainterHelpers.OffsetCounter
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import java.awt.image.BufferedImage
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.round
import kotlin.math.sqrt

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
                    AppConfiguration.Component.selected.GetRGBPixelValues(pixels[posY * width + posX]))
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

    fun changeColorSpace(colorSpace: ColorSpace) {
        for (pixel in pixels) {
            pixel.Kind = colorSpace
        }
    }

    fun changeGamma(newGamma: Float) {
        for (pixel in pixels) {
            pixel.UpdateValues(
                GammaConverter.ConvertToSave(
                    pixel.GetFloatArrayOfValues(),
                    AppConfiguration.Gamma.ConvertValue,
                    newGamma
                )
            )
        }
    }

    fun distanceLinePoint(line: LineConfiguration, x: Int, y: Int): Float {
        val start = line.Start
        val end = line.End
        val sqrt = sqrt((start.x - end.x) * (start.x - end.x) - (start.y - end.y) * (start.y - end.y))

        return abs((end.x - start.x) * (start.y - y) - (start.x - x) * (end.y - start.y)) / sqrt
    }

    fun drawLine(line: LineConfiguration) {

        var start = line.Start;
        var end = line.End;
        if (end.x < start.x) {
            start = line.End
            end = line.Start
        }

        val dx = end.x - start.x
        val dy = end.y - end.y
        val grad = dy / dx

        var y = start.y + grad * (round(start.x) - start.x)

        for (x in round(start.x).toInt()..round(end.x).toInt()) {
            for (plotY in (y - (line.Thickness - 1) / 2).toInt()..(y - (line.Thickness - 1) / 2 + line.Thickness).toInt()) {
                val newColor = ColorMixer.Mix(pixels[plotY * width + x].GetFloatArrayOfValues(), line.GetColor(),
                    line.MaxSaturation * min(1.0f, ((line.Thickness + 0.5f) / 2.0f - abs(y - plotY))))

                pixels[plotY * width + x].UpdateValues(newColor)
            }

            y += grad
        }

        for (plotX in (round(start.x) - line.Thickness / 2).toInt()..(round(start.x) - 1).toInt()) {
            y = start.y + grad * (plotX - start.x)

            for (plotY in (y - (line.Thickness - 1) / 2.0).toInt()..(y - (line.Thickness - 1) / 2.0 + line.Thickness).toInt()) {
                val newColor = ColorMixer.Mix(pixels[plotY * width + plotX].GetFloatArrayOfValues(), line.GetColor(),
                    line.MaxSaturation * min(1.0f, ((line.Thickness + 0.5f) / 2.0f - distanceLinePoint(line, plotX, plotY))))

                pixels[plotY * width + plotX].UpdateValues(newColor)
            }
        }

        for (plotX in (round(end.x) + 1).toInt()..(round(end.x) + line.Thickness / 2).toInt()) {
            y = start.y + grad * (plotX - start.x)

            for (plotY in (y - (line.Thickness - 1) / 2.0).toInt()..(y - (line.Thickness - 1) / 2.0 + line.Thickness).toInt()) {
                val newColor = ColorMixer.Mix(pixels[plotY * width + plotX].GetFloatArrayOfValues(), line.GetColor(),
                    line.MaxSaturation * min(1.0f, ((line.Thickness + 0.5f) / 2.0f - distanceLinePoint(line, plotX, plotY))))

                pixels[plotY * width + plotX].UpdateValues(newColor)
            }
        }
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
                        if (!AppConfiguration.Line.IsPainting())
                        {
                            val position = OffsetCounter.getActualOffset(it.changes.first().position)
                            if (OffsetCounter.checkOffSetValidity(position)) {
                                AppConfiguration.Line.Start = position
                                println(position)
                            }
                        }
                    }
                    .onPointerEvent(PointerEventType.Release) {
                        if (AppConfiguration.Line.IsPainting())
                        {
                            val position = OffsetCounter.getActualOffset(it.changes.first().position)
                            if (OffsetCounter.checkOffSetValidity(position)) {
                                AppConfiguration.Line.End = position
                                println(position)
                                drawLine(AppConfiguration.Line)
                            }
                        }
                    },
                contentDescription = "image",
                contentScale = ContentScale.Crop
            )
        }
    }
}