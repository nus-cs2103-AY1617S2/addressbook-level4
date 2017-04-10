package seedu.ezdo.logic.commands;

import seedu.ezdo.model.EzDo;

/**
 * Clears ezDo.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String SHORT_COMMAND_WORD = "c";

    public static final String MESSAGE_SUCCESS = "EzDo has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new EzDo());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
