package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.label.Label;

/**
 * JAXB-friendly adapted version of the Label.
 */
public class XmlAdaptedLabel {

    @XmlValue
    public String labelName;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLabel() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedLabel(Label source) {
        labelName = source.labelName;
    }

    /**
     * Converts this jaxb-friendly adapted label object into the model's {@link Label} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Label toModelType() throws IllegalValueException {
        return new Label(labelName);
    }

}
