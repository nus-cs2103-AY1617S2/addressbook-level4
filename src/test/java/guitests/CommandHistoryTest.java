package guitests;

import org.junit.Test;

import guitests.guihandles.GuiHandleSetting;
import javafx.scene.input.KeyCode;
import seedu.doist.logic.commands.AddCommand;
import seedu.doist.testutil.TestTask;

public class CommandHistoryTest extends DoistGUITest {

    private GuiRobot guiRobot = new GuiRobot();

    @Test
    public void testUp () {

        //Add task
        TestTask taskToAdd = td.email;
        commandBox.runCommand(taskToAdd.getAddCommand());

        //Press UP arrow, then press ENTER key
        guiRobot.push(KeyCode.UP, KeyCode.ENTER);
        guiRobot.sleep(GuiHandleSetting.SLEEP_LENGTH);

        //Check if the add command was executed twice
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);

    }

    @Test
    public void testDown () {

        //Add two task
        TestTask taskToAdd = td.email;
        commandBox.runCommand(taskToAdd.getAddCommand());
        taskToAdd = td.chores;
        commandBox.runCommand(taskToAdd.getAddCommand());
        guiRobot.sleep(GuiHandleSetting.COMMAND_RUN_TIME);

        //Press UP arrow twice followed by DOWN arrow, then press ENTER key
        //This combination tries to add a duplicate task
        guiRobot.push(KeyCode.UP);
        guiRobot.sleep(GuiHandleSetting.SLEEP_LENGTH);
        guiRobot.push(KeyCode.UP);
        guiRobot.sleep(GuiHandleSetting.SLEEP_LENGTH);
        guiRobot.push(KeyCode.DOWN);
        guiRobot.sleep(GuiHandleSetting.SLEEP_LENGTH);
        guiRobot.push(KeyCode.ENTER);
        guiRobot.sleep(GuiHandleSetting.SLEEP_LENGTH);

        //Assert that clear was executed successfully
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
    }
}
