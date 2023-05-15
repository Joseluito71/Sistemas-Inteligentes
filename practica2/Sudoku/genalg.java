import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class genalg {
    private static int[][] puzzle;
    private static List<int[][]> poblacion;
    private static int numpob = 20;
    private static int numgeneraciones = 1;

    private static void generar() {
        // DIFICULTADES:
        // 1 = 3 huecos por fila
        // 2 = SIMPLE
        // 3 = FACIL
        // 4 = INTERMEDIO
        // 5 = EXTREMO
        puzzle = generacion.nuevoSudoku(4);
        poblacion = new ArrayList<>();
    }

    private static void poblacionInicial(int[][] puzzle) {
        Random rnd = new Random();
        for (int c = 0; c < numpob; c++) {
            List<Integer> cromosomas = new ArrayList<>();
            List<Integer> slots = new ArrayList<>();
            boolean numeroFalta = true;
            int[][] individuo = new int[9][9];
            for (int i = 0; i < individuo.length; i++) {
                individuo[i] = Arrays.copyOf(puzzle[i], puzzle[i].length);
            }

            for (int fila = 0; fila < 9; fila++) {
                for (int num = 1; num <= 9; num++) {
                    numeroFalta = true;
                    int col = 0;
                    while (col < 9 && numeroFalta) {
                        if (individuo[fila][col] == num) {
                            numeroFalta = false;
                        }
                        col++;
                    }
                    if (numeroFalta) {
                        cromosomas.add(num);
                    }
                }
                for (int i = 0; i < 9; i++) {
                    if (individuo[fila][i] == 0) {
                        slots.add(i);
                    }
                }
                int random;
                for (int slot : slots) {
                    random = rnd.nextInt(cromosomas.size());
                    individuo[fila][slot] = cromosomas.get(random);
                    cromosomas.remove(random);
                }
                slots.clear();
            }
            poblacion.add(individuo);
        }
        System.out.println("------------------------------------------------------");
    }

    private static int[][] reproduccion(int[][] padre1, int[][] padre2) {
        Random rnd = new Random();
        int fila = rnd.nextInt(9);
        int[][] nuevoPadre = new int[9][9];
        for (int i = 0; i < fila; i++) {
            nuevoPadre[i] = padre1[i];
        }
        for (int i = fila; i < padre2.length; i++) {
            nuevoPadre[i] = padre2[i];
        }
        return nuevoPadre;
    }

    private static int[][] mutacion(int[][] individuo) {
        Random rnd = new Random();
        int fila = rnd.nextInt(9);
        int celda1 = rnd.nextInt(9);
        int celda2;
        do {
            celda2 = rnd.nextInt(9);
        } while (celda1==celda2);
        int aux = individuo[fila][celda1];
        individuo[fila][celda1] = individuo[fila][celda2];
        individuo[fila][celda2] = aux;

        return individuo;
    }

    private static int fitness(int[][] individuo) {
        boolean esta = false;
        int cont_dist_colum = 0;
        int cont_dist_bloq = 0;

        for (int col = 0; col < 9; col++) {
            for (int num = 1; num < 10; num++) {
                esta = false;
                for (int fila = 0; fila < 9 && !esta; fila++) {
                    if (individuo[fila][col] == num) {
                        esta = true;
                    }
                }
                if (esta) {
                    cont_dist_colum++;
                }
            }
        }

        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 3; col++) {
                for (int num = 1; num < 10; num++) {
                    esta = false;
                    for (int i = 0; i < 3 && !esta; i++) {
                        for (int j = 0; j < 3 && !esta; j++) {
                            if (individuo[fila*3+i][col*3+j] == num) {
                                esta = true;
                            }
                        }
                    }
                    if (esta) {
                        cont_dist_bloq++;
                    }
                }
            }
        }

        int num_fit = (cont_dist_colum + cont_dist_bloq);

        return num_fit;
    }

    private static Map<int[][],Integer> pesos() {
        Map<int[][],Integer> pesos = new HashMap<>();
        for (int[][] individuo : poblacion) {
            pesos.put(individuo, fitness(individuo));
        }
        return pesos;
    }

    private static List<int[][]> seleccion(Map<int[][],Integer> pesos) {
        List<int[][]> padres = new ArrayList<>(2);
        int mejorpeso = 0;
        int pesoacumulado = 0;
        int pesototal = 0;
        for (Integer peso : pesos.values()) {
            pesototal += peso;
            if (peso>mejorpeso) { mejorpeso = peso; }
        }

        // FITNESS MEDIO Y MEJOR FITNESS:
        System.out.println("DATOS DE LA GENERACION " + numgeneraciones + ":");
        System.out.println("FITNESS MEDIO: " + pesototal/numpob);
        System.out.println("MEJOR FITNESS: " + mejorpeso);
        System.out.println("----------------------------------------------");
        numgeneraciones++;

        try{Thread.sleep(1);}catch(InterruptedException e) {}

        int prob = new Random().nextInt(pesototal);

        for (int[][] individuo : pesos.keySet()) {
            pesoacumulado += pesos.get(individuo);
            if (pesoacumulado >= prob) {
                padres.add(0, individuo);
                break;
            }
        }

        prob = new Random().nextInt(100);
        pesoacumulado = 0;

        for (int[][] individuo : pesos.keySet()) {
            pesoacumulado += pesos.get(individuo);
            if (pesoacumulado >= prob && !individuo.equals(padres.get(0))) {
                padres.add(1, individuo);
                break;
            }
        }

        return padres;
    }

    private static int[][] algoritmoGenetico(List<int[][]> poblacion) {
        int fitnessmax = 0;
        int[][] sol = {}, hijo = {}, padre1 = {}, padre2 = {};
        Map<int[][],Integer> pesos;
        List<int[][]> nuevapoblacion;
        
        while (fitnessmax!=162) {
            pesos = pesos();
            nuevapoblacion = new ArrayList<>();
            for (int i = 0; i < poblacion.size(); i++) {
                List<int[][]> padres = seleccion(pesos);
                padre1 = padres.get(0);
                padre2 = padres.get(1);
                hijo = reproduccion(padre1, padre2);
                if (new Random().nextInt(100) < 10) {
                    hijo = mutacion(hijo);
                }
                nuevapoblacion.add(hijo);
            }
            poblacion = nuevapoblacion;

            for (int[][] individuo : poblacion) {
                if (fitnessmax < fitness(individuo)) {
                    fitnessmax = fitness(individuo);
                    sol = individuo;
                }
            }
        }
        System.out.println("SOLUCIÓN DEL SUDOKU");
        return sol;
    }

    private static void mostrar(int[][] individuo) {
        for (int i = 0; i < individuo.length; i++) {
            if (i%3==0 && i!=0 && !(i%9==0)) {
                System.out.println("█████████████████████");
            }
            for (int j = 0; j < individuo[i].length; j++) {
                if (j%3==0 && j!=0 && !(j%9==0)) {
                    System.out.print("█ ");
                }
                System.out.print(individuo[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("------------------");
    }

    public static void main(String[] args) {
        generar();
        mostrar(puzzle);
        poblacionInicial(puzzle);
        mostrar(
            algoritmoGenetico(poblacion)
        );
    }
}
