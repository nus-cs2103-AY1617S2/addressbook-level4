# A0146738U-reused
###### \java\seedu\bulletjournal\logic\LogicManagerTest.java
``` java

        /***
         * Tests for ChangeDirectoryCommand
         */
        @Test
        public void execute_changeDirectoryIllegalDirectory_notAllowed() throws Exception {

            TodoList expectedTM = new TodoList();
            assertCommandBehavior(true, "cd random path", ChangeDirectoryCommand.MESSAGE_CONVENSION_ERROR, expectedTM,
                    expectedTM.getTaskList());
        }

        @Test
        public void execute_changeDirectoryWrongFileType_notAllowed() throws Exception {

            TodoList expectedTM = new TodoList();
            assertCommandBehavior(true, "cd " + saveFolder.getRoot().getPath() + "cdtest.txt",
                    ChangeDirectoryCommand.MESSAGE_CONVENSION_ERROR, expectedTM, expectedTM.getTaskList());
        }

        @Test
        public void execute_changeDirectory_Successful() throws Exception {

            TestDataHelper helper = new TestDataHelper();
            TodoList expectedTM = new TodoList();
            assertCommandBehavior(true, "cd " + saveFolder.getRoot().getPath() + "cdtest.xml", String
                    .format(ChangeDirectoryCommand.MESSAGE_SUCCESS, saveFolder.getRoot().getPath() + "cdtest.xml"),
                    expectedTM, expectedTM.getTaskList());

            // Ensure model writes to this file
            expectedTM.addTask(helper.adam());
            logic.execute(helper.generateAddCommand("Adam Brown", helper.adam()));
            ReadOnlyTodoList retrieved = new StorageManager(saveFolder.getRoot().getPath() + "cdtest.xml",
                    saveFolder.getRoot().getPath() + "TempPreferences.json").readAddressBook().get();
            assertEquals(expectedTM, new TodoList(retrieved));
            assertEquals(model.getTodoList(), new TodoList(retrieved));
        }

```
