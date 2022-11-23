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

        override fun GetBytes(pixel: ColorSpaceInstance): ByteArray {
            return byteArrayOf(
                BytesParser.GetByteValueFromShade(pixel.firstShade),
                BytesParser.GetByteValueFromShade(pixel.secondShade),
                BytesParser.GetByteValueFromShade(pixel.thirdShade)
            )
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

        override fun GetBytes(pixel: ColorSpaceInstance): ByteArray {
            return byteArrayOf(
                BytesParser.GetByteValueFromShade(pixel.firstShade),
                BytesParser.GetByteValueFromShade(pixel.firstShade),
                BytesParser.GetByteValueFromShade(pixel.firstShade)
            )
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

        override fun GetBytes(pixel: ColorSpaceInstance): ByteArray {
            return byteArrayOf(
                BytesParser.GetByteValueFromShade(pixel.secondShade),
                BytesParser.GetByteValueFromShade(pixel.secondShade),
                BytesParser.GetByteValueFromShade(pixel.secondShade)
            )
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

        override fun GetBytes(pixel: ColorSpaceInstance): ByteArray {
            return byteArrayOf(
                BytesParser.GetByteValueFromShade(pixel.thirdShade),
                BytesParser.GetByteValueFromShade(pixel.thirdShade),
                BytesParser.GetByteValueFromShade(pixel.thirdShade)
            )
        }

        override fun GetFormat(): Format {
            return Format.P5
        }
    };

    abstract fun GetRGBPixelValues(pixel: ColorSpaceInstance): FloatArray
    abstract fun GetBytes(pixel: ColorSpaceInstance): ByteArray
    abstract fun GetFormat(): Format
    fun GetName(): String {
        return this.name
    }
}