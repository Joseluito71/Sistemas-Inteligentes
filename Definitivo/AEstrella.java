package Definitivo;
import java.util.*;

public class AEstrella {

    private Laberinto lab;
    private PriorityQueue<Nodo> Abiertos;
    private PriorityQueue<Nodo> Cerrados;

    public AEstrella(Laberinto laberinto){
        this.lab=laberinto;
        Abiertos= new PriorityQueue<Nodo>();
        Cerrados= new PriorityQueue<Nodo>();
    }

    public double calcularHeuristico(int x, int y){
        return (((Math.abs(lab.getFinX()-x))+(Math.abs(lab.getFinY()-y))));
    }

    public void anyadirVecinos(Nodo actual){
        int X1=actual.getX();
        int Y1=actual.getY();
        Nodo posVecino;

        if (X1+1<lab.getFilas() && lab.libre(X1+1, Y1)){
            posVecino= new Nodo(X1+1, Y1, actual, actual.getCoste()+1, calcularHeuristico(X1+1, Y1));
            if (!Cerrados.contains(posVecino)){
                Abiertos.add(posVecino);
                lab.ponerAbierto(X1+1, Y1);
            }
        }

        if (X1-1>=0 && lab.libre(X1-1, Y1)){
            posVecino= new Nodo(X1-1, Y1, actual, actual.getCoste()+1, calcularHeuristico(X1-1, Y1));
            if (!Cerrados.contains(posVecino)){
                Abiertos.add(posVecino);
                lab.ponerAbierto(X1-1, Y1);
            }
        }

        if (Y1+1<lab.getColumnas() && lab.libre(X1, Y1+1)){
            posVecino= new Nodo(X1, Y1+1, actual, actual.getCoste()+1, calcularHeuristico(X1, Y1+1));
            if (!Cerrados.contains(posVecino)){
                Abiertos.add(posVecino);
                lab.ponerAbierto(X1, Y1+1);
            }
        }        

        if (Y1-1>=0 && lab.libre(X1, Y1-1)){
            posVecino= new Nodo(X1, Y1-1, actual, actual.getCoste()+1, calcularHeuristico(X1, Y1-1));
            if (!Cerrados.contains(posVecino)){
                Abiertos.add(posVecino);
                lab.ponerAbierto(X1, Y1-1);
            }
        }        
    }

    private ArrayList<Nodo> Backtraking(Nodo actual){
        ArrayList<Nodo> res= new ArrayList<>();
        Nodo siguiente=actual;
        while(siguiente!=null){
            res.add(siguiente);
            siguiente=siguiente.getNodo();
        }
        return res;
    }

    public ArrayList<Nodo> calcularCamino(){
        Abiertos.clear();
        Cerrados.clear();//Inicializamos los sets de nodos
        boolean hemosTerminado=false;
        ArrayList<Nodo> camino= new ArrayList<>();
        Abiertos.add(new Nodo(this.lab.getSalidaX(), this.lab.getSalidaY(), null, 0, 0));
        while (!Abiertos.isEmpty() && !hemosTerminado ){
            Nodo actual= Abiertos.poll();
            Cerrados.add(actual);
            lab.ponerCerrado(actual.getX(), actual.getY());
            if (!lab.esFin(actual)){
                anyadirVecinos(actual);
            } else{
                hemosTerminado=true;
                camino= Backtraking(actual);
            }

        }
        return camino;
    }
}
