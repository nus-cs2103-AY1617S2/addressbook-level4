# ProcrastiNomore (Task Manager Application) - User Guide

By : `Team ProcrastiNomore`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Introduction](#1-introduction)
2. [Quick Start](#2-quick-start)
3. [Features](#3-features)
4. [FAQ](#4-faq)
5. [Command Summary](#5-command-summary)

## 1. Introduction

This User Guide will aid you in understanding how ProcrastiNomore operates and what are
the functionalities this application has.

ProcrastiNomore is a single command line task-management application that is able to
store new tasks and edit existing tasks. This application will aid you in organizing
all your corresponding tasks and events.

## 2. Quick Start

1. Please ensure that you have Java version 1.8.0_60 or later install in your Computer.<br>

   > Having any Java 8 version is not enough.<br>
   > This app will not work with earlier versions of Java 8.

2. Download the latest `ProcrastiNomore.jar` from the [releases](../../../releases) tab.
3. Copy the file to the folder you want to use as the home folder for your Task Manager.
4. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/ProcrastiNomore_Covershot.PNG" width="1000">

5. Using the application

   >This application works by entering commands via the keyboard.<br>
   >Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   >For example, typing **`HELP`** and pressing <kbd>Enter</kbd> in the command box and it will
   open the help window. <br>
   > <img src="images/ProcrastiNomore_Help.PNG" width="1000">

6. Refer to the [Features](#Features) section below for more details on the various
commands you can use.<br>


## 3. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the command words.
> * Command words can be in any order.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.

### 3.1. Viewing help : `HELP`

You can use this command to open a help window which will open this user guide page

Format: `HELP`

> Help is also shown if you enter an incorrect command e.g. `ADD`
> <img src="images/ProcrastiNomore_Help_Shown.PNG" width="1000">

### 3.2. Adding a new Task: `ADD`

You can use this command to add different types of task in ProcrastiNomore. <br>
There are 3 forms of task that ProcrastiNomore supports:

#### 3.2.1. Events

Format: `ADD` task `ON` date/day <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`ADD` task `ON` date/day time `TO` time <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`ADD` task `FROM` date/day time `TO` date/day time <br />

Sample Event:
> Type the following command `ADD Stayover with Friends FROM 10/05/17 1400 TO 11/05/16 1800 CATEGORY Fun` into the command Box and press <kbd>Enter</kbd>
> <img src="images/ProcrastiNomore_Before_Add_Event_Task.PNG" width="1000">
> The application will save the sample event in Events column 
> <img src="images/ProcrastiNomore_After_Add_Event_Task.PNG" width="1000">

#### 3.2.2. Deadlines

Format: `ADD` task `BY` date/time/date time

Sample Deadline:
> Type the following command `ADD finish project BY 05/05/17 1400` into the command Box and press <kbd>Enter</kbd>
> <img src="images/ProcrastiNomore_Before_Add_Deadline.PNG" width="1000">
> The application will save the sample deadline in the Deadlines column
> <img src="images/ProcrastiNomore_After_Add_Deadline.PNG" width="1000">


#### 3.2.3. Basic Task

Format:	`ADD` task

Sample Basic Task:
> Type the following command `ADD Eat fried chicken` into the command box and press <kbd>Enter</kbd>
> <img src="images/ProcrastiNomore_Before_Add_Floating_Task.PNG" width="1000">
> The application will save the sample basic task into the Basic Tasks column
> <img src="images/ProcrastiNomore_After_Add_Floating_Task.PNG" width="1000">

Other examples:

* `ADD` eat breakfast `ON` 03/03/17
* `ADD` eat lunch `ON` thursday
* `ADD` eat dinner `FROM` today 1730 `TO` today 1930
* `ADD` eat breakfast `FROM` tomorrow 0800 `TO` tomorrow 0830
* `ADD` eat lunch `ON` 03/03/17 1400 `TO` 1500
* `ADD` eat dinner `BY` 03/03/17 330
* `ADD` eat breakfast `BY` 0730
* `ADD` eat breakfast

> TIME input must be in 24hrs format <br>
> If the function "ADD ... BY .." is used without stating the end time, the default end time will be 2359.

### 3.3. Update an existing task : `UPDATE`

You can use this command to update existing task in ProcrastiNomore. <br>
There are 2 types of updates that ProcrastiNomore supports:

#### 3.3.1. Update task name

Format: `UPDATE` `Task Index` `New Task Name`

Sample Update task name:
> Type the following command `UPDATE 36 Buy a horse` into the command Box and press <kbd>Enter</kbd>
> <img src="images/ProcrastiNomore_Before_Update_Task_Name.PNG" width="1000">
> The application will update the existing task with task index 36 and change the task name from "Buy a cat" to "Buy a horse"
> <img src="images/ProcrastiNomore_After_Update_Task_Name.PNG" width="1000">

#### 3.3.2. Update task time/date

Format: `UPDATE` `Task Index` `FROM` Start time <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`UPDATE` `Task Index` `TO` End time

Sample Update task time:
> Type the following command `UPDATE 1 TO 0230` into the command Box and press <kbd>Enter</kbd>
> <img src="images/ProcrastiNomore_Before_Update_Task_Time.PNG" width="1000">
> The application will update the existing task with task index 1 and change the task end time from "0002" to "0230"
> <img src="images/ProcrastiNomore_After_Update_Task_Time.PNG" width="1000">

Examples:
* `UPDATE` `1` eat dinner
* `UPDATE` `1` `FROM` thursday `TO` friday

> Please note that by inputting `UPDATE` `TASKINDEX` with no additional information will result in
> all existing timings to be removed.

### 3.4. Delete an existing task: `DELETE`

You can use this command to delete existing task in ProcrastiNomore

Format: `DELETE` `Task Index` <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`DELETE` `Task name` <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`DELETE` `Date`

> In the event of `DELETE` `Task name`/`Date`, ProcrastiNomore will delete all tasks with the
> Task name/Date specified in the command.

Examples:
* `DELETE` breakfast <br />
* `DELETE` `1` <br />
* `DELETE` 15/06/17 <br />

### 3.5. Remove all tasks: `CLEAR`

You can use this command to clear all task that is shown in the current list.

Format: `CLEAR`

### 3.6. Search by entering keywords/dates: `SEARCH`

You can use this command to search through all existing task in ProcrastiNomore.

Format: `SEARCH` `Keyword` <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`SEARCH` `Date`

> In the event of non-unique KEYWORD, ProcrastiNomore will show a list of tasks
> with the same Keyword/Date

Examples:
* `SEARCH` breakfast <br>
  Returns any tasks with containing breakfast
* `SEARCH` 05/12/17 <br>
  Returns any tasks with dates containing 05/12/17

### 3.7. To mark tasks as completed: `MARK`

You can use this command to identify tasks that you have accomplished and completed.

Format: `MARK` `Task Index` <br>

Examples:
* `MARK` `1` <br>
  Task number `1` in your to do list will be marked as completed.
* `MARK` `5` <br>
  Task number `5` in your to do list will be marked as completed.

Tasks identified as completed will no longer be displayed in the uncompleted list of tasks.

### 3.8. To mark tasks as uncompleted: `UNMARK`

You can use this command to identify tasks that you have already previously identified that you have
 accomplished as uncompleted.

Format: `UNMARK` `Task Index` <br>

Examples:
* `UNMARK` `1` <br>
  Task number `1` in your completed list will be marked as uncompleted and to be done.
* `UNMARK` `5` <br>
  Task number `5` in your completed list will be marked as uncompleted and to be done.

### 3.9. To sort through the list of task displayed: `LIST`

You can use this command to view all the tasks that you have yet to finish doing.

Format: `LIST` <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`LIST date/day`

Examples:
* `LIST` today <br>
  All uncompleted tasks which contain the today's date will be shown.

Using the list command on its own will show all existing task which are uncompleted.

### 3.10. To sort through the list of task displayed: `COMPLETED`

You can use this command to view all the tasks that you have completed.

Format: `COMPLETED`

### 3.11. To Undo your previous commands: `UNDO`

1 command will be undone every time this command is called.

### 3.12. To Redo your previous commands: `REDO`

1 command will be redone every time this command is called.

### 3.13. Saving the data: `SAVE`

Task Manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

In the event that you with to change the save location of the task manager, you may use the following command: `SAVE` <br>

Format: `SAVE [File Location]`

Examples:
* `SAVE C:\Users\<ACCOUNT NAME>\Desktop\` <br>
  Changes the save location of the task manager to your desktop.
* `SAVE data\` <br>
  Changes the save location of the task manager back to the default save location.

### 3.14. Recurring command: `RECUR`

You can use this command to make repeated task with different dates.

### 3.15. Exiting the program : `EXIT`

Exits the program.<br>

Format: `EXIT`

## 4. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manager folder.

## 5. Command Summary

* **View Help Window** : `HELP` <br>

* **Add a new task**  `ADD (taskname) ...` <br>
  &nbsp;e.g. `ADD eat food ON 03/03/17 0830 TO 1030`

* **Update an existing task**  `UPDATE TASKINDEX TASKNAME` <br>
  &nbsp;e.g. `UPDATE 1 Email boss`

* **Delete an existing task** : `DELETE INDEX` <br>
   e.g. `DELETE 3`

* **Clear all tasks in current list** : `CLEAR`

* **Search for specified keywords** : `SEARCH KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `SEARCH find more food`

* **Indicate task is completed** : `MARK INDEX` <br>
   e.g. `MARK 3`

* **Indicate task is uncompleted** : `UNMARK INDEX` <br>
   e.g. `UNMARK 3`

* **List all uncompleted task** : `LIST` <br>

* **List all completed task** : `COMPLETED` <br>

* **Undo a command** : `UNDO` <br>

* **Redo a command** : `REDO` <br>

* **Change the save location** : `SAVE [File Location]` <br>
  e.g.`SAVE C:\Users\<ACCOUNT NAME>\Desktop\`
