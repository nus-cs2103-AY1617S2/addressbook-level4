package seedu.toluist.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Represents a Task
 */
public class Task {

    public String description;
    public ArrayList<Tag> allTags = new ArrayList<>();
    public LocalDateTime endDateTime;
    public LocalDateTime startDateTime;
    public boolean isCompleted = false;

    /**
     * To be used with json deserialisation
     */
    public Task() {}

    public Task(String description) {
        this(description, null, null);
    }

    public Task(String description, LocalDateTime startDateTime) {
        this(description, startDateTime, null);
    }

    public Task(String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        assert description != null;
        this.description = description.trim();
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Task // instanceof handles nulls
                && this.description.equals(((Task) other).description)) // state check
                && this.allTags.equals(((Task) other).allTags)
                && this.startDateTime.equals(((Task) other).startDateTime)
                && this.endDateTime.equals(((Task) other).endDateTime)
                && this.isCompleted == ((Task) other).isCompleted;
    }

    public void addTag(Tag tag) {
        this.allTags.add(tag);
    }

    public void removeTag(Tag tag) {
        this.allTags.remove(tag);
    }

    public void replaceTags(ArrayList<Tag> tags) {
        this.allTags = tags;
    }
}
