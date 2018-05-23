import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

class CubbyHole{
    private RedBlackTree<Program> tree = new RedBlackTree<>();

    private int quantum = 2;

    public synchronized void new_proccess(ArrayList<Program> program_list){
        Program program = program_list.remove(0);
        tree.insert(program);

        notifyAll();
    }

    public synchronized Program get_process(){

        while (this.tree.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Program program = tree.pop_most_left().get_key();

        if (program.isCompleted(quantum)){
            // Here we wait the remaining time for the process.
            try {
                sleep(quantum);
            } catch (InterruptedException e) { }
        } else {
            // If program does not finish. Add time and return to tree.
            program.addExecutedTime(quantum);

            // Here we wait quantum.
            try {
                sleep(quantum);
            } catch (InterruptedException e) { }

            tree.insert(program);
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


class CPU extends Thread {
    private CubbyHole cubbyHole;

    public CPU(CubbyHole cubbyHole){
        this.cubbyHole = cubbyHole;
    }

    public void run(){
        while(true){
            this.cubbyHole.get_process();

            try {
                sleep(100);
                System.out.print("hueheuheuheuehueheuheu\n");
            } catch (InterruptedException e) {
                System.out.print("not so much huehue\n");
            }
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

        CPU cpu1 = new CPU(cubbyHole);

        p1.run();
        cpu1.run();
    }
}
