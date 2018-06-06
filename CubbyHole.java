import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Thread.sleep;

class CubbyHole{
    private RedBlackTree<Program> tree = new RedBlackTree<>();

    private int initialQuantum;
    private int quantum = 2;
    private GUI gui;

    public CubbyHole(ArrayList<Program> program_list, int cpu_nro, int quantum){
        // Initialize GUI.
        this.gui = new GUI(program_list, cpu_nro, this);
        this.gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gui.setSize(1000,200);
        this.gui.setVisible(true);

        // Set quantum.
        this.initialQuantum = quantum;
    }

    public int getQuantum() {
        return this.quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public int getInitialQuantum() {
        return this.initialQuantum;
    }

    public RedBlackTree<Program> getTree() {
        return this.tree;
    }
    
    public void updateCpuQuantum(int cpu, boolean empty, int nodes, int quantum) {
    	if (empty) {
    		this.gui.updateCpuQuantum(cpu, quantum);
    	} else {
    	    this.gui.updateCpuQuantum(cpu, quantum/nodes);
    	}
    }

    public synchronized void new_proccess(ArrayList<Program> program_list){
        Program program = program_list.remove(0);
        this.tree.insert(program);

        notifyAll();
    }

    public synchronized Program get_process(int cpuId){

        while (this.tree.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Program program = this.tree.pop_most_left().get_key();

        Random rand = new Random();
        int aux = rand.nextInt(this.quantum+1)+1;
        int random = 0;

        if ((aux % 3 == 0) && (this.quantum - aux > 0)){
            random = aux;
        }

        // Update process in GUI.
        int auxPid = program.getPid();
        this.gui.updateDtm_cpu(cpuId, auxPid);

        if (program.isCompleted(this.quantum)){
            // Here we wait the remaining time for the process.
            try {
                sleep(this.quantum);
            } catch (InterruptedException e) { }
            
            //We update the table
            this.gui.updateDtm(auxPid, program.getTimeRemaining(), random);
            
        } else {
            // If program does not finish. We add the executed time and return it to the queue.

            // Here we wait quantum.
            try {
                sleep(this.quantum);
            } catch (InterruptedException e) { }
            
            //Add runtime of cpu.
            program.addExecutedTime(this.quantum, random);
            
            //Update Executed Time in table.
            this.gui.updateDtm(auxPid, program.getExecutedTime(), random);
            
            //Insert into the tree
            this.tree.insert(program);
        }

        return program;
    }
}
