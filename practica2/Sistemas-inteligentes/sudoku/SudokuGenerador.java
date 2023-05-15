import com.qqwing.*;
import com.qqwing.Difficulty;

public class SudokuGenerador {

  // method thath receive array of 81 int and convert to matrix of 9x9
  public static int[][] arrayToMatrix(int[] input) {
    int[][] matrix = new int[9][9];
    int k = 0;
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        matrix[i][j] = input[k];
        k++;
      }
    }
    return matrix;
  }

  // method that receive int matrix and prints as table
  public static String printMatrix(int[][] matrix) {
    String result = "";
    for (int i = 0; i < 9; i++) {
      if (i % 3 == 0) {
        result += "-------------------------\n";
      }
      for (int j = 0; j < 9; j++) {
        if (j % 3 == 0) {
          result += "| ";
        }
        result += matrix[i][j] + " ";
      }
      result += "|\n";
    }
    result += "-------------------------\n";
    return result;
  }

  public static int[] computePuzzleByDifficulty(Difficulty d) {
    QQWing qq = new QQWing();
    qq.setRecordHistory(true);
    qq.setLogHistory(false);
    boolean go_on = true;
    while (go_on) {
      qq.generatePuzzle();
      qq.solve();
      Difficulty actual_d = qq.getDifficulty();
      go_on = !actual_d.equals(d);
    }
    int[] puzzle = qq.getPuzzle();
    return puzzle;
  }

  public static int[][] computePuzzleByDifficultyAndFitness(Difficulty difficulty, int rangoInferior,
      int rangoSuperior) {
    int[][] matrixSudoku;
    int fitness = 0;

    do {

      // Difficulty difficulty = Difficulty.EXPERT;
      int[] sudoku = computePuzzleByDifficulty(difficulty);
      matrixSudoku = arrayToMatrix(sudoku);
      // matrixSudokuCopy = SudokuGenerador.arrayToMatrix(sudoku);
      matrixSudoku = PencilMarking.pencilMarking(matrixSudoku);
      Individuo individuoTest = new Individuo(matrixSudoku);
      fitness = individuoTest.getFitness();
    } while (fitness < rangoInferior || fitness > rangoSuperior);
    System.out.println("Sudoku generado con Fitiness: " + fitness);

    return matrixSudoku;
  }
}
