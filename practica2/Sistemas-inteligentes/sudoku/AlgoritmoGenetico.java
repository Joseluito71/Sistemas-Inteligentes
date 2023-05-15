import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

public class AlgoritmoGenetico {
  private List<Individuo> poblacion;
  private int cantidadPoblacion;
  private int[][] sudoku;
  private Individuo solucion;
  private double tasaCruce;
  private double tasaMutacion;

  public AlgoritmoGenetico(int[][] sudoku, int cantidadPoblacion, double tasaCruce, double tasaMutacion) {
    this.sudoku = sudoku;
    this.cantidadPoblacion = cantidadPoblacion;
    this.poblacion = generarPoblacion();
    this.tasaCruce = tasaCruce;
    this.tasaMutacion = tasaMutacion;
  }

  public List<Individuo> getPoblacion() {
    return poblacion;
  }

  public void setPoblacion(List<Individuo> poblacion) {
    this.poblacion = poblacion;
  }

  public Individuo getSolucion() {
    return solucion;
  }

  public List<Individuo> generarPoblacion() {
    List<Individuo> poblacion = new ArrayList<Individuo>();
    for (int i = 0; i < cantidadPoblacion; i++) {
      poblacion.add(generarIndividuo(this.sudoku));
    }
    return poblacion;
  }

  // Identifica cuales son los numeros faltantes para cada fila.
  // Genera una permutación aleatoria de los dígitos de cada fila, sin
  // intercambiar las posiciones originales del sudoku
  public Individuo generarIndividuo(int[][] sudoku) {
    int[][] sudokuPermutado = new int[9][9];
    for (int i = 0; i < 9; i++) {
      List<Integer> numeros = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
      for (int j = 0; j < 9; j++) {
        if (sudoku[i][j] != 0) {
          numeros.remove((Integer) sudoku[i][j]);
        }
      }
      Collections.shuffle(numeros);
      for (int j = 0; j < 9; j++) {
        if (sudoku[i][j] == 0) {
          sudokuPermutado[i][j] = numeros.remove(0);
        } else {
          sudokuPermutado[i][j] = sudoku[i][j];
        }
      }
    }
    return new Individuo(sudokuPermutado);
  }

  public Individuo[] cruzarIndividuos(Individuo individuo1, Individuo individuo2) {
    int[][] childSudoku1 = new int[9][9];
    int[][] childSudoku2 = new int[9][9];
    // copiar todos los datos del primer padre al hijo 1
    for (int row = 0; row < 9; row++) {
      System.arraycopy(individuo1.getGenes()[row], 0, childSudoku1[row], 0, 9);
    }

    // copiar todos los datos del segundo padre al hijo 2
    for (int row = 0; row < 9; row++) {
      System.arraycopy(individuo2.getGenes()[row], 0, childSudoku2[row], 0, 9);
    }

    double randomOperacion = Math.random();
    if (randomOperacion < this.tasaCruce) {
      int crossoverRow = (int) (Math.random() * 8); // seleccionar una fila aleatoria

      // copiar las primeras "crossoverRow" filas del primer individuo al primer hijo
      // y las restantes filas del segundo individuo al segundo hijo
      for (int row = 0; row < crossoverRow; row++) {
        System.arraycopy(individuo1.getGenes()[row], 0, childSudoku1[row], 0, 9);
        System.arraycopy(individuo2.getGenes()[row], 0, childSudoku2[row], 0, 9);
      }

      // copiar las restantes filas del primer individuo al segundo hijo
      // y las restantes filas del segundo individuo al primer hijo
      for (int row = crossoverRow; row < 9; row++) {
        System.arraycopy(individuo2.getGenes()[row], 0, childSudoku1[row], 0, 9);
        System.arraycopy(individuo1.getGenes()[row], 0, childSudoku2[row], 0, 9);
      }
    }
    Individuo[] hijos = new Individuo[2];
    hijos[0] = new Individuo(childSudoku1);
    hijos[1] = new Individuo(childSudoku2);
    return hijos;
  }

  public void mutarIndividuoVerificar(Individuo individuo) {
    double randomOperacion = Math.random();
    if (randomOperacion < this.tasaMutacion) {
      int casilla1, casilla2, fila;
      boolean duplicado = false;
      do {
        casilla1 = (int) (Math.random() * 9); // Selecciona una casilla aleatoria de la fila
        casilla2 = (int) (Math.random() * 9); // Selecciona otra casilla aleatoria de la fila
        fila = (int) (Math.random() * 9); // Selecciona una fila aleatoria

        // Verifica que las casillas sean diferentes y que ambas estén vacías en el
        // sudoku original
        if (casilla1 == casilla2 || this.sudoku[fila][casilla1] != 0 || this.sudoku[fila][casilla2] != 0) {
          duplicado = false;
          int valor1 = individuo.getGenes()[fila][casilla1];
          int valor2 = individuo.getGenes()[fila][casilla2];

          // Verifica si los valores a intercambiar están duplicados en la columna o
          // cuadrado de 3x3
          for (int i = 0; i < 9; i++) {
            if (i != fila) {
              if (individuo.getGenes()[i][casilla1] == valor2 || individuo.getGenes()[i][casilla2] == valor1) {
                duplicado = true;
                break;
              }
            }
          }

          if (!duplicado) {
            int filaInicial = (fila / 3) * 3;
            int columnaInicial1 = (casilla1 / 3) * 3;
            int columnaInicial2 = (casilla2 / 3) * 3;
            for (int i = filaInicial; i < filaInicial + 3; i++) {
              for (int j = columnaInicial1; j < columnaInicial1 + 3; j++) {
                if (i != fila || j != casilla1) {
                  if (individuo.getGenes()[i][j] == valor2) {
                    duplicado = true;
                    break;
                  }
                }
              }
              if (duplicado) {
                break;
              }
            }
            if (!duplicado) {
              for (int i = filaInicial; i < filaInicial + 3; i++) {
                for (int j = columnaInicial2; j < columnaInicial2 + 3; j++) {
                  if (i != fila || j != casilla2) {
                    if (individuo.getGenes()[i][j] == valor1) {
                      duplicado = true;
                      break;
                    }
                  }
                }
                if (duplicado) {
                  break;
                }
              }
            }
          }
        }
      } while (duplicado);

      // Intercambia las casillas seleccionadas
      int temp = individuo.getGenes()[fila][casilla1];
      individuo.getGenes()[fila][casilla1] = individuo.getGenes()[fila][casilla2];
      individuo.getGenes()[fila][casilla2] = temp;
    }
  }

  public void mutarIndividuo(Individuo individuo) {
    double randomOperacion = Math.random();
    if (randomOperacion < this.tasaMutacion) {
      int casilla1, casilla2, fila;
      do {
        casilla1 = (int) (Math.random() * 9); // Selecciona una casilla aleatoria de la fila
        casilla2 = (int) (Math.random() * 9); // Selecciona otra casilla aleatoria de la fila
        fila = (int) (Math.random() * 9); // Selecciona una fila aleatoria
      } while (casilla1 == casilla2 || this.sudoku[fila][casilla1] != 0 || this.sudoku[fila][casilla2] != 0);
      // Verifica que las casillas sean diferentes y que ambas estén vacías en el
      // sudoku original

      int temp = individuo.getGenes()[fila][casilla1]; // Intercambia las casillas seleccionada
      individuo.getGenes()[fila][casilla1] = individuo.getGenes()[fila][casilla2];
      individuo.getGenes()[fila][casilla2] = temp;
    }
  }

  // metodo para recorrer la poblacion y buscar si algun individuo tiene un
  // fitness de 162
  public boolean buscarSolucion() {
    for (Individuo individuo : this.poblacion) {
      if (individuo.getFitness() == 162) {
        this.solucion = individuo;
        return true;
      }
    }
    return false;
  }

  // recorrer la poblacion y encontrar el individuo con el mejor fitness
  public Individuo mejorIndividuo() {
    Individuo mejorIndividuo = this.poblacion.get(0);
    for (Individuo individuo : this.poblacion) {
      if (individuo.getFitness() > mejorIndividuo.getFitness()) {
        mejorIndividuo = individuo;
      }
    }
    return mejorIndividuo;
  }

  public Individuo seleccionarIndividuo() {
    int sumaFitness = 0;
    for (Individuo individuo : this.poblacion) {
      sumaFitness += individuo.getFitness();
    }

    int random = (int) (Math.random() * sumaFitness); // Genera un número aleatorio entre 0 y la suma total de los

    int sumaParcial = 0;
    for (Individuo individuo : poblacion) {
      sumaParcial += individuo.getFitness();
      if (sumaParcial >= random) {
        return individuo; // Retorna el individuo en el que se detuvo el recorrido
      }
    }
    return null; // Si no se encuentra ningún individuo, retorna null
  }

  // seleccionar 3 individuo aleatorios y retortar el individuo con mejor fitness
  // de los 3
  public Individuo seleccionarIndividuoCompetir() {
    // Generar 3 números aleatorios entre 0 y el tamaño de la población
    Random rand = new Random();
    int indiceAleatorio1 = rand.nextInt(this.poblacion.size());
    int indiceAleatorio2 = rand.nextInt(this.poblacion.size());
    int indiceAleatorio3 = rand.nextInt(this.poblacion.size());

    // Retornar el individuo con mejor fitness de los 3
    if (this.poblacion.get(indiceAleatorio1).getFitness() > this.poblacion.get(indiceAleatorio2).getFitness()
        && this.poblacion.get(indiceAleatorio1).getFitness() > this.poblacion.get(indiceAleatorio3).getFitness()) {
      return this.poblacion.get(indiceAleatorio1);
    } else if (this.poblacion.get(indiceAleatorio2).getFitness() > this.poblacion.get(indiceAleatorio1).getFitness()
        && this.poblacion.get(indiceAleatorio2).getFitness() > this.poblacion.get(indiceAleatorio3).getFitness()) {
      return this.poblacion.get(indiceAleatorio2);
    } else {
      return this.poblacion.get(indiceAleatorio3);
    }
  }

}
