package seedu.ezdo.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.EmptyStackException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
//@@author A0139248X
public class FixedStackTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private FixedStack<String> stack;

    @Test
    public void pop_empty_exception() throws EmptyStackException {
        stack = new FixedStack<String>(ModelManager.STACK_CAPACITY);
        thrown.expect(EmptyStackException.class);
        stack.pop();
    }

    @Test
    public void push_wrapAround_success() {
        stack = new FixedStack<String>(ModelManager.STACK_CAPACITY);
        stack.push("hey");
        stack.push("meow");
        stack.push("kitty");
        stack.push("dog");
        stack.push("BAZINGA");
        stack.push("world");
        assertTrue("world".equals(stack.pop()));
    }

    @Test
    public void push_isEmpty_false() {
        stack = new FixedStack<String>(ModelManager.STACK_CAPACITY);
        stack.push("MEOWR");
        assertFalse(stack.isEmpty());
    }

    @Test
    public void pop_indexZeroSuccess_equalsPushed() {
        stack = new FixedStack<String>(ModelManager.STACK_CAPACITY);
        stack.push("MEOWR");
        String popped = stack.pop();
        assertTrue(("MEOWR").equals(popped));
    }

    @Test
    public void pop_otherIndexSuccess_equalsPushed() {
        stack = new FixedStack<String>(ModelManager.STACK_CAPACITY);
        stack.push("lol");
        stack.push("omg");
        String popped = stack.pop();
        assertTrue(("omg").equals(popped));
    }

    @Test
    public void clear_isEmpty_true() {
        stack = new FixedStack<String>(ModelManager.STACK_CAPACITY);
        stack.push("omg");
        stack.push("hello");
        stack.clear();
        assertTrue(stack.isEmpty());
    }
}
