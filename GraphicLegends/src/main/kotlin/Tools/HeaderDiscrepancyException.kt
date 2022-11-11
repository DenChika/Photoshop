package Tools

class HeaderDiscrepancyException(message: String?) : GraphicLegendsException(message) {
    companion object {
        fun wrongMaxShade() : HeaderDiscrepancyException{
            return HeaderDiscrepancyException("Shade of pixel can't be greater than max shade")
        }

        fun wrongSize() : HeaderDiscrepancyException
        {
            return HeaderDiscrepancyException("Byte array doesn't match width and height")
        }
    }
}