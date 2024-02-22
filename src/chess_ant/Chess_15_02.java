package chess_ant;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Chess_15_02 extends JFrame {
    private JButton[][] squares = new JButton[8][8];
    private String[][] board = new String[8][8];

    private JButton selectedSquare = null;

    private boolean isWhiteTurn;
    private int bot;
//fuck
    public Chess_15_02() {

        String[] options = { "Trắng", "Đen" };
        int result = JOptionPane.showOptionDialog(this, "Chọn màu quân cờ bạn muốn chơi", "Chọn màu quân cờ",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        // Xác định lượt đi của quân trắng dựa trên lựa chọn của người chơi
        isWhiteTurn = (result == JOptionPane.YES_OPTION); // Nếu chọn "Trắng", isWhiteTurn = true, ngược lại isWhiteTurn
                                                          // = false

        if (isWhiteTurn) {
            bot = 1;
        } else {
            bot = -1;
        }

        System.out.println("Chess");
        setTitle("Chess Board");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));

        initializeBoard();

        
        

        setupGUI();
        

    }

    



    private void initializeBoard() {

        board = initializeBoard.initializeBoard();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col] = new JButton();
                squares[row][col].setPreferredSize(new Dimension(50, 50));

                if ((row + col) % 2 == 0) {
                    squares[row][col].setBackground(Color.WHITE);
                } else {
                    squares[row][col].setBackground(Color.green);
                }

                setPieceOnButton(row, col);

                add(squares[row][col]);
            }
        }
    }

    private void setupGUI() {

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                final int currentRow = row;
                final int currentCol = col;

                squares[row][col].addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        handleSquareClick(currentRow, currentCol);

                        if (isKingCaptured()) {
                            endGame(); // Kết thúc trò chơi
                        }

                    }
                });
            }
        }
    }

    private boolean isKingCaptured() {
        boolean whiteKingExists = false;
        boolean blackKingExists = false;

        // Duyệt qua toàn bộ bàn cờ để kiểm tra xem quân King nào còn tồn tại
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col].equals("K")) {
                    whiteKingExists = true; // Quân King trắng tồn tại
                } else if (board[row][col].equals("k")) {
                    blackKingExists = true; // Quân King đen tồn tại
                }
            }
        }

        // Nếu một trong hai quân King bị mất, trả về true
        return !whiteKingExists || !blackKingExists;
    }

    private void endGame() {
        // Hiển thị thông báo kết thúc trò chơi
        JOptionPane.showMessageDialog(this, "Game Over");

        // Vô hiệu hóa các sự kiện click trên bàn cờ
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col].setEnabled(false);
            }
        }
    }

    public static boolean checkSide(String str, int bot) {


        System.out.println("FUCK");

        if (str.matches("[A-Z]+") && bot == -1) {
            System.out.println("..........1");
            return true;
        }
        if (str.matches("[A-Z]+") == false && bot == 1) {
            System.out.println("..........2");
            return true;
        }
        System.out.println("str.matches(\"[A-Z]+\"): "+ str.matches("[A-Z]+"));
        System.out.println("bot: "+bot);
        return false;
    }

    public static void printBoard(String[][] board) {
        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {

                if (board[i][j].equals("| |")) {
                    // System.out.print(" ");
                    System.out.print("  ");
                }

                else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    private boolean handleSquareClick(int row, int col) {
        // printBoard(board);

        JButton clickedSquare = squares[row][col];

        if (selectedSquare == null) {
            selectedSquare = clickedSquare;
        } else {
            int fromRow = getRowOfButton(selectedSquare);
            int fromCol = getColOfButton(selectedSquare);

            // if(isValidMove.isValidMove(fromRow, fromCol,row,
            // col,board)==false||isBlack(board[fromRow][fromCol])==true)
            if (isValidMove.isValidMove(fromRow, fromCol, row, col, board) == false|| checkSide(board[fromRow][fromCol], bot)==false)

            // quân trắng chạy khi isblack()==true, đen chạy khi isblack()==false
            {
                System.out.println(fromRow + " " + fromCol + " " + row + " " + col);
                System.out.println("false");
                selectedSquare = null;

                return false;
            }

            else
            {
                System.out.println("checkSide(board[fromRow][fromCol], bot): "+checkSide(board[fromRow][fromCol], bot));
            }

            // Move the piece on the backend
            board[row][col] = board[fromRow][fromCol];
            board[fromRow][fromCol] = "| |";

            // Update the GUI
            setPieceOnButton(row, col);
            clearPieceOnButton(fromRow, fromCol);

            // WriteBoardToFile.WriteBoardToFile(board);
            // Reset the selected square
            // printBoard(board);

            selectedSquare = null;

            int next[] = Chess_AI.Chess_AI(board, bot);

            printBoard(board);

            

            setPieceOnButton(next[2], next[3]);
            clearPieceOnButton(next[0], next[1]);

        }
        return true;
    }

    private int getRowOfButton(JButton button) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (squares[row][col] == button) {
                    return row;
                }
            }
        }
        return -1;
    }

    private int getColOfButton(JButton button) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (squares[row][col] == button) {
                    return col;
                }
            }
        }
        return -1;
    }

    private void setPieceOnButton(int row, int col) {
        if (board[row][col] != null) {
            squares[row][col].setIcon(getPieceIcon(board[row][col]));
        }
        // System.out.println("setPieceOnButton");
    }

    private void clearPieceOnButton(int row, int col) {
        squares[row][col].setIcon(null);
        // System.out.println("clearPieceOnButton");
    }

    private ImageIcon getPieceIcon(String piece) {
        String path = "";
        // printBoard();
        if ("P".equals(piece)) {
            path = "src\\chess_ant\\img\\Black_Pawn.png";
        }

        if ("B".equals(piece)) {
            path = "src\\chess_ant\\img\\Black_Bishop.png";
        }

        if ("K".equals(piece)) {
            path = "src\\chess_ant\\img\\Black_King.png";
        }

        if ("Q".equals(piece)) {
            path = "src\\chess_ant\\img\\Black_Queen.png";
        }

        if ("R".equals(piece)) {
            path = "src\\chess_ant\\img\\Black_Rook.png";
        }

        if ("N".equals(piece)) {
            path = "src\\chess_ant\\img\\Black_Knight.png";
        }

        if ("p".equals(piece)) {
            path = "src\\chess_ant\\img\\White_Pawn.png";
        }

        if ("b".equals(piece)) {
            path = "src\\chess_ant\\img\\White_Bishop.png";
        }

        if ("k".equals(piece)) {
            path = "src\\chess_ant\\img\\White_King.png";
        }

        if ("q".equals(piece)) {
            path = "src\\chess_ant\\img\\White_Queen.png";
        }

        if ("r".equals(piece)) {
            path = "src\\chess_ant\\img\\White_Rook.png";
        }

        if ("n".equals(piece)) {
            path = "src\\chess_ant\\img\\White_Knight.png";
        }

        // Add more conditions for other board

        // Load the image and scale it to fit the button
        ImageIcon icon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        return icon;
    }

    public static void main(String[] args) {
        System.out.println("main");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Chess_15_02().setVisible(true);
            }
        });
    }
}