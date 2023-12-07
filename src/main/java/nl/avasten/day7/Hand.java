package nl.avasten.day7;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<Character> cards = new ArrayList<>();
    private int bet;
    private String resultType;
    private int value;

    public Hand(List<Character> cards, int bet) {
        this.cards = cards;
        this.bet = bet;
    }
}
