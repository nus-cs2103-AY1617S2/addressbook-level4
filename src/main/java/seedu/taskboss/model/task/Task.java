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

    private UniqueCategoryList categories;
    
    private static final String EMPTY_STRING = "";

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, PriorityLevel priorityLevel, DateTime startDateTime,
            DateTime endDateTime, Information information, UniqueCategoryList categories) {
        assert !CollectionUtil.isAnyNull(name, priorityLevel, startDateTime,
                endDateTime, information, categories);

        this.name = name;
        this.priorityLevel = priorityLevel;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.information = information;
        // protect internal categories from changes in the arg list
        this.categories = new UniqueCategoryList(categories);
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getPriorityLevel(), source.getStartDateTime(),
                source.getEndDateTime(), source.getInformation(), source.getCategories());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setPriorityLevel(PriorityLevel priorityLevel) {
        assert priorityLevel != null;
        this.priorityLevel = priorityLevel;
    }

    @Override
    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

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

    public void setInformation(Information information) {
        assert information != null;
        this.information = information;
    }

    @Override
    public Information getInformation() {
        return information;
    }

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
     * Updates this person with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setPriorityLevel(replacement.getPriorityLevel());
        this.setStartDateTime(replacement.getStartDateTime());
        this.setEndDateTime(replacement.getEndDateTime());
        this.setInformation(replacement.getInformation());
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
        return Objects.hash(name, priorityLevel, startDateTime, endDateTime, information, categories);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public int compareTo(ReadOnlyTask o) {
        if (this.endDateTime.getDate().equals(EMPTY_STRING) &&
                o.getEndDateTime().getDate().equals(EMPTY_STRING)) {
            return 0;
        } else if (this.endDateTime.getDate().equals(EMPTY_STRING)) {
            return -1;
        } else if(o.getEndDateTime().getDate().equals(EMPTY_STRING)) {
            return 1;
        } else {
            return this.endDateTime.getDate().compareTo(o.getEndDateTime().getDate());
        }
    }

}
