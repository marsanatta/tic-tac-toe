package game;

import java.util.Scanner;

public class Game {
    private static final int PLAYER_NUM = 2;
    private static final int BOARD_SIZE = 3;

    private Player[] players = new Player[PLAYER_NUM];
    private int curPlayerIdx = 0;
    private Scanner input = new Scanner(System.in);
    private Integer[][] board = new Integer[BOARD_SIZE][BOARD_SIZE];
    private int[] rowScore = new int[BOARD_SIZE];
    private int[] colScore = new int[BOARD_SIZE];
    private int[] diag1Score = new int[BOARD_SIZE * 2];
    private int[] diag2Score = new int[BOARD_SIZE * 2];
    private int moveCnt = 0;
    public int swithNextPlayer() {
         curPlayerIdx = 1 - curPlayerIdx;
         return curPlayerIdx;
    }

    public Game() {
        players[0] = new Player('O', 1);
        players[1] = new Player('X', -1);
    }

    public void inputPlayersInfo() {
        for (Player p : players) {
            System.out.println("Please enter the name for player " + p.getSymbol());
            p.setName(input.next());
        }
    }

    private void printBoard() {
        for (int i = 0; i < board.length; i++) {
            System.out.print('|');
            for (int j = 0; j < board[0].length; j++) {
                Integer player = board[i][j];
                System.out.print((player == null ? ' ' : players[player].getSymbol()) + "|");
            }
            System.out.println();
        }
    }

    public void printPlayerInfo() {
        for (Player p : players) {
            System.out.print(p + " ");
        }
        System.out.println();
    }

    private boolean isMoveValid(int r, int c) {
        return r >= 0 && r < BOARD_SIZE
                && c >= 0 && c < BOARD_SIZE
                && board[r][c] == null;
    }

    private int[] inputMove() throws Exception {
        System.out.println("row:");
        int r = input.nextInt();
        System.out.println("col:");
        int c = input.nextInt();
        if (!isMoveValid(r, c))
            throw new Exception("Invalid input");
        return new int[]{r, c};
    }

    public void run() {
        while (true) {
            printPlayerInfo();
            printBoard();
            System.out.println("Player " + players[curPlayerIdx].getName() + " 's move");
            int[] move = null;
            boolean isValidMove = false;
            while (!isValidMove) {
                try {
                    move = inputMove();
                    isValidMove = true;
                } catch (Exception e) {
                    System.out.println("Invalid input");
                    input.nextLine(); //consume token
                }
            }
            moveCnt++;
            int r = move[0], c = move[1];
            board[r][c] = curPlayerIdx;
            if (isWin(r, c)) {
                printBoard();
                System.out.println("Winner is player " + players[curPlayerIdx].getName());
                if(playAgain()) {
                    continue;
                } else {
                    return;
                }
            } else if (moveCnt == BOARD_SIZE * BOARD_SIZE) {
                System.out.println("Draw!");
                if(playAgain()) {
                    continue;
                } else {
                    return;
                }
            }
            swithNextPlayer();
        }
    }
    private boolean playAgain() {
        if(inputPlayAgain()) {
            reset();
            return true;
        } else {
            System.out.println("Game Over");
            return false;
        }
    }
    private void reset() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = null;
            }
        }
        for (int i = 0; i < BOARD_SIZE; i++) {
            rowScore[i] = 0;
            colScore[i] = 0;
        }
        for (int i = 0; i < BOARD_SIZE * 2; i++) {
            diag1Score[i] = 0;
            diag2Score[i] = 0;
        }
        curPlayerIdx = 0;
        moveCnt = 0;
    }

    private boolean inputPlayAgain() {
        String yn;
        while (true) {
            System.out.println("Play again? (Y/N)");
            yn = input.next().trim();
            if (!yn.equals("Y") && !yn.equals("N"))
                System.out.println("Invalid input");
            else
                break;
        }
        return yn.equals("Y");
    }

    private boolean isWin(int r, int c) {
        Player curPlayer = players[curPlayerIdx];
        int score = curPlayer.getScore();
        int winScore = BOARD_SIZE * score;
        rowScore[r] += score;
        if (rowScore[r] == winScore)
            return true;
        colScore[c] += score;
        if (colScore[c] == winScore)
            return true;
        int idx = r - c + BOARD_SIZE;
        diag1Score[idx] += score;
        if (diag1Score[idx] == winScore)
            return true;
        idx = r + c;
        diag2Score[idx] += score;
        if (diag2Score[idx] == winScore)
            return true;
        return false;
    }

}
