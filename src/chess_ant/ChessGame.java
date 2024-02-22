package chess_ant;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessGame extends JFrame {
    private JButton[][] squares = new JButton[8][8];
    private String[][] board = new String[8][8];
    private JButton selectedSquare = null;
    private boolean isWhiteTurn;
    private int bot;

    public ChessGame() {
        String[] options = { "Trắng", "Đen" };
        int result = JOptionPane.showOptionDialog(this, "Chọn màu quân cờ bạn muốn chơi", "Chọn màu quân cờ",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        isWhiteTurn = (result == JOptionPane.YES_OPTION);
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

        initializeBoard2(); // Gọi phương thức này đầu tiên

        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bot == -1) {
                    if (Chess_AI.Chess_AI(board, bot).length != 0) { // Kiểm tra xem Chess_AI đã trả về kết quả
                                                                           // chưa
                        ((Timer) e.getSource()).stop(); // Dừng Timer

                        getContentPane().removeAll(); // Xóa tất cả các component khỏi JFrame
                        initializeBoard(); // Gọi phương thức này sau khi Chess_AI đã trả ra kết quả
                        setupGUI();

                        if (bot == -1) {
                            int next[] = Chess_AI.Chess_AI(board, bot);
                            setPieceOnButton(next[2], next[3]);
                            clearPieceOnButton(next[0], next[1]);
                        }

                        revalidate(); // Cập nhật lại giao diện
                        repaint(); // Vẽ lại giao diện
                    }
                }

                else {
                    ((Timer) e.getSource()).stop(); // Dừng Timer
                    getContentPane().removeAll(); // Xóa tất cả các component khỏi JFrame

                    initializeBoard(); // Gọi phương thức này sau khi đã đợi 10 giây
                    setupGUI();

                    enableSquareClick = true;
                    revalidate(); // Cập nhật lại giao diện
                    repaint(); // Vẽ lại giao diện
                }
            }
        });
        timer.start(); 
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

    private void initializeBoard2() {
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

    public void updateBoard()
    {

    }
    private boolean enableSquareClick = true;
    private void endGame() {
        JOptionPane.showMessageDialog(this, "Game Over");
        enableSquareClick = false;
    }
    private boolean isKingCaptured() {
        boolean whiteKingExists = false;
        boolean blackKingExists = false;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col].equals("K")) {
                    whiteKingExists = true; // Quân King trắng tồn tại
                } else if (board[row][col].equals("k")) {
                    blackKingExists = true; // Quân King đen tồn tại
                }
            }
        }
        return !whiteKingExists || !blackKingExists;
    }

    private void setupGUI() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                final int currentRow = row;
                final int currentCol = col;
                // System.out.println("SetupGUI");

                squares[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (enableSquareClick) { // Kiểm tra xem có nên xử lý sự kiện click không
                            handleSquareClick(currentRow, currentCol);

                            if (isKingCaptured()) {
                                endGame(); // Kết thúc trò chơi
                            }
                        }
                    }
                });
            }
        }
    }

    public static boolean checkSide(String str, int bot) {
        if (str.matches("[A-Z]+") && bot == -1) {
            return true;
        }
        if (str.matches("[A-Z]+") == false && bot == 1) {
            return true;
        }
        return false;
    }

    private boolean handleSquareClick(int row, int col) {

        JButton clickedSquare = squares[row][col];

        if (selectedSquare == null) {
            selectedSquare = clickedSquare;
        } else {
            int fromRow = getRowOfButton(selectedSquare);
            int fromCol = getColOfButton(selectedSquare);

            if (isValidMove.isValidMove(fromRow, fromCol, row, col, board) == false
                    || checkSide(board[fromRow][fromCol], bot) == false)
            {
                System.out.println("false");
                selectedSquare = null;
                return false;
            }
           
            board[row][col] = board[fromRow][fromCol];
            board[fromRow][fromCol] = "| |";
            WriteBoardToFile.WriteBoardToFile(board); //FUCK
            setPieceOnButton(row, col);
            clearPieceOnButton(fromRow, fromCol);
            selectedSquare = null;
            revalidate();
            repaint();
            int next[] = Chess_AI.Chess_AI(board, bot);
            WriteBoardToFile.WriteBoardToFile(board); //FUCK
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
        
    }

    private void clearPieceOnButton(int row, int col) {
        squares[row][col].setIcon(null);
    }

    private ImageIcon getPieceIcon(String piece) {
        String path = "";
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
        ImageIcon icon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        return icon;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChessGame().setVisible(true);
            }
        });
    }
}