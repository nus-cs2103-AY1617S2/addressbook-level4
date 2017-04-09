package seedu.task.model;

//@@author A0163848R
/**
 * Data structure for undo/redo history
 * @param <T> State type to save
 */
public class History<T> {

    private Node<T> current = null;

    /**
     * Moves current state back one push.
     * @return State after undo
     */
    public T undo() {
        if (current == null) {
            return null;
        }

        Node<T> prev = current.getPrevious();
        if (prev == null) {
            return null;
        } else {
            current = prev;
            return prev.getElement();
        }
    }

    /**
     * Moves current state forward one push.
     * @return State after redo
     */
    public T redo() {
        if (current == null) {
            return null;
        }

        Node<T> next = current.getNext();
        if (next == null) {
            return null;
        } else {
            current = next;
            return next.getElement();
        }
    }

    /**
     * @return Current state
     */
    public T at() {
        if (current != null) {
            return current.getElement();
        } else {
            return null;
        }
    }

    /**
     * Adds value after current state.  Deletes the previous next value.
     * @param Value to add as next
     */
    public void push(T next) {
        Node<T> nextNode = new Node<T>(next);

        if (current != null) {
            nextNode.setPrevious(current);
            current.setNext(nextNode);
        }

        current = nextNode;
    }

    /**
     * @return Total number of saved states
     */
    public int size() {
        return 1 + sizePrev(current) + sizeNext(current);
    }

    /**
     * @param Source node to count from
     * @return Number of nodes before source node
     */
    private int sizePrev(Node<T> source) {
        return source.getPrevious() == null ? 0 : 1 + sizePrev(source.getPrevious());
    }

    /**
     * @param Source node to count from
     * @return Number of nodes after source node
     */
    private int sizeNext(Node<T> source) {
        return source.getNext() == null ? 0 : 1 + sizePrev(source.getNext());
    }

    /**
     * Doubly-linked-list node
     * @param <T> Element type to store
     */
    private class Node<T> {

        private T element;

        private Node<T> prev = null;
        private Node<T> next = null;

        public Node(T element) {
            this.element = element;
        }

        /**
         * @return Stored value
         */
        public T getElement() {
            return element;
        }

        /**
         * @return Previous node
         */
        public Node<T> getPrevious() {
            return prev;
        }

        /**
         * @return Next node
         */
        public Node<T> getNext() {
            return next;
        }

        /**
         * Set next node
         * @param Node to insert
         */
        public void setNext(Node<T> next) {
            this.next = next;
        }

        /**
         * Set previous node
         * @param Node to insert
         */
        public void setPrevious(Node<T> prev) {
            this.prev = prev;
        }

    }
}
