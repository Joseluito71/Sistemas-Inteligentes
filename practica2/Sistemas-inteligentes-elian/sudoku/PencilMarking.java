public class PencilMarking {
  // Función que realiza el proceso de pencil marking
  public static int[][] pencilMarking(int[][] sudoku) {
    boolean[][][] possibilities = new boolean[9][9][9]; // Array de posibilidades iniciales

    // Inicializar array de posibilidades para cada celda
    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        if (sudoku[row][col] == 0) {
          // Si la celda está vacía, se inicializa con todas las posibilidades
          for (int i = 0; i < 9; i++) {
            possibilities[row][col][i] = true;
          }
        } else {
          // Si la celda ya tiene un valor, se marca esa posibilidad como verdadera y las
          // demás como falsas
          int value = sudoku[row][col] - 1;
          for (int i = 0; i < 9; i++) {
            possibilities[row][col][i] = (i == value);
          }
        }
      }
    }

    boolean changed = true;
    while (changed) {
      changed = false;

      // Eliminar posibilidades de cada celda basado en los valores en la misma fila,
      // columna y subcuadrícula
      for (int row = 0; row < 9; row++) {
        for (int col = 0; col < 9; col++) {
          if (sudoku[row][col] == 0) {
            // Obtener subcuadrícula actual
            int subgridRow = (row / 3) * 3;
            int subgridCol = (col / 3) * 3;

            // Eliminar posibilidades basado en los valores en la misma fila, columna y
            // subcuadrícula
            for (int i = 0; i < 9; i++) {
              if (sudoku[row][i] != 0) {
                possibilities[row][col][sudoku[row][i] - 1] = false;
              }
              if (sudoku[i][col] != 0) {
                possibilities[row][col][sudoku[i][col] - 1] = false;
              }
              if (sudoku[subgridRow + (i / 3)][subgridCol + (i % 3)] != 0) {
                possibilities[row][col][sudoku[subgridRow + (i / 3)][subgridCol + (i % 3)] - 1] = false;
              }
            }

            // Verificar si sólo queda una posibilidad para la celda
            int count = 0;
            int value = 0;
            for (int i = 0; i < 9; i++) {
              if (possibilities[row][col][i]) {
                count++;
                value = i + 1;
              }
            }
            if (count == 1) {
              sudoku[row][col] = value;
              changed = true;
            }
          }
        }
      }
    }
    return sudoku;
  }
}
