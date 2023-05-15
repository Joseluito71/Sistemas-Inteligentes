import java.io.FileWriter;
import java.util.*;

public class Laberinto {
  public int numfila;
  public int numcolumna;
  public boolean[][] matriz;
  public int[] estadoInicial;
  public int[] estadoObjetivo;

  public Laberinto(double fraccionObstaculos, int numfila, int numcolumna) {
    matriz = new boolean[numfila][numcolumna];
    this.numfila = numfila;
    this.numcolumna = numcolumna;
    generarObstaculos(fraccionObstaculos);
    generarEstadosInicialYObjetivo();
  }

  // generar matriz de obstaculos
  // verificando que no hay un obstaculos en la posicion nueva
  private void generarObstaculos(double fraccionObstaculos) {
    int numObstaculos = (int) (numfila * numcolumna * fraccionObstaculos);
    Random rand = new Random();
    while (numObstaculos > 0) {
      int fila = rand.nextInt(numfila);
      int columna = rand.nextInt(numcolumna);
      if (!matriz[fila][columna]) {
        matriz[fila][columna] = true;
        numObstaculos--;
      }
    }
  }

  // generar estados inicial y objetivo
  // verificando que no hay un obstaculos en la posicion nueva
  // y la posicion inicial y final no son las mismas

  private void generarEstadosInicialYObjetivo() {
    Random rand = new Random();
    estadoInicial = generarEstadoAleatorio(rand);
    estadoObjetivo = generarEstadoAleatorio(rand);
    while (Arrays.equals(estadoInicial, estadoObjetivo) || matriz[estadoInicial[0]][estadoInicial[1]]
        || matriz[estadoObjetivo[0]][estadoObjetivo[1]]) {
      estadoInicial = generarEstadoAleatorio(rand);
      estadoObjetivo = generarEstadoAleatorio(rand);
    }
  }

  // genera un valor aleatorio para la fila y columna
  // y lo retorna como un objeto [fila, columna]

  private int[] generarEstadoAleatorio(Random rand) {
    int fila = rand.nextInt(numfila);
    int columna = rand.nextInt(numcolumna);
    return new int[] { fila, columna };
  }

  // imprimir matriz en la consola
  public void imprimirMatriz(List<Node> path) {
    for (int fila = 0; fila < numfila; fila++) {
      for (int columna = 0; columna < numcolumna; columna++) {
        int[] estadoActual = { fila, columna };
        if (this.matriz[fila][columna]) {
          System.out.print("[*]");
        } else {
          if (Arrays.equals(this.estadoInicial, estadoActual)) {
            System.out.print("[I]");
          } else if (Arrays.equals(this.estadoObjetivo, estadoActual)) {
            System.out.print("[F]");
          } else if (path != null && path.contains(new Node(fila, columna, 0, 0, null))) {
            System.out.print("[+]");
          } else {
            System.out.print("[ ]");
          }
        }
      }
      System.out.println();
    }
  }

  // escribir matriz en un archivo
  public void escribirMatriz(List<Node> path, FileWriter writer) {
    try {
      for (int fila = 0; fila < numfila; fila++) {
        for (int columna = 0; columna < numcolumna; columna++) {
          int[] estadoActual = { fila, columna };
          if (this.matriz[fila][columna]) {
            writer.write("[*]");
          } else {
            if (Arrays.equals(this.estadoInicial, estadoActual)) {
              writer.write("[I]");
            } else if (Arrays.equals(this.estadoObjetivo, estadoActual)) {
              writer.write("[F]");
            } else if (path != null && path.contains(new Node(fila, columna, 0, 0, null))) {
              writer.write("[+]");
            } else {
              writer.write("[ ]");
            }
          }
        }
        writer.write("\n");
      }
    } catch (Exception e) {
      System.out.println("Error al escribir el archivo.");
      e.printStackTrace();
    }
  }

}
