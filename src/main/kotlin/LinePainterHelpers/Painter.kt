package LinePainterHelpers

import ColorSpaces.ColorSpaceInstance
import Configurations.LineConfiguration
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.round
import kotlin.math.sqrt

class Painter {
    companion object {
        fun distanceLinePoint(line: LineConfiguration, x: Int, y: Int): Float {
            val start = line.Start
            val end = line.End
            val sqrt = sqrt((start.x - end.x) * (start.x - end.x) - (start.y - end.y) * (start.y - end.y))

            return abs((end.x - start.x) * (start.y - y) - (start.x - x) * (end.y - start.y)) / sqrt
        }

        fun drawLine(pixels: Array<ColorSpaceInstance>, width: Int, line: LineConfiguration): Array<FloatArray> {
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
                    val newColor = ColorMixer.Mix(
                        pixels[plotY * width + x].GetFloatArrayOfValues(), line.GetColor(),
                        line.MaxSaturation * min(1.0f, ((line.Thickness + 0.5f) / 2.0f - abs(y - plotY)))
                    )

                    pixels[plotY * width + x].UpdateValues(newColor)
                }

                y += grad
            }

            for (plotX in (round(start.x) - line.Thickness / 2).toInt()..(round(start.x) - 1).toInt()) {
                y = start.y + grad * (plotX - start.x)

                for (plotY in (y - (line.Thickness - 1) / 2.0).toInt()..(y - (line.Thickness - 1) / 2.0 + line.Thickness).toInt()) {
                    val newColor = ColorMixer.Mix(
                        pixels[plotY * width + plotX].GetFloatArrayOfValues(), line.GetColor(),
                        line.MaxSaturation * min(
                            1.0f,
                            ((line.Thickness + 0.5f) / 2.0f - distanceLinePoint(line, plotX, plotY))
                        )
                    )

                    pixels[plotY * width + plotX].UpdateValues(newColor)
                }
            }

            for (plotX in (round(end.x) + 1).toInt()..(round(end.x) + line.Thickness / 2).toInt()) {
                y = start.y + grad * (plotX - start.x)

                for (plotY in (y - (line.Thickness - 1) / 2.0).toInt()..(y - (line.Thickness - 1) / 2.0 + line.Thickness).toInt()) {
                    val newColor = ColorMixer.Mix(
                        pixels[plotY * width + plotX].GetFloatArrayOfValues(), line.GetColor(),
                        line.MaxSaturation * min(
                            1.0f,
                            ((line.Thickness + 0.5f) / 2.0f - distanceLinePoint(line, plotX, plotY))
                        )
                    )

                    pixels[plotY * width + plotX].UpdateValues(newColor)
                }
            }

            return Array<FloatArray>(0) { floatArrayOf() }
        }
    }
}