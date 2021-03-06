package burst.kit.test.crypto.plot;

import burst.kit.crypto.BurstCrypto;
import burst.kit.crypto.plot.impl.MiningPlot;
import burst.kit.util.LibShabal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class BurstCryptoPlottingBenchmark {
    private final BurstCrypto burstCrypto = BurstCrypto.getInstance();

    private void runBenchmark(int numberOfRepetitions) {
        byte[] buffer = new byte[numberOfRepetitions * MiningPlot.PLOT_SIZE];
        long start = System.currentTimeMillis();
        burstCrypto.plotNonces(123, 321, numberOfRepetitions, (byte) 2, buffer, 0);
        long duration = System.currentTimeMillis() - start;
        System.out.println("Time to plot " + numberOfRepetitions + " nonces: " + duration + "ms");
    }

    @Test
    public void benchmarkBulkPlot() {
        burstCrypto.setNativeEnabled(false);
        runBenchmark(64);
    }

    @Test
    public void benchmarkBulkPlot_native() {
        burstCrypto.setNativeEnabled(true);
        if (LibShabal.LOAD_ERROR != null) {
            System.out.println("LibShabal not loaded, can't benchmark native implementation");
            LibShabal.LOAD_ERROR.printStackTrace();
            return;
        }
        runBenchmark(64);
    }
}
