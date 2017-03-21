package seedu.address.logic.commands;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

//@@author A0143648Y
/**
 * Undoes the most recent modification to the ToDoList
 * 
 * @author Jia Yilin
 *
 */
public class UndoCommand extends Command {
    private final Logger logger = LogsCenter.getLogger(UndoCommand.class);

    public static final String MESSAGE_UNDO_FAILURE = "No more operations to undo";
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n" + "Only the last three commands can be recovered "
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute() {
        try {
            logger.info("-----------[SYSTEM UNDO COMMAND]");
            model.resetData(UndoableCommand.previousToDoLists.get(UndoableCommand.previousToDoLists.size() - 1));
            UndoableCommand.previousToDoLists.remove(UndoableCommand.previousToDoLists.size() - 1);
            String feedbackToUser = new String(UndoableCommand.previousCommandResults
                    .get(UndoableCommand.previousCommandResults.size() - 1).feedbackToUser);
            UndoableCommand.previousCommandResults.remove(UndoableCommand.previousCommandResults.size() - 1);
            return new CommandResult("The following command has been undone:" + feedbackToUser);
        } catch (ArrayIndexOutOfBoundsException e) {
            return new CommandResult(MESSAGE_UNDO_FAILURE);
        }

    }
}
