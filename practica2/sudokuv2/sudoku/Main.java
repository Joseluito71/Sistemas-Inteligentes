import java.util.Arrays;

import com.qqwing.*;
import com.qqwing.Difficulty;

public class Main {
  public static void main(String[] args) {
    QQWing test = new QQWing();
    test.generatePuzzle();
    test.solve();
    int[][] matrix = SudokuGenerador.arrayToMatrix(test.getSolution());
    SudokuGenerador.printMatrix(matrix);
    Individuo pIndividuo = new Individuo(matrix);
    System.out.println("Fitness: ");
    System.out.println(pIndividuo.getFitness());
    // Difficulty d = Difficulty.EASY;
    // int[] sudoku = computePuzzleByDifficulty(d);
    // int[][] matrix = arrayToMatrix(sudoku);

    // printMatrix(matrix);
  }

}
