package seedu.address.logic.parser;

//@@author A0144422R
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashSet;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;;

public class AddParserTest extends AddCommandParser {
    @Test
    public void parserTestWithTitle() throws Exception {
        AddCommand command = (AddCommand) parse("CS2103 project due");
        Task task = command.getTask();
        Name name = new Name("CS2103 project due");
        UniqueTagList tags = new UniqueTagList(new HashSet<>());
        assertTrue(task instanceof FloatingTask);
        assertTrue(!task.getDeadline().isPresent());
        assertTrue(task.getName().fullName.equals(name.fullName));
        assertTrue(!task.getStartingTime().isPresent());
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
        assertTrue(task instanceof DeadlineTask);
        assertTrue(task.getDeadline().get().isSameDay(new Date()));
        assertTrue(task.getName().fullName.equals(name.fullName));
        assertTrue(!task.getStartingTime().isPresent());
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
        assertTrue(task instanceof EventTask);
        assertTrue(task.getDeadline().get()
                .isSameDay(new Date(milisecondsTilDeadline)));
        assertTrue(task.getName().fullName.equals(name.fullName));
        assertTrue(task.getStartingTime().get()
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
        assertTrue(task instanceof FloatingTask);
        assertTrue(!task.getDeadline().isPresent());
        assertTrue(task.getName().fullName.equals(name.fullName));
        assertTrue(!task.getStartingTime().isPresent());
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
        assertTrue(task instanceof EventTask);
        assertTrue(task.getDeadline().get()
                .isSameDay(new Date(milisecondsTilDeadline)));
        assertTrue(task.getName().fullName.equals(name.fullName));
        assertTrue(task.getStartingTime().get()
                .isSameDay(new Date(milisecondsTilStartingTime)));
        assertTrue(task.getTags().toSet().equals(tags.toSet()));
        assertFalse(task.getTaskDateTime() == null);
    }

    @Test
    public void parserTestWithNameException() throws Exception {
        Command command = parse("CS2103 project^^^^ due today");
        assertTrue(
                command.getClass().equals(new IncorrectCommand("").getClass()));
    }

    @Test
    public void parserTestWithTagException() throws Exception {
        Command command = parse("CS2103 project #yyy^ #^^^ due today");
        assertTrue(
                command.getClass().equals(new IncorrectCommand("").getClass()));
    }
}
