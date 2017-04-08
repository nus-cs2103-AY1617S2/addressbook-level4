package guitests;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;
//@@author A0163935X
public class CalenderCommandTest extends AddressBookGuiTest  {
    @Test
    public void checkToday() {
        Calendar calendar = Calendar.getInstance();
        int dayOfToday = calendar.getTime().getDay() + 1;
        boolean dateOfTodayIsCorrect = false;
        for (int i = 1; i <= 7; i++) {
            String dateOfIthLabel = calenderPanel.getDateOfIthLabel(i);
            if (dateOfIthLabel.equals((String.valueOf(calendar.getTime().getMonth() + 1) +
                    "/" + String.valueOf(calendar.getTime().getDate()))) && i == dayOfToday) {
                dateOfTodayIsCorrect = true;
            }
        }
        assertTrue(dateOfTodayIsCorrect);

    }
    //    @Test
    //    public void checkTask() {
    //        commandBox.runCommand("add
    //    Say so long to Fiona Kunz p/1 sd/01/02/2017 ed/02/03/2100 r/0 t/owesMoney t/friends");
    //        System.out.println("@@"+taskListPanel.getListView().getItems().get(0));
    //
    //        String dateOfIthLabel=calenderPanel.getDateOfIthLabel(1);
    //        commandBox.runCommand("add timeOrderTest sd/02/04/2017 11:00 ed/02/04/2017 12:00");
    //        commandBox.runCommand("add asd sd/02/04/2017 11:00 ed/02/04/2017 12:00");
    //        System.out.println("@@"+taskListPanel.getListView().getItems().get(1));
    //        //
    //        System.out.println("@@"+taskListPanel.getListView().getItems().size());
    //        Calendar calendar = Calendar.getInstance();
    //        int dayOfToday=calendar.getTime().getDay()+1;
    //        System.out.println(dateOfIthLabel);
    //        boolean TaskExistCorrectly=false;
    //        for(int i=1;i<=28;i++)
    //            System.out.println("##"+calenderPanel.getListView(i).getItems());
    //        //        for(int i=0;i<tasksOfithTaskListview.size();i++){
    //        //            System.out.println("!");
    //        //            System.out.println(tasksOfithTaskListview.get(i));
    //        //        }
    //        //
    //        //        tasksOfithTaskListview=calenderPanel.getTaskOfListview(1);
    //        //        System.out.println("##"+tasksOfithTaskListview.size());
    //        //        for(int i=0;i<tasksOfithTaskListview.size();i++){
    //        //            System.out.println("!");
    //        //            System.out.println(tasksOfithTaskListview.get(i));
    //        //        }

    //}
}
