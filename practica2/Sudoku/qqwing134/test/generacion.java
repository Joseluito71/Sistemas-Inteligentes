/**
 * sudoku
 */

import java.util.HashSet;
import java.util.Random;

import com.qqwing.Difficulty;
import com.qqwing.QQWing;

public class generacion {

	//create sudoku of specific difficulty level
	private static int[] computePuzzleByDifficulty(Difficulty d) {
		QQWing qq = new QQWing();
		qq.setRecordHistory(true);
		qq.setLogHistory(false);
		boolean go_on = true;
		while (go_on) {
			qq.generatePuzzle();
			qq.solve();
			Difficulty actual_d = qq.getDifficulty();
			//System.out.println("Difficulty: "+actual_d.getName());
			go_on = !actual_d.equals(d);
		}
		int []puzzle = qq.getPuzzle();
		return puzzle;
	}
	
	//cheat by creating absurdly simple sudoku, with a given number of holes per row
	private static int[] computePuzzleWithNHolesPerRow(int numHolesPerRow) {
		Random rnd = new Random();
		QQWing qq = new QQWing();

		qq.setRecordHistory(true);
		qq.setLogHistory(false);
		qq.generatePuzzle();
		qq.solve();
		int []solution = qq.getSolution();
		HashSet<Integer> set = new HashSet<Integer>();
		for (int i=0; i<9; i++) {
			set.clear();
			while(set.size()<numHolesPerRow) {
				int n = rnd.nextInt(9);
				if (set.contains(n)) continue;
				set.add(n);
			}
			for (Integer hole_idx : set) {
				solution[i*9+hole_idx] = 0;
			}
		}
		return solution;
	}

    public static int[][] nuevoSudoku(int Dificultad) {
        int option = Dificultad;
		
        int[] puzzle = {};
        int[][] nuevoPuzzle = new int[9][9];
        switch (option) {
        case 1:
            //Extremely easy puzzle, should be solvable without tuning the parameters of the genetic algorithm
            puzzle = computePuzzleWithNHolesPerRow(3);
            break;
        case 2:
            //Puzzle with difficulty SIMPLE as assessed by QQWing.
            //Should require just minimal tuning of the parameters of the genetic algorithm
            puzzle = computePuzzleByDifficulty(Difficulty.SIMPLE);
            break;
        case 3:
            //Puzzle with difficulty EASY as assessed by QQWing.
            //Should require some tuning of the parameters of the genetic algorithm
            puzzle = computePuzzleByDifficulty(Difficulty.EASY);
            break;
        case 4:
            //Puzzle with difficulty INTERMEDIATE as assessed by QQWing.
            //Should require serious effort tuning the parameters of the genetic algorithm
            puzzle = computePuzzleByDifficulty(Difficulty.INTERMEDIATE);
            break;
        case 5:
            //Puzzle with difficulty EXPERT as assessed by QQWing.
            //Should require great effort tuning the parameters of the genetic algorithm
            puzzle = computePuzzleByDifficulty(Difficulty.EXPERT);
            break;
        }

        System.out.println("SUDOKU SIN RESOLVER: \n");
        for (int i = 0; i < puzzle.length; i++) {
            if (i%3==0 && i!=0) {
                if (i%9==0) {
                    System.out.println();
                } else {
                    System.out.print("█ ");
                }
            }
            if (i%27==0 && i!=0) {
                System.out.println("█████████████████████");
            }
            if (puzzle[i]==0) {
                System.out.print("  ");
            } else {
                nuevoPuzzle[i/9][i%9] = puzzle[i];
                System.out.print(puzzle[i] + " ");
            }
        }
        System.out.println();
        System.out.println("-----------------------");

        return nuevoPuzzle;
        //IMPORTANT: QQWing returns the puzzle as a single-dimensional array of size 81, row by row.
        //           Holes (cells without a number from 1 to 9) are represented by the value 0.
        //           It is advisable to convert this array to a data structure more amenable to manipulation.
    }

}