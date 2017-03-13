package seedu.address.testutil;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.label.Label;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Title;

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

    public TaskBuilder withLabels(String ... labels) throws IllegalValueException {
        task.setLabels(new UniqueLabelList());
        for (String label: labels) {
            task.getLabels().add(new Label(label));
        }
        return this;
    }

    public TaskBuilder withStartTime(String startTime) throws IllegalValueException, IllegalDateTimeValueException {
        if (startTime != null) {
            this.task.setStartTime(Optional.empty());
        } else {
            this.task.setStartTime(Optional.ofNullable(new Deadline(startTime)));
        }
        return this;
    }
    public TaskBuilder withDeadline(String deadline) throws IllegalValueException, IllegalDateTimeValueException {
        if (deadline != null) {
            this.task.setStartTime(Optional.empty());
        } else {
            this.task.setStartTime(Optional.ofNullable(new Deadline(deadline)));
        }
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
