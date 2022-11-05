package Converters

class ByteArrayConverter {
    companion object {
        fun BytesToDouble(body: ByteArray) : DoubleArray {
            var doubleBody = doubleArrayOf()
            for (byte in body) {
                doubleBody += (byte.toInt() + 128).toDouble() / 255
            }
            return doubleBody
        }
    }
}