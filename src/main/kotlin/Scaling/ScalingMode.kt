package Scaling

import ColorSpaces.ColorSpaceInstance
import Configurations.ImageConfiguration
import androidx.compose.ui.geometry.Offset

enum class ScalingMode {
    NearestNeighbor {
        override fun Scale(
            image: ImageConfiguration,
            zoomPoint: Offset,
            newWidth: Int,
            newHeight: Int
        ): ImageConfiguration {
            TODO("Not yet implemented")
        }

    },
    Bilinear {
        override fun Scale(
            image: ImageConfiguration,
            zoomPoint: Offset,
            newWidth: Int,
            newHeight: Int
        ): ImageConfiguration {
            TODO("Not yet implemented")
        }
    },
    Lanczos3 {
        override fun Scale(
            image: ImageConfiguration,
            zoomPoint: Offset,
            newWidth: Int,
            newHeight: Int
        ): ImageConfiguration {
            TODO("Not yet implemented")
        }

    },
    BCSplines {
        override fun Scale(
            image: ImageConfiguration,
            zoomPoint: Offset,
            newWidth: Int,
            newHeight: Int
        ): ImageConfiguration {
            // Тут будет вызов метода из скелийлинга
            TODO("Not yet implemented")
        }

    };
    abstract fun Scale(
        image : ImageConfiguration,
        zoomPoint : Offset,
        newWidth: Int,
        newHeight: Int
    ) : ImageConfiguration
    fun GetNeededPixels(
        image: ImageConfiguration,
        neededWidth : Int,
        neededHeight : Int,
        zoomPoint: Offset
    ) : Array<ColorSpaceInstance> {
        TODO("Not yet implemented")
    }
    fun GetNeededOriginalWidth() : Int {
        TODO("Not yet implemented")
    }
    fun GetNeededOriginalHeight() : Int {
        TODO("Not yet implemented")
    }

}