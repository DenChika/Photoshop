package HistogramSupport

import Configurations.AppConfiguration

class InfoCollector {
    companion object {
        fun collect() {
            for (y in 0 until AppConfiguration.Image.height) {
                for (x in 0 until AppConfiguration.Image.width) {
                    val pixel = AppConfiguration.Image.getPixel(x, y)
                    AppConfiguration.Histogram.histogramInfo[0][(pixel.firstShade * 255).toInt()]++
                    AppConfiguration.Histogram.histogramInfo[1][(pixel.secondShade * 255).toInt()]++
                    AppConfiguration.Histogram.histogramInfo[2][(pixel.thirdShade * 255).toInt()]++
                }
            }
        }
    }
}