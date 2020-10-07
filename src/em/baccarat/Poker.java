package em.baccarat;

import com.sun.xml.ws.api.model.wsdl.WSDLOutput;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Ron
 * @date 2020/10/7 下午 04:16
 */
class Card {
    private String suit;
    private char symbol;

    public Card(String suit, char symbol) {
        this.suit = suit;
        this.symbol = symbol;
    }


    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public String toString() {
        return suit + symbol;
    }
}

public class Poker {

    private final static int pack = 8;
    private final static int cardsNum = 52;


    private static String suit(int number) {
        switch (((number - 1) / 13) % 4) {
            case 0:
                return "桃";
            case 1:
                return "心";
            case 2:
                return "磚";
            default:
                return "梅";
        }
    }

    private static char symbol(int number) {
        int remain = number % 13;
        switch (remain) {
            case 0:
                return 'K';
            case 1:
                return 'A';
            case 11:
                return 'J';
            case 12:
                return 'Q';
            default:
                return (char) remain;
        }
    }

    public static void shuffle(List<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            Collections.swap(cards, i,
                    (int) (Math.random() * cards.size() - 1));
        }
//        return cards.toArray(new Card[cardsNum * pack]);
    }

    public static List<Card> buildPackOfCards(int pack) {
        List<Card> cards = new LinkedList<>();
        for (int i = 0; i < cardsNum * pack; i++) {
            cards.add(new Card(suit(i + 1), symbol(i + 1)));
        }
        return cards;
    }

    public static List<Card> takeOutCards(Stack<Card> cardStack, int cardsNum) {
        List<Card> cardList = new LinkedList<>();
        IntStream.range(0, cardsNum).forEach(i -> cardList.add(cardStack.pop()));
        return cardList;
    }

    public static void cutTheDeck(List<Card> cards) {
        Random random = new Random();
//        System.out.println("cards.size() = " + cards.size());
        int cutNum = random.nextInt(cards.size());
//        System.out.println("cutNum = " + cutNum);
        cards.subList(0, cutNum).clear();
//        System.out.println("cards.size() = " + cards.size());
    }


    public static void main(String[] args) {
        // 初始牌
        List<Card> cards = buildPackOfCards(pack);
        // 切牌
        cutTheDeck(cards);
        //洗牌
        shuffle(cards);
        Stack<Card> cardStack = new Stack<>();
        cardStack.addAll(cards);
        // 发牌
        List<Card> takeOutCards = takeOutCards(cardStack, 4);
        int indexOpt = IntStream.range(0, takeOutCards.size())
                .filter(i -> i % 2 == 0)
                .map(e -> {
                    return 1;
                })
                .sum();
//        takeOutCards.stream().filter().forEach(
//                e -> {
//                    System.out.println(e);
//                    switch (e.getSymbol()) {
//                        case 'A':
//
//                            break;
//                        default:
//                            break;
//                    }
//                }
//
//        );
//        cardStack.pop();
//        IntStream.range(0, 4).forEach(i -> System.out.println(cardStack.pop()));

        System.out.println("=============================");

//        for (int i = 0; i < cardStack.size(); i++) {
//            System.out.println(cardStack.pop());
//        }
//        cards.stream().forEach(e -> {
//            cards.remove(e);
//            System.out.printf(
//                    "%s%c", cards.get(i), (i + 1) % 13 == 0 ? '\n' : ' ');
//        });
//        cards.remove(0);
//        shuffle(cards);
//        for (int i = 0; i < cards.size(); i++) {
//            System.out.printf("%s%c", cards.get(i), (i + 1) % 13 == 0 ? '\n' : ' ');
//
//        }
    }
}
