# AddressBook Level 4 - User Guide

By : `Team SE-EDU`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jun 2016`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

0. [Introduction](#introduction)
1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 0. Introduction

This User Guide will aid you in understanding how ProcrastiNomore operates and what are
the functionalities this application has.

ProcrastinNomore is a single command line task-management application that is able to
store new tasks and edit existing tasks. It is able to store the tasks in Google calendar
and will aid you in organizing all your corresponding tasks and events.

## 1. Quick Start

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
   quick access of several functions
   >d. typing **`help`** and pressing <kbd>Enter</kbd> in the command box and it will
   open the help window.
   > <img src="images/Ui_help.png" width="300">

5. Refer to the [Features](#features) section below for more details on the various
commands you can use.<br>


## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 2.1. Viewing help : `help`

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

### 2.2. Adding a new Task: `add`

There are 4 forms of task that ProcrastiNomore supports:

#### 2.2.1. Events

Format: `add TASK on DATE/DAY` <br />
	`add TASK from STARTTIME to ENDTIME` <br />
	`add TASK on DATE/DAY from STARTTIME to ENDTIME` <br />

#### 2.2.2. Deadlines

Format: `add TASK by DATE/TIME/DATE+TIME`

#### 2.2.3. Untimed

Format:	`add TASK`

#### 2.2.4. Unconfirmed events

Format: `add TASK on DATE1 or DATE2 or DATE3`

> TIME input can be in 24hrs format or 12hrs format with am/pm

Examples:

* `add eat breakfast on 3/3/17`
* `add eat lunch on THURSDAY`
* `add eat dinner from 530pm to 730pm`
* `add eat breakfast from 1730 to 1930`
* `add eat lunch on 3/3/17 from 530pm to 730pm`
* `add eat dinner by 3/3/17`
* `add eat breakfast by 730pm`
* `add eat lunch by 3/3/17 by 330pm`
* `add eat dinner on 3/3/17 or 4/3/17 or 5/3/17`
* `add eat breakfast`

### 2.3. Update an existing task : `update`

There are 3 types of updates that ProcrastiNomore supports:

#### 2.3.1. Update task name

Format: `update TASK to NEWTASK`

#### 2.3.2. Update task time

Format: `update TASK to STARTTIME to ENDTIME` <br />
		`update TASK to TIME`

#### 2.3.3. Update the entire task index

Format: `update TASKINDEX NEWTASKNAME/DATE/TIME`

> For 2.3.1. and 2.3.2., ProcrastiNomore will show a list of task with the
> same task name and user will be required to put the TASKINDEX of the
> TASK user wants to change

Examples:
* `update eat breakfast to eat lunch` <br />
  `1`
* `update eat dinner to 730pm` <br />
  `1`

### 2.4. Delete an existing task: `delete`

Format: `delete TASKNAME` <br />
		`delete TASKINDEX`

> In the event of delete TASKNAME, ProcrastiNomore will show a list of tasks
> with the same TASKNAME and user will be required to input the TASKINDEX of
> the TASK user wants to delete

Examples:
* `delete breakfast` <br />
  `1`
* `list` <br />
  `delete 1`

### 2.5. Wipe out history of all or specified tasks: clear

There are 3 types of clear commands.

#### 2.5.1. Delete all tasks

Format: `clear all`

#### 2.5.2. Delete all tasks on date specified

Format: `clear DATE`

#### 2.5.3. Delete all previously marked uncomplete/complete tasks

Format: `clear completed` <br />
		`clear uncompleted`

Examples:
* `clear 03/03/17`

### 2.6. Search through all existing task by entering keywords/dates: search

Format: Search KEYWORD
        Search DATE

> In the event of non-unique KEYWORD, ProcrastiNomore will show a list of tasks
> with the same KEYWORD

Examples:
* `search breakfast`
Returns any tasks with containing breakfast

### 2.7. To sort through the list of task displayed: sort

Format: Sort
    Sort DATE
    Sort TASKNAME

Using the sort command on its own will sort the task in ascending TASKINDEX order

### 2.8. To mark a task with higher importance: prioritise

Format:   Prioritise TASKNAME
          Prioritise TASKINDEX
          Prioritise DATE

### 2.9. To Undo your previous commands: `undo`

Shortcut: `Ctrl+Z`<br>

1 command will be undone every time this command is called.

### 2.10. To Redo your previous commands: `redo`

Shortcut: `Ctrl+Y`<br>

1 command will be redone every time this command is called.

### 2.11. Exiting the program : `exit`

Exits the program.<br>

Format: `exit`

### 2.12. Saving the data

Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Address Book folder.

## 4. Command Summary

* **Add**  `add TASK on DATE/DAY from STARTTIME to ENDTIME` <br>
  e.g. `add eat food on 03/03/17 from 0830 to 1030`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find James Jake`

* **List** : `list` <br>
  e.g.

* **Help** : `help` <br>
  e.g.

* **Select** : `select INDEX` <br>
  e.g.`select 2`

