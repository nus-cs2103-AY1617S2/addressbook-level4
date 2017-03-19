package seedu.address.ui;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.Scene;
import seedu.address.MainApp;
import seedu.address.commons.util.FxViewUtil;
import java.util.Date;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
/**
 * The Calender Panel of the App.
 */
//tutorial
//https://www.youtube.com/watch?v=HiZ-glk9_LE&t=568s
public class CalenderPanel extends UiPart<Region> {
    private static final String FXML = "CalenderPanel.fxml";
    private static final DateFormat MONTH = new SimpleDateFormat("MM");
    private static final DateFormat DATE = new SimpleDateFormat("dd");
    private static final DateFormat DAY = new SimpleDateFormat("EEEE");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private HashMap<String, Label> dayHashMap;
    //tutorial https://www.mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/
    @FXML
    private ListView<String> listview1;

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
    
    public CalenderPanel(AnchorPane calendertPlaceholder) {
        super(FXML);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        ObservableList<String> data=FXCollections.observableArrayList("hey","you");
        label1.setText("v0.1");
        setDate();
        listview1.getItems().addAll("eat pizza","go to gym");
        calendertPlaceholder.getChildren().add(getRoot());
        
       
    }
    private void initDayHashMap(HashMap<String, Label> _dayHashMap){
    	_dayHashMap.put("day1", day1);
    	_dayHashMap.put("day2", day2);   
    	_dayHashMap.put("day3", day3);
    	_dayHashMap.put("day4", day4);
    	_dayHashMap.put("day5", day5);
    	_dayHashMap.put("day6", day6);
    	_dayHashMap.put("day7", day7);
    	_dayHashMap.put("day8", day8);
		_dayHashMap.put("day9", day9);
		_dayHashMap.put("day10", day10);
		_dayHashMap.put("day11", day11);
		_dayHashMap.put("day12", day12);
		_dayHashMap.put("day13", day13);
		_dayHashMap.put("day14", day14);
		_dayHashMap.put("day15", day15);
		_dayHashMap.put("day16", day16);
		_dayHashMap.put("day17", day17);
		_dayHashMap.put("day18", day18);
		_dayHashMap.put("day19", day19);
		_dayHashMap.put("day20", day20);
		_dayHashMap.put("day21", day21);
		_dayHashMap.put("day22", day22);
		_dayHashMap.put("day23", day23);
		_dayHashMap.put("day24", day24);
		_dayHashMap.put("day25", day25);
		_dayHashMap.put("day26", day26);
		_dayHashMap.put("day27", day27);
		_dayHashMap.put("day28", day28);    
    }
    private void setDate(){
    	HashMap<String, Label> dayHashMap = new HashMap<String, Label>();
		initDayHashMap(dayHashMap);	
		
		Date date = new Date();
		String dayOfTheWeek = DAY.format(date);		
		
		Calendar firstDay = Calendar.getInstance();
		Date firstDate;
			
    	
    	if(dayOfTheWeek.equals("星期一") || dayOfTheWeek.equals("Monday")){
    		firstDay.add(Calendar.DATE, -1);
    	}else if(dayOfTheWeek.equals("星期二") || dayOfTheWeek.equals("Tuesday")){
    		firstDay.add(Calendar.DATE, -2);
    	}else if(dayOfTheWeek.equals("星期三") || dayOfTheWeek.equals("Wednesday")){
    		firstDay.add(Calendar.DATE, -3);
    	}else if(dayOfTheWeek.equals("星期四") || dayOfTheWeek.equals("Thursday")){
    		firstDay.add(Calendar.DATE, -4);
    	}else if(dayOfTheWeek.equals("星期五") || dayOfTheWeek.equals("Friday")){
    		firstDay.add(Calendar.DATE, -5);
    	}else if(dayOfTheWeek.equals("星期六") || dayOfTheWeek.equals("Saturday")){
    		firstDay.add(Calendar.DATE, -6);
    	}
    	
    	firstDate = firstDay.getTime();

    	int firstDateMonth=Integer.valueOf(MONTH.format(firstDate));
    	int firstDateDate=Integer.valueOf(DATE.format(firstDate));
    	
    	
    	for(int count=1;count<=28;count++){
    		dayHashMap.get("day"+count).setText(Integer.toString(firstDateMonth)+"/"+Integer.toString(firstDateDate));
    		firstDateDate++;
            if(firstDateMonth==2 && firstDateDate>28){
            	firstDateMonth+=1;
            	firstDateDate=1;   	
            }else if((firstDateMonth==7 || firstDateMonth==8) && firstDateDate>31){
            	firstDateMonth+=1;
            	firstDateDate=1;
            }else if(firstDateMonth%2==1 && firstDateDate>31){
            	firstDateMonth+=1;
            	firstDateDate=1;            	
            	
            }else if(firstDateMonth%2==0 && firstDateDate>31){
            	firstDateMonth+=1;
            	firstDateDate=1;             	
            }
            
    	}
    }
}
