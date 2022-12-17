package Interfaces

import ColorSpaces.ColorSpaceInstance

interface IDitheringAlgorithm {
    fun Apply(pixels : Array<ColorSpaceInstance>, shapeBitsCount : Int)
}