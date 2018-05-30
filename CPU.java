class CPU extends Thread {
    private CubbyHole cubbyHole;
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
            this.pId = this.cubbyHole.get_process().getPid();
            this.quantum = this.calculateQuantum();

            try {
                System.out.println("CPU " + this.cpuId + " Dormir por " + this.quantum + " (Programa " + this.pId + ")");
                sleep(quantum);
            } catch (InterruptedException e) {
            }
        }
    }
}
