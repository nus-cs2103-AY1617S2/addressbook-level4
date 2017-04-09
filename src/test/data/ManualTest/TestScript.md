# Test Script for TypeTask

**Command:**<br>
(Pre-condition: src\test\data\ManualTest\taskManager.xml exists)

    setting src/test/data/ManualTest

**Expected Result:**

* "Task Manager is updated" will be displayed on the ResultDisplayBar.
* Load tasks from taskManager.xml in src/test/data/ManualTest to myPotato.

**Command:**

    help

**Expected Result:**

* A new window will be created and displays the Command Summary.

**Command:**

    add Buy Eggs
    
**Expected result:**

* Floating task Buy Eggs will be added to the list.
* "New task added!" and the details of task will be displayed on the ResultDisplayBar.
* The task can be seen straight away after adding.
    
**Command:**

    add FYP Project by:next week
    add Pay Bills by:11pm


**Expected result:**

* Deadline task will be added to the list.
* "New task added!" and the details of task will be displayed on the ResultDisplayBar.
* The task can be seen straight away after adding.
* "FYP Project" task should have a green indicator beside the index.
* "Pay Bills" task should have deadline set as today's date, 11pm.

**Command:**

    add NUS Open House from:15 November to:20 November

**Expected result:**

* Event task will be added to the list.
* "New task added!" and the details of task will be displayed on the ResultDisplayBar.
* The task can be seen straight away after adding.
* The start date should be 15 November 23:59:59 and end date should be 20 November 23:59:59
* Both date will show current year.

**Command:**

    add Meeting with boss @4pm p/High

**Expected result:**

* Deadline task will be added to the list.
* "New task added!" and the details of task will be displayed on the ResultDisplayBar.
* The deadline for this task will be current date, 4pm.
* There will be an exclaimation mark image on the task.

**Command:**

    done 52

**Expected result:**

* Task number 52 in the current list will be removed from the task list.
* "Completed? Yes" will be displayed on the ResultDisplayBar.
* This task is added to the completed task list.

**Command:**

    list

**Expected result:**

* The list containing all the uncompleted tasks are shown.
* "All task(s) listed!" message will be displayed on the ResultDisplayBar.

**Command:**

    listtoday

**Expected result:**

* The list containing today's date tasks will be shown.
* "Today's task(s) listed!" message will be displayed on the ResultDisplayBar.

**Command:**

    listdone

**Expected result:**

* The list containing completed tasks will be shown.
* "Completed task(s) listed!" message will be displayed on the ResultDisplayBar.
* All task should have a black color beside the index.


**Command:**

    listdone
    add Return book by:next month

**Expected result:**

* Completed list is shown 
* "Return book" task is added and will be now at the default list
* The default list is all uncompleted task. Same list as the "list" command
* Task can be seen straight away after adding.


**Command:**

    edit 1 read Harry Potter
    edit 2 by:next monday
    edit 3 from:5 april 1pm to:5 april 2pm

**Expected result:**

* edits the content of task number 1 from "read Harry Potter first book" to "read Harry Potter"
* edits the deadline for task number 2 to next monday
* edits the task to have a duration from 5 april 1pm to 5 april 2pm
* ResultDisplayBar should show each edited task details

**Command:**

    undo

**Expected result:**

* The previous edit should be undone and the result display should shows a "Restore previous command" message.

**Command:**

    redo

**Expected result:**

* The previous undo should be reversed. The task should be edited back. "Redo previous command" message will be shown in ResultDisplayBar

**Command:**

    find fast

**Expected result:**

* Task "watch fast and furious 8" will be shown in the list.
* A message "1 task(s) listed" should be shown in ResultDisplayBar


**Command:**

    list all
    delete 51

**Expected result:**

* Task 51 in the current list will be deleted
* ResultDisplayBar will display the details of the deleted task.

**Command:**<br>

    removedeadline 4

**Expected result:**

* Task with index number 4 in the list will have its schedule removed.
* ResultDisplayBar will display the details of the edited task


**Command:**

    clear

**Expected result:**

* The task list should now be emptied.
* "TaskManager has been cleared!" message should be shown on the result display.

**Command:**

    undo

**Expected result:**

* The previous task list should now be restored and the result display should shows a "Restore previous command" message

