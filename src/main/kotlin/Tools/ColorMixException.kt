package Tools

class ColorMixException(message: String?) : GraphicLegendsException(message) {
    companion object {
        fun invalidSaturation(value: Float) : ColorMixException{
            return ColorMixException("Color saturation is between 0 and 1, not $value")
        }
    }
}