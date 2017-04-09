package seedu.address.commons.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
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

    /**
     * Returns a random element from the given array
     * @param Array to retrieve from
     * @param Random object to use
     * @return Randomly-chosen element from array
     */
    public static <T> T getRandom(T[] arr, Random r) {
        return arr[r.nextInt(arr.length)];
    }

    /**
     * Finds the first in the list of the set of last indices of any in the searched list
     * @param List to search
     * @param Elements to find last indices of
     * @return First in the list of the set of last indices of any in the searched list
     */
    public static <T> int firstLastIndexOfAny(List<T> list, List<T> any) {
        int i = list.size();
        for (T a : any) {
            int t = list.lastIndexOf(a);
            if (t != -1 && t < i) {
                i = t;
            }
        }
        return i;
    }
    //@@author

}
