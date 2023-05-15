import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.qqwing.*;
import com.qqwing.Difficulty;

public class Principal {
  public static void main(String[] args) {

    double tasaIndividuo = 0.05;
    double tasaMutacion = 0.7;
    double tasaCruce = 0.7;
    int poblacionSize = 150;
    int generaciones = 0;
    int limiteGeneraciones = 80000;
    boolean solucion = false;
    int[][] matrixSudoku;
    // calcular el numero de mejores individuos que se seleccionara para la nueva
    // generacion llamad elite
    int elite = (int) Math.round(poblacionSize * tasaIndividuo);
    int generacionesSinMejora = 0;
    int generacionesReales = 0;
    List<String> datos = new ArrayList<>();

    Difficulty difficulty = Difficulty.EASY;
    // generar sudoku solo por dificultad
    // int[] sudoku = SudokuGenerador.computePuzzleByDifficulty(difficulty);
    // matrixSudoku = SudokuGenerador.arrayToMatrix(sudoku);
    matrixSudoku = SudokuGenerador.computePuzzleByDifficultyAndFitness(difficulty, 75, 80);

    System.out.println("Sudoku generado: ");
    System.out.println(SudokuGenerador.printMatrix(matrixSudoku));
    datos.add("Sudoku generado: ");
    datos.add(SudokuGenerador.printMatrix(matrixSudoku));
    Individuo individuo = new Individuo(matrixSudoku);
    System.out.println("Fitness: " + individuo.getFitness());
    datos.add("Fitness: " + individuo.getFitness());

    // intstaciar el algoritmoGenetico con el sudoku original, el tamano de la
    // poblacion
    // y las tasas de cruce y mutacion
    AlgoritmoGenetico algoritmoGenetico = new AlgoritmoGenetico(matrixSudoku,
        poblacionSize, tasaCruce, tasaMutacion);

    while (generaciones < limiteGeneraciones && !solucion) {
      List<Individuo> nuevaPoblacion = new ArrayList<Individuo>();

      // Ordenar población de mayor a menor fitness para seleccionar un porcentage de
      // los mejores
      // y agregarlos a la nueva población
      List<Individuo> poblacionIgual = algoritmoGenetico.getPoblacion()
          .stream()
          .sorted(Comparator.comparingInt(Individuo::getFitness).reversed())
          .limit(elite)
          .collect(Collectors.toList());
      for (Individuo individuoIgual : poblacionIgual) {
        nuevaPoblacion.add(individuoIgual);
      }
      // sumar 1 al numero de generaciones sin mejora si los dos mejores individuos
      // tienen el mismo fitness
      if (poblacionIgual.get(0).getFitness() == poblacionIgual.get(1).getFitness()) {
        generacionesSinMejora++;
      }

      // hacer el proceso cruce y mutación de los individuos restantes por completar
      // el numero
      // de la población
      for (int i = elite; i < poblacionSize; i += 2) {
        // Seleccionar individuo
        Individuo individuoPadre1 = algoritmoGenetico.cogerIndividuo();
        Individuo individuoPadre2 = algoritmoGenetico.cogerIndividuo();

        Individuo[] nuevoIndividuos = algoritmoGenetico.MezclarIndividuos(individuoPadre1, individuoPadre2);
        algoritmoGenetico.comprobarMutar(nuevoIndividuos[0]);
        algoritmoGenetico.comprobarMutar(nuevoIndividuos[1]);
        nuevaPoblacion.add(nuevoIndividuos[0]);
        nuevaPoblacion.add(nuevoIndividuos[1]);

      }

      // setear la nueva población
      algoritmoGenetico.setPoblacion(nuevaPoblacion);

      // Evaluar si el individuo es solución
      solucion = algoritmoGenetico.buscarRes();
      generaciones++;
      generacionesReales++;

      // si no hay mejora en 5000 generaciones, se regenera la población
      if (generacionesSinMejora > 5000) {
        List<Individuo> poblacionRegenerada = new ArrayList<Individuo>();
        poblacionRegenerada = algoritmoGenetico.generarPoblacion();
        algoritmoGenetico.setPoblacion(poblacionRegenerada);
        generacionesSinMejora = 0;
        System.out.println("Poblacion regenerada");
        datos.add("Población regenerada");
        generacionesReales = 0;
      }

      if (generaciones % 500 == 0 || solucion) {
        System.out.print("Generación: " + generaciones);

        int fitnessPromedio = 0;
        for (Individuo var : algoritmoGenetico.getPoblacion()) {
          fitnessPromedio += var.getFitness();
        }
        System.out.print(" Fitness promedio: " + fitnessPromedio /
            algoritmoGenetico.getPoblacion().size());
        Individuo mejorIndividuo = algoritmoGenetico.buscarMejor();
        System.out.println(" Mejor fitness: " + mejorIndividuo.getFitness());
        datos.add("Generacion: " + generaciones + "," + " Fitness promedio: " + fitnessPromedio /
            algoritmoGenetico.getPoblacion().size() + "," + " Mejor fitness: " + mejorIndividuo.getFitness());

      }
    }

    if (solucion)

    {
      System.out.println("Solucion encontrada");
      System.out.println(algoritmoGenetico.buscarRes());
      System.out.println("Generacion: " + generacionesReales);
      datos.add("Solucion encontrada");
      datos.add(algoritmoGenetico.getRes().toString());
      datos.add("Generacion: " + generacionesReales);
    } else {
      System.out.println("Solucion no encontrada");
      Individuo mejorIndividuo = algoritmoGenetico.buscarMejor();
      System.out.println(mejorIndividuo);
      System.out.println("Fitness: " + mejorIndividuo.getFitness());
      datos.add("Solucion no encontrada");
      datos.add(mejorIndividuo.toString());
      datos.add("Fitness: " + mejorIndividuo.getFitness());
    }
    try {
      File file = new File("output" + ".txt");
      FileWriter w = new FileWriter(file, false);
      BufferedWriter bufferWriter = new BufferedWriter(w);

      // Escribir encabezados de las columnas
      // bufferedWriter.write("Generacion,Fitness promedio,Mejor fitness\n");

      for (String fila : datos) {
        bufferWriter.write(fila + "\n");
      }

      bufferWriter.close();
      System.out.println("Archivo creado correctamente.");

      // Cerrar BufferedWriter
      bufferWriter.close();
      System.out.println("Archivo exportado correctamente.");

    } catch (IOException e) {
      System.err.println("Error al creado archivo: " + e.getMessage());
    }

  }

}
