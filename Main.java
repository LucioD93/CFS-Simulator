import java.util.ArrayList;

public class Main {
    public synchronized static void main(String[] args) {
    	//Read cores
    	int cpu_nro;
    	if (args.length >= 1){
    		cpu_nro = Integer.valueOf(args[0]);
    	} else {
    		cpu_nro = 1;
    	}

    	//Read Quantum
        int quantum;
        if (args.length >= 2) {
    	    quantum = Integer.valueOf(args[1]);
        } else {
            quantum = 100;
        }
    	
        // Read data from xml file.
        ArrayList<Program> program_list;
        XMLParser xmlparser = new XMLParser();
        program_list = xmlparser.readXMLFile("process.xml");
        

        // Set critical region and producer consumer.
        CubbyHole cubbyHole = new CubbyHole(program_list, cpu_nro, quantum);
        Producer p1 = new Producer(cubbyHole, program_list);

        //Release the processes.
        p1.start();
        
        //Turn on the cores.
        for (int i=0; i < cpu_nro; i++){
            CPU cpu = new CPU(cubbyHole,i);
            cpu.start();
        }
    }
}
