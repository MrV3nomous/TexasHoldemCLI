package poker;

public class Card {
    public enum Suit {HEARTS, DIAMONDS, CLUBS, SPADES}
    public enum Rank {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN,
        JACK, QUEEN, KING, ACE
    }

    private final Suit suit;
    private final Rank rank;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Suit getSuit() { return suit; }
    public Rank getRank() { return rank; }

    @Override
    public String toString() {
        String s = switch(suit) {
            case HEARTS -> "♥"; case DIAMONDS -> "♦";
            case CLUBS -> "♣"; case SPADES -> "♠";
        };
        String r = switch(rank) {
            case TWO -> "2"; case THREE -> "3"; case FOUR -> "4";
            case FIVE -> "5"; case SIX -> "6"; case SEVEN -> "7";
            case EIGHT -> "8"; case NINE -> "9"; case TEN -> "10";
            case JACK -> "J"; case QUEEN -> "Q"; case KING -> "K"; case ACE -> "A";
        };
        return r + s;
    }

    public String[] ascii() {
        String r = switch(rank) {
            case TEN -> "10"; case JACK -> "J "; case QUEEN -> "Q "; case KING -> "K "; case ACE -> "A ";
            default -> rank.ordinal()+2 + " ";
        };
        String s = switch(suit) {
            case HEARTS -> "♥"; case DIAMONDS -> "♦"; case CLUBS -> "♣"; case SPADES -> "♠";
        };
        return new String[]{
            "┌─────┐",
            "│" + r + "   │",
            "│  " + s + "  │",
            "│   " + r + "│",
            "└─────┘"
        };
    }
}
