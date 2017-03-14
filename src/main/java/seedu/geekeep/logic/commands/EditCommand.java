package seedu.geekeep.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.geekeep.commons.core.Messages;
import seedu.geekeep.commons.util.CollectionUtil;
import seedu.geekeep.logic.commands.exceptions.CommandException;
import seedu.geekeep.model.tag.UniqueTagList;
import seedu.geekeep.model.task.DateTime;
import seedu.geekeep.model.task.Location;
import seedu.geekeep.model.task.ReadOnlyTask;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.task.Title;
import seedu.geekeep.model.task.UniqueTaskList;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditTaskDescriptor {
        private Optional<Title> title = Optional.empty();
        private Optional<DateTime> endDateTime = Optional.empty();
        private Optional<DateTime> startDateTime = Optional.empty();
        private Optional<Location> location = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.title = toCopy.getTitle();
            this.endDateTime = toCopy.getEndDateTime();
            this.startDateTime = toCopy.getStartDateTime();
            this.location = toCopy.getLocation();
            this.tags = toCopy.getTags();
        }

        public Optional<DateTime> getEndDateTime() {
            return endDateTime;
        }

        public Optional<Location> getLocation() {
            return location;
        }

        public Optional<DateTime> getStartDateTime() {
            return startDateTime;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }

        public Optional<Title> getTitle() {
            return title;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.title, this.endDateTime, this.startDateTime,
                                               this.location, this.tags);
        }

        public void setEndDateTime(Optional<DateTime> endDateTime) {
            assert endDateTime != null;
            this.endDateTime = endDateTime;
        }

        public void setLocation(Optional<Location> location) {
            assert location != null;
            this.location = location;
        }

        public void setStartDateTime(Optional<DateTime> startDateTime) {
            assert startDateTime != null;
            this.startDateTime = startDateTime;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public void setTitle(Optional<Title> title) {
            assert title != null;
            this.title = title;
        }
    }

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS ] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 p/91234567 e/johndoe@yahoo.com";
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
                                             EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Title updatedTitle = editTaskDescriptor.getTitle().orElseGet(taskToEdit::getTitle);
        DateTime updatedEndDateTime
                = editTaskDescriptor.getEndDateTime().orElseGet(taskToEdit::getEndDateTime);
        DateTime updatedStartDateTime
                = editTaskDescriptor.getStartDateTime().orElseGet(taskToEdit::getStartDateTime);
        Location updatedLocation = editTaskDescriptor.getLocation().orElseGet(taskToEdit::getLocation);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        return new Task(updatedTitle, updatedStartDateTime, updatedEndDateTime, updatedLocation, updatedTags);
    }

    private final int filteredPersonListIndex;

    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param filteredPersonListIndex the index of the person in the filtered person list to edit
     * @param editTaskDescriptor details to edit the person with
     */
    public EditCommand(int filteredPersonListIndex, EditTaskDescriptor editTaskDescriptor) {
        assert filteredPersonListIndex > 0;
        assert editTaskDescriptor != null;

        // converts filteredPersonListIndex from one-based to zero-based.
        this.filteredPersonListIndex = filteredPersonListIndex - 1;

        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredPersonListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyTask personToEdit = lastShownList.get(filteredPersonListIndex);
        Task editedTask = createEditedTask(personToEdit, editTaskDescriptor);

        try {
            model.updateTask(filteredPersonListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, personToEdit));
    }
}
