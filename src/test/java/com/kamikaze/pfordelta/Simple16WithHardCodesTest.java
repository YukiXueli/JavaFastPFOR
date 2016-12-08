package com.kamikaze.pfordelta;

import org.junit.Test;
import com.kamikaze.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by xueli on 12/8/16.
 */
public class Simple16WithHardCodesTest {
    Simple16WithHardCodes sm = new Simple16WithHardCodes();

    @Test
    public void test(){
        int[] input = new int[1024];
        int[] output = new int[1024];

        for (int k = 0; k < 1024; ++k) input[k] = k;
        int k = sm.s16Compress(output, 0, input, 0, 1024, 10, 10, input);
        assertEquals(k, 8);
    }
}
