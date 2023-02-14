import java.util.Random;


public class Laberinto {

public int filas= 10;
public int columnas=5;
public String [] []   laberinto;
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
        if (laberinto [randomfilas] [randomcolumnas] == "*") {
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

private void RellenarObstaculosRec() {
  int rdm1= rand.nextInt(filas);
  int rdm2= rand.nextInt(columnas);
  if (laberinto [rdm1] [rdm2] == "*") {
    RellenarObstaculosRec();
    } else {
        laberinto [rdm1] [rdm2] = "*";
    } 
}


public void mostrar() {

    for(int i = 0; i<filas; i++){

        for(int j = 0; j<columnas; j++){
            if (laberinto [i] [j] ==null) {
                System.out.println("[ ]");
            }
            System.out.print("[" +laberinto[i][j] +"]");
            System.out.print(" ");
          
        }
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
   lab1.mostrar(); 
} 


}
