package blue.lhf.blobs.simulation.model;

import java.util.Objects;

public final class Tally {
    private int heads;
    private int tails;

    public Tally(int heads, int tails) {
        this.heads = heads;
        this.tails = tails;
    }

    public Tally() {
        this(0, 0);
    }

    public void flip(double headsProbability) {
        if (Math.random() < headsProbability) {
            heads++;
        } else {
            tails++;
        }
    }

    public int heads() {
        return heads;
    }

    public int tails() {
        return tails;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Tally) obj;
        return this.heads == that.heads &&
            this.tails == that.tails;
    }

    @Override
    public int hashCode() {
        return Objects.hash(heads, tails);
    }

    @Override
    public String toString() {
        return "%d heads, %d tails".formatted(heads, tails);
    }

}
