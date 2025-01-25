import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


public class ParallelMonteCarloPi {
    int numThreads;
    ParallelMonteCarloPi(String args) throws InterruptedException {
        if (Objects.equals(args, "")) {
            System.out.println("Usage: ParallelMonteCarloPi <num_threads>");
            return;
        }
        numThreads = Integer.parseInt(args.trim());

        long iterations = 100_000;
        AtomicLong pointsInsideCircle = new AtomicLong(0);
        Random random = new Random();

        long startTime = System.nanoTime();

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        for (int i = 0; i < numThreads; i++) {
            executor.execute(() -> {
                long localCount = 0;
                for (long j = 0; j < iterations / numThreads; j++) {
                    double x = random.nextDouble();
                    double y = random.nextDouble();
                    if (x * x + y * y <= 1) {
                        localCount++;
                    }
                }
                pointsInsideCircle.addAndGet(localCount);
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);

        long endTime = System.nanoTime();
        double pi = 4.0 * pointsInsideCircle.get() / iterations;

        System.out.println("Real PI is " + Math.PI);
        System.out.println("PI is " + pi);
        System.out.println("THREADS " + numThreads);
        System.out.println("ITERATIONS " + iterations);
        System.out.println("TIME " + (endTime - startTime) / 1e6 + "ms");
    }
}

