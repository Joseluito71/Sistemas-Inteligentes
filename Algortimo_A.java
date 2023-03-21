import java.util.ArrayList;

public class Algortimo_A {

    private laberinto laberinto;
    private ArrayList<NODO> openSet= new ArrayList<>();
    private ArrayList<NODO> closedSet= new ArrayList<>();
    
    public Algortimo_A(laberinto lab1){
        laberinto=lab1;
        NODO insertar= new NODO(laberinto.posIni[0],laberinto.posIni[1],laberinto.posFin[0], laberinto.posFin[0],null);
        openSet.add(insertar);
        }
    
    public void optimo(NODO nodo1){
        while(nodo1.nodo!=null){
            laberinto.lab[nodo1.getX()] [nodo1.getY()]='+';
            nodo1=nodo1.nodo;
        }
    }

    private int buscarNodo(int x1, int y1){ // buscamos la posición de un nodo en el conjunto cerrado a partir de sus coordenadas
        int pos=-1;
        for(int i=0;i<closedSet.size();i++){
            if(closedSet.get(i).getX()==x1 && closedSet.get(i).getY()==y1) pos=i;
        }
        return pos;
    }

    private void anyadirNodos(NODO nodo1, int n){
        int x1=nodo1.getX();
        int y1=nodo1.getY();
        
        if( ((x1-1)>=0) && (laberinto.lab[x1-1] [y1]!='*') && (buscarNodo(x1-1, y1)<0) ) {
            NODO insertar= new NODO(x1-1, y1, laberinto.posFin[0], laberinto.posFin[1],nodo1);
            insertar.cambiarPeso(n);
            openSet.add(insertar);
        }

        if( ((x1+1)<laberinto.filas) && (laberinto.lab[x1+1] [y1]!='*') && (buscarNodo(x1+1, y1)<0) ) {
            NODO insertar2= new NODO(x1+1, y1, laberinto.posFin[0], laberinto.posFin[1],nodo1);
            insertar2.cambiarPeso(n);
            openSet.add(insertar2);
        }

        if( ((y1-1)>=0) && (laberinto.lab[x1] [y1-1]!='*') && (buscarNodo(x1, y1-1)<0) ){
            NODO insertar3= new NODO(x1, y1-1, laberinto.posFin[0], laberinto.posFin[1],nodo1);
            insertar3.cambiarPeso(n);
            openSet.add(insertar3);
        }

        if ( ((y1+1)<laberinto.columnas) && (laberinto.lab[x1] [y1+1]!='*') && (buscarNodo(x1, y1+1)<0) ){
                    NODO insertar4= new NODO(x1, y1+1, laberinto.posFin[0], laberinto.posFin[1],nodo1);
                    insertar4.cambiarPeso(n);
                    openSet.add(insertar4);
                }
    }

    private NODO elegirSig(){
        NODO res=openSet.get(0);
        int g=res.getPeso(); 
        for(int i=1; i<openSet.size();i++){// empezamos en el 1 porque el nodo 0 ya lo hemos cogido como inicial
            if (openSet.get(i).getPeso()<g){
                g=openSet.get(i).getPeso();
                res=openSet.get(i);
            }
        }
        return res;
    }

    public void buscarCamino() {
        NODO actual=openSet.get(0);
        System.out.print(actual);
        int moverse=1;//moverse en una casilla
        while( (openSet.size()>0) && (actual.getX()!=laberinto.posFin[0] || actual.getY()!=laberinto.posFin[1]) ){
            anyadirNodos(actual, moverse);
            System.out.print(closedSet);
            closedSet.add(actual);
            System.out.print(closedSet);
            openSet.remove(actual);
            System.out.print(openSet);
            actual=elegirSig();
            System.out.print(actual);
            System.out.println(" SIGUIENTE ITERACION \n \n ");
            if(openSet.size()<=0) {
                throw new RuntimeException("Camino no generable");
            }
        }
        System.out.println("\n \n \n \n \n");
        optimo(actual.nodo);
    }

    
}
