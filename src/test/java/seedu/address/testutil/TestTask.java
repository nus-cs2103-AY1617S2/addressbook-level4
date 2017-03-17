package seedu.address.testutil;

import java.util.Optional;

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

    public TestTask() {
        // TODO perhaps a better idea for each of the test tasks to always have their dates
        // this is a temporary hack
        deadline = Optional.empty();
        startEndDateTime = Optional.empty();

        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.tags = taskToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Optional<Deadline> getDeadline() {
        return deadline;
    }

    @Override
    public Optional<StartEndDateTime> getStartEndDateTime() {
        return startEndDateTime;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        // TODO add the new fields
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
