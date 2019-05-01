package ru.naumen.puzzle;

/**
 * Интерфейс решения головоломки.
 */
public interface PuzzleResolver {
    /**
     * Метод решения головоломки.
     * @param start первоначальное состояние головоломки
     * @return решение головоломки
     */
    int[] resolve(int[] start);
}
