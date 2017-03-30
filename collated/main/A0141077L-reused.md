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
            + "Parameters: INDEX (must be a positive integer) [MORE_INDICES]\n"
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
        } catch (DuplicateTaskException e) {

        }
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the status of the task identified to undone "
            + "by the index number used in the last task listing as completed.\n"
            + "Parameters: INDEX (must be a positive integer) [MORE_INDICES]\n" + "Example: " + COMMAND_WORD
            + " 1 2";

    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "Task undone: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String MESSAGE_STATUS_UNDONE = "The task status is already set to Undone.";

    private int[] filteredTaskListIndices;

    private Task undoUnmark;
    private int undoUnmarkInt;

    public UnmarkCommand(int[] args) {
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
        final StringBuilder tasksUnmarkedMessage = new StringBuilder();

        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

            if (filteredTaskListIndices[i] >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyTask taskToUnmark = lastShownList.get(filteredTaskListIndices[i]);
            this.undoUnmark = new Task(taskToUnmark);

            try {
                Task unmarkedTask = createUnmarkedTask(taskToUnmark);
                model.updateTask(filteredTaskListIndices[i], unmarkedTask);
                this.undoUnmarkInt = filteredTaskListIndices[i];

            } catch (UniqueTaskList.DuplicateTaskException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            }

            tasksUnmarkedMessage.append(String.format(MESSAGE_UNMARK_TASK_SUCCESS, taskToUnmark) + "\n");
        }

        return new CommandResult(tasksUnmarkedMessage.toString());
    }

    @Override
    public void unexecute() {
        try {
            model.updateTask(undoUnmarkInt, undoUnmark);
        } catch (DuplicateTaskException e) {

        }
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToUnmark}
     */
    private static Task createUnmarkedTask(ReadOnlyTask taskToUnmark) throws CommandException {
        assert taskToUnmark != null;

        if (taskToUnmark.getStatus() == TaskStatus.UNDONE) {
            throw new CommandException(MESSAGE_STATUS_UNDONE);
        }

        Task unmarkedTask = new Task(taskToUnmark.getDescription(), taskToUnmark.getStartDate(),
                taskToUnmark.getEndDate(), taskToUnmark.getTags());
        unmarkedTask.setStatus(TaskStatus.UNDONE);

        return unmarkedTask;
    }

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
        return new CommandResult(String.format(VIEW_FILE_MESSAGE, getConfig().getWatodoFilePath()));
    }

    private Config getConfig() {
        try {
            Optional<Config> optionalConfig = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE);
            return optionalConfig.orElse(new Config());
        } catch (DataConversionException dce) {
            return new Config();
        }
    }
}
```
###### \java\seedu\watodo\logic\parser\DeleteCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser {
    int[] filteredTaskListIndices;

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {
        String[] indicesInString = args.split("\\s+");
        this.filteredTaskListIndices = new int[indicesInString.length];

        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            // To convert string array to int array
            try {
                filteredTaskListIndices[i] = Integer.parseInt(indicesInString[i]);
            } catch (NumberFormatException nfe) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }

            // To check if indices are valid
            Optional<Integer> index = ParserUtil.parseIndex(indicesInString[i]);
            if (!index.isPresent()) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        }

        // To sort int array
        List<Integer> list = Ints.asList(filteredTaskListIndices);
        Collections.sort(list, comparator);
        filteredTaskListIndices = Ints.toArray(list);

        return new DeleteCommand(filteredTaskListIndices);
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
    int[] filteredTaskListIndices;

    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkCommand
     * and returns an UnmarkCommand object for execution.
     */
    public Command parse(String args) {
        String[] indicesInString = args.split("\\s+");
        this.filteredTaskListIndices = new int[indicesInString.length];

        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            // To convert string array to int array
            try {
                filteredTaskListIndices[i] = Integer.parseInt(indicesInString[i]);
            } catch (NumberFormatException nfe) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
            }

            // To check if indices are valid
            Optional<Integer> index = ParserUtil.parseIndex(indicesInString[i]);
            if (!index.isPresent()) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
            }
        }

        // To sort int array
        List<Integer> list = Ints.asList(filteredTaskListIndices);
        Collections.sort(list, comparator);
        filteredTaskListIndices = Ints.toArray(list);

        return new UnmarkCommand(filteredTaskListIndices);
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
