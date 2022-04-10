package blue.lhf.blobs;

import java.util.DoubleSummaryStatistics;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Function;

import static blue.lhf.blobs.Action.GUESS_CHEATER;
import static blue.lhf.blobs.Action.GUESS_FAIR;

public class Simulator {
    private final Function<State, Action> algorithm;
    public Simulator(Function<State, Action> algorithm) {
        this.algorithm = algorithm;
    }

    public record Results(double correct, double flipsPerBlob) { }

    public Results simulate(int runs) {
        var correctStats = new DoubleSummaryStatistics();
        var flipsStats = new DoubleSummaryStatistics();
        for (int run = 0; run < runs; run++) {
            State state = new State();
            while (!state.dead()) {
                int priorFlips = state.flips();
                Action action = algorithm.apply(state);
                switch (action) {
                    case FLIP -> state.flip();
                    default -> {
                        correctStats.accept(state.guess(action == GUESS_CHEATER) ? 1 : 0);
                    }
                }
                flipsStats.accept(state.flips() - priorFlips);
            }
        }
        return new Results(correctStats.getAverage(), flipsStats.getAverage());
    }
}
