public class Laberinto2 {

    public int filas= 10;
    public int columnas=5;
    public String obstaculo = "*";
    public String NoObstaculo=" ";
    public String inicio= "I";
    public String fin= "G";
    public String Optimo= "+";
    public int  NumObstaculos= filas*columnas*30/100;
    public Block [] [] laberinto;

    public Laberinto2(){
        laberinto=new Block [filas] [columnas];
    }
}
