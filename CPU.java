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
        if (this.cubbyHole.getTree().getNumberOfNodes() == 0) {
            return this.cubbyHole.getInitialQuantum();
        }

        return this.cubbyHole.getInitialQuantum()/this.cubbyHole.getTree().getNumberOfNodes();
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
