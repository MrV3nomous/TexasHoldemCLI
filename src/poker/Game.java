package poker;

import java.util.*;

public class Game {

    private List<Player> players = new ArrayList<>();
    private Deck deck = new Deck();
    private static List<Card> communityCards = new ArrayList<>();

    private int pot = 0;

    private Scanner sc = new Scanner(System.in);

    public void setupGame() {

        System.out.print("Enter number of players (2-6): ");
        int n = sc.nextInt();
        sc.nextLine();

        players.add(new Player("You", 1000));

        for (int i = 1; i < n; i++)
            players.add(new AIPlayer("AI " + i, 1000));
    }

    public void startGame() {

        boolean keepPlaying = true;

        while (keepPlaying) {

            playRound();

            System.out.print("Play another round? (y/n): ");
            keepPlaying = sc.nextLine().trim().equalsIgnoreCase("y");
        }

        System.out.println("Thanks for playing!");
    }

    private void playRound() {

        deck.reset();
        communityCards.clear();
        pot = 0;

        for (Player p : players)
            p.clearHand();

        // Deal hole cards
        for (int i = 0; i < 2; i++)
            for (Player p : players)
                p.addCard(deck.dealCard());

        TerminalRenderer.displayTable(players, communityCards, pot);

        bettingRound(true);
        if (onlyOnePlayerLeft()) return;

        // FLOP
        for (int i = 0; i < 3; i++)
            communityCards.add(deck.dealCard());

        TerminalRenderer.displayTable(players, communityCards, pot);

        bettingRound(false);
        if (onlyOnePlayerLeft()) return;

        // TURN
        communityCards.add(deck.dealCard());

        TerminalRenderer.displayTable(players, communityCards, pot);

        bettingRound(false);
        if (onlyOnePlayerLeft()) return;

        // RIVER
        communityCards.add(deck.dealCard());

        TerminalRenderer.displayTable(players, communityCards, pot);

        bettingRound(false);
        if (onlyOnePlayerLeft()) return;

        // SHOWDOWN
        TerminalRenderer.displayShowdown(players, communityCards, pot);

        Player winner = HandEvaluator.determineWinner(players, communityCards);

        if (winner != null) {
            System.out.println("\nWinner: " + winner.getName());
            winner.winChips(pot);
        }
    }

    // ---------- EARLY WIN CHECK ----------
    private boolean onlyOnePlayerLeft() {

        int active = 0;
        Player last = null;

        for (Player p : players) {

            if (!p.isFolded() && p.getChips() > 0) {
                active++;
                last = p;
            }
        }

        if (active == 1 && pot>0) {

            System.out.println("\nAll other players folded.");
            System.out.println(last.getName() + " wins the pot of " + pot);

            last.winChips(pot);
            pot = 0;

            return true;
        }

        return false;
    }

    // ---------- REAL POKER BETTING ----------
   private void bettingRound(boolean preFlop) {

    int currentBet = 0;
    int currentPlayer;
    int playersToAct;

    for (Player p : players)
        p.resetBet();

    // ---- PRE-FLOP ----
    if (preFlop) {

        int smallBlindAmount = 10;
        int bigBlindAmount = 20;

        Player smallBlind = players.get(1 % players.size());
        Player bigBlind = players.get(2 % players.size());

        int sb = Math.min(smallBlindAmount, smallBlind.getChips());
        int bb = Math.min(bigBlindAmount, bigBlind.getChips());

        smallBlind.bet(sb);
        bigBlind.bet(bb);

        pot += sb + bb;

        System.out.println(smallBlind.getName() + " posts small blind: " + sb);
        System.out.println(bigBlind.getName() + " posts big blind: " + bb);

        currentBet = bb;
        currentPlayer = 3 % players.size();

    } else {

        currentBet = 0;
        currentPlayer = 0;
    }

    playersToAct = players.size();

    while (playersToAct > 0) {

        Player p = players.get(currentPlayer);

        if (!p.isFolded() && !p.isAllIn()) {

            int callAmount = currentBet - p.getCurrentBet();

            String action = p.chooseAction(callAmount, pot);

            if (action.equals("fold")) {

                p.fold();
                System.out.println(p.getName() + " folds.");

            }

            else if (action.equals("call")) {

                int bet = Math.min(callAmount, p.getChips());

                p.bet(bet);
                pot += bet;

                if (callAmount == 0)
                    System.out.println(p.getName() + " checks.");
                else
                    System.out.println(p.getName() + " calls " + bet);

                playersToAct--;   // player responded

            }

            else if (action.equals("raise")) {

                int raise = p.chooseRaiseAmount();
                int total = callAmount + raise;

                p.bet(total);
                pot += total;

                currentBet = p.getCurrentBet();

                System.out.println(p.getName() + " raises to " + currentBet);

                playersToAct = players.size() - 1; // everyone must respond again
            }

            if (onlyOnePlayerLeft())
                return;

            TerminalRenderer.displayTable(players, Game.getCommunityCards(), pot);
        }

        currentPlayer = (currentPlayer + 1) % players.size();
    }
}






    public static List<Card> getCommunityCards() {
        return communityCards;
    }
}
