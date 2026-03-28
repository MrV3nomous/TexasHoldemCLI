package poker;

import java.util.List;
import java.util.ArrayList;

public class HandEvaluator {

    public static double evaluateHandStrength(List<Card> hand, List<Card> community){
        List<Card> all = new ArrayList<>(hand);
        if(community!=null) all.addAll(community);
        PokerHand ph = new PokerHand(all);
        // Simple numeric strength (for AI logic)
        return (ph.getRank().ordinal()+1)/10.0;
    }

    public static Player determineWinner(List<Player> players, List<Card> community){
        Player best=null;
        PokerHand bestHand=null;
        for(Player p: players){
            if(p.isFolded()) continue;
            List<Card> all = new ArrayList<>(p.getHand());
            all.addAll(community);
            PokerHand ph = new PokerHand(all);
            if(bestHand==null || ph.compareTo(bestHand)>0){
                bestHand=ph;
                best=p;
            }
        }
        return best;
    }

    public static String getHandType(Player p, List<Card> community){
        List<Card> all = new ArrayList<>(p.getHand());
        all.addAll(community);
        PokerHand ph = new PokerHand(all);
        return ph.getHandName();
    }



  public static double estimateDrawPotential(List<Card> hand, List<Card> community) {
    if (community == null || community.isEmpty()) return 0.0;

    List<Card> combined = new ArrayList<>(hand);
    combined.addAll(community);

    int hearts = 0, diamonds = 0, clubs = 0, spades = 0;
    for (Card c : combined) {
        switch (c.getSuit()) {
            case HEARTS -> hearts++;
            case DIAMONDS -> diamonds++;
            case CLUBS -> clubs++;
            case SPADES -> spades++;
        }
    }
    int maxSuit = Math.max(Math.max(hearts, diamonds), Math.max(clubs, spades));
    double flushPotential = (maxSuit >= 4) ? 0.1 : 0.0;

    boolean[] ranksPresent = new boolean[13];
    for (Card c : combined) ranksPresent[c.getRank().ordinal()] = true;

    int maxSequence = 0, currentSeq = 0;
    for (int i = 0; i < ranksPresent.length; i++) {
        if (ranksPresent[i]) currentSeq++;
        else currentSeq = 0;
        if (currentSeq > maxSequence) maxSequence = currentSeq;
    }

    double straightPotential = (maxSequence >= 4) ? 0.1 : 0.0;

    return flushPotential + straightPotential;
}


}
