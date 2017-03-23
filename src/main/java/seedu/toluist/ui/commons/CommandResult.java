package seedu.toluist.ui.commons;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    public CommandResult(String feedbackToUser) {
        assert feedbackToUser != null;
        this.feedbackToUser = feedbackToUser;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }
}
