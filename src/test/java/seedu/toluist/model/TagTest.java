package seedu.toluist.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for Tag model
 */
public class TagTest {

    @Test
    public void testConstruct() {
        String tagName = "I am a tag";
        Tag tag = new Tag(tagName);
        assertEquals(tag.tagName, tagName);
    }

    @Test
    public void testEquals_SameTag_isTrue() {
        String tagName = "CS2101";
        Tag tag = new Tag(tagName);
        assertEquals(tag, tag);
    }

    @Test
    public void testEquals_TagsWithIdenticalName_isTrue() {
        String tagName = "CS2101";
        Tag tag1 = new Tag(tagName);
        Tag tag2 = new Tag(tagName);
        assertEquals(tag1, tag2);
    }

    @Test
    public void testHashCode_InsertTagsWithSameNameIntoSet_onlyOneItemInSet() {
        String tagName = "CS2103";
        Tag tag1 = new Tag(tagName);
        Tag tag2 = new Tag(tagName);

        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tag1);
        tagSet.add(tag2);

        assertEquals(tagSet.size(), 1);
    }

    @Test
    public void testCompareTo_InsertTagsToListThenSort_correctOrder() {
        String tagName1 = "CS2103";
        Tag tag1 = new Tag(tagName1);

        String tagName2 = "A";
        Tag tag2 = new Tag(tagName2);

        List<Tag> tagList = new ArrayList<>();
        tagList.add(tag1);
        tagList.add(tag2);

        Collections.sort(tagList);

        assertEquals(tagList.get(0), tag2);
        assertEquals(tagList.get(1), tag1);
    }
}
