package com.kamikaze.pfordelta;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

/**
 * Created by xueli on 12/8/16.
 */
public class PForDeltaUnpack128WithIntBufferTest {
    PForDeltaUnpack128WIthIntBuffer sm = new PForDeltaUnpack128WIthIntBuffer();

    byte[] buf = new byte[4096];
    ByteBuffer bufWrap = ByteBuffer.wrap(buf);

    IntBuffer ibuf = bufWrap.asIntBuffer();


    @Test
    public void test(){

        //int[] input = new int[1024];
        int[] output = new int[256];

        //for (int k = 0; k < 1024; ++k) input[k] = k;

        for(int i = 0; i < 29; i++){

            //int i = j << 28;

            sm.unpack(output, ibuf, i);
            

        }



    }
}
