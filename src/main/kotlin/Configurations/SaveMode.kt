package Configurations

import Parsers.BytesParser
import java.io.File

enum class SaveMode {
    Current {
        override fun InFile(file: File) {
            BytesParser.ParseFileToBytes(
                file.absolutePath,
                AppConfiguration.Image.width,
                AppConfiguration.Image.height,
                AppConfiguration.Image.maxShade,
                AppConfiguration.Image.getPixels()
            )
        }
    },
    Dithered {
        override fun InFile(file: File) {
            BytesParser.ParseFileToBytes(
                file.absolutePath,
                AppConfiguration.Image.width,
                AppConfiguration.Image.height,
                AppConfiguration.Image.maxShade,
                AppConfiguration.Image.getDitheredPixels()
            )
        }

    };
    abstract fun InFile(file: File)
}