package project.taskcrusher.storage;

import javax.xml.bind.annotation.XmlElement;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.model.event.Timeslot;
import project.taskcrusher.model.shared.DateUtil;

//@@author A0127737X
/**
 * JAXB-friendly version of Timeslot.
 */
public class XmlAdaptedTimeslot {

    @XmlElement(required = true)
    public String startDate;
    @XmlElement(required = true)
    public String endDate;

    /**
     * Constructs an XmlAdaptedTimeslot.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTimeslot() {}

    /**
     * Converts a given Timeslot into this class for JAXB use.
     */
    public XmlAdaptedTimeslot(Timeslot timeslot) {
        startDate = DateUtil.dateAsStringForStorage(timeslot.start);
        endDate = DateUtil.dateAsStringForStorage(timeslot.end);
    }

    /**
     * Converts this jaxb-friendly adapted timeslot object into the model's timeslot object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted timeslot
     */
    public Timeslot toModelType() throws IllegalValueException {
        return new Timeslot(startDate, endDate);
    }
}
