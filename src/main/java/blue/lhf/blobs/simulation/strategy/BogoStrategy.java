package blue.lhf.blobs.simulation.strategy;

import blue.lhf.blobs.simulation.model.State;

import static blue.lhf.blobs.simulation.strategy.Action.*;

public class BogoStrategy implements Strategy {
    @Override
    public Action act(State state) {
        double val = Math.random();
        if (val < (1 / 3D)) return GUESS_FAIR;
        if (val < (2 / 3D)) return FLIP;
        return GUESS_CHEATER;
    }
}
