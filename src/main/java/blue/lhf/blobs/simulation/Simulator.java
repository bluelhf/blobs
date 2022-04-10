package blue.lhf.blobs.simulation;

import blue.lhf.blobs.simulation.model.State;
import blue.lhf.blobs.simulation.strategy.*;

import java.util.DoubleSummaryStatistics;

import static blue.lhf.blobs.simulation.strategy.Action.GUESS_CHEATER;

public class Simulator {
    private final Strategy strategy;
    public Simulator(Strategy strategy) {
        this.strategy = strategy;
    }

    public record Results(double correct, double flipsPerBlob) { }

    public Results simulate(int runs) {
        var correctStats = new DoubleSummaryStatistics();
        var flipsStats = new DoubleSummaryStatistics();
        for (int run = 0; run < runs; run++) {
            SimState simState = new SimState();
            while (!simState.dead()) {
                int priorFlips = simState.flips();
                Action action = strategy.act(State.from(simState));
                if (action == Action.FLIP) {
                    simState.flip();
                } else {
                    correctStats.accept(simState.guess(action == GUESS_CHEATER) ? 1 : 0);
                }

                flipsStats.accept(simState.flips() - priorFlips);
            }
        }
        return new Results(correctStats.getAverage(), flipsStats.getAverage());
    }
}
