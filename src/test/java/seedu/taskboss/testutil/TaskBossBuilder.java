package seedu.taskboss.testutil;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.TaskBoss;
import seedu.taskboss.model.category.Tag;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.model.task.UniqueTaskList;

/**
 * A utility class to help with building TaskBoss objects.
 * Example usage: <br>
 *     {@code TaskBoss tb = new TaskBossBuilder().withTask("John", "Doe").withTag("Friend").build();}
 */
public class TaskBossBuilder {

    private TaskBoss taskBoss;

    public TaskBossBuilder(TaskBoss taskBoss) {
        this.taskBoss = taskBoss;
    }

    public TaskBossBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskBoss.addTask(task);
        return this;
    }

    public TaskBossBuilder withTag(String tagName) throws IllegalValueException {
        taskBoss.addTag(new Tag(tagName));
        return this;
    }

    public TaskBoss build() {
        return taskBoss;
    }
}
