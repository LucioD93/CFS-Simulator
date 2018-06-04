import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.security.Guard;
import java.util.ArrayList;
import java.util.Random;

public class HandlerClass  implements ActionListener {
    private GUI gui;
    ArrayList<Program> programs;

    public HandlerClass(GUI gui, ArrayList<Program> programs) {
        this.gui = gui;
        this.programs = programs;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        Random random = new Random();
        Program p = new Program(this.gui.numberOfProcessOnTable++, random.nextInt(30));
        this.programs.add(p);
        this.gui.dtm.addRow(p.toArray());
    }
}