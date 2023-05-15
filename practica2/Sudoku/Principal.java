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
    List<String> datos = new ArrayList<>();

    Difficulty difficulty = Difficulty.EASY;
    // generar sudoku solo por dificultad
    // int[] sudoku = SudokuGenerador.computePuzzleByDifficulty(difficulty);
    // matrixSudoku = SudokuGenerador.arrayToMatrix(sudoku);
    matrixSudoku = SudokuGenerador.computePuzzleByDifficultyAndFitness(difficulty, 75, 80);

    System.out.println("Sudoku generado: \n");
    System.out.println(SudokuGenerador.printMatrix(matrixSudoku));
    datos.add("Sudoku generado: ");
    datos.add(SudokuGenerador.printMatrix(matrixSudoku));
    Individuo individuo = new Individuo(matrixSudoku);
    System.out.println(" Fitness: " + individuo.getFitness()+" \n");
    datos.add(" Fitness: " + individuo.getFitness()+"\n");
    System.out.println(" ************************************ \n");

    AlgoritmoGenetico algoritmoGenetico = new AlgoritmoGenetico(matrixSudoku,
        poblacionSize, tasaCruce, tasaMutacion);

    while (generaciones < limiteGeneraciones && !solucion) {
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

      for (int i = elite; i < poblacionSize; i += 2) {

        Individuo individuoPadre1 = algoritmoGenetico.cogerIndividuo();
        Individuo individuoPadre2 = algoritmoGenetico.cogerIndividuo();

        Individuo[] nuevoIndividuos = algoritmoGenetico.MezclarIndividuos(individuoPadre1, individuoPadre2);
        algoritmoGenetico.comprobarMutar(nuevoIndividuos[0]);
        algoritmoGenetico.comprobarMutar(nuevoIndividuos[1]);
        nuevaPoblacion.add(nuevoIndividuos[0]);
        nuevaPoblacion.add(nuevoIndividuos[1]);

      }


      algoritmoGenetico.setPoblacion(nuevaPoblacion);


      solucion = algoritmoGenetico.buscarRes();
      generaciones++;

      if (generacionesSinMejora > 5000) {
        List<Individuo> poblacionRegenerada = new ArrayList<Individuo>();
        poblacionRegenerada = algoritmoGenetico.generarPoblacion();
        algoritmoGenetico.setPoblacion(poblacionRegenerada);
        generacionesSinMejora = 0;
        System.out.println("   ¡NUEVA POBLACION!   \n");
        datos.add("   ¡¡NUEVA POBLACION!!   \n");
      }

      if (generaciones % 1500 == 0 || solucion) {
        System.out.print(" Generación: " + generaciones+"\n");
        datos.add(" Generación: " + generaciones+"\n");
        int fitnessPromedio = 0;
        for (Individuo var : algoritmoGenetico.getPoblacion()) {
          fitnessPromedio += var.getFitness();
        }
        System.out.print(" Media Fitness: " + fitnessPromedio/algoritmoGenetico.getPoblacion().size()+"\n");
        datos.add(" Media Fitness: " + fitnessPromedio/algoritmoGenetico.getPoblacion().size()+"\n");
        Individuo mejorIndividuo = algoritmoGenetico.buscarMejor();
        System.out.println(" Mejor Fitness: " + mejorIndividuo.getFitness()+"\n");
        datos.add(" Mejor Fitness: " + mejorIndividuo.getFitness()+"\n");
        System.out.println(" ************************************ \n");
        datos.add(" ************************************ \n");
      }
    }

    if (solucion)

    {
      System.out.println("SOLUCION ENCONTRADA\n");
      algoritmoGenetico.buscarRes();
      datos.add("SOLUCION ENCONTRADA\n");
      System.out.println(algoritmoGenetico.getRes().toString());
      datos.add(algoritmoGenetico.getRes().toString());
    } else {
      System.out.println("NO HA SIDO POSIBLE ENCONTRAR SOLUCION\n");
      Individuo mejorIndividuo = algoritmoGenetico.buscarMejor();
      System.out.println(mejorIndividuo);
      System.out.println("Fitness: " + mejorIndividuo.getFitness());
      datos.add("NO HA SIDO POSIBLE ENCONTRAR SOLUCION\n");
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
      System.out.println("El archivo ha sido creado");


      bufferWriter.close();
      System.out.println("El archivo ha sido exportado");

    } catch (IOException e) {
      System.err.println("Error creando el archivo: " + e.getMessage());
    }

  }

}
