import java.util.*;


public class Laberinto {

public int filas= 10;
public int columnas=5;
public String [] []  laberinto;
public String obstaculo = "*";
public String NoObstaculo=" ";
public String inicio= "I";
public String fin= "G";
public String Optimo= "+";
public Random rand= new Random();
public int  NumObstaculos= filas*columnas*30/100;



public Laberinto() {
laberinto = new String [filas] [columnas];

}
public void Inicializar() {
    for (int i=0; i<filas;i++) {
        for (int j=0; j<columnas;j++) {
            laberinto [i] [j] = null;
        }
    }
}
public void RellenarObstaculos() {
    for (int i=0; i<NumObstaculos;i++) {
        int randomfilas=rand.nextInt(filas);
        int randomcolumnas= rand.nextInt(columnas);
        if (laberinto [randomfilas] [randomcolumnas] !=null) {
            RellenarObstaculosRec();
        } else  laberinto [randomfilas] [randomcolumnas] = "*";
    }
}
public void PonerInicio(){
    int rdm1=rand.nextInt(filas);
    int rdm2= rand.nextInt(columnas);
    if (laberinto [rdm1] [rdm2] != null) {
        PonerInicioRec();
    } else laberinto [rdm1] [rdm2] = "I";
}

private void PonerInicioRec() {
    int rdm1= rand.nextInt(filas);
  int rdm2= rand.nextInt(columnas);
  if (laberinto [rdm1] [rdm2] != null) {
    RellenarObstaculosRec();
    } else {
        laberinto [rdm1] [rdm2] = "I";
    } 
}

public void PonerFinal(){
    int rdm1=rand.nextInt(filas);
    int rdm2= rand.nextInt(columnas);
    if (laberinto [rdm1] [rdm2] != null) {
        PonerFinalRec();
    } else laberinto [rdm1] [rdm2] = "G";
}

private void PonerFinalRec() {
    int rdm1= rand.nextInt(filas);
  int rdm2= rand.nextInt(columnas);
  if (laberinto [rdm1] [rdm2] != null) {
    RellenarObstaculosRec();
    } else {
        laberinto [rdm1] [rdm2] = "G";
    } 
}

private void RellenarObstaculosRec() {
  int rdm1= rand.nextInt(filas);
  int rdm2= rand.nextInt(columnas);
  if (laberinto [rdm1] [rdm2] != "*") {
    RellenarObstaculosRec();
    } else {
        laberinto [rdm1] [rdm2] = "*";
    } 
}


public void mostrar() {
    for (int x=0; x < filas; x++) {
        System.out.print("|");
        for (int y=0; y < columnas; y++) {
          System.out.print (laberinto[x][y]);
          if (y!=laberinto[x].length-1) System.out.print("\t");
        }
        System.out.println("|");
      }
}

public String toString() {
    return laberinto.toString();
}

 public static void main(String[] args) {
   Laberinto lab1= new Laberinto();
   lab1.Inicializar();
   lab1.RellenarObstaculos();
   lab1.PonerInicio();
   lab1.PonerFinal();
   lab1.mostrar(); 
} 


// EMPIEZA A*


public Laberinto Start(){
   Laberinto lab1= new Laberinto();  
   lab1.Inicializar();
   lab1.RellenarObstaculos();
   lab1.PonerInicio();
   lab1.PonerFinal();
   return lab1;
}





public int h(int a){
int res = 0;


    return res;
}

public void A(){
    Laberinto openset = Start();
    Set<Map<Integer,Integer>> closedset= new HashSet<>() ;
    Map<Integer,Integer> parent = new HashMap<>();
    List<Integer> g = new ArrayList<>();
    List<Integer> f = new ArrayList<>();
    g.add(0, 0);
    f.add(0, (g.get(0)+h(0)));
    
    while(!closedset.isEmpty()){
        Map<Integer,Integer> current = new HashMap<>();
        
    }


}


}

