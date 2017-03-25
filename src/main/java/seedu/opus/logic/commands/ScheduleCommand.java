package seedu.opus.logic.commands;

import seedu.opus.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.opus.logic.commands.exceptions.CommandException;

public class ScheduleCommand extends Command {
    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = "";
    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked Task: %1$s as complete";
    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;


    public ScheduleCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor) {
        assert filteredTaskListIndex > 0;
        assert editTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        EditCommand editCommand = new EditCommand(filteredTaskListIndex, editTaskDescriptor);
        return editCommand.execute();
    }
}
