# Test Script for Manual Testing

## Loading ProcrastiNomore with sample data	

Ensure that you have the file `SampleData.xml` inside the `/src/test/data/ManualTesting` folder.
Open the SampleData.xml file and copy the contents into the taskmanager.xml file in the src/data folder. 

If the taskmanager.xml file does not exist, create a new xml file with the file name "taskmanager" then
copy the content from the SampleData.xml file over.

Run the MainApp.java file as a Java application. This will prompt the application to run and the ProcrastiNomore
UI will appear.

If the sample data will copied over correctly, the task manager will have a total of 51 tasks inside. <br>
Index 1 - 19 will be in the Events column. <br>
Index 20 - 35 will be in the Deadlines column. <br>
Index 36 - 51 will be in the Basic Task column.

## Testing the HELP function:

### 1. Help function
Input `HELP`

Result: A help window appears, listing out all the commands available, together with the command description and format.

### 2. Wrong command
Input `Add`

Result: "Unknown command <br>
Type HELP for user guide with detailed explanations of all commands"

## Testing the ADD function:

### 1. Add an event
Input `ADD doing testing FROM today 1200 TO tmr 1400`

Result: "New task added: doing testing Start Date: [today's date] Start Time: 1200 End Date: [tomorrow's date] End Time: 1300 <br>
Task added at index: [varies]"

### 2. Add a clash event
Input `ADD doing testing ON today 1200 TO 1400`

Result: "Clash with task: Index [varies] <br>
New task added: doing testing Start Date: [today's date] Start Time: 1200 End Date: [today's date] End Time: 1400 <br>
Task added at index: [varies]"

### 3. Add a deadline
Input `ADD deadline BY today`

Result: "New task added: deadline End Date: [today's date] End Time: 2359 <br>
Task added at index: [varies]" 


### 4. Add a floating task
Input `ADD floatingTask`

Result: "New task added: floatingTask <br>
Task added at index: [varies]"


## Testing out the UPDATE function:

### 1. Update task name
Find basic task name `Buy a dog` and remember the index number `[INDEX]`
Input `UPDATE [INDEX] Buy 4 dogs`

Result: "Updated Task: Buy 4 dogs Categories: [lepak] <br>
Task updated to index: [varies]"

### 2. Update task end time
Find event name `Meet boss` and remember the index number `[INDEX]`
Input `UPDATE [INDEX] TO 1400`

Result: "Updated Task: Meet boss Start Date: 07/05/17 Start Time: 0000
End Date: 08/05/17 End Time: 1400 Categories: [work] <br>
Task updated to index: 20"

## Testing out the SEARCH function:

### 1. Search by task name
Input `SEARCH work`

Result: "5 tasks listed!" <br>
The task whose task name and categories contain the word "work" will be shown.

### 2. Search by date
Input `SEARCH 03/03/17`

Result: "2 tasks listed!' <br>
The task whose start date or end date contain the date "03/03/17" will be shown.

## Testing out the CLEAR function:

Input `CLEAR`

Result: "Task Manager has been cleared!" <br>
All the task from ProcrastiNomore has been removed.

## Testing out the UNDO function:

Input `UNDO`

Result: "Previous action has been undone." <br>
All the task has been brought back from the previous CLEAR function.

## Testing out the REDO function:

Input `REDO`

Result: "Previous action has been redone." <br>
All the task has been cleared once again.

## Testing out the RECUR function:

### 1. Recur Events
Input <br>
1. `ADD recurring task ON 03/03/17 1400` <br>
2. `RECUR 1 3 days`

Result: <br>
1. "New task added: recurring task Start Date: 03/03/17 Start Time: 1400 
End Date: 03/03/17 End Time: 1500 <br>
Task added at index: 1"
2. "Task has successfully recurred" <br>
The event recurring task has been recurred 3 more times, everyday for 3 days.

### 2. Recur Deadlines
Input <br>
1. 'ADD recurring deadline BY 05/05/17 1400` <br>
2. `RECUR 5 4 months` <br>

Result: <br>
1. "New task added: recurring deadline End Date: 05/05/17 End Time: 1400 <br>
Task added at index: 5"
2. "Task has successfully recurred" <br>
The deadline recurring deadline has been recurred 4 more times, on the 5th of every month.

## Testing out the MARK function:
Input `MARK 1`

Result: "Task is marked as completed: recurring task Start Date: 03/03/17 
Start Time: 1400 End Date: 03/03/17 End Time: 1500" <br>

## Testing out the COMPLETED function:
Input `COMPLETED`

Result: "Listed all completed tasks"

## Testing out the UNMARK function:
Input `UNMARK 1`

Result: "Task is marked as incomplete: recurring task Start Date: 03/03/17
Start Time: 1400 End Date: 03/03/17 End Time: 1500" <br>

## Testing out the LIST function:

### 1. Listing all uncompleted task
Input <br>
1. `COMPLETED`
2. `LIST`
3. `MARK 1`
4. `COMPLETED`
5. `LIST`

Result: <br>
1. "Listed all completed tasks"
2. "Listed all uncompleted tasks"
3. Mark task at index 1 as completed
4. View previously marked task
5. Show the rest of the uncompleted tasks

### 2. Listing uncompleted task for a specific date
Input <br>
1. `ADD tasktobeviewed ON 05/05/17`
2. `ADD tasktobeviewed BY 05/05/17`
3. `LIST 05/05/17`
4. `MARK 3`
5. `LIST 05/05/17`

Result: <br>
1. Event task added.
2. Deadline task added.
3. "Listed all uncompleted tasks for [05/05/17]" <br>
There will be 3 uncompleted tasks listed
4. One of the deadline task is marked as completed.
5. There will be 2 uncompleted tasks listed now.

## Testing out other features:

### Remove categories from existing task

Input <br>
1. `ADD task CATEGORY High CATEGORY test`
2. `UPDATE 10 CATEGORY`

Result <br>
1. Add a floating task with 2 categories
2. Inputting the command word `CATEGORY` without any other information removes existing categories.

### Adding categories to existing task

Input
1. `UNDO`
2. `UPDATE 10 CATEGORY High CATEGORY test CATEGORY more`

Result <br>
1. Recover the previous floating task with 2 categories
2. Add another category "more"

### Change floating task to event

Input `UPDATE 10 ON 10/04/17 1200`

Result: "Updated Task: task Start Date: 10/04/17 Start Time: 1200 End Date: 10/04/17 End Time: 1300 Categories: [low] <br>
Task updated to index: 5???????????????" <br>
Notice the shift from Basic Tasks to the Events column.

### Change event to floating task

Input `UPDATE 4`

Result: "Updated Task: task Categories: [low] <br>
Task updated to index: 12????????????" <br>
Notice the shift from Events to Basic Tasks column.

### Change event to deadline

Input `UPDATE 1 BY 04/04/17 2300`

Result: "Updated Task: recurring task End Date: 04/04/17 End Time: 2300 <br>
Task updated to index: 5" 
Notice the shift from Events to Deadlines column.

## Testing out the SAVE function:
Input `SAVE C:\Users\<Computer User ID>\Desktop`

Result: "Save location changed to: C:\Users\<Computer User ID>\Desktop\taskmanager.xml"
ProcrastiNomore changes the save location as specified.

## Testing out the EXIT function:
Input `EXIT`

Result: ProcrastiNomore closes.

