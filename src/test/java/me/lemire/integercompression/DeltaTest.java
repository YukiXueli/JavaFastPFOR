package me.lemire.integercompression;

import me.lemire.integercompression.differential.Delta;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Tests for class Delta.
 * 
 * @author Xue Li
 */
@SuppressWarnings({ "static-method" })
public class DeltaTest {

    /**
     * 
     */
    @Test
    public void simpleDeltaTest() {

        int[] data = new int[1024];

        for (int k = 0; k < 1024; ++k) data[k] = k;

        Delta.delta(data);

        for (int k = 1; k < 1024; ++k){
            assertEquals("simple delta", data[k], 1);
        }
    }

    /**
     * 
     */
    @Test
    public void deltaWithInitialTest() {
        int[] data = new int[1024]; //0 1 2 3 4 5 6 7 8 9 10 ... => 0 1 2 -5 1 1 1 1 8 9 10, return 7

        int start = 3, length = 5, init;
        int[] out = new int[length];

        for (int k = 0; k < 1024; ++k) data[k] = k;
        init = data[start - 1];

        Delta.delta(data, start, length, init, out);

        for (int k = 0; k < length; ++k){
            assertEquals("deltaWithInitialTest", out[k], 1);
        }
    }

    @Test
    public void inverseDeltaTest(){
        int[] data = new int[1024];

        for (int k = 0; k < 1024; ++k) data[k] = k;

        Delta.delta(data);
        Delta.inverseDelta(data);

        for (int k = 0; k < 1024; ++k){
            assertEquals("inverseDeltaTest", data[k], k);
        }
    }

    @Test
    public void fastinverseDeltaTest(){
        int[] data = new int[1024];

        for (int k = 0; k < 1024; ++k) data[k] = k;

        Delta.delta(data);
        Delta.fastinverseDelta(data);

        for (int k = 0; k < 1024; ++k){
            assertEquals("inverseDeltaTest", data[k], k);
        }
    }

    @Test
    public void fastinverseDeltaWithInitialTest(){
        int[] data = {0, 1, 2, 1, 1, 1, 1, 1, 8, 9}; //0 1 2 3 4 5 6 7 8 9 10 ... => 0 1 2 -5 1 1 1 1 8 9 10, return 7
        int start = 3, length = 5, init = 2;

        Delta.fastinverseDelta(data, start, length, init);

        for (int k = 0; k < length; ++k){
            assertEquals("fastinverseDeltaWithInitialTest", data[k], k);
        }
    }
}
