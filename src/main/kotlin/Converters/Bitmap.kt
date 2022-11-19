package Converters

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

class Bitmap() {
    companion object{
        fun imageFromBuffer(buffer: BufferedImage): ImageBitmap {
            return Image.makeFromEncoded(
                toByteArray(buffer)
            ).toComposeImageBitmap()
        }
        fun toByteArray(image: BufferedImage) : ByteArray {
            val baos = ByteArrayOutputStream()
            ImageIO.write(image, "bmp", baos)
            return baos.toByteArray()
        }
    }
}