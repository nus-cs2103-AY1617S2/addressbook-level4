package seedu.toluist.model;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for CommandAliasConfig
 */
public class CommandAliasConfigTest {
    private final CommandAliasConfig aliasConfig = new CommandAliasConfig();
    private final CommandAliasConfig aliasConfigWithReservedWords = new CommandAliasConfig();
    private final String resersedWordA = "add";
    private final String resersedWordB = "update";


    @Before
    public void setUp() {
        Set<String> reservedWords = new HashSet<>();
        reservedWords.add(resersedWordA);
        reservedWords.add(resersedWordB);
        aliasConfigWithReservedWords.setReservedKeywords(reservedWords);
    }

    @Test
    public void isReservedWord_reservedWord_isTrue() {
        assert(aliasConfigWithReservedWords.isReservedWord(resersedWordA));
    }

    @Test
    public void isReservedWord_nonReservedWord_isFalse() {
        String nonReservedWord = "delete";
        assert(!aliasConfigWithReservedWords.isReservedWord(nonReservedWord));
    }

    @Test
    public void addAlias_validAlias_isSuccessful() {
        String alias = "add";
        String command = "test";
        aliasConfig.setAlias(alias, command);
        assert(aliasConfig.isAlias(alias));
    }

    @Test
    public void addAlias_nonValidAlias_fail() {
        String command = "test";
        aliasConfigWithReservedWords.setAlias(resersedWordA, command);
        assert(!aliasConfig.isAlias(resersedWordA));
    }

    @Test
    public void removeAlias_existingAlias_replacesData() {
        String alias = "add";
        String command = "test";
        aliasConfig.setAlias(alias, command);
        aliasConfig.removeAlias(alias);
        assert(!aliasConfig.isAlias(alias));
    }

    @Test
    public void removeAlias_nonExistentAlias_notReplacesData() {
        String alias = "add";
        String command = "test";
        String nonAlias = "add1";
        aliasConfig.setAlias(alias, command);
        aliasConfig.removeAlias(nonAlias);
        assert(aliasConfig.isAlias(alias));
    }

    @Test
    public void getAliasMapping_emptyMapping_isCorrect() {
        assertEquals(aliasConfig.getAliasMapping(), new HashMap<String, String>());
    }

    @Test
    public void getAliasMapping_OneAlias_isCorrect() {
        String alias = "add";
        String command = "test";
        aliasConfig.setAlias(alias, command);

        Map<String, String> otherMapping = new HashMap<>();
        otherMapping.put(alias, command);

        assertEquals(aliasConfig.getAliasMapping(), otherMapping);
    }

    @Test
    public void getAliasMapping_ManyAliases_isCorrect() {
        String alias1 = "add";
        String command1 = "test";
        String alias2 = "remove";
        String command2 = "test2";
        String alias3 = "update";
        String command3 = "yes";

        aliasConfig.setAlias(alias1, command1);
        aliasConfig.setAlias(alias2, command2);
        aliasConfig.setAlias(alias3, command3);

        Map<String, String> otherMapping = new HashMap<>();
        otherMapping.put(alias1, command1);
        otherMapping.put(alias2, command2);
        otherMapping.put(alias3, command3);

        assertEquals(aliasConfig.getAliasMapping(), otherMapping);
    }

    @Test
    public void dealias_commandInputWithAlias_replaceAlias() {
        String input = "d a task";
        String alias = "d";
        String command = "add that";
        String output = "add that a task";

        aliasConfig.setAlias(alias, command);
        assertEquals(aliasConfig.dealias(input), output);
    }

    @Test
    public void dealias_commandInputWithoutAlias_returnsCommandInput() {
        String input = "d a task";
        assertEquals(aliasConfig.dealias(input), input);
    }
}
