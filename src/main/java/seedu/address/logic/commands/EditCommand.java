package seedu.address.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartEndDateTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.exceptions.InvalidDurationException;
import seedu.address.model.task.exceptions.PastDateTimeException;

/**
 * Edits the details of an existing task in the task list.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [NAME] [by DEADLINE] "
            + "[from STARTDATE] [to ENDDATE] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 by the day after tomorrow";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list.";

    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor) {
        assert filteredTaskListIndex > 0;
        assert editTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;

        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
        // TODO if we don't know which index, we don't know if date is past date, and if we don't know if date
        // is past date we can't construct it
    }

    //@@author A0140023E
    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        //Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
        try {
            editTaskDescriptor.processFields(taskToEdit);
        } catch (PastDateTimeException e) {
            throw new CommandException(e.getMessage());
        } catch (InvalidDurationException e) {
            throw new CommandException(e.getMessage());
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }

        // TODO convert to exception like a NoFieldEditedException
        if (!this.editTaskDescriptor.isAnyFieldEdited()) {
            throw new CommandException(EditCommand.MESSAGE_NOT_EDITED);
        }

        Name updatedName = editTaskDescriptor.getUpdatedName();
        Optional<Deadline> updatedDeadline = editTaskDescriptor.getDeadline(); // getUpdatedDeadline
        // getUpdatedStartEndDateTime
        Optional<StartEndDateTime> updatedStartEndDateTime = editTaskDescriptor.getStartEndDateTime();
        UniqueTagList updatedTagList = editTaskDescriptor.getUpdatedTagList();

        Task editedTask = new Task(updatedName, updatedDeadline, updatedStartEndDateTime, updatedTagList);

        try {
            model.updateTask(filteredTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<Deadline> deadline = Optional.empty();
        private Optional<StartEndDateTime> startEndDateTime = Optional.empty();
        private Optional<UniqueTagList> tagList = Optional.empty();

        private Optional<String> rawDeadline;
        private Optional<String> rawStartDateTime;
        private Optional<String> rawEndDateTime;

        private Name updatedName;
        private UniqueTagList updatedTagList;

        public EditTaskDescriptor() {}

        public void processFields(ReadOnlyTask taskToEdit)
                throws PastDateTimeException, InvalidDurationException, IllegalValueException {

            updatedName = getName().orElseGet(taskToEdit::getName);

            if (getRawDeadline().isPresent()) {
                if (taskToEdit.getDeadline().isPresent()) {
                    // Bubble up the exception
                    deadline = ParserUtil.parseEditedDeadline(getRawDeadline(), taskToEdit.getDeadline().get());
                } else {
                    deadline = ParserUtil.parseNewDeadline(getRawDeadline());
                }
            } else {
                deadline = taskToEdit.getDeadline();
            }

            if (getRawStartDateTime().isPresent() && getRawEndDateTime().isPresent()) {
                if (taskToEdit.getStartEndDateTime().isPresent()) {
                    startEndDateTime =
                            ParserUtil.parseEditedStartEndDateTime(getRawStartDateTime(), getRawEndDateTime(),
                                    taskToEdit.getStartEndDateTime().get());
                } else {
                    startEndDateTime =
                            ParserUtil.parseNewStartEndDateTime(getRawStartDateTime(), getRawEndDateTime());
                }
            } else {
                startEndDateTime = taskToEdit.getStartEndDateTime();
            }
            updatedTagList = getTagList().orElseGet(taskToEdit::getTags);

        }

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            name = toCopy.getName();
            deadline = toCopy.getDeadline();
            startEndDateTime = toCopy.getStartEndDateTime();
            tagList = toCopy.getTagList();

            rawDeadline = toCopy.getRawDeadline();
            rawStartDateTime = toCopy.getRawStartDateTime();
            rawEndDateTime = toCopy.getRawEndDateTime();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            // TODO note that we ignore raw fields because they are not important
            return CollectionUtil.isAnyPresent(name, deadline, startEndDateTime, tagList);
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        // get updated
        public Optional<Deadline> getDeadline() {
            return deadline;
        }
        // get updated
        public Optional<StartEndDateTime> getStartEndDateTime() {
            return startEndDateTime;
        }


        public void setRawDeadline(Optional<String> rawDeadline) {
            this.rawDeadline = rawDeadline;
            // TODO Auto-generated method stub

        }

        public void setRawStartDateTime(Optional<String> value) {
            rawStartDateTime = value;
            // TODO Auto-generated method stub

        }

        public void setRawEndDateTime(Optional<String> value) {
            rawEndDateTime = value;
            // TODO Auto-generated method stub

        }

        public Optional<String> getRawDeadline() {
            return rawDeadline;
        }

        public Optional<String> getRawStartDateTime() {
            return rawStartDateTime;
        }

        public Optional<String> getRawEndDateTime() {
            return rawEndDateTime;
        }


        public Optional<UniqueTagList> getTagList() {
            return tagList;
        }

        public void setTagList(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tagList = tags;
        }

        public Name getUpdatedName() {
            return updatedName;
        }

        public UniqueTagList getUpdatedTagList() {
            return updatedTagList;
        }

    }
}
