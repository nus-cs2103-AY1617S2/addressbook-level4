package seedu.address.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.label.Label;

/**
 * Test class to test Label class
 */
public class LabelTest {

    @Test
    public void label_TestToString() throws IllegalValueException {
        String tag = "label1";
        String tagToString = "[" + tag + "]";
        Label label = new Label(tag);
        assertTrue(label.toString().equals(tagToString));
    }
}
