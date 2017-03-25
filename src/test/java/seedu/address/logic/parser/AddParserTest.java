package seedu.address.logic.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashSet;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask.TaskType;
import seedu.address.model.task.Task;;

public class AddParserTest extends AddCommandParser {
    @Test
    public void parserTestWithTitle() throws Exception {
        AddCommand command = (AddCommand) parse("CS2103 project due");
        Task task = command.getTask();
        Name name = new Name("CS2103 project due");
        UniqueTagList tags = new UniqueTagList(new HashSet<>());
        assertTrue(task.getTaskType().equals(TaskType.TaskWithNoDeadline));
        assertTrue(task.getDeadline() == null);
        assertTrue(task.getName().fullName.equals(name.fullName));
        assertTrue(task.getStartingTime() == null);
        assertTrue(task.getTags().toSet().equals(tags.toSet()));
        assertTrue(task.getTaskDateTime().equals(""));
        assertTrue(task.getTaskAbsoluteDateTime().equals(""));
    }

    @Test
    public void parserTestWithTitleDeadlineTag() throws Exception {
        AddCommand command = (AddCommand) parse(
                "CS2103 project due today #schoolwork #team");
        Task task = command.getTask();
        Name name = new Name("CS2103 project");
        UniqueTagList tags = new UniqueTagList(new HashSet<>());
        ObservableList<Tag> listOfTags = FXCollections.observableArrayList();
        listOfTags.addAll(new Tag("schoolwork"), new Tag("team"));
        tags = new UniqueTagList(listOfTags);
        assertTrue(task.getTaskType().equals(TaskType.TaskWithOnlyDeadline));
        assertTrue(task.getDeadline().isSameDay(new Date()));
        assertTrue(task.getName().fullName.equals(name.fullName));
        assertTrue(task.getStartingTime() == null);
        assertTrue(task.getTags().toSet().equals(tags.toSet()));
        assertFalse(task.getTaskDateTime() == null);
    }

    @Test
    public void parserTestWithTitleDeadlineStartingTimeTag() throws Exception {
        AddCommand command = (AddCommand) parse(
                "CS2103 project from 2 days later to 20/4/2017 #schoolwork #team");
        Task task = command.getTask();
        Name name = new Name("CS2103 project");
        UniqueTagList tags = new UniqueTagList(new HashSet<>());
        ObservableList<Tag> listOfTags = FXCollections.observableArrayList();
        listOfTags.addAll(new Tag("schoolwork"), new Tag("team"));
        tags = new UniqueTagList(listOfTags);
        long milisecondsTilDeadline = 1492646400000L;
        long milisecondsTilStartingTime = new Date().getTime()
                + 24 * 60 * 60 * 1000 * 2;
        assertTrue(task.getTaskType()
                .equals(TaskType.TaskWithDeadlineAndStartingTime));
        assertTrue(
                task.getDeadline().isSameDay(new Date(milisecondsTilDeadline)));
        assertTrue(task.getName().fullName.equals(name.fullName));
        assertTrue(task.getStartingTime()
                .isSameDay(new Date(milisecondsTilStartingTime)));
        assertTrue(task.getTags().toSet().equals(tags.toSet()));
        assertFalse(task.getTaskDateTime() == null);
    }

    @Test
    public void parserTestWithTitle2() throws Exception {
        AddCommand command = (AddCommand) parse(
                "CS2103 project from school to home");
        Task task = command.getTask();
        Name name = new Name("CS2103 project from school to home");
        UniqueTagList tags = new UniqueTagList(new HashSet<>());
        assertTrue(task.getTaskType().equals(TaskType.TaskWithNoDeadline));
        assertTrue(task.getDeadline() == null);
        assertTrue(task.getName().fullName.equals(name.fullName));
        assertTrue(task.getStartingTime() == null);
        assertTrue(task.getTags().toSet().equals(tags.toSet()));
        assertTrue(task.getTaskDateTime().equals(""));
        assertTrue(task.getTaskAbsoluteDateTime().equals(""));
    }

    @Test
    public void parserTestWithTitleDeadlineStartingTime() throws Exception {
        AddCommand command = (AddCommand) parse(
                "CS2103 project from school to home from 2 days later to 20/4/2017");
        Task task = command.getTask();
        Name name = new Name("CS2103 project from school to home");
        UniqueTagList tags = new UniqueTagList(new HashSet<>());
        long milisecondsTilDeadline = 1492646400000L;
        long milisecondsTilStartingTime = new Date().getTime()
                + 24 * 60 * 60 * 1000 * 2;
        assertTrue(task.getTaskType()
                .equals(TaskType.TaskWithDeadlineAndStartingTime));
        assertTrue(
                task.getDeadline().isSameDay(new Date(milisecondsTilDeadline)));
        assertTrue(task.getName().fullName.equals(name.fullName));
        assertTrue(task.getStartingTime()
                .isSameDay(new Date(milisecondsTilStartingTime)));
        assertTrue(task.getTags().toSet().equals(tags.toSet()));
        assertFalse(task.getTaskDateTime() == null);
    }
}
