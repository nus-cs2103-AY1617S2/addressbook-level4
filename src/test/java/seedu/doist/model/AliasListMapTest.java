package seedu.doist.model;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//@@author A0140887W
public class AliasListMapTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AliasListMap aliasListMap = new AliasListMap();

    @Test
    public void constructor() {
        assertFalse(aliasListMap.getDefaultCommandWordSet().equals(Collections.EMPTY_SET));
    }

    @Test
    public void setAlias_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        aliasListMap.setAlias("hello", "not a default command");
    }

    @Test
    public void getAliasList_modify_throwsUnsupportedOperationException() {
        List<String> list = aliasListMap.getAliasList("add");
        thrown.expect(UnsupportedOperationException.class);
        list.remove(0);
    }

    @Test
    public void getAliasListMapping_modify_throwsUnsupportedOperationException() {
        Map<String, ArrayList<String>> map = aliasListMap.getAliasListMapping();
        thrown.expect(UnsupportedOperationException.class);
        map.put("lol", new ArrayList<String>());
    }
}
