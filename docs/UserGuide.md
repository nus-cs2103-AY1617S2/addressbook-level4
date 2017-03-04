## **1. Quick Start**

1. Ensure you have Java version 1.8.0_60 or later installed in your Computer.

2. Having any Java 8 version is not enough.

3. This app will not work with earlier versions of Java 8.

4. Download the latest taskmanager.jar from the [releases](https://github.com/CS2103JAN2017-W15-B4/main/releases) tab.

5. Copy the file to the folder you want to use as the home folder for your task manager.

6. Double-click the file to start the app. The GUI should appear in a few seconds.

7. Type the command in the command box and press Enter to execute it.

8. e.g. typing help and pressing Enter will open the help window.

9. Some example commands you can try:

    * list : lists all events and tasks

    * Add Complete Progress Report ed/20-3-17 et/2359 : adds a task titled Complete Progress Report with deadline on 20 March 2017 2359H to the task manager.

    * Delete 3 : deletes the 3rd event or contact shown in the current list

    * exit : exits the app

10. Refer to the [Features](https://github.com/CS2103JAN2017-W15-B4/main/blob/master/docs/UserGuide.md#features) section below for details of each command.

## **2. Features**

Command Format

* Words in UPPER_CASE are the parameters.

* Items in SQUARE_BRACKETS are optional.

* Items with ... after them can have multiple instances.

* Parameters can be in any order.

### **2.1. Viewing help : help**

Format: help

Help is also shown if you enter an incorrect command e.g. abcd

### **2.2. Adding a task: add**

### Adds a task or event to the task manager

Format: add TASK_OR_EVENT_NAME [startdate/START_DATE] [sd/START_DATE] [starttime/START_TIME] [st/START_TIME] [enddate/END_DATE] [ed/END_DATE] [endtime/END_TIME] [et/END_TIME]

Examples:

* add Progress report ed/15-3-17 et/1600

* add shop groceries

* add Team meeting startdate/15-3-17 st/1500 ed/15-3-17 et/1600

* add celebration sd/1-4-17 ed/1-4-17

### **2.3. Listing all tasks or events : list**

Shows a list of all uncompleted tasks and events in the task manager.

Format: list

### **2.4. Listing all completed tasks or events : listcompleted**

Shows a list of all completed tasks and events in the task manager.

Format: listcompleted

### **2.5. Listing all tasks : listtasks**

Shows a list of all undone tasks in the task manager.

Format: listtasks

### **2.6. Listing all events : listevents**

Shows a list of all uncompleted events in the task manager. Events are tasks with a starting and ending time or date.

Format: listevents

### **2.7. Editing a task or event : update**

Edits an existing task in the task manager.

Format: update INDEX [TASK_OR_EVENT_NAME] [ed/END_DATE] [et/END_TIME]

* Edits the task at the specified INDEX. The index refers to the index number shown in the last task listing.

* The index must be a positive integer 1, 2, 3, ...

* At least one of the optional fields must be provided.

* Existing values will be updated to the input values.

Examples:

* update 1 Summary Report et/2359

* Edits the title of task 1 and updates its ending time to 2359

* update 2 ed/ et/

* Clears the existing deadlines for task 2

### **2.8. Finding all tasks and events containing any keyword in their name or on a specific date : find**

Finds tasks whose names contain any of the given keywords.

Format: find KEYWORD [MORE_KEYWORDS] [DATE]

* The search is case insensitive.

* The order of the keywords does not matter. e.g. progress report will match report progress

* Only the task or event name is searched.

* Substrings will be matched e.g. meet will match meeting

* Tasks matching at least one keyword will be returned (i.e. OR search). e.g. meeting will match team meeting

* Specifying the date will narrow the search space to tasks and events on the day

Examples:

* find report

* Returns any task or event having the word report

* find meet

* Returns any task or event having the word or substring meet.

* find meet 17-3-17

* Returns any task or event having the word or substring meet on 17-3-17

### **2.9. Check the tasks and events current day: check**

Finds tasks or events which are due on the current day.

Format: check

* Iterates through each item on the returned list

* Prompts the user for action

Examples:

* check

* Returns a list of tasks and events on the current day

* Task manager prompts user to mark each task and event as done, update or delete

### **2.10. Mark a task as done : done**

Mark the specified task from the task manager as done.

Format: done INDEX

Marks the task at the specified INDEX as done.

The index refers to the index number shown in the most recent listing.

The index must be a positive integer 1, 2, 3, ...

Examples:

* listtask

* done 2

* Marks the 2nd task in the task manager as done.

* find Report

* done 1

* Marks the 1st task in the results of the find command as done.

### **2.11. Deleting a task : delete**

Deletes the specified task from the task manager. Irreversible.

Format: delete INDEX

Deletes the task at the specified INDEX.

The index refers to the index number shown in the most recent listing.

The index must be a positive integer 1, 2, 3, ...

Examples:

* listtask

* delete 2

* Deletes the 2nd task in the task manager.

* find Report

* delete 1

* Deletes the 1st task in the results of the find command.

### **2.12. Undo most recent command : undo**

Undo the most recent command. Can redo with command redo

Format: undo

### **2.13. Redo most recent undo : redo**

Redo the most recent undo. Can undo with command undo

Format: redo

### **2.14. Exiting the program : exit**

Exits the program.

Format: exit

### **2.15. Saving the data**

task manager data are saved in the hard disk automatically after any command that changes the data.

There is no need to save manually.

## **3. FAQ**

Q: How do I transfer my data to another Computer?

A: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous task manager folder.

## **4. Command Summary**

* Add add TASK_OR_EVENT_NAME [sd/START_DATE] [st/START_TIME] [ed/END_DATE] [et/END_TIME]

* e.g. add Team meeting sd/15-3-17 st/1500 ed/15-3-17 et/1600

* Update : update INDEX [TASK_OR_EVENT_NAME] [ed/END_DATE] [et/END_TIME]

* e.g. : update 1 Summary Report et/2359

* Delete : delete INDEX

* e.g. delete 3

* Done : done INDEX

* e.g. done 1

* Find : find KEYWORD [MORE_KEYWORDS] [DATE]

* e.g. find report

* Undo : undo

* e.g. undo

* Redo : redo

* e.g. redo

* Check : check

* e.g. check

* List uncompleted tasks and events : list

* e.g. list

* List uncompleted tasks : listtasks

* e.g. listtasks

* List uncompleted events: listevents

* e.g. listevents

* Help : help

* e.g. help
