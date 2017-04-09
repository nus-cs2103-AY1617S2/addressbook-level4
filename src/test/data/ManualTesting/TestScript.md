# Test Script

A document explaining the steps to perform manual testing (i.e. manual scripted testing), starting with how to load the sample data. Should cover all functionality of the product. Should specify the command to type and the expected result (screenshots not required). Limit the test cases to about 20 minutes worth of testing.

## Step 1: Load the data
Find where the project is saved on your computer
Enter the command "load main/src/test/data/ManualTesting/SampleData.xml

## Step 2: Perform Testing

When testing, copy and paste the String after "Command: " into the command line and press "Enter"

#Help Command
Command: help
Result: A help window will pop up that directs you to the user guide

#Add Command
Command: add New Task d/010517 s/10:00 e/12:00 m/Sample Message
Result: A new task will be added with the above criteria

Command: add New Task
Result: A new floating task will be added

Command: add New Task s/10:00 m/Sample Message
Result: A new task with partial criteria be added

#Find Command
Command: find New
Result: All tasks containing the word "New" will be displayed

Command: find w T
Result: All tasks containing the word "New Task" will be displayed

#Edit Command
Command: edit 1 s/12:00
Result: The start time at for task 1 will be changed to 12:00

Command: edit 2 Edit Two m/New Message
Result: The task name and message for task 2 will be changed to Edit Two and New Message, respectively

#Clear Command
Command: clear
Result: All tasks in the task manager will be cleared and deleted

#Undo Command
Command: undo
Result: Will undo the most recent command, in this case clear

#Redo Command
Command: redo
Result: Will redo the most recent undo

#Done Command
Command: done 1
Result: Will mark the task at index 1 as "Completed" move it to the bottom of the list

Command: done 1 2
Result: Will mark the task at index 1 and 2 as "Completed" move them to the bottom of the list

#Filter Command
Command: filter status completed
Result: Shows all completed tasks

Command: filter after today
Result: Shows all tasks with a date after the current date

Command: filter before 12/12/12
Result: Shows all tasks with a date before the date 12/12/12

Command: filter name New Task
Result: Shows all tasks with the task name "New Task"

Command: filter desc Sample Message
Result: Shows all tasks with the description "Sample Message"

#Google Command
Command: google 2
Result: Google's the task name specified at index 2

#Path Command
Command:
Result:

#Load Command
Command:
Result:

#Exit Command
Command: exit
Result: Exits the application