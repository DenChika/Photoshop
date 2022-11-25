package Filtration

import ColorSpaces.ColorSpaceInstance
import Configurations.AppConfiguration
import Formats.Format
import Parsers.BytesParser

enum class FiltrationMode {
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
            return floatArrayOf(AppConfiguration.Line.FirstShape, 0f, 0f)
        }

        override fun GetFormat(): Format {
            return Format.P5
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
            return floatArrayOf(0f, AppConfiguration.Line.SecondShape, 0f)
        }

        override fun GetFormat(): Format {
            return Format.P5
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
            return floatArrayOf(0f, 0f, AppConfiguration.Line.ThirdShape)
        }

        override fun GetFormat(): Format {
            return Format.P5
        }
    };

    abstract fun GetRGBPixelValues(pixel: ColorSpaceInstance): FloatArray
    abstract fun GetBytes(pixelValue: FloatArray): ByteArray
    abstract fun GetLineColor(): FloatArray
    abstract fun GetFormat(): Format
    fun GetName(): String {
        return this.name
    }
}