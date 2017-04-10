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

    public static final String MESSAGE_DELETE_UNDO_FAIL = "Could not undo delete due to duplicate."; //TODO merv to use
    public static final String MESSAGE_DUPLICATE_INDICES = "Duplicate indices are not allowed.";
    public static final String MESSAGE_INCOMPLETE_EXECUTION = "Not all tasks successfully deleted.";
    public static final String MESSAGE_INDEX_OUT_OF_BOUNDS = "The task index provided is out of bounds.";
    public static final String MESSAGE_DELETE_TASK_SUCCESSFUL = "Task #%1$d deleted: %2$s";
    public static final String MESSAGE_DELETE_TASK_UNSUCCESSFUL = "Task #%1$d unsuccessfully deleted.";
    public static final String MESSAGE_STATUS_ALREADY_DONE = "The task status is already set to Done.";

    private int[] filteredTaskListIndices;
    private ReadOnlyTask taskToDelete;

    private Stack< Task > deletedTaskList;

    public DeleteCommand(int[] args) {
        this.filteredTaskListIndices = args;
        changeToZeroBasedIndexing();
        deletedTaskList = new Stack< Task >();
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
                deleteTaskAtIndex(filteredTaskListIndices[i], lastShownList);
                compiledExecutionMessage.append(String.format(MESSAGE_DELETE_TASK_SUCCESSFUL,
                        filteredTaskListIndices[i] + 1, this.taskToDelete) + '\n');

            } catch (IllegalValueException | TaskNotFoundException e) {
                // Moves on to next index even if current index execution is unsuccessful. CommandException thrown later
                executionIncomplete = true;
                e.printStackTrace();
                compiledExecutionMessage.append(String.format(MESSAGE_DELETE_TASK_UNSUCCESSFUL,
                        filteredTaskListIndices[i] + 1) + '\n' + e.getMessage() + '\n');
            }
        }

        if (executionIncomplete) {
            if (multipleExecutions(filteredTaskListIndices)) {
                compiledExecutionMessage.insert(0, MESSAGE_INCOMPLETE_EXECUTION + '\n');
            }
            throw new CommandException(compiledExecutionMessage.toString());
        }

        return new CommandResult(compiledExecutionMessage.toString());
    }

    private void clearClassTaskVariables() {
        this.taskToDelete = null;
    }

    private boolean multipleExecutions(int[] filteredTaskListIndices) {
        return (filteredTaskListIndices.length > 1) ? true : false;
    }

    private void checkIndexIsWithinBounds(int currIndex, UnmodifiableObservableList<ReadOnlyTask> lastShownList)
            throws IllegalValueException {
        if (currIndex >= lastShownList.size()) {
            throw new IllegalValueException(MESSAGE_INDEX_OUT_OF_BOUNDS);
        }
    }

    private void deleteTaskAtIndex(int currIndex, UnmodifiableObservableList<ReadOnlyTask> lastShownList)
            throws UniqueTaskList.TaskNotFoundException {
        this.taskToDelete = getTaskToDelete(currIndex, lastShownList);
        storeTasksForUndo(taskToDelete);
        deleteTask(taskToDelete);
    }

    private ReadOnlyTask getTaskToDelete(int currIndex, UnmodifiableObservableList<ReadOnlyTask> lastShownList) {
        return lastShownList.get(currIndex);
    }

    private void storeTasksForUndo(ReadOnlyTask taskToDelete) {
        this.deletedTaskList.push(new Task(taskToDelete));
    }

    private void deleteTask(ReadOnlyTask taskToDelete) throws UniqueTaskList.TaskNotFoundException {
        model.deleteTask(taskToDelete);
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

    public static final String MESSAGE_DUPLICATE_INDICES = "Duplicate indices are not allowed.";
    public static final String MESSAGE_INCOMPLETE_EXECUTION = "Not all tasks successfully marked.";
    public static final String MESSAGE_INDEX_OUT_OF_BOUNDS = "The task index provided is out of bounds.";
    public static final String MESSAGE_UNMARK_TASK_SUCCESSFUL = "Task #%1$d marked undone: %2$s";
    public static final String MESSAGE_UNMARK_TASK_UNSUCCESSFUL = "Task #%1$d unsuccessfully marked as undone.";
    public static final String MESSAGE_STATUS_ALREADY_UNDONE = "The task status is already set to Undone.";

    private int[] filteredTaskListIndices;
    private ReadOnlyTask taskToUnmark;
    private Task unmarkedTask;

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
                compiledExecutionMessage.append(String.format(MESSAGE_UNMARK_TASK_SUCCESSFUL,
                        filteredTaskListIndices[i] + 1, this.taskToUnmark) + '\n');

            } catch (IllegalValueException | CommandException e) {
                // Moves on to next index even if current index execution is unsuccessful. CommandException thrown later
                executionIncomplete = true;
                e.printStackTrace();
                compiledExecutionMessage.append(String.format(MESSAGE_UNMARK_TASK_UNSUCCESSFUL,
                        filteredTaskListIndices[i] + 1) + '\n' + e.getMessage() + '\n');
            }
        }

        if (executionIncomplete) {
            if (multipleExecutions(filteredTaskListIndices)) {
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

    private boolean multipleExecutions(int[] filteredTaskListIndices) {
        return (filteredTaskListIndices.length > 1) ? true : false;
    }

    private void checkIndexIsWithinBounds(int currIndex, UnmodifiableObservableList<ReadOnlyTask> lastShownList)
            throws IllegalValueException {
        if (currIndex >= lastShownList.size()) {
            throw new IllegalValueException(MESSAGE_INDEX_OUT_OF_BOUNDS);
        }
    }

    private void unmarkTaskAtIndex(int currIndex, UnmodifiableObservableList<ReadOnlyTask> lastShownList)
            throws CommandException, UniqueTaskList.DuplicateTaskException {
        this.taskToUnmark = getTaskToUnmark(currIndex, lastShownList);
        this.unmarkedTask = createUnmarkedCopyOfTask(this.taskToUnmark);
        storeTasksForUndo(taskToUnmark, unmarkedTask);
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
            throw new CommandException(MESSAGE_STATUS_ALREADY_UNDONE);
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

    private void storeTasksForUndo(ReadOnlyTask taskToUnmark, Task unmarkedTask) {
        this.taskToUnmarkList.push(new Task(taskToUnmark));
        this.unmarkedTaskList.push(unmarkedTask);
    }

    private void updateTaskListAtIndex(int currIndex, Task unmarkedTask) throws UniqueTaskList.DuplicateTaskException {
        model.updateTask(currIndex, unmarkedTask);
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
    private static final Integer INVALID_NUMBER = -1;
    int[] filteredTaskListIndices;

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {
        try {
            checkIndexFound(args);
            getOptionalIntArrayFromString(args);
            checkValidIndices();
            checkForDuplicateIndices();
            sortIntArray();
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
        return new DeleteCommand(filteredTaskListIndices);
    }

    private void checkIndexFound(String args) throws IllegalValueException {
        if (args.isEmpty()) {
            throw new IllegalValueException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

    private void getOptionalIntArrayFromString(String args) {
        String[] indicesInStringArray = args.split("\\s+");
        this.filteredTaskListIndices = new int[indicesInStringArray.length];

        //Sets index as NEGATIVE_NUMBER if it is not a positive unsigned integer
        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            Optional<Integer> optionalIndex = ParserUtil.parseIndex(indicesInStringArray[i]);
            filteredTaskListIndices[i] = optionalIndex.orElse(INVALID_NUMBER);
        }
    }

    private void checkValidIndices() throws IllegalValueException {
        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            if (filteredTaskListIndices[i] == INVALID_NUMBER) {
                throw new IllegalValueException(MESSAGE_INVALID_TASK_DISPLAYED_INDEX + '\n' +
                        DeleteCommand.MESSAGE_USAGE);
            }
        }
    }

    /** Ensures that there are no duplicate indices parsed */
    private void checkForDuplicateIndices() throws IllegalValueException {
        List<Integer> indicesAsInteger = Ints.asList(filteredTaskListIndices);
        Set<Integer> indicesHashSet = new HashSet<Integer>();
        for (Integer index : indicesAsInteger) {
            if (!indicesHashSet.add(index)) {
                throw new IllegalValueException(DeleteCommand.MESSAGE_DUPLICATE_INDICES);
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
    private static final Integer INVALID_NUMBER = -1;
    int[] filteredTaskListIndices;

    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkCommand
     * and returns an UnmarkCommand object for execution.
     */
    public Command parse(String args) {
        try {
            checkIndexFound(args);
            getOptionalIntArrayFromString(args);
            checkValidIndices();
            checkForDuplicateIndices();
            sortIntArray();
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
        return new UnmarkCommand(filteredTaskListIndices);
    }

    private void checkIndexFound(String args) throws IllegalValueException {
        if (args.isEmpty()) {
            throw new IllegalValueException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
        }
    }

    private void getOptionalIntArrayFromString(String args) {
        String[] indicesInStringArray = args.split("\\s+");
        this.filteredTaskListIndices = new int[indicesInStringArray.length];

        //Sets index as NEGATIVE_NUMBER if it is not a positive unsigned integer
        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            Optional<Integer> optionalIndex = ParserUtil.parseIndex(indicesInStringArray[i]);
            filteredTaskListIndices[i] = optionalIndex.orElse(INVALID_NUMBER);
        }
    }

    private void checkValidIndices() throws IllegalValueException {
        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            if (filteredTaskListIndices[i] == INVALID_NUMBER) {
                throw new IllegalValueException(MESSAGE_INVALID_TASK_DISPLAYED_INDEX + '\n' +
                        UnmarkCommand.MESSAGE_USAGE);
            }
        }
    }

    /** Ensures that there are no duplicate indices parsed */
    private void checkForDuplicateIndices() throws IllegalValueException {
        List<Integer> indicesAsInteger = Ints.asList(filteredTaskListIndices);
        Set<Integer> indicesHashSet = new HashSet<Integer>();
        for (Integer index : indicesAsInteger) {
            if (!indicesHashSet.add(index)) {
                throw new IllegalValueException(UnmarkCommand.MESSAGE_DUPLICATE_INDICES);
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
