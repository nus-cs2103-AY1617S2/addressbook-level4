package seedu.ezdo.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ezdo.model.todo.Recur;
//@@author A0139177W
public class RecurTest {

    @Test
    public void isValidRecur() {
        // invalid recur interval
        assertFalse(Recur.isValidRecur(" "));
        assertFalse(Recur.isValidRecur("everyday"));
        assertFalse(Recur.isValidRecur("week"));
        assertFalse(Recur.isValidRecur("month"));
        assertFalse(Recur.isValidRecur("year"));

        // valid recur interval
        assertTrue(Recur.isValidRecur(""));
        assertTrue(Recur.isValidRecur("daily"));
        assertTrue(Recur.isValidRecur("weekly"));
        assertTrue(Recur.isValidRecur("monthly"));
        assertTrue(Recur.isValidRecur("yearly"));
    }

    @Test
    public void isRecur() throws Exception {
        Recur noRecur = new Recur("");
        Recur validRecur = new Recur("monthly");
        assertFalse(noRecur.isRecur());
        assertTrue(validRecur.isRecur());
    }

    @Test
    public void printToString() throws Exception {
        Recur testRecur = new Recur("daily");
        assertEquals(testRecur.toString(), "daily");
    }
    
    @Test
    public void testEquals() throws Exception {
        Recur testRecur = new Recur("daily");
        Recur testRecur2 = testRecur;
        assertTrue(testRecur.equals(testRecur2));
    }
    
    @Test
    public void hashCode_equals() throws Exception {
        Recur testRecur = new Recur("daily");
        assertEquals(testRecur.hashCode(), (new Recur("daily")).hashCode());
    }
}

