package seedu.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.model.task.Title;

public class TitleTest {

    @Test
    public void isValidTitle() {
        // invalid title
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only
        assertFalse(Title.isValidTitle("^")); // only non-alphanumeric characters
        assertFalse(Title.isValidTitle("peter*")); // contains non-alphanumeric characters

        // valid title
        assertTrue(Title.isValidTitle("buy groceries")); // alphabets only
        assertTrue(Title.isValidTitle("12345")); // numbers only
        assertTrue(Title.isValidTitle("buy 10 pizzas")); // alphanumeric characters
        assertTrue(Title.isValidTitle("Buy Double Mac Spicy")); // with capital letters
        assertTrue(Title.isValidTitle("just buy itttttttttttttttttttttttttttt")); // long names
    }
}
