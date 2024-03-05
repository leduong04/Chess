package chess_ant.Drat_4_3;
import java.awt.*;
import javax.swing.*;

public class ChessBoard extends JFrame {
    private JLabel[][] boardSquares = new JLabel[8][8];
    private JPanel chessBoard;

    public ChessBoard() {
        initializeBoard();
        setPreferredSize(new Dimension(600, 600));
        setTitle("Chess Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeBoard() {
        chessBoard = new JPanel(new GridLayout(8, 8));
        chessBoard.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        chessBoard.setBackground(Color.WHITE);
        ImageIcon icon = new ImageIcon();

        for (int i = 0; i < boardSquares.length; i++) {
            for (int j = 0; j < boardSquares[i].length; j++) {
                JLabel square = new JLabel();
                square.setOpaque(true);
                square.setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.GRAY);
                boardSquares[i][j] = square;
                chessBoard.add(square);
            }
        }
        setPieces();
        add(chessBoard);
    }

    private void setPieces() {
        String[] pieces = {
            "R", "N", "B", "Q", "K", "B", "N", "R",
            "P", "P", "P", "P", "P", "P", "P", "P",
            "",  "",  "",  "",  "",  "",  "",  "",
            "",  "",  "",  "",  "",  "",  "",  "",
            "",  "",  "",  "",  "",  "",  "",  "",
            "",  "",  "",  "",  "",  "",  "",  "",
            "p", "p", "p", "p", "p", "p", "p", "p",
            "r", "n", "b", "q", "k", "b", "n", "r"
        };

        for (int i = 0; i < pieces.length; i++) {
            int row = i / 8;
            int col = i % 8;
            String piece = pieces[i];
            if (!piece.isEmpty()) {
                ImageIcon pieceIcon = new ImageIcon(getPieceImagePath(piece));
                boardSquares[row][col].setIcon(pieceIcon);
            }
        }
    }

    private String getPieceImagePath(String piece) {
        String path = "";
        if ("P".equals(piece)) {
            path = "D:\\Project\\Project_Java\\Chess_Ant\\src\\chess_ant\\img\\Black_Pawn.png";
        } else if ("B".equals(piece)) {
            path = "src\\chess_ant\\img\\Black_Bishop.png";
        } else if ("K".equals(piece)) {
            path = "src\\chess_ant\\img\\Black_King.png";
        } else if ("Q".equals(piece)) {
            path = "src\\chess_ant\\img\\Black_Queen.png";
        } else if ("R".equals(piece)) {
            path = "src\\chess_ant\\img\\Black_Rook.png";
        } else if ("N".equals(piece)) {
            path = "src\\chess_ant\\img\\Black_Knight.png";
        } else if ("p".equals(piece)) {
            path = "src\\chess_ant\\img\\White_Pawn.png";
        } else if ("b".equals(piece)) {
            path = "src\\chess_ant\\img\\White_Bishop.png";
        } else if ("k".equals(piece)) {
            path = "src\\chess_ant\\img\\White_King.png";
        } else if ("q".equals(piece)) {
            path = "src\\chess_ant\\img\\White_Queen.png";
        } else if ("r".equals(piece)) {
            path = "src\\chess_ant\\img\\White_Rook.png";
        } else if ("n".equals(piece)) {
            path = "src\\chess_ant\\img\\White_Knight.png";
        }
        return path;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChessBoard());
    }
}
