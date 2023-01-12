package Tools

class FormatException(message: String?) : GraphicLegendsException(message) {
    companion object {
        fun undefined() : FormatException{
            return FormatException("File format is not P5, P6 or PNG")
        }
    }
}