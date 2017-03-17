package savvytodo.storage;

import javax.xml.bind.annotation.XmlValue;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.model.category.Category;

/**
 * JAXB-friendly adapted version of the Category.
 */
public class XmlAdaptedCategory {

    @XmlValue
    public String categoryName;

    /**
     * Constructs an XmlAdaptedCategory.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCategory() {}

    /**
     * Converts a given Category into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedCategory(Category source) {
        categoryName = source.categoryName;
    }

    /**
     * Converts this jaxb-friendly adapted category object into the model's Category object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Category toModelType() throws IllegalValueException {
        return new Category(categoryName);
    }

}
