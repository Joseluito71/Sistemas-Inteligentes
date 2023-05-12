package sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Gen implements Comparable<Gen>{
	private List<List<Integer>> gen;
	private static Random rand=new Random();
	private int fitness;
	
	public Gen(Sudokux sudo) {
		gen=generarFormato(sudo);
		fitness=0;
	}
	public Gen(Gen other, boolean mix) {
		gen=other.copy().getGen();
		if(mix)permutarGen();
		fitness=0;
	}
	private Gen (List<List<Integer>> genCopy) {
		gen=genCopy;
		fitness=0;
	}
	private void permutarGen() {
		for(int f=0; f<gen.size();f++) {
			List<Integer> dominioFila=generarDominio(f);
			for(int c=0; c<gen.get(f).size();c++) {
				int pos=rand.nextInt(0,dominioFila.size());
				gen.get(f).add(c, dominioFila.get(pos));
				gen.get(f).remove(c+1);
				dominioFila.remove(pos);
			}
		}
	}
	
	private List<List<Integer>> generarFormato(Sudokux sudo) {
		int fil=0;
		int[] completo=sudo.getPuzzle();
		List<List<Integer>> sol=new ArrayList<>();
		sol.add(new ArrayList<>());
		List<Integer> dominioFila=generarDominio(sudo, 0);
		for(int i=0; i<Sudokux.getBoardSize();i++) {
			if(i%9==0 && i!=0) {
				fil++;
				sol.add(new ArrayList<>());
				dominioFila=generarDominio(sudo,fil);	
			}
			if(completo[i]==0) {
				sol.get(fil).add(dominioFila.get(0));
				dominioFila.remove(0);
			}	
		}
		//System.out.println(sol.toString());
		return sol;
	}
	private List<Integer> generarDominio(int fil){
		List<Integer> sol=new ArrayList<>();
		for(int i=0; i<gen.get(fil).size();i++) {
			sol.add(gen.get(fil).get(i));
		}
		return sol;
	}
	private List<Integer> generarDominio(Sudokux sudo, int fil) {
		List<Integer> sol=new ArrayList<>(List.of(1,2,3,4,5,6,7,8,9));
		int[] puzzle=sudo.getPuzzle();
		for(int i=9*fil;i<9*(fil+1);i++) {
			int pos=sol.indexOf(puzzle[i]);
			if(pos>=0) {
				sol.remove(pos);
			}
		}
		
		return sol;
	}
	public Gen copy() {
		List<List<Integer>> genCopy=copyGen(this.gen);
		return new Gen(genCopy);
	}
	public List<List<Integer>> getGen() {
		return gen;
	}
	public Integer get(int f, int c) {
		return gen.get(f).get(c);
	}
	@Override
	public String toString() {
		return gen.toString();
	}
	@Override
	public boolean equals(Object o) {
		boolean ok=false;
		if(o instanceof Gen) {
			ok=((Gen) o).getGen().equals(getGen());
		}
		return ok;
	}
	@Override
	public int hashCode() {
		return gen.hashCode();
	}
	public static Gen[] crossover(Gen[] individuos) {
		int nCruces=individuos[0].getGen().size()-1;
		if(nCruces>0) {
			for(int i=0; i<individuos.length;i=i+2) {
				int corte=rand.nextInt(0,nCruces);
				List<List<Integer>> aux=copyGen(individuos[i].gen.subList(corte+1, nCruces+1));
				for(int f=corte+1;f<nCruces+1;f++) individuos[i].gen.set(f, individuos[i+1].gen.get(f));
				for(int f=corte+1, c=0;f<nCruces+1;f++,c++) individuos[i+1].gen.set(f, aux.get(c));
			}
		}
		return individuos;
	}
	private static List<List<Integer>> copyGen(List<List<Integer>> other){
		List<List<Integer>> sol=new ArrayList<>();
		for(int f=0; f<other.size();f++) {
			sol.add(new ArrayList<>());
			for(int c=0; c<other.get(f).size();c++) {
				sol.get(f).add(other.get(f).get(c));
			}
		}
		return sol;
	}
	public static Gen[] mutacion(Gen[] poblacion) {
		for(int i=0; i<poblacion.length;i++) {
			int eleccionFila=rand.nextInt(0,poblacion[0].getGen().size());
			int intercambio1=rand.nextInt(0,poblacion[i].getGen().get(eleccionFila).size());
			int intercambio2=intercambio1;
			while(intercambio1==intercambio2) intercambio2=rand.nextInt(0,poblacion[i].getGen().get(eleccionFila).size());
			int aux=poblacion[i].getGen().get(eleccionFila).get(intercambio1);
			poblacion[i].getGen().get(eleccionFila).set(intercambio1,poblacion[i].getGen().get(eleccionFila).get(intercambio2));
			poblacion[i].getGen().get(eleccionFila).set(intercambio2,aux);
		}
		return poblacion;
	}
	@Override
	public int compareTo(Gen o) {
		return ((Integer)fitness).compareTo(o.fitness);
	}
	public void refreshFitness(Sudokux sudo) {
		int[] puzzle=toArray(sudo);
		//sudo.setPuzzle(puzzle);
		//System.out.print(sudo.toString());
		fitness=comprobarColumnas(puzzle)+comprobarCasillas(puzzle);
		
	}
	private int comprobarColumnas(int[] puzzle) {
		int sol=0;
		for(int f=0; f<Sudokux.getRowColSecSize();f++) {
			List<Integer> unicos=new ArrayList<>(), repetidos=new ArrayList<>();
			for(int c=f; c<Sudokux.getBoardSize();c+=Sudokux.getRowColSecSize()) {
				int num=puzzle[c];
				if(unicos.indexOf(num)<0 && repetidos.indexOf(num)<0) unicos.add(num);
				else if(unicos.indexOf(num)>=0 && repetidos.indexOf(num)<0) {
					repetidos.add(num);
					unicos.remove(unicos.indexOf(num));
				}else if(unicos.indexOf(num)>=0 && repetidos.indexOf(num)>=0) {
					throw new RuntimeException("Numero en conjunto de unicos y repetidos");
				}
			}
			sol+=unicos.size();
		}
		return sol;
	}
	private int comprobarCasillas(int[] puzzle) {
		int sol=0;
		int desp=-1*Sudokux.getGridSize();
		for(int i=0; i<Sudokux.getRowColSecSize()/Sudokux.getGridSize();i++) {
			int lim=0;
			desp+=Sudokux.getGridSize();
			for(int g=0; g<Sudokux.getRowColSecSize()/Sudokux.getGridSize();g++) {
				lim+=Sudokux.getGridSize();
				List<Integer> unicos=new ArrayList<>(), repetidos=new ArrayList<>();
				for(int f=lim-Sudokux.getGridSize();f<lim;f+=Sudokux.getRowColSecSize()) {
					for(int c=f+desp;c<lim;c++) {
						int num=puzzle[c];
						if(unicos.indexOf(num)<0 && repetidos.indexOf(num)<0) unicos.add(num);
						else if(unicos.indexOf(num)>=0 && repetidos.indexOf(num)<0) {
							repetidos.add(num);
							unicos.remove(unicos.indexOf(num));
						}else if(unicos.indexOf(num)>=0 && repetidos.indexOf(num)>=0) {
							throw new RuntimeException("Numero en conjunto de unicos y repetidos");
						}
					}
				}
				sol+=unicos.size();
			}
		}
		return sol;
	}
	public int getFitness() {
		return fitness;
	}
	public int[] toArray(Sudokux sudo) {
		int[] sol=sudo.getPuzzle();
		List<List<Integer>> genAux=copyGen(gen);
		for(int f=1;f<gen.size();f++) {
			for(int c=0; c<gen.get(f).size();c++) {
				genAux.get(0).add(genAux.get(f).get(c));
			}
		}
		int f=0;
		for(int i=0;i<sol.length;i++) {
			if(sol[i]==0) {
				sol[i]=genAux.get(0).get(f);
				f++;
			}
		}
		return sol;
	}
	
}
