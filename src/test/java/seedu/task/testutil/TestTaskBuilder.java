package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.NattyDateUtil;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.CompletionStatus;
import seedu.task.model.task.EndTime;
import seedu.task.model.task.Name;
import seedu.task.model.task.StartTime;

//@@author A0146789H
/**
 * Builds a test task.
 */
public class TestTaskBuilder {

    private TestTask task;

    public TestTaskBuilder() {
        this.task = new TestTask();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public TestTaskBuilder(TestTask taskToCopy) {
        this.task = new TestTask(taskToCopy);
    }

    public TestTaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public TestTaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TestTaskBuilder withCompletion(boolean completion) throws IllegalValueException {
        this.task.setCompletionStatus(new CompletionStatus(completion));
        return this;
    }

    public TestTaskBuilder withStartDate(String dateString) throws IllegalValueException {
        this.task.setStartTime(new StartTime(NattyDateUtil.parseSingleDate(dateString)));
        return this;
    }

    public TestTaskBuilder withEndDate(String email) throws IllegalValueException {
        this.task.setEndTime(new EndTime(NattyDateUtil.parseSingleDate(email)));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
