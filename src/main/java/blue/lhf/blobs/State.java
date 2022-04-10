package blue.lhf.blobs;

import java.util.Objects;

public final class State {
    private Blob blob;
    private Tally tally;
    private int score;
    private int flips;


    public State(Blob blob, Tally tally, int score, int flips) {
        this.blob = blob;
        this.tally = tally;
        this.score = score;
        this.flips = flips;
    }

    public State() {
        this(new Blob(Math.random() < 0.5), new Tally(0, 0), 0, 100);
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
        blob = new Blob(Math.random() < 0.5);
        tally = new Tally(0, 0);
        if (right) score++;
        return right;
    }

    public void flip() {
        blob.flip(tally); flips--;
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
        var that = (State) obj;
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
        return tally;
    }
}
