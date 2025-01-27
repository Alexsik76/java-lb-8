//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        int start = 5;
        int end = 40;


        if (args.length != 1) {
            System.out.printf("""
                    Usage: ParallelMonteCarloPi <num_threads>.
                    Without arguments, a loop of %d to %d threads will be launched
                    """, start, end);
            for (int i = start; i <= end; i+=5) {
                ParallelMonteCarloPi.getPiWithTime(Integer.toString(i));
            }
            return;
        }

        ParallelMonteCarloPi.getPiWithTime(args[0]);
    }

}