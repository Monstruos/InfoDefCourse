package io.github.monstruos.lab1;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CryptoLibTest {

    @org.junit.Test
    public void powMod() {
        //noinspection deprecation
        assertEquals(4,
                        CryptoLib.powMod(3, 100, 7));
    }

    @org.junit.Test
    public void eucleadean() {
        assertArrayEquals(new long[]{1, -2, 3},
                CryptoLib.eucleadean(28, 19));
    }

    @org.junit.Test
    public void diffieHellman() {
        assertArrayEquals(new long[]{10, 10},
                CryptoLib.diffieHellman(5, 23, 7, 13));
    }

    @org.junit.Test
    public void babyGiantSteps() {
        List<Long> res =
                CryptoLib.babyGiantSteps(2, 23, 9, 6, 4);
        Long[] expect = {16L, 5L};
        assertEquals(Arrays.asList(expect), res);
    }

    @Test
    public void eratosthenes() {
        List<Long> res = CryptoLib.eratosthenes(30L);
        Long[] arr = {2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L};
        assertEquals(Arrays.asList(arr), res);
    }

    @Test
    public void diffieHellman1() {
        assertArrayEquals(new long[]{10, 10},
                CryptoLib.diffieHellman(7, 13, 24));
    }

    @Test
    public void babyGiantSteps1() {
        List<Long> res =
                CryptoLib.babyGiantSteps(2, 23, 9);
        Long[] expect = {16L, 5L};
        assertEquals(Arrays.asList(expect), res);
    }
}