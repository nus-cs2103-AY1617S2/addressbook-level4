package seedu.address.logic.commands;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;

public class BookCommand extends Command {

    public static final String COMMAND_WORD = "Book";

    private final Task toAdd;

    public BookCommand(String title, String...date) throws IllegalValueException {
        /*
        final Set<Label> labelSet = new HashSet<>();
        for (String labelName : labels) {
            labelSet.add(new Label(labelName));
        }
*/
        this.toAdd = new Task(
                new Title(title),
                Optional.empty(),
                Optional.empty(),
                false,
                new UniqueLabelList()
        );
    }

    @Override
    public CommandResult execute() throws CommandException {
        return null;
    }

    @Override
    public boolean isMutating() {
        return true;
    }

}
