package ru.naumen.puzzle;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Тест решения головоломки
 * Из условия задачи следует что любое решение за минимальное количество ходов допустимо.
 * в приложении к заданию, на ввод массива permutationHigh предлагается вывод:
 * 2 1 3 4 5 1 3 2 3 1 5 4 2 1 3 1 2 4, отличающегося от полченного мной вывода, а именно последними пятью перестановками -
 * 3  1  3  2  4 что не влияет на общее количество ходов, так же являющиеся мнимальным.
 */
public class ResolverTest {
    @Test
    public void whenSolverThenSolution() {
        int[] permutationHigh = new int[] {0, 1, 2, 3, 4, 5, 6, 7};
        int[] permutationLow = new int[] {2, 1, 3, 4, 0, 5, 6, 7};
        int[] permutationDefault = new int[] {1, 2, 3, 4, 0, 5, 6, 7};
        Resolver board = new Resolver();
        assertThat(board.resolve(permutationHigh), is(new int[]{2, 1, 3, 4, 5, 1, 3, 2, 3, 1, 5, 4, 2, 3, 1, 3, 2, 4}));
        assertThat(board.resolve(permutationLow), is(new int[] {5, 3, 2, 1, 2, 3, 5}));
        assertThat(board.resolve(permutationDefault), is(new int[0]));
    }

}