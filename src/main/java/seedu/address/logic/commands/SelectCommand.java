package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
<<<<<<< HEAD
import seedu.address.model.person.ReadOnlyTask;
=======
import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.ui.JumpToListRequestEvent;
import seedu.task.model.task.ReadOnlyTask;
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285

/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class SelectCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s";

    public SelectCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

<<<<<<< HEAD
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredPersonList();
=======
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex));

    }

}
