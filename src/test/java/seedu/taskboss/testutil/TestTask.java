package seedu.taskboss.testutil;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.category.UniqueCategoryList;
import seedu.taskboss.model.task.DateTime;
import seedu.taskboss.model.task.Information;
import seedu.taskboss.model.task.Name;
import seedu.taskboss.model.task.PriorityLevel;
import seedu.taskboss.model.task.ReadOnlyTask;
import seedu.taskboss.model.task.Recurrence;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Information information;
    private PriorityLevel priorityLevel;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private Recurrence recurrence;
    private UniqueCategoryList categories;

    public TestTask() {
        categories = new UniqueCategoryList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.priorityLevel = taskToCopy.getPriorityLevel();
        this.startDateTime = taskToCopy.getStartDateTime();
        this.endDateTime = taskToCopy.getEndDateTime();
        this.information = taskToCopy.getInformation();
        this.recurrence = taskToCopy.getRecurrence();
        this.categories = taskToCopy.getCategories();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
    }

    public void setPriorityLevel(PriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public void setStartDateTime(DateTime startDateTime) throws IllegalValueException {
        DateTime parsedStartDateTime = new DateTime(startDateTime.value);
        this.startDateTime = parsedStartDateTime;
    }

    public void setEndDateTime(DateTime endDateTime) throws IllegalValueException {
        DateTime parsedEndDateTime = new DateTime(endDateTime.value);
        this.endDateTime = parsedEndDateTime;
    }

    public void setCategories(UniqueCategoryList categories) {
        this.categories = categories;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    @Override
    public DateTime getStartDateTime() {
        return startDateTime;
    }

    @Override
    public DateTime getEndDateTime() {
        return endDateTime;
    }

    @Override
    public Information getInformation() {
        return information;
    }

    @Override
    public UniqueCategoryList getCategories() {
        return categories;
    }

    @Override
    public Recurrence getRecurrence() {
        return recurrence;
    }

    @Override
    public boolean isRecurring() {
        return recurrence.isRecurring();
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add ");
        sb.append(this.getName().toString());
        sb.append(" p/" + this.getPriorityLevel().input);
        sb.append("sd/" + this.getStartDateTime().value + " ");
        sb.append("ed/" + this.getEndDateTime().value + " ");
        sb.append("i/" + this.getInformation().value + " ");
        sb.append("r/" + this.getRecurrence().toString() + " ");
        this.getCategories().asObservableList().stream().forEach(s -> sb.append("c/" + s.categoryName + " "));
        return sb.toString();
    }

    //@@author A0144904H
    public String getAddCommandPlus() {
        StringBuilder sb = new StringBuilder();
        //@@author A0144904H
        sb.append("+ ");
        sb.append(this.getName().toString());
        sb.append(" p/" + this.getPriorityLevel().input);
        //@@author
        sb.append("sd/" + this.getStartDateTime().value + " ");
        sb.append("ed/" + this.getEndDateTime().value + " ");
        sb.append("i/" + this.getInformation().value + " ");
        sb.append("r/" + this.getRecurrence().toString() + " ");
        this.getCategories().asObservableList().stream().forEach(s -> sb.append("c/" + s.categoryName + " "));
        return sb.toString();
    }

    //@@author A0143157J
    public String getShortAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("a ");
        sb.append(this.getName().toString());
        sb.append(" p/" + this.getPriorityLevel().input);
        sb.append("sd/" + this.getStartDateTime().value + " ");
        sb.append("ed/" + this.getEndDateTime().value + " ");
        sb.append("i/" + this.getInformation().value + " ");
        sb.append("r/" + this.getRecurrence().toString() + " ");
        this.getCategories().asObservableList().stream().forEach(s -> sb.append("c/" + s.categoryName + " "));
        return sb.toString();
    }

}
