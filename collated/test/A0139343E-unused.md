# A0139343E-unused
###### /java/guitests/FileTransferCommandTest.java
``` java

    //These tests only work in junit, but not in travis (suspects due to OS in travis too slow for creating files).
/*
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void save_fileAlreadyExist_exceptionThrown() throws Exception {
        resetStorages();
        commandBox.runCommand("save " + TEST_SAVE_FOLDER);
        thrown.expect(CommandException.class);
        commandBox.runCommand("save " + TEST_SAVE_FOLDER);
    }

    @Test
    public void save_invalidFileName_exceptionThrown() throws Exception {
        resetStorages();

        commandBox.runCommand("save " + TEST_INVALID_NAME_FOLDER);
        assertResultMessage(SaveToCommand.MESSAGE_SAVETO_MAKE_FILE_FAIL);
    }

    @Test
    public void import_fileNotExist_exceptionThrown() throws Exception {
        resetStorages();

        commandBox.runCommand("import " + TEST_INVALID_NAME_FOLDER);
        String result = ImportCommand.MESSAGE_IMPORT_FILE_MISSING
                + String.format(ImportCommand.MESSAGE_IMPORT_FAILURE, TEST_INVALID_NAME_FOLDER).toString();
        assertResultMessage(result);
    }

    @Test
    public void export_invalidFileName_exceptionThrown() throws Exception {
        resetStorages();

        commandBox.runCommand("export " + TEST_INVALID_NAME_FOLDER);
        assertResultMessage(ExportCommand.MESSAGE_EXPORT_MAKE_FILE_FAIL);
    }
*/
}
```
