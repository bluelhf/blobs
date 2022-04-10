package blue.lhf.blobs.util;

import java.util.function.IntToDoubleFunction;

public class Maths {
    public static double sum(int start, int end, IntToDoubleFunction func) {
        double sum = 0;
        for (int i = start; i <= end; i++) {
            sum += func.applyAsDouble(i);
        }
        return sum;
    }

    public static double chance(int heads, int tails, double p) {
        int n = heads + tails;
        if (heads == 0) return Math.pow(1 - p, n);

        return sum(heads, n, (k) ->
            binomialCoefficient(n, k) * Math.pow(p, k) * Math.pow(1 - p, tails));
    }

    public static long binomialCoefficient(long n, long k) {
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
}
