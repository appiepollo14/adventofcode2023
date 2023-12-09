package nl.avasten.day7;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Hand implements Comparable<Hand> {

    private final String chars;
    private Map<Integer, Integer> qtyPerChar = new HashMap<>();
    private int bet;
    private String resultType;

    public Hand(String chars, int bet) {
        this.chars = chars;
        this.bet = bet;
        this.qtyPerChar = mapper(chars);
        this.countResult();
    }

    public int getBet() {
        return bet;
    }

    public String getChars() {
        return chars;
    }

    private Map<Integer, Integer> mapper(String chars) {

        for (char currentChar : chars.toCharArray()) {
            int charValue = charToInt(currentChar);
            qtyPerChar.put(charValue, qtyPerChar.getOrDefault(charValue, 0) + 1);
        }
        return qtyPerChar;
    }

    private int charToInt(char c) {
        int charValue;
        if (c == 'A') {
            charValue = 14;
        } else if (c == 'K') {
            charValue = 13;
        } else if (c == 'Q') {
            charValue = 12;
        } else if (c == 'J') {
            charValue = 11;
        } else if (c == 'T') {
            charValue = 10;
        } else {
            charValue = Character.getNumericValue(c);
        }
        return charValue;
    }

    private void countResult() {
        if (this.qtyPerChar.containsValue(5)) {
            this.resultType = "g";
        } else if (this.qtyPerChar.containsValue(4)) {
            this.resultType = "f";
        } else if (this.qtyPerChar.containsValue(3) && this.qtyPerChar.containsValue(2)) {
            this.resultType = "e";
        } else if (this.qtyPerChar.containsValue(3)) {
            this.resultType = "d";
        } else if (this.qtyPerChar.containsValue(2)) {
            if (Collections.frequency(this.qtyPerChar.values(), 2) == 2) {
                this.resultType = "c";
            } else {
                this.resultType = "b";
            }
        } else {
            this.resultType = "a";
        }
    }

    @Override
    public int compareTo(Hand that) {

        // Sort resulttype highest to lowest
        int result = that.resultType.compareTo(this.resultType);
        if (result == 0) {
            for (int i = 0; i < that.chars.toCharArray().length; i++) {
                result = Integer.compare(charToInt(that.chars.toCharArray()[i]),charToInt(this.chars.toCharArray()[i]));
                if (result != 0) {
                    return result;
                }
            }
        }

        return result;
    }
}
