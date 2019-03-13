import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
/**
 * Your implementation of an AVL Tree.
 *
 * @author Joao Matheus Nascimento Francolin
 * @userid jfrancolin3
 * @GTID 903207758
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        
        // check for exceptions
        if (data == null) {
            throw new IllegalArgumentException(
            "Data structure does not support null entries");
        }
        // add nodes from collection
        for (T node: data) {
            add(node);
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        
        // check for exceptions
        if (data == null) {
            throw new IllegalArgumentException(
            "Data structure does not support null entries");
        }
        // call add with root argumment
        root = add(data, root);
    }

    /**
     * Recursively adds data to the tree
     * 
     * @param data the data to added
     * @param node the root node for a subtree
     * @return the root node of the tree
     */    
    private AVLNode<T> add(T data, AVLNode<T> node) {
        
        // check for exceptions
        if (node == null) {
            size++;
            return new AVLNode<>(data);
        }
        // insert left
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(add(data, node.getLeft()));
        }
        // insert right
        if (data.compareTo(node.getData()) > 0) {
            node.setRight(add(data, node.getRight()));
        }
        // update and return
        update(node);
        node = rotate(node);
        return node;
    }

    /**
     * Decides how to rotate a node
     * 
     * @param node the node to possibly be rotated
     * @return the node
     */ 
    private AVLNode<T> rotate(AVLNode<T> node) {

        // left rotation    
        if (node.getBalanceFactor() > 1) {
            // left-right rotation
            if (node.getLeft().getBalanceFactor() < 0) {
                node.setLeft(rotateLeft(node.getLeft()));
            }
            node = rotateRight(node);
        }
        // right rotation
        if (node.getBalanceFactor() < -1) {
            // right-left rotation    
            if (node.getRight().getBalanceFactor() > 0) {
                node.setRight(rotateRight(node.getRight()));
            }    
            node = rotateLeft(node);
        }
        return node;
    }

    /**
     * Left rotate a node
     * 
     * @param node the node to be rotated
     * @return the node
     */ 
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
    
        // get right-child
        AVLNode<T> rightChild = node.getRight();
        // node->right = node->right->left
        node.setRight(node.getRight().getLeft());
        // right-child->left = node
        rightChild.setLeft(node);
        // update and return
        update(rightChild.getLeft());
        update(rightChild);
        return rightChild;
    }

    /**
     * Right rotate a node
     * 
     * @param node the node to be rotated
     * @return the node
     */ 
    private AVLNode<T> rotateRight(AVLNode<T> node) {
    
        // get left-child
        AVLNode<T> leftChild = node.getLeft();
        // node->left = node->left->right
        node.setLeft(node.getLeft().getRight());
        // left-child->right = node
        leftChild.setRight(node);
        // update and return
        update(leftChild.getRight());
        update(leftChild);
        return leftChild;
    }

    /**
     * Update hight and balancing factor
     * 
     * @param node the node to be updated
     */ 
    private void update(AVLNode<T> node) {
    
        // default hights for null nodes
        int heightLeft = -1;
        int heightRight = -1;

        // update heightLeft
        if (node.getLeft() != null) {
            heightLeft = node.getLeft().getHeight();
        }
        // update heightRight
        if (node.getRight() != null) {
            heightRight = node.getRight().getHeight();
        }
        // set height and balancing factor
        node.setBalanceFactor(heightLeft - heightRight);
        node.setHeight(Math.max(heightLeft, heightRight) + 1);
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {

        // check for exceptions
        if (data == null) {
            throw new IllegalArgumentException(
            "Data structure does not support null entries");
        }
        
        // instanciate a buffer to store the data
        AVLNode<T> buffer = new AVLNode<>(null);
        // find and remove node with data
        root = remove(data, root, buffer);
        // return saved value
        return buffer.getData();
    }

    /**
     * Recursively finds and removes a node
     * 
     * @param data the data to removed
     * @param node a tracker node
     * @param buffer a node to store the data
     * @return the node
     */
    private AVLNode<T> remove(T data, AVLNode<T> node, AVLNode<T> buffer) {
        
        // check for exceptions
        if (node == null) {
            throw new NoSuchElementException("Data not found");
        }

        // travel left
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(remove(data, node.getLeft(), buffer));
        }
        // travel right
        if (data.compareTo(node.getData()) > 0) {
            node.setRight(remove(data, node.getRight(), buffer));
        }
        // found data
        if (data.equals(node.getData())) {   
            // save data
            buffer.setData(node.getData());
            size--;
            // get successor
            AVLNode<T> successor = new AVLNode<>(null);
            node.setRight(successor(node.getRight(), successor));
            node.setData(successor.getData());
        }
        // update and return
        update(node);
        return rotate(node);
    }

    /**
     * Finds the node successor after removal
     *
     * @param node the node to be succeded
     * @param successor a tracker node for candidates
     * @return successor node
     */
    private AVLNode<T> successor(AVLNode<T> node, AVLNode<T> successor) {
    
        // instanciate successor
        if (node.getLeft() == null) {
            successor.setData(node.getData());
            return node.getRight();
        }
        // travel the tree until left child is a leaf
        node.setLeft(successor(node.getLeft(), successor));  
        // update and return
        update(node);
        rotate(node);
        return node;
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        
        // check for exceptions
        if (data == null) {
            throw new IllegalArgumentException(
            "Data structure does not support null entries");
        }

        // travel the tree until data is found
        return get(data, root).getData();
    }

    /**
     * Transverse tree to find data
     * 
     * @param data the data to be found
     * @param node the root node for a subtree
     * @return the root node of the tree
     */ 
    private AVLNode<T> get(T data, AVLNode<T> node) {
    
        // check for exceptions
        if (node == null) {
            throw new NoSuchElementException("Data not found");
        }
        
        // travel left
        if (data.compareTo(node.getData()) < 0) {
            return get(data, node.getLeft());
        }
        // travel right
        if (data.compareTo(node.getData()) > 0) {
            return get(data, node.getRight());
        }
        // found data
        return node;
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        
        // check for exceptions
        if (data == null) {
            throw new IllegalArgumentException(
            "Data structure does not support null entries");
        }
        
        // get will throw an exeption if data is not found
        // use try-catch block 
        try {
            // return true if no exeption is thrown
            get(data);
            return true;
        } catch (NoSuchElementException e) {
            // return false otherwise
            return false;
        }
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * Your list should not duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        
        // instanciate list
        List<T> list = new ArrayList<>();
        // travel tree and add to list
        deepestBranches(root, list);
        return list;
    }

    /**
     * Transverse tree with the deepest brach
     * 
     * @param node the root node for a subtree
     * @param list the list to add data to
     */
    private void deepestBranches(AVLNode<T> node, List<T> list) {
    
        // travel left
        if (node.getBalanceFactor() > 0) {
            list.add(node.getData());
            deepestBranches(node.getLeft(), list);
            return;
        }
        // travel right
        if (node.getBalanceFactor() < 0) {
            list.add(node.getData());
            deepestBranches(node.getRight(), list);
            return;
        }
        // add node to list
        list.add(node.getData());
        // if not leaf, keep travalling
        if (node.getHeight() != 0) {
            deepestBranches(node.getLeft(), list);
            deepestBranches(node.getRight(), list);
            return;
        }
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * @throws java.lang.IllegalArgumentException if data1 or data2 are null
     * or if data1 > data2
     * @return a sorted list of data that is > data1 and < data2
     */
    public List<T> sortedInBetween(T data1, T data2) {

        // check for exceptions        
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException(
            "Data structure does not support null entries");
        }
        if (data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException(
            "Data1 and data2 are out of order");
        }

        // instanciate list
        List<T> list = new ArrayList<>();
        // travel tree and add to list
        sortedInBetween(data1, data2, root, list);
        return list;
    }

    /**
     * Transverse tree within the given range
     * 
     * @param data1 lower threshold
     * @param data2 upper threshold
     * @param node tracker node
     * @param list the list to add data to
     */
    private void sortedInBetween(T data1, T data2, AVLNode<T> node,
        List<T> list) {

        // reached end of the tree
        if (node == null) {
            return;
        }

        // travel left
        if (data1.compareTo(node.getData()) < 0) {
            sortedInBetween(data1, data2, node.getLeft(), list);
        }
        // add to list
        if (data1.compareTo(node.getData()) < 0 
                && data2.compareTo(node.getData()) > 0) {
            list.add(node.getData());
        }
        // travel right
        if (data2.compareTo(node.getData()) > 0) {
            sortedInBetween(data1, data2, node.getRight(), list);
        }
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {

        // root is null     
        if (root == null) {
            return -1;
        }

        return root.getHeight();
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}