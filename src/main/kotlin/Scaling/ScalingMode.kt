package Scaling

import Configurations.AppConfiguration
import Configurations.ImageConfiguration

enum class ScalingMode {
    NearestNeighbor {
        override fun Do(): ImageConfiguration {
            return NearestNeighborAlgorithm.getNewImage(
                AppConfiguration.Space.selected,
                AppConfiguration.Image,
                PositionFinder.getScaledFromOriginal(
                    AppConfiguration.Scaling.Center,
                    AppConfiguration.Image.width,
                    AppConfiguration.Image.height,
                    AppConfiguration.Scaling.Width,
                    AppConfiguration.Scaling.Height
                ),
                AppConfiguration.Scaling.Width,
                AppConfiguration.Scaling.Height
            )
        }

    },
    Bilinear {
        override fun Do(): ImageConfiguration {
            return BilinearAlgorithm.getNewImage(
                AppConfiguration.Space.selected,
                AppConfiguration.Image,
                PositionFinder.getScaledFromOriginal(
                    AppConfiguration.Scaling.Center,
                    AppConfiguration.Image.width,
                    AppConfiguration.Image.height,
                    AppConfiguration.Scaling.Width,
                    AppConfiguration.Scaling.Height
                ),
                AppConfiguration.Scaling.Width,
                AppConfiguration.Scaling.Height
            )
        }
    },
    Lanczos3 {
        override fun Do(): ImageConfiguration {
            return Lanczos3Algorithm.getNewImage(
                AppConfiguration.Space.selected,
                AppConfiguration.Image,
                PositionFinder.getScaledFromOriginal(
                    AppConfiguration.Scaling.Center,
                    AppConfiguration.Image.width,
                    AppConfiguration.Image.height,
                    AppConfiguration.Scaling.Width,
                    AppConfiguration.Scaling.Height
                ),
                AppConfiguration.Scaling.Width,
                AppConfiguration.Scaling.Height
            )
        }

    },
    BCSplines {
        override fun Do(): ImageConfiguration {
            return BCSplinesAlgorithm.getNewImage(
                AppConfiguration.Space.selected,
                AppConfiguration.Image,
                PositionFinder.getScaledFromOriginal(
                    AppConfiguration.Scaling.Center,
                    AppConfiguration.Image.width,
                    AppConfiguration.Image.height,
                    AppConfiguration.Scaling.Width,
                    AppConfiguration.Scaling.Height
                ),
                AppConfiguration.Scaling.Width,
                AppConfiguration.Scaling.Height,
                AppConfiguration.Scaling.BValue,
                AppConfiguration.Scaling.CValue
            )
        }

    };

    abstract fun Do(): ImageConfiguration
}