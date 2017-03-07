package seedu.toluist.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a Task
 */
public class Task implements Comparable<Task> {

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
                && Objects.equals(this.startDateTime, ((Task) other).startDateTime) // handles null
                && Objects.equals(this.endDateTime, ((Task) other).endDateTime) // handles null
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

    public boolean isOverdue() {
        return startDateTime != null && startDateTime.isBefore(LocalDateTime.now());
    }

    public boolean isStringContainedInDescriptionIgnoreCase(String comparison) {
        return this.description.toLowerCase().contains(comparison.toLowerCase());
    }

    public boolean isStringContainedInAnyTagIgnoreCase(String comparison) {
        for (Tag tag : allTags) {
            if (tag.tagName.toLowerCase().contains(comparison.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(Task comparison) {
        if (startDateTime.compareTo(comparison.startDateTime) != 0) {
            return startDateTime.compareTo(comparison.startDateTime);
        } else if (false) {
            //TODO add priority comparison with variable
            return -1;
        } else {
            return this.description.compareToIgnoreCase(comparison.description);
        }
    }
}
