package seedu.taskit.logic.commands;

import java.util.List;

import seedu.taskit.commons.core.EventsCenter;
import seedu.taskit.commons.core.Messages;
import seedu.taskit.commons.events.ui.JumpToListRequestEvent;
import seedu.taskit.logic.commands.exceptions.CommandException;
import seedu.taskit.model.task.ReadOnlyTask;

//@@author A0141872E
/**
 * Mark a existing tasks done or undone in TaskIt based on index.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";
    public static final String COMMAND_WORD_ALIAS = "m";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Mark a existing tasks in TaskIt based on index\n"
            + "Parameters: [done,undone]\n"
            + "Example: " + COMMAND_WORD + " done\n";

    public static final String MESSAGE_SUCCESS_ALL = "Marked Task as %1$s";
    public static final String MESSAGE_NOT_MARKED = "Must indicate to mark as done or undone.";
    public static final String MESSAGE_DUPLICATE_MARKING = "This task is alreadly marked as %1$s";

    private int filteredTaskListIndex;
    private String parameter;

    public MarkCommand (int filteredTaskListIndex,String parameter) {
        assert filteredTaskListIndex > 0;

     // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;

        this.parameter = parameter;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMark = lastShownList.get(filteredTaskListIndex);
        assert taskToMark != null;

        if(checkDuplicateMarking(taskToMark)) {
            return new CommandResult(String.format(MESSAGE_DUPLICATE_MARKING,parameter));
        }

        taskToMark.setDone(parameter);
        EventsCenter.getInstance().post(new JumpToListRequestEvent(filteredTaskListIndex));
        //TODO find a better way to show it
        //model.updateFilteredTaskList("undone");
        return new CommandResult(String.format(MESSAGE_SUCCESS_ALL, parameter));
    }

    public Boolean checkDuplicateMarking(ReadOnlyTask taskToMark) {
        if(taskToMark.isDone() == true && parameter.equals("done")){
            return true;
        } else if(taskToMark.isDone() == false && parameter.equals("undone")){
            return true;
        } else {
            return false;
        }
    }
}
