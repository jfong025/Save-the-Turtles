// =============================================================================

/**
 * The MyGenericDS class represents a data structure that is used to hold the heart objects, which
 * allows a flexible way to edit the structure that holds heart objects.
 **/
// =============================================================================
public class MyGenericDS<E> implements GenericOrderedCollection<E> {
    // =============================================================================


    // =========================================================================

    // creates an ending node
    Node<E> end;
    // =========================================================================

    /**
     * The constructor, which creates a new MyGeneric instance with default
     * values.
     **/

    public MyGenericDS() {
        end = null;
    }
    // =========================================================================

    /**
     * Adds a new element to the collection.
     **/

    public void append(E toAppend) {
        Node<E> toAdd = new Node<E>(toAppend);
        // referencing node inside a node
        toAdd.next = end;
        end = toAdd;
    }
    // =========================================================================

    /**
     * Returns the last element in the collection.
     **/

    public E peek() {
        if (end == null) {
            return null;
        }
        return end.element;
    }
    // =========================================================================

    /**
     * Removes the last element in the collection.
     **/

    public E pop() {
        if (end == null) {
            return null;
        }
        E toReturn = end.element;
        // setting end node to be node from before
        end = end.next;
        return toReturn;
    }
    // =========================================================================

    /**
     * Gets an object at a specified index.
     **/

    public E get(int index) {
        int counter = 1;
        Node<E> n = end;
        if (index <= length()) {
            while (n != null) {
                //end case
                if (index == length() - 1) {
                    return n.element;
                }
                if (counter == length() - (index)) {
                    return n.element;
                }
                counter++;
                n = n.next;
            }
        }
        return null;
    }
    // =========================================================================

    /**
     * Removes an object at a specified index.
     **/

    public void remove(int index) {
        int counter = 1;
        Node<E> n = end;
        if (index <= length()) {
            while (n != null) {
                if (index == length() - 1) {
                    pop();
                    break;
                }
                if (counter == length() - (index + 1)) {
                    n.next = n.next.next;
                    break;
                }
                counter++;
                n = n.next;
            }
        }
    }
    // =========================================================================

    /**
     * Prints out all the elements in the collection.
     **/

    public String toString() {
        String toReturn = "";
        Node<E> n = end;
        while (n != null) {
            toReturn = n.element + " " + toReturn;
            n = n.next;
        }
        return toReturn;
    }
    // =========================================================================

    /**
     * Updates the size of the collection.
     **/

    public int length() {
        int size = 0;
        Node<E> n = end;
        while (n != null) {
            n = n.next;
            size++;
        }
        return size;
    }
}

// =============================================================================

/**
 * Used in the GenericDS.
 **/
// =============================================================================

class Node<E> {
    // =============================================================================


    // =========================================================================

    // creates a generic variable
    E element;
    // creates a new Node
    Node<E> next;
    // =========================================================================

    /**
     * The constructor, which creates a new Node instance with default
     * values.
     **/

    public Node(E element) {
        this.element = element;
    }
}

