package Dithering

import ColorSpaces.ColorSpaceInstance
import Configurations.AppConfiguration
import Interfaces.IDitheringAlgorithm

class AtkinsonAlgorithm : IDitheringAlgorithm {
    override fun Apply(pixels: Array<ColorSpaceInstance>, shapeBitsCount: Int) {
        val width = AppConfiguration.Image.width
        val height = AppConfiguration.Image.height
        for (y in 0 until AppConfiguration.Image.height) {
            for (x in 0 until AppConfiguration.Image.width) {
                val (oldR: Float, oldG: Float, oldB: Float) = pixels[y * width + x].GetFloatArrayOfValues()
                val (newR: Float, newG: Float, newB: Float) = DitheringSubsidiaryFunctions.findClosestPaletteColor(oldR, oldG, oldB, shapeBitsCount)

                val errorR = oldR - newR
                val errorG = oldG - newG
                val errorB = oldB - newB

                pixels[y * width + x].UpdateValues(floatArrayOf( newR, newG, newB))

                var nextX = x + 1
                var nextY = y
                DitheringSubsidiaryFunctions.applyError(pixels, nextX, nextY, width, height, floatArrayOf(errorR * 1 / 8, errorG * 1 / 8, errorB * 1 / 8))

                nextX = x - 1
                nextY = y + 1
                DitheringSubsidiaryFunctions.applyError(pixels, nextX, nextY, width, height, floatArrayOf(errorR * 1 / 8, errorG * 1 / 8, errorB * 1 / 8))

                nextX = x
                nextY = y + 1
                DitheringSubsidiaryFunctions.applyError(pixels, nextX, nextY, width, height, floatArrayOf(errorR * 1 / 8, errorG * 1 / 8, errorB * 1 / 8))

                nextX = x + 1
                nextY = y + 1
                DitheringSubsidiaryFunctions.applyError(pixels, nextX, nextY, width, height, floatArrayOf(errorR * 1 / 8, errorG * 1 / 8, errorB * 1 / 8))

                nextX = x + 2
                nextY = y
                DitheringSubsidiaryFunctions.applyError(pixels, nextX, nextY, width, height, floatArrayOf(errorR * 1 / 8, errorG * 1 / 8, errorB * 1 / 8))

                nextX = x
                nextY = y + 2
                DitheringSubsidiaryFunctions.applyError(pixels, nextX, nextY, width, height, floatArrayOf(errorR * 1 / 8, errorG * 1 / 8, errorB * 1 / 8))
            }
        }
    }
}