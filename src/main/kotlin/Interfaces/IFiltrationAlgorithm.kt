package Interfaces

import ColorSpaces.ColorSpaceInstance

interface IFiltrationAlgorithm {
    fun Apply(pixels : Array<ColorSpaceInstance>)
}