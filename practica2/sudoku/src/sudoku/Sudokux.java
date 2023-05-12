package sudoku;

import sudok.com.qqwing.*;

import java.util.HashSet;
import java.util.Random;


public class Sudokux {
    private int[] puzzle;
    private static final int BOARD_SIZE=81, ROW_COL_SEC_SIZE=9, SEC_GROUP_SIZE=27, GRID_SIZE=3;
    private static final String NL="\n";
    //create sudoku of specific difficulty level
	private int[] computePuzzleByDifficulty(Difficulty d) {
		QQWing qq=new QQWing();
		qq.setRecordHistory(true);
		qq.setLogHistory(false);
		boolean go_on = true;
		while (go_on) {
			qq.generatePuzzle();
			qq.solve();
			Difficulty actual_d = qq.getDifficulty();
			go_on = !actual_d.equals(d);
		}
		int []puzzle = qq.getPuzzle();
		return puzzle;
	}
	public int[] getPuzzle() {
		return puzzle;
	}
	public void setPuzzle(int[] sol) {
		puzzle=sol;
	}
	
	//cheat by creating absurdly simple sudoku, with a given number of holes per row
	private int[] computePuzzleWithNHolesPerRow(int numHolesPerRow) {
		Random rnd = new Random();
		QQWing qq=new QQWing();
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

    public Sudokux(int dif){
        switch (dif) {
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
    }
    @Override
    public String toString (){
        return puzzleToString(puzzle);
    }
    private String puzzleToString(int[] sudoku) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < BOARD_SIZE; i++) {
			sb.append(" ");
			if (sudoku[i] == 0) {
				sb.append('.');
			} else {
				sb.append(sudoku[i]);
			}
			if (i == BOARD_SIZE - 1) {
				sb.append(NL);
				sb.append(NL);
			} else if (i % ROW_COL_SEC_SIZE == ROW_COL_SEC_SIZE - 1) {
				sb.append(NL);
				if (i % SEC_GROUP_SIZE == SEC_GROUP_SIZE - 1) {
					sb.append("-------|-------|-------").append(NL);
				}
			} else if (i % GRID_SIZE == GRID_SIZE - 1) {
				sb.append(" |");

			}
		}
		return sb.toString();
	}
	public static int getBoardSize() {
		return BOARD_SIZE;
	}
	public static int getRowColSecSize() {
		return ROW_COL_SEC_SIZE;
	}
	public static int getSecGroupSize() {
		return SEC_GROUP_SIZE;
	}
	public static int getGridSize() {
		return GRID_SIZE;
	}
	public static String getNl() {
		return NL;
	}
    
}
