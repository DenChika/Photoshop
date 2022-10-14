package Converters

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

class Bitmap() {
    companion object{
        fun imageFromBuffer(buffer: BufferedImage?): ImageBitmap? {
            if (buffer != null) {
                return Image.makeFromEncoded(
                    toByteArray(buffer)
                ).toComposeImageBitmap()
            }
            return null
        }
        fun toByteArray(image: BufferedImage?) : ByteArray? {
            if (image != null) {
                val baos = ByteArrayOutputStream()
                ImageIO.write(image, "bmp", baos)
                return baos.toByteArray()
            }
            return null
        }
    }
}