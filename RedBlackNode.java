
/**
 */ // class RedBlackNode
class RedBlackNode<T extends Comparable<T>> {

    /** Possible color for this node */
    public static final int BLACK = 0;
    /** Possible color for this node */
    public static final int RED = 1;
    // the key of each node
    public T key;

    /** Parent of node */
    RedBlackNode<T> parent;
    /** Left child */
    RedBlackNode<T> leftChild;
    /** Right child */
    RedBlackNode<T> rightChild;
    // the number of elements to the leftChild of each node
    public int numberLeftChildren = 0;
    // the number of elements to the rightChild of each node
    public int numberRightChildren = 0;
    // the color of a node
    public int color;

    RedBlackNode(){
        color = BLACK;
        numberLeftChildren = 0;
        numberRightChildren = 0;
        parent = null;
        leftChild = null;
        rightChild = null;
    }

    // Constructor which sets key to the argument.
    RedBlackNode(T key){
        this();
        this.key = key;
    }
}// end class RedBlackNode
