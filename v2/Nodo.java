package v2;

public class Nodo implements Comparable<Nodo>{
    private int corX;
    private int corY;
    private int coste;
    private Nodo padre;
    private double estimacion;

    public Nodo (int X, int Y, Nodo p, int g, double h){
        corX = X;
        corY = Y;
        coste = g;
        estimacion = h;
        padre = p;
    }

    public int getX () {
        return corX;
    }

    public int getY () {
        return corY;
    }

    public int getCoste () {
        return coste;
    }

    public Nodo getPadre () {
        return padre;
    }

    public double getEstimacion(){
        return estimacion;
    }

    @Override
    public boolean equals(Object o){
        boolean res = false;
        if (o instanceof Nodo){
            res = (((Nodo) o).getX() == corX && ((Nodo) o).getY() == corY);
        }
        
        return res;  
    }

    @Override
	public int compareTo (Nodo o) {
        if (o.getX() != corX || o.getY() != corY) {
            if ((coste + estimacion) < (o.getCoste() + o.getEstimacion())) {
                return -1;
            }else if ((coste + estimacion) > (o.getCoste() + o.getEstimacion())){
                return 1;
            }else{
                return 0;
            }
        }else{
            return 0;
        }
		//return (int)((coste + estimacion) - (o.getCoste() + o.getEstimacion()));
	}

    @Override
    public String toString () {
        return "(" + corX + ", " + corY + ")";
        //+ " /Coste: " + coste + " /Estimacion: " + estimacion;
    }
}