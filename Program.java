import java.util.Random;

public class Program implements Comparable<Program> {

    private int pid;
    private int timeRemaining;
    private int timeExecuted;

    public Program(int pid, int timeRemaining) {
        this.pid = pid;
        this.timeRemaining = timeRemaining;
        this.timeExecuted = 0;
    }

    public int getPid() {
        return this.pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void addExecutedTime(int time) {
    	this.timeExecuted += time;
    }

    public int getExecutedTime() {
    	return this.timeExecuted;
    }
    
    public int getTimeRemaining() {
    	return this.timeRemaining;
    }
    
    public boolean isCompleted(int quantum) {
        return this.timeExecuted + quantum >= this.timeRemaining;
    }

    public Object[] toArray(){
        return new Object[] {this.pid, this.timeRemaining, this.timeExecuted};
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
