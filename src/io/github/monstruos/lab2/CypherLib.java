package io.github.monstruos.lab2;

import io.github.monstruos.lab1.CryptoLib;

public class CypherLib {
    public static long ShamirCyph(long m, long p, long ca,
                                  long da, long cb, long db) {
        if ((ca * da) % (p - 1) != 1)
            return -1;

        if ((cb * db) % (p - 1) != 1)
            return -1;

        long x1 = CryptoLib.powMod(m, ca, p);
        long x2 = CryptoLib.powMod(x1, cb, p);
        long x3 = CryptoLib.powMod(x2, da, p);

        return CryptoLib.powMod(x3, db, p);
    }

    public static void main(String[] args) {
        Integer m = 10;
        Integer p = 23;
        Integer ca = 7;
        Integer da = 19;
        Integer cb = 5;
        Integer db = 9;
        long shamir = ShamirCyph(m, p, ca, da, cb, db);
        System.out.println("Shamir(" + m + ", " + p + ") = " + shamir);
    }
}
