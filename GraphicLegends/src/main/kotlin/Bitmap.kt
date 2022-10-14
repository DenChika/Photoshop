import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skiko.toBitmap
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

class Bitmap() {
    companion object{
        fun imageFromBuffer(buffer: BufferedImage?): ImageBitmap? {
            if (buffer != null) {
                return org.jetbrains.skia.Image.makeFromEncoded(
                    toByteArray(buffer)).toComposeImageBitmap()
            }
            return null
        }

        private fun toByteArray(bitmap: BufferedImage) : ByteArray {
            val baos = ByteArrayOutputStream()
            ImageIO.write(bitmap, "bmp", baos)
            return baos.toByteArray()
        }
    }
}