package Dithering

import ColorSpaces.ColorSpaceInstance

enum class DitheringAlgorithm {
    Ordered {
        override fun Use(pixels: Array<ColorSpaceInstance>, shapeBitesCount: Int) {
            OrderedAlgorithm().Apply(pixels, shapeBitesCount)
        }

    },
    Random {
        override fun Use(pixels: Array<ColorSpaceInstance>, shapeBitesCount: Int) {
            RandomAlgorithm().Apply(pixels, shapeBitesCount)
        }

    },
    FloydSteinberg {
        override fun Use(pixels: Array<ColorSpaceInstance>, shapeBitesCount : Int) {
            FloydSteinbergAlgorithm().Apply(pixels, shapeBitesCount)
        }

    },
    Atkinson {
        override fun Use(pixels: Array<ColorSpaceInstance>, shapeBitesCount: Int) {
            AtkinsonAlgorithm().Apply(pixels, shapeBitesCount)
        }

    };

    abstract fun Use(pixels : Array<ColorSpaceInstance>, shapeBitesCount: Int)
}