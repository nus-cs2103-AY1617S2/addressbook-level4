package seedu.address.model.datastructure;

import java.util.HashSet;
import java.util.Set;

//@@author A0162877N
/**
 * This class will handle the partial search algorithm and the search function itself
 */
public class PartialSearch {

    private final Set<String> infixes = new HashSet<>();

    private String itemToSearch;

    public PartialSearch(String itemToSearch) {
        this.itemToSearch = itemToSearch;
        prepareSearch();
    }

    /**
     * This method prepares the infix set for search
     */
    public void prepareSearch() {
        for (int i = 0; i < itemToSearch.length(); i++) {
            for (int j = i + 1; j <= itemToSearch.length(); j++) {
                infixes.add(itemToSearch.substring(i, j).toLowerCase());
            }
        }
    }

    /**
     * This method does the search by matching the hash set for match
     * @param searchString
     * @return true if exist and false if otherwise
     */
    public boolean search(String searchString) { // O(1) search
        return infixes.contains(searchString.toLowerCase());
    }
}
