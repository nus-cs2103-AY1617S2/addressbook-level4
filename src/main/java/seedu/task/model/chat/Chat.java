package seedu.task.model.chat;

import seedu.task.model.Sender;

public class Chat {
    private String message;
    private Sender sender;
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**
     * @return the sender
     */
    public Sender getSender() {
        return sender;
    }
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * @param sender the sender to set
     */
    public void setSender(Sender sender) {
        this.sender = sender;
    }
    /**
     * @param message
     */
    public Chat(String message, Sender sender) {
        super();
        this.message = message;
        this.sender = sender;
    }
}
