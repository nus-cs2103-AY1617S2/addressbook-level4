package seedu.taskboss.model.category;


import seedu.taskboss.commons.exceptions.IllegalValueException;

/**
 * Represents a Category in TaskBoss.
 * Guarantees: immutable; name is valid as declared in {@link #isValidCategoryName(String)}
 */
public class Category {

    private static final String EMPTY_STRING = "";
    public static final String MESSAGE_CATEGORY_CONSTRAINTS = "Categories names should be alphanumeric";
    public static final String CATEGORY_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String categoryName;

    public static Category done = new Category("Done", EMPTY_STRING);

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
        this.categoryName = formatName(trimmedName);
    }

    private Category(String name, String toDifferentiaiteBetweenConstructors) {
        assert toDifferentiaiteBetweenConstructors.equals(EMPTY_STRING);
        this.categoryName = name;
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

    //@@author A0147990R
    private String formatName(String name) {
        if (name.length() <= 1) {
            return name.toUpperCase();
        } else {
            String first = name.substring(0, 1).toUpperCase();
            String rest = name.substring(1).toLowerCase();
            return first + rest;
        }
    }

}
