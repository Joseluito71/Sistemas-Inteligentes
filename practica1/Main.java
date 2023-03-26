package practica1;
public class Main {
    
    public static void main(String[] args){

        Laberinto test = new Laberinto(10, 15);
        test.inicializar();
        AEstrella h0= new AEstrella(test);
        test.mostrar();
        System.out.println("------------------------------ \n");
        test.pintarSolucion(h0.calcularCamino());
    }
}
