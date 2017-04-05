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
        assertDispatcherSuggestions("");
        assertDispatcherSuggestions(" ");
    }

    @Test
    public void getSuggestions_partialCommandWord() {
        assertDispatcherSuggestions("m", "mark", "mark incomplete");
        assertDispatcherSuggestions("a", "add", "alias");
    }

    @Test
    public void getSuggestions_fullCommandWord() {
        assertDispatcherSuggestions("add", "add");
    }

    @Test
    public void getSuggestions_caseInsensitiveCommandWord() {
        assertDispatcherSuggestions("A", "add", "alias");
    }

    @Test
    public void getSuggestions_commandWordWithWhiteSpaces() {
        assertDispatcherSuggestions(" a", "add", "alias");
    }

    @Test
    public void getSuggestions_commandWordAlias() {
        assertDispatcherSuggestions("mi", "mark incomplete");
    }

    @Test
    public void getPredictedCommand_commandWordAliasCaseInsensitive() {
        assertDispatcherSuggestions("MI", "mark incomplete");
    }

    @Test
    public void getSuggestions_suggestKeywords() {
        assertDispatcherSuggestions("add ", "/by", "/from", "/to", "/repeat",
                "/repeatuntil", "/tags", "/priority");
        assertDispatcherSuggestions("add a task /", "/by", "/from", "/to", "/repeat",
                "/repeatuntil", "/tags", "/priority");
        assertDispatcherSuggestions("add /tags sth ", "/by", "/from", "/to", "/repeat",
                "/repeatuntil", "/priority");
    }

    @Test
    public void getSuggestions_invalidKeywords() {
        assertDispatcherSuggestions("help asdfasdfsadfaf");
    }

    @Test
    public void getSuggestions_keywordWithArgumentsOverMoreKeywords() {
        assertDispatcherSuggestions("add task /repeat ", "daily", "monthly", "weekly",
                "yearly");
    }

    @Test
    public void getSuggestions_noSuggestKeywordArgumentsIfAlreadyThere() {
        assertDispatcherSuggestions("add task /repeat daily ", "/by", "/from", "/to", "/repeatuntil",
                "/tags", "/priority");
    }

    /**
     * Helper assert method to check for suggestions to command
     * @param command command string
     * @param suggestions varargs of expected suggestions
     */
    private void assertDispatcherSuggestions(String command, String... suggestions) {
        SortedSet<String> expected = new TreeSet<>();
        for (String suggestion : suggestions) {
            expected.add(suggestion);
        }

        assertEquals(dispatcher.getSuggestions(command), expected);
    }
}
