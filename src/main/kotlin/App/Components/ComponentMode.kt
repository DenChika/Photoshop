package App.Components

import ColorSpaces.ColorSpaceInstance
import Configurations.AppConfiguration
import Formats.Format
import Parsers.BytesParser
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable

enum class ComponentMode {
    ALL {
        override fun GetRGBPixelValues(pixel: ColorSpaceInstance): FloatArray {
            return pixel.GetRGBPixelValue(isFirstNeeded = true, isSecondNeeded = true, isThirdNeeded = true)
        }

        override fun GetBytes(pixelValue: FloatArray): ByteArray {
            return byteArrayOf(
                BytesParser.GetByteValueFromShade(pixelValue[0]),
                BytesParser.GetByteValueFromShade(pixelValue[1]),
                BytesParser.GetByteValueFromShade(pixelValue[2])
            )
        }

        override fun GetLineColor(): FloatArray {
            return AppConfiguration.Line.GetColor()
        }

        override fun GetFormat(): Format {
            return AppConfiguration.Image.format
        }

        @Composable
        override fun GetLineColorTextFields() {
            Row {
                AppConfiguration.Line.GetFirstShadeTextField()
                AppConfiguration.Line.GetSecondShadeTextField()
                AppConfiguration.Line.GetThirdShadeTextField()
            }
        }

        @Composable
        override fun GetHistograms() {
            AppConfiguration.Histogram.First()
            AppConfiguration.Histogram.Second()
            AppConfiguration.Histogram.Third()
        }
    },
    OnlyFirst {
        override fun GetRGBPixelValues(pixel: ColorSpaceInstance): FloatArray {
            return pixel.GetRGBPixelValue(
                isFirstNeeded = true,
                isSecondNeeded = false,
                isThirdNeeded = false
            )
        }

        override fun GetBytes(pixelValue: FloatArray): ByteArray {
            return byteArrayOf(
                BytesParser.GetByteValueFromShade(pixelValue[0]),
                BytesParser.GetByteValueFromShade(pixelValue[0]),
                BytesParser.GetByteValueFromShade(pixelValue[0])
            )
        }

        override fun GetLineColor(): FloatArray {
            return floatArrayOf(AppConfiguration.Line.FirstShade, 0f, 0f)
        }

        override fun GetFormat(): Format {
            return Format.P5
        }

        @Composable
        override fun GetLineColorTextFields() {
            AppConfiguration.Line.GetFirstShadeTextField()
        }

        @Composable
        override fun GetHistograms() {
            AppConfiguration.Histogram.First()
        }
    },
    OnlySecond {
        override fun GetRGBPixelValues(pixel: ColorSpaceInstance): FloatArray {
            return pixel.GetRGBPixelValue(
                isFirstNeeded = false,
                isSecondNeeded = true,
                isThirdNeeded = false
            )
        }

        override fun GetBytes(pixelValue: FloatArray): ByteArray {
            return byteArrayOf(
                BytesParser.GetByteValueFromShade(pixelValue[1]),
                BytesParser.GetByteValueFromShade(pixelValue[1]),
                BytesParser.GetByteValueFromShade(pixelValue[1])
            )
        }

        override fun GetLineColor(): FloatArray {
            return floatArrayOf(0f, AppConfiguration.Line.SecondShade, 0f)
        }

        override fun GetFormat(): Format {
            return Format.P5
        }

        @Composable
        override fun GetLineColorTextFields() {
            AppConfiguration.Line.GetSecondShadeTextField()
        }

        @Composable
        override fun GetHistograms() {
            AppConfiguration.Histogram.Second()
        }
    },
    OnlyThird {
        override fun GetRGBPixelValues(pixel: ColorSpaceInstance): FloatArray {
            return pixel.GetRGBPixelValue(
                isFirstNeeded = false,
                isSecondNeeded = false,
                isThirdNeeded = true
            )
        }

        override fun GetBytes(pixelValue: FloatArray): ByteArray {
            return byteArrayOf(
                BytesParser.GetByteValueFromShade(pixelValue[2]),
                BytesParser.GetByteValueFromShade(pixelValue[2]),
                BytesParser.GetByteValueFromShade(pixelValue[2])
            )
        }

        override fun GetLineColor(): FloatArray {
            return floatArrayOf(0f, 0f, AppConfiguration.Line.ThirdShade)
        }

        override fun GetFormat(): Format {
            return Format.P5
        }

        @Composable
        override fun GetLineColorTextFields() {
            AppConfiguration.Line.GetThirdShadeTextField()
        }

        @Composable
        override fun GetHistograms() {
            AppConfiguration.Histogram.Third()
        }
    };

    abstract fun GetRGBPixelValues(pixel: ColorSpaceInstance): FloatArray
    abstract fun GetBytes(pixelValue: FloatArray): ByteArray
    abstract fun GetLineColor(): FloatArray
    abstract fun GetFormat(): Format
    @Composable
    abstract fun GetLineColorTextFields()
    @Composable
    abstract fun GetHistograms()
    fun GetName(): String {
        return this.name
    }
}