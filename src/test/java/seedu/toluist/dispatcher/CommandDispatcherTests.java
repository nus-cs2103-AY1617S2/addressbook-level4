package seedu.toluist.dispatcher;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import seedu.toluist.model.AliasTable;

/**
 * Tests for CommandDispatcher
 */
public class CommandDispatcherTests {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private Dispatcher dispatcher = new CommandDispatcher();
    @Mock
    private AliasTable aliasTable;

    @Before
    public void setUp() {
        when(aliasTable.dealias("m")).thenReturn("m");
        when(aliasTable.dealias("mi")).thenReturn("mark incomplete");
        Map<String, String> aliasMapping = new HashMap();
        aliasMapping.put("mi", "mark incomplete");
        when(aliasTable.getAliasMapping()).thenReturn(aliasMapping);
        dispatcher.setAliasConfig(aliasTable);
    }

    @Test
    public void getSuggestions_emptyCommand() {
        assertEquals(dispatcher.getSuggestions(""), new TreeSet<>());
    }

    @Test
    public void getSuggestions_partialCommandWord() {
        SortedSet<String> expected = new TreeSet<>();
        expected.add("mark");
        expected.add("mark incomplete");
        assertEquals(dispatcher.getSuggestions("m"), expected);

        SortedSet<String> expected2 = new TreeSet<>();
        expected2.add("add");
        expected2.add("alias");
        assertEquals(dispatcher.getSuggestions("a"), expected2);
    }

    @Test
    public void getSuggestions_fullCommandWord() {
        SortedSet<String> expected = new TreeSet<>();
        expected.add("add");

        assertEquals(dispatcher.getSuggestions("add"), expected);
    }

    @Test
    public void getSuggestions_caseInsensitiveCommandWord() {
        SortedSet<String> expected = new TreeSet<>();
        expected.add("add");
        expected.add("alias");
        assertEquals(dispatcher.getSuggestions("A"), expected);
    }

    @Test
    public void getSuggestions_commandWordWithWhiteSpaces() {
        SortedSet<String> expected = new TreeSet<>();
        expected.add("add");
        expected.add("alias");
        assertEquals(dispatcher.getSuggestions(" a"), expected);
    }

    @Test
    public void getSuggestions_commandWordAlias() {
        SortedSet<String> expected = new TreeSet<>();
        expected.add("mark incomplete");

        assertEquals(dispatcher.getSuggestions("mi"), expected);
    }

    @Test
    public void getPredictedCommand_commandWordAliasCaseInsensitive() {
        SortedSet<String> expected = new TreeSet<>();
        expected.add("mark incomplete");

        assertEquals(dispatcher.getSuggestions("MI"), expected);
    }

    @Test
    public void getSuggestions_suggestKeywords() {
        SortedSet<String> expected = new TreeSet<>();
        expected.add("/by");
        expected.add("/from");
        expected.add("/to");
        expected.add("/repeat");
        expected.add("/repeatuntil");
        expected.add("/tags");
        expected.add("/priority");

        assertEquals(dispatcher.getSuggestions("add "), expected);
        assertEquals(dispatcher.getSuggestions("add a task /"), expected);

        expected.remove("/tags");
        assertEquals(dispatcher.getSuggestions("add task /tags "), expected);
    }
}
