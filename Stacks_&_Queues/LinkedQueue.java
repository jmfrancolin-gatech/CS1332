/**
 * Your implementation of a linked queue. It should NOT be circular.
 *
 * @author Joao Matheus Nascimento Francolin
 * @userid jfrancolin3
 * @GTID 903207758
 * @version 1.0
 */

import java.util.NoSuchElementException;

public class LinkedQueue<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    // add at the tail
    // remove at the head

    /**
     * Adds the given data to the queue.
     *
     * This method should be implemented in O(1) time.
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        // check for ilegal data
        if (data == null) {
            throw new IllegalArgumentException("Data structure does not "
            + "support null objects");
        }
        // create new node
        LinkedNode<T> current = new LinkedNode(data);
        // insert node on an empty queue
        if (size == 0) {
            // update variables
            head = current;
            tail = current;
        // insert node on a non-empty queue
        } else if (size > 0) {
            // insert at the tail
            tail.setNext(current);
            tail = current;
        }
        size++;
    }

    /**
     * Removes the data from the front of the queue.
     *
     * This method should be implemented in O(1) time.
     *
     * @return the data from the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        // check for empty queue
        if (size == 0) {
            throw new NoSuchElementException("Cannot dequeue empty queue");
        }
        // save a copy of the data
        T element = head.getData();
        // delete data
        head = head.getNext();
        // check if queue is now empty to update tail
        if (head == null) {
            tail = head;
        }
        // update size
        size--;
        return element;
    }

    /**
     * Retrieves the next data to be dequeued without removing it.
     *
     * This method should be implemented in O(1) time.
     *
     * @return the next data or null if the queue is empty
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
     * Return the size of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the queue
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the head node of the queue.
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

    /**
     * Returns the tail node of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the tail node
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }
}