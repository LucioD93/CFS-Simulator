import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

class CubbyHole{
    private RedBlackTree<Program> tree = new RedBlackTree<>();

    private int quantum = 0;

    public void new_proccess(int who, ArrayList<Program> program_list){
        Program program = program_list.remove(0);
        tree.insert(program);
    }

    public Program get_process(int who){
        Program program = tree.pop_most_left().get_key();

        if (program.isCompleted(quantum)){
            // Here we wait the remaining time for the process.
            try {
                sleep(quantum);
            } catch (InterruptedException e) { }
        } else {
            program.addExecutedTime(quantum);
            tree.insert(program);

            // Here we wait quantum.
            try {
                sleep(quantum);
            } catch (InterruptedException e) { }
        }

        return program;
    }
}


public class Main {
    public synchronized static void main(String[] args) {
        ArrayList<Program> program_list = new ArrayList<>();

        XMLParser xmlparser = new XMLParser(args[1]);

        program_list = xmlparser.readFile();

    }
}
