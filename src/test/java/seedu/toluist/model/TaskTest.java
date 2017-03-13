package seedu.toluist.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
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
        assertEquals(floatingTask.getDescription(), floatingTaskDescripion);
        assertSimilarTags(floatingTask, tag1);
    }

    @Test
    public void addTag_newTag_tagIsAdded() {
        floatingTask.addTag(tag3);
        assertSimilarTags(floatingTask, tag1, tag3);
    }

    @Test
    public void addTag_existingTag_tagIsNotAddedTwice() {
        floatingTask.addTag(tag1);
        assertSimilarTags(floatingTask, tag1);
    }

    @Test
    public void addTag_tagWithSameTagName_tagIsNotAddedTwice() {
        floatingTask.addTag(tag2);
        assertSimilarTags(floatingTask, tag1);
    }

    @Test
    public void removeTag_existingTag_tagIsRemoved() {
        floatingTask.removeTag(tag1);
        assertSimilarTags(floatingTask);
    }

    @Test
    public void removeTag_tagWithSameTagName_tagIsRemoved() {
        floatingTask.removeTag(tag2);
        assertSimilarTags(floatingTask);
    }

    @Test
    public void addTag_nonExistingTag_noEffect() {
        floatingTask.addTag(tag2);
        assertSimilarTags(floatingTask, tag1);
    }

    @Test
    public void replaceTag_emptyTagList_replaceTags() {
        floatingTask.replaceTags(new ArrayList<>());
        assertSimilarTags(floatingTask);
    }

    @Test
    public void replaceTag_listWithSingleTag_replaceTags() {
        ArrayList<Tag> tagsList = new ArrayList<>();
        tagsList.add(tag3);

        floatingTask.replaceTags(tagsList);
        assertSimilarTags(floatingTask, tag3);
    }

    @Test
    public void replaceTag_listWithMultipleTags_replaceTags() {
        ArrayList<Tag> tagsList = new ArrayList<>();
        tagsList.add(tag3);
        tagsList.add(tag4);

        floatingTask.replaceTags(tagsList);
        assertSimilarTags(floatingTask, tag3, tag4);
    }

    @Test
    public void replaceTag_listWithEqualTags_keepUniqueTags() {
        ArrayList<Tag> tagsList = new ArrayList<>();
        tagsList.add(tag1);
        tagsList.add(tag2);
        tagsList.add(tag4);

        floatingTask.replaceTags(tagsList);
        assertSimilarTags(floatingTask, tag1, tag4);
    }

    @Test
    public void compareTo_differentEndDateTime() {
        Task floatingTask = new Task("floating");
        Task taskWithDeadline = new Task("task with deadline", LocalDateTime.now());
        Task event = new Task("event", LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));

        assertEquals(taskWithDeadline.compareTo(event), -1);
        assertEquals(event.compareTo(floatingTask), -1);
    }

    @Test
    public void compareTo_sameEndDateTimeDifferentStartDateTime() {
        LocalDateTime to = LocalDateTime.now();
        Task event1 = new Task("event 1", to.minusDays(1), to);
        Task event2 = new Task("event 2", to.minusDays(2), to);
        assertEquals(event2.compareTo(event1), -1);
    }

    @Test
    public void compareTo_sameEndDateTimeSameStartDateTimeDifferentPriority() {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from  = to.minusDays(1);
        Task event1 = new Task("event 1", from, to);
        Task event2 = new Task("event 2", from, to);
        event1.setTaskPriority(Task.TaskPriority.HIGH);
        event2.setTaskPriority(Task.TaskPriority.LOW);
        assertEquals(event2.compareTo(event1), -1);

        Task taskWithDeadline1 = new Task("task 1", to);
        Task taskWithDeadline2 = new Task("task 2", to);
        taskWithDeadline1.setTaskPriority(Task.TaskPriority.LOW);
        taskWithDeadline2.setTaskPriority(Task.TaskPriority.HIGH);
        assertEquals(taskWithDeadline1.compareTo(taskWithDeadline2), -1);

        Task floatingTask1 = new Task("floating 1");
        Task floatingTask2 = new Task("floating 2");
        floatingTask1.setTaskPriority(Task.TaskPriority.HIGH);
        floatingTask2.setTaskPriority(Task.TaskPriority.LOW);
        assertEquals(floatingTask2.compareTo(floatingTask1), -1);
    }

    @Test
    public void compareTo_sameEndDateTimeSameStartDateTimeSamePriorityDifferentDescription() {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from  = to.minusDays(1);
        Task event1 = new Task("event 1", from, to);
        Task event2 = new Task("event 2", from, to);
        assertEquals(event1.compareTo(event2), -1);

        Task taskWithDeadline1 = new Task("task 1", to);
        Task taskWithDeadline2 = new Task("task 2", to);
        assertEquals(taskWithDeadline1.compareTo(taskWithDeadline2), -1);

        Task floatingTask1 = new Task("floating 1");
        Task floatingTask2 = new Task("floating 2");
        assertEquals(floatingTask1.compareTo(floatingTask2), -1);
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
