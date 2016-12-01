package me.lemire.integercompression;

import org.junit.Test;

import java.util.Arrays;


/**
 * Just some basic sanity tests.
 * 
 * @author Daniel Lemire
 */
@SuppressWarnings({ "static-method" })
public class XueAddBasicTest {
    SkippableIntegerCODEC[] codecs = {
            new GroupSimple9()
    };

    
    /**
     * 
     */
    @Test
    public void consistentTest() {
        int bitLength[] = { 1, 2, 3, 4, 5, 7, 9, 14, 28 };
        int codeNum[] = { 28, 14, 9, 7, 5, 4, 3, 2, 1 };
        for(int s1 = 0 ; s1 < 9; ++s1){
            for(int s2 = 0; s2 < 9; ++s2){
                int max1 = 1 << bitLength[s1];
                int max2 = 1 << bitLength[s2];
                int num1 = codeNum[s1];
                int num2 = codeNum[s2];

                int N = num1 + num2;
                int[] data = new int[N];

                int i = 0;
                for(; i < num1; ++i){
                    data[i] = max1 - 1;
                }
                for(; i < num1 + num2; ++i){
                    data[i] = max2 - 1;
                }

                oneTest(N, data);

            }
        }


    }
    private void oneTest(int N, int[] data){
        int[] rev = new int[N];

        //for (int k = 0; k < N; ++k)
        //    data[k] = k % 128;
        for (SkippableIntegerCODEC c : codecs) {
            System.out.println("[SkippeableBasicTest.consistentTest] codec = "
                    + c);
            int[] outBuf = new int[N + 1024];
            for (int n = 0; n <= N; ++n) {
                IntWrapper inPos = new IntWrapper();
                IntWrapper outPos = new IntWrapper();
                c.headlessCompress(data, inPos, n, outBuf, outPos);

                IntWrapper inPoso = new IntWrapper();
                IntWrapper outPoso = new IntWrapper();
                c.headlessUncompress(outBuf, inPoso, outPos.get(), rev,
                        outPoso, n);
                if (outPoso.get() != n) {
                    throw new RuntimeException("bug "+n);
                }
                if (inPoso.get() != outPos.get()) {
                    throw new RuntimeException("bug "+n+" "+inPoso.get()+" "+outPos.get());
                }
                for (int j = 0; j < n; ++j)
                    if (data[j] != rev[j]) {
                        throw new RuntimeException("bug");
                    }
            }
        }
    }
    
    /**
     * 
     */
    @Test
    public void varyingLengthTest() {
        int N = 4096;
        int[] data = new int[N];
        for (int k = 0; k < N; ++k)
            data[k] = k;
        for (SkippableIntegerCODEC c : codecs) {
            System.out.println("[SkippeableBasicTest.varyingLengthTest] codec = "+c);
            for (int L = 1; L <= 128; L++) {
                int[] comp = TestUtils.compressHeadless(c, Arrays.copyOf(data, L));
                int[] answer = TestUtils.uncompressHeadless(c, comp, L);
                for (int k = 0; k < L; ++k)
                    if (answer[k] != data[k])
                        throw new RuntimeException("bug "+c.toString()+" "+k+" "+answer[k]+" "+data[k]);
            }
            for (int L = 128; L <= N; L *= 2) {
                int[] comp = TestUtils.compressHeadless(c, Arrays.copyOf(data, L));
                int[] answer = TestUtils.uncompressHeadless(c, comp, L);
                for (int k = 0; k < L; ++k)
                    if (answer[k] != data[k])
                        throw new RuntimeException("bug");
            }

        }
    }

    /**
     * 
     */
    /*
    @Test

    public void varyingLengthTest2() {
        int N = 128;
        int[] data = new int[N];
        data[127] = -1;
        for (SkippableIntegerCODEC c : codecs) {
            System.out.println("[SkippeableBasicTest.varyingLengthTest2] codec = "+c);

            try {
                // CODEC Simple9 is limited to "small" integers.
                if (c.getClass().equals(
                        Class.forName("me.lemire.integercompression.Simple9")))
                    continue;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                // CODEC Simple16 is limited to "small" integers.
                if (c.getClass().equals(
                        Class.forName("me.lemire.integercompression.Simple16")))
                    continue;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            for (int L = 1; L <= 128; L++) {
                int[] comp = TestUtils.compressHeadless(c, Arrays.copyOf(data, L));
                int[] answer = TestUtils.uncompressHeadless(c, comp, L);
                for (int k = 0; k < L; ++k)
                    if (answer[k] != data[k])
                        throw new RuntimeException("bug at k = "+k+" "+answer[k]+" "+data[k]+" for "+c.toString());
            }
            for (int L = 128; L <= N; L *= 2) {
                int[] comp = TestUtils.compressHeadless(c, Arrays.copyOf(data, L));
                int[] answer = TestUtils.uncompressHeadless(c, comp, L);
                for (int k = 0; k < L; ++k)
                    if (answer[k] != data[k])
                        throw new RuntimeException("bug");
            }

        }
    }

    */
}
