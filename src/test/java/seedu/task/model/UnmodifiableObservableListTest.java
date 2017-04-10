package seedu.task.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static seedu.task.testutil.TestUtil.assertThrows;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import seedu.task.commons.core.UnmodifiableObservableList;

public class UnmodifiableObservableListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private List<Integer> backing;
    private UnmodifiableObservableList<Integer> list;
    private UnmodifiableObservableList<Integer> listCopy;
    private UnmodifiableObservableList<Integer> listMoreElements;

    @Before
    public void setUp() {
        backing = new ArrayList<>();
        backing.add(10);
        list = new UnmodifiableObservableList<>(FXCollections.observableList(backing));
        listCopy = new UnmodifiableObservableList<>(FXCollections.observableList(backing));
        backing.add(1);
        backing.add(4);
        backing.add(3);
        backing.add(4);
        listMoreElements = new UnmodifiableObservableList<>(FXCollections.observableList(backing));
    }

    @Test
    public void equal_symmentric() {
        assertTrue(list.equals(listCopy));
        assertTrue(list.hashCode() == listCopy.hashCode());
    }

    @Test
    public void indexOf_success() {
        assertTrue(list.indexOf(10) == 0);
        assertTrue(listMoreElements.indexOf(4) == 2);
    }

    @Test
    public void lastIndexOf_success() {
        assertTrue(list.lastIndexOf(10) == 0);
        assertTrue(listMoreElements.lastIndexOf(4) == 4);
    }

    @Test
    public void sublist_success() {
        UnmodifiableObservableList<Integer> expectedList = new UnmodifiableObservableList<>(
                FXCollections.observableList(backing.subList(1, 3)));
        assertTrue(listMoreElements.subList(1, 3).equals(expectedList));
    }

    @Test
    public void toArray_success() {
        Object[] array = list.toArray();
        for (Object o : array) {
            assertTrue(list.contains((Integer) o));
        }
    }

    @Test
    public void setAll_fail() {
        thrown.expect(UnsupportedOperationException.class);
        ArrayList<Integer> al = new ArrayList<>();
        al.add(10);
        al.add(11);
        list.setAll(al);
    }

    @Test
    public void contains_success() {
        assertTrue(list.contains(10));
    }

    @Test
    public void contains_fail() {
        assertFalse(list.contains(11));
    }

    @Test
    public void containsAll_success() {
        ArrayList<Integer> al = new ArrayList<>();
        al.add(10);
        assertTrue(list.containsAll(al));
    }

    @Test
    public void containsAll_fail() {
        ArrayList<Integer> al = new ArrayList<>();
        al.add(10);
        al.add(11);
        assertFalse(list.containsAll(al));

    }

    @Test
    public void listIterator_previous_and_next_success() {
        ListIterator<Integer> iterator = listMoreElements.listIterator(1);
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasPrevious());
    }

    @Test
    public void transformationListGenerators_correctBackingList() {
        assertSame(list.sorted().getSource(), list);
        assertSame(list.filtered(i -> true).getSource(), list);
    }

    @Test
    public void mutatingMethods_disabled() {

        final Class<UnsupportedOperationException> ex = UnsupportedOperationException.class;

        assertThrows(ex, () -> list.add(0, 2));
        assertThrows(ex, () -> list.add(3));

        assertThrows(ex, () -> list.addAll(2, 1));
        assertThrows(ex, () -> list.addAll(backing));
        assertThrows(ex, () -> list.addAll(0, backing));

        assertThrows(ex, () -> list.set(0, 2));

        assertThrows(ex, () -> list.setAll(new ArrayList<Number>()));
        assertThrows(ex, () -> list.setAll(1, 2));

        assertThrows(ex, () -> list.remove(0, 1));
        assertThrows(ex, () -> list.remove(null));
        assertThrows(ex, () -> list.remove(0));

        assertThrows(ex, () -> list.removeAll(backing));
        assertThrows(ex, () -> list.removeAll(1, 2));

        assertThrows(ex, () -> list.retainAll(backing));
        assertThrows(ex, () -> list.retainAll(1, 2));

        assertThrows(ex, () -> list.replaceAll(i -> 1));

        assertThrows(ex, () -> list.sort(Comparator.naturalOrder()));

        assertThrows(ex, () -> list.clear());

        final Iterator<Integer> iter = list.iterator();
        iter.next();
        assertThrows(ex, iter::remove);

        final ListIterator<Integer> liter = list.listIterator();
        liter.next();
        assertThrows(ex, liter::remove);
        assertThrows(ex, () -> liter.add(5));
        assertThrows(ex, () -> liter.set(3));
        assertThrows(ex, () -> list.removeIf(i -> true));
    }
}
