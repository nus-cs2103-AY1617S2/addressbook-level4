package seedu.task.logic.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.model.UndoManager;
import seedu.task.model.task.Name;
import seedu.task.model.task.Task;

//@@author A0146789H
public class ParserTest {
    private Parser parser;
    @SuppressWarnings("unused")
    private UndoManager undomanager;

    // Set up the fixtures
    @Before
    public void setUp() {
        this.parser = new Parser();
        // Initialize the UndoManager as well to create a previous command stack
        this.undomanager = new UndoManager();
    }

    /* Invalid Tests
     *
     */

    @Test
    public void parser_emptyInput() {
        Command result = this.parser.parseCommand("");
        assertTrue(result instanceof IncorrectCommand);
    }

    /* Valid Tests
     *
     */

    // Add Command Tests

    @Test
    public void parser_add_floatingTask() {
        String commandString = "add Test Task";
        Command result = this.parser.parseCommand(commandString);
        assertTrue(result instanceof AddCommand);
        AddCommand added = (AddCommand) result;
        Task toAdd = added.getToAdd();
        Name name = toAdd.getName();
        assertTrue(name.fullName.equals("Test Task"));
    }

    @Test
    public void parser_add_basicTask() {
        String commandString = "add Test Task from 03/24/17 to 03/25/17";
        Command result = this.parser.parseCommand(commandString);
        assertTrue(result instanceof AddCommand);
        AddCommand added = (AddCommand) result;
        Task toAdd = added.getToAdd();
        Name name = toAdd.getName();
        assertTrue(name.fullName.equals("Test Task"));
        // TODO: Add the assertions to compare dates
    }

    @Test
    public void parser_add_basicTaskFlexibleDate() {
        String commandString = "add Test Task from the first of january 2017 to the second of april 2018";
        Command result = this.parser.parseCommand(commandString);
        assertTrue(result instanceof AddCommand);
        AddCommand added = (AddCommand) result;
        Task toAdd = added.getToAdd();
        Name name = toAdd.getName();
        assertTrue(name.fullName.equals("Test Task"));
        // TODO: Add the assertions to compare dates
    }
}
