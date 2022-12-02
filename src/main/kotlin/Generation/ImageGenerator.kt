package Generation

import ColorSpaces.ColorSpace
import Configurations.ImageConfiguration
import Formats.Format

class ImageGenerator {
    companion object {
        fun Gradient(width : Int, height : Int) : ImageConfiguration{
            val pixels = Array(height * width) { ColorSpace.RGB.GetDefault()}
            val step : Float = 1f / width
            for (posY in 0 until height) {
                    for (posX in 0 until width) {
                        pixels[posY * width + posX].firstShade = posX * step
                        pixels[posY * width + posX].secondShade = posX * step
                        pixels[posY * width + posX].thirdShade = posX * step
                    }
            }

            return ImageConfiguration(Format.P5, width, height, 255, pixels)
        }
    }
}