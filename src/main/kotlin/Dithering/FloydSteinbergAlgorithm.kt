package Dithering

import ColorSpaces.ColorSpaceInstance
import Configurations.AppConfiguration
import Interfaces.IDitheringAlgorithm

class FloydSteinbergAlgorithm : IDitheringAlgorithm {
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
                DitheringSubsidiaryFunctions.applyError(pixels, nextX, nextY, width, height, floatArrayOf(errorR * 7 / 16, errorG * 7 / 16, errorB * 7 / 16))

                nextX = x - 1
                nextY = y + 1
                DitheringSubsidiaryFunctions.applyError(pixels, nextX, nextY, width, height, floatArrayOf(errorR * 3 / 16, errorG * 3 / 16, errorB * 3 / 16))

                nextX = x
                nextY = y + 1
                DitheringSubsidiaryFunctions.applyError(pixels, nextX, nextY, width, height, floatArrayOf(errorR * 5 / 16, errorG * 5 / 16, errorB * 5 / 16))

                nextX = x + 1
                nextY = y + 1
                DitheringSubsidiaryFunctions.applyError(pixels, nextX, nextY, width, height, floatArrayOf(errorR * 1 / 16, errorG * 1 / 16, errorB * 1 / 16))
            }
        }
    }
}