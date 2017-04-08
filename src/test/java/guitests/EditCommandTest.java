//package guitests;
//
//import static org.junit.Assert.assertTrue;
//import static project.taskcrusher.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//
//import org.junit.Test;
//
//import guitests.guihandles.TaskCardHandle;
//import project.taskcrusher.commons.core.Messages;
//import project.taskcrusher.logic.commands.EditCommand;
//import project.taskcrusher.model.shared.Description;
//import project.taskcrusher.model.shared.Name;
//import project.taskcrusher.model.shared.Priority;
//import project.taskcrusher.model.tag.Tag;
//import project.taskcrusher.model.task.Deadline;
//import project.taskcrusher.testutil.TaskBuilder;
//import project.taskcrusher.testutil.TestTaskCard;
//
//public class EditCommandTest extends TaskcrusherGuiTest {
//
//    // The list of persons in the person list panel is expected to match this list.
//    // This list is updated with every successful call to assertEditSuccess().
//    TestTaskCard[] expectedPersonsList = td.getTypicalTasks();
//
//    @Test
//    public void edit_allFieldsSpecified_success() throws Exception {
//        String detailsToEdit = "Bobby p/91234567 e/bobby@gmail.com a/Block 123, BobbyStreet 3 t/husband";
//        int addressBookIndex = 1;
//
//        TestTaskCard editedPerson = new TaskBuilder().withName("Bobby").withPriority("91234567")
//                .withDeadline("bobby@gmail.com").withDescription("Block 123, Bobby Street 3")
//                .withTags("husband").build();
//
//        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
//    }
//
//    @Test
//    public void edit_notAllFieldsSpecified_success() throws Exception {
//        String detailsToEdit = "t/sweetie t/bestie";
//        int addressBookIndex = 2;
//
//        TestTaskCard personToEdit = expectedPersonsList[addressBookIndex - 1];
//        TestTaskCard editedPerson = new TaskBuilder(personToEdit).withTags("sweetie", "bestie").build();
//
//        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
//    }
//
//    @Test
//    public void edit_clearTags_success() throws Exception {
//        String detailsToEdit = "t/";
//        int addressBookIndex = 2;
//
//        TestTaskCard personToEdit = expectedPersonsList[addressBookIndex - 1];
//        TestTaskCard editedPerson = new TaskBuilder(personToEdit).withTags().build();
//
//        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
//    }
//
//    @Test
//    public void edit_findThenEdit_success() throws Exception {
//        commandBox.runCommand("find Elle");
//
//        String detailsToEdit = "Belle";
//        int filteredPersonListIndex = 1;
//        int addressBookIndex = 5;
//
//        TestTaskCard personToEdit = expectedPersonsList[addressBookIndex - 1];
//        TestTaskCard editedPerson = new TaskBuilder(personToEdit).withName("Belle").build();
//
//        assertEditSuccess(filteredPersonListIndex, addressBookIndex, detailsToEdit, editedPerson);
//    }
//
//    @Test
//    public void edit_missingPersonIndex_failure() {
//        commandBox.runCommand("edit Bobby");
//        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
//    }
//
//    @Test
//    public void edit_invalidPersonIndex_failure() {
//        commandBox.runCommand("edit 8 Bobby");
//        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
//    }
//
//    @Test
//    public void edit_noFieldsSpecified_failure() {
//        commandBox.runCommand("edit 1");
//        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
//    }
//
//    @Test
//    public void edit_invalidValues_failure() {
//        commandBox.runCommand("edit 1 *&");
//        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);
//
//        commandBox.runCommand("edit 1 p/abcd");
//        assertResultMessage(Priority.MESSAGE_PRIORITY_CONSTRAINTS);
//
//        commandBox.runCommand("edit 1 d/randomstring");
//        assertResultMessage(Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
//
//        commandBox.runCommand("edit 1 a/");
//        assertResultMessage(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
//
//        commandBox.runCommand("edit 1 t/*&");
//        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
//    }
//
//    @Test
//    public void edit_duplicatePerson_failure() {
//        commandBox.runCommand(
//                "edit 3 Alice Pauline p/85355255 e/alice@gmail.com " + "a/123, Jurong West Ave 6, #08-111 t/friends");
//        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
//    }
//
//     /**
//     * Checks whether the edited person has the correct updated details.
//     *
//     * @param filteredPersonListIndex index of person to edit in filtered list
//     * @param addressBookIndex index of person to edit in the address book.
//     * Must refer to the same person as {@code filteredPersonListIndex}
//     * @param detailsToEdit details to edit the person with as input to the edit
//     command
//     * @param editedPerson the expected person after editing the person's details
//     */
//    private void assertEditSuccess(int filteredPersonListIndex, int addressBookIndex,
//            String detailsToEdit, TestTaskCard editedPerson) {
//        commandBox.runCommand("edit " + filteredPersonListIndex + " " + detailsToEdit);
//
//        // confirm the new card contains the right data
//        TaskCardHandle editedCard = userInboxPanel.navigateToTask(editedPerson.getName().name);
//        assertMatching(editedPerson, editedCard);
//
//        // confirm the list now contains all previous persons plus the person with updated details
//        expectedPersonsList[addressBookIndex - 1] = editedPerson;
//        assertTrue(userInboxPanel.isListMatching(expectedPersonsList));
//        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedPerson));
//    }
//}
