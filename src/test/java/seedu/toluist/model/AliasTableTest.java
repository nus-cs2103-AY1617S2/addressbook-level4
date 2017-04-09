//@@author A0131125Y
package seedu.toluist.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for AliasTable
 */
public class AliasTableTest {
    private final AliasTable aliasTable = new AliasTable();
    private final AliasTable aliasTableWithReservedWords = new AliasTable();
    private final String resersedWordA = "add";
    private final String resersedWordB = "update";


    @Before
    public void setUp() {
        Set<String> reservedWords = new HashSet<>();
        reservedWords.add(resersedWordA);
        reservedWords.add(resersedWordB);
        aliasTableWithReservedWords.setReservedKeywords(reservedWords);
    }

    @Test
    public void equalsMethod() {
        // Tables with no aliases are equal
        AliasTable aliasTable1 = new AliasTable();
        AliasTable aliasTable2 = new AliasTable();
        assertEquals(aliasTable1, aliasTable2);

        // Table is not equal to null
        assertNotEquals(aliasTable1, null);

        // Tables with equals alias mapping are equal
        aliasTable1.setAlias("a", "add");
        aliasTable2.setAlias("a", "add");
        assertEquals(aliasTable1, aliasTable2);

        // Tables with same reserved keywords are equal
        aliasTable1.setReservedKeywords(new HashSet<String>(Arrays.asList(new String[] { "add" })));
        aliasTable2.setReservedKeywords(new HashSet<String>(Arrays.asList(new String[] { "add" })));
        assertEquals(aliasTable1, aliasTable2);
    }

    @Test
    public void isReservedWord_reservedWord_isTrue() {
        assert(aliasTableWithReservedWords.isReservedWord(resersedWordA));
    }

    @Test
    public void isReservedWord_nonReservedWord_isFalse() {
        String nonReservedWord = "delete";
        assert(!aliasTableWithReservedWords.isReservedWord(nonReservedWord));
    }

    @Test
    public void addAlias_validAlias_isSuccessful() {
        String alias = "add";
        String command = "test";
        aliasTable.setAlias(alias, command);
        assert(aliasTable.isAlias(alias));
    }

    @Test
    public void addAlias_nonValidAlias_fail() {
        String command = "test";
        aliasTableWithReservedWords.setAlias(resersedWordA, command);
        assert(!aliasTable.isAlias(resersedWordA));
    }

    @Test
    public void removeAlias_existingAlias_replacesData() {
        String alias = "add";
        String command = "test";
        aliasTable.setAlias(alias, command);
        aliasTable.removeAlias(alias);
        assert(!aliasTable.isAlias(alias));
    }

    @Test
    public void removeAlias_nonExistentAlias_notReplacesData() {
        String alias = "add";
        String command = "test";
        String nonAlias = "add1";
        aliasTable.setAlias(alias, command);
        aliasTable.removeAlias(nonAlias);
        assert(aliasTable.isAlias(alias));
    }

    @Test
    public void getAliasMapping_emptyMapping_isCorrect() {
        assertEquals(aliasTable.getAliasMapping(), new HashMap<String, String>());
    }

    @Test
    public void getAliasMapping_OneAlias_isCorrect() {
        String alias = "add";
        String command = "test";
        aliasTable.setAlias(alias, command);

        Map<String, String> otherMapping = new HashMap<>();
        otherMapping.put(alias, command);

        assertEquals(aliasTable.getAliasMapping(), otherMapping);
    }

    @Test
    public void getAliasMapping_ManyAliases_isCorrect() {
        String alias1 = "add";
        String command1 = "test";
        String alias2 = "remove";
        String command2 = "test2";
        String alias3 = "update";
        String command3 = "yes";

        aliasTable.setAlias(alias1, command1);
        aliasTable.setAlias(alias2, command2);
        aliasTable.setAlias(alias3, command3);

        Map<String, String> otherMapping = new HashMap<>();
        otherMapping.put(alias1, command1);
        otherMapping.put(alias2, command2);
        otherMapping.put(alias3, command3);

        assertEquals(aliasTable.getAliasMapping(), otherMapping);
    }

    @Test
    public void dealias_commandInputWithAlias_replaceAlias() {
        String input = "d a task";
        String alias = "d";
        String command = "add that";
        String output = "add that a task";

        aliasTable.setAlias(alias, command);
        assertEquals(aliasTable.dealias(input), output);
    }

    @Test
    public void dealias_commandInputWithoutAlias_returnsCommandInput() {
        String input1 = "d a task";
        assertEquals(aliasTable.dealias(input1), input1);

        aliasTable.setAlias("a", "add");
        String input2 = "add task";
        // Won't match partial word
        assertEquals(aliasTable.dealias(input2), input2);
    }
}
