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

    double ratioIndividuo = 0.05;
    double ratioMutacion = 0.7;
    double ratioHijo = 0.7;
    int poblacionSz = 150;
    int generaciones = 0;
    int limiteGeneraciones = 80000;
    boolean sol = false;
    int[][] matrixSudoku;
    int elite = (int) Math.round(poblacionSz * ratioIndividuo);
    int generacionesSinMejora = 0;
    int generacionesReales = 0;
    List<String> datos = new ArrayList<>();

    Difficulty difficulty = Difficulty.EASY;
    matrixSudoku = SudokuGenerador.computePuzzleByDifficultyAndFitness(difficulty, 75, 80);

    System.out.println("Sudoku generado: ");
    System.out.println(SudokuGenerador.printMatrix(matrixSudoku));
    datos.add("Sudoku generado: ");
    datos.add(SudokuGenerador.printMatrix(matrixSudoku));
    Individuo individuo = new Individuo(matrixSudoku);
    System.out.println("Fitness: " + individuo.getFitness());
    datos.add("Fitness: " + individuo.getFitness());
    AlgoritmoGenetico algoritmoGenetico = new AlgoritmoGenetico(matrixSudoku,
        poblacionSz, ratioHijo, ratioMutacion);

    while (generaciones < limiteGeneraciones && !sol) {
      List<Individuo> nuevaPoblacion = new ArrayList<Individuo>();
      List<Individuo> poblacionIgual = algoritmoGenetico.getPoblacion()
          .stream()
          .sorted(Comparator.comparingInt(Individuo::getFitness).reversed())
          .limit(elite)
          .collect(Collectors.toList());
      for (Individuo individuoIgual : poblacionIgual) {
        nuevaPoblacion.add(individuoIgual);
      }
      if (poblacionIgual.get(0).getFitness() == poblacionIgual.get(1).getFitness()) {
        generacionesSinMejora++;
      }
      for (int i = elite; i < poblacionSz; i += 2) {
        Individuo individuoPadre1 = algoritmoGenetico.seleccionarIndividuo();
        Individuo individuoPadre2 = algoritmoGenetico.seleccionarIndividuo();

        Individuo[] nuevoIndividuos = algoritmoGenetico.cruzarIndividuos(individuoPadre1, individuoPadre2);
        algoritmoGenetico.mutarIndividuoVerificar(nuevoIndividuos[0]);
        algoritmoGenetico.mutarIndividuoVerificar(nuevoIndividuos[1]);
        nuevaPoblacion.add(nuevoIndividuos[0]);
        nuevaPoblacion.add(nuevoIndividuos[1]);

      }

      algoritmoGenetico.setPoblacion(nuevaPoblacion);
      sol = algoritmoGenetico.buscarSolucion();
      generaciones++;
      generacionesReales++;
      if (generacionesSinMejora > 5000) {
        List<Individuo> poblacionRegenerada = new ArrayList<Individuo>();
        poblacionRegenerada = algoritmoGenetico.generarPoblacion();
        algoritmoGenetico.setPoblacion(poblacionRegenerada);
        generacionesSinMejora = 0;
        System.out.println("Poblacion regenerada");
        datos.add("Población regenerada");
        generacionesReales = 0;
      }

      if (generaciones % 500 == 0 || sol) {
        System.out.print("Generacion: " + generaciones);
        int fitnessPromedio = 0;
        for (Individuo var : algoritmoGenetico.getPoblacion()) {
          fitnessPromedio += var.getFitness();
        }
        System.out.print(" Fitness promedio: " + fitnessPromedio /
            algoritmoGenetico.getPoblacion().size());
        Individuo mejorIndividuo = algoritmoGenetico.mejorIndividuo();
        System.out.println(" Mejor fitness: " + mejorIndividuo.getFitness());
        datos.add("Generacion: " + generaciones + "," + " Fitness promedio: " + fitnessPromedio /
            algoritmoGenetico.getPoblacion().size() + "," + " Mejor fitness: " + mejorIndividuo.getFitness());

      }
    }

    if (sol)

    {
      System.out.println("Solucion encontrada");
      System.out.println(algoritmoGenetico.getSolucion());
      System.out.println("Generacion: " + generacionesReales);
      datos.add("Solucion encontrada");
      datos.add(algoritmoGenetico.getSolucion().toString());
      datos.add("Generacion: " + generacionesReales);
    } else {
      System.out.println("Solucion no encontrada");
      Individuo mejorIndividuo = algoritmoGenetico.mejorIndividuo();
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

      for (String fila : datos) {
        bufferWriter.write(fila + "\n");
      }
      bufferWriter.close();
      System.out.println("Archivo creado correctamente.");
    } catch (IOException e) {
      System.err.println("Error al creado archivo: " + e.getMessage());
    }

  }

}
