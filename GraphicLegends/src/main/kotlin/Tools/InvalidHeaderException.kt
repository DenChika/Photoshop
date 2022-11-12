package Tools

class InvalidHeaderException(message: String?) : GraphicLegendsException(message) {
    companion object {
        fun invalidWidthFormat() : InvalidHeaderException{
            return invalidFormat("width")
        }
        fun invalidHeightFormat() : InvalidHeaderException{
            return invalidFormat("height")
        }
        fun invalidMaxShadeFormat() : InvalidHeaderException{
            return invalidFormat("max shade")
        }
        fun widthInvalidValue(value: Int) : InvalidHeaderException{
            return invalidValue("Width", value)
        }
        fun heightInvalidValue(value: Int) : InvalidHeaderException{
            return invalidValue("Height", value)
        }
        fun maxShadeInvalidValue(value: Int) : InvalidHeaderException{
            return invalidValue("Max shade", value)
        }
        private fun invalidFormat(name : String) : InvalidHeaderException{
            return InvalidHeaderException("Invalid format for $name")
        }
        private fun invalidValue(name : String, value: Int) : InvalidHeaderException{
            return InvalidHeaderException("$name can't be equal to this value ($value)")
        }
    }
}