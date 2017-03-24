package seedu.task.logic.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.NattyDateUtil;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.model.UndoManager;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.EndTime;
import seedu.task.model.task.Name;
import seedu.task.model.task.StartTime;
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

        // Check that the AddCommand is parsed properly
        assertTrue(result instanceof AddCommand);

        AddCommand added = (AddCommand) result;
        Task toAdd = added.getToAdd();

        // Check the description
        Name name = toAdd.getName();
        assertTrue(name.fullName.equals("Test Task"));
    }

    @Test
    public void parser_add_basicTask() {
        String commandString = "add Test Task from 03/24/17 to 03/25/17";

        Command result = this.parser.parseCommand(commandString);

        // Check that the AddCommand is parsed properly
        assertTrue(result instanceof AddCommand);

        AddCommand added = (AddCommand) result;
        Task toAdd = added.getToAdd();

        // Check the description
        Name name = toAdd.getName();
        assertTrue(name.fullName.equals("Test Task"));

        // Check the dates
        StartTime compareStartTime = null;
        EndTime compareEndTime = null;
        try {
            compareStartTime = new StartTime(NattyDateUtil.parseSingleDate("03/24/17"));
            compareEndTime = new EndTime(NattyDateUtil.parseSingleDate("03/25/17"));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        assertTrue(toAdd.getStartTime().equals(compareStartTime));
        assertTrue(toAdd.getEndTime().equals(compareEndTime));
    }

    @Test
    public void parser_add_basicTaskDueDate() {
        String commandString = "add Test Task by 03/26/17";

        Command result = this.parser.parseCommand(commandString);

        // Check that the AddCommand is parsed properly
        assertTrue(result instanceof AddCommand);

        AddCommand added = (AddCommand) result;
        Task toAdd = added.getToAdd();

        // Check the description
        Name name = toAdd.getName();
        assertTrue(name.fullName.equals("Test Task"));

        // Check the dates
        EndTime compareEndTime = null;
        try {
            compareEndTime = new EndTime(NattyDateUtil.parseSingleDate("03/26/17"));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        assertTrue(toAdd.getEndTime().equals(compareEndTime));
    }

    @Test
    public void parser_add_basicTaskFlexibleDate() {
        String commandString = "add Test Task from the first of january 2017 to the second of april 2018";

        Command result = this.parser.parseCommand(commandString);

        // Check that the AddCommand is parsed properly
        assertTrue(result instanceof AddCommand);
        AddCommand added = (AddCommand) result;
        Task toAdd = added.getToAdd();

        // Check the description
        Name name = toAdd.getName();
        assertTrue(name.fullName.equals("Test Task"));

        // Check the dates
        StartTime compareStartTime = null;
        EndTime compareEndTime = null;
        try {
            compareStartTime = new StartTime(NattyDateUtil.parseSingleDate("first of january 2017"));
            compareEndTime = new EndTime(NattyDateUtil.parseSingleDate("second of april 2018"));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        assertTrue(toAdd.getStartTime().equals(compareStartTime));
        assertTrue(toAdd.getEndTime().equals(compareEndTime));
    }

    @Test
    public void parser_add_floatingTaskWithTags() {
        String commandString = "add Test Task #one #two";

        Command result = this.parser.parseCommand(commandString);

        // Check that the AddCommand is parsed properly
        assertTrue(result instanceof AddCommand);
        AddCommand added = (AddCommand) result;
        Task toAdd = added.getToAdd();
        Name name = toAdd.getName();

        //Check the description
        assertTrue(name.fullName.equals("Test Task"));

        // Check the tags
        UniqueTagList tagList = toAdd.getTags();
        assertTrue(tagList.toSet().size() == 2);
        try {
            assertTrue(tagList.contains(new Tag("one")));
            assertTrue(tagList.contains(new Tag("two")));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parser_add_basicTaskWithTags() {
        String commandString = "add Test Task from 03/24/17 to 03/25/17 #one #two #three";

        Command result = this.parser.parseCommand(commandString);

        // Check that the AddCommand is parsed properly
        assertTrue(result instanceof AddCommand);
        AddCommand added = (AddCommand) result;
        Task toAdd = added.getToAdd();

        // Check the description
        Name name = toAdd.getName();
        assertTrue(name.fullName.equals("Test Task"));

        // Check the dates
        StartTime compareStartTime = null;
        EndTime compareEndTime = null;
        try {
            compareStartTime = new StartTime(NattyDateUtil.parseSingleDate("03/24/17"));
            compareEndTime = new EndTime(NattyDateUtil.parseSingleDate("03/25/17"));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        assertTrue(toAdd.getStartTime().equals(compareStartTime));
        assertTrue(toAdd.getEndTime().equals(compareEndTime));

        // Check the tags
        UniqueTagList tagList = toAdd.getTags();
        assertTrue(tagList.toSet().size() == 3);
        try {
            assertTrue(tagList.contains(new Tag("one")));
            assertTrue(tagList.contains(new Tag("two")));
            assertTrue(tagList.contains(new Tag("three")));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }
}
