import com.qqwing.*;
import com.qqwing.Difficulty;

public class SudokuGenerador {


  public static int[][] arrayTomatriz(int[] arrayEntrada) {
	  
    int[][] matriz = new int[9][9];
    
    int k = 0;
    
    for (int i = 0; i < 9; i++) {
    	
      for (int j = 0; j < 9; j++) {
    	  
        matriz[i][j] = arrayEntrada[k];
        
        k++;
        
      }
    }
    return matriz;
  }

 
  public static String printMatrix(int[][] matriz) {
	  
    String result = "";
    
    for (int i = 0; i < 9; i++) {
    	
      if (i % 3 == 0) {
    	  
        result += "█████████████████████████\n";
        
      }
      
      for (int j = 0; j < 9; j++) {
    	  
        if (j % 3 == 0) {
        	
          result += "█ ";
          
        }
        
        result = result + matriz[i][j] + " ";
        
      }
      
      result += "█\n";
      
    }
    
    result += "█████████████████████████\n";
    
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
    int[][] matrizSudoku;
    int fitness = 0;

    do {

      // Difficulty difficulty = Difficulty.EXPERT;
      int[] sudoku = computePuzzleByDifficulty(difficulty);
      matrizSudoku = arrayTomatriz(sudoku);
      // matrizSudokuCopy = SudokuGenerador.arrayTomatriz(sudoku);
      matrizSudoku = PencilMarking.pencilMarking(matrizSudoku);
      Individuo individuoTest = new Individuo(matrizSudoku);
      fitness = individuoTest.getFitness();
    } while (fitness < rangoInferior || fitness > rangoSuperior);
    System.out.println("Sudoku inicial. Fitness: " + fitness);

    return matrizSudoku;
  }
}
