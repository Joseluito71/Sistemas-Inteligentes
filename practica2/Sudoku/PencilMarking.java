public class PencilMarking {
  public static int[][] pencilMarking(int[][] s) {

	  boolean[][][] p = new boolean[9][9][9]; 
    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        if (s[row][col] == 0) {
          for (int i = 0; i < 9; i++) {
            p[row][col][i] = true;
          }
        } else {
          
        	int value = s[row][col] - 1;
          
        	for (int i = 0; i < 9; i++) {
            p[row][col][i] = (i == value);
          }
        }
      }
    }

    
    boolean cambiado = true;
    
    while (cambiado) {
    
    	cambiado = false;
      
    	for (int row = 0; row < 9; row++) {
        
    	for (int col = 0; col < 9; col++) {
          if (s[row][col] == 0) {
            int subgridRow = (row / 3) * 3;
            int subgridCol = (col / 3) * 3;
            for (int i = 0; i < 9; i++) {
              if (s[row][i] != 0) {
                p[row][col][s[row][i] - 1] = false;
              }
              if (s[i][col] != 0) {
                p[row][col][s[i][col] - 1] = false;
              }
              if (s[subgridRow + (i / 3)][subgridCol + (i % 3)] != 0) {
                p[row][col][s[subgridRow + (i / 3)][subgridCol + (i % 3)] - 1] = false;
              }
            }
            int count = 0;
            int value = 0;
            for (int i = 0; i < 9; i++) {
              if (p[row][col][i]) {
                count++;
                value = i + 1;
              }
            }
            if (count == 1) {
              s[row][col] = value;
              cambiado = true;
            }
          }
        }
      }
    }
    return s;
  }
}
