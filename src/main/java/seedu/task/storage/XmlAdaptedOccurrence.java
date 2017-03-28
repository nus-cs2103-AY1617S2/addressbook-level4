//@@author A0164212U
package seedu.task.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.RecurringTaskOccurrence;
import seedu.task.model.task.Timing;

/**
 * JAXB-friendly adapted version of the RecurringTaskOccurrence.
 */
public class XmlAdaptedOccurrence {

    @XmlValue
    public String startTime;
    public String endTime;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedOccurrence() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedOccurrence(RecurringTaskOccurrence occurrence) {
        startTime = occurrence.getStartTiming().toString();
        endTime = occurrence.getEndTiming().toString();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's RecurringTaskOccurrence object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public RecurringTaskOccurrence toModelTypeOccurrence() throws IllegalValueException {
        return new RecurringTaskOccurrence(new Timing(startTime), new Timing(endTime));
    }

}
