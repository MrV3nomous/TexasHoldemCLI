package poker;

import java.util.*;

public class PokerHand {
    private List<Card> cards;

    public enum HandRank {
        HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND,
        STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND,
        STRAIGHT_FLUSH, ROYAL_FLUSH
    }

    private HandRank rank;
    private List<Integer> rankValues;

    public PokerHand(List<Card> sevenCards) {
        this.cards = sevenCards;
        evaluate();
    }

    public HandRank getRank() { return rank; }
    public List<Integer> getRankValues() { return rankValues; }

    private void evaluate() {

        cards.sort((a,b) -> b.getRank().ordinal() - a.getRank().ordinal());

        Map<Card.Suit, List<Card>> suits = new HashMap<>();
        Map<Integer, Integer> ranks = new HashMap<>();
        for(Card c : cards){
            suits.computeIfAbsent(c.getSuit(), k->new ArrayList<>()).add(c);
            ranks.put(c.getRank().ordinal(), ranks.getOrDefault(c.getRank().ordinal(),0)+1);
        }

        List<Card> flushCards = null;
        for(List<Card> suitCards : suits.values()){
            if(suitCards.size() >=5) { flushCards = suitCards; break; }
        }

        List<Integer> uniqueRanks = new ArrayList<>(ranks.keySet());
        Collections.sort(uniqueRanks, Collections.reverseOrder());
        int consecutive=1, straightHigh=-1;
        for(int i=0;i<uniqueRanks.size()-1;i++){
            if(uniqueRanks.get(i)-1 == uniqueRanks.get(i+1)) consecutive++;
            else consecutive=1;
            if(consecutive>=5) straightHigh=uniqueRanks.get(i-3);
        }

        boolean isStraight = straightHigh!=-1;
        boolean isFlush = flushCards!=null;

        if(isStraight && isFlush){
            List<Integer> flushRanks = new ArrayList<>();
            for(Card c: flushCards) flushRanks.add(c.getRank().ordinal());
            Collections.sort(flushRanks, Collections.reverseOrder());
            consecutive=1;
            for(int i=0;i<flushRanks.size()-1;i++){
                if(flushRanks.get(i)-1 == flushRanks.get(i+1)) consecutive++;
                else consecutive=1;
                if(consecutive>=5){
                    if(flushRanks.get(i-3)==12) { rank=HandRank.ROYAL_FLUSH; rankValues=List.of(12); return; }
                    else { rank=HandRank.STRAIGHT_FLUSH; rankValues=List.of(flushRanks.get(i-3)); return; }
                }
            }
        }

        int four=-1, three=-1;
        List<Integer> pairs = new ArrayList<>();
        for(Map.Entry<Integer,Integer> e:ranks.entrySet()){
            if(e.getValue()==4) four=e.getKey();
            else if(e.getValue()==3) three=e.getKey();
            else if(e.getValue()==2) pairs.add(e.getKey());
        }
        Collections.sort(pairs, Collections.reverseOrder());

        if(four!=-1){ rank=HandRank.FOUR_OF_A_KIND; rankValues=List.of(four); return; }
        if(three!=-1 && pairs.size()>=1){ rank=HandRank.FULL_HOUSE; rankValues=List.of(three,pairs.get(0)); return; }
        if(isFlush){ rank=HandRank.FLUSH; rankValues=getTopNCards(flushCards,5); return; }
        if(isStraight){ rank=HandRank.STRAIGHT; rankValues=List.of(straightHigh); return; }
        if(three!=-1){ rank=HandRank.THREE_OF_A_KIND; rankValues=List.of(three); return; }
        if(pairs.size()>=2){ rank=HandRank.TWO_PAIR; rankValues=List.of(pairs.get(0),pairs.get(1)); return; }
        if(pairs.size()==1){ rank=HandRank.PAIR; rankValues=List.of(pairs.get(0)); return; }

        rank=HandRank.HIGH_CARD; rankValues=List.of(cards.get(0).getRank().ordinal());
    }

    private List<Integer> getTopNCards(List<Card> c, int n){
        c.sort((a,b)->b.getRank().ordinal()-a.getRank().ordinal());
        List<Integer> top = new ArrayList<>();
        for(int i=0;i<n && i<c.size();i++) top.add(c.get(i).getRank().ordinal());
        return top;
    }

    public int compareTo(PokerHand other){
        if(this.rank.ordinal()!=other.rank.ordinal()) return this.rank.ordinal()-other.rank.ordinal();
        for(int i=0;i<Math.min(this.rankValues.size(),other.rankValues.size());i++){
            if(!this.rankValues.get(i).equals(other.rankValues.get(i)))
                return this.rankValues.get(i)-other.rankValues.get(i);
        }
        return 0;
    }

    public String getHandName(){
        switch(rank){
            case HIGH_CARD: return "High Card";
            case PAIR: return "Pair";
            case TWO_PAIR: return "Two Pair";
            case THREE_OF_A_KIND: return "Three of a Kind";
            case STRAIGHT: return "Straight";
            case FLUSH: return "Flush";
            case FULL_HOUSE: return "Full House";
            case FOUR_OF_A_KIND: return "Four of a Kind";
            case STRAIGHT_FLUSH: return "Straight Flush";
            case ROYAL_FLUSH: return "Royal Flush";
            default: return "Unknown";
        }
    }
}
