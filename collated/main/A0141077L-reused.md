# A0141077L-reused
###### \java\seedu\watodo\logic\commands\DeleteCommand.java
``` java
/**
 * Deletes a task identified using it's last displayed index from the task manager.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) [MORE_INDICES]...\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";
    public static final String MESSAGE_DELETE_UNDO_FAIL = "Could not undo delete due to duplicate.";


    private int[] filteredTaskListIndices;

    private ReadOnlyTask taskToDelete;

    public DeleteCommand(int[] args) {
        this.filteredTaskListIndices = args;

        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            assert filteredTaskListIndices != null;
            assert filteredTaskListIndices.length > 0;
            assert filteredTaskListIndices[i] > 0;

            // converts filteredTaskListIndex to from one-based to zero-based.
            filteredTaskListIndices[i] = filteredTaskListIndices[i] - 1;
        }
    }


    @Override
    public CommandResult execute() throws CommandException {
        final StringBuilder tasksDeletedMessage = new StringBuilder();

        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

            if (filteredTaskListIndices[i] >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            taskToDelete = lastShownList.get(filteredTaskListIndices[i]);

            try {
                model.deleteTask(taskToDelete);
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }

            tasksDeletedMessage.append(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete) + "\n");
        }

        return new CommandResult(tasksDeletedMessage.toString());
    }

    @Override
    public void unexecute() {
        assert model != null;

        try {

            model.addTask(new Task(taskToDelete));
            model.updateFilteredListToShowAll();
        } catch (DuplicateTaskException e) {

        }
    }

    @Override
    public void redo() {
        assert model != null;

        try {
            model.updateFilteredListToShowAll();
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException e) {

        }
    }
  
    @Override
    public String toString() {
        return COMMAND_WORD;
    }

}
```
###### \java\seedu\watodo\logic\commands\UnmarkCommand.java
``` java
/**
 * Marks a task identified using it's last displayed index from the task manager
 * as undone.
 */
public class UnmarkCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the status of the task identified to Undone, "
            + "using the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) [MORE_INDICES]...\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    private static final String MESSAGE_INCOMPLETE_EXECUTION = "Not all tasks sucessfully marked.";
    public static final String MESSAGE_INDEX_OUT_OF_BOUNDS = "The task index provided is out of bounds.";
    public static final String MESSAGE_UNMARK_TASK_SUCCESSFUL = "Task #%1$d marked undone: %2$s";
    private static final String MESSAGE_UNMARK_TASK_UNSUCCESSFUL = "Task #%1$d unsuccessfully marked as undone.";
    public static final String MESSAGE_STATUS_AlREADY_UNDONE = "The task status is already set to Undone.";


    private int[] filteredTaskListIndices;
    private ReadOnlyTask taskToUnmark;
    private Task unmarkedTask;

    //private int indexForUndoUnmark;
    //private Task markedTaskForUndoUnmark;
    private Stack< Task > taskToUnmarkList;
    private Stack< Task > unmarkedTaskList;


    public UnmarkCommand(int[] args) {
        this.filteredTaskListIndices = args;
        changeToZeroBasedIndexing();
        taskToUnmarkList = new Stack< Task >();
        unmarkedTaskList = new Stack< Task >();
    }


    /** Converts filteredTaskListIndex from one-based to zero-based. */
    private void changeToZeroBasedIndexing() {
        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            assert filteredTaskListIndices[i] > 0;
            filteredTaskListIndices[i] = filteredTaskListIndices[i] - 1;
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        final StringBuilder compiledExecutionMessage = new StringBuilder();
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        boolean executionIncomplete = false;

        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            clearClassTaskVariables();
            try {
                checkIndexIsWithinBounds(filteredTaskListIndices[i], lastShownList);
                unmarkTaskAtIndex(filteredTaskListIndices[i], lastShownList);
                storeUnmarkedTaskForUndo(filteredTaskListIndices[i], taskToUnmark, unmarkedTask);
                compiledExecutionMessage.append(
                        String.format(MESSAGE_UNMARK_TASK_SUCCESSFUL, filteredTaskListIndices[i]+1, this.taskToUnmark) + '\n');

            } catch (IllegalValueException | CommandException e) {
                // Moves on to next index even if execution of current index is unsuccessful. CommandException thrown later.
                executionIncomplete = true;
                e.printStackTrace();
                compiledExecutionMessage.append(String.format(MESSAGE_UNMARK_TASK_UNSUCCESSFUL, filteredTaskListIndices[i]+1)
                        + '\n' + e.getMessage() + '\n');
            }
        }

        if (executionIncomplete) {
            if (multipleExectutions(filteredTaskListIndices)) {
                compiledExecutionMessage.insert(0, MESSAGE_INCOMPLETE_EXECUTION + '\n');
            }
            throw new CommandException(compiledExecutionMessage.toString());
        }

        return new CommandResult(compiledExecutionMessage.toString());
    }

    private void clearClassTaskVariables() {
        this.taskToUnmark = null;
        this.unmarkedTask = null;
    }

    private boolean multipleExectutions(int[] filteredTaskListIndices) {
        return (filteredTaskListIndices.length > 1) ? true : false;
    }

    private void checkIndexIsWithinBounds(int currIndex, UnmodifiableObservableList<ReadOnlyTask> lastShownList) throws IllegalValueException {
        if (currIndex >= lastShownList.size()) {
            throw new IllegalValueException(MESSAGE_INDEX_OUT_OF_BOUNDS);
        }
    }

    private void unmarkTaskAtIndex(int currIndex, UnmodifiableObservableList<ReadOnlyTask> lastShownList)
            throws CommandException, UniqueTaskList.DuplicateTaskException {
        this.taskToUnmark = getTaskToUnmark(currIndex, lastShownList);
        this.unmarkedTask = createUnmarkedCopyOfTask(this.taskToUnmark);

        updateTaskListAtIndex(currIndex, unmarkedTask);
    }

    private ReadOnlyTask getTaskToUnmark(int currIndex, UnmodifiableObservableList<ReadOnlyTask> lastShownList) {
        return lastShownList.get(currIndex);
    }


    private Task createUnmarkedCopyOfTask(ReadOnlyTask taskToUnmark) throws CommandException {
        assert taskToUnmark != null;

        checkCurrentTaskStatusIsDone(taskToUnmark);
        Task unmarkedTask = createUnmarkedTask(taskToUnmark);
        return unmarkedTask;
    }

    private void checkCurrentTaskStatusIsDone(ReadOnlyTask taskToUnmark) throws CommandException {
        if (taskToUnmark.getStatus() == TaskStatus.UNDONE) {
            throw new CommandException(MESSAGE_STATUS_AlREADY_UNDONE);
        }
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToMark} but with TaskStatus changed to Done
     * Assumes TaskStatus is not currently Done.
     */
    private Task createUnmarkedTask(ReadOnlyTask taskToUnmark) {
        Task unmarkedTask = new Task(taskToUnmark);
        unmarkedTask.setStatus(TaskStatus.UNDONE);
        return unmarkedTask;
    }

    private void updateTaskListAtIndex(int currIndex, Task unmarkedTask) throws UniqueTaskList.DuplicateTaskException{
        model.updateTask(currIndex, unmarkedTask);
    }

    private void storeUnmarkedTaskForUndo(int currIndex, ReadOnlyTask taskToUnmark, Task unmarkedTask) {
        //this.indexForUndoUnmark = currIndex;
        //this.markedTaskForUndoUnmark = new Task(taskToUnmark);
        this.taskToUnmarkList.push(new Task(taskToUnmark));
        this.unmarkedTaskList.push(unmarkedTask);
    }

```
###### \java\seedu\watodo\logic\commands\ViewFileCommand.java
``` java
/**
 * To view the current storage file location.
 */
public class ViewFileCommand extends Command {

    public static final String COMMAND_WORD = "viewfile";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the current storage file location.\n"
            + "Example: " + COMMAND_WORD;

    public static final String VIEW_FILE_MESSAGE = "Storage file is currently located at %s";

    @Override
    public CommandResult execute() {
        Config currConfig = getConfig();
        return new CommandResult(String.format(VIEW_FILE_MESSAGE, currConfig.getWatodoFilePath()));
    }

    private Config getConfig() {
        Config initialisedConfig;
        try {
            Optional<Config> optionalConfig = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE);
            initialisedConfig = optionalConfig.orElse(new Config());
        } catch (DataConversionException dce) {
            initialisedConfig = new Config();
        }
        return initialisedConfig;
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
```
###### \java\seedu\watodo\logic\parser\DeleteCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser {
    private static final Integer NEGATIVE_NUMBER = -1;
    int[] filteredTaskListIndices;

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {
        try {
            getOptionalIntArrayFromString(args);
            checkValidIndices();
            sortIntArray();
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
        return new DeleteCommand(filteredTaskListIndices);
    }

    private void getOptionalIntArrayFromString(String args) {
        String[] indicesInStringArray = args.split("\\s+");
        this.filteredTaskListIndices = new int[indicesInStringArray.length];

        //Sets filteredTaskListIndices[i] as NEGATIVE_NUMBER if indicesInStringArray[i] is not a positive unsigned integer
        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            Optional<Integer> optionalIndex = ParserUtil.parseIndex(indicesInStringArray[i]);
            filteredTaskListIndices[i] = optionalIndex.orElse(NEGATIVE_NUMBER);
        }
    }

    private void checkValidIndices() throws IllegalValueException {
        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            if (filteredTaskListIndices[i] == NEGATIVE_NUMBER) {
                throw new IllegalValueException(MESSAGE_INVALID_TASK_DISPLAYED_INDEX + '\n' + DeleteCommand.MESSAGE_USAGE);
            }
        }
    }

    private void sortIntArray() {
        List<Integer> tempIndicesList = Ints.asList(filteredTaskListIndices);
        Collections.sort(tempIndicesList, comparator);
        filteredTaskListIndices = Ints.toArray(tempIndicesList);
    }

    // Comparator to sort list in descending order
    Comparator<Integer> comparator = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2.compareTo(o1);
        }
    };

}
```
###### \java\seedu\watodo\logic\parser\UnmarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new UnmarkCommand object
 */
public class UnmarkCommandParser {
    private static final Integer NEGATIVE_NUMBER = -1;
    int[] filteredTaskListIndices;

    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkCommand
     * and returns an UnmarkCommand object for execution.
     */
    public Command parse(String args) {
        try {
            getOptionalIntArrayFromString(args);
            checkValidIndices();
            sortIntArray();
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
        return new UnmarkCommand(filteredTaskListIndices);
    }

    private void getOptionalIntArrayFromString(String args) {
        String[] indicesInStringArray = args.split("\\s+");
        this.filteredTaskListIndices = new int[indicesInStringArray.length];

        //Sets filteredTaskListIndices[i] as NEGATIVE_NUMBER if indicesInStringArray[i] is not a positive unsigned integer
        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            Optional<Integer> optionalIndex = ParserUtil.parseIndex(indicesInStringArray[i]);
            filteredTaskListIndices[i] = optionalIndex.orElse(NEGATIVE_NUMBER);
        }
    }

    private void checkValidIndices() throws IllegalValueException {
        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            if (filteredTaskListIndices[i] == NEGATIVE_NUMBER) {
                throw new IllegalValueException(MESSAGE_INVALID_TASK_DISPLAYED_INDEX + '\n' + UnmarkCommand.MESSAGE_USAGE);
            }
        }
    }

    private void sortIntArray() {
        List<Integer> tempIndicesList = Ints.asList(filteredTaskListIndices);
        Collections.sort(tempIndicesList, comparator);
        filteredTaskListIndices = Ints.toArray(tempIndicesList);
    }

    // Comparator to sort list in descending order
    Comparator<Integer> comparator = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2.compareTo(o1);
        }
    };

}
```
