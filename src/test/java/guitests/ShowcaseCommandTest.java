package guitests;

import static org.junit.Assert.*;

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
            int number = rand.nextInt(100);
            commandBox.runCommand("showcase " + number);
            System.out.println(number + " " + taskListPanel.getNumberOfPeople());
            // assertTrue(number == taskListPanel.getNumberOfPeople());
        }
    }

}
