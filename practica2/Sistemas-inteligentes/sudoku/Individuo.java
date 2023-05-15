import java.util.HashSet;

public class Individuo implements Comparable<Individuo> {
  private int[][] genes;
  private int fitness;

  public Individuo(int[][] genes) {
    this.genes = genes;
    this.fitness = calculateFitness();
  }

  public int[][] getGenes() {
    return this.genes;
  }

  public int getFitness() {
    return fitness;
  }

  // Calcula el fitness del individuo
  private int calculateFitness() {
    int fitness = 0;

    // Comprobar las columnas
    for (int i = 0; i < 9; i++) {
      int[] column = new int[9];
      for (int j = 0; j < 9; j++) {
        column[j] = genes[j][i];
      }
      fitness += countUniqueValues(column);
    }

    // Comprobar los bloques de 3x3
    for (int i = 0; i < 9; i += 3) {
      for (int j = 0; j < 9; j += 3) {
        int[] block = new int[9];
        int index = 0;
        for (int k = i; k < i + 3; k++) {
          for (int l = j; l < j + 3; l++) {
            block[index] = genes[k][l];
            index++;
          }
        }
        fitness += countUniqueValues(block);
      }
    }

    return fitness;
  }

  // Cuenta los valores únicos en un array de enteros
  private int countUniqueValues(int[] array) {
    HashSet<Integer> uniqueValues = new HashSet<Integer>();
    for (int i = 0; i < array.length; i++) {
      uniqueValues.add(array[i]);
    }
    return uniqueValues.size();
  }

  // Compara el fitness de dos individuos para poder ordenarlos
  public int compareTo(Individuo other) {
    return other.fitness - this.fitness;
  }

  //print genes to string with format
  public String toString() {
    String result = "";
    for (int i = 0; i < 9; i++) {
      if (i % 3 == 0) {
        result += "-------------------------\n";
      }
      for (int j = 0; j < 9; j++) {
        if (j % 3 == 0) {
          result += "| ";
        }
        result += genes[i][j] + " ";
      }
      result += "|\n";
    }
    result += "-------------------------\n";
    return result;
  }

}
