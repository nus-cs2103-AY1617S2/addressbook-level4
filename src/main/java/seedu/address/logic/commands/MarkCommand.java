package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;
import seedu.address.model.task.Task;
import seedu.address.model.task.ReadOnlyPerson;
import seedu.address.model.task.UniquePersonList;

public class MarkCommand extends Command {
	
	public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks as complete the task identified "
            + "by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_PERSON_SUCCESS = "Marked task: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task is already complete.";

    private final int filteredPersonListIndex;
    
    /**
     * @param filteredPersonListIndex the index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public MarkCommand(int filteredPersonListIndex) {
        assert filteredPersonListIndex > 0;
        
        // converts filteredPersonListIndex from one-based to zero-based.
        this.filteredPersonListIndex = filteredPersonListIndex - 1;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (filteredPersonListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(filteredPersonListIndex);
        Task editedPerson = createMarkedPerson(personToEdit);

        try {
            model.updatePerson(filteredPersonListIndex, editedPerson);
        } catch (UniquePersonList.DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_MARK_PERSON_SUCCESS, personToEdit));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited to be complete.
     */
    private static Task createMarkedPerson(ReadOnlyPerson personToEdit) {
        assert personToEdit != null;
    	
    	UniqueTagList updatedTags =
    	        personToEdit
    	        .getTags()
    	        .except(UniqueTagList.constructUniqueTagList(
    	                        Tag.TAG_COMPLETE,
    	                        Tag.TAG_INCOMPLETE));
        
        try {
			updatedTags.add(new Tag(Tag.TAG_COMPLETE));
		} catch (DuplicateTagException e) {
			e.printStackTrace();
		} catch (IllegalValueException e) {
			e.printStackTrace();
		}

        return new Task(personToEdit.getName(),
        		personToEdit.getDate(), personToEdit.getEmail(), personToEdit.getGroup(), updatedTags);
    }
}
