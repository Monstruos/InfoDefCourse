package io.github.monstruos.lab1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("ALL")
public class CryptoLib {

    public static List<Long> eratosthenes(long n) {
        List<Long> primes = new ArrayList<>();
        if (n < 2) return primes;
        primes.add(2L);
        for (long num = 3; num <= n; num += 2) primes.add(num);

        for (int ind = 0; ind < primes.size(); ++ind) {
            long prime = primes.get(ind);
            for (long iter = prime * prime; iter <= n; iter += prime)
                primes.remove(Long.valueOf(iter));
        }

        return primes;
    }

    public static long powMod(long base, long exp, long mod) {
        long result = 1;

        for (long powBase = base; exp != 0; exp >>= 1) {
            long expBit = exp & 0x1;
            if (expBit == 1) {
                result *= (powBase % mod);
            }
            powBase = (powBase * powBase) % mod;
        }
        result %= mod;
        return result;
    }

    public static long[] eucleadean(long a, long b) {
        long[] u = {a, 1, 0};
        long[] v = {b, 0, 1};
        while (v[0] != 0) {
            long q = u[0] / v[0];
            long[] t = {u[0] % v[0], u[1] - q * v[1], u[2] - q * v[2]};
            u = v;
            v = t;
        }
        return u;
    }

    public static long[] diffieHellman(long a, long b, long n) {
        List<Long> primes = eratosthenes(n);
        long p = 0;
        long q = 0;
        for (int ind = primes.size() - 1; ind >= 0; --ind) {
            if (primes.contains((primes.get(ind) - 1) / 2)) {
                p = primes.get(ind);
                q = (p - 1) / 2;
                break;
            }
        }
        long g;
        for (g = 2; g < p - 1; ++g)
            if (powMod(g, q, p) != 1L) break;


        return diffieHellman(g, p, a, b);
    }

    public static long[] diffieHellman(long g, long p, long a, long b) {
        long[] res = new long[2];
        if (g > p - 1) {
            res[0] = -1;
            res[1] = -1;
            return res;
        }
        long alice;
        long bob;
        if (g >= p) return res;
        alice = powMod(g, a, p);
        bob = powMod(g, b, p);

        res[0] = powMod(bob, a, p);
        res[1] = powMod(alice, b, p);
        return res;
    }

    public static List<Long> babyGiantSteps(long a, long p, long res) {
        long m = (long)(Math.sqrt(p)) + 1;
        long k = m;
        return babyGiantSteps(a, p, res, m, k);
    }

    public static List<Long> babyGiantSteps(long a, long p,
                                               long res, long m,
                                               long k) {
        if (m * k < p)
            return new LinkedList<>();

        List<StepListElem> rows = new LinkedList<>();
        long mStepExp = 1;
        for (long i = 0; i < m; ++i) {
            long mRowValue = (mStepExp * res) % p;
            rows.add(new StepListElem(mRowValue, i, StepType.BABY));
            // after last iteration mStepExp got a^m step
            mStepExp = (mStepExp * (a % p)) % p;
        }
        long kStepExp = 1;
        for (long i = 1; i <= k; ++i) {
            kStepExp = (kStepExp * mStepExp) % p;
            rows.add(new StepListElem(kStepExp, i, StepType.GIANT));
        }
        rows.sort(Comparator.comparingLong(StepListElem::getElem));
        StepListElem t1;
        StepListElem t2;

        List<Long> x = new LinkedList<>();
        // find i, j
        // there can be many variations!
        for (int i = 1; i < rows.size(); i++) {
            t1 = rows.get(i - 1);
            t2 = rows.get(i);
            if ((t1.getElem() == t2.getElem()) &&
                    t1.getStep() != t2.getStep()) {
                boolean firstI = t1.getStep() == StepType.GIANT &&
                        t2.getStep() == StepType.BABY;
                x.add((firstI ? t1 : t2).getIndex() * m -
                        (firstI ? t2 : t1).getIndex());
            }
        }
        return x;
    }
}

class StepListElem {
    private long elem;
    private long index;
    private StepType step;
    StepListElem(long elem, long index, StepType step) {
        this.elem = elem;
        this.index = index;
        this.step = step;
    }

    long getElem() {
        return elem;
    }

    long getIndex() {
        return index;
    }

    StepType getStep() {
        return step;
    }
}

enum StepType {
    BABY,
    GIANT
}
