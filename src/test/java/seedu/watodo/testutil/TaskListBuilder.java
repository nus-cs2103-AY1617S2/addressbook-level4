package seedu.watodo.testutil;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.model.TaskManager;
import seedu.watodo.model.tag.Tag;
import seedu.watodo.model.task.Task;
import seedu.watodo.model.task.UniqueTaskList;

/**
 * A utility class to help with building taskManager objects.
 */
public class TaskListBuilder {

    private TaskManager taskList;

    public TaskListBuilder(TaskManager taskList) {
        this.taskList = taskList;
    }

    public TaskListBuilder withPerson(Task person) throws UniqueTaskList.DuplicateTaskException {
        taskList.addTask(person);
        return this;
    }

    public TaskListBuilder withTag(String tagName) throws IllegalValueException {
        taskList.addTag(new Tag(tagName));
        return this;
    }

    public TaskManager build() {
        return taskList;
    }
}
