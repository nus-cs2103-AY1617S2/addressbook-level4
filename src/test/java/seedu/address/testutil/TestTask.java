package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Name;

/**
 * A mutable task object. For testing only.
 */
public class TestTask extends FloatingTask {
    private static int counter = 0;

    public TestTask() throws IllegalValueException {
        super(new Name(String.valueOf(counter)), new UniqueTagList(), false, false);
        counter++;
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        super(taskToCopy.getName(), taskToCopy.getTags(), taskToCopy.isDone(), taskToCopy.isManualToday());
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        if (!this.getTags().toSet().isEmpty()) {
            this.getTags().asObservableList().stream().forEach(s -> sb.append(" #" + s.tagName));
        }
        return sb.toString();
    }
}
