package seedu.jobs.testutil;

import java.util.Optional;

import seedu.jobs.commons.exceptions.IllegalValueException;
import seedu.jobs.model.tag.Tag;
import seedu.jobs.model.tag.UniqueTagList;
import seedu.jobs.model.task.Description;
import seedu.jobs.model.task.Name;
import seedu.jobs.model.task.Period;
import seedu.jobs.model.task.Time;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public TaskBuilder(TestTask personToCopy) {
        this.task = new TestTask(personToCopy);
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(Optional.of(name)));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withDescription(String description) throws IllegalValueException {
        this.task.setDescription(new Description(Optional.of(description)));
        return this;
    }

    public TaskBuilder withStartTime(String time) throws IllegalValueException {
        this.task.setStartTime(new Time(Optional.of(time)));
        return this;
    }

    public TaskBuilder withEndTime(String time) throws IllegalValueException {
        this.task.setEndTime(new Time(Optional.of(time)));
        return this;
    }

    public TaskBuilder withPeriod(String period) throws IllegalValueException {
        this.task.setPeriod(new Period(Optional.of(period)));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
