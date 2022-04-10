package blue.lhf.blobs;

public class Blob {
    private final boolean cheat;

    public Blob(boolean cheat) {
        this.cheat = cheat;
    }

    public void flip(Tally tally) {
        tally.flip(cheat ? 0.75 : 0.5);
    }

    public boolean check(boolean cheating) {
        return cheat == cheating;
    }
}
