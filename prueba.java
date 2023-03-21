public class prueba {
     public static void main(String[] args) {
        laberinto lab= new laberinto();
        lab.mostrar();
        System.out.println("\n \n \n \n \n \n  ");
        Algortimo_A ejecutar= new Algortimo_A(lab);
        System.out.println("hola ");
        ejecutar.buscarCamino();
        lab.mostrar();
    }
}
