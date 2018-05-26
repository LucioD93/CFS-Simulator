import java.util.ArrayList;

public class Main {
    public synchronized static void main(String[] args) {
        // Read data from xml file.
        ArrayList<Program> program_list;
        XMLParser xmlparser = new XMLParser();
        program_list = xmlparser.readXMLFile("process.xml");

        // Set critical region and producer consumer.
        CubbyHole cubbyHole = new CubbyHole(program_list);
        Producer p1 = new Producer(cubbyHole, program_list);
        CPU cpu1 = new CPU(cubbyHole);

        p1.run();
        cpu1.run();
    }
}
