package practica2;
import java.util.HashSet;
import java.util.Random;

import com.qqwing.Difficulty;
import com.qqwing.QQWing;

public class Sudoku {
    //create sudoku of specific difficulty level
	public static int[] computePuzzleByDifficulty(Difficulty d) {
		QQWing qq = new QQWing();
		qq.setRecordHistory(true);
		qq.setLogHistory(false);
		boolean go_on = true;
		while (go_on) {
			qq.generatePuzzle();
			qq.solve();
			Difficulty actual_d = qq.getDifficulty();
			System.out.println("Difficulty: "+actual_d.getName());
			go_on = !actual_d.equals(d);
		}
		int []puzzle = qq.getPuzzle();
		return puzzle;
	}
	
	//cheat by creating absurdly simple sudoku, with a given number of holes per row
	public static int[] computePuzzleWithNHolesPerRow(int numHolesPerRow) {
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

	public static void main(String[] args) {
		int option = 1;
		//int option = 2;
		//int option = 3;
		//int option = 4;
		//int option = 5;
		
        int []puzzle;
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
        
        //IMPORTANT: QQWing returns the puzzle as a single-dimensional array of size 81, row by row.
        //           Holes (cells without a number from 1 to 9) are represented by the value 0.
        //           It is advisable to convert this array to a data structure more amenable to manipulation.
	}
}
