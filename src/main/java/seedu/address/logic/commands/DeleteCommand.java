package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyPerson;
import seedu.address.model.task.UniquePersonList.PersonNotFoundException;

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
        try {
            this.targetIndex = Integer.parseInt(token);
        } catch (NumberFormatException e) {
            this.targetName = token;
        }
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex != null && lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToDelete = null;
        if (targetIndex != null) personToDelete = lastShownList.get(targetIndex - 1);

        try {
            if (targetName != null) personToDelete = getPersonByName(lastShownList, targetName);
            
            model.deletePerson(personToDelete);
        } catch (Exception pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, personToDelete));
    }

    private ReadOnlyPerson getPersonByName(UnmodifiableObservableList<ReadOnlyPerson> list, String name) {
        for (ReadOnlyPerson person : list) {
            if (person.getName().toString().equals(name)) {
                return person;
            }
        }
        return null;
    }
    
}
