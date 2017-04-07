package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;
import seedu.address.model.task.ReadOnlyPerson;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniquePersonList;

//@@author A0163848R-reused
/**
 * Command that marks task as incomplete
 */
public class UnmarkCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks as incomplete the task identified "
            + "by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    //@@author A0164466X
    public static final String MESSAGE_MARK_PERSON_SUCCESS = "Marked task as incomplete: %1$s";
    //@@author
    public static final String MESSAGE_DUPLICATE_PERSON = "This task is already incomplete.";

    private final int filteredPersonListIndex;

    /**
     * @param filteredPersonListIndex the index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public UnmarkCommand(int filteredPersonListIndex) {
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
        ReadOnlyPerson editedPerson = createUnmarkedPerson(personToEdit);

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
    private static ReadOnlyPerson createUnmarkedPerson(ReadOnlyPerson personToEdit) {
        assert personToEdit != null;

        UniqueTagList updatedTags =
                personToEdit
                .getTags()
                .except(UniqueTagList.build(
                        Tag.TAG_COMPLETE,
                        Tag.TAG_INCOMPLETE));

        try {
            updatedTags.add(new Tag(Tag.TAG_INCOMPLETE));
        } catch (DuplicateTagException e) {
            e.printStackTrace();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        return new Task(personToEdit.getName(),
                personToEdit.getStartDate(), personToEdit.getEndDate(), personToEdit.getGroup(), updatedTags);
    }
}
