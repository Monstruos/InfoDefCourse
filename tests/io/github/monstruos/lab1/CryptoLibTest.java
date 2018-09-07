package io.github.monstruos.lab1;

import java.util.List;

import static org.junit.Assert.*;

public class CryptoLibTest {

    @org.junit.Test
    public void powMod() {
        //noinspection deprecation
        assertEquals(Integer.valueOf(4),
                        CryptoLib.powMod(3, 100, 7));
    }

    @org.junit.Test
    public void eucleadean() {
        assertArrayEquals(new Integer[]{1, -2, 3},
                CryptoLib.eucleadean(28, 19));
    }

    @org.junit.Test
    public void diffieHellman() {
        assertArrayEquals(new Integer[]{10, 10},
                CryptoLib.diffieHellman(5, 23, 7, 13));
    }

    @org.junit.Test
    public void babyGiantSteps() {
        List<Integer> res =
                CryptoLib.babyGiantSteps(2, 23, 9, 6, 4);
        //noinspection deprecation
        assertEquals(Integer.valueOf(16), res.get(0));
        //noinspection deprecation
        assertEquals(Integer.valueOf(5), res.get(1));
    }
}