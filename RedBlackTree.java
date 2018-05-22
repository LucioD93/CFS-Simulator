
/**
 */ // class RedBlackNode
public class RedBlackTree<T extends Comparable<T>> {

    private RedBlackNode<T> nil = new RedBlackNode<T>();
    public RedBlackNode<T> root = nil;

    public RedBlackTree() {
        root.leftChild = nil;
        root.rightChild = nil;
        root.parent = nil;
    }

    // @param: x, The node which the lefRotate is to be performed on.
    // Performs a leftRotate around x.
    private void leftRotate(RedBlackNode<T> x){

        // Call leftRotateFixup() which updates the numberLeftChildren
        // and numberRightChildren values.
        leftRotateFixup(x);

        // Perform the leftChild rotate as described in the algorithm
        // in the course text.
        RedBlackNode<T> y;
        y = x.rightChild;
        x.rightChild = y.leftChild;

        // Check for existence of y.leftChild and make pointer changes
        if (y.leftChild != nil)
            y.leftChild.parent = x;
        y.parent = x.parent;

        // x's parent is nul
        if (x.parent == nil)
            root = y;

            // x is the leftChild child of it's parent
        else if (x.parent.leftChild == x)
            x.parent.leftChild = y;

            // x is the rightChild child of it's parent.
        else
            x.parent.rightChild = y;

        // Finish of the leftRotate
        y.leftChild = x;
        x.parent = y;
    }// end leftRotate(RedBlackNode x)


    // @param: x, The node which the leftRotate is to be performed on.
    // Updates the numberLeftChildren & numberRightChildren values affected by leftRotate.
    private void leftRotateFixup(RedBlackNode x){

        // Case 1: Only x, x.rightChild and x.rightChild.rightChild always are not nil.
        if (x.leftChild == nil && x.rightChild.leftChild == nil){
            x.numberLeftChildren = 0;
            x.numberRightChildren = 0;
            x.rightChild.numberLeftChildren = 1;
        }

        // Case 2: x.rightChild.leftChild also exists in addition to Case 1
        else if (x.leftChild == nil && x.rightChild.leftChild != nil){
            x.numberLeftChildren = 0;
            x.numberRightChildren = 1 + x.rightChild.leftChild.numberLeftChildren +
                    x.rightChild.leftChild.numberRightChildren;
            x.rightChild.numberLeftChildren = 2 + x.rightChild.leftChild.numberLeftChildren +
                    x.rightChild.leftChild.numberRightChildren;
        }

        // Case 3: x.leftChild also exists in addition to Case 1
        else if (x.leftChild != nil && x.rightChild.leftChild == nil){
            x.numberRightChildren = 0;
            x.rightChild.numberLeftChildren = 2 + x.leftChild.numberLeftChildren + x.leftChild.numberRightChildren;

        }
        // Case 4: x.leftChild and x.rightChild.leftChild both exist in addtion to Case 1
        else{
            x.numberRightChildren = 1 + x.rightChild.leftChild.numberLeftChildren +
                    x.rightChild.leftChild.numberRightChildren;
            x.rightChild.numberLeftChildren = 3 + x.leftChild.numberLeftChildren + x.leftChild.numberRightChildren +
                    x.rightChild.leftChild.numberLeftChildren + x.rightChild.leftChild.numberRightChildren;
        }

    }// end leftRotateFixup(RedBlackNode x)


    // @param: x, The node which the rightRotate is to be performed on.
    // Updates the numberLeftChildren and numberRightChildren values affected by the Rotate.
    private void rightRotate(RedBlackNode<T> y){

        // Call rightRotateFixup to adjust numberRightChildren and numberLeftChildren values
        rightRotateFixup(y);

        // Perform the rotate as described in the course text.
        RedBlackNode<T> x = y.leftChild;
        y.leftChild = x.rightChild;

        // Check for existence of x.rightChild
        if (x.rightChild != nil)
            x.rightChild.parent = y;
        x.parent = y.parent;

        // y.parent is nil
        if (y.parent == nil)
            root = x;

            // y is a rightChild child of it's parent.
        else if (y.parent.rightChild == y)
            y.parent.rightChild = x;

            // y is a leftChild child of it's parent.
        else
            y.parent.leftChild = x;
        x.rightChild = y;

        y.parent = x;

    }// end rightRotate(RedBlackNode y)


    // @param: y, the node around which the righRotate is to be performed.
    // Updates the numberLeftChildren and numberRightChildren values affected by the rotate
    private void rightRotateFixup(RedBlackNode y){

        // Case 1: Only y, y.leftChild and y.leftChild.leftChild exists.
        if (y.rightChild == nil && y.leftChild.rightChild == nil){
            y.numberRightChildren = 0;
            y.numberLeftChildren = 0;
            y.leftChild.numberRightChildren = 1;
        }

        // Case 2: y.leftChild.rightChild also exists in addition to Case 1
        else if (y.rightChild == nil && y.leftChild.rightChild != nil){
            y.numberRightChildren = 0;
            y.numberLeftChildren = 1 + y.leftChild.rightChild.numberRightChildren +
                    y.leftChild.rightChild.numberLeftChildren;
            y.leftChild.numberRightChildren = 2 + y.leftChild.rightChild.numberRightChildren +
                    y.leftChild.rightChild.numberLeftChildren;
        }

        // Case 3: y.rightChild also exists in addition to Case 1
        else if (y.rightChild != nil && y.leftChild.rightChild == nil){
            y.numberLeftChildren = 0;
            y.leftChild.numberRightChildren = 2 + y.rightChild.numberRightChildren +y.rightChild.numberLeftChildren;

        }

        // Case 4: y.rightChild & y.leftChild.rightChild exist in addition to Case 1
        else{
            y.numberLeftChildren = 1 + y.leftChild.rightChild.numberRightChildren +
                    y.leftChild.rightChild.numberLeftChildren;
            y.leftChild.numberRightChildren = 3 + y.rightChild.numberRightChildren +
                    y.rightChild.numberLeftChildren +
                    y.leftChild.rightChild.numberRightChildren + y.leftChild.rightChild.numberLeftChildren;
        }

    }// end rightRotateFixup(RedBlackNode y)


    synchronized public void insert(T key) {
        insert(new RedBlackNode<T>(key));
    }

    // @param: z, the node to be inserted into the Tree rooted at root
    // Inserts z into the appropriate position in the RedBlackTree while
    // updating numberLeftChildren and numberRightChildren values.
    private void insert(RedBlackNode<T> z) {

        // Create a reference to root & initialize a node to nil
        RedBlackNode<T> y = nil;
        RedBlackNode<T> x = root;

        // While we haven't reached a the end of the tree keep
        // tryint to figure out where z should go
        while (x != nil){
            y = x;

            // if z.key is < than the current key, go leftChild
            if (z.key.compareTo(x.key) < 0){

                // Update x.numberLeftChildren as z is < than x
                x.numberLeftChildren++;
                x = x.leftChild;
            }

            // else z.key >= x.key so go rightChild.
            else{

                // Update x.numGreater as z is => x
                x.numberRightChildren++;
                x = x.rightChild;
            }
        }
        // y will hold z's parent
        z.parent = y;

        // Depending on the value of y.key, put z as the leftChild or
        // rightChild child of y
        if (y == nil)
            root = z;
        else if (z.key.compareTo(y.key) < 0)
            y.leftChild = z;
        else
            y.rightChild = z;

        // Initialize z's children to nil and z's color to red
        z.leftChild = nil;
        z.rightChild = nil;
        z.color = RedBlackNode.RED;

        // Call insertFixup(z)
        insertFixup(z);

    }// end insert(RedBlackNode z)


    // @param: z, the node which was inserted and may have caused a violation
    // of the RedBlackTree properties
    // Fixes up the violation of the RedBlackTree properties that may have
    // been caused during insert(z)
    private void insertFixup(RedBlackNode<T> z){

        RedBlackNode<T> y = nil;
        // While there is a violation of the RedBlackTree properties..
        while (z.parent.color == RedBlackNode.RED){

            // If z's parent is the the leftChild child of it's parent.
            if (z.parent == z.parent.parent.leftChild){

                // Initialize y to z 's cousin
                y = z.parent.parent.rightChild;

                // Case 1: if y is red...recolor
                if (y.color == RedBlackNode.RED){
                    z.parent.color = RedBlackNode.BLACK;
                    y.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    z = z.parent.parent;
                }
                // Case 2: if y is black & z is a rightChild child
                else if (z == z.parent.rightChild){

                    // leftRotaet around z's parent
                    z = z.parent;
                    leftRotate(z);
                }

                // Case 3: else y is black & z is a leftChild child
                else{
                    // recolor and rotate round z's grandpa
                    z.parent.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    rightRotate(z.parent.parent);
                }
            }

            // If z's parent is the rightChild child of it's parent.
            else{

                // Initialize y to z's cousin
                y = z.parent.parent.leftChild;

                // Case 1: if y is red...recolor
                if (y.color == RedBlackNode.RED){
                    z.parent.color = RedBlackNode.BLACK;
                    y.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    z = z.parent.parent;
                }

                // Case 2: if y is black and z is a leftChild child
                else if (z == z.parent.leftChild){
                    // rightRotate around z's parent
                    z = z.parent;
                    rightRotate(z);
                }
                // Case 3: if y  is black and z is a rightChild child
                else{
                    // recolor and rotate around z's grandpa
                    z.parent.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        // Color root black at all times
        root.color = RedBlackNode.BLACK;

    }// end insertFixup(RedBlackNode z)

    public RedBlackNode<T> treeMinimum() {
        return this.treeMinimum(this.root);
    }

    // @param: node, a RedBlackNode
    // @param: node, the node with the smallest key rooted at node
    public RedBlackNode<T> treeMinimum(RedBlackNode<T> node){

        // while there is a smaller key, keep going leftChild
        while (node.leftChild != nil)
            node = node.leftChild;
        return node;
    }// end treeMinimum(RedBlackNode node)



    // @param: x, a RedBlackNode whose successor we must find
    // @return: return's the node the with the next largest key
    // from x.key
    public RedBlackNode<T> treeSuccessor(RedBlackNode<T> x){

        // if x.leftChild is not nil, call treeMinimum(x.rightChild) and
        // return it's value
        if (x.leftChild != nil )
            return treeMinimum(x.rightChild);

        RedBlackNode<T> y = x.parent;

        // while x is it's parent's rightChild child...
        while (y != nil && x == y.rightChild){
            // Keep moving up in the tree
            x = y;
            y = y.parent;
        }
        // Return successor
        return y;
    }// end treeMinimum(RedBlackNode x)


    // @param: z, the RedBlackNode which is to be removed from the the tree
    // Remove's z from the RedBlackTree rooted at root
    synchronized public void remove(RedBlackNode<T> v){

        RedBlackNode<T> z = search(v.key);

        // Declare variables
        RedBlackNode<T> x = nil;
        RedBlackNode<T> y = nil;

        // if either one of z's children is nil, then we must remove z
        if (z.leftChild == nil || z.rightChild == nil)
            y = z;

            // else we must remove the successor of z
        else y = treeSuccessor(z);

        // Let x be the leftChild or rightChild child of y (y can only have one child)
        if (y.leftChild != nil)
            x = y.leftChild;
        else
            x = y.rightChild;

        // link x's parent to y's parent
        x.parent = y.parent;

        // If y's parent is nil, then x is the root
        if (y.parent == nil)
            root = x;

            // else if y is a leftChild child, set x to be y's leftChild sibling
        else if (y.parent.leftChild != nil && y.parent.leftChild == y)
            y.parent.leftChild = x;

            // else if y is a rightChild child, set x to be y's rightChild sibling
        else if (y.parent.rightChild != nil && y.parent.rightChild == y)
            y.parent.rightChild = x;

        // if y != z, trasfer y's satellite data into z.
        if (y != z){
            z.key = y.key;
        }

        // Update the numberLeftChildren and numberRightChildren numbers which might need
        // updating due to the deletion of z.key.
        fixNodeData(x,y);

        // If y's color is black, it is a violation of the
        // RedBlackTree properties so call removeFixup()
        if (y.color == RedBlackNode.BLACK)
            removeFixup(x);
    }// end remove(RedBlackNode z)


    // @param: y, the RedBlackNode which was actually deleted from the tree
    // @param: key, the value of the key that used to be in y
    private void fixNodeData(RedBlackNode<T> x, RedBlackNode<T> y){

        // Initialize two variables which will help us traverse the tree
        RedBlackNode<T> current = nil;
        RedBlackNode<T> track = nil;


        // if x is nil, then we will start updating at y.parent
        // Set track to y, y.parent's child
        if (x == nil){
            current = y.parent;
            track = y;
        }

        // if x is not nil, then we start updating at x.parent
        // Set track to x, x.parent's child
        else{
            current = x.parent;
            track = x;
        }

        // while we haven't reached the root
        while (current != nil){
            // if the node we deleted has a different key than
            // the current node
            if (y.key != current.key) {

                // if the node we deleted is greater than
                // current.key then decrement current.numberRightChildren
                if (y.key.compareTo(current.key) > 0)
                    current.numberRightChildren--;

                // if the node we deleted is less than
                // current.key thendecrement current.numberLeftChildren
                if (y.key.compareTo(current.key) < 0)
                    current.numberLeftChildren--;
            }

            // if the node we deleted has the same key as the
            // current node we are checking
            else{
                // the cases where the current node has any nil
                // children and update appropriately
                if (current.leftChild == nil)
                    current.numberLeftChildren--;
                else if (current.rightChild == nil)
                    current.numberRightChildren--;

                    // the cases where current has two children and
                    // we must determine whether track is it's leftChild
                    // or rightChild child and update appropriately
                else if (track == current.rightChild)
                    current.numberRightChildren--;
                else if (track == current.leftChild)
                    current.numberLeftChildren--;
            }

            // update track and current
            track = current;
            current = current.parent;

        }

    }//end fixNodeData()


    // @param: x, the child of the deleted node from remove(RedBlackNode v)
    // Restores the Red Black properties that may have been violated during
    // the removal of a node in remove(RedBlackNode v)
    private void removeFixup(RedBlackNode<T> x){

        RedBlackNode<T> w;

        // While we haven't fixed the tree completely...
        while (x != root && x.color == RedBlackNode.BLACK){

            // if x is it's parent's leftChild child
            if (x == x.parent.leftChild){

                // set w = x's sibling
                w = x.parent.rightChild;

                // Case 1, w's color is red.
                if (w.color == RedBlackNode.RED){
                    w.color = RedBlackNode.BLACK;
                    x.parent.color = RedBlackNode.RED;
                    leftRotate(x.parent);
                    w = x.parent.rightChild;
                }

                // Case 2, both of w's children are black
                if (w.leftChild.color == RedBlackNode.BLACK &&
                        w.rightChild.color == RedBlackNode.BLACK){
                    w.color = RedBlackNode.RED;
                    x = x.parent;
                }
                // Case 3 / Case 4
                else{
                    // Case 3, w's rightChild child is black
                    if (w.rightChild.color == RedBlackNode.BLACK){
                        w.leftChild.color = RedBlackNode.BLACK;
                        w.color = RedBlackNode.RED;
                        rightRotate(w);
                        w = x.parent.rightChild;
                    }
                    // Case 4, w = black, w.rightChild = red
                    w.color = x.parent.color;
                    x.parent.color = RedBlackNode.BLACK;
                    w.rightChild.color = RedBlackNode.BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            }
            // if x is it's parent's rightChild child
            else{

                // set w to x's sibling
                w = x.parent.leftChild;

                // Case 1, w's color is red
                if (w.color == RedBlackNode.RED){
                    w.color = RedBlackNode.BLACK;
                    x.parent.color = RedBlackNode.RED;
                    rightRotate(x.parent);
                    w = x.parent.leftChild;
                }

                // Case 2, both of w's children are black
                if (w.rightChild.color == RedBlackNode.BLACK &&
                        w.leftChild.color == RedBlackNode.BLACK){
                    w.color = RedBlackNode.RED;
                    x = x.parent;
                }

                // Case 3 / Case 4
                else{
                    // Case 3, w's leftChild child is black
                    if (w.leftChild.color == RedBlackNode.BLACK){
                        w.rightChild.color = RedBlackNode.BLACK;
                        w.color = RedBlackNode.RED;
                        leftRotate(w);
                        w = x.parent.leftChild;
                    }

                    // Case 4, w = black, and w.leftChild = red
                    w.color = x.parent.color;
                    x.parent.color = RedBlackNode.BLACK;
                    w.leftChild.color = RedBlackNode.BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }// end while

        // set x to black to ensure there is no violation of
        // RedBlack tree Properties
        x.color = RedBlackNode.BLACK;
    }// end removeFixup(RedBlackNode x)


    // @param: key, the key whose node we want to search for
    // @return: returns a node with the key, key, if not found, returns null
    // Searches for a node with key k and returns the first such node, if no
    // such node is found returns null
    public RedBlackNode<T> search(T key){

        // Initialize a pointer to the root to traverse the tree
        RedBlackNode<T> current = root;

        // While we haven't reached the end of the tree
        while (current != nil){

            // If we have found a node with a key equal to key
            if (current.key.equals(key))

                // return that node and exit search(int)
                return current;

                // go leftChild or rightChild based on value of current and key
            else if (current.key.compareTo(key) < 0)
                current = current.rightChild;

                // go leftChild or rightChild based on value of current and key
            else
                current = current.leftChild;
        }

        // we have not found a node whose key is "key"
        return null;


    }// end search(int key)

}