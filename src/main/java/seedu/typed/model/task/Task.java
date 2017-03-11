package seedu.typed.model.task;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.commons.util.CollectionUtil;
import seedu.typed.model.tag.Tag;
import seedu.typed.model.tag.UniqueTagList;
import seedu.typed.model.tag.UniqueTagList.DuplicateTagException;

/**
 * Represents a Task in the task manager. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Date date;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Date date, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, date, tags);
        this.name = name;
        this.date = date;
        this.tags = new UniqueTagList(tags); // protect internal tags from
                                             // changes
                                             // in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDate(), source.getTags());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setDate(Date date) {
        assert date != null;
        this.date = date;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setDate(replacement.getDate());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your
        // own
        return Objects.hash(name, date, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    /**
     * TaskBuilder helps to build a Task object by being flexible in
     * what attributes a Task object will initialise with. In particular,
     * only a name is compulsory whereas other attributes are optional.
     * @param ReadOnlyTask An existing task to modify
     * @return Task
     * @author YIM CHIA HUI
     *
     */
    public static class TaskBuilder {
        private Name name;
        private Date date;
        private UniqueTagList tags;

        public TaskBuilder() {
            this.tags = new UniqueTagList();
        }

        public TaskBuilder(ReadOnlyTask task) {
            this.name = task.getName();
            this.date = task.getDate();
            this.tags = task.getTags();
        }

        public TaskBuilder setName(String name) {
            this.name = new Name(name);
            return this;
        }

        public TaskBuilder setName(Name name) {
            this.name = name;
            return this;
        }

        public TaskBuilder setDate(String date) throws IllegalValueException {
            this.date = new Date(date);
            return this;
        }

        public TaskBuilder setDate(Date date) {
            this.date = date;
            return this;
        }

        public TaskBuilder addTags(String tag) throws DuplicateTagException, IllegalValueException {
            this.tags.add(new Tag(tag));
            return this;
        }

        public TaskBuilder setTags(UniqueTagList tags) {
            this.tags = tags;
            return this;
        }

        public TaskBuilder setTags(Set<String> tags) throws IllegalValueException {
            final Set<Tag> tagSet = new HashSet<>();
            for (String tagName : tags) {
                tagSet.add(new Tag(tagName));
            }
            this.tags = new UniqueTagList(tagSet);
            return this;
        }

        public Task build() {
            return new Task(this.name, this.date, this.tags);
        }
    }

}
