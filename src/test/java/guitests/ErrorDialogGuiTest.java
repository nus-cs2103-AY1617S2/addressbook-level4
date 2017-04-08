package guitests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import guitests.guihandles.AlertDialogHandle;
import seedu.task.commons.events.storage.DataSavingExceptionEvent;

//import static junit.framework.TestCase.assertTrue;
//
//import java.io.IOException;
//
//import org.junit.Test;
//
//import guitests.guihandles.AlertDialogHandle;
//import seedu.task.commons.events.storage.DataSavingExceptionEvent;
//@@author A0163935X
public class ErrorDialogGuiTest extends AddressBookGuiTest {

    @Test
    public void showErrorDialogs() throws InterruptedException {
        //Test DataSavingExceptionEvent dialog
        raise(new DataSavingExceptionEvent(new IOException("Stub")));
        AlertDialogHandle alertDialog = mainGui.getAlertDialog("File Op Error");
        assertTrue(alertDialog.isMatching("Could not save data", "Could not save data to file" + ":\n"
                + "java.io.IOException: Stub"));

    }

}
