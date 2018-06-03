import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class GUI extends JFrame {
    String[] columnNames = {"PID", "Time", "Time in CPU"};
    String[] cpuColumnNames = {"Nro", "Quantum","Process"};

    JLabel quantum;
    JTable process_data;
    JTable cpu_data;

    DefaultTableModel dtm;
    DefaultTableModel dtm_cpu;

    public GUI(ArrayList<Program> program_list, int cpu_nro){
        super("Super Awesome CFS Simulator.");
        setLayout(new FlowLayout());

        this.quantum = new JLabel("El Quantum");
        add(this.quantum);

        // Loads program_list into table.
        this.process_data = new JTable(program_list.size()+2, 3);
        this.dtm = new DefaultTableModel(0, 0);

        this.dtm.setColumnIdentifiers(this.columnNames);
        this.process_data.setModel(this.dtm);

        for (Program p: program_list){
            this.dtm.addRow(p.toArray());
        }

        add(new JScrollPane(this.process_data));

        // Setup cpu panel.
        this.cpu_data = new JTable(cpu_nro, 3);
        this.dtm_cpu = new DefaultTableModel(0, 0);

        this.dtm_cpu.setColumnIdentifiers(this.cpuColumnNames);
        this.cpu_data.setModel(this.dtm_cpu);

        Object [] aux = {0, -1, -1};
        for (int i=0; i < cpu_nro; i++){
            aux[0]= i;
            this.dtm_cpu.addRow(aux);
        }

        add(new JScrollPane(this.cpu_data));
    }

    public DefaultTableModel getDtm() {
        return this.dtm;
    }

    public void setDtm(DefaultTableModel dtm) {
        this.dtm = dtm;
    }
    
    public void updateDtm(int process,int time){
    	this.dtm.setValueAt(time,process,2);
    }

    public DefaultTableModel getDtm_cpu() {
        return this.dtm_cpu;
    }

    public void setDtm_cpu(int cpu, int process) {
        Object [] aux = {cpu, process};
        this.dtm_cpu.addRow(aux);
    	
        add(new JScrollPane(this.cpu_data));
    }
    
    public void updateDtm_cpu(int cpu, int process){
    	this.dtm_cpu.setValueAt(process,cpu,2);
    }
    
    public void updateCpuQuantum(int cpu, int quantum) {
    	this.dtm_cpu.setValueAt(quantum,cpu,1);
    }
}
