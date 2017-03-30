# FunTaskTic - User Guide

By : `F12-B3`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `taskmanager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/uiv0.0.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`help`** : list all the command can use
   * **`list`** : list all tasks
   * **`add submit report by Friday`** : add a task to the task manager
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## 2. Features

### 2.1. Viewing help : `help`

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

A command guide will be shown in the window

### 2.2. Adding a task: `add`

Adds a task to the task manager<br>
Format: `add TASK s/STARTING DATE e/ENDING DATE d/Description#tags`

> Only Task title is a compulsory field
> Tasks can have any number of tags (including 0)
> Tasks without starting starting or ending date will have it defaulted to the current time.

Examples:

* `add submit report e/29/07/2017 d/Assignment 1 #academic`
* `add read Harry Potter s/12/03/2017 e/25/06/2017 d/chapter 5 #personal`

### 2.3. Listing all tasks : `list`

Shows a list of all currently ongoing tasks in the task manager.<br>
Format: `list`
A list of tasks with index number will be shown in the column

### 2.4. Select a task : `select`

Selects the task identified by the index number used in the last task listing.<br>
Format: `select INDEX`

Examples:

* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the task manager.
* `find report` <br>
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.

### 2.5. Editing a task : `edit`

Edits an existing task in the task manager.<br>
The tasks can be edited through the index from a list.<br>
Format: `edit INDEX COMPONENT`

Examples:

* `edit 3 e/24/06/2017`<br>
  Changes the ENDING DATE of task 3 to 24/06/2017
* `edit 2 s/21/01/2017 e/24/06/2017 #school`<br>
  Changes task 3 STARTING DATE to 21/01/2017, ENDING DATE to 24/06/2017, tags to school

### 2.6. Finding all tasks containing any keyword in their task title / description / tags: `find`

Format: `find KEYWORD`

Examples:

* `find report`<br>
  Returns `submit report`  `print report` `#report` etc. in the column
* `find schoolwork sports`<br>
  Returns `do schoolwork`  `play sports` `#schoolwork` `#sports` etc. in the column

### 2.7. Deleting a task : `delete` or `remove`

Deletes the specified task from the task manager. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

### 2.8. Sorting tasks : `sort`

Sorts all tasks by their start date or end date.<br>
Format: `sort KEYWORD`

> Sorts tasks according to the input `KEYWORD`.<br>
> The keyword refers to start date or end date.<br>
> The keyword can be `s/` for start date or `e/` for end date.<br>
> Any trailing symbols after a valid keyword will be ignored.

### 2.9. Clearing all entries : `clear`

Clears all entries from the task manager.<br>
Format: `clear`

### 2.10. Viewing history of task : `history`

A list of history of tasks that have been completed will be shown in the column sorted in reverse-chronological order
Format: `history`

### 2.11. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.12. Saving the data

Task Manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

### 2.13. Change the directory of the task manager : `cd`

Load Task Manager data from specified file path and continue using that directory for operations.<br>
Create new file if no such file exists and cancel operations if given non-XML address or invalid XML file.<br>
Default file path is at data/taskmanager.xml.<br>
Format: `cd FILEPATH`

Examples:

* `cd /Users/admin/taskmanager.xml`<br>
  Changes the Storage Directory to /Users/admin/taskmanager.xml in UNIX/MAC system
* `cd data/new/cloudsync.xml`<br>
  Changes the Storage Directory to ./data/new/cloudsync.xml

### 2.14. Move the directory of the task manager : `mv`

Save Task Manager data to specified file path and continue using that directory for operations.<br>
If existing file with the same name exists, it will be overwritten with current Task Manager data.<br>
Default file path is at data/taskmanager.xml.<br>
Format: `mv FILEPATH`

Examples:

* `mv /Users/admin/taskmanager.xml`<br>
  Moves the Storage Directory to /Users/admin/taskmanager.xml in UNIX/MAC system
* `mv data/new/cloudsync.xml`<br>
  Moves the Storage Directory to ./data/new/cloudsync.xml

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manager folder.

## 4. Command Summary

Command | Syntax | Example
------- | ------ | -------
Help    | help
Add     | add TASK | add submit report
Add     | add TASK s/STARTING DATE | add submit report s/24/03/2017
Add     | add TASK s/STARTING DATE e/ENDING DATE    | add submit report s/24/03/207 e/27/03/2017
Add     | add TASK s/STARTING DATE e/ENDING DATE d/Description   | add submit report s/24/03/207 e/27/03/2017 d/assignment 1
Add     | add TASK s/STARTING DATE e/ENDING DATE d/Description #tags   | add submit report s/24/03/207 e/27/03/2017 d/assignment 1 #school
List    | list
Select  | select INDEX                    | select 3
Edit    | edit INDEX COMPONENT            | edit 3 e/04/04/2017
Find    | find KEYWORD(s)                 | find report assignment
Delete  | delete INDEX                    | delete 3
Sort    | sort KEYWORD                    | sort e/
Clear   | clear
History | history
Exit    | exit
<br>
