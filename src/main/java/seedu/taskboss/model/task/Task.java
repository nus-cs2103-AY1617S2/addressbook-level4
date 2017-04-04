package seedu.taskboss.model.task;

import java.util.Objects;

import seedu.taskboss.commons.util.CollectionUtil;
import seedu.taskboss.model.category.UniqueCategoryList;

/**
 * Represents a Task in the TaskBoss.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private PriorityLevel priorityLevel;
    private Information information;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private Recurrence recurrence;

    private UniqueCategoryList categories;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, PriorityLevel priorityLevel, DateTime startDateTime,
            DateTime endDateTime, Information information, Recurrence recurrence,
            UniqueCategoryList categories) {
        assert !CollectionUtil.isAnyNull(name, priorityLevel, startDateTime,
                endDateTime, information, recurrence, categories);

        this.name = name;
        this.priorityLevel = priorityLevel;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.information = information;
        this.recurrence = recurrence;
        // protect internal categories from changes in the arg list
        this.categories = new UniqueCategoryList(categories);
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(),
                source.getPriorityLevel(), source.getStartDateTime(),
                source.getEndDateTime(), source.getInformation(), source.getRecurrence(),
                source.getCategories());
    }

    //@@author A0144904H
    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    //@@author A0144904H
    @Override
    public Name getName() {
        return name;
    }

    //@@author A0144904H
    public void setPriorityLevel(PriorityLevel priorityLevel) {
        assert priorityLevel != null;
        this.priorityLevel = priorityLevel;
    }

    //@@author A0144904H
    @Override
    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    //@@author A0143157J
    public void setStartDateTime(DateTime startDateTime) {
        assert startDateTime != null;
        this.startDateTime = startDateTime;
    }

    @Override
    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public void setEndDateTime(DateTime endDateTime) {
        assert endDateTime != null;
        this.endDateTime = endDateTime;
    }

    @Override
    public DateTime getEndDateTime() {
        return endDateTime;
    }

    //@@author
    public void setInformation(Information information) {
        assert information != null;
        this.information = information;
    }

    @Override
    public Information getInformation() {
        return information;
    }

    //@@author A0143157J
    public void setRecurrence(Recurrence recurrence) {
        assert recurrence != null;
        this.recurrence = recurrence;
    }

    @Override
    public Recurrence getRecurrence() {
        return this.recurrence;
    }

    @Override
    public boolean isRecurring() {
        return this.recurrence.isRecurring();
    }

    //@@author
    @Override
    public UniqueCategoryList getCategories() {
        return new UniqueCategoryList(categories);
    }

    /**
     * Replaces this task's categories with the categories in the argument category list.
     */
    public void setCategories(UniqueCategoryList replacement) {
        categories.setCategories(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setPriorityLevel(replacement.getPriorityLevel());
        this.setStartDateTime(replacement.getStartDateTime());
        this.setEndDateTime(replacement.getEndDateTime());
        this.setInformation(replacement.getInformation());
        this.setRecurrence(replacement.getRecurrence());
        this.setCategories(replacement.getCategories());
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
        return Objects.hash(name, priorityLevel, startDateTime, endDateTime, information,
                recurrence, categories);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
