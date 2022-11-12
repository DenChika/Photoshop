package Tools

class ColorSpaceException(message: String?) : GraphicLegendsException(message) {
    companion object{
        fun undefined() : ColorSpaceException{
            return ColorSpaceException("Undefined color space is given")
        }
        fun shadeIsNan() : ColorSpaceException{
            return ColorSpaceException("Shade must be a number")
        }
        fun wrongHSVFormat() : ColorSpaceException{
            return wrongFormat("HSV")
        }
        fun wrongHSLFormat() : ColorSpaceException{
            return wrongFormat("HSL")
        }
        private fun wrongFormat(formatName : String) : ColorSpaceException{
            return ColorSpaceException("Error. Wrong $formatName format.")
        }
    }
}