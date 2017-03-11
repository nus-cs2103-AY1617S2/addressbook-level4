package savvytodo.testutil;

import savvytodo.model.category.UniqueCategoryList;
import savvytodo.model.task.Description;
import savvytodo.model.task.Location;
import savvytodo.model.task.Name;
import savvytodo.model.task.Priority;
import savvytodo.model.task.ReadOnlyTask;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Description email;
    private Location location;
    private Priority priority;
    private UniqueCategoryList categories;

    public TestTask() {
        categories = new UniqueCategoryList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.priority = taskToCopy.getPriority();
        this.email = taskToCopy.getDescription();
        this.location = taskToCopy.getLocation();
        this.categories = taskToCopy.getCategories();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setAddress(Location address) {
        this.location = address;
    }

    public void setEmail(Description email) {
        this.email = email;
    }

    public void setPhone(Priority phone) {
        this.priority = phone;
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
        return email;
    }

    @Override
    public Location getLocation() {
        return location;
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
        sb.append("add " + this.getName().fullName + " ");
        sb.append("l/" + this.getLocation().value + " ");
        sb.append("p/" + this.getPriority().value + " ");
        sb.append("d/" + this.getDescription().value + " ");
        this.getCategories().asObservableList().stream().forEach(s -> sb.append("c/" + s.categoryName + " "));
        return sb.toString();
    }
}
