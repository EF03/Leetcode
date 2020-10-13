package em.baccarat;

import java.util.*;

/**
 * @author Ron
 * @date 2020/10/7 下午 04:16
 */

public class Croupier extends Thread {
    private static boolean isThisContinue = true;
    private final static int pack = 8;
    private final static int cardsNum = 52;
    private static Stack<Card> cardStack = new Stack<>();

    // 初始化
    static {
        // 初始牌
        List<Card> cards = buildPackOfCards(pack);
        // 切牌
        cutTheDeck(cards);
        //洗牌
        shuffle(cards);
        cardStack.addAll(cards);
    }


    public static int getAlreadyUsedNum() {
        return pack * cardsNum - cardStack.size();
    }

    private static char suit(int number) {
        switch (((number - 1) / 13) % 4) {
            // ♥
            case 0:
                return '\u2665';
            //♦
            case 1:
                return '\u2666';
            // ♣
            case 2:
                return '\u2663';
            //♠
            default:
                return '\u2660';
        }
    }

    private static char symbol(int number) {
        int remain = number % 13;
        return (char) (remain + 65);
    }

    public static void shuffle(List<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            Collections.swap(cards, i,
                    (int) (Math.random() * cards.size() - 1));
        }
    }

    public static List<Card> buildPackOfCards(int pack) {
        List<Card> cards = new LinkedList<>();
        for (int i = 0; i < cardsNum * pack; i++) {
            cards.add(new Card(suit(i + 1), symbol(i + 1)));
        }
        return cards;
    }

    public static void cutTheDeck(List<Card> cards) {
        Random random = new Random();
        // 只切一半以內
        int cutNum = random.nextInt(cards.size() / 2);
        cards.subList(0, cutNum).clear();
    }

    public static Map<String, List<Card>> firstDistribute(Stack<Card> cardStack) {
        Map<String, List<Card>> result = new HashMap<>(4);
        List<Card> odd = new ArrayList<>();
        List<Card> even = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) {
                odd.add(cardStack.pop());
            } else {
                even.add(cardStack.pop());
            }
        }
        result.put("odd", odd);
        result.put("even", even);
        return result;
    }

    public static int getCardsSum(List<Card> cards) {
        return cards.stream()
                .mapToInt(Card::getSymbol)
                .map(e -> e - 64)
                .filter(e -> e < 10)
                .sum() % 10;
    }

    private static void playBaccarat() {

        if (cardStack.size() < 7) {
            cardStack.clear();
            // 初始牌
            List<Card> cards = buildPackOfCards(pack);
            // 切牌
            cutTheDeck(cards);
            //洗牌
            shuffle(cards);
            cardStack.addAll(cards);
        }
        // 初始发排
        Map<String, List<Card>> firstDistributeMap = firstDistribute(cardStack);
        List<Card> odd = firstDistributeMap.get("odd");
        List<Card> even = firstDistributeMap.get("even");
        printCards(odd);
        printCards(even);

        baccaratDraw(odd, even, cardStack);
        System.out.println("===========抽卡後==================");

        // 分勝負
        printCards(odd);
        printCards(even);

        int sumOdd = getCardsSum(odd);
        int sumEven = getCardsSum(even);

        System.out.println("sumOdd = " + sumOdd);
        System.out.println("sumEven = " + sumEven);

        int differenceOdd = differenceNine(sumOdd);
        int differenceEven = differenceNine(sumEven);
        System.out.println("differenceOdd = " + differenceOdd);
        System.out.println("differenceEven = " + differenceEven);

        // 判斷結果
        if (differenceOdd < differenceEven) {
            System.out.println("== 閒贏 ==");
        } else if (differenceOdd > differenceEven) {
            System.out.println("== 莊贏 ==");
        } else {
            System.out.println("== 和局 ==");
        }

        // 判斷前兩張是否是對子
        if (isPair(odd)) {
            System.out.println("== 閒對 ==");
        }
        if (isPair(even)) {
            System.out.println("== 莊對 ==");
        }

        // 判斷大小 大為true 小為false
        if (isBig(odd, even)) {
            System.out.println("== 大 ==");
        } else {
            System.out.println("== 小 ==");
        }
        // 判斷 單雙
        if (sumOdd % 2 == 0) {
            System.out.println("== 閒雙 ==");
        } else {
            System.out.println("== 閒單 ==");
        }
        if (sumEven % 2 == 0) {
            System.out.println("== 莊雙 ==");
        } else {
            System.out.println("== 莊單 ==");
        }
    }

    private static void printCards(List<Card> cards) {
        int symbol;
        List<Card> printCard = new ArrayList<>();
        for (Card card : cards) {
            symbol = card.getSymbol() - 64;
            switch (symbol) {
                case 13:
                    symbol = 'K';
                    break;
                case 1:
                    symbol = 'A';
                    break;
                case 10:
                    symbol = 'T';
                    break;
                case 11:
                    symbol = 'J';
                    break;
                case 12:
                    symbol = 'Q';
                    break;
                default:
                    symbol = (symbol + 48);
                    break;
            }
            Card cloneCard = new Card(card.getSuit(), (char) symbol);
            printCard.add(cloneCard);
        }
        System.out.println(printCard);
    }

    private static boolean isBig(List<Card> odd, List<Card> even) {
        return odd.size() + even.size() > 4;
    }

    private static boolean isPair(List<Card> cards) {
        return cards.get(0).getSymbol() == cards.get(1).getSymbol();
    }

    private static int getSymbolNum(char symbol) {
        return (int) symbol - 64;
    }

    private static void baccaratDraw(List<Card> odd, List<Card> even, Stack<Card> cardStack) {
        // 莊閒 累計
        int tempOddNum = 0;
        int sumOdd = getCardsSum(odd);
        int sumEven = getCardsSum(even);
        Card oddThird = null;
        System.out.println("sumOdd = " + sumOdd);
        System.out.println("sumEven = " + sumEven);
        // 天生贏家
        if (sumOdd == 8 || sumOdd == 9 || sumEven == 8 || sumEven == 9) {
            return;
        }
        // 閒家有補牌 莊家才補
        if (sumOdd <= 5) {
            oddThird = cardStack.pop();
            odd.add(oddThird);

            tempOddNum = getSymbolNum(oddThird.getSymbol());
            switch (sumEven) {
                case 0:
                case 1:
                case 2:
                    even.add(cardStack.pop());
                    break;
                case 3:
                    if (tempOddNum == 8) {
                        return;
                    } else {
                        even.add(cardStack.pop());
                    }
                    break;
                case 4:
                    if (tempOddNum == 0 || tempOddNum == 1 || tempOddNum == 8 || tempOddNum == 9) {
                        return;
                    } else {
                        even.add(cardStack.pop());
                    }
                    break;
                case 5:
                    if (tempOddNum == 0 || tempOddNum == 1 || tempOddNum == 2 || tempOddNum == 3 || tempOddNum == 8 || tempOddNum == 9) {
                        return;
                    } else {
                        even.add(cardStack.pop());
                    }
                    break;
                case 6:
                    if (tempOddNum == 6 || tempOddNum == 7) {
                        even.add(cardStack.pop());
                    } else {
                        return;
                    }
                    break;
                default:
                    break;
            }
        }
        // 閒家 沒補牌 判斷莊家補牌
        else {
            if (sumEven <= 5) {
                even.add(cardStack.pop());
            }
        }
    }

    private static int differenceNine(int sum) {
        return (9 - sum);
    }


    @Override
    public void run() {
        int i = 0;
        while (isThisContinue && LayoutService.isAllContinue) {
            System.out.println("已使用牌數 = " + getAlreadyUsedNum());
            playBaccarat();
            System.out.println("已使用牌數 = " + getAlreadyUsedNum());
            System.out.println("======== 抽下一次 ========");
            ++i;
            if (i > 5) {
                isThisContinue = false;
            }
        }
    }

    public static void main(String[] args) {

        Croupier croupier = new Croupier();
        croupier.run();


//        System.out.println("已使用牌數 = " + getAlreadyUsedNum());
//
//        for (int i = 0; i < 10; i++) {
//            playBaccarat();
//            System.out.println();
//            System.out.println("======== 抽下一次 ========");
//        }
//
//        System.out.println("已使用牌數 = " + getAlreadyUsedNum());
    }

}
