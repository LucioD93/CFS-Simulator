public class Program implements Comparable<Program> {
    private int pid;

    private int timeRemaining;

    public Program(int pid, int timeRemaining) {
        this.pid = pid;
        this.timeRemaining = timeRemaining;
    }

    @Override
    public int compareTo(Program program) {
        if (this.timeRemaining < program.timeRemaining) {
            return -1;
        } else if (this.timeRemaining == program.timeRemaining) {
            return 0;
        } else {
            return 1;
        }


    }
}
