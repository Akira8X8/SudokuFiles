package sudoku;

public class Sudoku {

    private int[][] gameBoard;
    private Scanner keyboard;

    public static void main(String[] args) {
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
}