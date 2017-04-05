package seedu.address.logic.commands;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
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
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit that is different must be provided.";
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
    }

    //@@author A0140023E
    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        try {
            editTaskDescriptor.processFields(taskToEdit);
        } catch (PastDateTimeException | InvalidDurationException | IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }

        if (!this.editTaskDescriptor.isAnyFieldEdited(taskToEdit)) {
            throw new CommandException(EditCommand.MESSAGE_NOT_EDITED);
        }

        Task editedTask = new Task(editTaskDescriptor.getUpdatedName(),
                editTaskDescriptor.getUpdatedDeadline(),
                editTaskDescriptor.getUpdatedStartEndDateTime(),
                editTaskDescriptor.getUpdatedTagList());

        try {
            model.updateTask(filteredTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        //@@author A0148052L-reused
        model.pushCommand(COMMAND_WORD);
        model.pushStatus(model.getTaskList());
        //@@author
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<String> rawDeadline = Optional.empty();
        private Optional<String> rawStartDateTime = Optional.empty();
        private Optional<String> rawEndDateTime = Optional.empty();
        private Optional<UniqueTagList> tagList = Optional.empty();

        private Name updatedName;
        private Optional<Deadline> updatedDeadline;
        private Optional<StartEndDateTime> updatedStartEndDateTime;
        private UniqueTagList updatedTagList;

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            name = toCopy.getName();
            rawDeadline = toCopy.getRawDeadline();
            rawStartDateTime = toCopy.getRawStartDateTime();
            rawEndDateTime = toCopy.getRawEndDateTime();
            tagList = toCopy.getTagList();

            updatedName = toCopy.getUpdatedName();
            updatedDeadline = toCopy.getUpdatedDeadline();
            updatedStartEndDateTime = toCopy.getUpdatedStartEndDateTime();
            updatedTagList = toCopy.getUpdatedTagList();
        }

        public void processFields(ReadOnlyTask taskToEdit)
                throws PastDateTimeException, InvalidDurationException, IllegalValueException {

            processName(taskToEdit);
            processDeadline(taskToEdit);
            processStartEndDateTime(taskToEdit);
            processTagList(taskToEdit);
        }

        private void processName(ReadOnlyTask taskToEdit) {
            updatedName = getName().orElseGet(taskToEdit::getName);
        }

        private void processDeadline(ReadOnlyTask taskToEdit)
                throws PastDateTimeException, IllegalValueException {
            if (!getRawDeadline().isPresent()) {
                updatedDeadline = taskToEdit.getDeadline();
                return;
            }

            if (taskToEdit.getDeadline().isPresent()) {
                updatedDeadline = ParserUtil.parseEditedDeadline(getRawDeadline(), taskToEdit.getDeadline().get());
            } else {
                updatedDeadline = ParserUtil.parseNewDeadline(getRawDeadline());
            }
        }

        private void processStartEndDateTime(ReadOnlyTask taskToEdit)
                throws PastDateTimeException, InvalidDurationException, IllegalValueException {
            updatedStartEndDateTime = Optional.empty();

            if (hasNoRawStartAndEndDateTime()) {
                updatedStartEndDateTime = taskToEdit.getStartEndDateTime();
                return;
            }

            if (!taskToEdit.getStartEndDateTime().isPresent()) {
                if (hasBothRawStartAndEndDateTime()) {
                    updatedStartEndDateTime =
                            ParserUtil.parseNewStartEndDateTime(getRawStartDateTime(), getRawEndDateTime());
                    return;
                }
                throw new IllegalValueException("Must have both start and end date-time");
            }

            final StartEndDateTime originalStartEndDateTime = taskToEdit.getStartEndDateTime().get();

            if (hasBothRawStartAndEndDateTime()) {
                processUsingRawStartAndEnd(originalStartEndDateTime);
            } else if (hasOnlyRawStartDateTime()) {
                processUsingRawStart(originalStartEndDateTime);
            } else if (hasOnlyRawEndDateTime()) {
                processUsingRawEnd(originalStartEndDateTime);
            }
        }

        private boolean hasNoRawStartAndEndDateTime() {
            return !getRawStartDateTime().isPresent() && !getRawEndDateTime().isPresent();
        }

        private boolean hasBothRawStartAndEndDateTime() {
            return getRawStartDateTime().isPresent() && getRawEndDateTime().isPresent();
        }

        private boolean hasOnlyRawStartDateTime() {
            return getRawStartDateTime().isPresent() && !getRawEndDateTime().isPresent();
        }

        private boolean hasOnlyRawEndDateTime() {
            return !getRawStartDateTime().isPresent() && getRawEndDateTime().isPresent();
        }

        /**
         * Process the updated StartEndDateTime with the raw start and date-time with reference to the
         * original start-end date-time.
         */
        private void processUsingRawStartAndEnd(StartEndDateTime originalStartEndDateTime)
                throws PastDateTimeException, InvalidDurationException, IllegalValueException {

            updatedStartEndDateTime = ParserUtil.parseEditedStartEndDateTime(getRawStartDateTime(),
                    getRawEndDateTime(), originalStartEndDateTime);
        }

        /**
         * Process the updated StartEndDateTime with the raw start date-time with reference to the
         * original end date time.
         */
        private void processUsingRawStart(StartEndDateTime originalStartEndDateTime)
                throws PastDateTimeException, InvalidDurationException, IllegalValueException {

            ZonedDateTime startDateTime = ParserUtil.parseEditedDateTimeString(
                    getRawStartDateTime().get(), originalStartEndDateTime.getStartDateTime());
            ZonedDateTime endDateTime = originalStartEndDateTime.getEndDateTime();
            updatedStartEndDateTime = Optional.of(new StartEndDateTime(startDateTime, endDateTime));
        }

        /**
         * Process the updated StartEndDateTime with the raw end date-time with reference to the
         * original start date time.
         */
        private void processUsingRawEnd(StartEndDateTime originalStartEndDateTime)
                throws PastDateTimeException, InvalidDurationException, IllegalValueException {

            ZonedDateTime startDateTime = originalStartEndDateTime.getStartDateTime();
            ZonedDateTime endDateTime = ParserUtil.parseEditedDateTimeString(getRawEndDateTime().get(),
                    originalStartEndDateTime.getEndDateTime());
            updatedStartEndDateTime = Optional.of(new StartEndDateTime(startDateTime, endDateTime));
        }

        private void processTagList(ReadOnlyTask taskToEdit) {
            updatedTagList = getTagList().orElseGet(taskToEdit::getTags);
        }

        /**
         * Returns true if at least one field that is different is edited.
         */
        public boolean isAnyFieldEdited(ReadOnlyTask taskToEdit) {
            // note that the tags are added in alphabetical order and uses list compare vs set compare
            if (updatedName.equals(taskToEdit.getName())
                    && updatedDeadline.equals(taskToEdit.getDeadline())
                    && updatedStartEndDateTime.equals(taskToEdit.getStartEndDateTime())
                    && updatedTagList.equals(taskToEdit.getTags())) {
                return false;
            }
            return true;
        }

        //// methods for initializing an EditTaskDescriptor
        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public void setRawDeadline(Optional<String> rawDeadline) {
            assert rawDeadline != null;
            this.rawDeadline = rawDeadline;
        }

        public void setRawStartDateTime(Optional<String> rawStartDateTime) {
            assert rawStartDateTime != null;
            this.rawStartDateTime = rawStartDateTime;
        }

        public void setRawEndDateTime(Optional<String> rawEndDateTime) {
            assert rawEndDateTime != null;
            this.rawEndDateTime = rawEndDateTime;
        }

        public void setTagList(Optional<UniqueTagList> tagList) {
            assert tagList != null;
            this.tagList = tagList;
        }

        //// methods for getting the un-processed edited fields
        public Optional<Name> getName() {
            return name;
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

        //// methods for getting the processed edited fields
        public Name getUpdatedName() {
            return updatedName;
        }

        public Optional<Deadline> getUpdatedDeadline() {
            return updatedDeadline;
        }
        public Optional<StartEndDateTime> getUpdatedStartEndDateTime() {
            return updatedStartEndDateTime;
        }

        public UniqueTagList getUpdatedTagList() {
            return updatedTagList;
        }
    }
}
