package seedu.address.model.datastructure;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

//@@author A0140042A
/**
 * Nodes that are contained in data structure {@link Trie}
 */
public class TrieNode {

    private Character character;
    private HashMap<Character, TrieNode> children;

    public TrieNode(char c) {
        super();
        this.character = new Character(c);
        children = new HashMap<Character, TrieNode>();
    }

    /**
     * Gets the value of the node
     */
    public char getNodeValue() {
        return character.charValue();
    }

    /**
     * Get the children of the current node
     */
    public Collection<TrieNode> getChildren() {
        return children.values();
    }

    /**
     * Gets the values of the children of the current node
     */
    public Set<Character> getChildrenNodeValues() {
        return children.keySet();
    }

    /**
     * Adds a child to the current node
     */
    public void add(char c) {
        if (children.get(new Character(c)) == null) {
            // children does not contain c, add a TrieNode
            children.put(new Character(c), new TrieNode(c));
        }
    }

    /**
     * Gets a child node
     * @param c - the key whose associated value is to be returned
     * @return - the value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    public TrieNode getChildNode(char c) {
        return children.get(new Character(c));
    }

    /**
     * Checks if the child has such a character
     * @param c - the key whose associated value is to be returned
     * @return - true if there is a match
     */
    public boolean contains(char c) {
        return (children.get(new Character(c)) != null);
    }

    @Override
    public int hashCode() {
        return character.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TrieNode)) {
            return false;
        }
        TrieNode that = (TrieNode) obj;
        return (this.getNodeValue() == that.getNodeValue());
    }
}
