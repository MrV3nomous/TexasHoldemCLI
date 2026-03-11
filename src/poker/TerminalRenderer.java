package poker;

import java.util.List;

public class TerminalRenderer {

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Regular table display
    public static void displayTable(List<Player> players, List<Card> community, int pot) {
        clearScreen();
        System.out.println("=== TEXAS HOLD'EM ===");
        System.out.println("Pot: " + pot);
        System.out.println("Community Cards: ");
        printAsciiCards(community);    System.out.println();

        for (Player p : players) {
            System.out.println(p.getName() + " [" + p.getChips() + " chips]");
            if (p instanceof AIPlayer) System.out.print("?? ??");
            else printAsciiCards(p.getHand());
            if (p.isFolded()) System.out.print("(Folded)");
            if (p.isAllIn()) System.out.print("(All-in)");
            System.out.println();
        }
        System.out.println("=====================");
    }

    // New method: ASCII showdown

public static void displayShowdown(List<Player> players, List<Card> community, int pot) {

    clearScreen();
    System.out.println("=== SHOWDOWN ===");
    System.out.println("Pot: " + pot);

    System.out.println("Community Cards:");
    if (community.isEmpty())
    	System.out.println("(not yet)");
    else
    	printAsciiCards(community);
    System.out.println();

    for(Player p : players){

        System.out.println(p.getName() + " [" + p.getChips() + " chips]" 
            + (p.isFolded() ? " (Folded)" : "") 
            + (p.isAllIn() ? " (All-in)" : ""));

        // reveal all cards now
        if(!p.isFolded()) {
	    printAsciiCards(p.getHand());
	}
        System.out.println();
    }

    System.out.println("=====================");
}

    // Helper to print multiple cards horizontally
    private static void printAsciiCards(List<Card> cards) {

    if(cards == null || cards.isEmpty()){
        System.out.println("(none yet)");
        return;
    }

    String[][] asciiCards = new String[cards.size()][];

    for(int i = 0; i < cards.size(); i++){
        asciiCards[i] = cards.get(i).ascii();
    }

    for(int line = 0; line < asciiCards[0].length; line++){
        for(int c = 0; c < asciiCards.length; c++){
            System.out.print(asciiCards[c][line] + " ");
        }
        System.out.println();
    }
}

}
