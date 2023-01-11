package Filtration

import ColorSpaces.ColorSpaceInstance
import Dithering.AtkinsonAlgorithm
import Dithering.FloydSteinbergAlgorithm
import Dithering.OrderedAlgorithm
import Dithering.RandomAlgorithm

enum class FiltrationAlgorithm {
    Threshold {
        override fun Apply(pixels: Array<ColorSpaceInstance>) {
            ThresholdAlgorithm().Apply(pixels)
        }
    },
    OtsuThreshold {
        override fun Apply(pixels: Array<ColorSpaceInstance>) {
            OtsuThresholdAlgorithm().Apply(pixels)
        }
    },
    Median {
        override fun Apply(pixels: Array<ColorSpaceInstance>) {
            MedianAlgorithm().Apply(pixels)
        }
    },
    Gaussian {
        override fun Apply(pixels: Array<ColorSpaceInstance>) {
            GaussianAlgorithm().Apply(pixels)
        }
    },
    BoxBlur {
        override fun Apply(pixels: Array<ColorSpaceInstance>) {
            BoxBlurAlgorithm().Apply(pixels)
        }
    },
    SobelFilter {
        override fun Apply(pixels: Array<ColorSpaceInstance>) {
            SobelFilterAlgorithm().Apply(pixels)
        }
    },
    ContrastAdaptiveSharpening {
        override fun Apply(pixels: Array<ColorSpaceInstance>) {
            ContrastAdaptiveSharpeningAlgorithm().Apply(pixels)
        }
    };

    abstract fun Apply(pixels : Array<ColorSpaceInstance>)
}