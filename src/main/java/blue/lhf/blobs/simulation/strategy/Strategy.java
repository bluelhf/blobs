package blue.lhf.blobs.simulation.strategy;

import blue.lhf.blobs.simulation.SimState;
import blue.lhf.blobs.simulation.model.State;

@FunctionalInterface
public interface Strategy {
    Action act(State state);
}
