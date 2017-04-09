package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) OR Task name\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Example: " + COMMAND_WORD + " fishing";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s";

    private Integer targetIndex = null;
    private String targetName = null;

    public DeleteCommand(String token) {
        //@@author A0163848R
        try {
            this.targetIndex = Integer.parseUnsignedInt(token);
        } catch (NumberFormatException e) {
            this.targetName = token;
        }
        //@@author
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredPersonList();

        //@@author A0163848R
        if (targetIndex != null && lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask personToDelete = null;
        if (targetIndex != null) personToDelete = lastShownList.get(targetIndex - 1);
        if (targetName != null) personToDelete = getPersonByName(lastShownList, targetName);
        //@@author

        try {
            model.deletePerson(personToDelete);
        } catch (Exception pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, personToDelete));
    }

    private ReadOnlyTask getPersonByName(UnmodifiableObservableList<ReadOnlyTask> list, String name) {
        for (ReadOnlyTask person : list) {
            if (person.getName().toString().equals(name)) {
                return person;
            }
        }
        return null;
    }

}
