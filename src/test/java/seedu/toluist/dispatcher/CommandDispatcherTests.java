package seedu.toluist.dispatcher;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import seedu.toluist.model.AliasTable;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Tests for CommandDispatcher
 */
public class CommandDispatcherTests {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private Dispatcher dispatcher = new CommandDispatcher();
    @Mock
    AliasTable aliasTable;

    @Before
    public void setUp() {
        when(aliasTable.dealias("mi")).thenReturn("mark incomplete");
    }

    @Test
    public void getPredictedCommand_EmptyCommand() {
        assertEquals(dispatcher.getPredictedCommands(""), new TreeSet<>());
    }

    @Test
    public void getPredictedCommand_partialCommandWord() {
        SortedSet<String> expected = new TreeSet<>();
        expected.add("mark");
        assertEquals(dispatcher.getPredictedCommands("m"), expected);

        SortedSet<String> expected2 = new TreeSet<>();
        expected2.add("add");
        expected2.add("alias");
        assertEquals(dispatcher.getPredictedCommands("a"), expected2);
    }

    @Test
    public void getPredictedCommand_fullCommandWord() {
        SortedSet<String> expected = new TreeSet<>();
        expected.add("add");

        assertEquals(dispatcher.getPredictedCommands("add"), expected);
    }

    @Test
    public void getPredictedCommand_fullCommand() {
        SortedSet<String> expected = new TreeSet<>();
        expected.add("add a task");

        assertEquals(dispatcher.getPredictedCommands("add a task"), expected);
    }

    @Test
    public void getPredictedCommand_alias() {
        SortedSet<String> expected = new TreeSet<>();
        expected.add("mark incomplete");

        assertEquals(dispatcher.getPredictedCommands("mi"), expected);
    }
}
