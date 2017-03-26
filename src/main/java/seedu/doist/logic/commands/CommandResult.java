package seedu.doist.logic.commands;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public final String feedbackToUser;
    public boolean isMutating;

    public CommandResult(String feedbackToUser) {
        assert feedbackToUser != null;
        this.feedbackToUser = feedbackToUser;
        isMutating = false;
    }

    public CommandResult(String feedbackToUser, boolean isMutating) {
        this(feedbackToUser);
        this.isMutating = isMutating;
    }
}
