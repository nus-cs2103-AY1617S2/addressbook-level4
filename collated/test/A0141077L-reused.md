# A0141077L-reused
###### \java\seedu\watodo\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_deleteIndexMissing_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE);
        assertCommandFailure("delete", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotUnsignedPositiveInteger_errorMessageShown() throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX + '\n' +
                DeleteCommand.MESSAGE_USAGE;
        assertCommandFailure("delete +1", expectedMessage); // index should be unsigned
        assertCommandFailure("delete -1", expectedMessage);
        assertCommandFailure("delete hello", expectedMessage);
    }

    @Test
    public void execute_deleteIndexOutOfBounds_errorMessageShown() throws Exception {
        final StringBuilder expectedMessage = new StringBuilder();
        expectedMessage.append(String.format(DeleteCommand.MESSAGE_DELETE_TASK_UNSUCCESSFUL, 4) + '\n'
                + DeleteCommand.MESSAGE_INDEX_OUT_OF_BOUNDS + '\n');
        assertCommandFailure("delete 4", expectedMessage.toString());
    }

    @Test
    public void execute_deleteDuplicateIndices_errorMessageShown() {
        String expectedMessage = DeleteCommand.MESSAGE_DUPLICATE_INDICES;
        assertCommandFailure("delete 1 2 1", expectedMessage);
    }

    @Test
    public void execute_deleteMultipleIndicesInDescendingOrder_success() throws IllegalValueException {
        String messageForNonDescendingIndices = "";
        String messageForDescendingIndices = "";

        try {
            logic.execute("delete 1 4 2");
        } catch (CommandException e1) {
            //expect all indices to be unsuccessful, and error message to be printed for each index in descending order
            messageForNonDescendingIndices = new String (e1.getMessage());
        }

        try {
            logic.execute("delete 4 2 1");
        } catch (CommandException e2) {
            //expect all indices to be unsuccessful, and error message to be printed for each index
            messageForDescendingIndices = new String (e2.getMessage());
        }

        assertEquals(messageForDescendingIndices, messageForNonDescendingIndices);
    }

    @Test
    public void execute_deleteValidIndicesAmongstInvalidOnes_success() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithDescription("Task1");
        List<Task> twoTasks = helper.generateTaskList(p1);
        helper.addToModel(model, twoTasks);

        final StringBuilder expectedMessage = new StringBuilder();
        expectedMessage.append(DeleteCommand.MESSAGE_INCOMPLETE_EXECUTION + '\n');
        expectedMessage.append(String.format(DeleteCommand.MESSAGE_DELETE_TASK_UNSUCCESSFUL, 2) + '\n'
                + DeleteCommand.MESSAGE_INDEX_OUT_OF_BOUNDS + '\n');
        expectedMessage.append(String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESSFUL, 1, p1) + '\n');

        String actualMessage = "";
        try {
            //indices 2 is invalid (OOB) and will be executed before index 1.
            logic.execute("delete 1 2");
        } catch (CommandException e1) {
            actualMessage = new String (e1.getMessage());
        }

        assertEquals(expectedMessage.toString(), actualMessage);
    }

```
###### \java\seedu\watodo\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_unmarkIndexMissing_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnmarkCommand.MESSAGE_USAGE);
        assertCommandFailure("unmark", expectedMessage);
    }

    @Test
    public void execute_unmarkIndexNotUnsignedPositiveInteger_errorMessageShown() throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX + '\n' +
                UnmarkCommand.MESSAGE_USAGE;
        assertCommandFailure("unmark +1", expectedMessage); // index should be unsigned
        assertCommandFailure("unmark -1", expectedMessage);
        assertCommandFailure("unmark hello", expectedMessage);
    }

    @Test
    public void execute_unmarkIndexOutOfBounds_errorMessageShown() throws Exception {
        final StringBuilder expectedMessage = new StringBuilder();
        expectedMessage.append(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_UNSUCCESSFUL, 4) + '\n'
                + UnmarkCommand.MESSAGE_INDEX_OUT_OF_BOUNDS + '\n');
        assertCommandFailure("unmark 4", expectedMessage.toString());
    }

    @Test
    public void execute_unmarkDuplicateIndices_errorMessageShown() {
        String expectedMessage = UnmarkCommand.MESSAGE_DUPLICATE_INDICES;
        assertCommandFailure("unmark 1 2 1", expectedMessage);
    }

    @Test
    public void execute_unmarkMultipleIndicesInDescendingOrder_success() throws IllegalValueException {
        String messageForNonDescendingIndices = "";
        String messageForDescendingIndices = "";

        try {
            logic.execute("unmark 1 4 2");
        } catch (CommandException e1) {
            //expect all indices to be unsuccessful, and error message to be printed for each index in descending order
            messageForNonDescendingIndices = new String (e1.getMessage());
        }

        try {
            logic.execute("unmark 4 2 1");
        } catch (CommandException e2) {
            //expect all indices to be unsuccessful, and error message to be printed for each index
            messageForDescendingIndices = new String (e2.getMessage());
        }

        assertEquals(messageForDescendingIndices, messageForNonDescendingIndices);
    }

    @Test
    public void execute_unmarkValidIndicesAmongstInvalidOnes_success() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithDescription("doneTask1");
        Task p2 = helper.generateTaskWithDescription("undoneTask2");
        List<Task> twoTasks = helper.generateTaskList(p1, p2);
        helper.addToModel(model, twoTasks);
        p1.setStatus(TaskStatus.DONE);

        final StringBuilder expectedMessage = new StringBuilder();
        expectedMessage.append(UnmarkCommand.MESSAGE_INCOMPLETE_EXECUTION + '\n');
        expectedMessage.append(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_UNSUCCESSFUL, 3) + '\n'
                + UnmarkCommand.MESSAGE_INDEX_OUT_OF_BOUNDS + '\n');
        expectedMessage.append(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_UNSUCCESSFUL, 2) + '\n'
                + UnmarkCommand.MESSAGE_STATUS_ALREADY_UNDONE + '\n');
        expectedMessage.append(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESSFUL, 1, p1) + '\n');

        String actualMessage = "";
        try {
            //indices 2 and 3 are invalid (one already unmarked, one OOB) and will be executed before index 1.
            logic.execute("unmark 1 3 2");
        } catch (CommandException e1) {
            actualMessage = new String (e1.getMessage());
        }

        assertEquals(expectedMessage.toString(), actualMessage);
    }

```
