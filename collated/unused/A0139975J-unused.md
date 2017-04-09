# A0139975J-unused
###### /FindDateCommand.java
``` java
// merged with find command
public class FindDateCommand extends Command {

    public static final String COMMAND_WORD_1 = "finddate";

    public static final String MESSAGE_LISTBYDONE_SUCCESS = "Listed all tasks with specified date";
    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Lists all tasks with specified date in KIT.\n"
            + "Example: " + COMMAND_WORD_1;


    private final Date date;

    public FindDateCommand(String date) throws IllegalValueException {
        this.date = new Date(date);
    }

    @Override
    public CommandResult execute() {
        model.sortTaskList();
        model.updateFilteredTaskList(this.date);
        return new CommandResult(getMessageForDoneTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
```
###### /FindDateCommandParser.java
``` java
// merged with find command
public class FindDateCommandParser extends CommandParser {

    public static final String DEFAULT_DATE = "DEFAULT_DATE";
    public static final String MESSAGE_DATE_CONSTRAINTS = "Date format invalid, try dates like,"
            + " tomorrow at 5pm or 4th April." + " Check that Month is before date," + " MM/DD/YY or MM-DD-YY";

    @Override
    public Command parse(String date) {
        try {
            return new FindDateCommand(date);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
```
###### /ListByDoneCommand.java
``` java
// merged with list command
public class ListByDoneCommand extends Command {

    public static final String COMMAND_WORD_1 = "listdone";
    public static final String COMMAND_WORD_2 = "ld";

    public static final String MESSAGE_LISTBYDONE_SUCCESS = "Listed all done tasks";
    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Lists all done tasks in KIT.\n"
            + "Example: " + COMMAND_WORD_1;


    private final boolean value;

    public ListByDoneCommand(boolean value) {
        this.value = value;
    }

    @Override
    public CommandResult execute() {
        model.sortTaskList();
        model.updateFilteredTaskList(this.value);
        return new CommandResult(getMessageForDoneTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
```
###### /ListByDoneCommandParser.java
``` java
// merged with list command
public class ListByDoneCommandParser extends CommandParser {

    private boolean isDone = true;


    @Override
    public Command parse(String args) {
        return new ListByDoneCommand(isDone);
    }

}
```
###### /ListByNotDoneCommand.java
``` java
// merged with list command
public class ListByNotDoneCommand extends Command {

    public static final String COMMAND_WORD_1 = "listnotdone";
    public static final String COMMAND_WORD_2 = "listundone";
    public static final String COMMAND_WORD_3 = "lnd";

    public static final String MESSAGE_LISTBYNOTDONE_SUCCESS = "Listed all undone tasks";
    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Lists all undone tasks in KIT.\n"
            + "Example: " + COMMAND_WORD_1;

    private final boolean value;

    public ListByNotDoneCommand(boolean value) {
        this.value = value;
    }

    @Override
    public CommandResult execute() {
        model.sortTaskList();
        model.updateFilteredTaskList(this.value);
        return new CommandResult(getMessageForUnDoneTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
```
###### /ListByNotDoneCommandParser.java
``` java
// merged with list command
public class ListByNotDoneCommandParser extends CommandParser {

    private boolean isDone = false;

    @Override
    public Command parse(String args) {
        return new ListByNotDoneCommand(isDone);
    }

}
```
