import java.util.Random;


public class Laberinto {

public int filas= 60;
public int columnas=80;
public String [] []   laberinto;
public String obstaculo = "*";
public String NoObstaculo=" ";
public String inicio= "I";
public String fin= "G";
public String Optimo= "+";
public Random rand= new Random();
public int  NumObstaculos= filas*columnas*30/100;
public int area= columnas*filas;

public Laberinto() {
laberinto = new String [filas] [columnas];
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

private void RellenarObstaculosRec() {
  int rdm1= rand.nextInt(area);
  int rdm2= rand.nextInt(area);
  if (laberinto [rdm1] [rdm2] == "*") {
    RellenarObstaculosRec();
    } else {
        laberinto [rdm1] [rdm2] = "*";
    } 
}

}
