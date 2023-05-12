import sudoku.*;
public class pruebas {
	public static final int POBLACION_INICIAL=20;
	public static void main(String[] args) {
        Sudokux sudo=new Sudokux(1);
        System.out.println(sudo.toString());
        int[] sol=SudokuMasterResolutor.resolverSudoku(sudo,POBLACION_INICIAL);
        sudo.setPuzzle(sol);
        System.out.println(sudo.toString());
    }    
    
}
