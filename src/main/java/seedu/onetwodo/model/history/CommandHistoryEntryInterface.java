package seedu.onetwodo.model.history;

//@@author A0135739W
/**
 * The API of the CommandHistoryEntry.
 */
public interface CommandHistoryEntryInterface {

    /**
     * returns the feedback message of a command
     * @return the feedback message of a command
     */
    public String getFeedbackMessage();

    /**
     * returns the feedback message of the reverse command
     * @return the feedback message of the reverse command
     */
    public String getFeedbackMessageInReverseCommand();
}
