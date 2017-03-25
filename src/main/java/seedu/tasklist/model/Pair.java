package seedu.tasklist.model;

//@author A0139747N
/**
 * Creates a Pair object which will be used for undo/redo command.
 */
public class Pair {
    private ReadOnlyTaskList list;
    private String userInput;


    public Pair(ReadOnlyTaskList list, String userInput) {
        this.list = list;
        this.userInput = userInput;
    }

    public ReadOnlyTaskList getList() {
        return list;
    }

    public String getUserInput() {
        return userInput;
    }

}
