package tictactoe;

import java.util.*;

public class Main {

    // Set of acceptable symbols
    static final Set<String> symbols = Set.of("X", "O", "_", " ");

    /*
       Map of coordinates to grid array element index
       Origin is (1,1) at the top left corner
       (1, 1) -> 0
       (1, 2) -> 1
       (1, 3) -> 2
       (2, 1) -> 3
       (2, 2) -> 4
       (2, 3) -> 5
       (3, 1) -> 6
       (3, 2) -> 7
       (3, 3) -> 8
    */
    static final Map<String, Integer> map = Map.of(
            "11", 0,
            "12", 1,
            "13", 2,
            "21", 3,
            "22", 4,
            "23", 5,
            "31", 6,
            "32", 7,
            "33", 8
    );

    public static void main(String[] args) {

        // Create an empty grid
        String[] currentGrid = new String[9];

        // Initialize grid cells to empty spaces
        for (int i = 0; i < currentGrid.length; i++) {
            currentGrid[i] = " ";
        }

        // Display an empty grid
        display(currentGrid);

        // First player is player X
        var currentPlayer = Player.X;

        boolean isGameOver = false;

        // -- GAME LOOP START --
        while (!isGameOver) {

            // Prompt user to enter coordinates
            // Return target index in grid array
            int targetIndex = getCoordinatesFromPlayer(currentGrid);

            // Place current player's symbol
            // Return a new, updated grid with placed symbol
            String[] updatedGrid = placeSymbol(currentPlayer, targetIndex, currentGrid);
            currentGrid = updatedGrid;

            // Display updated grid
            display(currentGrid);

            // Checks the grid for the current game state (win, draw, not finished)
            isGameOver = getGameState(currentGrid);

            if (isGameOver) break;

            // Switch player
            currentPlayer = currentPlayer == Player.X ? currentPlayer = Player.O : Player.X;
        }
        // -- GAME LOOP END --
    }

    private static boolean getGameState(String[] updatedGrid) {

        // Default game state variables
        boolean xWins = false;
        boolean oWins = false;
        boolean impossible = false;
        boolean finished = false;
        boolean draw = false;
        String gameResult;

        // Check if X's have been placed in a winning position

        if (
                ("X".equals(updatedGrid[0]) && "X".equals(updatedGrid[1]) && "X".equals(updatedGrid[2])) ||
                        ("X".equals(updatedGrid[3]) && "X".equals(updatedGrid[4]) && "X".equals(updatedGrid[5])) ||
                        ("X".equals(updatedGrid[6]) && "X".equals(updatedGrid[7]) && "X".equals(updatedGrid[8])) ||
                        ("X".equals(updatedGrid[0]) && "X".equals(updatedGrid[3]) && "X".equals(updatedGrid[6])) ||
                        ("X".equals(updatedGrid[1]) && "X".equals(updatedGrid[4]) && "X".equals(updatedGrid[7])) ||
                        ("X".equals(updatedGrid[2]) && "X".equals(updatedGrid[5]) && "X".equals(updatedGrid[8])) ||
                        ("X".equals(updatedGrid[0]) && "X".equals(updatedGrid[4]) && "X".equals(updatedGrid[8])) ||
                        ("X".equals(updatedGrid[2]) && "X".equals(updatedGrid[4]) && "X".equals(updatedGrid[6]))
        ) {
            xWins = true;
        }


        // Check if O's have been placed in a winning position

        if (
                ("O".equals(updatedGrid[0]) && "O".equals(updatedGrid[1]) && "O".equals(updatedGrid[2])) ||
                        ("O".equals(updatedGrid[3]) && "O".equals(updatedGrid[4]) && "O".equals(updatedGrid[5])) ||
                        ("O".equals(updatedGrid[6]) && "O".equals(updatedGrid[7]) && "O".equals(updatedGrid[8])) ||
                        ("O".equals(updatedGrid[0]) && "O".equals(updatedGrid[3]) && "O".equals(updatedGrid[6])) ||
                        ("O".equals(updatedGrid[1]) && "O".equals(updatedGrid[4]) && "O".equals(updatedGrid[7])) ||
                        ("O".equals(updatedGrid[2]) && "O".equals(updatedGrid[5]) && "O".equals(updatedGrid[8])) ||
                        ("O".equals(updatedGrid[0]) && "O".equals(updatedGrid[4]) && "O".equals(updatedGrid[8])) ||
                        ("O".equals(updatedGrid[2]) && "O".equals(updatedGrid[4]) && "O".equals(updatedGrid[6]))
        ) {
            oWins = true;
        }

        // Check if game is in an impossible state
        // Three X's in a row and three O's in a row
        if (oWins && xWins) impossible = true;

        // More (at least 2) X's than O's or vice-versa
        int countX = 0;
        int countO = 0;
        for (String element : updatedGrid) {
            if ("X".equals(element)) countX++;
            else if ("O".equals(element)) countO++;
        }
        if (Math.abs(countX - countO) > 1) impossible = true;

        // Check if game is finished
        if (countX + countO == 9) finished = true;

        // Check if game is a draw
        if (finished == true && !xWins && !oWins) draw = true;

        if (impossible) gameResult = "Impossible";
        else {
            if (xWins) gameResult = "X wins";
            else if (oWins) gameResult = "O wins";
            else if (!xWins && !oWins && finished) gameResult = "Draw";
            else {
                gameResult = "Game not finished";
            }
        }

        if ("Game not finished".equals(gameResult)) {
            return false;

        } else {
            System.out.println(gameResult);
            return true;
        }
    }

    private static void display(String[] array) {

        System.out.println("---------");
        System.out.printf("| %s %s %s |\n", array[0], array[1], array[2]);
        System.out.printf("| %s %s %s |\n", array[3], array[4], array[5]);
        System.out.printf("| %s %s %s |\n", array[6], array[7], array[8]);
        System.out.println("---------");

    }

    // Although this function consumes an array, it does not mutate it
    // This function prompts the current player to provide coordinates to place their symbol
    // It returns an integer that corresponds to the index of the placed symbol in the grid array
    private static int getCoordinatesFromPlayer(String[] inputStringArray) {

        var scanner = new Scanner(System.in);

        // String representation of numbers entered by user
        String xString;
        String yString;

        // Integer representation of numbers entered by user
        int x;
        int y;

        // Target index in grid array
        // Default set to 0
        int targetIndex = 0;

        boolean loopCoordinateInput = true;
        while (loopCoordinateInput) {

            System.out.print("Enter the coordinates: ");
            xString = scanner.next();
            yString = scanner.next();

            // Check if input coordinates are numbers
            if (isInteger(xString) && isInteger(yString)) {
                x = Integer.parseInt(xString);
                y = Integer.parseInt(yString);

                // Check if input coordinates are between 1 and 3
                if (x > 3 || x < 1 || y < 1 || y > 3) {
                    System.out.println("Coordinates should be from 1 to 3");
                    continue;
                } else {
                    targetIndex = map.get(xString + yString);
                    if (isCellOccupied(targetIndex, inputStringArray)) {
                        System.out.println("This cell is occupied! Choose another one!");
                        continue;
                    }
                }

            } else {
                System.out.println("You should enter numbers!");
                continue;
            }

            // Exit the loop if input coordinates are numbers and are between 1 and 3
            loopCoordinateInput = false;
        }

        return targetIndex;

    }

    private static String[] placeSymbol(Player currentPlayer, int targetIndex, String[] currentGrid) {

        String[] updatedGrid = Arrays.stream(currentGrid).toArray(String[]::new);

        updatedGrid[targetIndex] = currentPlayer.toString();

        return updatedGrid;

    }

    private static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isCellOccupied(int index, String[] array) {
        if (array == null) {
            return false;
        } else if ("X".equals(array[index]) || "O".equals(array[index])) {
            return true;

        } else {
            return false;
        }
    }

}
