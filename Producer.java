import java.util.ArrayList;

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
