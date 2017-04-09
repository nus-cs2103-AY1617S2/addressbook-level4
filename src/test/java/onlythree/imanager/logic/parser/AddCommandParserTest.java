package onlythree.imanager.logic.parser;

import static onlythree.imanager.testutil.TestDateTimeHelper.assertEqualsTaskIgnoreDeadlineSeconds;
import static onlythree.imanager.testutil.TestDateTimeHelper.assertEqualsTaskIgnoreStartEndDateTimeSeconds;
import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import onlythree.imanager.commons.core.Messages;
import onlythree.imanager.commons.exceptions.IllegalValueException;
import onlythree.imanager.logic.commands.AddCommand;
import onlythree.imanager.logic.commands.Command;
import onlythree.imanager.logic.commands.exceptions.CommandException;
import onlythree.imanager.model.ModelManager;
import onlythree.imanager.model.tag.UniqueTagList;
import onlythree.imanager.model.task.Deadline;
import onlythree.imanager.model.task.Name;
import onlythree.imanager.model.task.StartEndDateTime;
import onlythree.imanager.model.task.Task;
import onlythree.imanager.model.task.exceptions.InvalidDurationException;
import onlythree.imanager.model.task.exceptions.PastDateTimeException;

//@@author A0140023E
public class AddCommandParserTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    private static Task actualTask;

    @Test
    public void parse_withRequiredFieldsOnly_expectCorrectTask()
            throws PastDateTimeException, IllegalValueException, CommandException {
        Name name = new Name("only name");
        Task expectedTask = new Task(name, Optional.empty(), Optional.empty(), new UniqueTagList());

        parseAndExecute("only name");

        assertEquals(expectedTask, actualTask);
    }

    @Test
    public void parse_withTags_expectCorrectTask()
            throws PastDateTimeException, IllegalValueException, CommandException {
        Name name = new Name("fill me up");
        Task expectedTask = new Task(name, Optional.empty(), Optional.empty(), new UniqueTagList("tag1", "tag2"));

        parseAndExecute(" fill me up t/tag1 t/tag2");

        assertEquals(expectedTask, actualTask);
    }

    @Test
    public void parse_withDeadline_expectCorrectTask()
            throws PastDateTimeException, IllegalValueException, CommandException {
        Name name = new Name("stand by me");
        Deadline deadline = new Deadline(ZonedDateTime.now().plusDays(1));
        Task expectedTask = new Task(name, Optional.of(deadline), Optional.empty(), new UniqueTagList());

        parseAndExecute(" stand by me by tmr");

        assertEqualsTaskIgnoreDeadlineSeconds(expectedTask, actualTask);
    }

    @Test
    public void parse_withStartEndDateTime_expectCorrectTask()
            throws PastDateTimeException, IllegalValueException, CommandException, InvalidDurationException {
        Name name = new Name("vacation");
        StartEndDateTime startEndDateTime =
                new StartEndDateTime(ZonedDateTime.now().plusDays(3), ZonedDateTime.now().plusDays(7));
        Task expectedTask = new Task(name, Optional.empty(), Optional.of(startEndDateTime), new UniqueTagList());

        parseAndExecute(" vacation from 3 days after to 7 days after");

        assertEqualsTaskIgnoreStartEndDateTimeSeconds(expectedTask, actualTask);

    }

    @Test
    public void parse_withDeadlineAndStartEndDateTime_expectsException()
            throws PastDateTimeException, IllegalValueException, CommandException, InvalidDurationException {
        exception.expect(CommandException.class);
        exception.expectMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        parseAndExecute(" by tmr from 3 days after to 7 days after");
    }

    private void parseAndExecute(String args) throws CommandException {
        Command command = new AddCommandParser().parse(args);
        command.setData(new ModelManagerMock());
        command.execute();
    }


    private class ModelManagerMock extends ModelManager {
        @Override
        public synchronized void addTask(Task task) {
            AddCommandParserTest.actualTask = task;
            super.addTask(task);
        }
    }

}
