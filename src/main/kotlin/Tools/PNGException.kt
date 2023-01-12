package Tools

class PNGException(message: String?) : GraphicLegendsException(message) {
    companion object {
        fun wrongFormat(message: String = "") : PNGException {
            return PNGException("Wrong PNG format. $message.")
        }

        fun unsupported(message: String = "") : PNGException {
            return PNGException("Unsupported feature: $message.")
        }
    }
}