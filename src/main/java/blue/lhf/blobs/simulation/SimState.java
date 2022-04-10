package blue.lhf.blobs.simulation;

import blue.lhf.blobs.simulation.model.*;

import java.util.Objects;

public final class SimState {
    private Blob blob;
    private int score;
    private int flips;


    public SimState(Blob blob, int score, int flips) {
        this.blob = blob;
        this.score = score;
        this.flips = flips;
    }

    public SimState() {
        this(new Blob(Math.random() < 0.5, new Tally()), 0, 100);
    }

    public Blob blob() {
        return blob;
    }

    public int score() {
        return score;
    }

    public boolean guess(boolean cheating) {
        boolean right = blob.check(cheating);
        flips += right ? 15 : -30;
        if (right) score++;

        newBlob();
        return right;
    }

    public void newBlob() {
        blob = new Blob(Math.random() < 0.5, new Tally());
    }

    public void flip() {
        blob.flip(); flips--;
    }

    public boolean dead() {
        return flips <= 0;
    }


    public int flips() {
        return flips;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SimState) obj;
        return Objects.equals(this.blob, that.blob) &&
            this.score == that.score &&
            this.flips == that.flips;
    }

    @Override
    public int hashCode() {
        return Objects.hash(blob, score, flips);
    }

    @Override
    public String toString() {
        return "State[" +
            "blob=" + blob + ", " +
            "score=" + score + ", " +
            "flips=" + flips + ']';
    }


    public Tally tally() {
        return blob.tally();
    }
}
