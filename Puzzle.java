package Sudoku;

import java.util.Random;

public class Puzzle {

    private static final int EMPTY_CELL = 0;

    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    public Puzzle() {
        super();
    }

    public void newPuzzle(int cellsToGuess) {
        generateCompleteSudoku(); // Generăm un Sudoku complet
        removeCells(cellsToGuess); // Eliminăm un număr fix de celule

        // Atunci când începe un joc nou, afișăm în consolă Sudoku-ul generat
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuConstants.GRID_SIZE; j++) {
                System.out.print(numbers[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void generateCompleteSudoku() {
        solveSudoku(numbers); // Generăm un Sudoku complet
        copyPuzzle(numbers, isGiven); // Marcam toate celulele ca fiind date
    }

    private void solveSudoku(int[][] board) {
        solveSudokuHelper(board);
    }

    private boolean solveSudokuHelper(int[][] board) {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                if (board[row][col] == EMPTY_CELL) {
                    // Generăm vectorul cu numere de la 1 la 9
                    int[] randomNumbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
                    shuffleArray(randomNumbers); // Amestecăm vectorul

                    for (int i = 0; i < randomNumbers.length; i++) {
                        int num = randomNumbers[i];
                        if (num != EMPTY_CELL && isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (solveSudokuHelper(board)) {
                                return true;
                            } else {
                                board[row][col] = EMPTY_CELL;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    // Metodă pentru amestecarea unui vector
    private void shuffleArray(int[] arr) {
        Random rnd = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }

    }

    private boolean isValid(int[][] board, int row, int col, int num) {
        // Verificăm pe aceeași linie și coloană
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        // Verificăm în mini-tablă
        int startRow = row - row % SudokuConstants.SUBGRID_SIZE;
        int startCol = col - col % SudokuConstants.SUBGRID_SIZE;
        for (int i = 0; i < SudokuConstants.SUBGRID_SIZE; i++) {
            for (int j = 0; j < SudokuConstants.SUBGRID_SIZE; j++) {
                if (board[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }


    private void copyPuzzle(int[][] src, boolean[][] dest) {
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuConstants.GRID_SIZE; j++) {
                dest[i][j] = src[i][j] != EMPTY_CELL;
            }
        }
    }

    private void removeCells(int cellsToGuess) {
        Random random = new Random();
        int cellsRemoved = 0;
        while (cellsRemoved < cellsToGuess) {
            int row = random.nextInt(SudokuConstants.GRID_SIZE);
            int col = random.nextInt(SudokuConstants.GRID_SIZE);
            if (isGiven[row][col] == true) {
                isGiven[row][col] = false; // Marcăm celula ca fiind eliminată
                cellsRemoved++;
            }
        }
    }
}

