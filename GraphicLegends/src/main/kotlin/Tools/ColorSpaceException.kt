package Tools

class ColorSpaceException(message: String?) : GraphicLegendsException(message) {
    companion object{
        fun undefined() : ColorSpaceException{
            return ColorSpaceException("Undefined color space is given")
        }
        fun shadeIsNan() : ColorSpaceException{
            return ColorSpaceException("Shade must be a number")
        }
        fun wrongFormat(formatName : String) : ColorSpaceException{
            return ColorSpaceException("Error. Wrong $formatName format.")
        }
    }
}