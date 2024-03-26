package chess_ant;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class GameDataRetriever {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/projectjava";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        JFrame frame = new JFrame("CÁC VÁN ĐẤU");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("STT");
        model.addColumn("Bên Trắng");
        model.addColumn("Bên Đen");
        model.addColumn("Người thắng");
        model.addColumn("FEN");
        model.addColumn("Ngày");

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        retrieveGameData(isLoggedin.isLoggedin(), model);

        frame.setVisible(true);

        JPanel playerStatsPanel = new JPanel(new GridLayout(3, 2));
        frame.add(playerStatsPanel, BorderLayout.SOUTH);

        displayPlayerStats(isLoggedin.isLoggedin(), playerStatsPanel);
    }

    private static void retrieveGameData(int userId, DefaultTableModel model) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT games.gameid, " +
                    "user1.username AS player1, " +
                    "user2.username AS player2, " +
                    "IF(games.winnerid IS NULL, 'Draw', " +
                    "CASE " +
                    "WHEN games.winnerid = games.player1id THEN user1.username " +
                    "ELSE user2.username " +
                    "END) AS winner, " +
                    "games.FEN, " +
                    "games.date " +
                    "FROM games " +
                    "INNER JOIN users AS user1 ON games.player1id = user1.userid " +
                    "INNER JOIN users AS user2 ON games.player2id = user2.userid " +
                    "WHERE games.player1id = ? OR games.player2id = ? " +
                    "ORDER BY games.gameid"; 

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                int count = 1; 
                while (resultSet.next()) {
                    String player1 = resultSet.getString("player1");
                    String player2 = resultSet.getString("player2");
                    String winner = resultSet.getString("winner");
                    String fen = resultSet.getString("FEN");
                    Date date = resultSet.getDate("date");
                    model.addRow(new Object[] { count, player1, player2, winner, fen, date });
                    count++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayPlayerStats(int userId, JPanel playerStatsPanel) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT COUNT(*) AS totalGames, " +
                    "SUM(CASE WHEN games.winnerid = ? THEN 1 ELSE 0 END) AS wins, " +
                    "elo " +
                    "FROM games " +
                    "INNER JOIN users ON (games.player1id = users.userid OR games.player2id = users.userid) " +
                    "WHERE users.userid = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int totalGames = resultSet.getInt("totalGames");
                    int wins = resultSet.getInt("wins");
                    int elo = resultSet.getInt("elo");

                    JLabel totalGamesLabel = new JLabel("Tổng số ván: " + totalGames);
                    JLabel winsLabel = new JLabel("Số ván thắng: " + wins);
                    JLabel winRateLabel = new JLabel(
                            "Tỷ lệ thắng: " + (totalGames > 0 ? (wins * 100 / totalGames) : 0) + "%");
                    JLabel eloLabel = new JLabel("ELO: " + elo);

                    playerStatsPanel.add(totalGamesLabel);
                    playerStatsPanel.add(winsLabel);
                    playerStatsPanel.add(winRateLabel);
                    playerStatsPanel.add(eloLabel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}