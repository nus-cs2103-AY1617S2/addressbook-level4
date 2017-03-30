package seedu.address.model.datastructure;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import seedu.address.logic.autocomplete.AutocompleteDataStructure;

//@@author A0140042A
/**
 * Data structure used to store auto complete data
 */
public class Trie implements AutocompleteDataStructure {
    private TrieNode rootNode;

    /**
     * Initializes a new Trie data structure with an empty root node
     */
    public Trie() {
        super();
        rootNode = new TrieNode(' ');
    }

    /**
     * Loads phrases used for auto completion into the Trie
     * @param phrases
     */
    @Override
    public void load(String... phrases) {
        for (String phrase : phrases) {
            loadRecursive(rootNode, phrase + "$");
        }
    }

    /**
     * Loads phrases character by character into the Trie
     * @param node - root to add the phrase from
     * @param phrase - phrase to add
     */
    private void loadRecursive(TrieNode node, String phrase) {
        if (StringUtils.isBlank(phrase)) {
            return;
        }
        char firstChar = phrase.charAt(0);
        node.add(firstChar);
        TrieNode childNode = node.getChildNode(firstChar);
        if (childNode != null) {
            loadRecursive(childNode, phrase.substring(1));
        }
    }

    /**
     * Checks recursively if each node contains a specific prefix
     * @param node - The node to check
     * @param prefix - the string to match
     * @return true if the node or any of its children contains the prefix
     */
    private TrieNode matchPrefixRecursive(TrieNode node, String prefix) {
        if (StringUtils.isBlank(prefix)) {
            return node;
        }
        char firstChar = prefix.charAt(0);
        TrieNode childNode = node.getChildNode(firstChar);
        if (childNode == null) {
            // no match at this char, exit
            return null;
        } else {
            // go deeper
            return matchPrefixRecursive(childNode, prefix.substring(1));
        }
    }

    /**
     * Finds a list of completions that can be made using the current prefix
     * @param prefix - to match against
     * @return a list of strings with auto completed suggestions
     */
    @Override
    public List<String> findCompletions(String prefix) {
        TrieNode matchedNode = matchPrefixRecursive(rootNode, prefix);
        List<String> completions = new LinkedList<String>();
        findCompletionsRecursive(matchedNode, prefix, completions);
        return completions;
    }

    /**
     * Finds a list of completions that can be made using the current prefix recursively
     * @param node - current node to check
     * @param prefix - to match against
     * @param completions - List of completions that are currently matched
     */
    private void findCompletionsRecursive(TrieNode node, String prefix, List<String> completions) {
        if (node == null) {
            // our prefix did not match anything, just return
            return;
        }
        if (node.getNodeValue() == '$') {
            /*
             * end reached, append prefix into completions list. Do not append
             * the trailing $, that is only to distinguish words like ann and
             * anne into separate branches of the tree.
             */
            completions.add(prefix.substring(0, prefix.length() - 1));
            return;
        }
        Collection<TrieNode> childNodes = node.getChildren();
        for (TrieNode childNode : childNodes) {
            char childChar = childNode.getNodeValue();
            findCompletionsRecursive(childNode, prefix + childChar, completions);
        }
    }
}
