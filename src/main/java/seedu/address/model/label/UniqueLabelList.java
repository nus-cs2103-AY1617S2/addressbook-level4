package seedu.address.model.label;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;

//@@author A0140042A
/**
 * A list of labels that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Label#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueLabelList implements Iterable<Label> {

    private final ObservableList<Label> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty LabelList.
     */
    public UniqueLabelList() {}

    /**
     * Creates a UniqueLabelList using given String labels. Enforces no nulls or
     * duplicates.
     */
    public UniqueLabelList(String... labels) throws DuplicateLabelException, IllegalValueException {
        final List<Label> labelList = new ArrayList<Label>();
        for (String label : labels) {
            labelList.add(new Label(label));
        }
        setLabels(labelList);
    }

    /**
     * Creates a UniqueLabelList using given labels. Enforces no nulls or
     * duplicates.
     */
    public UniqueLabelList(Label... labels) throws DuplicateLabelException {
        assert !CollectionUtil.isAnyNull((Object[]) labels);
        final List<Label> initialLabels = Arrays.asList(labels);
        if (!CollectionUtil.elementsAreUnique(initialLabels)) {
            throw new DuplicateLabelException();
        }
        internalList.addAll(initialLabels);
    }

    /**
     * Creates a UniqueLabelList using given labels. Enforces no null or
     * duplicate elements.
     */
    public UniqueLabelList(Collection<Label> labels) throws DuplicateLabelException {
        this();
        setLabels(labels);
    }

    /**
     * Creates a UniqueLabelList using given labels. Enforces no nulls.
     */
    public UniqueLabelList(Set<Label> labels) {
        assert !CollectionUtil.isAnyNull(labels);
        internalList.addAll(labels);
    }

    /**
     * Creates a copy of the given list. Insulates from changes in source.
     */
    public UniqueLabelList(UniqueLabelList source) {
        internalList.addAll(source.internalList); // insulate internal list from
                                                  // changes in argument
    }

    /**
     * Returns all labels in this list as a Set. This set is mutable and
     * change-insulated against the internal list.
     */
    public Set<Label> toSet() {
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Tags in this list with those in the argument label list.
     */
    public void setLabels(UniqueLabelList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setLabels(Collection<Label> labels) throws DuplicateLabelException {
        assert !CollectionUtil.isAnyNull(labels);
        if (!CollectionUtil.elementsAreUnique(labels)) {
            throw new DuplicateLabelException();
        }
        internalList.setAll(labels);
    }

    /**
     * Removes all labels from the list. Warning, this cannot be undone!
     */
    public void removeAll() {
        internalList.clear();
    }

    @Override
    public UniqueLabelList clone() {
        UniqueLabelList labelList = new UniqueLabelList();
        try {

            for (Label label : internalList) {
                labelList.add(new Label(label.getLabelName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return labelList;
    }

    /**
     * Ensures every label in the argument list exists in this object.
     */
    public void mergeFrom(UniqueLabelList from) {
        final Set<Label> alreadyInside = this.toSet();
        from.internalList.stream().filter(label -> !alreadyInside.contains(label)).forEach(internalList::add);
    }

    /**
     * Returns true if the list contains an equivalent Label as the given
     * argument.
     */
    public boolean contains(Label toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Returns true if the list contains an equivalent Label as the given
     * argument.
     */
    public boolean containsStringLabel(String labelToCheck) {
        assert labelToCheck != null;
        // return internalList.contains(toCheck);
        for (Label label : internalList) {
            if (label.getLabelName().equalsIgnoreCase(labelToCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a Label to the list.
     *
     * @throws DuplicateLabelException
     *             if the Label to add is a duplicate of an existing Label in
     *             the list.
     */
    public void add(Label toAdd) throws DuplicateLabelException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateLabelException();
        }
        internalList.add(toAdd);
    }

    @Override
    public Iterator<Label> iterator() {
        return internalList.iterator();
    }

    public UnmodifiableObservableList<Label> asObservableList() {
        return new UnmodifiableObservableList<>(internalList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueLabelList // instanceof handles nulls
                        && this.internalList.equals(((UniqueLabelList) other).internalList));
    }

    public boolean equalsOrderInsensitive(UniqueLabelList other) {
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates'
     * property of the list.
     */
    public static class DuplicateLabelException extends DuplicateDataException {
        protected DuplicateLabelException() {
            super("Operation would result in duplicate labels");
        }
    }

}
