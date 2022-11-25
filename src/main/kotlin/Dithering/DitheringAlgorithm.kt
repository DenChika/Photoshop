package Dithering

import ColorSpaces.ColorSpaceInstance

enum class DitheringAlgorithm {
    Ordered {
        override fun Use(pixels: Array<ColorSpaceInstance>, shapeBitesCount: Int): Array<ColorSpaceInstance> {
            return OrderedAlgorithm().Apply(pixels, shapeBitesCount)
        }

    },
    Random {
        override fun Use(pixels: Array<ColorSpaceInstance>, shapeBitesCount: Int): Array<ColorSpaceInstance> {
            return RandomAlgorithm().Apply(pixels, shapeBitesCount)
        }

    },
    FloydSteinberg {
        override fun Use(pixels: Array<ColorSpaceInstance>, shapeBitesCount : Int): Array<ColorSpaceInstance> {
            return FloydSteinbergAlgorithm().Apply(pixels, shapeBitesCount)
        }

    },
    Atkinson {
        override fun Use(pixels: Array<ColorSpaceInstance>, shapeBitesCount: Int): Array<ColorSpaceInstance> {
            return AtkinsonAlgorithm().Apply(pixels, shapeBitesCount)
        }

    };

    abstract fun Use(pixels : Array<ColorSpaceInstance>, shapeBitesCount: Int) : Array<ColorSpaceInstance>
}