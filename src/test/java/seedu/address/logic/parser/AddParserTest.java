package seedu.address.logic.parser;

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
    public void parserTest() throws Exception {
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

        command = (AddCommand) parse(
                "CS2103 project due today #schoolwork #team");
        task = command.getTask();
        name = new Name("CS2103 project");
        tags = new UniqueTagList(new HashSet<>());
        ObservableList<Tag> listOfTags = FXCollections.observableArrayList();
        listOfTags.addAll(new Tag("schoolwork"), new Tag("team"));
        tags = new UniqueTagList(listOfTags);
        assertTrue(task.getTaskType().equals(TaskType.TaskWithOnlyDeadline));
        assertTrue(task.getDeadline().isSameDay(new Date()));
        assertTrue(task.getName().fullName.equals(name.fullName));
        assertTrue(task.getStartingTime() == null);
        assertTrue(task.getTags().toSet().equals(tags.toSet()));
        assertTrue(task.getTaskDateTime() == null);
    }
}
