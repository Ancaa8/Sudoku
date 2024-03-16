package Sudoku;
import java.awt.*;
import javax.swing.JTextField;

public class Cell extends JTextField {

    public static final Color BG_GIVEN = new Color(255, 255, 255);
    public static final Color FG_GIVEN = Color.BLACK;
    public static final Color FG_NOT_GIVEN = Color.BLACK;
    public static final Color BG_TO_GUESS = new Color(255, 255, 255);
    public static final Color BG_CORRECT_GUESS = new Color(103, 253, 46);
    public static final Color BG_WRONG_GUESS = new Color(176, 74, 74);
    public static final Font FONT_NUMBERS = new Font("OCR A Extended", Font.PLAIN, 28);

    int row, col;
    int number;
    CellStatus status;

    public Cell(int row, int col) {
        super();
        this.row = row;
        this.col = col;
        super.setHorizontalAlignment(JTextField.CENTER);
        super.setFont(FONT_NUMBERS);
    }



    public void newGame(int number, boolean isGiven) {
        this.number = number;
        status = isGiven ? CellStatus.GIVEN : CellStatus.TO_GUESS;
        paint();
    }

    public void paint() {
        if (status == CellStatus.GIVEN) {
            super.setText(number + "");
            super.setEditable(false);
            super.setBackground(BG_GIVEN);
            super.setForeground(FG_GIVEN);
        } else if (status == CellStatus.TO_GUESS) {
            super.setText("");
            super.setEditable(true);
            super.setBackground(BG_TO_GUESS);
            super.setForeground(FG_NOT_GIVEN);
        } else if (status == CellStatus.CORRECT_GUESS) {
            super.setBackground(BG_CORRECT_GUESS);
        } else if (status == CellStatus.WRONG_GUESS) {
            super.setBackground(BG_WRONG_GUESS);
        }
    }

    public CellStatus getStatus() {
        return status;
    }

    public int getNumber() {
        return number;
    }

    public void setStatus(CellStatus cellStatus) {
        this.status = cellStatus;
        paint();
    }

    public int getRow() {
        return row;
    }

    public int getCol(){
        return col;
    }

}

