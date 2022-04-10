package blue.lhf.blobs.simulation.strategy;

import blue.lhf.blobs.simulation.model.State;
import blue.lhf.blobs.util.Maths;

import static blue.lhf.blobs.simulation.strategy.Action.*;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class ChanceStrategy implements Strategy {
    public static final double BEST_KNOWN_G = 2.69;
    private final double g;

    public ChanceStrategy() {
        this(BEST_KNOWN_G);
    }

    public ChanceStrategy(double gCoefficient) {
        this.g = gCoefficient;
    }

    @Override
    public Action act(State state) {
        int heads = state.heads(), tails = state.tails();
        double fair = Maths.chance(heads, tails, 1 / 2D);
        double cheater = Maths.chance(heads, tails, 3 / 4D);

        if (min(fair, cheater) * g > max(fair, cheater))
            return FLIP;

        return (fair > cheater) ? GUESS_FAIR : GUESS_CHEATER;
    }
}
