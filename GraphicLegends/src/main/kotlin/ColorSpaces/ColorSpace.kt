package ColorSpaces

import Interfaces.IColorSpace

enum class ColorSpace {
    RGB {
        override fun GetInstance(firstValue: Int, secondValue: Int, thirdValue: Int): IColorSpace {
            return RGB(firstValue, secondValue, thirdValue)
        }
    },
    HSL {
        override fun GetInstance(firstValue: Int, secondValue: Int, thirdValue: Int): IColorSpace {
            TODO("Not yet implemented")
        }
    },
    HSV {
        override fun GetInstance(firstValue: Int, secondValue: Int, thirdValue: Int): IColorSpace {
            TODO("Not yet implemented")
        }
    },
    YCbCr601 {
        override fun GetInstance(firstValue: Int, secondValue: Int, thirdValue: Int): IColorSpace {
            TODO("Not yet implemented")
        }

    },
    YCbCr709 {
        override fun GetInstance(firstValue: Int, secondValue: Int, thirdValue: Int): IColorSpace {
            TODO("Not yet implemented")
        }

    },
    YCoCg {
        override fun GetInstance(firstValue: Int, secondValue: Int, thirdValue: Int): IColorSpace {
            TODO("Not yet implemented")
        }

    },
    CMY {
        override fun GetInstance(firstValue: Int, secondValue: Int, thirdValue: Int): IColorSpace {
            TODO("Not yet implemented")
        }
    };

    abstract fun GetInstance(firstValue: Int, secondValue : Int, thirdValue: Int) : IColorSpace
}