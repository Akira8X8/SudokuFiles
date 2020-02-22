package sudoku;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Sudoku {

    private int[][] gameBoard;
    private Scanner keyboard;
    
    //Rules: chosen number cannot be repeated in same row
    //or column
    // or 3x3 group, from 0-2, 3-5, 6-9

    public static void main(String[] args) {
        new Sudoku();
    }
    
    public Sudoku(){
        // initialize game
        initializeGame();
        printGameBoard();
        while (!hasGameBeenSolved()) {
            int[] cellLocation = getNextCell();
            if (cellLocation != null) {
                System.out.print("Enter number in (" + cellLocation[0] + ","
                        + cellLocation[1] + "): ");
                gameBoard[cellLocation[0]][cellLocation[1]] = keyboard.nextInt();
                gameBoard[cellLocation[0]][cellLocation[1]] = cellLocation[2];
                printGameBoard();
            } else {
                System.out.println("Difficult Level Sudoku Game. \n"
                        + "You need to implement more rules to solve it...");
                break;
            }
        }
    }
    
    private void initializeGame(){
        //Opens a text file containing a 9x9 space-separated integer numbers representing a single sudoku game. After reading it, the method places those numbers in the global gameBoard 2D variable. 
    }
    
    private void printGameBoard(){
        //Description: Prints the game board in the terminal window. Later in this course we will implement it in a GUI app.

    }
    
    private boolean hasGameBeenSolved(){
        //Description: Returns true if the game has been completely solved; false otherwise.Hint, loop over all the cells and if you find one cell that has 0 returns false immediately. If after looping over all the cells you could not find any cell with 0, then returns true (game has been solved since there is no empty cells left).
        return false;
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
    
    private Set<Integer> getFirstRuleElimination(int row){
        //Description: Loops over all the columns of the given row (method input) and adds all the numbers found in these cells into a Set object. Returns this set object.
        Set<Integer> set = new HashSet();
        return set;
    }
    
    private Set<Integer> getSecondRuleElimination(int row){
        //Loops over all the rows of the given column (method input) and adds all the numbers found in these cells into a Set object. Returns this set object.
        Set<Integer> set = new HashSet();
        return set;
    }
    
    private Set<Integer> getThirdRuleElimination(int row, int column){
        //Each cell defined by a (row, column) belongs to a group (A, B, C, … as shown in the second figure of this assignment). This method shall initially call two methods (see description about the methods below): one returning an array with min and max rows and the other with min and max columns for the current group that this cell defined by a rowcol belongs to. And then, loop over the 9 cells defined in this group (2 Nested FOR loops) and collect all the numbers found in the gameboard into a Set object and return that object.
        Set<Integer> set = new HashSet();
        return set;
    }
    
    private int[] getRowRangeForGroup(int row) {
        //Based on the given row, it defines the minimum and maximum row for that group. Example for row = 1 then min and max row for this group is 0 and 2 respectively, while for row = 4 then min and max rows are 3 and 5. This method returns an integer array with those two numbers.
        int[] rowRange = new int[2];
        return rowRange;
    }
    
    private int[] getColRangeForGroup(int row) {
        
        int[] colRange = new int[2];
        return colRange;
    }
    
    private Set<Integer> getSurvivors(Set<Integer> firstRuleElimination,
Set<Integer> secondRuleElimination, Set<Integer> thirdRuleElimination) {
        Set<Integer> survivors = new HashSet();
        return survivors;
        //This method does two tasks: first it creates a temporary Set and add all the sets given in the method input into it (a consolidated set with all non-allowed numbers found when applying the 3 rules). Then it creates another Set that is the opposite of this consolidated set, i.e., contains only the allowed numbers for the given current cell.
    }



}