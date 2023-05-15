import java.util.HashSet;

public class Individuo implements Comparable<Individuo> {
	
  private int[][] genes;
  private int fitness;

  public Individuo(int[][] genes) {
	  
    this.genes = genes;
    this.fitness = calculoFitness();
    
  }

  public int[][] getGenes() {
	  
    return this.genes;
    
  }

  public int getFitness() {
	  
    return fitness;
    
  }

 
  private int calculoFitness() {
    int fitness = 0;
    for (int i = 0; i < 9; i++) {
      int[] columnas = new int[9];
      for (int j = 0; j < 9; j++) {
        columnas[j] = genes[j][i];      
      } 
      fitness = fitness + valoresUnicos(columnas);
    }
    for (int i = 0; i < 9; i += 3) {
      for (int j = 0; j < 9; j += 3) {  
        int[] tresPorTres = new int[9]; 
        int indice = 0;
        for (int k = i; k < i + 3; k++) {        	
          for (int l = j; l < j + 3; l++) {       	  
            tresPorTres[indice] = genes[k][l];
            indice++;          
          }         
        }        
        fitness = fitness + valoresUnicos(tresPorTres);      
      }   
    }
    return fitness;
  }

  private int valoresUnicos(int[] array) {
	  
    HashSet<Integer> valUnico = new HashSet<Integer>();
    
    for (int i = 0; i < array.length; i++) {
    	
      valUnico.add(array[i]);
      
    }
    
    return valUnico.size();
  }


  public int compareTo(Individuo individuo2) {
	  
    return individuo2.fitness - this.fitness;
    
  }

 
  public String toString() {
	  
    String finalResultado = "";
    
    for (int i = 0; i < 9; i++) {
    	
      if (i % 3 == 0) {
    	  
        finalResultado += "█████████████████████████\n"; 
        
      }
      for (int j = 0; j < 9; j++) {
    	  
        if (j % 3 == 0) {
        	
          finalResultado += "█ ";
          
        }
        
        finalResultado = finalResultado + genes[i][j] + " ";
        
      }
      
      finalResultado += "█\n";
    }
    
    finalResultado += "█████████████████████████\n";
    
    return finalResultado;
    
  }

}
