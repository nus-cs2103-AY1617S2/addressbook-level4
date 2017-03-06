package seedu.geekeep.model.task;

/**
 * Represents title of a task
 */
public class Title {
    public final String value;

    public Title(String title) {
        assert title != null;
        String trimmedTitle = title.trim();
        this.value = trimmedTitle;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this
                || (obj instanceof Location
                        && this.value.equals(((Title)obj).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
