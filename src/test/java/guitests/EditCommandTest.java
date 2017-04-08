//package guitests;
//import static org.junit.Assert.assertTrue;
////import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//
//import org.junit.Test;
//
////import guitests.guihandles.TaskCardHandle;
//
//import guitests.guihandles.TaskCardHandle;
////import seedu.task.commons.core.Messages;
//import seedu.task.logic.commands.EditCommand;
////import seedu.task.model.task.Description;
////import seedu.task.model.tag.Tag;
//import seedu.task.testutil.TaskBuilder;
//import seedu.task.testutil.TestTask;
//
//// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
//public class EditCommandTest extends AddressBookGuiTest {
//
//     // The list of persons in the person list panel is expected to match this list.
//     // This list is updated with every successful call to assertEditSuccess().
//    TestTask[] expectedTasksList = td.getTypicalTasks();
//
//    @Test
//     public void edit_allFieldsSpecified_success() throws Exception {
//        String detailsToEdit = "Bobby p/91234567 e/bobby@gmail.com a/Block 123, Bobby Street 3 t/husband";
//        int addressBookIndex = 1;
//
//        TestTask editedPerson = new
//                 TaskBuilder().withDescription("Bobby").withFrequency("91234567")
//                 .withEmail("bobby@gmail.com").withTags("husband").build();
//
//        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit,
//                 editedPerson);
//    }
//
////     @Test
////     public void edit_notAllFieldsSpecified_success() throws Exception {
////         String detailsToEdit = "t/sweetie t/bestie";
////         int addressBookIndex = 2;
////
////         TestTask personToEdit = expectedPersonsList[addressBookIndex - 1];
////         TestTask editedPerson = new
////                 TaskBuilder(personToEdit).withTags("sweetie", "bestie").build();
////
////         assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit,
////                 editedPerson);
////     }
////
////     @Test
////     public void edit_clearTags_success() throws Exception {
////         String detailsToEdit = "t/";
////         int addressBookIndex = 2;
////
////         TestTask personToEdit = expectedPersonsList[addressBookIndex - 1];
////         TestTask editedPerson = new
////                 TaskBuilder(personToEdit).withTags().build();
////
////         assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit,
////                 editedPerson);
////     }
////
////     @Test
////     public void edit_findThenEdit_success() throws Exception {
////     commandBox.runCommand("find Elle");
////
////     String detailsToEdit = "Belle";
////     int filteredPersonListIndex = 1;
////     int addressBookIndex = 5;
////
////     TestTask personToEdit = expectedPersonsList[addressBookIndex - 1];
////     TestTask editedPerson = new
////     TaskBuilder(personToEdit).withDescription("Belle").build();
////
////     assertEditSuccess(filteredPersonListIndex, addressBookIndex,
////     detailsToEdit, editedPerson);
////     }
////
////     @Test
////     public void edit_missingPersonIndex_failure() {
////         commandBox.runCommand("edit Bobby");
////         assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
////                 EditCommand.MESSAGE_USAGE));
////     }
////
////     @Test
////     public void edit_invalidPersonIndex_failure() {
////     commandBox.runCommand("edit 8 Bobby");
////     assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
////     }
////
////     @Test
////     public void edit_noFieldsSpecified_failure() {
////     commandBox.runCommand("edit 1");
////     assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
////     }
////
////     @Test
////     public void edit_invalidValues_failure() {
////     commandBox.runCommand("edit 1 *&");
////     assertResultMessage(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
////
////     commandBox.runCommand("edit 1 p/abcd");
////     assertResultMessage(Priority.MESSAGE_PRIORITY_CONSTRAINTS);
////
////     commandBox.runCommand("edit 1 e/yahoo!!!");
////     assertResultMessage(Timing.MESSAGE_TIMING_CONSTRAINTS);
////
////     commandBox.runCommand("edit 1 t/*&");
////     assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
////     }
////
////     @Test
////     public void edit_duplicatePerson_failure() {
////     commandBox.runCommand("edit 3 Alice Pauline p/85355255 e/alice@gmail.com
////     "
////     + "a/123, Jurong West Ave 6, #08-111 t/friends");
////     assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
////     }
//
//    /**
//    * Checks whether the edited person has the correct updated details.
//    *
//    * @param filteredTaskListIndex index of person to edit in filtered list
//    * @param taskListIndex index of person to edit in the address book.
//    * Must refer to the same person as {@code filteredPersonListIndex}
//    * @param detailsToEdit details to edit the person with as input to the
//    edit command
//    * @param editedPerson the expected person after editing the person's
//    details
//    */
//    private void assertEditSuccess(int filteredTaskListIndex, int
//             taskListIndex, String detailsToEdit, TestTask editedTask) {
//        commandBox.runCommand("edit " + filteredTaskListIndex + " " +
//                 detailsToEdit);
//
//        // confirm the new card contains the right data
//        TaskCardHandle editedCard =
//                 taskListPanel.navigateToTask(editedTask.getDescription().description);
//        assertMatching(editedTask, editedCard);
//
//        // confirm the list now contains all previous persons plus the person
//        //with updated details
//        expectedPersonsList[taskListIndex - 1] = editedTask;
//        assertTrue(taskListPanel.isListMatching(expectedPersonsList));
//        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS,
//                 editedTask));
//    }
//}
