package seedu.taskcrusher.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Observable;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import javafx.collections.ObservableList;
import seedu.taskcrusher.commons.core.LogsCenter;
import seedu.taskcrusher.commons.exceptions.DataConversionException;
import seedu.taskcrusher.commons.util.XmlUtil;
import seedu.taskcrusher.model.ReadOnlyUserInbox;
import seedu.taskcrusher.model.UserInbox;
import seedu.taskcrusher.model.shared.Description;
import seedu.taskcrusher.model.shared.Name;
import seedu.taskcrusher.model.tag.UniqueTagList;
import seedu.taskcrusher.model.task.ReadOnlyTask;
import seedu.taskcrusher.model.task.Task;
import seedu.taskcrusher.model.task.UniqueTaskList;

/**
 * Stores User inbox data in a Text file. 
 */
public class TextFileStorage {
    /**
     * Saves the given User inbox data to the specified file.
     */
    public static void saveDataToFile(File file, ReadOnlyUserInbox userInbox)
            throws FileNotFoundException {
    	//read every single task from the userinbox and store it into file
    	
    	System.out.println("starting???");
    	System.out.println(file.getName());
    	
    	Logger myLog= LogsCenter.getLogger(TextFileStorage.class);
    	myLog.fine("starting to write");
    	
    	
    	try {
        	BufferedWriter write = new BufferedWriter(new FileWriter(file));
        	
        	ObservableList<ReadOnlyTask> listToRead = userInbox.getTaskList();
        	Iterator<ReadOnlyTask> taskIterator = listToRead.iterator();
        	while (taskIterator.hasNext()) {
        		ReadOnlyTask taskToSave = taskIterator.next(); 		   		  		
        		String toWriteToFile = "";
        		toWriteToFile += taskToSave.getTaskName();
        		toWriteToFile += " ";
        		toWriteToFile += "//" + taskToSave.getDescription();
        		write.write(toWriteToFile);
        		write.newLine();
        		write.flush();
        	}
    		
    	} catch (Exception e) {
    		
    	}
    	
    	//need to use iterator to read tasks?
    	
    	//overwrite the file, I'm guessing
    	
    }

    /**
     * Returns address book in the file or an empty address book
     * @throws IOException 
     */
    public static ReadOnlyUserInbox loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            IOException {
    	
    	System.out.println("load??");
    	
    	Logger myLog= LogsCenter.getLogger(TextFileStorage.class);
    	myLog.fine("starting to read");
    	
    	//TODO construct tasks, create ReadOnlyUserInbox

    	BufferedReader read = new BufferedReader(new FileReader(file));
    	String line;
    	UniqueTaskList tasksRead = new UniqueTaskList();
    	UserInbox inboxToReturn = new UserInbox();
    	
    	try {
        	while ((line = read.readLine()) != null) {
        		StringTokenizer str = new StringTokenizer(line);
        		System.out.println(line);
        		Name taskName = new Name(str.nextToken());
        		Description description = new Description(str.nextToken().replaceAll("/", ""));
        		UniqueTagList tagList = new UniqueTagList();
        		tasksRead.add(new Task(taskName, description, tagList));
        		inboxToReturn.setTasks(tasksRead);
        	}    		
    	} catch (Exception e) {
    		//whatever
    		e.printStackTrace();
    	}
    	
    	read.close();
    	
    	return inboxToReturn;
    }

}
