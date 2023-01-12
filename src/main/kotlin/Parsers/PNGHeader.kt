package Parsers

class PNGHeader(
    private val width: Int,
    private val height: Int,
    private val bitDepth: Int,
    private val colorType: Int
) {

    fun getWidth(): Int {
        return width
    }

    fun getHeight(): Int {
        return height
    }

    fun getBitDepth(): Int {
        return bitDepth
    }

    fun getColorType(): Int {
        return colorType
    }
}