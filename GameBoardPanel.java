package Sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoardPanel extends JPanel {

    public static final int CELL_SIZE = 60;
    public static final int BOARD_WIDTH = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;
    private long startTime; // timpul de început al jocului
    private long endTime; // timpul de sfârșit al jocului

    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private Puzzle puzzle = new Puzzle();
    private int wrongGuessCount = 0;
    private boolean gameOver = false;

    public GameBoardPanel() {
        super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE, 1, 1));

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                super.add(cells[row][col]);
            }
        }

        CellInputListener listener = new CellInputListener();

        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener);
                }
            }
        }

        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Desenăm liniile subțiri de separare între mini-tablele de 3x3
        g.setColor(Color.BLACK);
        for (int row = 1; row < SudokuConstants.SUBGRID_SIZE; row++) {
            int y = row * (CELL_SIZE * SudokuConstants.SUBGRID_SIZE);
            g.fillRect(0, y - 1, getWidth(), 3);
        }
        for (int col = 1; col < SudokuConstants.SUBGRID_SIZE; col++) {
            int x = col * (CELL_SIZE * SudokuConstants.SUBGRID_SIZE);
            g.fillRect(x - 1, 0, 3, getHeight());
        }
    }


    public void newGame(int cellsToGuess) {
        // Generate a new puzzle
        startTime = System.currentTimeMillis(); // marcam timpul de început al jocului
        puzzle.newPuzzle(cellsToGuess);

        // Initialize all the 9x9 cells, based on the puzzle.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }

        // Reset the wrong guess count
        wrongGuessCount = 0;
        gameOver = false;
    }


    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].getStatus() == CellStatus.TO_GUESS || cells[row][col].getStatus() == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }


    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameOver) {
                return; // Ignoră acțiunile de la celule dacă jocul s-a terminat
            }

            Cell sourceCell = (Cell) e.getSource();
            int row = sourceCell.getRow();
            int col = sourceCell.getCol();
            String text = sourceCell.getText().trim();

            if (!text.matches("\\d+")) {
                return;
            }

            int numberIn = Integer.parseInt(text);
            if (numberIn == puzzle.numbers[row][col]) {
                sourceCell.setStatus(CellStatus.CORRECT_GUESS);
            } else {
                sourceCell.setStatus(CellStatus.WRONG_GUESS);
                wrongGuessCount++;
                if (wrongGuessCount >= 4) {
                    gameOver = true;
                    JOptionPane.showMessageDialog(GameBoardPanel.this, "You made too many wrong guesses. Game over!");
                }
            }
            sourceCell.paint();

            if (isSolved()) {
                endTime = System.currentTimeMillis(); // marcam timpul de sfârșit al jocului
                long elapsedTime = endTime - startTime; // calculăm timpul total pentru a rezolva jocul
                long seconds = elapsedTime / 1000; // convertim în secunde
                long minutes = seconds / 60; // convertim secundele în minute
                seconds = seconds % 60; // calculăm secundele rămase după conversia în minute

                JOptionPane.showMessageDialog(GameBoardPanel.this, "Congratulations! You solved the puzzle in " + minutes + " minutes and " + seconds + " seconds!");

                gameOver = true;

            }
        }
    }
}

