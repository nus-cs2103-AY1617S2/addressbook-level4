package seedu.ezdo.model.todo;

import java.util.Objects;

import seedu.ezdo.commons.util.CollectionUtil;
import seedu.ezdo.model.tag.UniqueTagList;

/**
 * Represents a Task in ezDo.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Priority priority;
    private Email email;
    private StartDate startDate;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
<<<<<<< HEAD:src/main/java/seedu/ezdo/model/todo/Person.java
    public Person(Name name, Priority priority, Email email, Address address, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, priority, email, address, tags);
=======
    public Task(Name name, Phone phone, Email email, StartDate startDate, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, phone, email, startDate, tags);
>>>>>>> 6228ae6b03115b0e64fed2b189d86d8b94d2fa7e:src/main/java/seedu/ezdo/model/todo/Task.java
        this.name = name;
        this.priority = priority;
        this.email = email;
        this.startDate = startDate;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
<<<<<<< HEAD:src/main/java/seedu/ezdo/model/todo/Person.java
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPriority(), source.getEmail(), source.getAddress(), source.getTags());
=======
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getStartDate(), source.getTags());
>>>>>>> 6228ae6b03115b0e64fed2b189d86d8b94d2fa7e:src/main/java/seedu/ezdo/model/todo/Task.java
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setPriority(Priority priority) {
        assert priority != null;
        this.priority = priority;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setEmail(Email email) {
        assert email != null;
        this.email = email;
    }

    @Override
    public Email getEmail() {
        return email;
    }

    public void setStartDate(StartDate startDate) {
        assert startDate != null;
        this.startDate = startDate;
    }

    @Override
    public StartDate getStartDate() {
        return startDate;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setPriority(replacement.getPriority());
        this.setEmail(replacement.getEmail());
        this.setStartDate(replacement.getStartDate());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
<<<<<<< HEAD:src/main/java/seedu/ezdo/model/todo/Person.java
        return Objects.hash(name, priority, email, address, tags);
=======
        return Objects.hash(name, phone, email, startDate, tags);
>>>>>>> 6228ae6b03115b0e64fed2b189d86d8b94d2fa7e:src/main/java/seedu/ezdo/model/todo/Task.java
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
