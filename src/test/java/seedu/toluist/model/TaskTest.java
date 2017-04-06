//@@author A0131125Y
package seedu.toluist.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.toluist.commons.util.DateTimeUtil;

/**
 * Tests for Task model
 */
public class TaskTest {
    private Task testTask;
    private final String taskDescription = "a description";

    private final Tag tag1 = new Tag("nature");
    private final Tag tag2 = new Tag("nature");
    private final Tag tag3 = new Tag("wolf");
    private final Tag tag4 = new Tag("cat");


    @Before
    public void setUp() {
        testTask = new Task(taskDescription);
        testTask.addTag(tag1);
    }

    @Test
    public void testConstruct_testTask() {
        Task task = new Task(taskDescription);
        assertEquals(task.getDescription(), taskDescription);
        assertNull(task.getStartDateTime());
        assertNull(task.getEndDateTime());
        assertNull(task.getCompletionDateTime());
        assertEquals(task.getTaskPriority(), Task.TaskPriority.LOW);
        assertTrue(task.getAllTags().isEmpty());
        assertTrue(task.isFloatingTask());
    }

    @Test
    public void testConstruct_taskWithDeadline() {
        LocalDateTime dateTime = LocalDateTime.now();
        Task task = new Task(taskDescription, dateTime);
        assertEquals(task.getDescription(), taskDescription);
        assertNull(task.getStartDateTime());
        assertEquals(task.getEndDateTime(), dateTime);
        assertNull(task.getCompletionDateTime());
        assertEquals(task.getTaskPriority(), Task.TaskPriority.LOW);
        assertTrue(task.getAllTags().isEmpty());
        assertTrue(task.isTaskWithDeadline());
    }

    @Test
    public void testConstruct_event() {
        LocalDateTime from = LocalDateTime.now().minusDays(1);
        LocalDateTime to = LocalDateTime.now();
        Task event = new Task(taskDescription, from, to);
        assertEquals(event.getDescription(), taskDescription);
        assertEquals(event.getStartDateTime(), from);
        assertEquals(event.getEndDateTime(), to);
        assertNull(event.getCompletionDateTime());
        assertEquals(event.getTaskPriority(), Task.TaskPriority.LOW);
        assertTrue(event.getAllTags().isEmpty());
        assertTrue(event.isEvent());
    }

    @Test
    public void setDeadline() {
        LocalDateTime now = LocalDateTime.now();
        testTask.setDeadLine(now);
        assertEquals(testTask.getEndDateTime(), now);
        // task is now a task with deadline
        assertTrue(testTask.isTaskWithDeadline());
    }

    @Test
    public void setFromTo() {
        LocalDateTime from = LocalDateTime.now().minusDays(1);
        LocalDateTime to = LocalDateTime.now();
        testTask.setFromTo(from, to);
        assertEquals(testTask.getStartDateTime(), from);
        assertEquals(testTask.getEndDateTime(), to);
        // task is now an event
        assertTrue(testTask.isEvent());
    }

    @Test
    public void setComplete() {
        LocalDateTime now = LocalDateTime.now();
        // Set completed
        testTask.setCompleted(true);
        assertNotNull(testTask.getCompletionDateTime());
        assertTrue(testTask.isCompleted());
        assertTrue(DateTimeUtil.isBeforeOrEqual(now, testTask.getCompletionDateTime()));

        // Set incomplete
        testTask.setCompleted(false);
        assertNull(testTask.getCompletionDateTime());
        assertFalse(testTask.isCompleted());
    }

    @Test
    public void isOverdue() {
        // incomplete task with end date passed is overdue
        testTask.setDeadLine(LocalDateTime.now().minusDays(1));
        assertTrue(testTask.isOverdue());

        // incomplete task with end date in future is not overdue
        testTask.setDeadLine(LocalDateTime.MAX);
        assertFalse(testTask.isOverdue());

        // complete task with end date passed is not overdue
        testTask.setDeadLine(LocalDateTime.MIN);
        testTask.setCompleted(true);
        assertTrue(testTask.isCompleted());
        assertFalse(testTask.isOverdue());
    }

    @Test
    public void addTag_newTag_tagIsAdded() {
        testTask.addTag(tag3);
        assertSimilarTags(testTask, tag1, tag3);
    }

    @Test
    public void addTag_existingTag_tagIsNotAddedTwice() {
        testTask.addTag(tag1);
        assertSimilarTags(testTask, tag1);
    }

    @Test
    public void addTag_tagWithSameTagName_tagIsNotAddedTwice() {
        testTask.addTag(tag2);
        assertSimilarTags(testTask, tag1);
    }

    @Test
    public void removeTag_existingTag_tagIsRemoved() {
        testTask.removeTag(tag1);
        assertSimilarTags(testTask);
    }

    @Test
    public void removeTag_tagWithSameTagName_tagIsRemoved() {
        testTask.removeTag(tag2);
        assertSimilarTags(testTask);
    }

    @Test
    public void removeTag_nonExistingTag_noEffect() {
        testTask.removeTag(tag3);
        assertSimilarTags(testTask, tag1);
    }

    @Test
    public void replaceTag_emptyTagList_replaceTags() {
        testTask.replaceTags(new ArrayList<>());
        assertSimilarTags(testTask);
    }

    @Test
    public void replaceTag_listWithSingleTag_replaceTags() {
        ArrayList<Tag> tagsList = new ArrayList<>();
        tagsList.add(tag3);

        testTask.replaceTags(tagsList);
        assertSimilarTags(testTask, tag3);
    }

    @Test
    public void replaceTag_listWithMultipleTags_replaceTags() {
        ArrayList<Tag> tagsList = new ArrayList<>();
        tagsList.add(tag3);
        tagsList.add(tag4);

        testTask.replaceTags(tagsList);
        assertSimilarTags(testTask, tag3, tag4);
    }

    @Test
    public void replaceTag_listWithEqualTags_keepUniqueTags() {
        ArrayList<Tag> tagsList = new ArrayList<>();
        tagsList.add(tag1);
        tagsList.add(tag2);
        tagsList.add(tag4);

        testTask.replaceTags(tagsList);
        assertSimilarTags(testTask, tag1, tag4);
    }

    @Test
    public void compareTo_differentPriority() {
        Task.sortBy("default");
        Task testTask = new Task("floating");
        Task highPriorityTask = new Task("high priority");
        Task event = new Task("event", LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        highPriorityTask.setTaskPriority(Task.TaskPriority.HIGH);

        assertEquals(highPriorityTask.compareTo(event), -1);
        assertEquals(highPriorityTask.compareTo(testTask), -1);
    }

    @Test
    public void compareTo_samePriorityDifferentEndDateTime() {
        Task.sortBy("default");
        Task testTask = new Task("floating");
        Task taskWithDeadline = new Task("task with deadline", LocalDateTime.now());
        Task event = new Task("event", LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        assertEquals(taskWithDeadline.compareTo(event), -1);
        assertEquals(event.compareTo(testTask), -1);
    }

    @Test
    public void compareTo_sameEndDateTimeSamePriorityDifferentStartDateTime() {
        Task.sortBy("default");
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from  = to.minusDays(1);
        Task event1 = new Task("event 1", from, to);
        Task event2 = new Task("event 2", from.minusDays(1), to);
        assertEquals(event2.compareTo(event1), -1);
    }

    @Test
    public void compareTo_sameEndDateTimeSameStartDateTimeSamePriorityDifferentDescription() {
        Task.sortBy("default");
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from  = to.minusDays(1);
        Task event1 = new Task("event 1", from, to);
        Task event2 = new Task("event 2", from, to);
        assertEquals(event1.compareTo(event2), -1);

        Task taskWithDeadline1 = new Task("task 1", to);
        Task taskWithDeadline2 = new Task("task 2", to);
        assertEquals(taskWithDeadline1.compareTo(taskWithDeadline2), -1);

        Task testTask1 = new Task("floating 1");
        Task testTask2 = new Task("floating 2");
        assertEquals(testTask1.compareTo(testTask2), -1);
    }

    @Test
    public void compareTo_nonOverdueOverdue() {
        Task.sortBy("default");
        Task task1 = new Task("yesterday", LocalDateTime.now().minusDays(1));
        task1.setCompleted(true);
        Task task2 = new Task("today", LocalDateTime.now());

        assertEquals(task2.compareTo(task1), -1);
    }

    @Test
    public void isWithinInterval_nullFromNullTo() {
        Task floatingTask = new Task("floating");
        Task taskWithDeadline = new Task("task with deadline", LocalDateTime.MIN);
        Task event = new Task("event", LocalDateTime.MIN, LocalDateTime.MAX);

        assertTrue(floatingTask.isWithinInterval(null, null));
        assertTrue(taskWithDeadline.isWithinInterval(null, null));
        assertTrue(event.isWithinInterval(null, null));
    }

    @Test
    public void isWithinInterval_nonNullFromNullTo() {
        Task floatingTask = new Task("floating");
        Task taskWithDeadline = new Task("task with deadline", LocalDateTime.MIN);
        Task event = new Task("event", LocalDateTime.MIN, LocalDateTime.MAX);

        assertTrue(floatingTask.isWithinInterval(LocalDateTime.MIN, null));
        assertTrue(taskWithDeadline.isWithinInterval(LocalDateTime.MIN, null));
        assertTrue(event.isWithinInterval(LocalDateTime.MIN, null));
    }

    @Test
    public void isWithinInterval_nullFromNonNullTo() {
        Task floatingTask = new Task("floating");
        Task taskWithDeadline = new Task("task with deadline", LocalDateTime.MIN);
        Task event = new Task("event", LocalDateTime.MIN, LocalDateTime.MAX);

        assertTrue(floatingTask.isWithinInterval(null, LocalDateTime.MIN));
        assertTrue(taskWithDeadline.isWithinInterval(null, LocalDateTime.MIN));
        assertTrue(event.isWithinInterval(null, LocalDateTime.MIN));
    }

    @Test
    public void isWithinInterval_nonNullFromNonNullTo() {
        Task floatingTask = new Task("floating");
        Task taskWithDeadline1 = new Task("task with deadline", LocalDateTime.MAX);
        Task taskWithDeadline2 = new Task("task with deadline", LocalDateTime.MIN);
        Task event1 = new Task("event", LocalDateTime.MIN, LocalDateTime.MIN.plusDays(2));
        Task event2 = new Task("event", LocalDateTime.MAX.minusDays(2), LocalDateTime.MAX);

        assertFalse(floatingTask.isWithinInterval(LocalDateTime.MIN, LocalDateTime.MAX));
        assertFalse(taskWithDeadline1.isWithinInterval(LocalDateTime.MIN, LocalDateTime.MAX.minusDays(1)));
        assertTrue(taskWithDeadline2.isWithinInterval(LocalDateTime.MIN, LocalDateTime.MAX));
        assertTrue(event1.isWithinInterval(LocalDateTime.MIN.plusDays(1), LocalDateTime.MAX));
        assertTrue(event2.isWithinInterval(LocalDateTime.MIN, LocalDateTime.MAX.minusDays(1)));
    }

    /**
     * Check if the set of tags in the tasks and the tags passed in are identical
     */
    private void assertSimilarTags(Task task, Tag... tags) {
        Set<Tag> tagSet = new HashSet<>();
        for (Tag tag : tags) {
            tagSet.add(tag);
        }

        assertEquals(testTask.getAllTags(), tagSet);
    }

    //@@author A0162011A
    @Test
    public void updateSort_SortByPriority() {
        sortThenAssertTrue("priority");
    }

    @Test
    public void updateSort_SortByDescription() {
        sortThenAssertTrue("description");
    }

    @Test
    public void updateSort_SortByOverdue() {
        sortThenAssertTrue("overdue");
    }

    @Test
    public void updateSort_SortByEndDate() {
        sortThenAssertTrue("enddate");
    }

    @Test
    public void updateSort_SortByStartDate() {
        sortThenAssertTrue("startdate");
    }

    @Test
    public void updateSort_SortByDefault() {
        Task.sortBy("default");
        assertTrue(Task.getCurrentSort()[0].equals("overdue")
            && Task.getCurrentSort()[1].equals("priority")
            && Task.getCurrentSort()[2].equals("enddate")
            && Task.getCurrentSort()[3].equals("startdate")
            && Task.getCurrentSort()[4].equals("description"));
    }

    private void sortThenAssertTrue(String sort) {
        Task.sortBy(sort);
        assertTrue(Task.getCurrentSort()[0].equals(sort));
    }
}
