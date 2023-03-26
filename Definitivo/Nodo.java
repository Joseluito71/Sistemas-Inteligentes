package Definitivo;

public class Nodo implements Comparable<Nodo> {
    
    private int x;
    private int y;
     private Nodo nodo;
    private int coste;
    private double heuristico;

    public Nodo(int x,int y, Nodo n, int g, double h){
        this.x=x;
        this.y=y;
        this.nodo=n;
        this.coste=g;
        this.heuristico=h;
    }
    public int getX(){
        return this.x;
    }

    public int getY(){
            return this.y;
        }

    public Nodo getNodo(){
            return this.nodo;
        }

    public int getCoste(){
            return this.coste;
        }
        
    public double getHeuristico(){
        return this.heuristico;
    }

    @Override
    public boolean equals(Object o){
        boolean res= false;
        if (o instanceof Nodo){
            res= (((Nodo) o).getX()== this.x &&((Nodo) o).getY()==this.y );
        }
        return res;
    }
    
    @Override
    public int compareTo (Nodo n){
        if (n.getX() != this.x || n.getY()!=this.y){
            if((this.coste+this.heuristico) < (n.getCoste()+n.getHeuristico())){
                return -1;
            } else if ((this.coste+this.heuristico) > (n.getCoste()+n.getHeuristico())){
                return 1;
            }else {
                return 0;
            }
        } else {
            return 0;
        }

    }
}
