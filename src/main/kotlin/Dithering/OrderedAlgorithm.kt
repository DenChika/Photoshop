package Dithering

import ColorSpaces.ColorSpaceInstance
import Configurations.AppConfiguration
import Interfaces.IDitheringAlgorithm

class OrderedAlgorithm : IDitheringAlgorithm {
    override fun Apply(pixels: Array<ColorSpaceInstance>, shapeBitsCount: Int) {
        val thresholdMap = floatArrayOf(
            0f, 32f,  8f, 40f,  2f, 34f, 10f, 42f,
            48f, 16f, 56f, 24f, 50f, 18f, 58f, 26f,
            12f, 44f,  4f, 36f, 14f, 46f,  6f, 38f,
            60f, 28f, 52f, 20f, 62f, 30f, 54f, 22f,
            3f, 35f, 11f, 43f,  1f, 33f,  9f, 41f,
            51f, 19f, 59f, 27f, 49f, 17f, 57f, 25f,
            15f, 47f,  7f, 39f, 13f, 45f,  5f, 37f,
            63f, 31f, 55f, 23f, 61f, 29f, 53f, 21f
        )

        val width = AppConfiguration.Image.width
        val r = 1f / shapeBitsCount

        for (y in 0 until AppConfiguration.Image.height) {
            for (x in 0 until AppConfiguration.Image.width) {
                val (oldR: Float, oldG: Float, oldB: Float) = pixels[y * width + x].GetFloatArrayOfValues()

                val mapX = x % 8
                val mapY = y % 8

                val norm = 0.5f
                val matrixCoefficient = 64

                val midR = oldR + r * (thresholdMap[mapY * 8 + mapX] / matrixCoefficient - norm)
                val midG = oldG + r * (thresholdMap[mapY * 8 + mapX] / matrixCoefficient - norm)
                val midB = oldB + r * (thresholdMap[mapY * 8 + mapX] / matrixCoefficient - norm)

                val newColor = DitheringSubsidiaryFunctions.findClosestPaletteColor(midR, midG, midB, shapeBitsCount)

                pixels[y * width + x].UpdateValues(newColor)
            }
        }
    }
}