package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.UnmodifiableObservableList;
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

    UniqueLabelList normalList;
    UniqueLabelList unorderedList;
    UniqueLabelList disruptiveList;
    UniqueLabelList emptyList1;
    UniqueLabelList emptyList2;

    @Before
    public void setup() throws DuplicateLabelException, IllegalValueException {
        disruptiveList = new UniqueLabelList("lAbEl2");
        normalList = new UniqueLabelList(new Label("label1"), new Label("label2"));
        unorderedList = new UniqueLabelList(new Label("label2"), new Label("label1"));
        emptyList1 = new UniqueLabelList();
        emptyList2 = new UniqueLabelList();

        disruptiveList.add(new Label("laBeL1"));
    }

    @Test
    public void uniqueLabelList_TestInitializationEmpty_ThrowIllegalValueException()
            throws DuplicateLabelException, IllegalValueException {
        exception.expect(IllegalValueException.class);
        new UniqueLabelList("");
    }

    @Test
    public void uniqueLabelList_TestInitializationNull_ThrowAssertionException()
            throws DuplicateLabelException, IllegalValueException {
        exception.expect(AssertionError.class);
        new UniqueLabelList("validTag", null, null);
    }

    @Test
    public void uniqueLabelList_TestInitializationDuplicateString_ThrowDuplicateLabelException()
            throws DuplicateLabelException, IllegalValueException {
        exception.expect(DuplicateLabelException.class);
        Label label = new Label("validTag");
        new UniqueLabelList(label, label);
    }

    @Test
    public void uniqueLabelList_TestInitializationDuplicateList_ThrowDuplicateLabelException()
            throws DuplicateLabelException, IllegalValueException {
        exception.expect(DuplicateLabelException.class);
        List<Label> labels = new LinkedList<Label>();
        labels.add(new Label("tag1"));
        labels.add(new Label("tag2"));
        labels.add(new Label("tag1"));
        new UniqueLabelList(labels);
    }

    @Test
    public void uniqueLabelList_TestInitializationList() throws DuplicateLabelException, IllegalValueException {
        UniqueLabelList list1 = new UniqueLabelList("tag1", "tag2");
        List<Label> labels = new LinkedList<Label>();
        labels.add(new Label("tag1"));
        labels.add(new Label("tag2"));
        UniqueLabelList list2 = new UniqueLabelList(labels);
        assertTrue(list1.equals(list2));

        list2 = new UniqueLabelList(list1.toSet());
        assertTrue(list1.equals(list2));

        list2 = new UniqueLabelList(list1);
        assertTrue(list1.equals(list2));

        list2 = new UniqueLabelList();
        list2.mergeFrom(list1);
        assertTrue(list1.equals(list2));

        list2.setLabels(list1);
        assertTrue(list1.equals(list2));
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

        //Test different order
        assertTrue(normalList.equalsOrderInsensitive(unorderedList));
    }

    @Test
    public void uniqueLabelList_TestHashCode() {
        assertTrue(emptyList1.hashCode() == emptyList1.hashCode());
        assertTrue(emptyList1.hashCode() == emptyList2.hashCode());
        assertTrue(normalList.hashCode() != disruptiveList.hashCode());
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

    @Test
    public void uniqueLabelList_TestIteratorAndUnmodifiableString() {
        Iterator<Label> iterator = normalList.iterator();
        UnmodifiableObservableList<Label> list = normalList.asObservableList();
        for (int i = 0; i < list.size() && iterator.hasNext(); i++) {
            assertTrue(iterator.next().equals(list.get(i)));
        }
    }
}
