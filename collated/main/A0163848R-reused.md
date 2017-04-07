# A0163848R-reused
###### /java/seedu/address/logic/commands/ThemeCommand.java
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
###### /java/seedu/address/logic/commands/UndoCommand.java
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
###### /java/seedu/address/logic/commands/UnmarkCommand.java
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

    public static final String MESSAGE_MARK_PERSON_SUCCESS = "Unmarked task: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task is already incomplete.";

    private final int filteredPersonListIndex;

    /**
     * @param filteredPersonListIndex the index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public UnmarkCommand(int filteredPersonListIndex) {
        assert filteredPersonListIndex > 0;

        // converts filteredPersonListIndex from one-based to zero-based.
        this.filteredPersonListIndex = filteredPersonListIndex - 1;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (filteredPersonListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(filteredPersonListIndex);
        Task editedPerson = createUnmarkedPerson(personToEdit);

        try {
            model.updatePerson(filteredPersonListIndex, editedPerson);
        } catch (UniquePersonList.DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_MARK_PERSON_SUCCESS, personToEdit));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited to be complete.
     */
    private static Task createUnmarkedPerson(ReadOnlyPerson personToEdit) {
        assert personToEdit != null;

        UniqueTagList updatedTags =
                personToEdit
                .getTags()
                .except(UniqueTagList.build(
                                Tag.TAG_COMPLETE,
                                Tag.TAG_INCOMPLETE));

        try {
            updatedTags.add(new Tag(Tag.TAG_INCOMPLETE));
        } catch (DuplicateTagException e) {
            e.printStackTrace();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        return new Task(personToEdit.getName(),
                personToEdit.getStartDate(), personToEdit.getEndDate(), personToEdit.getGroup(), updatedTags);
    }
}
```
###### /java/seedu/address/commons/events/ui/LoadRequestEvent.java
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
###### /java/seedu/address/commons/events/ui/LoadResultAvailableEvent.java
``` java
/**
 * Indicates that a new result is available.
 */
public class LoadResultAvailableEvent extends BaseEvent {

    private final Optional<ReadOnlyAddressBook> imported;
    private final File origin;

    public LoadResultAvailableEvent(Optional<ReadOnlyAddressBook> imported, File origin) {
        this.imported = imported;
        this.origin = origin;
    }

    public Optional<ReadOnlyAddressBook> getImported() {
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
###### /java/seedu/address/model/ModelManager.java
``` java
        addToHistory(new YTomorrow(addressBook));
```
