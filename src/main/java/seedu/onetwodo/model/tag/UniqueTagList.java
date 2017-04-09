package seedu.onetwodo.model.tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.emory.mathcs.backport.java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.onetwodo.commons.core.UnmodifiableObservableList;
import seedu.onetwodo.commons.exceptions.DuplicateDataException;
import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.commons.util.CollectionUtil;

/**
 * A list of tags that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Tag#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTagList implements Iterable<Tag> {

    private final ObservableList<Tag> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TagList.
     */
    public UniqueTagList() {}

    /**
     * Creates a UniqueTagList using given String tags.
     * Enforces no nulls or duplicates.
     */
    public UniqueTagList(String... tags) throws DuplicateTagException, IllegalValueException {
        final List<Tag> tagList = new ArrayList<Tag>();
        for (String tag : tags) {
            tagList.add(new Tag(tag));
        }
        setTags(tagList);
    }

    /**
     * Creates a UniqueTagList using given tags.
     * Enforces no nulls or duplicates.
     */
    public UniqueTagList(Tag... tags) throws DuplicateTagException {
        assert !CollectionUtil.isAnyNull((Object[]) tags);
        final List<Tag> initialTags = Arrays.asList(tags);
        if (!CollectionUtil.elementsAreUnique(initialTags)) {
            throw new DuplicateTagException();
        }
        Collections.sort(initialTags);
        internalList.addAll(initialTags);
    }

    /**
     * Creates a UniqueTagList using given tags.
     * Enforces no null or duplicate elements.
     */
    public UniqueTagList(Collection<Tag> tags) throws DuplicateTagException {
        this();
        setTags(tags);
    }

    /**
     * Creates a UniqueTagList using given tags.
     * Enforces no nulls.
     */
    public UniqueTagList(Set<Tag> tags) {
        assert !CollectionUtil.isAnyNull(tags);
        internalList.addAll(tags);
        Collections.sort(internalList);
    }

    /**
     * Creates a copy of the given list.
     * Insulates from changes in source.
     */
    public UniqueTagList(UniqueTagList source) {
        internalList.addAll(source.internalList); // insulate internal list from changes in argument
        Collections.sort(internalList);
    }

    /**
     * Returns all tags in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Tag> toSet() {
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Tags in this list with those in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        this.internalList.setAll(replacement.internalList);
        Collections.sort(internalList);
    }

    public void setTags(Collection<Tag> tags) throws DuplicateTagException {
        assert !CollectionUtil.isAnyNull(tags);
        if (!CollectionUtil.elementsAreUnique(tags)) {
            throw new DuplicateTagException();
        }
        internalList.setAll(tags);
        Collections.sort(internalList);
    }

    /**
     * Ensures every tag in the argument list exists in this object.
     */
    public void mergeFrom(UniqueTagList from) {
        final Set<Tag> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(tag -> !alreadyInside.contains(tag))
                .forEach(internalList::add);
        Collections.sort(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Tag as the given argument.
     */
    public boolean contains(Tag toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Tag to the list.
     *
     * @throws DuplicateTagException if the Tag to add is a duplicate of an existing Tag in the list.
     */
    public void add(Tag toAdd) throws DuplicateTagException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTagException();
        }
        internalList.add(toAdd);
        Collections.sort(internalList);
    }

    @Override
    public Iterator<Tag> iterator() {
        return internalList.iterator();
    }

    public UnmodifiableObservableList<Tag> asObservableList() {
        return new UnmodifiableObservableList<>(internalList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTagList // instanceof handles nulls
                && this.internalList.sorted().equals(
                ((UniqueTagList) other).internalList.sorted()));
    }

    public boolean equalsOrderInsensitive(UniqueTagList other) {
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    public boolean hasTag() {
        return !internalList.isEmpty();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTagException extends DuplicateDataException {
        protected DuplicateTagException() {
            super("Operation would result in duplicate tags");
        }
    }

    public UniqueTagList sort() {
        Collections.sort(internalList);
        return this;
    }

    //@@author A0139343E
    /**
     * Return a string of tags combined together
     */
    public String combineTagString() {
        StringBuilder sb = new StringBuilder();
        for (Tag tag : internalList) {
            sb.append(tag.tagName + " ");
        }
        return sb.toString().trim();
    }

    public void shrinkTagList (Tag tag) {
    }
}
