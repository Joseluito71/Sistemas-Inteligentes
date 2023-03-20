public class NODO{
    
    private int x;
    private int y;
    private int peso;
    protected NODO nodo;

    public NODO(int x1,int y1,int x2,int y2) {
        x=x1;
        y=y1;
        peso= Math.abs(x1-x2)+ Math.abs(y1-y2);
    }

    public NODO(int x1,int y1,int x2,int y2, NODO nodo1) {
        this(x1,x2,y1,y2);
        nodo=nodo1;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public int getPeso(){
        return peso;
    }

    public void cambiarPeso(int p) {
        peso= peso+p;
    }

}