package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.booking.Booking;

/**
 * JAXB-friendly adapted version of the Booking.
 */
public class XmlAdaptedBooking {

    @XmlValue
    public String bookingSlots;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedBooking() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedBooking(Booking source) {
        bookingSlots = source.toString();
    }

    /**
     * Converts this jaxb-friendly adapted label object into the model's {@link bookings} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     * @throws CommandException
     */
    public Booking toModelType() throws IllegalValueException, CommandException {
        return new Booking(bookingSlots);
    }
}
