package Tools

class ColorSpaceException(message: String?) : GraphicLegendsException(message) {
    companion object{
        fun undefined() : ColorSpaceException{
            return ColorSpaceException("Undefined color space is given")
        }
    }
}