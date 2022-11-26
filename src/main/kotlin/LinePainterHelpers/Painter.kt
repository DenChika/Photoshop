package LinePainterHelpers

import ColorSpaces.ColorSpaceInstance
import Configurations.AppConfiguration
import Configurations.LineConfiguration
import androidx.compose.ui.geometry.Offset
import kotlin.math.*

class Painter {
    companion object {
        fun distanceLinePoint(start: Offset, end: Offset, x: Int, y: Int): Float {
            val sqrt = sqrt((start.x - end.x) * (start.x - end.x) + (start.y - end.y) * (start.y - end.y))

            var distance = abs((end.x - start.x) * (start.y - y) - (start.x - x) * (end.y - start.y)) / sqrt

            if (distance.isNaN()) {
                distance = 0f
            }

            return distance
        }

        fun drawLine(pixels: Array<ColorSpaceInstance>, line: LineConfiguration) {
            var start = line.Start
            var end = line.End

            var length = AppConfiguration.Image.width

            val steep = abs(end.y - start.y) > abs(end.x - start.x)
            if (steep) {
                start = Offset(start.y, start.x)
                end = Offset(end.y, end.x)
            }

            if (end.x < start.x) {
                start = end.also { end = start }
            }

            val dx = end.x - start.x
            val dy = end.y - start.y

            var grad = dy / dx

            if (grad.isNaN()) {
                grad = 0f
            }

            for (x in round(start.x).toInt()..round(end.x).toInt()) {
                for (plotX in ((x - line.Thickness) / 1.0f).toInt()..((x + line.Thickness) / 1.0f).toInt()) {
                    val y = start.y + grad * (plotX - start.x)

                    for (plotY in ((y - line.Thickness) / 1.0f).toInt()..((y + line.Thickness + 1f) / 1.0f).toInt()) {
                        if (steep) {
                            val newColor = ColorMixer.Mix(
                                pixels[plotX * length + plotY].GetFloatArrayOfValues(), line.GetColor(),
                                line.MaxSaturation * max(0f, min(1.0f, line.Thickness / 10f / distanceLinePoint(start, end, plotX, plotY))))

                            pixels[plotX * length + plotY].UpdateValues(newColor)
                        } else {
                            val newColor = ColorMixer.Mix(
                                pixels[plotY * length + plotX].GetFloatArrayOfValues(), line.GetColor(),
                                line.MaxSaturation * max(0f, min(1.0f, line.Thickness / 10f / distanceLinePoint(start, end, plotX, plotY))))
                            pixels[plotY * length + plotX].UpdateValues(newColor)
                        }
                    }
                }
            }
        }
    }
}