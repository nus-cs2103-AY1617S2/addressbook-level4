package seedu.tache.testutil;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.model.tag.Tag;
import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.Date;
import seedu.tache.model.task.Name;
import seedu.tache.model.task.Time;

public class DetailedTaskBuilder {

    private TestDetailedTask detailedTask;

    public DetailedTaskBuilder() {
        this.detailedTask = new TestDetailedTask();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public DetailedTaskBuilder(TestDetailedTask taskToCopy) {
        this.detailedTask = new TestDetailedTask(taskToCopy);
    }

    public DetailedTaskBuilder withName(String name) throws IllegalValueException {
        this.detailedTask.setName(new Name(name));
        return this;
    }

    public DetailedTaskBuilder withStartDate(String startDate) throws IllegalValueException {
        this.detailedTask.setStartDate(new Date(startDate));
        return this;
    }

    public DetailedTaskBuilder withEndDate(String endDate) throws IllegalValueException {
        this.detailedTask.setEndDate(new Date(endDate));
        return this;
    }

    public DetailedTaskBuilder withStartTime(String startTime) throws IllegalValueException {
        this.detailedTask.setStartTime(new Time(startTime));
        return this;
    }

    public DetailedTaskBuilder withEndTime(String endTime) throws IllegalValueException {
        this.detailedTask.setEndTime(new Time(endTime));
        return this;
    }

    public DetailedTaskBuilder withTags(String ... tags) throws IllegalValueException {
        detailedTask.setTags(new UniqueTagList());
        for (String tag: tags) {
            detailedTask.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TestDetailedTask build() {
        return this.detailedTask;
    }

}
