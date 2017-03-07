package seedu.taskboss.model.category;


import seedu.taskboss.commons.exceptions.IllegalValueException;

/**
 * Represents a Category in TaskBoss.
 * Guarantees: immutable; name is valid as declared in {@link #isValidCategoryName(String)}
 */
public class Category {

    public static final String MESSAGE_CATEGORY_CONSTRAINTS = "Categories names should be alphanumeric";
    public static final String CATEGORY_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String categoryName;

    /**
     * Validates given category name.
     *
     * @throws IllegalValueException if the given category name string is invalid.
     */
    public Category(String name) throws IllegalValueException {
        assert name != null;
        String trimmedName = name.trim();
        if (!isValidCategoryName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_CATEGORY_CONSTRAINTS);
        }
        this.categoryName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid category name.
     */
    public static boolean isValidCategoryName(String test) {
        return test.matches(CATEGORY_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Category // instanceof handles nulls
                && this.categoryName.equals(((Category) other).categoryName)); // state check
    }

    @Override
    public int hashCode() {
        return categoryName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + categoryName + ']';
    }

}
