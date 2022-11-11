package Filtration

import ColorSpaces.ColorSpaceInstance
import Configurations.AppConfiguration
import Formats.Format

enum class FiltrationMode {
    ALL {
        override fun GetRGBPixelValues(pixel: ColorSpaceInstance): FloatArray {
            return pixel.GetRGBPixelValue(isFirstNeeded = true, isSecondNeeded = true, isThirdNeeded = true)
        }

        override fun GetBytes(pixel: ColorSpaceInstance): ByteArray {
            return pixel.GetBytes()
        }

        override fun GetFormat(): Format {
            return AppConfiguration.Image.format
        }

        override fun GetName(): String {
            return "All channels"
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
                pixel.GetFirstByte(),
                pixel.GetFirstByte(),
                pixel.GetFirstByte()
            )
        }

        override fun GetFormat(): Format {
            return Format.P5
        }

        override fun GetName(): String {
            return "Only 1st channel"
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
                pixel.GetSecondByte(),
                pixel.GetSecondByte(),
                pixel.GetSecondByte()
            )
        }

        override fun GetFormat(): Format {
            return Format.P5
        }

        override fun GetName(): String {
            return "Only 2nd channel"
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
                pixel.GetThirdByte(),
                pixel.GetThirdByte(),
                pixel.GetThirdByte()
            )
        }

        override fun GetFormat(): Format {
            return Format.P5
        }

        override fun GetName(): String {
            return "Only 3rd channel"
        }
    };

    abstract fun GetRGBPixelValues(pixel: ColorSpaceInstance): FloatArray
    abstract fun GetBytes(pixel: ColorSpaceInstance): ByteArray
    abstract fun GetFormat(): Format
    abstract fun GetName(): String
}