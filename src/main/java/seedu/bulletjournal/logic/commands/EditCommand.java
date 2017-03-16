package seedu.bulletjournal.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.bulletjournal.commons.core.Messages;
import seedu.bulletjournal.commons.util.CollectionUtil;
import seedu.bulletjournal.logic.commands.exceptions.CommandException;
import seedu.bulletjournal.model.tag.UniqueTagList;
import seedu.bulletjournal.model.task.Deadline;
import seedu.bulletjournal.model.task.ReadOnlyTask;
import seedu.bulletjournal.model.task.StartTime;
import seedu.bulletjournal.model.task.Status;
import seedu.bulletjournal.model.task.Task;
import seedu.bulletjournal.model.task.TaskName;
import seedu.bulletjournal.model.task.UniqueTaskList;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [NAME] [d/PHONE] [s/EMAIL] [b/ADDRESS ] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 d/91234567 s/johndoe@yahoo.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final int filteredPersonListIndex;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param filteredPersonListIndex
     *            the index of the person in the filtered person list to edit
     * @param editPersonDescriptor
     *            details to edit the person with
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
        List<ReadOnlyTask> lastShownList = model.getFilteredPersonList();

        if (filteredPersonListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyTask personToEdit = lastShownList.get(filteredPersonListIndex);
        Task editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        try {
            model.updatePerson(filteredPersonListIndex, editedPerson);
        } catch (UniqueTaskList.DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, personToEdit));
    }

    /**
     * Creates and returns a {@code Person} with the details of
     * {@code personToEdit} edited with {@code editPersonDescriptor}.
     */
    private static Task createEditedPerson(ReadOnlyTask personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        TaskName updatedName = editPersonDescriptor.getName().orElseGet(personToEdit::getName);
        Deadline updatedPhone = editPersonDescriptor.getPhone().orElseGet(personToEdit::getPhone);
        Status updatedEmail = editPersonDescriptor.getEmail().orElseGet(personToEdit::getEmail);
        StartTime updatedAddress = editPersonDescriptor.getAddress().orElseGet(personToEdit::getAddress);
        UniqueTagList updatedTags = editPersonDescriptor.getTags().orElseGet(personToEdit::getTags);

        return new Task(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value
     * will replace the corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Optional<TaskName> taskName = Optional.empty();
        private Optional<Deadline> deadline = Optional.empty();
        private Optional<Status> status = Optional.empty();
        private Optional<StartTime> startTime = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditPersonDescriptor() {
        }

        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            this.taskName = toCopy.getName();
            this.deadline = toCopy.getPhone();
            this.status = toCopy.getEmail();
            this.startTime = toCopy.getAddress();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.taskName, this.deadline, this.status, this.startTime, this.tags);
        }

        public void setName(Optional<TaskName> taskName) {
            assert taskName != null;
            this.taskName = taskName;
        }

        public Optional<TaskName> getName() {
            return taskName;
        }

        public void setPhone(Optional<Deadline> deadline) {
            assert deadline != null;
            this.deadline = deadline;
        }

        public Optional<Deadline> getPhone() {
            return deadline;
        }

        public void setEmail(Optional<Status> status) {
            assert status != null;
            this.status = status;
        }

        public Optional<Status> getEmail() {
            return status;
        }

        public void setAddress(Optional<StartTime> startTime) {
            assert startTime != null;
            this.startTime = startTime;
        }

        public Optional<StartTime> getAddress() {
            return startTime;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }
    }
}
