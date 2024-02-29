package chess_ant;


public class Bot {
    private static String[][] previousBoardState;

    public static void Bot(int bot) {

        if (bot == -1) {
            previousBoardState = initializeBoard.initializeBoard();
            WriteBoardToFile.WriteBoardToFile(previousBoardState);

            for (int i = 0; i < 1000; i++) {
                System.out.println(i);
            }
            Chess_AI.Chess_AI(previousBoardState, -1);
            WriteBoardToFile.WriteBoardToFile(previousBoardState);
        } else {
            previousBoardState = ReadBoardFromFile.ReadBoardFromFile();
        }
        while (whoWon.displayWinner(ReadBoardFromFile.ReadBoardFromFile())==0) {
            try {
                Thread.sleep(100); // Chờ 1 giây trước khi kiểm tra lại
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String[][] currentBoardState = ReadBoardFromFile.ReadBoardFromFile();
            // whoWon.displayWinner(currentBoardState);
            if (isBoardStateChanged(currentBoardState)&&whoWon.whoWon(ReadBoardFromFile.ReadBoardFromFile())==0) {
                Chess_AI.Chess_AI(currentBoardState, bot);
                WriteBoardToFile.WriteBoardToFile(currentBoardState);

            }
        }
    }



    private static boolean isBoardStateChanged(String[][] currentBoardState) {
        if (previousBoardState == null || previousBoardState.length != currentBoardState.length) {
            previousBoardState = currentBoardState;
            return true;
        }

        for (int i = 0; i < currentBoardState.length; i++) {
            for (int j = 0; j < currentBoardState[i].length; j++) {
                if (!currentBoardState[i][j].equals(previousBoardState[i][j])) {
                    previousBoardState = currentBoardState;
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {

        //
        previousBoardState = initializeBoard.initializeBoard();
        WriteBoardToFile.WriteBoardToFile(previousBoardState);

        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
        }
        Chess_AI.Chess_AI(previousBoardState, -1);
        WriteBoardToFile.WriteBoardToFile(previousBoardState);

        
        while (true) {
            

            String[][] currentBoardState = ReadBoardFromFile.ReadBoardFromFile();

            if (isBoardStateChanged(currentBoardState)) {
                Chess_AI.Chess_AI(currentBoardState, -1);
                WriteBoardToFile.WriteBoardToFile(currentBoardState);

            }

            // try {
            //     Thread.sleep(100); // Chờ 1 giây trước khi kiểm tra lại
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
        }
    }
}
