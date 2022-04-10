package blue.lhf.blobs.simulation.model;

import blue.lhf.blobs.simulation.SimState;

import java.util.Objects;

public final class State {
    private int heads;
    private int tails;
    private int score;
    private int flips;

    public State(int heads, int tails, int score, int flips) {
        this.heads = heads;
        this.tails = tails;
        this.score = score;
        this.flips = flips;
    }

    public static State from(SimState simState) {
        return new State(simState.tally().heads(), simState.tally().tails(), simState.score(), simState.flips());
    }

    public void setHeads(int heads) {
        this.heads = heads;
    }

    public void setTails(int tails) {
        this.tails = tails;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setFlips(int flips) {
        this.flips = flips;
    }

    public int heads() {
        return heads;
    }

    public int tails() {
        return tails;
    }

    public int score() {
        return score;
    }

    public int flips() {
        return flips;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (State) obj;
        return this.heads == that.heads &&
            this.tails == that.tails &&
            this.score == that.score &&
            this.flips == that.flips;
    }

    @Override
    public int hashCode() {
        return Objects.hash(heads, tails, score, flips);
    }

    @Override
    public String toString() {
        return "State[" +
            "heads=" + heads + ", " +
            "tails=" + tails + ", " +
            "score=" + score + ", " +
            "flips=" + flips + ']';
    }

}
