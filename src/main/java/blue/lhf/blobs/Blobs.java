package blue.lhf.blobs;

import org.apache.commons.math3.util.*;

import java.util.function.*;

import static java.lang.Math.*;
import static org.apache.commons.math3.util.CombinatoricsUtils.binomialCoefficient;
import static org.apache.commons.math3.util.CombinatoricsUtils.stirlingS2;

public class Blobs {

    public static Action guess(State state) {
        double val = Math.random();
        if (val < (1 / 3D)) return Action.GUESS_FAIR;
        if (val < (2 / 3D)) return Action.FLIP;
        return Action.GUESS_CHEATER;
    }


    public static void main(String[] args) {
        double maxG = 0; var max = new Simulator.Results(0, -30);
        for (double g = 0; g < 3; g += 0.01) {
            final double finalG = g;
            var results = new Simulator((state) -> Blobs.chance(state, finalG)).simulate(300);
            if (results.flipsPerBlob() > max.flipsPerBlob()) {
                max = results;
                maxG = g;
            }
        }
        System.out.println("Best G-value was " + maxG + ", results were " + max);
    }

    private static double sum(int start, int end, IntToDoubleFunction func) {
        double sum = 0;
        for (int i = start; i <= end; i++) {
            sum += func.applyAsDouble(i);
        }
        return sum;
    }

    private static double chance(int heads, int thr, double p) {
        int n = thr, x = heads;
        if (x == 0) return Math.pow(1 - p, n);
        return sum(x, n, (k) -> choose(n, k) * Math.pow(p, k) * Math.pow(1 - p, n - x));
    }


    private static long choose(long n, long k) {
        if (n == k) return 1;
        long numerator = 1;
        long denominator = 1;

        for (long i = n; i >= (n - k + 1); i--) {
            numerator *= i;
        }

        for (long i = k; i >= 1; i--) {
            denominator *= i;
        }
        return (numerator / denominator);
    }

    private static Action chance(State state, double g) {
        int a = state.tally().heads(), b = state.tally().tails();
        double fair = chance(a, a + b, 1/2D) * 100;
        double cheater = chance(a, a + b, 3/4D) * 100;
        return min(fair, cheater) * g > max(fair, cheater)
            ? Action.FLIP
            : (fair > cheater
            ? Action.GUESS_FAIR
            : Action.GUESS_CHEATER);
    }
}
