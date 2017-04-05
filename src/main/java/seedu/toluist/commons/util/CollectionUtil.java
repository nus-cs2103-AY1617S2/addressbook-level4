package seedu.toluist.commons.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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

    //@@author A0131125Y
    /**
     * Returns true if the two collections holds exactly the same set of items, and have same sizes
     */
    public static <T> boolean elementsAreSimilar(Collection<T> items1, Collection<T> items2) {
        for (T item1 : items1) {
            if (!items2.contains(item1)) {
                return false;
            }
        }
        return items1.size() == items2.size();
    }

    /**
     * Convert items to string, using each item's default toString method
     */
    public static <T> String getStringRepresentation(String delimiter, Collection<T> items) {
        List<String> itemStringList = items.stream().map(T::toString).collect(Collectors.toList());
        return String.join(delimiter, itemStringList);
    }

    /**
     * Converts collection of item to array of arrays
     */
    public static <T> T[][] collectionToArrayOfArrays(Collection<T> items) {
        return (T[][]) items.stream().map(item -> (T[]) new Object[] { item })
                .collect(Collectors.toList())
                .toArray(new Object[0][0]);
    }
}
