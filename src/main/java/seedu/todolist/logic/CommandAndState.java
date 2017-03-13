package seedu.todolist.logic;

import seedu.todolist.logic.commands.Command;
import seedu.todolist.model.ReadOnlyToDoList;

public class CommandAndState {
	Command command;
	ReadOnlyToDoList state;

	public CommandAndState(Command command, ReadOnlyToDoList state) {
	    this.command = command;
	    this.state = state;

	}

    public Command getCommand() {
        return command;
    }

    public ReadOnlyToDoList getState() {
        return state;
    }
}
