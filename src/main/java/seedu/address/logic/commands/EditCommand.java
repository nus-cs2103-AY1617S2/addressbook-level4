package seedu.address.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Location;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Name;
import seedu.address.model.person.Task;
import seedu.address.model.person.Date;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.UniqueTagList;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [NAME] [p/DATE] [e/REMARK] [a/LOCATION ] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 p/91234567 e/johndoe@yahoo.com";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This person already exists in the address book.";

    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param filteredTaskListIndex the index of the person in the filtered person list to edit
     * @param editTaskDescriptor details to edit the person with
     */
    public EditCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor) {
        assert filteredTaskListIndex > 0;
        assert editTaskDescriptor != null;

        // converts filteredPersonListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;

        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyPerson taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task editedPerson = createEditedTask(taskToEdit, editTaskDescriptor);

        try {
            model.updatePerson(filteredTaskListIndex, editedPerson);
        } catch (UniquePersonList.DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyPerson taskToEdit,
                                             EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
        Date updatedDate = editTaskDescriptor.getDate().orElseGet(taskToEdit::getPhone);
        Remark updatedRemark = editTaskDescriptor.getRemark().orElseGet(taskToEdit::getEmail);
        Location updatedLocation = editTaskDescriptor.getLocation().orElseGet(taskToEdit::getAddress);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        return new Task(updatedName, updatedDate, updatedRemark, updatedLocation, updatedTags);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<Date> date = Optional.empty();
        private Optional<Remark> remark = Optional.empty();
        private Optional<Location> location = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.date = toCopy.getDate();
            this.remark = toCopy.getRemark();
            this.location = toCopy.getLocation();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name, this.date, this.remark, this.location, this.tags);
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setPhone(Optional<Date> date) {
            assert date != null;
            this.date = date;
        }

        public Optional<Date> getDate() {
            return date;
        }

        public void setEmail(Optional<Remark> remark) {
            assert remark != null;
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return remark;
        }

        public void setAddress(Optional<Location> location) {
            assert location != null;
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return location;
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
