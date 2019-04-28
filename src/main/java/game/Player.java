package game;

public class Player {
    private char symbol;
    private String name;
    private int score;
    public Player(char symbol, int score) {
        this.symbol = symbol;
        this.score = score;
    }
    public char getSymbol() {
        return symbol;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String toString() {
        return "Player " + name + " symbol:" + symbol;
    }
    public int getScore() {
        return score;
    }
}
