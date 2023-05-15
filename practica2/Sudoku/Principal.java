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

	double ratioHijo = 0.83;
    double ratioMutacion = 0.9;
    double ratioIndividuo = 0.07;
    
    
    int tamPoblacion = 150;
    
    int numIteraciones = 0;
    int limIteraciones = 75000;
    
    boolean solEncontrada = false;
    
    int[][] matrizSudo;

    int pobXindiv = (int) Math.round(tamPoblacion*ratioIndividuo);
    
    int noMejora = 0;
    
    List<String> dLista = new ArrayList<>();

    Difficulty dificultad = Difficulty.EASY;

    int [] lista = SudokuGenerador.calcularPuzle(dificultad);
    matrizSudo = SudokuGenerador.arrayToSudoku(lista);

    System.out.println("Sudoku generado: \n");
    dLista.add("Sudoku generado: ");
    
    System.out.println(SudokuGenerador.dibujarMatriz(matrizSudo));
    dLista.add(SudokuGenerador.dibujarMatriz(matrizSudo));
    
    Individuo individuo = new Individuo(matrizSudo);
    
    System.out.println(" Fitness: " + individuo.getFitness()+" \n");
    dLista.add(" Fitness: " + individuo.getFitness()+"\n");
    
    System.out.println(" ************************************ \n");
    dLista.add(" Fitness: " + individuo.getFitness()+"\n");

    AlgoritmoGenetico alGen = new AlgoritmoGenetico(matrizSudo, tamPoblacion, ratioHijo, ratioMutacion);

    while (numIteraciones < limIteraciones && !solEncontrada) {
    	
      List<Individuo> pNueva = new ArrayList<Individuo>();


      List<Individuo> noCambiaPoblacion = alGen.getPoblacion()
          .stream()
          .sorted(Comparator.comparingInt(Individuo::getFitness).reversed())
          .limit(pobXindiv)
          .collect(Collectors.toList());
      
     
      if (noCambiaPoblacion.get(0).getFitness() == noCambiaPoblacion.get(1).getFitness()) {
    	  
        noMejora++;
        
      }
      
      for (Individuo individuoIgual : noCambiaPoblacion) {
    	  
          pNueva.add(individuoIgual);
          
        }

      for (int i = pobXindiv; i < tamPoblacion; i += 2) {

        Individuo padre1 = alGen.cogerIndividuo();
        Individuo padre2 = alGen.cogerIndividuo();

        Individuo[] actIndividuos = alGen.MezclarIndividuos(padre1, padre2);
        alGen.comprobarMutar(actIndividuos[0]);
        alGen.comprobarMutar(actIndividuos[1]);
        pNueva.add(actIndividuos[0]);
        pNueva.add(actIndividuos[1]);

      }


      alGen.setPoblacion(pNueva);
      solEncontrada = alGen.buscarRes();
      numIteraciones++;

      if (noMejora > 3000) {
    	  
        List<Individuo> poblacionRegenerada = new ArrayList<Individuo>();
        
        poblacionRegenerada = alGen.generarPoblacion();
        alGen.setPoblacion(poblacionRegenerada);
        
        System.out.println("   ¡NUEVA POBLACION!   \n");
        dLista.add("   ¡¡NUEVA POBLACION!!   \n");
        
        noMejora = 0;
        
      }

      if (solEncontrada || numIteraciones % 1500 == 0) {
    	  
        System.out.print(" Generación: " + numIteraciones+"\n");
        dLista.add(" Generación: " + numIteraciones+"\n");
        
        int fitnessPromedio = 0;
        
        for (Individuo variable : alGen.getPoblacion()) {
        	
          fitnessPromedio = fitnessPromedio + variable.getFitness();
        }
        
        
        System.out.print(" Media Fitness: " + fitnessPromedio/alGen.getPoblacion().size()+"\n");
        dLista.add(" Media Fitness: " + fitnessPromedio/alGen.getPoblacion().size()+"\n");
        
        Individuo mejorIndividuo = alGen.buscarMejor();
        
        System.out.println(" Mejor Fitness: " + mejorIndividuo.getFitness()+"\n");
        dLista.add(" Mejor Fitness: " + mejorIndividuo.getFitness()+"\n");
        
        System.out.println(" ************************************ \n");
        dLista.add(" ************************************ \n");
        
      }
    }

    if (solEncontrada)

    {
      System.out.println("SOLUCION ENCONTRADA\n");
      dLista.add("SOLUCION ENCONTRADA\n");
      
      alGen.buscarRes();
 
      System.out.println(alGen.getRes().toString());
      dLista.add(alGen.getRes().toString());
      
    } else {
    	
      System.out.println("NO HA SIDO POSIBLE ENCONTRAR SOLUCION\n");
      dLista.add("NO HA SIDO POSIBLE ENCONTRAR SOLUCION\n");
      
      Individuo mejorIndividuo = alGen.buscarMejor();
      
      System.out.println(mejorIndividuo);
      dLista.add(mejorIndividuo.toString());
      
      System.out.println("Fitness: " + mejorIndividuo.getFitness());    
      dLista.add("Fitness: " + mejorIndividuo.getFitness());
    }
    
    try {
    	
      File file = new File("output"+".txt");
      FileWriter fWriter = new FileWriter(file, false);
      BufferedWriter bWriter = new BufferedWriter(fWriter);

      for (String fila : dLista) {
    	  
        bWriter.write(fila + "\n");
        
      }

      bWriter.close();
      System.out.println("El archivo ha sido creado");


      bWriter.close();
      System.out.println("El archivo ha sido exportado");

    } catch (IOException error) {
    	
      System.err.println("Error creando el archivo: " + error.getMessage());
      
    }

  }

}
