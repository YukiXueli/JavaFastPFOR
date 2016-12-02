package me.lemire.integercompression;

import org.junit.Test;

import java.util.Arrays;
import me.lemire.integercompression.differential.Delta;

import static org.junit.Assert.assertEquals;


/**
 * Tests for class Delta.
 * 
 * @author Xue Li
 */
@SuppressWarnings({ "static-method" })
public class Xue_DeltaTest {

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
        int start = 3, length = 5, init = 8;

        for (int k = 0; k < 1024; ++k) data[k] = k;

        Delta.delta(data, start, length, init);

        for (int k = 0; k < 1024; ++k){
            if(k == start) assertEquals("deltaWithInitialTest", data[k], start - init);
            else if(k > start && k < start + length) assertEquals("deltaWithInitialTest", data[k], 1);
            else assertEquals("deltaWithInitialTest", data[k], k);
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
        int[] data = new int[1024]; //0 1 2 3 4 5 6 7 8 9 10 ... => 0 1 2 -5 1 1 1 1 8 9 10, return 7
        int start = 3, length = 5, init = 8;

        for (int k = 0; k < 1024; ++k) data[k] = k;

        int newInit = Delta.delta(data, start, length, init);
        Delta.fastinverseDelta(data, start, length, init);

        for (int k = 0; k < 1024; ++k){
            assertEquals("inverseDeltaTest", data[k], k);
        }
    }
}
