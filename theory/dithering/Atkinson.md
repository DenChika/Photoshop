# Алгоритм Аткинсона
Алгоритм Флойда-Штейнберга - это алгоритм дизеринга изображений, разработанный комьютерным инженером компании Apple.

Алгоритм дизеринга организован с помощью принципа распространения ошибки. При обработки пикселя ошибка по итогу его обработки передаётся соседним
пикселям по определённой схеме.

Данная матрица задаёт схема распространения ошибки:

| ... | ...   | current | 1 / 8 | 1 / 8 | ... |
|-----|-------|---------|-------|-------|-----|
| ... | 1 / 8 | 1 / 8   | 1 / 8 | ...   | ... |
| ... | ...   | 1 / 8   | ...   | ...   | ... |

Current – пиксель, обрабатываемый в данный момент

Алгоритм:
1) Выбираем пиксель для обработки и получаем его цвет в некотором цветовом пространстве
2) Сравниваем значения оригинального цвета и нового цвета в соответствии с указанной битностью или битностью графического устройства
3) Получаем значение ошибки, путём вычитания значения нового цвета из значения оригинального цвета
4) Распространяем ошибку на соседние пиксели в соответствии с вышеуказанной схемой, умножая значение ошибки на коэффициенты и прибавляя
   результат к значениям соседних пикселей

![example](content/FloydSteinber_vs_Atkinson.png)

Source: [Beyond loom](https://beyondloom.com/blog/dither.html)