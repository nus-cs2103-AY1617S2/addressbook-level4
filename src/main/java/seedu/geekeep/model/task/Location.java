package seedu.geekeep.model.task;

/**
 * Represents location of a task
 */
public class Location {
    public final String value;

    public Location(String location) {
        assert location != null;
        String trimmedLocation = location.trim();
        this.value = trimmedLocation;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this
                || (obj instanceof Location
                        && this.value.equals(((Location) obj).value));
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
