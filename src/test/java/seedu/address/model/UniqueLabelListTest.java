package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.label.Label;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.label.UniqueLabelList.DuplicateLabelException;

/**
 * Test file for UniqueLabelList
 */
public class UniqueLabelListTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    UniqueLabelList normalList = new UniqueLabelList();
    UniqueLabelList unorderedList = new UniqueLabelList();
    UniqueLabelList emptyList1 = new UniqueLabelList();
    UniqueLabelList emptyList2 = new UniqueLabelList();

    @Before
    public void setup() throws DuplicateLabelException, IllegalValueException {
        normalList.add(new Label("label1"));
        normalList.add(new Label("label2"));
        unorderedList.add(new Label("lAbEl2"));
        unorderedList.add(new Label("laBeL1"));
    }

    @Test
    public void uniqueLabelList_TestEmptyLists() {
        //Test equals
        assertTrue(emptyList1.equals(emptyList1));

        //Test both empty list
        assertTrue(emptyList1.equals(emptyList2));

        //Test null
        assertFalse(emptyList1.equals(null));
    }

    @Test
    public void uniqueLabelList_TestEqualOrderCaseSensitive_ReturnTrue()
            throws DuplicateLabelException, IllegalValueException {
        //Test same list
        assertTrue(normalList.equalsOrderInsensitive(normalList));
    }

    @Test
    public void uniqueLabelList_TestHashCode() {
        assertTrue(emptyList1.hashCode() == emptyList1.hashCode());
        assertTrue(emptyList1.hashCode() == emptyList2.hashCode());
        assertTrue(normalList.hashCode() != unorderedList.hashCode());
    }

    @Test
    public void uniqueLabelList_TestDuplicateLabels()
            throws DuplicateLabelException, IllegalValueException {
        exception.expect(DuplicateLabelException.class);
        UniqueLabelList duplicateTagList = new UniqueLabelList();
        duplicateTagList.add(new Label("label"));
        duplicateTagList.add(new Label("label"));
    }

    @Test
    public void uniqueLabelList_ContainLabelString() {
        assertTrue(normalList.containsStringLabel("label1"));
        assertFalse(normalList.containsStringLabel("nonexistentlabel"));
    }
}
