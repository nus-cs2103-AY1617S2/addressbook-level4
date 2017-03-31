package seedu.address.model;

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
    
    private int sizePrev(Node source) {
        return source.getPrevious() == null ? 0 : 1 + sizePrev(source.getPrevious());
    }
    
    private int sizeNext(Node source) {
        return source.getNext() == null ? 0 : 1 + sizePrev(source.getNext());
    }
    
    /**
     * Double linked list node
     * @param <T> Element type to store
     */
    private class Node<T> {
        
        private T element;
        
        private Node<T> prev = null;
        private Node<T> next = null;
        
        public Node(T element) {
            this.element = element;
        }
        
        public T getElement() {
            return element;
        }
        
        public Node<T> getPrevious() {
            return prev;
        }
        
        public Node<T> getNext() {
            return next;
        }
        
        public void setNext(Node<T> next) {
            this.next = next;
        }
        
        public void setPrevious(Node<T> prev) {
            this.prev = prev;
        }
        
    }
}
