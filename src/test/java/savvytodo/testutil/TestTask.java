package savvytodo.testutil;

import savvytodo.model.category.UniqueCategoryList;
import savvytodo.model.task.DateTime;
import savvytodo.model.task.Description;
import savvytodo.model.task.Location;
import savvytodo.model.task.Name;
import savvytodo.model.task.Priority;
import savvytodo.model.task.ReadOnlyTask;
import savvytodo.model.task.Recurrence;
import savvytodo.model.task.Status;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Description description;
    private Location location;
    private Priority priority;
    private DateTime dateTime;
    private Recurrence recurrence;
    private UniqueCategoryList categories;
    private Status isCompleted;

    public TestTask() {
        categories = new UniqueCategoryList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.priority = taskToCopy.getPriority();
        this.dateTime = taskToCopy.getDateTime();
        this.description = taskToCopy.getDescription();
        this.location = taskToCopy.getLocation();
        this.categories = taskToCopy.getCategories();
        this.isCompleted = taskToCopy.isCompleted();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setCategories(UniqueCategoryList categories) {
        this.categories = categories;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    public void setDateTime(DateTime dateTime) {
        assert dateTime != null;
        this.dateTime = dateTime;
    }

    @Override
    public DateTime getDateTime() {
        return dateTime;
    }

    public void setRecurrence(Recurrence recurrence) {
        assert recurrence != null;
        this.recurrence = recurrence;
    }

    @Override
    public Recurrence getRecurrence() {
        return recurrence;
    }

    @Override
    public Status isCompleted() {
        return isCompleted;
    }

    public void setCompleted(Status isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Override
    public UniqueCategoryList getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().name + " ");
        sb.append("l/" + this.getLocation().value + " ");
        sb.append("p/" + this.getPriority().value + " ");
        sb.append("dt/" + this.getDateTime().startValue + DateTime.DATETIME_STRING_CONNECTOR
                + this.getDateTime().endValue + " ");
        sb.append("d/" + this.getDescription().value + " ");
        this.getCategories().asObservableList().stream().forEach(s -> sb.append("c/" + s.categoryName + " "));
        return sb.toString();
    }
}
