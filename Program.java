public class Program implements Comparable<Program> {

    private int pid;
    private int timeRemaining;
    private int timeOfArrival;
    private int timeExecuted;

    public Program() {
        this.pid = 0;
        this.timeRemaining = 0;
        this.timeOfArrival = 0;
        this.timeExecuted = 0;
    }

    public Program(int pid, int timeRemaining, int timeOfArrival) {
        this.pid = pid;
        this.timeRemaining = timeRemaining;
        this.timeOfArrival = timeOfArrival;
        this.timeExecuted = 0;
    }

    public void addExecutedTime(int time) {
        this.timeExecuted += time;
    }

    public boolean isCompleted(int quantum) {
        return this.timeExecuted + quantum >= this.timeRemaining;
    }

    @Override
    public int compareTo(Program program) {
        if (this.timeExecuted < program.timeExecuted) {
            return -1;
        } else if (this.timeExecuted == program.timeExecuted) {
            return 0;
        } else {
            return 1;
        }
    }
}
