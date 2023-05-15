import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

public class AlgoritmoGenetico {
  private List<Individuo> poblacion;
  private int nPoblacion;
  private int[][] sudoku;
  private Individuo res;
  private double ratioHijo;
  private double ratioMutacion;

  public AlgoritmoGenetico(int[][] sudoku, int nPoblacion, double ratioHijo, double ratioMutacion) {
	  
    this.sudoku = sudoku;
    this.nPoblacion = nPoblacion;
    this.poblacion = generarPoblacion();
    this.ratioHijo = ratioHijo;
    this.ratioMutacion = ratioMutacion;
    
  }

 

  public List<Individuo> generarPoblacion() {
	  
    List<Individuo> poblacion = new ArrayList<Individuo>();
    
    for (int i = 0; i < nPoblacion; i++) {
    	
      poblacion.add(generarIndividuo(this.sudoku));
      
    }
    
    return poblacion;
    
  }

  public Individuo generarIndividuo(int[][] sudoku) {
    int[][] sudokuMezclado = new int[9][9];
    for (int i = 0; i < 9; i++) {
    	
      List<Integer> rango = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
      
      for (int j = 0; j < 9; j++) {
    	  
        if (sudoku[i][j] != 0) {
        	
          rango.remove((Integer) sudoku[i][j]);
          
        }
        
      }
      
      Collections.shuffle(rango);
      
      for (int j = 0; j < 9; j++) {
    	  
        if (sudoku[i][j] == 0) {
        	
          sudokuMezclado[i][j] = rango.remove(0);
          
        } else {
        	
          sudokuMezclado[i][j] = sudoku[i][j];
          
        }
        
      }
      
    }
    
    return new Individuo(sudokuMezclado);
    
  }

  public Individuo[] MezclarIndividuos(Individuo individuo1, Individuo individuo2) {
	  
    int[][] hijo1 = new int[9][9];
    int[][] hijo2 = new int[9][9];

    for (int iteratorFila2 = 0; iteratorFila2 < 9; iteratorFila2++) {
    	
      System.arraycopy(individuo1.getGenes()[iteratorFila2], 0, hijo1[iteratorFila2], 0, 9);
      
    }

    for (int iteratorFila2 = 0; iteratorFila2 < 9; iteratorFila2++) {
    	
      System.arraycopy(individuo2.getGenes()[iteratorFila2], 0, hijo2[iteratorFila2], 0, 9);
      
    }

    double randomRatio = Math.random();
    
    if (randomRatio < this.ratioHijo) {
    	
      int randomiteratorFila = (int) (Math.random() * 8);

      for (int iteratorFila2 = 0; iteratorFila2 < randomiteratorFila; iteratorFila2++) {
    	  
        System.arraycopy(individuo1.getGenes()[iteratorFila2], 0, hijo1[iteratorFila2], 0, 9);
        System.arraycopy(individuo2.getGenes()[iteratorFila2], 0, hijo2[iteratorFila2], 0, 9);
        
      }

      for (int iteratorFila2 = randomiteratorFila; iteratorFila2 < 9; iteratorFila2++) {
    	  
        System.arraycopy(individuo2.getGenes()[iteratorFila2], 0, hijo1[iteratorFila2], 0, 9);
        System.arraycopy(individuo1.getGenes()[iteratorFila2], 0, hijo2[iteratorFila2], 0, 9);
        
      }
      
    }
    
    Individuo[] hijos = new Individuo[2];
    hijos[0] = new Individuo(hijo1);
    hijos[1] = new Individuo(hijo2);
    
    return hijos;
  }

  public void comprobarMutar(Individuo individuo) {
	  
    double randomRatio = Math.random();
    
    if (randomRatio < this.ratioMutacion) {
    	
      int hueco1, hueco2, iteratorFila;
      boolean duplicado = false;
      
      do {
    	  
        hueco1 = (int) (Math.random() * 9);
        hueco2 = (int) (Math.random() * 9);
        iteratorFila = (int) (Math.random() * 9);

        if (hueco1 == hueco2 || this.sudoku[iteratorFila][hueco1] != 0 || this.sudoku[iteratorFila][hueco2] != 0) {
        	
          duplicado = false;
          int valor1 = individuo.getGenes()[iteratorFila][hueco1];
          int valor2 = individuo.getGenes()[iteratorFila][hueco2];

          for (int i = 0; i < 9; i++) {
        	  
            if (i != iteratorFila) {
            	
              if (individuo.getGenes()[i][hueco1] == valor2 || individuo.getGenes()[i][hueco2] == valor1) {
            	  
                duplicado = true;
                break;
                
              }
              
            }
            
          }

          if (!duplicado) {
        	  
            int iteratorFilaInicial = (iteratorFila / 3) * 3;
            int columnaInicial1 = (hueco1 / 3) * 3;
            int columnaInicial2 = (hueco2 / 3) * 3;
            
            for (int i = iteratorFilaInicial; i < iteratorFilaInicial + 3; i++) {
            	
              for (int j = columnaInicial1; j < columnaInicial1 + 3; j++) {
            	  
                if (i != iteratorFila || j != hueco1) {
                	
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
            	
              for (int i = iteratorFilaInicial; i < iteratorFilaInicial + 3; i++) {
            	  
                for (int j = columnaInicial2; j < columnaInicial2 + 3; j++) {
                	
                  if (i != iteratorFila || j != hueco2) {
                	  
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
      
      int temp = individuo.getGenes()[iteratorFila][hueco1];
      individuo.getGenes()[iteratorFila][hueco1] = individuo.getGenes()[iteratorFila][hueco2];
      individuo.getGenes()[iteratorFila][hueco2] = temp;
      
    }
    
  }
  


	  public Individuo buscarMejor() {
		  
	    Individuo mejor = this.poblacion.get(0);
	    
	    for (Individuo individuo : this.poblacion) {
	    	
	      if (individuo.getFitness() > mejor.getFitness()) {
	    	  
	        mejor = individuo;
	        
	      }
	      
	    }
	    
	    return mejor;
	    
	  }
	  
  public void mutarIndividuo(Individuo individuo) {
	  
    double randomRatio = Math.random();
    
    if (randomRatio < this.ratioMutacion) {
    	
      int hueco1, hueco2, iteratorFila;
      
      do {
    	  
        hueco1 = (int) (Math.random() * 9);
        hueco2 = (int) (Math.random() * 9);
        iteratorFila = (int) (Math.random() * 9);
        
      } while (hueco1 == hueco2 || this.sudoku[iteratorFila][hueco1] != 0 || this.sudoku[iteratorFila][hueco2] != 0);
      
      int temp = individuo.getGenes()[iteratorFila][hueco1];
      individuo.getGenes()[iteratorFila][hueco1] = individuo.getGenes()[iteratorFila][hueco2];
      individuo.getGenes()[iteratorFila][hueco2] = temp;
      
    }
    
  }

  public Individuo cogerIndividuo() {
	  
    int fitnessTotal = 0;
    
    for (Individuo individuo : this.poblacion) {
    	
      fitnessTotal += individuo.getFitness();
      
    }

    int random = (int) (Math.random() * fitnessTotal);
    int sumaRandom = 0;
    
    for (Individuo individuo : poblacion) {
    	
      sumaRandom += individuo.getFitness();
      
      if (sumaRandom >= random) {
    	  
        return individuo;
        
      }
      
    }
    
    return null;
  }

  public List<Individuo> getPoblacion() {
	  
	    return poblacion;
	    
	  }

	  public void setPoblacion(List<Individuo> poblacion) {
		  
	    this.poblacion = poblacion;
	    
	  }

	  public Individuo getRes() {
	    return res;
	    
	  }
	  public boolean buscarRes() {
		  
		    for (Individuo individuo : this.poblacion) {
		    	
		      if (individuo.getFitness() == 162) {
		    	  
		        this.res = individuo;
		        return true;
		        
		      }
		      
		    }
		    
		    return false;
		    
		  }
}
