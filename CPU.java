class CPU extends Thread {
    private CubbyHole cubbyHole;

    public CPU(CubbyHole cubbyHole){
        this.cubbyHole = cubbyHole;
    }

    public int calculateQuantum() {
        if (this.cubbyHole.getTree().getNumberOfNodes() == 0) {
            return this.cubbyHole.getInitialQuantum();
        }
        return this.cubbyHole.getInitialQuantum()/this.cubbyHole.getTree().getNumberOfNodes();
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
