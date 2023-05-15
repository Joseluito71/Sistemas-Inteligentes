import com.qqwing.*;
import com.qqwing.Difficulty;

public class Sudoku {


 

 
  public static String dibujarMatriz(int[][] sudoku) {
	  
    String linea = "";
    
    for (int i = 0; i < 9; i++) {
    	
      if (i % 3 == 0) {
    	  
        linea += "█████████████████████████\n";
        
      }
      
      for (int j = 0; j < 9; j++) {
    	  
        if (j % 3 == 0) {
        	
          linea += "█ ";
          
        }
        
        linea = linea + sudoku[i][j] + " ";
        
      }
      
      linea += "█\n";
      
    }
    
    linea += "█████████████████████████\n";
    
    return linea;
    
  }
  public static int[][] arrayToSudoku(int[] arrayASudoku) {
	  
	    int[][] sudoku = new int[9][9];
	    
	    int k = 0;
	    
	    for (int i = 0; i < 9; i++) {
	    	
	      for (int j = 0; j < 9; j++) {
	    	  
	        sudoku[i][j] = arrayASudoku[k];
	        
	        k++;
	        
	      }
	    }
	    return sudoku;
	  }

  public static int[] calcularPuzle(Difficulty d) {
	  
    QQWing qq = new QQWing();
    qq.setRecordHistory(true);
    qq.setLogHistory(false);
    boolean control = true;
    
    while (control) {
    	
      qq.generatePuzzle();
      qq.solve();
      Difficulty dificultadActual = qq.getDifficulty();
      control = !dificultadActual.equals(d);
      
    }
    
    int[] puzzle = qq.getPuzzle();
    return puzzle;
    
  }

}
