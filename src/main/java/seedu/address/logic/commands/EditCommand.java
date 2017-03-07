package seedu.address.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Information;
import seedu.address.model.person.PriorityLevel;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.Task;
import seedu.address.model.person.TaskName;
import seedu.address.model.person.UniqueTagList;
import seedu.address.model.tag.UniqueTagList;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS ] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 p/91234567 e/johndoe@yahoo.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final int filteredPersonListIndex;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param filteredPersonListIndex the index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(int filteredPersonListIndex, EditTaskDescriptor editPersonDescriptor) {
        assert filteredPersonListIndex > 0;
        assert editPersonDescriptor != null;

        // converts filteredPersonListIndex from one-based to zero-based.
        this.filteredPersonListIndex = filteredPersonListIndex - 1;

        this.editTaskDescriptor = new EditTaskDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredPersonList();

        if (filteredPersonListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyTask personToEdit = lastShownList.get(filteredPersonListIndex);
        Task editedPerson = createEditedPerson(personToEdit, editTaskDescriptor);

        try {
            model.updatePerson(filteredPersonListIndex, editedPerson);
        } catch (UniqueTagList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, personToEdit));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Task createEditedPerson(ReadOnlyTask personToEdit,
                                             EditTaskDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        TaskName updatedTaskName = editPersonDescriptor.getTaskName().orElseGet(personToEdit::getName);
        Deadline updatedDeadline = editPersonDescriptor.getDeadline().orElseGet(personToEdit::getDate);
        PriorityLevel updatedPriorityLevel = editPersonDescriptor.getPriorityLevel().orElseGet(personToEdit::getPriority);
        Information updatedInformation = editPersonDescriptor.getInfo().orElseGet(personToEdit::getInfo);
        UniqueTagList updatedTags = editPersonDescriptor.getTags().orElseGet(personToEdit::getTags);

        return new Task(updatedTaskName, updatedDeadline, updatedPriorityLevel, updatedInformation, updatedTags);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditTaskDescriptor {
        private Optional<TaskName> taskName = Optional.empty();
        private Optional<Deadline> deadline = Optional.empty();
        private Optional<PriorityLevel> priorityLevel = Optional.empty();
        private Optional<Information> info = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.taskName = toCopy.getTaskName();
            this.deadline = toCopy.getDeadline();
            this.priorityLevel = toCopy.getPriorityLevel();
            this.info = toCopy.getInfo();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.taskName, this.deadline, this.priorityLevel, this.info, this.tags);
        }

        public void setName(Optional<TaskName> name) {
            assert name != null;
            this.taskName = name;
        }

        public Optional<TaskName> getTaskName() {
            return taskName;
        }

        public void setDeadline(Optional<Deadline> deadline) {
            assert deadline != null;
            this.deadline = deadline;
        }

        public Optional<Deadline> getDeadline() {
            return deadline;
        }

        public void setPriorityLevel(Optional<PriorityLevel> priorityLevel) {
            assert priorityLevel != null;
            this.priorityLevel = priorityLevel;
        }

        public Optional<PriorityLevel> getPriorityLevel() {
            return priorityLevel;
        }

        public void setInfo(Optional<Information> info) {
            assert info != null;
            this.info = info;
        }

        public Optional<Information> getInfo() {
            return info;
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
