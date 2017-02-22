package seedu.address.dispatcher;

import seedu.address.controller.AddTaskController;

/**
 * Created by louis on 21/2/17.
 */
public class CommandDispatcher {

    private static CommandDispatcher instance;

    public static CommandDispatcher getInstance() {
        if (instance == null) {
            instance = new CommandDispatcher();
        }
        return instance;
    }

    private CommandDispatcher() {}

    public void dispatch(String command) {
        new AddTaskController().execute(command);
    }
}
