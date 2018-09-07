package io.github.monstruos.lab1;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("ALL")
public class CryptoLib {

    public static Integer powMod(Integer base, Integer exp, Integer mod) {
        int result = 1;

        for (Integer powBase = base; exp != 0; exp >>= 1) {
            int expBit = exp & 0x1;
            if (expBit == 1) {
                result *= (powBase % mod);
            }
            powBase = (powBase * powBase) % mod;
        }
        result %= mod;
        return result;
    }

    public static Integer[] eucleadean(Integer a, Integer b) {
        Integer[] u = {a, 1, 0};
        Integer[] v = {b, 0, 1};
        while (v[0] != 0) {
            Integer q = u[0] / v[0];
            Integer[] t = {u[0] % v[0], u[1] - q * v[1], u[2] - q * v[2]};
            u = v;
            v = t;
        }
        return u;
    }

    public static Integer[] diffieHellman(Integer g, Integer p,
                                          Integer a, Integer b) {
        Integer[] res = new Integer[2];
        if (g > p - 1) {
            res[0] = -1;
            res[1] = -1;
            return res;
        }
        Integer alice;
        Integer bob;
        if (g >= p) return res;
        alice = powMod(g, a, p);
        bob = powMod(g, b, p);

        res[0] = powMod(bob, a, p);
        res[1] = powMod(alice, b, p);
        return res;
    }

    public static List<Integer> babyGiantSteps(Integer a, Integer p,
                                                     Integer res, Integer m,
                                                     Integer k) {
        if (m * k < p)
            return new LinkedList<>();

        List<StepListElem> rows = new LinkedList<>();
        Integer mStepExp = 1;
        for (Integer i = 0; i < m; ++i) {
            Integer mRowValue = (mStepExp * res) % p;
            rows.add(new StepListElem(mRowValue, i, StepType.BABY));
            // after last iteration mStepExp got a^m step
            mStepExp = (mStepExp * (a % p)) % p;
        }
        Integer kStepExp = 1;
        for (Integer i = 1; i <= k; ++i) {
            kStepExp = (kStepExp * mStepExp) % p;
            rows.add(new StepListElem(kStepExp, i, StepType.GIANT));
        }
        rows.sort(Comparator.comparingInt(StepListElem::getElem));
        StepListElem t1;
        StepListElem t2;

        List<Integer> x = new LinkedList<>();
        // find i, j
        // there can be many variations!
        for (int i = 1; i < rows.size(); i++) {
            t1 = rows.get(i - 1);
            t2 = rows.get(i);
            if (t1.getElem().equals(t2.getElem()) &&
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
    private Integer elem;
    private Integer index;
    private StepType step;
    StepListElem(Integer elem, Integer index, StepType step) {
        this.elem = elem;
        this.index = index;
        this.step = step;
    }

    Integer getElem() {
        return elem;
    }

    Integer getIndex() {
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
