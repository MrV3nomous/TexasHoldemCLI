package poker;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {

    protected String name;
    protected int chips;

    protected List<Card> hand = new ArrayList<>();

    protected boolean folded = false;
    protected boolean allIn = false;

    protected int currentBet = 0;

    private static final Scanner sc = new Scanner(System.in);

    public Player(String name, int chips) {
        this.name = name;
        this.chips = chips;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void clearHand() {
        hand.clear();
        folded = false;
        allIn = false;
        currentBet = 0;
    }

    public boolean isFolded() {
        return folded;
    }

    public void fold() {
        folded = true;
    }

    public boolean isAllIn() {
        return allIn;
    }

    public void setAllIn() {
        allIn = true;
    }

    public String getName() {
        return name;
    }

    public int getChips() {
        return chips;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void resetBet() {
        currentBet = 0;
    }

    public void bet(int amount) {

        if (amount >= chips) {
            amount = chips;
            allIn = true;
        }

        chips -= amount;
        currentBet += amount;
    }

    public void winChips(int amount) {
        chips += amount;
    }

    public String chooseAction(int callAmount, int pot) {

        System.out.println(
            "\n" + name +
            "'s turn | Pot: " + pot +
            " | Call: " + callAmount +
            " | Chips: " + chips
        );

        System.out.print("Action (fold/call/raise): ");

        String action = sc.nextLine().trim().toLowerCase();

        while (
            !action.equals("fold") &&
            !action.equals("call") &&
            !action.equals("raise")
        ) {
            System.out.print("Invalid. Choose fold/call/raise: ");
            action = sc.nextLine().trim().toLowerCase();
        }

        return action;
    }

    public int chooseRaiseAmount() {

        System.out.print("Enter raise amount (max " + chips + "): ");

        int amount = 0;

        try {
            amount = Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            amount = 0;
        }

        if (amount > chips)
            amount = chips;

        if (amount == chips)
            allIn = true;

        return amount;
    }
}
