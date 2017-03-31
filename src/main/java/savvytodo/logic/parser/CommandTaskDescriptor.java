package savvytodo.logic.parser;

import java.util.Optional;

import savvytodo.commons.util.CollectionUtil;
import savvytodo.model.category.UniqueCategoryList;
import savvytodo.model.task.DateTime;
import savvytodo.model.task.Description;
import savvytodo.model.task.Location;
import savvytodo.model.task.Name;
import savvytodo.model.task.Priority;
import savvytodo.model.task.Recurrence;
import savvytodo.model.task.Status;

/**
 * @author A0140016B
 * Stores the details to edit the task with. Each non-empty field value will
 * replace the corresponding field value of the task.
 */
public class CommandTaskDescriptor {

    private Optional<Name> name = Optional.empty();
    private Optional<Priority> priority = Optional.empty();
    private Optional<Description> description = Optional.empty();
    private Optional<Location> location = Optional.empty();
    private Optional<UniqueCategoryList> categories = Optional.empty();
    private Optional<DateTime> dateTime = Optional.empty();
    private Optional<Recurrence> recurrence = Optional.empty();
    private Optional<Status> status = Optional.empty();

    public CommandTaskDescriptor() {
    }

    public CommandTaskDescriptor(CommandTaskDescriptor toCopy) {
        this.name = toCopy.getName();
        this.priority = toCopy.getPriority();
        this.description = toCopy.getDescription();
        this.location = toCopy.getLocation();
        this.categories = toCopy.getCategories();
        this.status = toCopy.getStatus();
        this.dateTime = toCopy.getDateTime();
        this.recurrence = toCopy.getRecurrence();
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldPresent() {
        return CollectionUtil.isAnyPresent(this.name, this.priority, this.description, this.location, this.categories,
                this.dateTime, this.recurrence);
    }

    public void setName(Optional<Name> name) {
        assert name != null;
        this.name = name;
    }

    public Optional<Name> getName() {
        return name;
    }

    public void setPriority(Optional<Priority> priority) {
        assert priority != null;
        this.priority = priority;
    }

    public Optional<Priority> getPriority() {
        return priority;
    }

    public void setDescription(Optional<Description> description) {
        assert description != null;
        this.description = description;
    }

    public Optional<Description> getDescription() {
        return description;
    }

    public void setLocation(Optional<Location> location) {
        assert location != null;
        this.location = location;
    }

    public Optional<Location> getLocation() {
        return location;
    }

    public void setDateTime(Optional<DateTime> dateTime) {
        assert dateTime != null;
        this.dateTime = dateTime;
    }

    public Optional<DateTime> getDateTime() {
        return dateTime;
    }

    public void setRecurrence(Optional<Recurrence> recurrence) {
        assert recurrence != null;
        this.recurrence = recurrence;
    }

    public Optional<Recurrence> getRecurrence() {
        return recurrence;
    }

    public void setStatus(Optional<Status> status) {
        assert status != null;
        this.status = status;
    }

    public Optional<Status> getStatus() {
        return status;
    }

    public void setCategories(Optional<UniqueCategoryList> categories) {
        assert categories != null;
        this.categories = categories;
    }

    public Optional<UniqueCategoryList> getCategories() {
        return categories;
    }

}
