package project.taskcrusher.storage;

import javax.xml.bind.annotation.XmlValue;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.model.tag.Tag;

public class XmlAdaptedTimeslot {

    @XmlValue
    public String startDate;
    @XmlValue
    public String endDate;

    /**
     * Constructs an XmlAdaptedTimeslot.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTimeslot() {}

    /**
     * Converts a given Timeslot into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTimeslot(Timeslot timeslot) {
        startDate = timeslot.startDate.toString();
        endDate = timeslot.endDate.toString();
    }

    /**
     * Converts this jaxb-friendly adapted timeslot object into the model's Timeslot object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted timeslot
     */
    public Tag toModelType() throws IllegalValueException {
        return new Timeslot(startDate, endDate);
    }
}
