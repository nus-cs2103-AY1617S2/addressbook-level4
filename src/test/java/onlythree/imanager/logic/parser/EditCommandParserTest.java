package onlythree.imanager.logic.parser;

import static onlythree.imanager.testutil.TestDateTimeHelper.assertEqualsTaskIgnoreDeadlineSeconds;
import static onlythree.imanager.testutil.TestDateTimeHelper.assertEqualsTaskIgnoreStartEndDateTimeSeconds;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import onlythree.imanager.commons.core.DateTimeFormats;
import onlythree.imanager.commons.exceptions.IllegalValueException;
import onlythree.imanager.logic.commands.Command;
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
public class EditCommandParserTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    private static Task actualTask;

    @Test
    public void parse_withDeadlineExplicitDate_expectCorrectTask()
            throws PastDateTimeException, IllegalValueException, CommandException {
        Model model = new ModelManagerMock();

        addSampleTaskWithDeadline(model);

        parseEditAndExecute(model, " 1 by 14 Aug");

        Deadline deadline = new Deadline(
                ZonedDateTime.now().plusDays(1).with(LocalDate.of(Year.now().getValue(), 8, 14)));
        Task expectedTask = new Task(new Name("stand by me"), Optional.of(deadline), Optional.empty(),
                new UniqueTagList());

        assertEqualsTaskIgnoreDeadlineSeconds(expectedTask, actualTask);
    }

    @Test
    public void parse_withDeadlineExplicitTime_expectCorrectTask()
            throws PastDateTimeException, IllegalValueException, CommandException {
        Model model = new ModelManagerMock();

        addSampleTaskWithDeadline(model);

        parseEditAndExecute(model, " 1 by 2pm");

        Deadline deadline = new Deadline(ZonedDateTime.now().plusDays(1).with(LocalTime.of(14, 0)));
        Task expectedTask = new Task(new Name("stand by me"), Optional.of(deadline), Optional.empty(),
                new UniqueTagList());

        assertEqualsTaskIgnoreDeadlineSeconds(expectedTask, actualTask);
    }

    @Test
    public void parse_withDeadlineExplicitDateTime_expectCorrectTask()
            throws PastDateTimeException, IllegalValueException, CommandException {
        Model model = new ModelManagerMock();

        addSampleTaskWithDeadline(model);

        parseEditAndExecute(model, " 1 by 17 Oct 7pm");

        Deadline deadline = new Deadline(ZonedDateTime.of(Year.now().getValue(), 10, 17, 19, 0, 0, 0,
                DateTimeFormats.SYSTEM_TIME_ZONE));
        Task expectedTask = new Task(new Name("stand by me"), Optional.of(deadline), Optional.empty(),
                new UniqueTagList());

        assertEqualsTaskIgnoreDeadlineSeconds(expectedTask, actualTask);
    }

    @Test
    public void parse_withDeadlineRelativeDate_expectCorrectTask()
            throws PastDateTimeException, IllegalValueException, CommandException {
        Model model = new ModelManagerMock();

        addSampleTaskWithDeadline(model);

        parseEditAndExecute(model, " 1 by 2 days later");

        Deadline deadline = new Deadline(ZonedDateTime.now().plusDays(2));
        Task expectedTask = new Task(new Name("stand by me"), Optional.of(deadline), Optional.empty(),
                new UniqueTagList());

        assertEqualsTaskIgnoreDeadlineSeconds(expectedTask, actualTask);
    }

    @Test
    public void parse_withDeadlineRelativeToAnotherDate_expectCorrectTask()
            throws PastDateTimeException, IllegalValueException, CommandException {
        Model model = new ModelManagerMock();

        addSampleTaskWithDeadline(model);

        parseEditAndExecute(model, " 1 by 8 days from 3 Sep");

        Deadline deadline = new Deadline(ZonedDateTime.now().withMonth(9).withDayOfMonth(3).plusDays(8));

        Task expectedTask = new Task(new Name("stand by me"), Optional.of(deadline), Optional.empty(),
                new UniqueTagList());

        assertEqualsTaskIgnoreDeadlineSeconds(expectedTask, actualTask);
    }

    @Test
    public void parse_withDeadlineLookingLikeRelativeToAnotherDate_expectException()
            throws PastDateTimeException, IllegalValueException, CommandException {
        Model model = new ModelManagerMock();

        addSampleTaskWithDeadline(model);

        exception.expect(CommandException.class);
        exception.expectMessage(EditCommand.EditTaskDescriptor.MESSAGE_NEED_START_END_DATE_TIME);

        // later is not recognized as a token by Natty so it is interpreted that there is start date-time
        parseEditAndExecute(model, " 1 by 8 days later from 3 Sep");
    }

    @Test
    public void parse_withDeadlineLookingLikeRelativeToAnotherDate_expectCorrectTask()
            throws PastDateTimeException, InvalidDurationException, IllegalValueException, CommandException {
        Model model = new ModelManagerMock();

        addSampleTaskWithStartEndDateTime(model);

        parseEditAndExecute(model, " 1 by 8 days later from 3 Sep");

        ZonedDateTime startDateTime = ZonedDateTime.now().withMonth(9).withDayOfMonth(3);
        ZonedDateTime endDateTime = ZonedDateTime.now().withMonth(10).withDayOfMonth(28);
        StartEndDateTime startEndDateTime = new StartEndDateTime(startDateTime, endDateTime);
        Task expectedTask = new Task(new Name("by 8 days later"), Optional.empty(), Optional.of(startEndDateTime),
                new UniqueTagList());

        assertEqualsTaskIgnoreStartEndDateTimeSeconds(expectedTask, actualTask);
    }

    @Test
    public void parse_withDeadlineRelativeDateWithDateLookingName_expectCorrectTask()
            throws PastDateTimeException, IllegalValueException, CommandException {
        Model model = new ModelManagerMock();
        addSampleTaskWithDeadline(model);

        parseEditAndExecute(model, " 1 Pass rose from Uncle to Jane by 5 days after");

        Deadline deadline = new Deadline(ZonedDateTime.now().plusDays(5));
        Task expectedTask = new Task(new Name("Pass rose from Uncle to Jane"), Optional.of(deadline), Optional.empty(),
                new UniqueTagList());

        assertEqualsTaskIgnoreDeadlineSeconds(expectedTask, actualTask);
    }

    private void addSampleTaskWithDeadline(Model model) throws CommandException {
        Command command = new AddCommandParser().parse("stand by me by tmr");
        command.setData(model);
        command.execute();
    }

    private void addSampleTaskWithStartEndDateTime(Model model) throws CommandException {
        Command command = new AddCommandParser().parse("stand by me from 23 Apr to 28 Oct");
        command.setData(model);
        command.execute();
    }

    private void parseEditAndExecute(Model model, String args) throws CommandException {
        Command command = new EditCommandParser().parse(args);
        command.setData(model);
        command.execute();
    }

    private class ModelManagerMock extends ModelManager {
        @Override
        public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask) {
            try {
                EditCommandParserTest.actualTask = new Task(editedTask);
            } catch (IllegalValueException e) {
                throw new AssertionError("Copying a valid task should always result in a valid task");
            }
            super.updateTask(filteredTaskListIndex, editedTask);
        }
    }
}
