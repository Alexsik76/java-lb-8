import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


public class ParallelMonteCarloPi {




        public static void getPiWithTime(String arg) throws InterruptedException {
            int numThreads = 1;
            long iterations = 100_000_000;
            try {
                numThreads = Integer.parseInt(arg.trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid argument");
            }
            Map<String, Double> result = new HashMap<>();
            long startTime = System.nanoTime();
            double piMonte = getPi(numThreads, iterations);
            long endTime = System.nanoTime();
            long timeExecution = endTime - startTime;
            result.put("piMonte", piMonte);
            result.put("timeExecution", timeExecution / 1e6);  // Store execution time in milliseconds for better readability

            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.ENGLISH);
            DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
            symbols.setGroupingSeparator(',');
            formatter.setDecimalFormatSymbols(symbols);
            System.out.println("Real PI is " + Math.PI);
            System.out.println("PI(Monte) is " + result.get("piMonte"));
            System.out.println("THREADS " + numThreads);
//            String string_iter = String.format("%0,9d", iterations);
            System.out.println("ITERATIONS " + formatter.format(iterations));
            System.out.println("TIME " + result.get("timeExecution") + "ms");
            System.out.println("-".repeat(30));

        }


        private static double getPi(int numThreads, long iterations) throws InterruptedException {
            ExecutorService executor;

            AtomicLong pointsInsideCircle = new AtomicLong(0);
            Random random = new Random();

            try {
                executor = Executors.newFixedThreadPool(numThreads);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid argument");
            }

            for (int i = 0; i < numThreads; i++) {
                executor.execute(() -> {
                    long localCount = 0;
                    for (long j = 0; j < iterations / numThreads; j++) {
                        double x = random.nextDouble();
                        double y = random.nextDouble();
                        if (Math.sqrt(x * x + y * y) <= 1) {
                            localCount++;
                        }
                    }
                    pointsInsideCircle.addAndGet(localCount);
                });
            }

                executor.shutdown();

            try {
                executor.awaitTermination(1, TimeUnit.HOURS);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
                throw new InterruptedException("InterruptedException");
            }
            return 4.0 * pointsInsideCircle.get() / iterations;

        }

        }



