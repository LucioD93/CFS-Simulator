class CPU extends Thread {
    private final CubbyHole cubbyHole;
    private int cpuId;
    private int pId;
    private int quantum;

    public CPU(CubbyHole cubbyHole, int cpuId){
        this.cubbyHole = cubbyHole;
        this.cpuId = cpuId;
        this.pId = 0;
        this.quantum = calculateQuantum();
    }

    public int calculateQuantum() {
    	int auxQuantum = this.cubbyHole.getInitialQuantum();
    	int auxNodes = this.cubbyHole.getTree().getNumberOfNodes();
    	
        if ( auxNodes == 0) {
        	this.cubbyHole.updateCpuQuantum(this.cpuId, true, auxNodes, auxQuantum);
            return auxQuantum;
        }
        this.cubbyHole.updateCpuQuantum(this.cpuId, false, auxNodes, auxQuantum);
        return auxQuantum/auxNodes;
    }

    public void run(){
        while(true){
            this.pId = this.cubbyHole.get_process(this.cpuId).getPid();
            this.quantum = this.calculateQuantum();
            this.cubbyHole.setQuantum(this.quantum);
            
            try {
                System.out.println("CPU " + this.cpuId + " Dormir por " + this.quantum + " (Programa " + this.pId + ")");
                sleep(5000);
            } catch (InterruptedException e) {
            }
        }
    }
}
