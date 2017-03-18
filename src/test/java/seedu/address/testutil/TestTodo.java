package seedu.address.testutil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.todo.Name;
import seedu.address.model.todo.ReadOnlyTodo;

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

    /**
     * Constructor for a scheduled task
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

    /**
     * Creates a copy of the given ReadOnlyTodo.
     */

    public TestTodo(ReadOnlyTodo source) {
            this(source.getName(), source.getStartTime(), source.getEndTime(), source.getTags());
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

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    @Override
    public Date getCompleteTime() {
        return completeTime;
    }

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

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        if (this.getStartTime() != null && this.getEndTime() != null) {

            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd'T'HH:mm");
            String startDate = dateFormat.format(this.getStartTime());
            String endDate = dateFormat.format(this.getEndTime());
            sb.append("s/" + startDate + " e/" + endDate + " ");

        } else if (this.getStartTime() == null && this.getEndTime() != null) {

            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd'T'HH:mm");
            String endDate = dateFormat.format(this.getEndTime());
            sb.append(" e/" + endDate + " ");

        }

        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
