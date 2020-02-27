package sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sudoku {

    private int[][] gameBoard = new int[9][9];
//    private Scanner keyboard;

//Rules: chosen number cannot be repeated in same row
//or column
// or 3x3 group, from 0-2, 3-5, 6-9
    public static void main(String[] args) {
        new Sudoku();
    }

    public Sudoku() {
// initialize game
        initializeGame();
        printGameBoard();
        while (!hasGameBeenSolved()) {
            int[] cellLocation = getNextCell();
            if (cellLocation != null) {
//                System.out.print("Enter number in (" + cellLocation[0] + ","
//                        + cellLocation[1] + "): ");
//                gameBoard[cellLocation[0]][cellLocation[1]] = keyboard.nextInt();
                gameBoard[cellLocation[0]][cellLocation[1]] = cellLocation[2];
                printGameBoard();
            } else {
                System.out.println("Difficult Level Sudoku Game. \n"
                        + "You need to implement more rules to solve it...");
                break;
            }
        }
    }

    private void initializeGame() {
//Opens a text file containing a 9x9 space-separated integer numbers representing a single sudoku game. After reading it, the method places those numbers in the global gameBoard 2D variable. 
        File gameFile = new File("C:\\Users\\Ziyan\\Documents\\NetBeansProjects\\Sudoku\\src\\sudoku\\sudokuSample.txt");
        try {
            Scanner gameScanner = new Scanner(gameFile);
            while (gameScanner.hasNextLine()) {
                for (int i = 0; i < gameBoard.length; i++) {
                    String[] line = gameScanner.nextLine().trim().split(" ");
                    for (int j = 0; j < line.length; j++) {
                        gameBoard[i][j] = Integer.parseInt(line[j]);
                    }
                }
            }
//        System.out.println(Arrays.deepToString(gameBoard));
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.  Check path.");
        }
    }

    private void printGameBoard() {
//Description: Prints the game board in the terminal window. Later in this course we will implement it in a GUI app.
        System.out.println("Current gameboard:");
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                if (j == gameBoard.length - 1) {
                    System.out.print(gameBoard[i][j] + "\t\n");
                } else {
                    System.out.print(gameBoard[i][j] + "  ");
                }
            }
        }
    }

    private boolean hasGameBeenSolved() {
//Description: Returns true if the game has been completely solved; false otherwise.Hint, loop over all the cells and if you find one cell that has 0 returns false immediately. If after looping over all the cells you could not find any cell with 0, then returns true (game has been solved since there is no empty cells left).
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard.length; col++) {
                if (gameBoard[row][col] == 0) {
                    System.out.println("game is NOT solved");
                    return false;
                }
            }
        }
        System.out.println("game is solved");
        return true;
    }

    private int[] getNextCell() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (gameBoard[row][col] == 0) {
                    Set<Integer> firstRuleElimination = getFirstRuleElimination(row);
                    Set<Integer> secondRuleElimination = getSecondRuleElimination(col);
                    Set<Integer> thirdRuleElimination = getThirdRuleElimination(row, col);
                    Set<Integer> survivors = getSurvivors(firstRuleElimination,
                            secondRuleElimination, thirdRuleElimination);
                    if (survivors.size() == 1) {
                        List<Integer> list = new ArrayList(survivors);
                        int uniqueValue = list.get(0);
                        return new int[]{row, col, uniqueValue};
                    }
                }
            }
        }
        return null;
    }

    private Set<Integer> getFirstRuleElimination(int row) {
//Description: Loops over all the columns of the given row (method input) and adds all the numbers found in these cells into a Set object. Returns this set object.
        Set<Integer> set = new HashSet();
        for (int col = 0; col < gameBoard.length; col++) {
            if(gameBoard[row][col] != 0){
                set.add(gameBoard[row][col]);
            }
            
        }
        return set;
    }

    private Set<Integer> getSecondRuleElimination(int col) {
//Loops over all the rows of the given column (method input) and adds all the numbers found in these cells into a Set object. Returns this set object.
        Set<Integer> set = new HashSet();
        for (int row = 0; row < gameBoard.length; row++) {
            if(gameBoard[row][col] != 0){
                set.add(gameBoard[row][col]);
            }
        }
        return set;
    }

    private Set<Integer> getThirdRuleElimination(int row, int col) {
//Each cell defined by a (row, column) belongs to a group (A, B, C, … as shown in the second         figure of this assignment). This method shall initially call two methods(see description about the methods below ): one returning an array with min and max rows and the other with min and max columns        for the current  group that  {this}        cell defined by a rowcol belongs to.And then, loop over the 9 cells defined in this group(2 Nested FOR loops        ) and collect all the numbers found in the gameboard into a Set object and return that object.
        int[] rowRange = getRowRangeForGroup(row); //[0, 2]
        int[] colRange = getColRangeForGroup(col); //[1, 3]
        Set<Integer> set = new HashSet();
        for (int xValue = rowRange[0]; xValue <= rowRange[1]; xValue++) {
            /* For given value, check if it's contained in row */
            for (int yValue = colRange[0]; yValue <= colRange[1]; yValue++) {
                if (gameBoard[xValue][yValue] != 0) {
                    set.add(gameBoard[xValue][yValue]);
                }
            }
        }
        return set;
    }

    private int[] getRowRangeForGroup(int row) {
//Based on the given row, it defines the minimum and maximum row for that group. Example for row = 1 then min and max row for this group is 0 and 2 respectively, while for row = 4 then min and max rows are 3 and 5. This method returns an integer array with those two numbers.
        int[] rowRange = new int[2];
        if (row == 0 || row == 3 | row == 6) {
            rowRange[0] = row;
            rowRange[1] = row + 2;
        } else if (row == 2 || row == 5 || row == 8) {
            rowRange[0] = row - 2;
            rowRange[1] = row;
        } else {
            rowRange[0] = row - 1;
            rowRange[1] = row + 1;
        }
        return rowRange;
    }

    private int[] getColRangeForGroup(int col) {

        int[] colRange = new int[2];
        if (col == 0 || col == 3 | col == 6) {
            colRange[0] = col;
            colRange[1] = col + 2;
        } else if (col == 2 || col == 5 || col == 8) {
            colRange[0] = col - 2;
            colRange[1] = col;
        } else {
            colRange[0] = col - 1;
            colRange[1] = col + 1;
        }
        return colRange;
    }

    private Set<Integer> getSurvivors(Set<Integer> firstRuleElimination,Set<Integer> secondRuleElimination, Set<Integer> thirdRuleElimination) {
        Set<Integer> overall = new HashSet();
        overall.addAll(firstRuleElimination);
        overall.addAll(secondRuleElimination);
        overall.addAll(thirdRuleElimination);
        
        Set<Integer> container = new HashSet();
        for(int i = 1; i < 10; i++){
            if(!overall.contains(i)){
                container.add(i);
            }
        }
        return container;
//This method does two tasks: first it creates a temporary Set and add all the sets given in the method input into it (a consolidated set with all non-allowed numbers found when applying the 3 rules). Then it creates another Set that is the opposite of this consolidated set, i.e., contains only the allowed numbers for the given current cell.
    }

}
