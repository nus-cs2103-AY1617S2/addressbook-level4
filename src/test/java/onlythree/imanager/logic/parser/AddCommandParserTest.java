package onlythree.imanager.logic.parser;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import onlythree.imanager.commons.exceptions.IllegalValueException;
import onlythree.imanager.logic.commands.Command;
import onlythree.imanager.logic.commands.CommandResult;
import onlythree.imanager.logic.commands.EditCommand;
import onlythree.imanager.logic.commands.exceptions.CommandException;
import onlythree.imanager.model.Model;
import onlythree.imanager.model.ModelManager;
import onlythree.imanager.model.tag.UniqueTagList;
import onlythree.imanager.model.task.Deadline;
import onlythree.imanager.model.task.Name;
import onlythree.imanager.model.task.ReadOnlyTask;
import onlythree.imanager.model.task.StartEndDateTime;
import onlythree.imanager.model.task.Task;
import onlythree.imanager.model.task.exceptions.InvalidDurationException;
import onlythree.imanager.model.task.exceptions.PastDateTimeException;

//@@author A0140023E
public class AddCommandParserTest {
    // TODO make logicmanagertest test some simple add and edit
    // TODO fixed dates like 25 Apr can fail if it is in the past
    // TODO see who else is using ExpectedException
    // TODO fuzzy equals

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private static Task actualTask;

    //TODO
    @Test
    public void testAdd() throws PastDateTimeException, IllegalValueException, CommandException {
        Name name = new Name("stand by me");
        Deadline deadline = new Deadline(ZonedDateTime.now().plusDays(1).truncatedTo(ChronoUnit.MINUTES));
        Task expectedTask = new Task(name, Optional.of(deadline), Optional.empty(), new UniqueTagList());

        Command command = new AddCommandParser().parse("stand by me by tmr");
        command.setData(new ModelManagerMock());
        CommandResult result = command.execute();
        actualTask.setDeadline(new Deadline(
                actualTask.getDeadline().get().getDateTime().truncatedTo(ChronoUnit.MINUTES)));
        assertEquals(expectedTask, actualTask);
        System.out.println(actualTask);
        System.out.println(result.feedbackToUser);
    }


    @Test
    public void testEdit() throws PastDateTimeException, IllegalValueException, CommandException {
        Model model = new ModelManagerMock();

        addSampleTaskWithDeadline(model);

        editTaskCommand(model, "1 by 2 days later");

        Deadline deadline = new Deadline(ZonedDateTime.now().plusDays(2).truncatedTo(ChronoUnit.MINUTES));
        Task expectedTask = new Task(new Name("stand by me"), Optional.of(deadline), Optional.empty(),
                new UniqueTagList());
        actualTask.setDeadline(new Deadline(
                actualTask.getDeadline().get().getDateTime().truncatedTo(ChronoUnit.MINUTES)));

        assertEquals(expectedTask, actualTask);
    }

    @Test
    public void testEdit2() throws PastDateTimeException, IllegalValueException, CommandException {
        Model model = new ModelManagerMock();
        addSampleTaskWithDeadline(model);

        editTaskCommand(model, "1 Pass rose from Uncle to Jane by 5 days later");

        Deadline deadline = new Deadline(ZonedDateTime.now().plusDays(5).truncatedTo(ChronoUnit.MINUTES));
        Task expectedTask = new Task(new Name("Pass rose from Uncle to Jane"), Optional.of(deadline), Optional.empty(),
                new UniqueTagList());
        actualTask.setDeadline(new Deadline(
                actualTask.getDeadline().get().getDateTime().truncatedTo(ChronoUnit.MINUTES)));

        assertEquals(expectedTask, actualTask);
    }

    @Test
    public void testEdit3() throws PastDateTimeException, IllegalValueException, CommandException {
        Model model = new ModelManagerMock();

        addSampleTaskWithDeadline(model);

        editTaskCommand(model, "1 by 8 days from 25 Apr");

        Deadline deadline = new Deadline(ZonedDateTime.now().withMonth(4).withDayOfMonth(25).plusDays(8)
                .truncatedTo(ChronoUnit.MINUTES));
        Task expectedTask = new Task(new Name("stand by me"), Optional.of(deadline), Optional.empty(),
                new UniqueTagList());
        actualTask.setDeadline(new Deadline(
                actualTask.getDeadline().get().getDateTime().truncatedTo(ChronoUnit.MINUTES)));

        assertEquals(expectedTask, actualTask);
    }


    @Test
    public void testEditSpecial1() throws PastDateTimeException, IllegalValueException, CommandException {
        Model model = new ModelManagerMock();

        addSampleTaskWithDeadline(model);

        exception.expect(CommandException.class);
        exception.expectMessage(EditCommand.EditTaskDescriptor.MESSAGE_NEED_START_END_DATE_TIME);

        editTaskCommand(model, "1 by 8 days later from 25 Apr");

        Deadline deadline = new Deadline(ZonedDateTime.now().withMonth(4).withDayOfMonth(25).plusDays(8)
                .truncatedTo(ChronoUnit.MINUTES));
        Task expectedTask = new Task(new Name("stand by me"), Optional.of(deadline), Optional.empty(),
                new UniqueTagList());
        actualTask.setDeadline(new Deadline(
                actualTask.getDeadline().get().getDateTime().truncatedTo(ChronoUnit.MINUTES)));

        assertEquals(expectedTask, actualTask);
    }

    @Test
    public void testEditSpecial2()
            throws PastDateTimeException, InvalidDurationException, IllegalValueException, CommandException {
        Model model = new ModelManagerMock();

        addSampleTaskWithStartEndDateTime(model);

        editTaskCommand(model, "1 by 8 days later from 25 Apr");

        ZonedDateTime startDateTime =
                ZonedDateTime.now().withMonth(4).withDayOfMonth(25).truncatedTo(ChronoUnit.MINUTES);
        ZonedDateTime endDateTime =
                ZonedDateTime.now().withMonth(4).withDayOfMonth(28).truncatedTo(ChronoUnit.MINUTES);
        StartEndDateTime startEndDateTime = new StartEndDateTime(startDateTime, endDateTime);
        Task expectedTask = new Task(new Name("by 8 days later"), Optional.empty(), Optional.of(startEndDateTime),
                new UniqueTagList());

        StartEndDateTime actualStartEndDateTime = actualTask.getStartEndDateTime().get();
        actualTask.setStartEndDateTime(new StartEndDateTime(
                actualStartEndDateTime.getStartDateTime().truncatedTo(ChronoUnit.MINUTES),
                actualStartEndDateTime.getEndDateTime().truncatedTo(ChronoUnit.MINUTES)));

        assertEquals(expectedTask, actualTask);
    }

    private void addSampleTaskWithDeadline(Model model) throws CommandException {
        Command command = new AddCommandParser().parse("stand by me by tmr");
        command.setData(model);
        command.execute();
    }

    private void addSampleTaskWithStartEndDateTime(Model model) throws CommandException {
        Command command = new AddCommandParser().parse("stand by me from 23 Apr to 28 Apr");
        command.setData(model);
        command.execute();
    }

    private void editTaskCommand(Model model, String args) throws CommandException {
        Command command = new EditCommandParser().parse(args);
        command.setData(model);
        command.execute();
    }

    private class ModelManagerMock extends ModelManager {
        @Override
        public synchronized void addTask(Task task) {
            AddCommandParserTest.actualTask = task;
            super.addTask(task);
        }

        // TODO move this to EditCommandParserTest
        @Override
        public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask) {
            try {
                AddCommandParserTest.actualTask = new Task(editedTask);
            } catch (IllegalValueException e) {
                throw new AssertionError("Copying a valid task should always result in a valid task");
            }
            super.updateTask(filteredTaskListIndex, editedTask);
        }
    }

}
