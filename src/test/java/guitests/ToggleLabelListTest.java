package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author A0140042A
/**
 * Test class to check if label list is being toggled
 */
public class ToggleLabelListTest extends TaskManagerGuiTest {

    @Test
    public void testToggleLabelListView() {
        leftPanel.clickOnLabelHeader();
        assertTrue(leftPanel.isVisible() == false);
        leftPanel.clickOnLabelHeader();
        assertTrue(leftPanel.isVisible() == true);
    }
}
