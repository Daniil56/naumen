[![Build Status](https://travis-ci.org/Daniil56/naumen.svg?branch=master)](https://travis-ci.org/Daniil56/naumen)
[![codecov](https://codecov.io/gh/Daniil56/naumen/branch/master/graph/badge.svg)](https://codecov.io/gh/Daniil56/naumen)
# Тестовое задание "Головоломка", комании Naumen
 <a href="https://www.naumen.ru"> Naumen</a>

 <p><h2>Постановка задачи: </h2></p> 
  
<img src="https://graphonline.ru/tmp/saved/wE/wEZJxniMdXFEprnp.png"></a>

<h4>На рисунке изображена головоломка, в которой цифры от 1 до 7 располагаются в ячейках, а одна из ячеек свободна. 
Она используется для проверки интеллекта роботов. Для ее решения необходимо переставить цифры, используя свободную
ячейку, чтобы цифры расположились так, как показано на рисунке. Перемещение цифры в свободную ячейку возможно, только
если ячейки соединены линией.</h4>

<h4>Реализовать интерфейс:</h4>
` interface PuzzleResolver {
 int[] resolve(int[] start);
 }
 `
<h5> где : _start_ первоначальное состояние головоломки
 _return_ решение головоломки</h5>
 
 
 <p><h3>Пример </h3></p>
 
**Ввод:** `new int[] {2 1 3 4 0 5 6 7}`
<img src="https://graphonline.ru/tmp/saved/tj/tjYbKRkjrbfOTeCX.png"></a>



**Вывод**
`new int[] {5 3 2 1 2 3 5}`

Если существует несколько вариантов путей с одинаковым количеством ходов, вывести любой из них.  




