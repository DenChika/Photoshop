# Фильтр Собеля
Оператор Собеля вычисляет градиент яркости изображения в каждой точке. Так находится направление наибольшего увеличения яркости и величина её изменения в этом направлении. Результат показывает, насколько «резко» или «плавно» меняется яркость изображения в каждой точке, а значит, вероятность нахождения точки на грани, а также ориентацию границы.

Данный фильтр применяется для алгоритмов выделения границ.

Оператор использует матрицы свёртки 3х3:

![image](https://user-images.githubusercontent.com/79001610/211898944-1ff28d50-ce56-468b-a352-b08677631cb7.png)

С помощью этих ядер вычисляются Gx и Gy приближённые значения производных по горизонтали и вертикали, а затем вычисляется значение градиента:

![image](https://user-images.githubusercontent.com/79001610/211899265-fe4a04a9-8d7b-44c9-ac39-3d01dedc82d8.png)

Что и будет являться новым значением пикселя

[Тестирование (собака)](content/dogSobel.jpg)

[Тестирование (труба)](content/pipeSobel.jpg)
