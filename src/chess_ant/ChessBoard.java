package chess_ant;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessBoard extends JFrame {
    private static final int BOARD_SIZE = 8;
    private static final int CELL_SIZE = 60;
    private JPanel chessBoardPanel;
    private JLabel[][] chessCells = new JLabel[BOARD_SIZE][BOARD_SIZE];

    public ChessBoard() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Chess Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(BOARD_SIZE * CELL_SIZE, BOARD_SIZE * CELL_SIZE);
        setLayout(new BorderLayout());

        chessBoardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        chessBoardPanel.setPreferredSize(new Dimension(BOARD_SIZE * CELL_SIZE, BOARD_SIZE * CELL_SIZE));
        add(chessBoardPanel, BorderLayout.CENTER);

        initializeCells();
    }

    private void initializeCells() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                JLabel cell = new JLabel();
                cell.setOpaque(true);
                cell.setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.GRAY);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                chessCells[i][j] = cell;
                chessBoardPanel.add(cell);
            }
        }

        // Setup initial chess positions
        // For simplicity, let's just place some pawns
        for (int i = 0; i < BOARD_SIZE; i++) {
            chessCells[1][i].setIcon(new ImageIcon("pawn_white.png"));
            chessCells[6][i].setIcon(new ImageIcon("pawn_black.png"));
        }

        // Add mouse listener to cells for moving pieces
        addMouseListenerToCells();
    }

    private void addMouseListenerToCells() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                JLabel cell = chessCells[i][j];
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        int row = -1;
                        int col = -1;

                        // Find the clicked cell
                        for (int i = 0; i < BOARD_SIZE; i++) {
                            for (int j = 0; j < BOARD_SIZE; j++) {
                                if (chessCells[i][j] == cell) {
                                    row = i;
                                    col = j;
                                    break;
                                }
                            }
                        }

                        if (row != -1 && col != -1) {
                            System.out.println("Selected cell: " + (char)('A' + col) + (BOARD_SIZE - row));
                        }
                    }
                });
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessBoard chessBoard = new ChessBoard();
            chessBoard.setVisible(true);
        });
    }
}
