package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.util.Pair;
import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyToDoList;
import seedu.address.model.ToDoList;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Description;
import seedu.address.model.task.EndTime;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UrgencyLevel;
import seedu.address.model.task.Venue;

/**
 * Edits the details of an existing task in the to-do list.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer)[TITLE][place/VENUE][from/STARTTIME]"
            + "[level/URGENCYLEVEL][des/DESCRIPTION][to/ENDTIME][#TAG]..\n" + "Example: " + COMMAND_WORD + " 1 place/Toilet";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the to-do list.";

    private final int filteredTaskListIndex;
    private final char filteredTaskListChar;
    private final EditTaskDescriptor editTaskDescriptor;
    private ReadOnlyToDoList originalToDoList;
    private CommandResult commandResultToUndo;

    /**
     * @param filteredTaskListIndex
     *            the index of the task in the filtered task list to edit
     * @param editTaskDescriptor
     *            details to edit the task with
     */
    public EditCommand(Pair<Character,Integer> filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor) {
        assert filteredTaskListIndex.getValue() > 0;
        assert editTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex.getValue() - 1;
        this.filteredTaskListChar = filteredTaskListIndex.getKey();

        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    // @@author A0143648Y
    @Override
    public CommandResult execute() throws CommandException {
        originalToDoList = new ToDoList(model.getToDoList());
        List<ReadOnlyTask> lastShownList = model.getListFromChar(filteredTaskListChar);

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        try {
            model.updateTask(filteredTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        commandResultToUndo = new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
        updateUndoLists();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    @Override
    public void updateUndoLists() {
        if (previousToDoLists == null) {
            previousToDoLists = new ArrayList<ReadOnlyToDoList>(3);
            previousCommandResults = new ArrayList<CommandResult>(3);
        }
        if (previousToDoLists.size() >= 3) {
            previousToDoLists.remove(0);
            previousCommandResults.remove(0);
            previousToDoLists.add(originalToDoList);
            previousCommandResults.add(commandResultToUndo);
        } else {
            previousToDoLists.add(originalToDoList);
            previousCommandResults.add(commandResultToUndo);
        }
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private Task createEditedTask(ReadOnlyTask taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Title updatedTitle = editTitle(taskToEdit);
        Venue updatedVenue = editVenue(taskToEdit);
        StartTime updatedStartTime = editStartTime(taskToEdit);
        EndTime updatedEndTime = editEndTime(taskToEdit);
        UrgencyLevel updatedUrgencyLevel = editUrgencyLevel(taskToEdit);
        Description updatedDescription = editDescription(taskToEdit);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        return new Task(updatedTitle, updatedVenue, updatedStartTime, updatedEndTime, updatedUrgencyLevel,
                updatedDescription, updatedTags);
    }
    
    public Title editTitle(ReadOnlyTask taskToEdit){
        return editTaskDescriptor.getTitle().isPresent() ? editTaskDescriptor.getTitle().get() : taskToEdit.getTitle();
    }
    
    public Venue editVenue(ReadOnlyTask taskToEdit){
        if (editTaskDescriptor.getVenue().isPresent()){
            return editTaskDescriptor.getVenue().get();
        }
        else if (taskToEdit.getVenue().isPresent()){
            return taskToEdit.getVenue().get();
        }
        return null;
    }
    
    public StartTime editStartTime(ReadOnlyTask taskToEdit){
        if (editTaskDescriptor.getStartTime().isPresent()){
            return editTaskDescriptor.getStartTime().get();
        }
        else if (taskToEdit.getStartTime().isPresent()){
            return taskToEdit.getStartTime().get();
        }
        return null;
    }
    
    public EndTime editEndTime(ReadOnlyTask taskToEdit){
        if (editTaskDescriptor.getEndTime().isPresent()){
            return editTaskDescriptor.getEndTime().get();
        }
        else if (taskToEdit.getEndTime().isPresent()){
            return taskToEdit.getEndTime().get();
        }
        return null;
    }
    
    public UrgencyLevel editUrgencyLevel(ReadOnlyTask taskToEdit){
        if (editTaskDescriptor.getUrgencyLevel().isPresent()){
            return editTaskDescriptor.getUrgencyLevel().get();
        }
        else if (taskToEdit.getUrgencyLevel().isPresent()){
            return taskToEdit.getUrgencyLevel().get();
        }
        return null;
    }
    
    public Description editDescription(ReadOnlyTask taskToEdit){
        if (editTaskDescriptor.getDescription().isPresent()){
            return editTaskDescriptor.getDescription().get();
        }
        else if (taskToEdit.getDescription().isPresent()){
            return taskToEdit.getDescription().get();
        }
        return null;
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will
     * replace the corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Title> title = Optional.empty();
        private Optional<Venue> venue = Optional.empty();
        private Optional<StartTime> startTime = Optional.empty();
        private Optional<EndTime> endTime = Optional.empty();
        private Optional<UrgencyLevel> urgencyLevel = Optional.empty();
        private Optional<Description> description = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditTaskDescriptor() {
        }

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.title = toCopy.getTitle();
            this.venue = toCopy.getVenue();
            this.startTime = toCopy.getStartTime();
            this.endTime = toCopy.getEndTime();
            this.urgencyLevel = toCopy.getUrgencyLevel();
            this.description = toCopy.getDescription();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.title, this.venue, this.startTime, this.endTime, this.urgencyLevel,
                    this.description, this.tags);
        }

        public void setTitle(Optional<Title> title) {
            assert title != null;
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return title;
        }

        public void setVenue(Optional<Venue> venue) {
            assert venue != null;
            this.venue = venue;
        }

        public Optional<Venue> getVenue() {
            return venue;
        }

        public void setStartTime(Optional<StartTime> startTime) {
            assert startTime != null;
            this.startTime = startTime;
        }

        public Optional<StartTime> getStartTime() {
            return startTime;
        }

        public void setEndTime(Optional<EndTime> endTime) {
            assert endTime != null;
            this.endTime = endTime;
        }

        public Optional<EndTime> getEndTime() {
            return endTime;
        }

        public void setUrgencyLevel(Optional<UrgencyLevel> urgencyLevel) {
            assert urgencyLevel != null;
            this.urgencyLevel = urgencyLevel;
        }

        public Optional<UrgencyLevel> getUrgencyLevel() {
            return urgencyLevel;
        }

        public void setDescription(Optional<Description> description) {
            assert description != null;
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return description;
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
