package seedu.todolist.testutil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import seedu.todolist.commons.util.CollectionUtil;
import seedu.todolist.logic.commands.AddCommand;
import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.todo.Name;
import seedu.todolist.model.todo.ReadOnlyTodo;

/**
 * A mutable todo object. For testing only.
 */
public class TestTodo implements ReadOnlyTodo {


    private Name name;
    private Date starttime;
    private Date endtime;
    private Date completeTime;

    private UniqueTagList tags;
    /**
     * Constructor for a empty floating task
     */
    public TestTodo() {
        this.name = null;
        this.starttime = null;
        this.endtime = null;
        this.tags = new UniqueTagList(); // protect internal tags from changes in the arg list
    }

    /**
     * Constructor for a floating task
     */
    public TestTodo(Name name, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    //@@author A0163786N
    /**
     * Constructor for a deadline
     */
    public TestTodo(Name name, Date endtime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
        if (endtime != null) {
            this.endtime = endtime;
        }
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    //@@author
    /**
     * Constructor for an event
     */
    public TestTodo(Name name, Date starttime, Date endtime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
        if (starttime != null && endtime != null) {
            this.starttime = starttime;
            this.endtime = endtime;
        }
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    //@@author A0163786N
    /**
     * General todo constructor
     */
    public TestTodo(Name name, Date starttime, Date endtime, Date completeTime, UniqueTagList tags) {
        this.name = name;
        this.starttime = starttime;
        this.endtime = endtime;
        this.completeTime = completeTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    //@@author
    /**
     * Creates a copy of the given ReadOnlyTodo.
     */
    public TestTodo(ReadOnlyTodo source) {
        this(source.getName(), source.getStartTime(), source.getEndTime(), source.getCompleteTime(), source.getTags());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setStartTime(Date starttime) {
        assert starttime != null;
        this.starttime = starttime;
    }

    @Override
    public Date getStartTime() {
        return starttime;
    }

    public void setEndTime(Date endtime) {
        assert endtime != null;
        this.endtime = endtime;
    }

    @Override
    public Date getEndTime() {
        return endtime;
    }
    //@@author A0163786N
    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }
    //@@author
    //@@author A0163786N
    @Override
    public Date getCompleteTime() {
        return completeTime;
    }
    //@@author
    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this todo's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this todo with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTodo replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setStartTime(replacement.getStartTime());
        this.setEndTime(replacement.getEndTime());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTodo // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTodo) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, starttime, endtime, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    //@@author A0163786N
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName);
        if (this.getEndTime() != null) {
            DateFormat dateFormat = new SimpleDateFormat(AddCommand.DATE_FORMAT);
            sb.append(" e/" + dateFormat.format(this.getEndTime()));
            if (this.getStartTime() != null) {
                sb.append(" s/" + dateFormat.format(this.getStartTime()));
            }
        }

        this.getTags().asObservableList().stream().forEach(s -> sb.append(" t/" + s.tagName + " "));
        return sb.toString();
    }
    //@@author
}
