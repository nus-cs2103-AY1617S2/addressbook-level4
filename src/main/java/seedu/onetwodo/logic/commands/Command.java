package seedu.onetwodo.logic.commands;

import javafx.collections.transformation.FilteredList;
import seedu.onetwodo.commons.core.Messages;
import seedu.onetwodo.commons.core.UnmodifiableObservableList;
import seedu.onetwodo.logic.commands.exceptions.CommandException;
import seedu.onetwodo.logic.parser.DoneStatus;
import seedu.onetwodo.model.Model;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.TaskType;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of tasks.
     *
     * @param displaySize used to generate summary
     * @return summary message for tasks displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute() throws CommandException;

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model) {
        this.model = model;
    }
    
    public void filterTasksByDoneStatus() {
        DoneStatus doneStatus = model.getDoneStatus();
        switch (doneStatus) {
        case ALL: 
        case DEFAULT:
            model.updateFilteredListToShowAll();
            break;
        case DONE:
            model.updateFilteredDoneTaskList();
            break;
        case UNDONE:
            model.updateFilteredUndoneTaskList();
        }   
    }
 }
