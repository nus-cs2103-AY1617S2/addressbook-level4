package seedu.address.testutil;

import java.util.Optional;

import seedu.address.logic.parser.DateTimeUtil;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartEndDateTime;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Optional<Deadline> deadline;
    private Optional<StartEndDateTime> startEndDateTime;
    private UniqueTagList tags;
    private boolean done;

    //@@author A0140023E
    public TestTask() {
        // Initialize TestTask with empty deadlines, startEndDateTime and tags because they are optional.
        // this will allow us to conveniently create TestTasks through TaskBuilder without being forced
        // to specify the optional fields
        deadline = Optional.empty();
        startEndDateTime = Optional.empty();
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy} by shallow copying only the data.
     * Shallow copying reduces the overhead of copying the data.
     */
    public TestTask(TestTask taskToCopy) {
        name = taskToCopy.getName();
        deadline = taskToCopy.getDeadline();
        startEndDateTime = taskToCopy.getStartEndDateTime();
        tags = taskToCopy.getTags();
    }

    //@@author
    public void setName(Name name) {
        this.name = name;
    }

    //@@author A0140023E
    public void setDeadline(Deadline dateTime) {
        this.deadline = Optional.of(dateTime);
    }

    public void setStartEndDateTime(StartEndDateTime startEndDateTime) {
        this.startEndDateTime = Optional.of(startEndDateTime);
    }

    //@@author
    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    //@@author A0135998H
    public void markDone() {
        this.done = !done;
    }

    @Override
    public Name getName() {
        return name;
    }

    //@@author A0140023E
    @Override
    public Optional<Deadline> getDeadline() {
        return deadline;
    }

    @Override
    public Optional<StartEndDateTime> getStartEndDateTime() {
        return startEndDateTime;
    }

    //@@author
    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    //@@author A0135998H
    public boolean isDone() {
        return done;
    }

    //@@author
    @Override
    public String toString() {
        return getAsText();
    }

    //@@author A0140023E
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + getName().value + " ");

        if (getDeadline().isPresent()) {
            sb.append(" by ");
            // TODO change the format
            sb.append(getDeadline().get().getDateTime().format(DateTimeUtil.DATE_TIME_FORMAT));
        }

        if (getStartEndDateTime().isPresent()) {
            // TODO change the format
            StartEndDateTime startEndDateTime = getStartEndDateTime().get();
            sb.append(" from ");
            sb.append(startEndDateTime.getStartDateTime().format(DateTimeUtil.DATE_TIME_FORMAT));
            sb.append(" to ");
            sb.append(startEndDateTime.getEndDateTime().format(DateTimeUtil.DATE_TIME_FORMAT));
        }

        getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
