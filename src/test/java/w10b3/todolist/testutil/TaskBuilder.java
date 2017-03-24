package w10b3.todolist.testutil;

import w10b3.todolist.commons.exceptions.IllegalValueException;
import w10b3.todolist.model.tag.Tag;
import w10b3.todolist.model.tag.UniqueTagList;
import w10b3.todolist.model.task.Description;
import w10b3.todolist.model.task.EndTime;
import w10b3.todolist.model.task.StartTime;
import w10b3.todolist.model.task.Title;
import w10b3.todolist.model.task.UrgencyLevel;
import w10b3.todolist.model.task.Venue;

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

    public TaskBuilder withTags(String... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag : tags) {
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

    public TaskBuilder withUrgencyLevel(String urgencyLevel) throws IllegalValueException {
        this.task.setUrgencyLevel(new UrgencyLevel(urgencyLevel));
        return this;
    }

    public TaskBuilder withDescription(String description) throws IllegalValueException {
        this.task.setDescription(new Description(description));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
