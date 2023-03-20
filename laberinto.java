import java.util.Random;
public class laberinto {

    protected final int filas=60;
    protected final int columnas=80;
    protected static final double porcentaje=0.3;
    protected char [] [] lab;
    public int[] posIni= new int [2];
    public int[] posFin= new int [2];

    public laberinto() {
        lab= new char[filas] [columnas];
        inicializar();
        ponerInicioFin();
        ponerObstaculos();


    }

    private void inicializar() {
        for (int i=0;i<filas;i++){
            for (int j=0;i<columnas;j++){
                lab[i][j]=' ';
            }
        }
    }

    private void ponerInicioFin(){
        Random filaR= new Random();
        Random coluR= new Random();
        int filaAnterior=0;
        int coluAnterior=0;
        int condicion=0;
        while (condicion<2){
            int filaRand= filaR.nextInt(filas);
            int coluRand= coluR.nextInt(columnas);
            if (condicion==0){
                lab[filaRand] [coluRand]='I';
                posIni[0]=filaRand;
                posIni[1]=coluRand;
                condicion++;
                filaAnterior=filaRand;// asegurarnos que no ponemos el fin en el mismo sitio que el final
                coluAnterior=coluRand;
            } else {
                if(!(filaRand==filaAnterior && coluRand==coluAnterior)) {
                condicion++;
                lab[filaRand] [coluRand]='G';
                posFin[0]=filaRand;
                posFin[1]=coluRand;
                }
            }
            
        }
    }

   private void ponerObstaculos(){
    double ObstaculosAprox= (filas*columnas*porcentaje);
    int nObstaculos=(int) ObstaculosAprox;
    int obstsPuestos=0;
    Random filaR= new Random();
    Random coluR= new Random();
    while(obstsPuestos<nObstaculos){
        int filaRand= filaR.nextInt(filas);
        int coluRand= coluR.nextInt(columnas);
        if (lab[filaRand][coluRand]==' '){
            lab[filaRand][coluRand]='*';
            obstsPuestos++;
        }
    }
   }

   public void mostrar(){
    for (int i=0;i<filas;i++){
        for(int j=0;j<columnas;j++){
            System.out.print(lab[i][j]);
        }
        System.out.println("\n");
    }
   }
}
