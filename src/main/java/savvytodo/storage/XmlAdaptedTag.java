package savvytodo.storage;

import javax.xml.bind.annotation.XmlValue;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.model.category.Category;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedTag {

    @XmlValue
    public String tagName;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTag() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTag(Category source) {
        tagName = source.categoryName;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Category toModelType() throws IllegalValueException {
        return new Category(tagName);
    }

}
