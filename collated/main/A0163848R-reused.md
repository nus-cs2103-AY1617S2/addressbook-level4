# A0163848R-reused
###### /java/seedu/task/logic/commands/ThemeCommand.java
``` java
/**
 * Format full help instructions for every command for display.
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program theme manager.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_THEME_MESSAGE = "Opened theme manager window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowThemeRequestEvent());
        return new CommandResult(SHOWING_THEME_MESSAGE);
    }
}
```
###### /java/seedu/task/logic/commands/AddCommand.java
``` java
    /**
     * Extracts group from keywords
     * @param keywords to search
     * @return group if found
     */
    private static Optional<Group> getGroup(List<String> keywords) {
        if (keywords.contains(GROUP_KEYWORD)) {
            String inner = StringUtil.extract(keywords, keywords.lastIndexOf(GROUP_KEYWORD) + 1, SPECIAL_KEYWORDS);
            if (!inner.isEmpty()) {
                try {
                    return Optional.of(new Group(inner));
                } catch (IllegalValueException e) {
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Extracts start date from keywords
     * @param keywords to search
     * @return start date if found
     */
    private static Optional<StartDate> getStartDate(List<String> keywords) {
        if (keywords.contains(STARTDATE_KEYWORD)) {
            String inner = StringUtil.extract(keywords, keywords.lastIndexOf(STARTDATE_KEYWORD) + 1, SPECIAL_KEYWORDS);
            if (!inner.isEmpty()) {
                try {
                    return Optional.of(new StartDate(inner));
                } catch (IllegalValueException e) {
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Extracts end date from keywords
     * @param keywords to search
     * @return end date if found
     */
    private static Optional<EndDate> getEndDate(List<String> keywords) {
        if (keywords.contains(ENDDATE_KEYWORD)) {
            String inner = StringUtil.extract(keywords, keywords.lastIndexOf(ENDDATE_KEYWORD) + 1, SPECIAL_KEYWORDS);
            if (!inner.isEmpty()) {
                try {
                    return Optional.of(new EndDate(inner));
                } catch (IllegalValueException e) {
                }
            }
        }
        return Optional.empty();
    }
```
###### /java/seedu/task/logic/commands/UndoCommand.java
``` java
/**
 * Command that undoes changes caused by the last command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undoes the changes made by the last command.\n";
    public static final String UNDO_SUCCESS = "Undo!";
    public static final String UNDO_FAILURE = "Nothing to undo!";

    public UndoCommand() {
    }

    @Override
    public CommandResult execute() throws CommandException {
        boolean result = model.undoLastModification();

        return new CommandResult(result ? UNDO_SUCCESS : UNDO_FAILURE);
    }

}
```
###### /java/seedu/task/logic/commands/UnmarkCommand.java
``` java
/**
 * Command that marks task as incomplete
 */
public class UnmarkCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks as incomplete the task identified "
            + "by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

```
###### /java/seedu/task/commons/events/ui/LoadRequestEvent.java
``` java
/**
* Represents a request to retrieve the file at the stored path.
*/
public class LoadRequestEvent extends BaseEvent {

    private File target;

    public LoadRequestEvent(File target) {
        this.target = target;
    }

    public File getTargetFile() {
        return target;
    }

    @Override
    public String toString() {
        return "Imported YTomorrow: " + target.toString();
    }
}
```
###### /java/seedu/task/commons/events/ui/LoadResultAvailableEvent.java
``` java
/**
 * Indicates that a new result is available.
 */
public class LoadResultAvailableEvent extends BaseEvent {

    private final Optional<ReadOnlyTaskManager> imported;
    private final File origin;

    public LoadResultAvailableEvent(Optional<ReadOnlyTaskManager> imported, File origin) {
        this.imported = imported;
        this.origin = origin;
    }

    public Optional<ReadOnlyTaskManager> getImported() {
        return imported;
    }

    public File getFile() {
        return origin;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
