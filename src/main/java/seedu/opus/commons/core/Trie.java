package seedu.opus.commons.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//@@author A0124368A
/**
 * Java implementation of Trie prefix tree for autocompletion. More information for how a Trie works can be found here:
 * https://medium.com/algorithms/trie-prefix-tree-algorithm-ee7ab3fe3413
 *
 * @author xbili (Credits to Marcus McCurdy)
 *
 */
public class Trie {

    protected final Map<Character, Trie> children;
    protected String value;
    protected boolean terminal = false;

    /** Creates an empty Trie. */
    public Trie() {
        this(null);
    }

    /** Creates a Trie with a default value. */
    private Trie(String value) {
        this.value = value;
        children = new HashMap<Character, Trie>();
    }

    /**
     * Adds a character into the prefix tree.
     * @param c - character to be inserted into the Trie
     */
    protected void add(char c) {
        String val;
        if (this.value == null) {
            val = Character.toString(c);
        } else {
            val = this.value + c;
        }

        children.put(c, new Trie(val));
    }

    /**
     * Inserts a string into the prefix tree.
     * @param word - String to be inserted into prefix tree
     */
    public void insert(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Cannot add null to a Trie");
        }

        Trie node = this;
        for (char c : word.toCharArray()) {
            if (!node.children.containsKey(c)) {
                node.add(c);
            }
            node = node.children.get(c);
        }

        node.terminal = true;
    }

    /**
     * Finds a word in the prefix tree.
     * @param word - string to find in the prefix tree
     * @return the word if found, else an empty string
     */
    public String find(String word) {
        Trie node = this;
        for (char c : word.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return "";
            }
            node = node.children.get(c);
        }
        return node.value;
    }

    /**
     * @param prefix
     * @return Collection of all the matching words that should be part of the autocomplete.
     */
    public Collection<String> autoComplete(String prefix) {
        Trie node = this;
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return Collections.emptyList();
            }
            node = node.children.get(c);
        }

        return node.getAllPrefixes();
    }

    /**
     * @return Collection of all the matching prefixes of the current node in the Trie.
     */
    protected Collection<String> getAllPrefixes() {
        List<String> results = new ArrayList<String>();
        if (this.terminal) {
            results.add(this.value);
        }

        for (Entry<Character, Trie> entry : children.entrySet()) {
            Trie child = entry.getValue();
            Collection<String> childPrefixes = child.getAllPrefixes();
            results.addAll(childPrefixes);
        }

        return results;
    }

}
//@@author
