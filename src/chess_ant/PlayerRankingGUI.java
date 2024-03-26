package chess_ant;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PlayerRankingGUI extends JFrame {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/projectjava";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public PlayerRankingGUI() {
        setTitle("Player Ranking");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Rank");
        model.addColumn("Kỳ thủ");
        model.addColumn("ELO");

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        retrievePlayerRanking(model);

        setVisible(true);
    }

    private void retrievePlayerRanking(DefaultTableModel model) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT username, elo FROM users ORDER BY elo DESC";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                int rank = 1;
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    int elo = resultSet.getInt("elo");
                    model.addRow(new Object[]{rank, username, elo});
                    rank++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PlayerRankingGUI::new);
    }
}


