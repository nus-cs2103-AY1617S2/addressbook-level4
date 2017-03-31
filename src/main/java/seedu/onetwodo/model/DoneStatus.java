package seedu.onetwodo.model;

//@@author A0143029M
public enum DoneStatus {
    DONE("done"),
    UNDONE("undone"),
    ALL("all");

    private final String status;
    public static final String DONE_STRING = "done";
    public static final String UNDONE_STRING = "undone";
    public static final String ALL_STRING = "all";

    DoneStatus (String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return status;
    }
}
