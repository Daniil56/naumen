package ru.naumen.puzzle;
import java.util.*;

/**
 * Класс реализующий интерфейс решения головолки, аналога игры в 8
 * @author Daniil Emelyanov
 * @since 08.05.2019
 * @version 0.2
 */
public class Resolver implements PuzzleResolver {

    @Override
/**
 * Метод решения аналога головоломки игра в 8.
 * Метод принимает массив start, после чего при помощи вспомогательных методов создает HashMap path возможных путей перемещения.
 * В path ключем является предыдущий путь, значением текущий путь.
 * Затем из всех возможных путей path, значений целевого положения вершин end и данного изначального положения start,
 * c помощью метода pathRecovery, происходит восстоновление наикратчайшего пути в массив result.
 * Добавил проверку на решаемость начального состояния игра, если решение нет, выбрасывает исключение
 * NoStateSolvedException.
 * Произвел небольшой рефакторинг алгоритма bsf.
 * @param start первоначальное состояние головоломки
 * @return результирующий массив чисел, являющийся решением головоломки
 */
    public int[] resolve(int[] start) {
        int[] result;
        int[] cache = new int[8];
        int first = fromStateToInt(start);
        LinkedList<Integer> queueState = new LinkedList<>();
        HashMap<Integer, Integer> visited = new HashMap();
        HashMap<Integer, Integer> path = new HashMap<>();
        int end = 12340567;
        if (!isSolvable(start)) {
                throw new NoStateSolvedException("This start state not be solved");
        }

        if (first == end) {
            result = new int[0];
        }
        queueState.addLast(first);
        visited.put(first, 0);
        while (!queueState.isEmpty()) {
                int curr = queueState.remove();
                int[] curState = toState(curr, cache);
            for (Integer edge : getStateEdge(curState)) {
                if (!visited.containsKey(edge)) {
                    visited.put(edge, visited.get(curr) + 1);
                    queueState.addLast(edge);
                    path.put(edge, curr);
                }
                if (edge == end) {
                    queueState.clear();
                    break;
                }
            }

        }
        result = pathRecovery(path, end, first);
        return result;
    }

    /**
     * Метод определяющий есть ли решение у данного состоянии игры, путем вычесление остатка от деления
     * для суммы числа инверсий в массиве
     * @param start начальное сосотояние игры
     * @return есть ли решение
     */
    private boolean isSolvable(int[] start) {
        boolean result;
        int count = 0;
        for (int index = 0; index < start.length - 1; index++)  {
                if (start[index] > 0 && start[index + 1] > 0 && start[index] > start[index + 1]) {
                    count++;
                }
        }
        if (count == 0) {
            result = true;
        } else {
            result = count % 2 != 0;
        }
        return result;
    }

    /**
     * Метод восстоновления наикратчайшего пути для решения головоломки
     * @param path Хэш карта всех возможных состояний решения
     * @param endState целевое состояние вершин головоломки
     * @param firstState исходное состояние вершин головоломки
     * @return массив чисел с минимально необходимым количеством ходов для решения головоломки
     */
    private int[] pathRecovery(HashMap<Integer, Integer> path, int endState, int firstState) {
        int[] cache1 = new int[8];
        int[] cache2 = new int[8];
        Stack<Integer> route = new Stack<>();
        int c = endState;
        LinkedList<Integer> matrixStateInd = new LinkedList<>();
        LinkedList<Integer> matrixStateVal = new LinkedList<>();
        while (c != firstState) {
            int prev = path.get(c);
            toState(c, cache1);
            toState(prev, cache2);
            matrixStateInd.add(c);
            matrixStateVal.add(prev);
            c = prev;
        }
        for (int i = 0; i < matrixStateInd.size(); i++) {
            int[] identification = new int[8];
            int[] pathValues = new int[8];
            toState(matrixStateInd.get(matrixStateInd.size() - 1 - i), identification);
            toState(matrixStateVal.get(matrixStateInd.size() - 1 - i), pathValues);
            int ind = positionZeroToIndex(identification);
            route.add(pathValues[ind]);
        }

        return toArray(route);
    }


    /**
     * Вспомогоательный метод перевода массива в целое число
     * @param state маиисв состояния
     * @return число состояния
     */
    private int fromStateToInt(int[] state) {
        int ret = 0;
        for (int value : state) {
            ret += value;
            ret *= 10;
        }
        return ret / 10;
    }

    /**
     * Вспомогательный метод перевода числа в массив состояния
     * @param source число состояния
     * @param cache массив состояния
     * @return массив состояния
     */
    private int[] toState(int source, int[] cache) {
        for (int i = cache.length - 1; i >= 0; i--) {
            cache[i] = source % 10;
            source /= 10;
        }
        return cache;
    }

    /**
     *  Метод перестаовки чисел головоломки
     * @param inp массив исходного состояния головоломки
     * @param i текущая позиция ячейки
     * @param j новая позиция ячейки
     * @return массив нового состояния игры
     */
    private int[] swap(int[] inp, int i, int j) {
        int tmp = inp[i];
        inp[i] = inp[j];
        inp[j] = tmp;
        return inp;
    }

    /**
     * Метод получения потомка для текущего состояния игры
     * @param inp текущиее состояние
     * @param i текщее положение  ячейки
     * @param j новое положение ячейки
     * @return новое состояние игры
     */
    private int getSibling(int[] inp, int i, int j) {
        int ret = fromStateToInt(swap(inp, i, j));
        swap(inp, i, j);
        return ret;
    }

    /**
     * Метод поиска нулевой ячейки
     * @param array массив текущего состояния
     * @return позиция нуля в текущем массиве состояния
     */
    private int positionZeroToIndex(int[] array) {
        int result = -1;
        for (int index = 0; index < array.length - 1; index++) {
            if (array[index] == 0) {
                result = index;
            }
        }
        return result;
    }

    /**
     * Метод получения коллекции нового состояние игры
     * @param state массив текущего состояние игры
     * @return хеш таблица нового состояния
     */
    private Iterable<Integer> getStateEdge(int[] state) {
        int zeroPos = positionZeroToIndex(state);
        HashSet<Integer> edgeSet = new HashSet<>();
        if (zeroPos == 0) {
            edgeSet.add(getSibling(state, 0, 1));
            edgeSet.add(getSibling(state, 0, 2));
        } else if (zeroPos == 1) {
            edgeSet.add(getSibling(state, 1, 0));
            edgeSet.add(getSibling(state, 1, 2));
            edgeSet.add(getSibling(state, 1, 3));
        } else if (zeroPos == 2) {
            edgeSet.add(getSibling(state, 2, 0));
            edgeSet.add(getSibling(state, 2, 1));
            edgeSet.add(getSibling(state, 2, 5));
        } else if (zeroPos == 3) {
            edgeSet.add(getSibling(state, 3, 1));
            edgeSet.add(getSibling(state, 3, 4));
            edgeSet.add(getSibling(state, 3, 6));
        } else if (zeroPos == 4) {
            edgeSet.add(getSibling(state, 4, 3));
            edgeSet.add(getSibling(state, 4, 5));
        } else if (zeroPos == 5) {
            edgeSet.add(getSibling(state, 5, 2));
            edgeSet.add(getSibling(state, 5, 4));
            edgeSet.add(getSibling(state, 5, 7));
        } else if (zeroPos == 6) {
            edgeSet.add(getSibling(state, 6, 3));
            edgeSet.add(getSibling(state, 6, 7));
        } else if (zeroPos == 7) {
            edgeSet.add(getSibling(state, 7, 6));
            edgeSet.add(getSibling(state, 7, 5));
        }
        return edgeSet;
    }

    /***
     * Метод перевода стека в целочисленный массив
     * @param list стек чисел
     * @return масиив чисел
     */
    private int[] toArray(Stack<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}