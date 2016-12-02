package me.lemire.integercompression; /**
 * This code is released under the
 * Apache License Version 2.0 http://www.apache.org/licenses/.
 *
 * (c) Daniel Lemire, http://lemire.me/en/
 */
//package me.lemire.integercompression.benchmarktools;
import me.lemire.integercompression.benchmarktools.*;
import me.lemire.integercompression.BitPacking;
import me.lemire.integercompression.differential.Delta;
import me.lemire.integercompression.differential.IntegratedBitPacking;

import org.junit.Test;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;

/**
 * Class used to benchmark the speed of bit packing. (For expert use.)
 * 
 * @author Xue Li
 * 
 */
public class Xue_BenchmarkBitPackingTesting {
        @Test
        public void test(){
                testCore(true);
                testCore(false);
        }
        private void testCore(boolean verbose) {
                DecimalFormat dfspeed = new DecimalFormat("0");
                final int N = 32;
                final int times = 100000;
                Random r = new Random(0);
                int[] data = new int[N];
                int[] compressed = new int[N];
                int[] uncompressed = new int[N];
                for (int bit = 0; bit < 32; ++bit) {
                        long comp = 0;
                        long compwm = 0;
                        long decomp = 0;
                        for (int t = 0; t < times; ++t) {
                                for (int k = 0; k < N; ++k) {
                                        data[k] = r.nextInt(1 << (bit == 31 ? 30 : bit));
                                }
                                long time1 = System.nanoTime();
                                BitPacking
                                        .fastpack(data, 0, compressed, 0, bit);
                                long time2 = System.nanoTime();
                                BitPacking.fastpackwithoutmask(data, 0,
                                        compressed, 0, bit);
                                long time3 = System.nanoTime();
                                BitPacking.fastunpack(compressed, 0,
                                        uncompressed, 0, bit);
                                long time4 = System.nanoTime();
                                comp += time2 - time1;
                                compwm += time3 - time2;
                                decomp += time4 - time3;
                        }
                        if (verbose)
                                System.out.println("bit = "
                                        + bit
                                        + " comp. speed = "
                                        + dfspeed.format(N * times * 1000.0
                                                / (comp))
                                        + " comp. speed wm = "
                                        + dfspeed.format(N * times * 1000.0
                                                / (compwm))
                                        + " decomp. speed = "
                                        + dfspeed.format(N * times * 1000.0
                                                / (decomp)));
                }
        }
        @Test
        public void testWithDeltas(){
                testWithDeltasCore(false);
                testWithDeltasCore(true);
        }
        private void testWithDeltasCore(boolean verbose) {
                DecimalFormat dfspeed = new DecimalFormat("0");
                final int N = 32;
                final int times = 100000;
                Random r = new Random(0);
                int[] data = new int[N];
                int[] compressed = new int[N];
                int[] icompressed = new int[N];
                int[] uncompressed = new int[N];
                for (int bit = 1; bit < 32; ++bit) {
                        long comp = 0;
                        long decomp = 0;
                        long icomp = 0;
                        long idecomp = 0;
                        for (int t = 0; t < times; ++t) {
                                data[0] = r.nextInt(1 << (bit == 31 ? 30 : bit));
                                for (int k = 1; k < N; ++k) {
                                        data[k] = r.nextInt(1 << (bit == 31 ? 30 : bit))
                                                + data[k - 1];
                                }
                                int[] tmpdata = Arrays
                                        .copyOf(data, data.length);
                                long time1 = System.nanoTime();
                                Delta.delta(tmpdata);
                                BitPacking.fastpackwithoutmask(tmpdata, 0,
                                        compressed, 0, bit);
                                long time2 = System.nanoTime();
                                BitPacking.fastunpack(compressed, 0,
                                        uncompressed, 0, bit);
                                Delta.fastinverseDelta(uncompressed);
                                long time3 = System.nanoTime();
                                if (!Arrays.equals(data, uncompressed))
                                        throw new RuntimeException("bug");
                                comp += time2 - time1;
                                decomp += time3 - time2;
                                tmpdata = Arrays.copyOf(data, data.length);
                                time1 = System.nanoTime();
                                if(bit == 26){
                                        int tttt = 0;
                                }
                                IntegratedBitPacking.integratedpack(0, tmpdata,
                                        0, icompressed, 0, bit);
                                time2 = System.nanoTime();
                                IntegratedBitPacking.integratedunpack(0,
                                        icompressed, 0, uncompressed, 0, bit);
                                time3 = System.nanoTime();
                                if (!Arrays.equals(icompressed, compressed))
                                        throw new RuntimeException("ibug "
                                                + bit);
                                if (!Arrays.equals(data, uncompressed))
                                        throw new RuntimeException("bug " + bit);
                                icomp += time2 - time1;
                                idecomp += time3 - time2;
                        }
                        if (verbose)
                                System.out.println("bit = "
                                        + bit
                                        + " comp. speed = "
                                        + dfspeed.format(N * times * 1000.0
                                                / (comp))
                                        + " decomp. speed = "
                                        + dfspeed.format(N * times * 1000.0
                                                / (decomp))
                                        + " icomp. speed = "
                                        + dfspeed.format(N * times * 1000.0
                                                / (icomp))
                                        + " idecomp. speed = "
                                        + dfspeed.format(N * times * 1000.0
                                                / (idecomp)));
                }
        }

        /**
         * Main method
         * 
         * @param args
         *                command-line arguments
         */
        /*
        public static void main(String[] args) {
                System.out.println("Testing packing and delta ");
                testWithDeltas(false);
                testWithDeltas(true);
                System.out.println("Testing packing alone ");
                test(false);
                test(true);
        }
        */

}
