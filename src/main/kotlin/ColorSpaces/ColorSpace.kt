package ColorSpaces

import Interfaces.IColorSpace

enum class ColorSpace {
    RGB {
        override fun GetDefault(): ColorSpaceInstance {
            return ColorSpaceInstance(RGB)
        }

        override fun GetService(): IColorSpace {
            return RGB()
        }
    },
    HSL {
        override fun GetDefault(): ColorSpaceInstance {
            return ColorSpaceInstance(HSL)
        }

        override fun GetService(): IColorSpace {
            return HSL()
        }
    },
    HSV {
        override fun GetDefault(): ColorSpaceInstance {
            return ColorSpaceInstance(HSV)
        }

        override fun GetService(): IColorSpace {
            return HSV()
        }
    },
    YCbCr601 {
        override fun GetDefault(): ColorSpaceInstance {
            return ColorSpaceInstance(YCbCr601)
        }

        override fun GetService(): IColorSpace {
            return YCbCr601()
        }

    },
    YCbCr709 {
        override fun GetDefault(): ColorSpaceInstance {
            return ColorSpaceInstance(YCbCr709)
        }

        override fun GetService(): IColorSpace {
            return YCbCr709()
        }

    },
    YCoCg {
        override fun GetDefault(): ColorSpaceInstance {
            return ColorSpaceInstance(YCoCg)
        }

        override fun GetService(): IColorSpace {
            return YCoCg()
        }

    },
    CMY {
        override fun GetDefault(): ColorSpaceInstance {
            return ColorSpaceInstance(CMY)
        }

        override fun GetService(): IColorSpace {
            return CMY()
        }
    };

    abstract fun GetDefault() : ColorSpaceInstance
    abstract fun GetService() : IColorSpace
    fun GetName() : String {
        return this.name
    }
}