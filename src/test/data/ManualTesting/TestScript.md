# Test Script

A document explaining the steps to perform manual testing (i.e. manual scripted testing), starting with how to load the sample data. Should cover all functionality of the product. Should specify the command to type and the expected result (screenshots not required). Limit the test cases to about 20 minutes worth of testing.

## Step 1: Load the data
SampleData.xml will already be loaded in the .jar file when you open it.

## Step 2: Perform Testing

When testing, copy and paste the String after "Command: " into the command line and press "Enter"

## Help Command
Command: help <br>
Result: A help window will pop up that directs you to the user guide

## Add Command
Command: add New Task d/010517 s/10:00 e/12:00 m/Sample Message<br>
Result: A new task will be added with the above criteria<br>

Command: add New Task<br>
Result: A new floating task will be added<br>

Command: add New Task s/10:00 m/Sample Message<br>
Result: A new task with partial criteria be added<br>

## Find Command
Command: find New<br>
Result: All tasks containing the word "New" will be displayed<br>

Command: find w T<br>
Result: All tasks containing the word "New Task" will be displayed<br>

## Edit Command
Command: edit 1 s/12:00<br>
Result: The start time at for task 1 will be changed to 12:00<br>

Command: edit 2 Edit Two m/New Message<br>
Result: The task name and message for task 2 will be changed to Edit Two and New Message, respectively<br>

## Clear Command
Command: clear<br>
Result: All tasks in the task manager will be cleared and deleted<br>

## Undo Command
Command: undo<br>
Result: Will undo the most recent command, in this case clear<br>

## Redo Command
Command: redo <br>
Result: Will redo the most recent undo<br>

## Done Command
Command: done 1<br>
Result: Will mark the task at index 1 as "Completed" move it to the bottom of the list<br>

Command: done 1 2<br>
Result: Will mark the task at index 1 and 2 as "Completed" move them to the bottom of the list<br>

## Filter Command
Command: filter status completed<br>
Result: Shows all completed tasks<br>

Command: filter after today<br>
Result: Shows all tasks with a date after the current date<br>

Command: filter before 12/12/12<br>
Result: Shows all tasks with a date before the date 12/12/12<br>

Command: filter name New Task<br>
Result: Shows all tasks with the task name "New Task"<br>

Command: filter desc Sample Message<br>
Result: Shows all tasks with the description "Sample Message"<br>

## Google Command
Command: google 2<br>
Result: Google's the task name specified at index 2<br>

## Path Command
Command (PC): path C:\Desktop\MyTasks.xml <br>
Result: Will create a new .xml file on the Desktop called MyTasks.xml. All further changes to Fast Task will me made to this file.

Command (Mac): path /Users/YourName/Desktop/MyTasks.xml <br>
Result: Will create a new .xml file on the Desktop called MyTasks.xml. All further changes to Fast Task will me made to this file. <br>

## Load Command
Command (PC): load SampleData.xml <br>
Result: Will load the initial set of tasks that were modifed before a new task path was specified<br>

Command (Mac): load SampleData.xml <br>
Result: Will load the initial set of tasks that were modifed before a new task path was specified<br>

## Exit Command
Command: exit<br>
Result: Exits the application
