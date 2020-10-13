package em.baccarat;

/**
 * @author Ron
 * @date 2020/10/8 下午 12:45
 */
public class Card {
    /*
       A-B-C-D-E-F-G-H-I- J-K-L-M
       K-A-2-3-4-5-6-7-8-9-10-J-Q
       A-2-3-4-5-6-7-8-9-10-J-Q-K
   */
    private char suit;
    private char symbol;

    public Card(char suit, char symbol) {
        this.suit = suit;
        this.symbol = symbol;
    }


    public char getSuit() {
        return suit;
    }

    public void setSuit(char suit) {
        this.suit = suit;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public String toString() {
        return suit + "" + symbol;
    }
}
