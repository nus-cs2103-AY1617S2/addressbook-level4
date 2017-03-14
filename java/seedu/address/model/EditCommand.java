package seedu.address.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Date;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Time;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.UniqueTagList;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [NAME] [d/DATE] [s/START_TIME] [e/END_TIME] [m/DESCRIPTION]\n"
            + "Example: " + COMMAND_WORD + " 1 d/140317 s/1200";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the task manager.";

    private final int filteredPersonListIndex;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param filteredPersonListIndex the index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(int filteredPersonListIndex, EditPersonDescriptor editPersonDescriptor) {
        assert filteredPersonListIndex > 0;
        assert editPersonDescriptor != null;

        // converts filteredPersonListIndex from one-based to zero-based.
        this.filteredPersonListIndex = filteredPersonListIndex - 1;

        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (filteredPersonListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(filteredPersonListIndex);
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        try {
            model.updatePerson(filteredPersonListIndex, editedPerson);
        } catch (UniquePersonList.DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, personToEdit));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedTaskName = editPersonDescriptor.getTaskName().orElseGet(personToEdit::getTaskName);
        Date updatedDate = editPersonDescriptor.getDate().orElseGet(personToEdit::getDate);
        Time updatedStartTime = editPersonDescriptor.getStartTime().orElseGet(personToEdit::getStartTime);
        Time updatedEndTime = editPersonDescriptor.getEndTime().orElseGet(personToEdit::getEndTime);
        String updatedDescription = editPersonDescriptor.getDescription().orElseGet(personToEdit::getDescription);

        return new Person(updatedTaskName, updatedDate, updatedStartTime, updatedEndTime, updatedDescription);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Optional<Name> taskName = Optional.empty();
        private Optional<Date> date = Optional.empty();
        private Optional<Time> startTime = Optional.empty();
        private Optional<Time> endTime = Optional.empty();
        private Optional<String> description = Optional.empty();

        public EditPersonDescriptor() {}

        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            this.taskName = toCopy.getTaskName();
            this.date = toCopy.getDate();
            this.startTime = toCopy.getStartTime();
            this.endTime = toCopy.getEndTime();
            this.description = toCopy.getDescription();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.taskName, this.date, this.startTime, this.endTime, this.description);
        }

        public void setTaskName(Optional<Name> taskName) {
            assert taskName != null;
            this.taskName = taskName;
        }

        public Optional<Name> getTaskName() {
            return taskName;
        }

        public void setDate(Optional<Date> date) {
            assert date != null;
            this.date = date;
        }

        public Optional<Date> getDate() {
            return date;
        }

        public void setStartTime(Optional<Time> startTime) {
            assert startTime != null;
            this.startTime = startTime;
        }

        public Optional<Time> getStartTime() {
            return startTime;
        }

        public void setEndTime(Optional<Time> endTime) {
            assert endTime != null;
            this.endTime = endTime;
        }

        public Optional<Time> getEndTime() {
            return endTime;
        }

        public void setDescription(Optional<String> description) {
            assert description != null;
            this.description = description;
        }

        public Optional<String> getDescription() {
            return description;
        }

    }
}
