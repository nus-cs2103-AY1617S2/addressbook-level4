package guitests;

import java.io.IOException;

import org.junit.Test;

import guitests.guihandles.AlertDialogHandle;
import seedu.jobs.commons.events.storage.DataSavingExceptionEvent;

public class ErrorDialogGuiTest extends TaskBookGuiTest {

    @Test
    public void showErrorDialogs() throws InterruptedException {
        //Test DataSavingExceptionEvent dialog
        raise(new DataSavingExceptionEvent(new IOException("Stub")));
        AlertDialogHandle alertDialog = mainGui.getAlertDialog("File Op Error");
    }

}
