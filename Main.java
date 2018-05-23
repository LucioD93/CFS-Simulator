import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

class CubbyHole{
    private RedBlackTree<Program> tree = new RedBlackTree<>();

    private int quantum = 2;
    private GUI gui;

    public CubbyHole(ArrayList<Program> program_list){
        // Initialize GUI.
        this.gui = new GUI(program_list, 1);
        this.gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gui.setSize(1000,200);
        this.gui.setVisible(true);
    }

    public CubbyHole(ArrayList<Program> program_list, int quantum){
        // Initialize GUI.
        this.gui = new GUI(program_list, 1);
        this.gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gui.setSize(1000,200);
        this.gui.setVisible(true);

        // Set quantum.
        this.quantum = quantum;
    }

    public synchronized void new_proccess(ArrayList<Program> program_list){
        Program program = program_list.remove(0);
        this.tree.insert(program);

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

        Program program = this.tree.pop_most_left().get_key();

        // Update process in GUI.
        this.gui.setDtm_cpu(1, program.getPid());

        if (program.isCompleted(this.quantum)){
            // Here we wait the remaining time for the process.
            try {
                sleep(this.quantum);
            } catch (InterruptedException e) { }
        } else {
            // If program does not finish. Add time and return to tree.
            program.addExecutedTime(this.quantum);

            // Here we wait quantum.
            try {
                sleep(this.quantum);
            } catch (InterruptedException e) { }

            this.tree.insert(program);
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
                sleep(200);
            } catch (InterruptedException e) {
            }
        }
    }
}


public class Main {
    public synchronized static void main(String[] args) {
        // Read data from xml file.
        ArrayList<Program> program_list;
        XMLParser xmlparser = new XMLParser();
        program_list = xmlparser.readXMLFile("process.xml");

        // Set critical region and producer consumer.
        CubbyHole cubbyHole = new CubbyHole(program_list);
        Producer p1 = new Producer(cubbyHole, program_list);
        CPU cpu1 = new CPU(cubbyHole);

        p1.run();
        cpu1.run();
    }
}
