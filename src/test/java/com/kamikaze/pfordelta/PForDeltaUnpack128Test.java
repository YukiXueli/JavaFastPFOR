package com.kamikaze.pfordelta;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by xueli on 12/8/16.
 */
public class PForDeltaUnpack128Test {
    PForDeltaUnpack128 sm = new PForDeltaUnpack128();

    @Test
    public void test(){
        int[] input = new int[1024];
        int[] output = new int[1024];

        for (int k = 0; k < 1024; ++k) input[k] = k;

        for(int i = 0; i < 29; i++){

            //int i = j << 28;

            sm.unpack(output, input, i);
            

        }



    }
}
