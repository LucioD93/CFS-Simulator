import javax.swing.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

class CubbyHole{
    private RedBlackTree<Program> tree = new RedBlackTree<>();

    private int initialQuantum = 2;
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

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public int getInitialQuantum() {
        return initialQuantum;
    }

    public RedBlackTree<Program> getTree() {
        return tree;
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
