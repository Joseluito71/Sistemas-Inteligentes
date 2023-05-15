import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.List;
import java.util.Set;
import java.util.Arrays;

public class Main {

  public static void main(String[] args) {

    // execute just one time
    Laberinto newLaberinto = new Laberinto(0.3, 60, 80);
    Node res = AStarTree.aStar(newLaberinto.matriz, newLaberinto.estadoInicial,
        newLaberinto.estadoObjetivo);
    File file = new File("salida" + ".txt");
    // write to file
    try {
      FileWriter writer = new FileWriter(file, false);
      if (res instanceof Node) {
        List<Node> path = AStarTree.getPath(res);
        System.out.println();
        newLaberinto.imprimirMatriz(path);
        newLaberinto.escribirMatriz(path, writer);

      } else {
        System.out.println("El algoritmo A* no pudo encontrar una solucion");
        newLaberinto.escribirMatriz(null, writer);
      }
      writer.close();
    } catch (Exception e) {
      System.out.println("Error al escribir el archivo.");
      e.printStackTrace();
    }

    //ESTA PARTE HACE LA EJECUCION DE LA TAREA OPCIONAL
    // fraccion de obstaculos a iterar
    // double[] fraccionesObstaculos = { 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9
    // };
    // int numExecutions = 100;

    // //por cada fraccion de obstaculo se crea un nuevo archivo y se ejecuta 100
    // veces el algoritmo A*
    // for (double fraccionObstaculos : fraccionesObstaculos) {
    // //cuenta el numero de solucciones que se pudieron encontrar
    // int numSoluciones = 0;
    // //suma la longitud del camine en cada iteracion
    // int numIteraciones = 0;
    // File file = new File("salida_" + fraccionObstaculos + ".txt");
    // // write to file
    // try {
    // FileWriter writer = new FileWriter(file, false);

    // for (int i = 0; i < numExecutions; i++) {
    // Laberinto newLaberinto = new Laberinto(fraccionObstaculos, 60, 80);
    // Node res = AStarTree.aStar(newLaberinto.matriz, newLaberinto.estadoInicial,
    // newLaberinto.estadoObjetivo);
    // if (res instanceof Node) {
    // numSoluciones++;
    // List<Node> path = AStarTree.getPath(res);
    // numIteraciones += path.size();
    // writer.write("\n");
    // newLaberinto.escribirMatriz(path, writer);

    // } else {
    // writer.write("\nEl algoritmo A* no pudo encontrar una solucion\n");
    // newLaberinto.escribirMatriz(null, writer);
    // }
    // }
    // writer.write("\nFraccion de obstaculos: " + fraccionObstaculos);
    // writer.write("\nNumero de soluciones: " + numSoluciones);
    // writer.write("\nNumero de fallidos: " + (numExecutions - numSoluciones));

    // // avoid divide by zero
    // if (numSoluciones != 0) {
    // writer.write("\nLongitud promedio del camino: " + numIteraciones /
    // numSoluciones);
    // } else {
    // writer.write("\nLongitud promedio del camino: " + numIteraciones);
    // writer.write("\n");
    // }

    // writer.close();
    // } catch (Exception e) {
    // System.out.println("Error al escribir el archivo.");
    // e.printStackTrace();
    // }
    // }
  }
}
