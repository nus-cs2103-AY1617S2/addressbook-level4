package seedu.task.testutil;

import java.util.ArrayList;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Description;
import seedu.task.model.task.Priority;
import seedu.task.model.task.RecurringFrequency;
import seedu.task.model.task.RecurringTaskOccurrence;
import seedu.task.model.task.Timing;

//@@author A0163559U
/**
 * A class for building mutable task objects. For testing only.
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

    public TaskBuilder withDescription(String description) throws IllegalValueException {
        this.task.setDescription(new Description(description));
        return this;
    }

    public TaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }

    public TaskBuilder withOccurrences(ArrayList<RecurringTaskOccurrence> occurrences) throws IllegalValueException {
        this.task.setOccurrences(occurrences);
        this.task.getOccurrences().add(new RecurringTaskOccurrence());
        return this;
    }

    public TaskBuilder withRecurring(boolean isRecurring) throws IllegalValueException {
        this.task.setRecurring(isRecurring);
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withFrequency(String frequency) throws IllegalValueException {
        this.task.setFrequency(new RecurringFrequency(frequency));
        return this;
    }

    public TaskBuilder withOccurrenceIndexList(ArrayList<Integer> occurrenceIndexList) throws IllegalValueException {
        this.task.setOccurrenceIndexList(occurrenceIndexList);
        return this;
    }

    public TaskBuilder withStartTiming(String string) throws IllegalValueException {
        this.task.setStartTiming(new Timing(string));
        return this;
    }

    public TaskBuilder withEndTiming(String string) throws IllegalValueException {
        this.task.setEndTiming(new Timing(string));
        return this;
    }
    public TestTask build() {
        return this.task;
    }

}
