/**
 * Your implementation of an array-backed queue.
 *
 * @author Joao Matheus Nascimento Francolin
 * @userid jfrancolin3
 * @GTID 903207758
 * @version 1.0
 */

import java.util.NoSuchElementException;

public class ArrayQueue<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * The initial capacity of a queue with fixed-size backing storage.
     */
    public static final int INITIAL_CAPACITY = 9;

    /**
     * Constructs a new ArrayQueue.
     */
    public ArrayQueue() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        front = 0;
        size = 0;
    }

    /**
     * Adds the given data to the queue.
     *
     * If sufficient space is not available in the backing array, you should
     * resize it to double the current length. If a resize is necessary,
     * you should copy elements to the front of the new array and reset
     * front to 0.
     *
     * This method should be implemented in amortized O(1) time.
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
        // compute tail
        int tail = (size + front) % (backingArray.length);
        // resize condition
        if (tail == front && backingArray[front] != null) {
            // declate new array
            T[] newArray = (T[]) new Object[backingArray.length * 2];
            // copy elements over
            for (int i = 0; i < size; i++) {
                newArray[i] = backingArray[(front + i) % (backingArray.length)];
            }
            // update variables
            backingArray = newArray;
            front = 0;
            tail = size;
        }
        // insert element
        backingArray[tail] = data;
        size++;
    }

    /**
     * Removes the data from the front of the qcueue.
     *
     * Do not shrink the backing array. If the queue becomes empty as a result
     * of this call, you should explicitly reset front to 0.
     *
     * You should replace any spots that you dequeue from with null. Failure to
     * do so can result in a loss of points.
     *
     * This method should be implemented in O(1) time.
     *
     * See the homework pdf for more information on implementation details.
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
        T element = backingArray[front];
        // delete data
        backingArray[front] = null;
        // update variables
        front = (front + 1) % backingArray.length;
        size--;
        // return data
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
        return backingArray[front];
    }

    /**
     * Returns the size of the queue.
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
     * Returns the backing array of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }
}