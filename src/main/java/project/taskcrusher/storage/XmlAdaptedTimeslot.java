package project.taskcrusher.storage;

import javax.xml.bind.annotation.XmlElement;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.model.event.Timeslot;
import project.taskcrusher.model.shared.DateUtil;

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
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTimeslot(Timeslot timeslot) {
//        startDate = timeslot.start.toString();
//        endDate = timeslot.end.toString();
        startDate = DateUtil.dateAsString(timeslot.start);
        endDate = DateUtil.dateAsString(timeslot.end);
    }

    /**
     * Converts this jaxb-friendly adapted timeslot object into the model's Timeslot object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted timeslot
     */
    public Timeslot toModelType() throws IllegalValueException {
        return new Timeslot(startDate, endDate, Timeslot.IS_LOADING_FROM_STORAGE);
    }
}
