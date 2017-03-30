package seedu.address.model;

public class UndoStack {
    
    private Node current = null;
    
    public YTomorrow undo() {
        if (current == null) {
            return null;
            }
        
        Node prev = current.getPrevious();
        if (prev == null) {
            return null;
        } else {
            current = prev;
            return prev.getElement();
        }
    }
    
    public YTomorrow redo() {
        if (current == null) {
            return null;
        }
        
        Node next = current.getNext();
        if (next == null) {
            return null;
        } else {
            current = next;
            return next.getElement();
        }
    }
    
    public YTomorrow at() {
        if (current != null) {
            return current.getElement();
        } else {
            return null;
        }
    }
    
    public void push(YTomorrow next) {
        Node nextNode = new Node(next);
        
        if (current != null) {
            nextNode.setPrevious(current);
            current.setNext(nextNode);
        }
        
        current = nextNode;
    }
    
    public int size() {
        return 1 + sizePrev(current) + sizeNext(current);
    }
    
    private int sizePrev(Node source) {
        return source.getPrevious() == null ? 0 : 1 + sizePrev(source.getPrevious());
    }
    
    private int sizeNext(Node source) {
        return source.getNext() == null ? 0 : 1 + sizePrev(source.getNext());
    }
    
    private class Node {
        
        private YTomorrow element;
        
        private Node prev = null;
        private Node next = null;
        
        public Node(YTomorrow element) {
            this.element = element;
        }
        
        public YTomorrow getElement() {
            return element;
        }
        
        public Node getPrevious() {
            return prev;
        }
        
        public Node getNext() {
            return next;
        }
        
        public void setNext(Node next) {
            this.next = next;
        }
        
        public void setPrevious(Node prev) {
            this.prev = prev;
        }
        
    }
}
