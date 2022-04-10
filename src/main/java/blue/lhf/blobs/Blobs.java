package blue.lhf.blobs;

import blue.lhf.blobs.simulation.Simulator;
import blue.lhf.blobs.simulation.SimState;
import blue.lhf.blobs.simulation.strategy.*;

public class Blobs {

    public static Action guess(SimState simState) {
        double val = Math.random();
        if (val < (1 / 3D)) return Action.GUESS_FAIR;
        if (val < (2 / 3D)) return Action.FLIP;
        return Action.GUESS_CHEATER;
    }


    public static void main(String[] args) {
        double maxG = 0; var max = new Simulator.Results(0, -30);
        for (double g = 0; g < 3; g += 0.005) {
            Strategy strategy = new ChanceStrategy(g);
            var results = new Simulator(strategy).simulate(250);
            if (results.flipsPerBlob() > max.flipsPerBlob()) {
                max = results;
                maxG = g;
            }
        }
        System.out.println("Best G-value was " + maxG + ", results were " + max);
    }
}
