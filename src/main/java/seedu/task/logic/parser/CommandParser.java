package seedu.task.logic.parser;

//@@author A0142487Y
import seedu.task.logic.commands.Command;
/**
 * An abstract CommandParser super class
 * The implementation of the parse method is up to its subclasses
 * @author Xu
 *
 */
public abstract class CommandParser {

    public abstract Command parse(String args);

}
