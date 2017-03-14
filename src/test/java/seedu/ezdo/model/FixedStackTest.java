package seedu.ezdo.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.EmptyStackException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FixedStackTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private FixedStack<String> stack;

    @Test
    public void popEmpty_Exception() throws EmptyStackException {
        stack = new FixedStack<String>(5);
        thrown.expect(EmptyStackException.class);
        stack.pop();
    }

    @Test
    public void push_success_notEmpty() {
        stack = new FixedStack<String>(5);
        stack.push("MEOWR");
        assertFalse(stack.isEmpty());
    }

    @Test
    public void popIndexZero_success_equalsPushed() {
        stack = new FixedStack<String>(5);
        stack.push("MEOWR");
        String popped = stack.pop();
        assertTrue(("MEOWR").equals(popped));
    }

    @Test
    public void pop_OtherIndex_success_equalsPushed() {
        stack = new FixedStack<String>(5);
        stack.push("lol");
        stack.push("omg");
        String popped = stack.pop();
        assertTrue(("omg").equals(popped));
    }
}
