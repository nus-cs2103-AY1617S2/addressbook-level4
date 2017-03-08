package seedu.toluist.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for Task model
 */
public class TaskTest {
    private Task floatingTask;
    private final String floatingTaskDescripion = "floating task";
    private final Tag tag1 = new Tag("nature");
    private final Tag tag2 = new Tag("nature");
    private final Tag tag3 = new Tag("wolf");
    private final Tag tag4 = new Tag("cat");


    @Before
    public void setUp() {
        floatingTask = new Task(floatingTaskDescripion);
        floatingTask.addTag(tag1);
    }

    @Test
    public void testConstruct_floatingTask() {
        assertEquals(floatingTask.description, floatingTaskDescripion);
        assertSimilarTags(floatingTask, tag1);
    }

    @Test
    public void addTag_NewTag_tagIsAdded() {
        floatingTask.addTag(tag3);
        assertSimilarTags(floatingTask, tag1, tag3);
    }

    @Test
    public void addTag_ExistingTag_tagIsNotAddedTwice() {
        floatingTask.addTag(tag1);
        assertSimilarTags(floatingTask, tag1);
    }

    @Test
    public void addTag_TagWithSameTagName_tagIsNotAddedTwice() {
        floatingTask.addTag(tag2);
        assertSimilarTags(floatingTask, tag1);
    }

    @Test
    public void removeTag_ExistingTag_tagIsRemoved() {
        floatingTask.removeTag(tag1);
        assertSimilarTags(floatingTask);
    }

    @Test
    public void removeTag_TagWithSameTagName_tagIsRemoved() {
        floatingTask.removeTag(tag2);
        assertSimilarTags(floatingTask);
    }

    @Test
    public void addTag_NonExistingTag_noEffect() {
        floatingTask.addTag(tag2);
        assertSimilarTags(floatingTask, tag1);
    }

    @Test
    public void replaceTag_EmptyTagList_replaceTags() {
        floatingTask.replaceTags(new ArrayList<>());
        assertSimilarTags(floatingTask);
    }

    @Test
    public void replaceTag_ListWithSingleTag_replaceTags() {
        ArrayList<Tag> tagsList = new ArrayList<>();
        tagsList.add(tag3);

        floatingTask.replaceTags(tagsList);
        assertSimilarTags(floatingTask, tag3);
    }

    @Test
    public void replaceTag_ListWithMultipleTags_replaceTags() {
        ArrayList<Tag> tagsList = new ArrayList<>();
        tagsList.add(tag3);
        tagsList.add(tag4);

        floatingTask.replaceTags(tagsList);
        assertSimilarTags(floatingTask, tag3, tag4);
    }

    @Test
    public void replaceTag_ListWithEqualTags_keepUniqueTags() {
        ArrayList<Tag> tagsList = new ArrayList<>();
        tagsList.add(tag1);
        tagsList.add(tag2);
        tagsList.add(tag4);

        floatingTask.replaceTags(tagsList);
        assertSimilarTags(floatingTask, tag1, tag4);
    }

    /**
     * Check if the set of tags in the tasks and the tags passed in are identical
     */
    private void assertSimilarTags(Task task, Tag... tags) {
        Set<Tag> tagSet = new HashSet<>();
        for (Tag tag : tags) {
            tagSet.add(tag);
        }

        assertEquals(floatingTask.getAllTags(), tagSet);
    }
}
