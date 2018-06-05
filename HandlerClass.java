import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.security.Guard;
import java.util.ArrayList;
import java.util.Random;

public class HandlerClass  implements ActionListener {
    private GUI gui;
    ArrayList<Program> programs;
    CubbyHole cubbyHole;

    public HandlerClass(GUI gui, ArrayList<Program> programs, CubbyHole cubbyHole) {
        this.gui = gui;
        this.programs = programs;
        this.cubbyHole = cubbyHole;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Random random = new Random();
        Program p = new Program(this.gui.numberOfProcessOnTable++, random.nextInt(30));
        this.programs.add(p);
        this.gui.dtm.addRow(p.toArray());
        this.cubbyHole.getTree().insert(p);
    }
}