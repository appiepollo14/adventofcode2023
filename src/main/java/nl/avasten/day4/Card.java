package nl.avasten.day4;

import java.util.ArrayList;
import java.util.List;

public class Card {

    private int id;
    private List<Integer> winningNumbers = new ArrayList<>();
    private List<Integer> myNumbers = new ArrayList<>();
    private int winners = 0;

    public void setWinners(int winners) {
        this.winners = winners;
    }

    public int getWinners() {
        return winners;
    }

    public Card(int id, List<Integer> winningNumbers, List<Integer> myNumbers) {
        this.id = id;
        this.winningNumbers = winningNumbers;
        this.myNumbers = myNumbers;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getWinningNumbers() {
        return winningNumbers;
    }


    public List<Integer> getMyNumbers() {
        return myNumbers;
    }

}
