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

        assertEquals(sm.s16CompressBackup(output, 0, input, 0, 1024, 10, 10, input), 8);

        assertEquals(sm.s16Decompress(output, 0, input, 0, 1024), 28);

        assertEquals(sm.s16DecompressWithIntBufferBackup(output, 0, 22, 1024), 28);

        assertEquals(sm.s16DecompressWithIntBuffer(output, 0, 22, 1024), 28);

        assertEquals(sm.s16DecompressWithIntBufferWithHardCodes(output, 0, 22, 1024), 28);

        assertEquals(sm.s16DecompressWithIntBufferIntegrated(output, 0, 22, 1024, input, 8), 28);

        assertEquals(sm.s16DecompressWithIntBufferIntegrated2(output, 0, 22, 1024, input, 8), 28);

        assertEquals(sm.s16DecompressWithIntBufferIntegratedBackup(output, 0, 22, 1024, input, 8), 28);

        assertEquals(sm.s16DecompressOneNumberWithHardCodes(output, 0, 22, 0), 28);

        assertEquals(sm.s16DecompressOneNumberWithHardCodesIntegrated(output, 0, 22, 0, 8, input), 28);

    }
}
