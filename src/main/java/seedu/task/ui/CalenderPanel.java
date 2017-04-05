//@@author A0163935X

package seedu.task.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.task.commons.util.FxViewUtil;
import seedu.task.model.task.ReadOnlyTask;

/**
 * The Calender Panel of the App.
 */
// tutorial
// https://www.youtube.com/watch?v=HiZ-glk9_LE&t=568s
public class CalenderPanel extends UiPart<Region> {
    private static final String FXML = "CalenderPanel.fxml";
    private static final DateFormat MONTH = new SimpleDateFormat("MM");
    private static final DateFormat DATE = new SimpleDateFormat("dd");
    private static final DateFormat DAY = new SimpleDateFormat("EEEE");
    private static final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    private HashMap<String , Label> dayHashMap = new HashMap<String , Label>();
    private HashMap<String , ListView<String>> taskListHashMap = new HashMap<String , ListView<String>>();
    // private static final DateTimeFormatter dtf =
    // DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    // private HashMap<String, Label> dayHashMap;
    // tutorial
    // https://www.mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/
    @FXML
    private TextField textfield;
    @FXML
    private Button button;
    @FXML
    private ListView<String> listview1;
    @FXML
    private ListView<String> listview2;
    @FXML
    private ListView<String> listview3;
    @FXML
    private ListView<String> listview4;
    @FXML
    private ListView<String> listview5;
    @FXML
    private ListView<String> listview6;
    @FXML
    private ListView<String> listview7;
    @FXML
    private ListView<String> listview8;
    @FXML
    private ListView<String> listview9;
    @FXML
    private ListView<String> listview10;
    @FXML
    private ListView<String> listview11;
    @FXML
    private ListView<String> listview12;
    @FXML
    private ListView<String> listview13;
    @FXML
    private ListView<String> listview14;
    @FXML
    private ListView<String> listview15;
    @FXML
    private ListView<String> listview16;
    @FXML
    private ListView<String> listview17;
    @FXML
    private ListView<String> listview18;
    @FXML
    private ListView<String> listview19;
    @FXML
    private ListView<String> listview20;
    @FXML
    private ListView<String> listview21;
    @FXML
    private ListView<String> listview22;
    @FXML
    private ListView<String> listview23;
    @FXML
    private ListView<String> listview24;
    @FXML
    private ListView<String> listview25;
    @FXML
    private ListView<String> listview26;
    @FXML
    private ListView<String> listview27;
    @FXML
    private ListView<String> listview28;
    @FXML
    private Label label1;

    @FXML
    private Label day1;
    @FXML
    private Label day2;
    @FXML
    private Label day3;
    @FXML
    private Label day4;
    @FXML
    private Label day5;
    @FXML
    private Label day6;
    @FXML
    private Label day7;
    @FXML
    private Label day8;
    @FXML
    private Label day9;
    @FXML
    private Label day10;
    @FXML
    private Label day11;
    @FXML
    private Label day12;
    @FXML
    private Label day13;
    @FXML
    private Label day14;
    @FXML
    private Label day15;
    @FXML
    private Label day16;
    @FXML
    private Label day17;
    @FXML
    private Label day18;
    @FXML
    private Label day19;
    @FXML
    private Label day20;
    @FXML
    private Label day21;
    @FXML
    private Label day22;
    @FXML
    private Label day23;
    @FXML
    private Label day24;
    @FXML
    private Label day25;
    @FXML
    private Label day26;
    @FXML
    private Label day27;
    @FXML
    private Label day28;

    public CalenderPanel(AnchorPane calendertPlaceholder, ObservableList<ReadOnlyTask> taskList, int dDate, int dMonth,
            int dYear) {
        super(FXML);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        /* ObservableList<String> data = */ FXCollections.observableArrayList("hey", "you");
        // label1.setText("v0.1");
        setDate(dDate, dMonth, dYear);
        setTasks(taskList);
        // listview1.getItems().addAll("eat pizza", "go to gym");
        calendertPlaceholder.getChildren().add(getRoot());
        button.setOnAction(event -> {
            String str = textfield.getText();
            if (str != null) {
                str = str.replaceAll("[^0-9]+", " ");
                List<String> monthAndDate = Arrays.asList(str.trim().split(" "));
                int day = Integer.valueOf(monthAndDate.get(0));
                int month = Integer.valueOf(monthAndDate.get(1));
                int year = Integer.valueOf(monthAndDate.get(2));
                cleanAllListView();
                setDate(day, month, year);
                setTasks(taskList);
            }
        });
    }
    private void cleanAllListView() {
        listview1.getItems().clear();
        listview2.getItems().clear();
        listview3.getItems().clear();
        listview4.getItems().clear();
        listview5.getItems().clear();
        listview6.getItems().clear();
        listview7.getItems().clear();
        listview8.getItems().clear();
        listview9.getItems().clear();
        listview10.getItems().clear();
        listview11.getItems().clear();
        listview12.getItems().clear();
        listview13.getItems().clear();
        listview14.getItems().clear();
        listview15.getItems().clear();
        listview16.getItems().clear();
        listview17.getItems().clear();
        listview18.getItems().clear();
        listview19.getItems().clear();
        listview20.getItems().clear();
        listview21.getItems().clear();
        listview22.getItems().clear();
        listview23.getItems().clear();
        listview24.getItems().clear();
        listview25.getItems().clear();
        listview26.getItems().clear();
        listview27.getItems().clear();
        listview28.getItems().clear();


    }
    private void initTaskListHashMap(HashMap<String, ListView<String>> mytaskListHashMap) {
        mytaskListHashMap.put("day1TaskList", listview1);
        mytaskListHashMap.put("day2TaskList", listview2);
        mytaskListHashMap.put("day3TaskList", listview3);
        mytaskListHashMap.put("day4TaskList", listview4);
        mytaskListHashMap.put("day5TaskList", listview5);
        mytaskListHashMap.put("day6TaskList", listview6);
        mytaskListHashMap.put("day7TaskList", listview7);
        mytaskListHashMap.put("day8TaskList", listview8);
        mytaskListHashMap.put("day9TaskList", listview9);
        mytaskListHashMap.put("day10TaskList", listview10);
        mytaskListHashMap.put("day11TaskList", listview11);
        mytaskListHashMap.put("day12TaskList", listview12);
        mytaskListHashMap.put("day13TaskList", listview13);
        mytaskListHashMap.put("day14TaskList", listview14);
        mytaskListHashMap.put("day15TaskList", listview15);
        mytaskListHashMap.put("day16TaskList", listview16);
        mytaskListHashMap.put("day17TaskList", listview17);
        mytaskListHashMap.put("day18TaskList", listview18);
        mytaskListHashMap.put("day19TaskList", listview19);
        mytaskListHashMap.put("day20TaskList", listview20);
        mytaskListHashMap.put("day21TaskList", listview21);
        mytaskListHashMap.put("day22TaskList", listview22);
        mytaskListHashMap.put("day23TaskList", listview23);
        mytaskListHashMap.put("day24TaskList", listview24);
        mytaskListHashMap.put("day25TaskList", listview25);
        mytaskListHashMap.put("day26TaskList", listview26);
        mytaskListHashMap.put("day27TaskList", listview27);
        mytaskListHashMap.put("day28TaskList", listview28);
    }

    private void setTasks(ObservableList<ReadOnlyTask> taskList) {
        initTaskListHashMap(taskListHashMap);
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).isRecurring()) {
                for (int j = 0; j < taskList.get(i).getOccurrences().size(); j++) {
                    System.out.println("----");
                    System.out.println(taskList.get(i).getOccurrences().get(j).getStartTiming());
                    System.out.println(taskList.get(i).getOccurrences().get(j).getEndTiming());
                    System.out.println("----");
                }
            }
            if (!taskList.get(i).getEndTiming().isFloating()) {
                if (taskList.get(i).isRecurring()) {
                    for (int k = 0; k < taskList.get(i).getOccurrences().size(); k++) {
                        for (int j = 0; j < 28; j++) {
                            ListView<String> currentTaskList = taskListHashMap.get("day" + (j + 1) + "TaskList");
                            String labelDate = dayHashMap.get("day" + (j + 1)).getText().toString();
                            String taskDate = taskList.get(i).getOccurrences().get(k).getEndTiming().toString();
                            String[] taskListDateData = taskDate.toString().split("/");
                            String taskDateMonth = taskListDateData[1];
                            String taskDateDate = taskListDateData[0];
                            if (taskDateDate.charAt(0) == '0') {
                                taskDateDate = taskDateDate.replaceAll("0" , "");
                            }
                            if (taskDateMonth.charAt(0) == '0') {
                                taskDateMonth = taskDateMonth.replaceAll("0" , "");
                            }
                            System.out.println("--------");
                            System.out.println((taskDateMonth + "/" + taskDateDate));
                            System.out.println(labelDate);
                            System.out.println("--------");
                            if ((taskDateMonth + "/" + taskDateDate).equals(labelDate)) {
                                currentTaskList.getItems().addAll(taskList.get(i).getDescription().toString());

                            }
                        }
                    }
                } else {
                    for (int j = 0; j < 28; j++) {
                        ListView<String> currentTaskList = taskListHashMap.get("day" + (j + 1) + "TaskList");
                        String labelDate = dayHashMap.get("day" + (j + 1)).getText().toString();
                        String taskDate = taskList.get(i).getEndTiming().toString();
                        String[] taskListDateData = taskDate.toString().split("/");
                        String taskDateMonth = taskListDateData[1];
                        String taskDateDate = taskListDateData[0];
                        if (taskDateDate.charAt(0) == '0') {
                            taskDateDate = taskDateDate.replaceAll("0" , "");
                        }
                        if (taskDateMonth.charAt(0) == '0') {
                            taskDateMonth = taskDateMonth.replaceAll("0" , "");
                        }
                        if ((taskDateMonth + "/" + taskDateDate).equals(labelDate)) {
                            currentTaskList.getItems().addAll(taskList.get(i).getDescription().toString());

                        }
                    }
                }
            }
        }
    }



    private void initDayHashMap(HashMap<String, Label> dayHash) {
        dayHash.put("day1", day1);
        dayHash.put("day2", day2);
        dayHash.put("day3", day3);
        dayHash.put("day4", day4);
        dayHash.put("day5", day5);
        dayHash.put("day6", day6);
        dayHash.put("day7", day7);
        dayHash.put("day8", day8);
        dayHash.put("day9", day9);
        dayHash.put("day10", day10);
        dayHash.put("day11", day11);
        dayHash.put("day12", day12);
        dayHash.put("day13", day13);
        dayHash.put("day14", day14);
        dayHash.put("day15", day15);
        dayHash.put("day16", day16);
        dayHash.put("day17", day17);
        dayHash.put("day18", day18);
        dayHash.put("day19", day19);
        dayHash.put("day20", day20);
        dayHash.put("day21", day21);
        dayHash.put("day22", day22);
        dayHash.put("day23", day23);
        dayHash.put("day24", day24);
        dayHash.put("day25", day25);
        dayHash.put("day26", day26);
        dayHash.put("day27", day27);
        dayHash.put("day28", day28);
    }

    private void setDate(int dDate, int dMonth, int dYear) {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if (dDate != 0) {
            try {
                // date =
                // fmt.parse(Integer.toString(Year)+"-"+Integer.toString(Month)+"-"+Integer.toString(Date));
                date = fmt.parse(String.valueOf(dYear)
                        + "-" + String.valueOf(dMonth) + "-" + String.valueOf(dDate));
            } catch (java.text.ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        initDayHashMap(dayHashMap);
        String dayOfTheWeek = DAY.format(date);

        Calendar firstDay = Calendar.getInstance();
        if (dDate != 0) {
            firstDay.set(Calendar.YEAR, dYear);

            // We will have to increment the month field by 1

            firstDay.set(Calendar.MONTH, dMonth - 1);

            // As the month indexing starts with 0

            firstDay.set(Calendar.DAY_OF_MONTH, dDate);
        }
        Date firstDate;
        firstDate = firstDay.getTime();

        if ("星期一".equals(dayOfTheWeek) || "Monday".equals(dayOfTheWeek)) {
            firstDay.add(Calendar.DATE, -1);
        } else if ("星期二".equals(dayOfTheWeek) || "Tuesday".equals(dayOfTheWeek)) {
            firstDay.add(Calendar.DATE, -2);
        } else if ("星期三".equals(dayOfTheWeek) || "Wednesday".equals(dayOfTheWeek)) {
            firstDay.add(Calendar.DATE, -3);
        } else if ("星期四".equals(dayOfTheWeek) || "Thursday".equals(dayOfTheWeek)) {
            firstDay.add(Calendar.DATE, -4);
        } else if ("星期五".equals(dayOfTheWeek) || "Friday".equals(dayOfTheWeek)) {
            firstDay.add(Calendar.DATE, -5);
        } else if ("星期六".equals(dayOfTheWeek) || "Saturday".equals(dayOfTheWeek)) {
            firstDay.add(Calendar.DATE, -6);
        }

        firstDate = firstDay.getTime();

        int firstDateMonth = Integer.valueOf(MONTH.format(firstDate));
        int firstDateDate = Integer.valueOf(DATE.format(firstDate));

        for (int count = 1; count <= 28; count++) {
            dayHashMap.get("day" + count).setText(Integer.toString(firstDateMonth)
                    + "/" + Integer.toString(firstDateDate));
            firstDateDate++;
            if (firstDateMonth == 2 && firstDateDate > 28) {
                firstDateMonth += 1;
                firstDateDate = 1;
            } else if ((firstDateMonth == 7 || firstDateMonth == 8) && firstDateDate > 31) {
                firstDateMonth += 1;
                firstDateDate = 1;
            } else if (firstDateMonth % 2 == 1 && firstDateDate > 31) {
                firstDateMonth += 1;
                firstDateDate = 1;

            } else if (firstDateMonth % 2 == 0 && firstDateDate > 31) {
                firstDateMonth += 1;
                firstDateDate = 1;
            }

        }
    }
}
