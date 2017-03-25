# Melvin
###### /java/seedu/toluist/commons/util/DateTimeUtilTest.java
``` java
    @Test
    public void isSameLocalDateTime() {
        String stringDate;
        LocalDateTime localDateTime1;
        LocalDateTime localDateTime2;

        // convert a fully specified date time to local date time
        stringDate = "31 Mar 2017, 5:24pm";
        localDateTime1 = DateTimeUtil.parseDateString(stringDate);
        localDateTime2 = LocalDateTime.of(2017, Month.MARCH, 31, 17, 24);
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime2));

        stringDate = "31 Mar 2017, 5am";
        localDateTime1 = DateTimeUtil.parseDateString(stringDate);
        localDateTime2 = LocalDateTime.of(2017, Month.MARCH, 31, 5, 0);
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime1));

        // convert a fully specified date to local date time
        stringDate = "31 Mar 2017";
        localDateTime1 = DateTimeUtil.parseDateString(stringDate);
        localDateTime2 = LocalDateTime.of(2017, Month.MARCH, 31,
                LocalDateTime.now().getHour(), LocalDateTime.now().getMinute());
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime2));

        // convert an unclear date time to local date time
        stringDate = "now";
        localDateTime1 = DateTimeUtil.parseDateString(stringDate);
        localDateTime2 = LocalDateTime.now();
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime2));

        stringDate = "3 hours from now";
        localDateTime1 = DateTimeUtil.parseDateString(stringDate);
        localDateTime2 = LocalDateTime.now();
        localDateTime2 = localDateTime2.plus(3, ChronoUnit.HOURS);
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime2));

        stringDate = "tomorrow";
        localDateTime1 = DateTimeUtil.parseDateString(stringDate);
        localDateTime2 = LocalDateTime.now();
        localDateTime2 = localDateTime2.plus(1, ChronoUnit.DAYS);
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime2));

        stringDate = "next week";
        localDateTime1 = DateTimeUtil.parseDateString(stringDate);
        localDateTime2 = LocalDateTime.now();
        localDateTime2 = localDateTime2.plus(1, ChronoUnit.WEEKS);
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime2));

        stringDate = "next month";
        localDateTime1 = DateTimeUtil.parseDateString(stringDate);
        localDateTime2 = LocalDateTime.now();
        localDateTime2 = localDateTime2.plus(1, ChronoUnit.MONTHS);
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime2));

        stringDate = "next year";
        localDateTime1 = DateTimeUtil.parseDateString(stringDate);
        localDateTime2 = LocalDateTime.now();
        localDateTime2 = localDateTime2.plus(1, ChronoUnit.YEARS);
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime2));
    }

    @Test
    public void dateTimeIsNull() {
        String stringDate;
        LocalDateTime localDateTime;

        localDateTime = DateTimeUtil.parseDateString(null);
        assertTrue(localDateTime == null);

        stringDate = "buy banana";
        localDateTime = DateTimeUtil.parseDateString(stringDate);
        assertTrue(localDateTime == null);
    }

```
###### /java/seedu/toluist/commons/util/DateTimeUtilTest.java
``` java
    private boolean datesApproximatelyEqual(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        // Dates are approximately equal so long as they are accurate up to the minute.
        return localDateTime1.getYear() == localDateTime2.getYear()
                && localDateTime1.getMonth() == localDateTime2.getMonth()
                && localDateTime1.getDayOfMonth() == localDateTime2.getDayOfMonth()
                && localDateTime1.getHour() == localDateTime2.getHour()
                && localDateTime1.getMinute() == localDateTime2.getMinute();
    }
}
```
###### /java/seedu/toluist/controller/commons/IndexParserTest.java
``` java
package seedu.toluist.controller.commons;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class IndexParserTest {
    //---------------- Tests for splitIndexes --------------------------------------

    @Test
    public void splitIndexes_obtainSingleIndex() {
        List<Integer> actual = IndexParser.splitStringToIndexes(" 3  ", 8);
        List<Integer> expected = Arrays.asList(3);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_obtainIndexesToEndIndex() {
        List<Integer> actual = IndexParser.splitStringToIndexes(" - 3", 8);
        List<Integer> expected = Arrays.asList(1, 2, 3);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_obtainIndexesFromStartIndex() {
        List<Integer> actual = IndexParser.splitStringToIndexes(" 3 -", 8);
        List<Integer> expected = Arrays.asList(3, 4, 5, 6, 7, 8);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_obtainIndexesFromStartIndexToEndIndex() {
        List<Integer> actual = IndexParser.splitStringToIndexes("3-5", 8);
        List<Integer> expected = Arrays.asList(3, 4, 5);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_obtainIndexesFromPoorlyFormattedStringIndexes() {
        List<Integer> actual = IndexParser.splitStringToIndexes("1-2  ,    3-   4   5  -6   , 7   -      8", 8);
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_obtainIndexesFromAssortedIndexes() {
        List<Integer> actual = IndexParser.splitStringToIndexes("1 3,  7", 8);
        List<Integer> expected = Arrays.asList(1, 3, 7);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_obtainIndexesFromAssortedUnorderedIndexes1() {
        List<Integer> actual = IndexParser.splitStringToIndexes("3, 2, 8, 5-7", 8);
        List<Integer> expected = Arrays.asList(2, 3, 5, 6, 7, 8);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_obtainIndexesFromAssortedUnorderedIndexes2() {
        List<Integer> actual = IndexParser.splitStringToIndexes("7, - 3", 8);
        List<Integer> expected = Arrays.asList(1, 2, 3, 7);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_obtainIndexesComplexStringIndexes() {
        List<Integer> actual = IndexParser.splitStringToIndexes("- 3, 5, 7 - 12", 8);
        List<Integer> expected = Arrays.asList(1, 2, 3, 5, 7, 8);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_withInvalidIndex1() {
        List<Integer> actual = IndexParser.splitStringToIndexes("banana", 8);
        List<Integer> expected = Arrays.asList();
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_withInvalidIndex2() {
        List<Integer> actual = IndexParser.splitStringToIndexes("-3 banana 5", 8);
        List<Integer> expected = Arrays.asList(1, 2, 3);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_withInvalidIndex3() {
        List<Integer> actual = IndexParser.splitStringToIndexes("-3 5 banana", 8);
        List<Integer> expected = Arrays.asList(1, 2, 3, 5);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_withInvalidIndex4() {
        List<Integer> actual = IndexParser.splitStringToIndexes("-3, banana, 5", 8);
        List<Integer> expected = Arrays.asList(1, 2, 3, 5);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_withInvalidIndex5() {
        List<Integer> actual = IndexParser.splitStringToIndexes("-3, 5, banana", 8);
        List<Integer> expected = Arrays.asList(1, 2, 3, 5);
        assertTrue(actual.equals(expected));
    }
}
```
###### /java/seedu/toluist/controller/commons/KeywordTokenizerTest.java
``` java
package seedu.toluist.controller.commons;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

public class KeywordTokenizerTest {
    //---------------- Tests for tokenize --------------------------------------

    @Test
    public void tokenize_allNullValues() {
        HashMap<String, String> actual = KeywordTokenizer.tokenize(null, null, (String[]) null);
        HashMap<String, String> expected = new HashMap<String, String>();
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_onlyDescriptionGiven() {
        HashMap<String, String> actual = KeywordTokenizer.tokenize("Description", null, (String[]) null);
        HashMap<String, String> expected = new HashMap<String, String>();
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_onlyDefaultKeywordGiven() {
        HashMap<String, String> actual = KeywordTokenizer.tokenize(null, "default", (String[]) null);
        HashMap<String, String> expected = new HashMap<String, String>();
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_onlyOneNonDefaultKeywordGiven() {
        String[] keywords = {"non-default"};
        HashMap<String, String> actual = KeywordTokenizer.tokenize(null, null, keywords);
        HashMap<String, String> expected = new HashMap<String, String>();
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_descriptionAndDefaultKeywordGiven() {
        HashMap<String, String> actual = KeywordTokenizer.tokenize("description", "default");
        HashMap<String, String> expected = new HashMap<String, String>();
        expected.put("default", "description");
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_descriptionAndNonMatchingNonDefaultKeywordsGiven() {
        HashMap<String, String> actual = KeywordTokenizer.tokenize("description", null, "non-default");
        HashMap<String, String> expected = new HashMap<String, String>();
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_descriptionAndMatchingNonDefaultKeywordsGiven() {
        HashMap<String, String> actual = KeywordTokenizer.tokenize("non default description", null, "non default");
        HashMap<String, String> expected = new HashMap<String, String>();
        expected.put("non default", "description");
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_generalSituationOrderedKeywords() {
        String description = "long description with many key-value pairs like A x y z B C D x y EFGHI  xyzabc  def  ";
        HashMap<String, String> actual = KeywordTokenizer.tokenize(description, "description", "with",
                                                                                               "A",
                                                                                               "B C D",
                                                                                               "EFGHI");
        HashMap<String, String> expected = new HashMap<String, String>();
        expected.put("description", "long description");
        expected.put("with", "many key-value pairs like");
        expected.put("A", "x y z");
        expected.put("B C D", "x y");
        expected.put("EFGHI", "xyzabc  def");
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_generalSituationUnorderedKeywords() {
        String description = "long description with many key-value pairs like A x y z B C D x y EFGHI  xyzabc  def  ";
        HashMap<String, String> actual = KeywordTokenizer.tokenize(description, "description", "B C D",
                                                                                               "with",
                                                                                               "EFGHI",
                                                                                               "A");
        HashMap<String, String> expected = new HashMap<String, String>();
        expected.put("description", "long description");
        expected.put("with", "many key-value pairs like");
        expected.put("A", "x y z");
        expected.put("B C D", "x y");
        expected.put("EFGHI", "xyzabc  def");
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_updateTaskCommand() {
        String description = "update v0.3 enddate/next wednesday tags/ohno tag manysubtasks startdate/today";
        HashMap<String, String> actual = KeywordTokenizer.tokenize(description, "description", "startdate/",
                                                                                               "enddate/",
                                                                                               "tags/");
        HashMap<String, String> expected = new HashMap<String, String>();
        expected.put("description", "update v0.3");
        expected.put("startdate/", "today");
        expected.put("enddate/", "next wednesday");
        expected.put("tags/", "ohno tag manysubtasks");
        assertTrue(actual.equals(expected));
    }
}
```
###### /java/seedu/toluist/controller/commons/TagParserTest.java
``` java
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
        Set<Tag> expected = new TreeSet<>(Arrays.asList(new Tag("yoohoo"), new Tag("lololol"), new Tag("wheeeeeee")));
        assertTrue(actual.equals(expected));
    }
}
```
###### /java/seedu/toluist/model/TodoListTest.java
``` java
package seedu.toluist.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import seedu.toluist.commons.exceptions.DataStorageException;
import seedu.toluist.storage.TodoListStorage;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Tests for TodoList model
 */
public class TodoListTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private TodoListStorage storage;
    private TodoList todoList1;
    private TodoList todoListWithStorage;
    private TodoList sampleTodoList = new TypicalTestTodoLists().getTypicalTodoList();
    private final Task task1 = new Task("Task 1");
    private final Task task2 = new Task("Task 2");
    private final Task task3 = new Task("Task 3");
    private final Task task4 = new Task("Task 4");
    private final Task task5 = new Task("Task 5");

    @Before
    public void setUp() throws DataStorageException {
        when(storage.load()).thenReturn(sampleTodoList);
        todoList1 = new TodoList();
        todoList1.add(task1);
        todoList1.add(task2);
        todoList1.add(task3);
        todoList1.add(task4);
        todoListWithStorage = new TodoList(storage);
    }

```
###### /java/seedu/toluist/model/TodoListTest.java
``` java
    @Test
    public void testEquals() {
        TodoList todoList2 = new TodoList();
        todoList2.add(task1);
        todoList2.add(task2);
        todoList2.add(task3);
        todoList2.add(task4);
        assertTrue(todoList1.equals(todoList1));
        assertTrue(todoList1.equals(todoList2));
    }

    @Test
    public void testNotEquals() {
        TodoList todoList2 = new TodoList();
        todoList2.add(task1);
        todoList2.add(task2);
        todoList2.add(task3);
        TodoList todoList3 = new TodoList();
        todoList2.add(task1);
        todoList2.add(task3);
        todoList2.add(task2);
        assertFalse(todoList1.equals(todoList2));
        assertFalse(todoList2.equals(todoList3));
    }

    @Test
    public void testGetAllTasks() {
        List<Task> actual = todoList1.getTasks();
        List<Task> expected = Arrays.asList(task1, task2, task3, task4);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void testAddDuplicatedTask() {
        todoList1.add(task1);
        List<Task> actual = todoList1.getTasks();
        List<Task> expected = Arrays.asList(task1, task2, task3, task4);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void testAddNewTask() {
        todoList1.add(task5);
        List<Task> actual = todoList1.getTasks();
        List<Task> expected = Arrays.asList(task1, task2, task3, task4, task5);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void testRemoveExistingTask() {
        todoList1.remove(task2);
        List<Task> actual = todoList1.getTasks();
        List<Task> expected = Arrays.asList(task1, task3, task4);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void testRemoveNonExistingTask() {
        todoList1.remove(task5);
        List<Task> actual = todoList1.getTasks();
        List<Task> expected = Arrays.asList(task1, task2, task3, task4);
        assertTrue(actual.equals(expected));
    }
}
```
