package savvytodo.model.category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import savvytodo.commons.core.UnmodifiableObservableList;
import savvytodo.commons.exceptions.DuplicateDataException;
import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.commons.util.CollectionUtil;

/**
 * A list of tags that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Category#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueCategoryList implements Iterable<Category> {

    private final ObservableList<Category> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TagList.
     */
    public UniqueCategoryList() {}

    /**
     * Creates a UniqueTagList using given String tags.
     * Enforces no nulls or duplicates.
     */
    public UniqueCategoryList(String... tags) throws DuplicateCategoryException, IllegalValueException {
        final List<Category> tagList = new ArrayList<Category>();
        for (String tag : tags) {
            tagList.add(new Category(tag));
        }
        setCategories(tagList);
    }

    /**
     * Creates a UniqueTagList using given tags.
     * Enforces no nulls or duplicates.
     */
    public UniqueCategoryList(Category... tags) throws DuplicateCategoryException {
        assert !CollectionUtil.isAnyNull((Object[]) tags);
        final List<Category> initialTags = Arrays.asList(tags);
        if (!CollectionUtil.elementsAreUnique(initialTags)) {
            throw new DuplicateCategoryException();
        }
        internalList.addAll(initialTags);
    }

    /**
     * Creates a UniqueTagList using given tags.
     * Enforces no null or duplicate elements.
     */
    public UniqueCategoryList(Collection<Category> tags) throws DuplicateCategoryException {
        this();
        setCategories(tags);
    }

    /**
     * Creates a UniqueTagList using given tags.
     * Enforces no nulls.
     */
    public UniqueCategoryList(Set<Category> tags) {
        assert !CollectionUtil.isAnyNull(tags);
        internalList.addAll(tags);
    }

    /**
     * Creates a copy of the given list.
     * Insulates from changes in source.
     */
    public UniqueCategoryList(UniqueCategoryList source) {
        internalList.addAll(source.internalList); // insulate internal list from changes in argument
    }

    /**
     * Returns all tags in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Category> toSet() {
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Tags in this list with those in the argument tag list.
     */
    public void setTags(UniqueCategoryList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setCategories(Collection<Category> tags) throws DuplicateCategoryException {
        assert !CollectionUtil.isAnyNull(tags);
        if (!CollectionUtil.elementsAreUnique(tags)) {
            throw new DuplicateCategoryException();
        }
        internalList.setAll(tags);
    }

    /**
     * Ensures every tag in the argument list exists in this object.
     */
    public void mergeFrom(UniqueCategoryList from) {
        final Set<Category> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(tag -> !alreadyInside.contains(tag))
                .forEach(internalList::add);
    }

    /**
     * Returns true if the list contains an equivalent Tag as the given argument.
     */
    public boolean contains(Category toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Tag to the list.
     *
     * @throws DuplicateCategoryException if the Tag to add is a duplicate of an existing Tag in the list.
     */
    public void add(Category toAdd) throws DuplicateCategoryException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateCategoryException();
        }
        internalList.add(toAdd);
    }

    @Override
    public Iterator<Category> iterator() {
        return internalList.iterator();
    }

    public UnmodifiableObservableList<Category> asObservableList() {
        return new UnmodifiableObservableList<>(internalList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueCategoryList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueCategoryList) other).internalList));
    }

    public boolean equalsOrderInsensitive(UniqueCategoryList other) {
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateCategoryException extends DuplicateDataException {
        protected DuplicateCategoryException() {
            super("Operation would result in duplicate tags");
        }
    }

}
