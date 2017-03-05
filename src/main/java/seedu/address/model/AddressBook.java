package seedu.address.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.label.Label;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.person.Task;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.UniquePersonList.DuplicatePersonException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueLabelList labels;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        labels = new UniqueLabelList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons and Labels in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setPersons(List<? extends ReadOnlyTask> persons)
            throws UniquePersonList.DuplicatePersonException {
        this.persons.setPersons(persons);
    }

    public void setLabels(Collection<Label> labels) throws UniqueLabelList.DuplicateLabelException {
        this.labels.setLabels(labels);
    }

    public void resetData(ReadOnlyAddressBook newData) {
        assert newData != null;
        try {
            setPersons(newData.getPersonList());
        } catch (UniquePersonList.DuplicatePersonException e) {
            assert false : "AddressBooks should not have duplicate persons";
        }
        try {
            setLabels(newData.getLabelList());
        } catch (UniqueLabelList.DuplicateLabelException e) {
            assert false : "AddressBooks should not have duplicate labels";
        }
        syncMasterLabelListWith(persons);
    }

    //// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's labels and updates {@link #labels} with any new labels found,
     * and updates the Label objects in the person to point to those in {@link #labels}.
     *
     * @throws UniquePersonList.DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Task p) throws UniquePersonList.DuplicatePersonException {
        syncMasterLabelListWith(p);
        persons.add(p);
    }

    /**
     * Updates the person in the list at position {@code index} with {@code editedReadOnlyPerson}.
     * {@code AddressBook}'s label list will be updated with the labels of {@code editedReadOnlyPerson}.
     * @see #syncMasterLabelListWith(Task)
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updatePerson(int index, ReadOnlyTask editedReadOnlyPerson)
            throws UniquePersonList.DuplicatePersonException {
        assert editedReadOnlyPerson != null;

        Task editedPerson = new Task(editedReadOnlyPerson);
        syncMasterLabelListWith(editedPerson);
        // TODO: the labels master list will be updated even though the below line fails.
        // This can cause the labels master list to have additional labels that are not labeled to any person
        // in the person list.
        persons.updatePerson(index, editedPerson);
    }

    /**
     * Ensures that every label in this person:
     *  - exists in the master list {@link #labels}
     *  - points to a {@link Label} object in the master list
     */
    private void syncMasterLabelListWith(Task person) {
        final UniqueLabelList personLabels = person.getLabels();
        labels.mergeFrom(personLabels);

        // Create map with values = label object references in the master list
        // used for checking person label references
        final Map<Label, Label> masterLabelObjects = new HashMap<>();
        labels.forEach(label -> masterLabelObjects.put(label, label));

        // Rebuild the list of person labels to point to the relevant labels in the master label list.
        final Set<Label> correctLabelReferences = new HashSet<>();
        personLabels.forEach(label -> correctLabelReferences.add(masterLabelObjects.get(label)));
        person.setLabels(new UniqueLabelList(correctLabelReferences));
    }

    /**
     * Ensures that every label in these persons:
     *  - exists in the master list {@link #labels}
     *  - points to a LAbel object in the master list
     *  @see #syncMasterLabelListWith(Task)
     */
    private void syncMasterLabelListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterLabelListWith);
    }

    public boolean removePerson(ReadOnlyTask key) throws UniquePersonList.PersonNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new UniquePersonList.PersonNotFoundException();
        }
    }

    //// label-level operations

    public void addLabel(Label t) throws UniqueLabelList.DuplicateLabelException {
        labels.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + labels.asObservableList().size() +  " labels";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyTask> getPersonList() {
        return new UnmodifiableObservableList<>(persons.asObservableList());
    }

    @Override
    public ObservableList<Label> getLabelList() {
        return new UnmodifiableObservableList<>(labels.asObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                        && this.persons.equals(((AddressBook) other).persons)
                        && this.labels.equalsOrderInsensitive(((AddressBook) other).labels));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, labels);
    }
}
