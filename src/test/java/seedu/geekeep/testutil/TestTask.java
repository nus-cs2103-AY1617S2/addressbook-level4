//@@author A0121658E
package seedu.geekeep.testutil;

import seedu.geekeep.model.tag.UniqueTagList;
import seedu.geekeep.model.task.DateTime;
import seedu.geekeep.model.task.Description;
import seedu.geekeep.model.task.ReadOnlyTask;
import seedu.geekeep.model.task.Title;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Title title;
    private Description description;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.title = taskToCopy.getTitle();
        this.endDateTime = taskToCopy.getEndDateTime();
        this.startDateTime = taskToCopy.getStartDateTime();
        this.description = taskToCopy.getDescriptoin();
        this.tags = taskToCopy.getTags();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTitle().title + " ");
        sb.append("d/" + this.getDescriptoin().value + " ");
        sb.append("e/" + this.getEndDateTime().value + " ");
        sb.append("s/" + this.getStartDateTime().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    //@@author
    @Override
    public DateTime getEndDateTime() {
        return endDateTime;
    }

    @Override
    public Description getDescriptoin() {
        return description;
    }

    @Override
    public DateTime getStartDateTime() {
        return startDateTime;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public boolean isFloatingTask() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEvent() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isDeadline() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isDone() {
        // TODO Auto-generated method stub
        return false;
    }


}
