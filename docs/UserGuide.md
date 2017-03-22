# AddressBook Level 4 - User Guide

By : `Team SE-EDU`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jun 2016`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

0. [Introduction](#introduction)
1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Introduction

This User Guide will aid you in understanding how ProcrastiNomore operates and what are
the functionalities this application has.

ProcrastinNomore is a single command line task-management application that is able to
store new tasks and edit existing tasks. It is able to store the tasks in Google calendar
and will aid you in organizing all your corresponding tasks and events.

## 2. Quick Start

0. Please ensure that you have Java version 1.8.0_60 or later install in your Computer.<br>

   > Having any Java 8 version is not enough.<br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `procrastinomore.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="300">

4. Using the application

   >a. This application works by entering commands via the keyboard.<br>
   >b. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   >c. There are also several keyboard shortcuts found at the bottom of the User Guide for
   quick access of several functions.<br>
   >d. typing **`help`** and pressing <kbd>Enter</kbd> in the command box and it will
   open the help window.
   > <img src="images/Ui_help.png" width="300">

5. Refer to the [Features](#features) section below for more details on the various
commands you can use.<br>


## 3. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 3.1. Viewing help : `HELP`

Format: `HELP`

> Help is also shown if you enter an incorrect command e.g. `abcd`

### 3.2. Adding a new Task: `ADD`

There are 4 forms of task that ProcrastiNomore supports:

#### 3.2.1. Events

Format: `ADD TASK ON DATE/DAY` <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`ADD TASK FROM STARTDATE STARTTIME TO ENDDATE ENDTIME` <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`ADD TASK ON DATE/DAY STARTTIME TO ENDTIME` <br />

#### 3.2.2. Deadlines

Format: `ADD TASK BY DATE/TIME/DATE+TIME`

#### 3.2.3. Untimed

Format:	`ADD TASK`

Examples:

* `ADD eat breakfast ON 03/03/17`
* `ADD eat lunch ON thursday`
* `ADD eat dinner FROM today 1730 TO today 1930`
* `ADD eat breakfast FROM tomorrow 0800 TO tomorrow 0830`
* `ADD eat lunch ON 03/03/17 1400 TO 1500`
* `ADD eat dinner BY 03/03/17`
* `ADD eat breakfast BY 0730`
* `ADD eat lunch BY 03/03/17 330pm`
* `ADD eat breakfast`

> TIME input can be in 24hrs format or 12hrs format with am/pm
> If the function "ADD ... BY .." is used without stating the end time, the default end time will be 2359.

### 3.3. Update an existing task : `UPDATE`

There are 3 types of updates that ProcrastiNomore supports:

#### 3.3.1. Update task name

Format: `UPDATE TASK`
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select task index `1/2/3...`
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`new TASK`

#### 3.3.2. Update task time

Format: `UPDATE TASK TO STARTTIME ENDTIME` <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`update TASK to TIME`

#### 3.3.3. Update the entire task index

Format: `UPDATE TASKINDEX NEWTASKNAME/DATE/TIME`

> For 3.3.1. and 3.3.2., ProcrastiNomore will show a list of task with the
> same task name and user will be required to put the TASKINDEX of the
> TASK user wants to change

Examples:
* `UPDATE eat breakfast TO eat lunch` <br />
  `1`
* `UPDATE eat dinner TO 730pm` <br />
  `1`
* `UPDATE eat dinner TO 730pm 830pm`
  `2`
* `LIST`
  `UPDATE 1 eat dinner 730pm`

### 3.4. Delete an existing task: `DELETE`

Format: `DELETE TASKNAME` <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`DELETE TASKINDEX`

> In the event of DELETE TASKNAME, ProcrastiNomore will show a list of tasks
> with the same TASKNAME and user will be required to input the TASKINDEX of
> the TASK user wants to delete

Examples:
* `DELETE breakfast` <br />
  `1`
* `list` <br />
  `DELETE 1`

### 3.5. Wipe out history of all or specified tasks: `CLEAR`

There are 3 types of clear commands.

#### 3.5.1. Delete all tasks

Format: `CLEAR all`

#### 3.5.2. Delete all tasks on date specified

Format: `CLEAR DATE`

#### 3.5.3. Delete all previously marked uncomplete/complete tasks

Format: `CLEAR completed` <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`CLEAR uncompleted`

Examples:
* `CLEAR 03/03/17`

### 3.6. Search through all existing task by entering keywords/dates: `SEARCH`

Format: `SEARCH KEYWORD` <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`SEARCH DATE`

> In the event of non-unique KEYWORD, ProcrastiNomore will show a list of tasks
> with the same KEYWORD

Examples:
* `SEARCH breakfast` <br>
  Returns any tasks with containing breakfast

### 3.7. To sort through the list of task displayed: `SORT`

Format: `SORT` <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`SORT DATE` <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`SORT TASKNAME`

Using the sort command on its own will sort the task in ascending TASKINDEX order

### 3.8. To mark a task with higher importance: `PRIOR`

Format: `PRIOR TASKNAME` <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`PRIOR TASKINDEX` <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`PRIOR DATE`

### 3.9. To Undo your previous commands: `UNDO`

Shortcut: `Ctrl+Z`<br>

1 command will be undone every time this command is called.

### 3.10. To Redo your previous commands: `REDO`

Shortcut: `Ctrl+Y`<br>

1 command will be redone every time this command is called.

### 3.11. Exiting the program : `EXIT`

Exits the program.<br>

Format: `exit`

### 3.12. Saving the data

Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 4. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Address Book folder.

## 5. Command Summary

* **Add**  `ADD TASK ON DATE/DAY FROM STARTTIME TO ENDTIME` <br>
  &nbsp;e.g. `ADD eat food ON 03/03/17 FROM 0830 TO 1030`

* **Clear** : `CLEAR`

* **Delete** : `DELETE INDEX` <br>
   e.g. `delete 3`

* **Search** : `SEARCH KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find more food`

* **List** : `LIST` <br>
  e.g.

* **Help** : `HELP` <br>
  e.g.

* **Select** : `SELECT INDEX` <br>
  e.g.`select 2`
