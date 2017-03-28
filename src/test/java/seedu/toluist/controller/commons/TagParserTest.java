//@@author A0127545A
package seedu.toluist.controller.commons;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import seedu.toluist.model.Tag;

public class TagParserTest {
    //---------------- Tests for parseTags --------------------------------------

    @Test
    public void parseTags_emptyTag() {
        Set<Tag> actual = TagParser.parseTags("   ");
        Set<Tag> expected = new TreeSet<>(Arrays.asList());
        assertTrue(actual.equals(expected));
    }

    @Test
    public void parseTags_singleTag() {
        Set<Tag> actual = TagParser.parseTags("  yoohoo   ");
        Set<Tag> expected = new TreeSet<>(Arrays.asList(new Tag("yoohoo")));
        assertTrue(actual.equals(expected));
    }

    @Test
    public void parseTags_multipleTags() {
        Set<Tag> actual = TagParser.parseTags("  yoohoo lololol   wheeeeeee     ");
        Set<Tag> expected = new TreeSet<>(Arrays.asList(new Tag("yoohoo"), new Tag("lololol"),
                new Tag("wheeeeeee")));
        assertTrue(actual.equals(expected));
    }
}
