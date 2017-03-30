package seedu.address.model.datastructure;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test UndoPair methods
 */
public class UndoPairTest {

    @Test
    public void undoPair_TestHashCode() {
        UndoPair<String, String> undoPair1 = new UndoPair<String, String>("Command1", "Test1");
        UndoPair<String, String> undoPair2 = new UndoPair<String, String>("Command1", "Test1");
        UndoPair<String, String> undoPair3 = new UndoPair<String, String>("Command2", "Test2");
        assertTrue(undoPair1.hashCode() == undoPair2.hashCode());
        assertFalse(undoPair1.hashCode() == undoPair3.hashCode());
    }

    @Test
    public void undoPair_TestEquals() {
        UndoPair<String, String> undoPair1 = new UndoPair<String, String>("Command1", "Test1");
        UndoPair<String, String> undoPair2 = new UndoPair<String, String>("Command1", "Test1");
        UndoPair<String, String> undoPair3 = new UndoPair<String, String>("Command1", "Test2");
        UndoPair<String, String> undoPair4 = new UndoPair<String, String>("Command1", "Test2");
        UndoPair<String, String> undoPair5 = new UndoPair<String, String>("Command2", "Test2");
        assertTrue(undoPair1.equals(undoPair2));
        assertFalse(undoPair1.equals(undoPair3));
        assertFalse(undoPair1.equals(undoPair4));
        assertFalse(undoPair1.equals(undoPair5));
        assertFalse(undoPair1.equals(null));
    }
}
