package seedu.onetwodo.logic.parser;

public enum DoneStatus {
    DEFAULT("default"),
    DONE("done"),
    UNDONE("undone"),
    ALL("all");
    
    private final String status;
    
    DoneStatus (String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    
    public String toString() {
        return status;
    }
}
