package seedu.onetwodo.model;

import seedu.onetwodo.commons.exceptions.IllegalValueException;

public enum SortOrder {
    DATETIME("datetime"),
    ALPHANUMERIC("alphanumeric"),
    PRIORITY("priority"),
    NONE("");

    private static final String NO_SUCH_SORT_ORDER = "No such sort order";
    private final String description;
    public static final String DATETIME_STRING = "datetime";
    public static final String ALPHANUMERIC_STRING = "alphanumeric";
    public static final String PRIORITY_STRING = "priority";

    SortOrder (String description) {
        this.description = description;
    }

    public String getStatus() {
        return description;
    }

    public static SortOrder getSortOrder(String description) throws IllegalValueException {
        switch (description) {
        case DATETIME_STRING:
            return DATETIME;
        case ALPHANUMERIC_STRING:
            return ALPHANUMERIC;
        case PRIORITY_STRING:
            return PRIORITY;
        case "":
            return NONE;
        default:
            throw new IllegalValueException(NO_SUCH_SORT_ORDER);
        }
    }

    @Override
    public String toString() {
        return description;
    }
}
