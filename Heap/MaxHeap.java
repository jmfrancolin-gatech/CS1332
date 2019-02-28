import java.util.ArrayList;
import java.util.NoSuchElementException;
/**
 * Your implementation of a max heap.
 *
 * @author Joao Matheus Nascimento Francolin
 * @userid jfrancolin3
 * @GTID 903207758
 * @version 1.0
 */
public class MaxHeap<T extends Comparable<? super T>> {

    // DO NOT ADD OR MODIFY THESE INSTANCE/CLASS VARIABLES.
    public static final int INITIAL_CAPACITY = 13;

    private T[] backingArray;
    private int size;

    /**
     * Creates a Heap with an initial capacity of INITIAL_CAPACITY
     * for the backing array.
     *
     * Use the constant field provided. Do not use magic numbers!
     */
    public MaxHeap() {

        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * The data in the backingArray should be in the same order as it appears
     * in the passed in ArrayList before you start the Build Heap Algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY from
     * the interface). Index 0 should remain empty, indices 1 to n should
     * contain the data in proper order, and the rest of the indices should
     * be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public MaxHeap(ArrayList<T> data) {

        // trow Exceptions if needed
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into"
            + " data structure.");
        }

        // copy data into backingArray
        size = data.size();
        backingArray = (T[]) new Comparable[(2 * size) + 1];
        for (int index = size; index > 0; index--) {
            backingArray[index] = data.get(index - 1);
        }

        // build heap
        for (int index = size / 2; index > 0; index--) {
            downHeap(index);
        }
    }

    /**
     * heapfy up
     *
     * @param index node index
     */
    private void upHeap(int index) {

        // root node
        if (index == 1) {
            return;
        }

        T node = backingArray[index];
        T parent = backingArray[index / 2];

        // if parent < node
        if (parent.compareTo(node) < 0) {
            backingArray[index] = parent;
            backingArray[index / 2] = node;
            upHeap(index / 2);
        }
        // parent >= node
        return;
    }

    /**
     * heapfy down
     *
     * @param index node index
     */
    private void downHeap(int index) {

        // parent with two children
        if (hasLeftChild(index) && hasRightChild(index)) {
            // get leftChild & rightChild
            T parent = backingArray[index];
            T leftChild = backingArray[2 * index];
            T rightChild = backingArray[(2 * index) + 1];

            // parent < leftChild || parent < rightChild
            if (parent.compareTo(leftChild) < 0
                || parent.compareTo(rightChild) < 0) {

                // leftChild < rightChild
                if (leftChild.compareTo(rightChild) < 0) {
                    // swap parent with rightChild
                    backingArray[index * 2 + 1] = backingArray[index];
                    backingArray[index] = rightChild;
                    // parent = rightChild
                    downHeap(index * 2 + 1);

                // leftChild >= rightChild
                } else {
                    // swap parent and leftChild
                    backingArray[index * 2] = backingArray[index];
                    backingArray[index] = leftChild;
                    // parent = leftChild
                    downHeap(index * 2);
                }
            }
        // parent with one child
        } else if (hasLeftChild(index)) {
            // get leftChild
            T parent = backingArray[index];
            T leftChild = backingArray[2 * index];

            // parent < leftChild
            if (parent.compareTo(leftChild) < 0) {
                // swap parent and leftChild
                backingArray[index * 2] = backingArray[index];
                backingArray[index] = leftChild;
                // parent = leftChild
                downHeap(index * 2);
            }
        }
        // leaf node
        return;
    }

    /**
     * check if node has left children
     *
     * @param index node index
     * @return boolean
     */
    private boolean hasLeftChild(int index) {
        return index * 2 <= size;
    }

    /**
     * check if node has right children
     *
     * @param index node index
     * @return boolean
     */
    private boolean hasRightChild(int index) {
        return index * 2 + 1 <= size;
    }

    /**
     * Adds an item to the heap. If the backing array is full and you're trying
     * to add a new item, then double its capacity.
     *
     * @throws IllegalArgumentException if the item is null
     * @param item the item to be added to the heap
     */
    public void add(T item) {

        // trow Exceptions if needed
        if (item == null) {
            throw new IllegalArgumentException("Cannot insert null data into"
            + " data structure.");
        }

        // first element of the array is null
        // therefore max size == backingArray.size() - 1
        if (size == backingArray.length - 1) {
            // resize backingArray
            T[] buff = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i < backingArray.length; i++) {
                buff[i] = backingArray[i];
            }
            backingArray = buff;
        }
        // insert element
        backingArray[size + 1] = item;
        // swap up to correct location
        upHeap(size + 1);
        // update size
        size++;
    }

    /**
     * Removes and returns the max item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * @throws java.util.NoSuchElementException if the heap is empty
     * @return the removed item
     */
    public T remove() {

        // trow Exceptions if needed
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty.");
        }

        // save return value
        T value = backingArray[1];
        // swap root node with last element of heap
        backingArray[1] = backingArray[size];
        // set last element of heap to null
        backingArray[size] = null;
        // update size
        size--;
        // update root node
        downHeap(1);
        // return saved value
        return value;
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element, null if the heap is empty
     */
    public T getMax() {

        // empty heap
        if (size == 0) {
            return null;
        }
        // return root
        return backingArray[1];
    }

    /**
     * Returns if the heap is empty or not.
     *
     * @return true if the heap is empty, false otherwise
     */
    public boolean isEmpty() {

        return size == 0;
    }

    /**
     * Clears the heap and rests the backing array to a new array of capacity
     * {@code INITIAL_CAPACITY}.
     */
    public void clear() {

        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the heap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the heap
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }
}