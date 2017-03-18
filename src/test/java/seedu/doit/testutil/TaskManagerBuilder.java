package seedu.doit.testutil;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.model.TaskManager;
import seedu.doit.model.item.Event;
import seedu.doit.model.item.FloatingTask;
import seedu.doit.model.item.Task;
import seedu.doit.model.item.UniqueTaskList;
import seedu.doit.model.tag.Tag;

/**
 * A utility class to help with building TaskManager objects.
 * Example usage: <br>
 * {@code TaskManager ab = new TaskManagerBuilder().withTask("John", "Doe").withTag("Friend").build();}
 */
public class TaskManagerBuilder {

    private TaskManager taskManager;

    public TaskManagerBuilder(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public TaskManagerBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        this.taskManager.addTask(task);
        return this;
    }

    public TaskManagerBuilder withEvent(Event event) {//throws UniqueTaskList.DuplicateTaskException {
        this.taskManager.addEvent(event);
        return this;
    }

    public TaskManagerBuilder withFloatingTask(FloatingTask task) {//throws UniqueTaskList.DuplicateTaskException {
        this.taskManager.addFloatingTask(task);
        return this;
    }


    public TaskManagerBuilder withTag(String tagName) throws IllegalValueException {
        this.taskManager.addTag(new Tag(tagName));
        return this;
    }

    public TaskManager build() {
        return this.taskManager;
    }
}
