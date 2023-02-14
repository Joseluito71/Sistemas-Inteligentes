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
public int area= columnas*filas;

public laberinto() {
laberinto = new String [filas] [columnas];

}
public void Inicializar() {
    for (int i=0; i<filas;i++) {
        for (int j=0; j<columnas;j++) {
            laberinto [i] [j] =null;
        }
    }
}
public void RellenarObstaculos() {
    for (int i=0; i<NumObstaculos;i++) {
        int randomfilas=rand.nextInt(area);
        int randomcolumnas= rand.nextInt(area);
        if (laberinto [randomfilas] [randomcolumnas] == "*") {
            RellenarObstaculosRec();
        } else  laberinto [randomfilas] [randomcolumnas] = "*";
    }
}
public void PonerInicio(){
    int rdm1=rand.nextInt(area);
    int rdm2= rand.nextInt(area);
    if (laberinto [rdm1] [rdm2] != null) {
        PonerInicioRec();
    } else laberinto [rdm1] [rdm2] = "I",
}

private void PonerInicioRec() {
    int rdm1= rand.nextInt(area);
  int rdm2= rand.nextInt(area);
  if (laberinto [rdm1] [rdm2] != null) {
    RellenarObstaculosRec();
    } else {
        laberinto [rdm1] [rdm2] = "I";
    } 
}

private void RellenarObstaculosRec() {
  int rdm1= rand.nextInt(area);
  int rdm2= rand.nextInt(area);
  if (laberinto [rdm1] [rdm2] == "*") {
    RellenarObstaculosRec();
    } else {
        laberinto [rdm1] [rdm2] = "*";
    } 
}


public void mostrar() {

    for(int i = 0; i<filas; i++){

        for(int j = 0; j<columnas; j++){

            System.out.print(laberinto[i][j]);

            if(j == columnas-1){
                System.out.print("\t");
            }
        }
    }   
}

public String toString() {
    return laberinto.toString();
}

public main Prueba {
 Laberinto lab1 = new Laberinto;
 lab1.Laberinto();
 lab1.RellenarObstaculos;
 System.out.println();
}


}
