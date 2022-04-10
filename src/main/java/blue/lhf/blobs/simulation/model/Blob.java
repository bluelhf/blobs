package blue.lhf.blobs.simulation.model;

public class Blob {
    private final boolean cheat;
    private final Tally tally;

    public Blob(boolean cheat, Tally tally) {
        this.cheat = cheat;
        this.tally = tally;
    }

    public Tally tally() {
        return tally;
    }

    public void flip() {
        tally.flip(cheat ? 0.75 : 0.5);
    }

    public boolean check(boolean cheating) {
        return cheat == cheating;
    }
}
