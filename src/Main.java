//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        if (args.length != 1) {
            System.out.println("Usage: ParallelMonteCarloPi <num_threads>");
            return;
        }

        ParallelMonteCarloPi new_run = new ParallelMonteCarloPi(args[0]);
    }
}