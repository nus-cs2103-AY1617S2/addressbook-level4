package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.EndTime;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Title;
import seedu.address.model.task.Venue;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

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

    public TaskBuilder withTitle(String title) throws IllegalValueException {
        this.task.setTitle(new Title(title));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withEndTime(String endtime) throws IllegalValueException {
        this.task.setEndTime(new EndTime(endtime));
        return this;
    }

    public TaskBuilder withVenue(String venue) throws IllegalValueException {
        this.task.setVenue(new Venue(venue));
        return this;
    }

    public TaskBuilder withStartTime(String starttime) throws IllegalValueException {
        this.task.setStartTime(new StartTime(starttime));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
