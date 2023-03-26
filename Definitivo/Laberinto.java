package Definitivo;

import java.util.Random;
import java.util.StringJoiner;

public class Laberinto {

    private static final char SALIDA= 'I';
    private static final char FIN= 'G';
    private static final char OBSTACULO= '\u2588';
    private static final char LIBRE= ' ';
    private static final char OPTIMO= '+';
    private static final char ABIERTO='A';
    private static final char CERRADO= 'C';
    private int filas;
    private int columnas;
    private char[] [] lab;
    private int porcentaje=30;
    private int salidaX,salidaY,finX,finY;

    public Laberinto(int f, int c){
        this.filas=f;
        this.columnas=c;
        lab= new char [this.filas] [this.columnas];
        //Inicializar a vacio 
        for (int i=0; i<this.filas;i++){
            for(int j=0;j<this.columnas;j++){
                lab[i][j]= LIBRE;
            }
        }
    }
    public int getFilas(){
        return this.filas;
    }
    public int getColumnas(){
        return this.columnas;
    }
    public int getSalidaX(){
        return this.salidaX;
    }
    public int getSalidaY(){
        return this.salidaY;
    }
    public int getFinX(){
        return this.finX;
    }
    public int getFinY(){
        return this.finY;
    }
    public int getPorcentaje(){
        return this.porcentaje;
    }

    public void inicializar(){

        ponerInicio();
        ponerFinal();
        ponerObstaculos();

        /* 
        Random rand= new Random();
        int tamaño= this.filas*this.columnas;
        int nObstaculos= (int) (tamaño*this.porcentaje/100);
        int obstaculoX,obstaculoY;
        //ponemos inicio
        salidaX=rand.nextInt(filas-1);
        salidaY=rand.nextInt(columnas-1);
        lab[salidaX][salidaY]=SALIDA;
        //ponemos final
        finX=rand.nextInt(filas-1);
        finY=rand.nextInt(columnas-1);
        boolean superPosicion=salidaX==finX && salidaY==finY;
        if (superPosicion){
            while(superPosicion){
                finX=rand.nextInt(filas-1);
                finY=rand.nextInt(columnas-1);
            }
            lab[finX][finY]=FIN;
        }else{
            lab[finX][finY]=FIN;
        }
        //ponemos obstaculos
        for (int i=0;i<nObstaculos;i++){
            obstaculoX=rand.nextInt(filas-1);
            obstaculoY=rand.nextInt(columnas-1);
            while(lab[obstaculoX][obstaculoY]!=LIBRE){
            obstaculoX=rand.nextInt(filas-1);
            obstaculoY=rand.nextInt(columnas-1);
            }
            lab[obstaculoX][obstaculoY]=OBSTACULO;
        }
            */

    }

    private void ponerInicio(){
        Random rand= new Random();
        salidaX = rand.nextInt(filas-1);
        salidaY=rand.nextInt(columnas-1);
        lab[salidaX][salidaY] = SALIDA;
    }

    private void ponerFinal(){
        Random rand= new Random();
        finX = rand.nextInt(filas-1);
        finY=rand.nextInt(columnas-1);
        boolean superPosicion=salidaX==finX && salidaY==finY;
        while(superPosicion){
            finX=rand.nextInt(filas-1);
            finY=rand.nextInt(columnas-1);
        }
        lab[finX][finY]=FIN;
    }

    private void ponerObstaculos(){
        Random rand= new Random();
        int tamaño= this.filas*this.columnas;
        int nObstaculos= (int) (tamaño*this.porcentaje/100);
        int obstaculoX,obstaculoY;

        for (int i=0;i<nObstaculos;i++){
            obstaculoX=rand.nextInt(filas-1);
            obstaculoY=rand.nextInt(columnas-1);
            while(lab[obstaculoX][obstaculoY]!=LIBRE){
            obstaculoX=rand.nextInt(filas-1);
            obstaculoY=rand.nextInt(columnas-1);
            }
            lab[obstaculoX][obstaculoY]=OBSTACULO;
        }

    }

    public boolean libre(int x,int y){
        return (lab[x][y]==LIBRE || lab[x][y]==FIN);
    }

    public void ponerAbierto(int x,int y){
        if(libre(x, y) && lab[x][y]!=FIN){
            lab[x][y]=ABIERTO;
        }
    }

    public void ponerCerrado(int x,int y){
        if(lab[x][y]==ABIERTO){
            lab[x][y]=CERRADO;
        }
    }

    public boolean esObstaculo(int x,int y){
        return (lab[x][y]==OBSTACULO);
    }

    public boolean esFin(Nodo n){
        return (n.getX()==finX && n.getY()==finY);
    }

    public void mostrar(){
        System.out.println(this.toString());
    }

    @Override
    public String toString(){

        StringJoiner str= new StringJoiner("\n");
        for(int i=0;i<filas;i++){
            String linea="";
            for(int j=0;j<columnas;j++){
//                if(lab[i][j]==ABIERTO || lab[i][j]==CERRADO){
//                    linea+=LIBRE;
//                }else{
//                    linea+= lab[i][j]
//                }
                linea+= lab[i][j];
            }
            str.add(linea);
        }
        return str.toString();
    }


}
