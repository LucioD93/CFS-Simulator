public class Main {

    public static void main(String[] args) {

        Program p1 = new Program(1, 5, 0);

        RedBlackTree<Program> tree = new RedBlackTree<>();
        tree.insert(p1);
        tree.insert(new Program(2, 10, 0));
        tree.insert(new Program(3, 15, 0));
        tree.insert(new Program(4, 20, 0));
        tree.insert(new Program(5, 25, 0));
        tree.insert(new Program(6, 30, 0));
        tree.insert(new Program(7, 35, 0));
        tree.insert(new Program(8, 40, 0));

        tree.remove(tree.treeMinimum());
        tree.remove(tree.treeMinimum());
        tree.remove(tree.treeMinimum());

        System.out.println("Todo bien");

    }

}
