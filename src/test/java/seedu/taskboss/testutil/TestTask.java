package seedu.taskboss.testutil;

import seedu.taskboss.model.category.UniqueCategoryList;
import seedu.taskboss.model.task.Information;
import seedu.taskboss.model.task.Name;
import seedu.taskboss.model.task.PriorityLevel;
import seedu.taskboss.model.task.ReadOnlyTask;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Information information;
    private PriorityLevel priorityLevel;
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
        this.information = taskToCopy.getInformation();
        this.categories = taskToCopy.getCategories();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public void setPriorityLevel(PriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
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
    public Information getInformation() {
        return information;
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
        sb.append("i/" + this.getInformation().value + " ");
        sb.append("p/" + this.getPriorityLevel().value + " ");
        this.getCategories().asObservableList().stream().forEach(s -> sb.append("t/" + s.categoryName + " "));
        return sb.toString();
    }
}
