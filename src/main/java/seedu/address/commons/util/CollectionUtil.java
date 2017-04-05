package seedu.address.commons.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Utility methods related to Collections
 */
public class CollectionUtil {

    /** @see #isAnyNull(Collection) */
    public static boolean isAnyNull(Object... items) {
        return Stream.of(items).anyMatch(Objects::isNull);
    }

    /**
     * Returns true if any element of {@code items} is null.
     * @throws NullPointerException if {@code items} itself is null.
     */
    public static boolean isAnyNull(Collection<?> items) {
        return items.stream().anyMatch(Objects::isNull);
    }

    /**
     * Returns true is any of the given items are present.
     */
    public static boolean isAnyPresent(Optional<?>... items) {
        return Stream.of(items).anyMatch(Optional::isPresent);
    }

    /**
     * Returns true if every element in a collection are unique by {@link Object#equals(Object)}.
     */
    public static boolean elementsAreUnique(Collection<?> items) {
        final Set<Object> testSet = new HashSet<>();
        for (Object item : items) {
            final boolean itemAlreadyExists = !testSet.add(item); // see Set documentation
            if (itemAlreadyExists) {
                return false;
            }
        }
        return true;
    }
    
    //@@author A0163848R
    /**
     * Returns first occurrence of the given type in the array.
     * @param Collection to search.
     * @param Type to find.
     * @return First object of given type, or null if not found.
     */
    public static <T> T firstOf(Object[] items, Class<T> type) {
        for (Object el : items) {
            if (type.isInstance(el)) {
                return type.cast(el);
            }
        }
        return null;
    }
}
