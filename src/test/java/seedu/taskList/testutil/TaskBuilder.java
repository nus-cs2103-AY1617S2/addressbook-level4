package seedu.taskList.testutil;

import seedu.taskList.commons.exceptions.IllegalValueException;
import seedu.taskList.model.tag.Tag;
import seedu.taskList.model.tag.UniqueTagList;
import seedu.taskList.model.task.Comment;
import seedu.taskList.model.task.Name;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(TestTask taskToCopy) {
        this.task = new TestTask(taskToCopy);
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withComment(String address) throws IllegalValueException {
        this.task.setComment(new Comment(address));
        return this;
    }


    public TestTask build() {
        return this.task;
    }

}
