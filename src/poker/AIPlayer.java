package poker;


import java.util.List;

public class AIPlayer extends Player {

    private final double riskTolerance;

    public AIPlayer(String name, int chips) {
        super(name, chips);

        this.riskTolerance = 0.3 + Math.random() * 0.4;
    }

    @Override
    public String chooseAction(int callAmount, int pot) {

        List<Card> community = Game.getCommunityCards();
        double strength;

        if (community.isEmpty()) {

            strength = preFlopStrength(getHand());

            if (strength < 0.15 && Math.random() < 0.1 * (1 - riskTolerance)) {
                return "fold";
            }

            if (Math.random() < 0.8) {
                return "call";
            }

            if (strength > 0.5 && Math.random() < 0.2) {
                return "raise";
            }

            return "call";

        } else {

            strength =
                    HandEvaluator.evaluateHandStrength(getHand(), community)
                    + HandEvaluator.estimateDrawPotential(getHand(), community);

            double potPressure = callAmount / (double)(pot + 1);
            strength -= potPressure * 0.2;

            double foldThreshold = 0.25 * (1 - riskTolerance);
            double raiseThreshold = 0.65 * (1 + riskTolerance);

            if (strength >= raiseThreshold && getChips() > callAmount) {

                if (callAmount > getChips() * 0.25)
                    return "call";

                if (Math.random() < 0.35)
                    return "raise";
            }

            if (strength >= foldThreshold || callAmount == 0) {
                return "call";
            }

            return Math.random() < 0.1 ? "call" : "fold";
        }
    }

    @Override
    public int chooseRaiseAmount() {

        List<Card> community = Game.getCommunityCards();

        double strength =
                (community.isEmpty())
                        ? preFlopStrength(getHand())
                        : HandEvaluator.evaluateHandStrength(getHand(), community)
                        + HandEvaluator.estimateDrawPotential(getHand(), community);

        int minRaise = Math.max(10, (int)(strength * 0.15 * getChips()));
        int maxRaise = Math.max(minRaise, (int)(strength * 0.35 * getChips()));

        double personalityFactor = 0.7 + Math.random() * 0.6;

        int raise =
                (int)(minRaise
                        + Math.random() * (maxRaise - minRaise + 1)
                        * personalityFactor);

        return Math.max(10, Math.min(raise, getChips()));
    }

    private double preFlopStrength(List<Card> hand) {

        Card c1 = hand.get(0);
        Card c2 = hand.get(1);

        double score = 0.0;

        if (c1.getRank() == c2.getRank())
            score += 0.6;

        score += (c1.getRank().ordinal() + 1) / 14.0 * 0.2;
        score += (c2.getRank().ordinal() + 1) / 14.0 * 0.2;

        if (c1.getSuit() == c2.getSuit())
            score += 0.05;

        return Math.min(score, 1.0);
    }
}
