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
  public static void printMatrix(int[][] matrix) {
    for (int i = 0; i < 9; i++) {
      System.out.print("|");
      for (int j = 0; j < 9; j++) {
        System.out.print(matrix[i][j] + "|");
      }
      System.out.println("");
    }
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
      System.out.println("Difficulty: " + actual_d.getName());
      go_on = !actual_d.equals(d);
    }
    int[] puzzle = qq.getPuzzle();
    return puzzle;
  }
}
