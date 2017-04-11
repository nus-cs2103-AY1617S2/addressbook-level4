package guitests;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

//@@author A0163845X
public class ShowcaseCommandTest extends TaskManagerGuiTest {

    @Test
    public void Showcase() {
        try {
            setup();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            commandBox.runCommand("clear");
            int number = rand.nextInt(50);
            commandBox.runCommand("showcase " + number);
            System.out.println(number + " " + taskListPanel.getNumberOfTasks());
            assertTrue(number == taskListPanel.getNumberOfTasks());
            commandBox.runCommand("clear");

        }
    }

}
