/**
 * Your implementation of a linked stack. It should NOT be circular.
 *
 * @author Joao Matheus Nascimento Francolin
 * @userid jfrancolin3
 * @GTID 903207758
 * @version 1.0
 */

import java.util.NoSuchElementException;

public class LinkedStack<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private int size;

    /**
     * Adds the given data onto the stack. The given element becomes the
     * top-most element of the stack.
     *
     * This method should be implemented in O(1) time.
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data is null
     */
    public void push(T data) {
        // check for ilegal data
        if (data == null) {
            throw new IllegalArgumentException("Data structure does not "
            + "support null objects");
        }
        // create new node
        LinkedNode<T> current = new LinkedNode(data);
        // insert at the head
        current.setNext(head);
        head = current;
        // update variables
        size++;
    }

    /**
     * Removes and returns the top-most element on the stack.
     *
     * This method should be implemented in O(1) time.
     *
     * @return the data from the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T pop() {
        // check for empty queue
        if (size == 0) {
            throw new NoSuchElementException("Cannot dequeue empty queue");
        }
        // save a copy of the data
        T element = head.getData();
        // delete data
        head = head.getNext();
        // update variables
        size--;
        // return data
        return element;
    }

    /**
     * Retrieves the next element to be popped without removing it.
     *
     * This method should be implemented in O(1) time.
     *
     * @return the next data or null if the stack is empty
     */
    public T peek() {
        // check for empty queue
        if (size == 0) {
            return null;
        }
        // return data
        return head.getData();
    }

    /**
     * Return the size of the stack.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the stack
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the head node of the stack.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the head node
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }
}