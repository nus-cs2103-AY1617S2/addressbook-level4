package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.label.Label;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Title;
import seedu.address.model.person.Task;
import seedu.address.model.person.ReadOnlyTask;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;

    @XmlElement
    private List<XmlAdaptedLabel> labeled = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyTask source) {
        name = source.getTitle().title;
        labeled = new ArrayList<>();
        for (Label label : source.getLabels()) {
            labeled.add(new XmlAdaptedLabel(label));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        final List<Label> personLabels = new ArrayList<>();
        for (XmlAdaptedLabel label : labeled) {
            personLabels.add(label.toModelType());
        }
        final Title name = new Title(this.name);
        final Deadline address = new Deadline(this.address);
        final UniqueLabelList labels = new UniqueLabelList(personLabels);
        return new Task(name, address, labels);
    }
}
