package seedu.task.model.task;

public class Chat {
    private String message;
    //    private String sender;
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**
     * @return the sender
     */
    //    public String getSender() {
    //        return sender;
    //    }
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * @param sender the sender to set
     */
    //    public void setSender(String sender) {
    //        this.sender = sender;
    //    }
    /**
     * @param message
     */
    public Chat(String message) {
        super();
        this.message = message;
    }
}
