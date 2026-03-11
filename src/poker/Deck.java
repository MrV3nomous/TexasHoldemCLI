package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards = new ArrayList<>();

    public Deck() { reset(); }

    public void reset() {
        cards.clear();
        for(Card.Suit suit : Card.Suit.values())
            for(Card.Rank rank : Card.Rank.values())
                cards.add(new Card(rank, suit));
        shuffle();
    }

    public void shuffle() { Collections.shuffle(cards); }

    public Card dealCard() {
        if(cards.isEmpty()) return null;
        return cards.remove(0);
    }
}
