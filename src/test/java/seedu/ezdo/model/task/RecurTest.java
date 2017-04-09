package seedu.ezdo.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.integration.junit4.JMockit;
import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.model.todo.Recur;

//@@author A0139248X
@RunWith(JMockit.class)
public class RecurTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
  //@@author A0139177W
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

    //@@author A0139248X
    @Test
    public void constructor_isValidRecurFalse_throwsIVE() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        new Expectations(Recur.class) {
            {
                Recur.isValidRecur("test"); result = false;
            }
        };
        new Recur("test");
    }
}
