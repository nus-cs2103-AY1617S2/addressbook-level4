package guitests;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import seedu.opus.commons.core.Config;
import seedu.opus.commons.exceptions.FileDeletionException;
import seedu.opus.commons.util.FileUtil;
import seedu.opus.logic.commands.SaveCommand;

//@@author A0148081H
public class SaveCommandTest extends TaskManagerGuiTest {

    private final String validLocation = "data/test.xml";
    private final String badLocation = "opus/.xml";

    @Test
    public void saveValidLocationMessage() {
        //save to a valid directory
        commandBox.runCommand("save " + validLocation);
        assertResultMessage(String.format(SaveCommand.MESSAGE_SUCCESS, validLocation));
    }

    @Test
    public void saveDefaultLocationMessage() {
        //save to default directory
        commandBox.runCommand("save default");
        assertResultMessage(String.format(SaveCommand.MESSAGE_LOCATION_DEFAULT, Config.DEFAULT_SAVE_LOCATION));
    }

    @Test
    public void saveInvalidLocationMessage() {
        //invalid Location
        commandBox.runCommand("save " + badLocation);
        assertResultMessage(SaveCommand.MESSAGE_PATH_WRONG_FORMAT);
    }

    @Test
    public void saveExistingFileMessage() throws IOException, FileDeletionException {
        //file exists
        FileUtil.createIfMissing(new File(validLocation));
        commandBox.runCommand("save " + validLocation);
        assertResultMessage(SaveCommand.MESSAGE_FILE_EXISTS);
        FileUtil.deleteFile(validLocation);
    }
}
