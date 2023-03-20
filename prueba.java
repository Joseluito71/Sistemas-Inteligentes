public class prueba {
     public static void main(String[] args) {
        laberinto lab= new laberinto();
        lab.mostrar();
        System.out.println("\n \n \n \n \n \n  ");
        System.out.println("espacios"); 
        Algortimo_A ejecutar= new Algortimo_A(lab);
        System.out.println("crea");
        ejecutar.buscarCamino();
        System.out.println("busca");
        lab.mostrar();
    }
}
