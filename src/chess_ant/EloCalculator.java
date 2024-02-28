package chess_ant;

public class EloCalculator {
    // Hệ số K tùy chọn (độ nhạy của Elo score)
    // private static final int K_FACTOR = 20;

    /**
     * Tính điểm Elo mới của mỗi người chơi dựa trên kết quả của trò chơi và điểm Elo của họ.
     *
     * @param player1Elo  Điểm Elo của người chơi 1
     * @param player2Elo  Điểm Elo của người chơi 2
     * @param player1Score Kết quả của người chơi 1 (1: thắng, 0.5: hòa, 0: thua)
     * @return Mảng chứa Elo mới của hai người chơi (index 0 là Elo của người chơi 1, index 1 là Elo của người chơi 2)
     */
    public static int calculateElo(int player1Elo, int player2Elo, double player1Score) {
        // double player1ExpectedScore = 1 / (1 + Math.pow(10, (player2Elo - player1Elo) / 400.0));
        // double player2ExpectedScore = 1 / (1 + Math.pow(10, (player1Elo - player2Elo) / 400.0));

        // int player1NewElo = (int) (player1Elo + K_FACTOR * (player1Score - player1ExpectedScore));
        // int player2NewElo = (int) (player2Elo + K_FACTOR * ((1 - player1Score) - player2ExpectedScore));

        // return new int[]{player1NewElo, player2NewElo};

        int K_FACTOR=0;
        if(player1Elo<1600)
        {
            K_FACTOR=25;
        }
        else if(player1Elo<2000)
        {
            K_FACTOR=20;
        }
        else if(player1Elo<2400)
        {
            K_FACTOR=15;
        }
        else
        {
            K_FACTOR=10;
        }
        double playerExpectedScore = 1 / (1 + Math.pow(10, (player2Elo - player1Elo) / 400.0));
        int playerNewElo = (int) (player1Elo + K_FACTOR * (player1Score - playerExpectedScore));

        return playerNewElo;
    }

    public static void main(String[] args) {
        int player1Elo = 1609;
        int player2Elo =  1613;
        double player1Score = 1; // player 1 wins

        int newEloScores = calculateElo(player1Elo, player2Elo, player1Score);

        System.out.println("New Elo for Player 1: " + newEloScores);
        // System.out.println("New Elo for Player 2: " + newEloScores[1]);
    }
}

