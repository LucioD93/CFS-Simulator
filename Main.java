import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

class CubbyHole{
    private RedBlackTree<Program> tree = new RedBlackTree<>();

    private int quantum = 0;

    public void new_proccess(ArrayList<Program> program_list){
        Program program = program_list.remove(0);
        tree.insert(program);
    }

    public Program get_process(){
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


class Producer extends Thread {
    private CubbyHole cubbyHole;
    private ArrayList<Program> program_list;

    public Producer(CubbyHole c, ArrayList<Program> program_list){
        this.cubbyHole = c;
        this.program_list = program_list;
    }

    public void run(){
        while (!program_list.isEmpty()){
            this.cubbyHole.new_proccess(program_list);
        }
    }
}


public class Main {
    public synchronized static void main(String[] args) {
        ArrayList<Program> program_list = new ArrayList<Program>();

        CubbyHole cubbyHole = new CubbyHole();

        XMLParser xmlparser = new XMLParser();

        program_list = xmlparser.readXMLFile("process.xml");

        Producer p1 = new Producer(cubbyHole, program_list);

        p1.run();
    }
}
