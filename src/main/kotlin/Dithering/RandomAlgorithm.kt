package Dithering

import ColorSpaces.ColorSpaceInstance
import Configurations.AppConfiguration
import Interfaces.IDitheringAlgorithm
import kotlin.random.Random

class RandomAlgorithm : IDitheringAlgorithm {
    override fun Apply(pixels: Array<ColorSpaceInstance>, shapeBitsCount : Int) {
        val width = AppConfiguration.Image.width
        val height = AppConfiguration.Image.height

        val random = Random
        for (y in 0 until AppConfiguration.Image.height) {
            for (x in 0 until AppConfiguration.Image.width) {
                val (oldR: Float, oldG: Float, oldB: Float) = pixels[y * width + x].GetFloatArrayOfValues()

                val nextRandom = random.nextInt(256)

                if (nextRandom <= oldR * 255) {
                    pixels[y * width + x].UpdateValues(floatArrayOf(1.0f, 1.0f, 1.0f))
                } else {
                    pixels[y * width + x].UpdateValues(floatArrayOf(0.0f, 0.0f, 0.0f))
                }
            }
        }
    }
}