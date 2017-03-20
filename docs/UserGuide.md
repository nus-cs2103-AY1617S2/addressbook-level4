# Task Manager - User Guide

By : `Team W14-B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `February 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#1-quick-start)
2. [Features](#2-features)
3. [FAQ](#3-faq)
4. [Command Summary](#4-command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `addressbook.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Address Book.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all contacts
   * **`add`**` John Doe p/98765432 e/johnd@gmail.com a/John street, block 123, #01-01` :
     adds a contact named `John Doe` to the Address Book.
   * **`delete`**` 3` : deletes the 3rd contact shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#2-features) section below for details of each command.<br>


## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items in `CURLY_BRACKETS` are exclusive, only one of them can be used.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 2.1. Viewing help: help

Format: help

> Help is also shown after an incorrect command

### 2.2. Adding a task: add

Adds a task to the task manager<br>
Format: add TASK_DETAILS [priority/ PRIORITY_LEVEL] [comment/ COMMENTS] [tag/ TAG]...

> Add an event<br>
> Add a task with deadline<br>
> Add a floating task

TASK_DETAILS include task name, date and time, which can be written in any order as it is processed by an inbuilt natural language processor<br>
PRIORITY_LEVEL: high, medium, low
Each task can have any number of tags (including 0)

Example:

* Add Orientation camp from 22 Feb 9am to 27 Feb 9pm
* Add Tetris AI Project due by 30th March priority/high tag/cs3243 project
* Add Revision for cs3243 priority/ medium comment/ find people to study with tag/ cs3243

### 2.3. Deleting a task: delete

Deletes a task from the task manager<br>
Format: delete INDEX

> Deletes a task at the specified INDEX. The index refers to the index number shown in the most recent listing.

Example:
* list<br>delete 2

> Deletes 2nd task from the task manager

* find tutorial<br>
delete 1

> Deletes 1st task in the results of the find command

### 2.4. Completing a task: done

Marks a tasks as completed<br>
Format: done INDEX

Example:
* list<br>
done 2

> Completes 2nd task from the task manager

* find tutorial<br>
done 1

> Completes 1st task in the results of the find command

### 2.5. Modifies a current task: edit

Edits a task of the following index, starting date or priority level if given from the task manager<br>
Format: edit INDEX TASK_DETAILS [priority/ PRIORITY_LEVEL] [comment/ COMMENTS] [tag/ TAG]...

Example:
* find homework<br>
edit 2 cs3243 homework due 3 March 11.59pm priority/ high

### 2.6. Finding all tasks including any keyword: find
Finds all task with the following keywords<br>
Format: find KEYWORD...

> Search is case insensitive, the order of keywords does not matter.<br>
> Tasks containing all keywords will be returned

Example:
* find cs2103
> Returns all tasks with cs2103 in their name or as a tag

* find cs2103 tutorial
> Returns all tasks with cs2103 and tutorial in their name or tasks with both cs2103 and tutorial as tags

### 2.7. Listing all tasks: list
Shows a list of all tasks or groups of tasks<br>
Format: list

> List shows uncompleted tasks first before completed tasks

### 2.8. Sorting the tasks: sort
Sorts command based on task name, date, priority level.<br>
Format: sort {TASK_DETAILS}{DATE}{PRIORITY}
>> Only 1 of 3 parameters can be used

> n: Sorts based on task name in alphabetical order<br>
> d: Sorts based on date starting with the earliest date<br>
> p: Sorts based on priority assigned starting with the highest priority <br>

Example:
* sort p<br>

> Displays all tasks sorted according to priority

### 2.9. Reversing previous action: undo
Undo the last change made to the task manager<br>
Format: undo

Example:
* delete 1<br>
undo

> Delete action will be reversed

### 2.10. Clearing entries: clear
Clear all task or groups of task from the task manager<br>
Format: clear [TAG]...

> Without parameters, clear will delete all tasks from the task manager<br>
> With parameters, clear will only delete tasks containing all of the tags from the task manager

Example:
* clear completed
* clear completed 2103

### 2.11. Saving the program data to a specified path: save
Save FlexiTask list to a file specified by user<br>
Format: save FILE_PATH/FILE_NAME.xml 

> File extension must be xml<br>
> If specified file does not exists, FlexiTask will create the file.

Example:
* save data/mytasklist.xml

### 2.12. Loading the program data from a specified path: load
Load FlexiTask list from file specified by user<br>
Format: load FILE_PATH/FILE_NAME.xml 

> File extension must be xml<br>
> File specified must exist

Example:
* load users/user/Documents/mytasklist.xml

### 2.13. Exiting the program: exit
Exits the program<br>
Format: exit

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manager folder.

## 4. Command Summary

* **Add** : `add TASK_DETAILS [priority/ PRIORITY_LEVEL] [comment/ COMMENTS] [tag/ TAG]...` <br>
	e.g. `add 2103 Lecture from 4-6 comment/ICube tag/2103`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Done** : `done INDEX` <br>
	e.g. `done 2`

* **Edit** : `edit INDEX TASK_DETAILS [priority/ PRIORITY_LEVEL] [comment/ COMMENTS] [tag/ TAG]...`<br>
	e.g. `edit 1 2103 Lecture from 2-4 priority/high comment/ICube tag/2103

* **Find** : `find KEYWORD...` <br>
 	e.g. `find James Jake`

* **List** : `list` <br>
	e.g. `list`

* **Help** : `help` <br>
  	e.g. `help`

* **Sort** : `sort {TASK_DETAILS}{DATE}{PRIORITY}` <br>
  	e.g.`sort n`

* **Undo** : `undo` <br>
	e.g. `undo`

* **Clear** : `clear [TAG]...`<br>
	e.g. `clear completed 2103

* **Save** : `save FILE_PATH/FILE_NAME.xml`<br>
e.g. `save data/mytasklist.xml

* **Load** : `load FILE_PATH/FILE_NAME.xml`<br>
e.g. `load users/user/Documents/mytasklist.xml

* **Exit** : `exit`<br>
	e.g. `exit`
