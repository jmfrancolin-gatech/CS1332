/**
 * Your implementation of a circular singly linked list.
 *
 * @author Joao Matheus Nascimento Francolin
 * @userid jfrancolin3
 * @GTID 903207758
 * @version 1.0
 */
public class SinglyLinkedList<T> {
    // Do not add new instance variables or modify existing ones.
    private LinkedListNode<T> head;
    private int size;

    public void printList(){

        LinkedListNode<T> current = head;

        do{
            System.out.println(current.getData());
            current = current.getNext();
        } while(current != head);
        System.out.println();
    }

    /**
     * Adds the element to the index specified.
     *
     * Adding to indices 0 and {@code size} should be O(1), all other cases are
     * O(n).
     *
     * @param index the requested index for the new element
     * @param data the data for the new element
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index > size
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addAtIndex(int index, T data) {
        // trow Exceptions if needed
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into"
            + " data structure.");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Trying to insert element"
            + " at an index out of range");
        }

        // add to empty list
        if(size == 0){
            LinkedListNode<T> node = new LinkedListNode(data);
            head = node;
            node.setNext(head);
        // add to front
        } else if(index == 0){
            // create a new node & put the data from the head into the new node
            LinkedListNode<T> node = new LinkedListNode(head.getData());
            // set the new node’s next to head’s next
            node.setNext(head.getNext());
            // set head’s next to the new node
            head.setNext(node);
            // put the data we want to add into the head node
            head.setData(data);
        // add to back
        } else if(index == size){
            // create a new node & put the data from the head into the new node
            LinkedListNode<T> node = new LinkedListNode(head.getData());
            // set the new node’s next to head’s next
            node.setNext(head.getNext());
            // set head’s next to the new node
            head.setNext(node);
            // put the data we want to add into the head node
            head.setData(data);
            // update head
            head = node;
        // add at index
        } else {
            // create new node
            LinkedListNode<T> node = new LinkedListNode(data);
            // create reference node to iterate the list
            LinkedListNode<T> current = head;
            for (int i = 0; i < index - 1; i++){
                current = current.getNext();
            }
            // set node next to current next
            node.setNext(current.getNext());
            // set current next to node
            current.setNext(node);
        }
        size++;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the element from the index specified.
     *
     * Removing from index 0 should be O(1), all other cases are O(n).
     *
     * @param index the requested index to be removed
     * @return the data formerly located at index
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index >= size
     */
    public T removeAtIndex(int index) {
        // trow Exceptions if needed
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Trying to insert element"
            + " at an index out of range");
        }

        // empty list
        if (size == 0){
            return null;
        // remove from list of size 1
        } else if(size == 1){
            // save head data
            T node_data = head.getData();
            // update head
            head = null;
            // update size
            size--;
            // return saved data
            return node_data;
        // remove from front
        } else if(index == 0){
            // save head data
            T node_data = head.getData();
            // set head data to head->next data
            head.setData((head.getNext()).getData());
            // set head->next to (head->next)->next
            head.setNext((head.getNext()).getNext());
            // update size
            size--;
            // return saved data
            return node_data;
        // remove at index
        } else {
            // create reference node to iterate the list
            LinkedListNode<T> current = head;
            for(int i = 0; i < index - 1; i++){
                current = current.getNext();
            }
            // create new node to return data
            T node_data = (current.getNext()).getData();
            // current->next = (current->next)->next
            current.setNext((current.getNext()).getNext());
            // update size
            size--;
            // return saved data
            return node_data;
        }
    }

    /**
     * Removes and returns the element at the front of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(1) for all cases.
     *
     * @return the data formerly located at the front, null if empty list
     */
    public T removeFromFront() {
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the element at the back of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(n) for all cases.
     *
     * @return the data formerly located at the back, null if empty list
     */
    public T removeFromBack() {
        return removeAtIndex(size - 1);
    }

    /**
     * Removes the last copy of the given data from the list.
     *
     * Must be O(n) for all cases.
     *
     * @param data the data to be removed from the list
     * @return the removed data occurrence from the list itself (not the data
     * passed in), null if no occurrence
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public T removeLastOccurrence(T data) {
        // trow Exceptions if needed
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into"
            + " data structure.");
        }

        if (size == 0){
            return null;
        }
        if (size == 1){
            removeAtIndex(0);
        }

        // create new node
        LinkedListNode<T> node = null;
        // create reference node to iterate the list
        LinkedListNode<T> current = head;

        if(head.getData().equals(data)){
            node = head;
        }
        do{
            if(current.getNext().getData().equals(data)){
                node = current;
            }
            current = current.getNext();
        } while(current != head);

        if (node != null){
            T node_data = node.getNext().getData();
            node.setNext(node.getNext().getNext());
            size--;
            return node_data;
        }
        return null;
    }

    /**
     * Returns the element at the specified index.
     *
     * Getting index 0 should be O(1), all other cases are O(n).
     *
     * @param index the index of the requested element
     * @return the object stored at index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T get(int index) {
        // trow Exceptions if needed
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Trying to insert element"
            + " at an index out of range");
        }

        // create reference node to iterate the list
        LinkedListNode<T> current = head;
        for(int i = 0; i < index; i++){
            current = current.getNext();
        }
        return (T) current.getData();
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length {@code size} holding all of the objects in
     * this list in the same order
     */
    public Object[] toArray() {

        Object array[] = new Object[size];

        // create reference node to iterate the list
        LinkedListNode<T> current = head;
        for(int i = 0; i < size; i++){
            array[i] = current.getData();
            current = current.getNext();
        }

        return array;
    }

    /**
     * Returns a boolean value indicating if the list is empty.
     *
     * Must be O(1) for all cases.
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the list of all data.
     *
     * Must be O(1) for all cases.
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    /**
     * Returns the head node of the linked list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the linked list
     */
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }
}