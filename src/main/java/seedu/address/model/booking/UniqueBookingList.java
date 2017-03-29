package seedu.address.model.booking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.emory.mathcs.backport.java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author A0162877N
/**
 * A list of bookings that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueBookingList implements Iterable<Booking>, Cloneable {
    private final ObservableList<Booking> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty BookingList.
     */
    public UniqueBookingList() {}

    /**
     * Creates a UniqueBookingList using given String bookings. Enforces no nulls or
     * duplicates.
     * @throws CommandException
     */
    public UniqueBookingList(String... bookings)
            throws DuplicateBookingException, IllegalValueException, CommandException {
        final List<Booking> bookingList = new ArrayList<Booking>();
        for (String booking : bookings) {
            bookingList.add(new Booking(booking));
        }
        sortBooklingList(bookingList);
        setBookings(bookingList);
    }

    /**
     * Creates a UniqueBookingList using given bookings. Enforces no nulls or
     * duplicates.
     */
    public UniqueBookingList(Booking... booking) throws DuplicateBookingException {
        assert !CollectionUtil.isAnyNull((Object[]) booking);
        final List<Booking> initialBookings = Arrays.asList(booking);
        if (!CollectionUtil.elementsAreUnique(initialBookings)) {
            throw new DuplicateBookingException();
        }
        sortBooklingList(initialBookings);
        internalList.addAll(initialBookings);
    }

    /**
     * Creates a UniqueBookingList using given bookings. Enforces no null or
     * duplicate elements.
     * @throws DuplicateBookingException
     */
    public UniqueBookingList(Collection<Booking> bookings) throws DuplicateBookingException {
        this();
        setBookings(bookings);
    }

    /**
     * Creates a UniqueBookingList using given bookings. Enforces no nulls.
     */
    public UniqueBookingList(Set<Booking> bookings) {
        assert !CollectionUtil.isAnyNull(bookings);
        final List<Booking> bookingList = new ArrayList<Booking>(bookings);
        sortBooklingList(bookingList);
        internalList.addAll(bookingList);
    }

    /**
     * Creates a copy of the given list. Insulates from changes in source.
     */
    public UniqueBookingList(UniqueBookingList source) {
        internalList.addAll(source.internalList);
    }

    /**
     * Returns all bookings in this list as a Set. This set is mutable and
     * change-insulated against the internal list.
     */
    public Set<Booking> toSet() {
        return new HashSet<>(internalList);
    }

    /**
     * Returns all bookings in this list as a List. This set is mutable and
     * change-insulated against the internal list.
     */
    public ArrayList<Booking> toList() {
        return new ArrayList<Booking>(internalList);
    }

    /**
     * Replaces the Tags in this list with those in the argument booking list.
     */
    public void setBookings(UniqueBookingList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setBookings(Collection<Booking> bookings) throws DuplicateBookingException {
        assert !CollectionUtil.isAnyNull(bookings);
        if (!CollectionUtil.elementsAreUnique(bookings)) {
            throw new DuplicateBookingException();
        }
        internalList.setAll(bookings);
    }

    /**
     * Removes all bookings from the list. Warning, this cannot be undone!
     */
    public void removeAll() {
        internalList.clear();
    }

    @Override
    public UniqueBookingList clone() {
        UniqueBookingList bookingList = new UniqueBookingList();
        try {
            for (Booking booking : internalList) {
                bookingList.add(new Booking(booking.getBookingStartDate(), booking.getBookingEndDate()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingList;
    }

    /**
     * Ensures every booking in the argument list exists in this object.
     */
    public void mergeFrom(UniqueBookingList from) {
        final Set<Booking> alreadyInside = this.toSet();
        from.internalList.stream().filter(booking -> !alreadyInside.contains(booking)).forEach(internalList::add);
    }

    /**
     * Returns true if the list contains an equivalent Booking as the given
     * argument.
     */
    public boolean contains(Booking toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Returns true if the booking list contains an equivalent booking as the given
     * argument.
     */
    public boolean containsStringBooking(Booking bookingToCheck) {
        assert bookingToCheck != null;
        for (Booking booking : internalList) {
            if (booking.getBookingStartDate().equals(bookingToCheck.getBookingStartDate())
                    && booking.getBookingEndDate().equals(bookingToCheck.getBookingEndDate())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a booking to the list.
     *
     * @throws DuplicateBookingException
     *             if the booking to add is a duplicate of an existing booking in
     *             the list.
     */
    public void add(Booking toAdd) throws DuplicateBookingException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateBookingException();
        }
        internalList.add(toAdd);
    }

    @Override
    public Iterator<Booking> iterator() {
        return internalList.iterator();
    }

    public UnmodifiableObservableList<Booking> asObservableList() {
        return new UnmodifiableObservableList<>(internalList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueBookingList // instanceof handles nulls
                        && this.internalList.equals(((UniqueBookingList) other).internalList));
    }

    public boolean equalsOrderInsensitive(UniqueBookingList other) {
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    public boolean isEmpty() {
        return internalList.isEmpty();
    }

    private void sortBooklingList(List<Booking> bookingList) {
        Collections.sort(bookingList);
    }

    /**
     * Signals that an operation would have violated the 'no duplicates'
     * property of the list.
     */
    public static class DuplicateBookingException extends DuplicateDataException {
        protected DuplicateBookingException() {
            super("Operation would result in duplicate bookings");
        }
    }

    //@@author A0105287E
    /**
     * Returns the earliest startDate in the whole list. To be used for sorting.
     *
     */
    public Date getEarliestStartTime() {
        Date earliest = internalList.get(0).getBookingStartDate();
        for (Booking booking : internalList) {
            if (booking.getBookingStartDate().before(earliest)) {
                earliest = booking.getBookingStartDate();
            }
        }
        return earliest;
    }
}
