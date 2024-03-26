package chess_ant;

public class EloCalculator {
    
     
    public static int calculateElo(int player1Elo, int player2Elo, double player1Score) {
        
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
        double player1Score = 1; 

        int newEloScores = calculateElo(player1Elo, player2Elo, player1Score);

        System.out.println("New Elo for Player 1: " + newEloScores);
    }
}

