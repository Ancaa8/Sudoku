package Sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SudokuMain extends JFrame {

    private GameBoardPanel board = new GameBoardPanel();
    private JButton btnNewGame = new JButton("New Game");

    public SudokuMain() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("SUDOKU", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        cp.add(titleLabel, BorderLayout.NORTH);
        cp.add(board, BorderLayout.CENTER);
        cp.add(btnNewGame, BorderLayout.SOUTH);

        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.newGame(43); // Inițializează un joc nou cu 43 de celule de ghicit
            }
        });

        board.newGame(43); // Inițializează jocul la pornirea aplicației

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SudokuMain();
        });
    }
}
