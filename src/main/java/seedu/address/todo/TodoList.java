package seedu.address.todo;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.todo.ReadOnlyTodo;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.UniqueTodoList;
import seedu.address.model.todo.UniqueTodoList.DuplicateTodoException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TodoList implements ReadOnlyTodoList {

    private final UniqueTodoList persons;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniqueTodoList();
        tags = new UniqueTagList();
    }

    public TodoList() {}

    /**
     * Creates an AddressBook using the Persons and Tags in the {@code toBeCopied}
     */
    public TodoList(ReadOnlyTodoList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

//// list overwrite operations

    public void setTodos(List<? extends ReadOnlyTodo> persons)
            throws UniqueTodoList.DuplicateTodoException {
        this.persons.setTodos(persons);
    }

    public void setTags(Collection<Tag> tags) throws UniqueTagList.DuplicateTagException {
        this.tags.setTags(tags);
    }

    public void resetData(ReadOnlyTodoList newData) {
        assert newData != null;
        try {
            setTodos(newData.getPersonList());
        } catch (UniqueTodoList.DuplicateTodoException e) {
            assert false : "AddressBooks should not have duplicate persons";
        }
        try {
            setTags(newData.getTagList());
        } catch (UniqueTagList.DuplicateTagException e) {
            assert false : "AddressBooks should not have duplicate tags";
        }
        syncMasterTagListWith(persons);
    }

//// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws UniqueTodoList.DuplicateTodoException if an equivalent person already exists.
     */
    public void addTodo(Todo p) throws UniqueTodoList.DuplicateTodoException {
        syncMasterTagListWith(p);
        persons.add(p);
    }

    /**
     * Updates the person in the list at position {@code index} with {@code editedReadOnlyTodo}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedReadOnlyTodo}.
     * @see #syncMasterTagListWith(Todo)
     *
     * @throws DuplicateTodoException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTodo(int index, ReadOnlyTodo editedReadOnlyTodo)
            throws UniqueTodoList.DuplicateTodoException {
        assert editedReadOnlyTodo != null;

        Todo editedPerson = new Todo(editedReadOnlyTodo);
        syncMasterTagListWith(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.updateTodo(index, editedPerson);
    }

    /**
     * Ensures that every tag in this person:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Todo person) {
        final UniqueTagList personTags = person.getTags();
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        person.setTags(new UniqueTagList(correctTagReferences));
    }

    /**
     * Ensures that every tag in these persons:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     *  @see #syncMasterTagListWith(Todo)
     */
    private void syncMasterTagListWith(UniqueTodoList persons) {
        persons.forEach(this::syncMasterTagListWith);
    }

    public boolean removePerson(ReadOnlyTodo key) throws UniqueTodoList.TodoNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new UniqueTodoList.TodoNotFoundException();
        }
    }

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyTodo> getPersonList() {
        return new UnmodifiableObservableList<>(persons.asObservableList());
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return new UnmodifiableObservableList<>(tags.asObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TodoList // instanceof handles nulls
                && this.persons.equals(((TodoList) other).persons)
                && this.tags.equalsOrderInsensitive(((TodoList) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
}
