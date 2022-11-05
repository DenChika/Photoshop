package ColorSpaces

import Interfaces.ColorSpace

enum class ColorSpace {
    RGB {
        override fun GetInstance(firstValue: Int, secondValue: Int, thirdValue: Int): ColorSpace {
            return RGB(firstValue, secondValue, thirdValue)
        }
    },
    HSL {
        override fun GetInstance(firstValue: Int, secondValue: Int, thirdValue: Int): ColorSpace {
            TODO("Not yet implemented")
        }
    },
    HSV {
        override fun GetInstance(firstValue: Int, secondValue: Int, thirdValue: Int): ColorSpace {
            TODO("Not yet implemented")
        }
    },
    YCbCr601 {
        override fun GetInstance(firstValue: Int, secondValue: Int, thirdValue: Int): ColorSpace {
            TODO("Not yet implemented")
        }

    },
    YCbCr709 {
        override fun GetInstance(firstValue: Int, secondValue: Int, thirdValue: Int): ColorSpace {
            TODO("Not yet implemented")
        }

    },
    YCoCg {
        override fun GetInstance(firstValue: Int, secondValue: Int, thirdValue: Int): ColorSpace {
            TODO("Not yet implemented")
        }

    },
    CMY {
        override fun GetInstance(firstValue: Int, secondValue: Int, thirdValue: Int): ColorSpace {
            TODO("Not yet implemented")
        }
    };

    abstract fun GetInstance(firstValue: Int, secondValue : Int, thirdValue: Int) : ColorSpace
}