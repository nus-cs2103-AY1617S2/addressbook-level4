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
 * A list of categories that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Category#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueCategoryList implements Iterable<Category> {

    private final ObservableList<Category> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty CategoryList.
     */
    public UniqueCategoryList() {}

    /**
     * Creates a UniqueCategoryList using given String categories.
     * Enforces no nulls or duplicates.
     */
    public UniqueCategoryList(String... categories) throws DuplicateCategoryException, IllegalValueException {
        final List<Category> categoryList = new ArrayList<Category>();
        for (String category : categories) {
            categoryList.add(new Category(category));
        }
        setCategories(categoryList);
    }

    /**
     * Creates a UniqueCategoryList using given categories.
     * Enforces no nulls or duplicates.
     */
    public UniqueCategoryList(Category... categories) throws DuplicateCategoryException {
        assert !CollectionUtil.isAnyNull((Object[]) categories);
        final List<Category> initialCategories = Arrays.asList(categories);
        if (!CollectionUtil.elementsAreUnique(initialCategories)) {
            throw new DuplicateCategoryException();
        }
        internalList.addAll(initialCategories);
    }

    /**
     * Creates a UniqueCategoryList using given categories.
     * Enforces no null or duplicate elements.
     */
    public UniqueCategoryList(Collection<Category> categories) throws DuplicateCategoryException {
        this();
        setCategories(categories);
    }

    /**
     * Creates a UniqueCategoryList using given categories.
     * Enforces no nulls.
     */
    public UniqueCategoryList(Set<Category> categories) {
        assert !CollectionUtil.isAnyNull(categories);
        internalList.addAll(categories);
    }

    /**
     * Creates a copy of the given list.
     * Insulates from changes in source.
     */
    public UniqueCategoryList(UniqueCategoryList source) {
        internalList.addAll(source.internalList); // insulate internal list from changes in argument
    }

    /**
     * Returns all categories in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Category> toSet() {
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Categories in this list with those in the argument category list.
     */
    public void setCategories(UniqueCategoryList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setCategories(Collection<Category> categories) throws DuplicateCategoryException {
        assert !CollectionUtil.isAnyNull(categories);
        if (!CollectionUtil.elementsAreUnique(categories)) {
            throw new DuplicateCategoryException();
        }
        internalList.setAll(categories);
    }

    /**
     * Ensures every category in the argument list exists in this object.
     */
    public void mergeFrom(UniqueCategoryList from) {
        final Set<Category> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(category -> !alreadyInside.contains(category))
                .forEach(internalList::add);
    }

    /**
     * Returns true if the list contains an equivalent Category as the given argument.
     */
    public boolean contains(Category toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Category to the list.
     *
     * @throws DuplicateCategoryException if the Category to add is a duplicate of an existing Category in the list.
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
            super("Operation would result in duplicate categories");
        }
    }

}
