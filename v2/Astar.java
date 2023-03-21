package v2;
import java.util.*;

public class Astar{
    
    private Laberinto problema;
    private int heuristico;// 1 -> h = 0 |  2 -> h = distManhatan | 3 -> h = distEuclidea
    PriorityQueue<Nodo> Abiertos;
    PriorityQueue<Nodo> Cerrados;

    public Astar(Laberinto laberinto, int tipoHeuristico){
        problema = laberinto;
        heuristico = tipoHeuristico;
        Abiertos = new PriorityQueue<Nodo>();
        Cerrados = new PriorityQueue<Nodo>();
    }

    public double h(int x, int y){// 1 -> h = 0 |  2 -> h = distManhatan | 3 -> h = distEuclidea
        int finX = problema.getFinX();
        int finY = problema.getFinY();
        double res = 0;
        if (heuristico == 2){
            res = Math.abs(finX - x) + Math.abs(finY - y); 
        } else if (heuristico == 3){
            res = Math.sqrt(Math.pow((finX - x), 2) + (Math.pow((finY - y),2)));
            //System.out.println("x: " + x + "/y: " + y + "/Res: " + res);
        }

        return res;
    }

    public ArrayList<Nodo> resolver(){
        Abiertos.clear();
        Cerrados.clear();
        boolean encontradaSolucion = false;
        ArrayList<Nodo> sol = new ArrayList<>();  

        Abiertos.add(new Nodo(this.problema.getInicX(), problema.getInicY(), null, 0, 0));

        while (!Abiertos.isEmpty() && !encontradaSolucion){
            Nodo n = Abiertos.poll();
            
            Cerrados.add(n);
            problema.marcarCerrado(n.getX(), n.getY());
            
            if (!problema.esObjetivo(n)) {
                sucesores(n);
            } else {
                encontradaSolucion = true;
                sol = reconstruirSolucion(n);
             //   System.out.println(n);
            }
           // System.out.println("-------------------------------------");
            //problema.mostrarLaberinto();
        }
        
        return sol;
    }

    private ArrayList<Nodo> reconstruirSolucion(Nodo n) {

        ArrayList<Nodo> sol = new ArrayList<>();
        Nodo sig = n;

        while (sig != null) {
            sol.add(sig);
            sig = sig.getPadre();
        }

        return sol;
    }

    private void sucesores (Nodo actual) {

        int posX = actual.getX();
        int posY = actual.getY();
        Nodo nuevo;

        if(posX + 1 < problema.getDimX() && problema.estaLibre(posX+1, posY)) { //Derecha
            
            nuevo = new Nodo(posX + 1, posY, actual, actual.getCoste() + 1, h(posX + 1, posY));

            if (!Cerrados.contains(nuevo)) { //Comprueba que el nodo nuevo no se ha explorado antes
                Abiertos.add(nuevo);//Si el nodo nuevo ya esta en la lista de abiertos, no se añadirá ya que el que esta dentro de la lista de abiertos debería ser mejor
                problema.marcarAbierto(posX + 1, posY);
            }

        }

        if(posX-1 >= 0 && problema.estaLibre(posX-1, posY)) { //Izquierda
            
            nuevo = new Nodo(posX - 1, posY, actual, actual.getCoste() + 1, h(posX - 1, posY));

            if (!Cerrados.contains(nuevo)) {
                Abiertos.add(nuevo);
                problema.marcarAbierto(posX - 1, posY);
            }

        } 

        if(posY + 1 < problema.getDimY() && problema.estaLibre(posX, posY + 1)) { //Abajo
            
            nuevo = new Nodo(posX, posY + 1, actual, actual.getCoste() + 1, h(posX, posY + 1));

            if (!Cerrados.contains(nuevo)) {
                Abiertos.add(nuevo);
                problema.marcarAbierto(posX, posY + 1);
            }

        }

        if(posY - 1 >= 0 && problema.estaLibre(posX, posY - 1)) { //Arriba
            
            nuevo = new Nodo(posX, posY - 1, actual, actual.getCoste() + 1, h(posX, posY - 1));

            if (!Cerrados.contains(nuevo)) {
                Abiertos.add(nuevo);
                problema.marcarAbierto(posX, posY - 1);
            }

        }
        //System.out.println(Abiertos);
    }
}