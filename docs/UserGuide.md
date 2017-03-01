# AddressBook Level 4 - User Guide

By : `Team SE-EDU`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jun 2016`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---
1. [Introduction](#introduction)
2. [Quick Start](#quick-start)
3. [Features](#features)
4. [FAQ](#faq)
5. [Command Summary](#command-summary)

## 1. Introduction 
Welcome. We will take you for an enthralling journey as we bring forward our task manager "TASKCRUSHER"

It can help you manage events, deadlines, add tasks with or without deadlines or view your history. This is a great amanager if you are looking to move away from clicking as the simplified scheduling is just a few short and sweet command lines away.


## 2. Quick Start

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
6. Refer to the [Features](#features) section below for details of each command.<br>


## 3. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 2.1. Viewing help : `help`

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

### 2.2. Adding a item: `add`

Adds an event, task <br>
**Format(event)**: `add event[start datetime] to [end datetime] [t/TAG]...`<br>
**Format(task with deadline)**:`add task [end datetime]`<br>
**Format(task without deadline)**:`add task `<br>


Examples:

* `add Cs2103 meeting 3 Mar 17:00 to 18:00`
* `add Buy tickets 3 mar 17:00`
* `add Take GF for dinner`

### 2.3 View : `View`

View task with deadline or without deadlines, View tasks to be completed by specific deadline,
View list of events in time frame, View the overdue task, view last action, view done, view history 

**View task with deadline**: ` Viewd task`

Example 

* ` Viewd class Hw`

**View task without deadline**: `View task`

Example:
* `View Dinner`

**View task to be completed by deadline** :`View task by date`

Example:
* ` View class HW by 5 Mar`

**View list of event in time frame** :` View event [start time] to [End time]`

Example:
* `View Boss meeting 17:00 to 18:00`


**View overdue task** : `View over`


**View last action** : `View last`


**View completed task**: `View done`


**View history**: `view history`

**View the calendar**: `view calendar`

**View freetime** : `view free [daily/weekly/monthly]`





### 2.3. Update : `Update`

**Update by name**: `Update task/event name [New name]`

**Update by description**: `Update task/event desc [Description]`

**Update Event by location**: `Update event location [Location]`

**Update Event by time**: `Update event time [Time]`

**Update task by priority**:` Update task Priority [1...10]`

**Update task by deadline** :` Update task Deadline [New Date]`


### 2.4 Sorting : `Sort`

**Sort tasks** : `Sort taskname By Name/Priority/deadline`

**Sort events**: `Sort Eventname By Name/Start date`




### 2.5. Finding event or task by keyword: `find`

Finds task/Events whose names/decription contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`



Examples:

* `find cs2103`<br>
  Returns any task or event pertaining to cs2103

### 2.6. Deleting a task/event: `delete`


Format: `delete task/event name `

Examples:

* `delete cs2103 lab 2`<br>
  Deletes the above task.



### 2.7. Clearing all expired task/event : `clear`

Format: `clear`

### 2.8. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.9. Saving the data

Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

### 2.10 Recyle 

Brings back the expired events back to active list 

Format : `Recycle Task/event name`

### 2.11 Undo 

Undo the last command

Format : `Undo`

### 2.12 Files 

**Add a new file**: `New Filename`

**Switch between files**: `Goto Filename`





## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Address Book folder.

## 4. Command Summary


COMMAND | FORMAT 
--------|:--------
ADD EVENT|`add event[start datetime] to [end datetime] [t/TAG]...`
ADD TASK WITH DEADLINE |`add task [end datetime]`
ADD TASK WITHOUT DEADLINE|`add task `
VIEW TASK WITH DEADLINE|` Viewd task`
VIEW TASK WITHOUT DEADLINE|`View task`
VIEW TASK TO BE COMPLETED BY DEADLINE|`View task by date`
VIEW LIST OF EVENT IN TIME FRAME|` View event [start time] to [End time]`
VIEW OVERDUE TASK|`View over`
VIEW LAST ACTION|`View last`
VIEW COMPLETED TASKS|`View done`
VIEW HISTORY|`view history`
VIEW THE CALENDAR|`view calendar`
VIEW FREETIME|`view free [daily/weekly/monthly]`
UPDATE BY NAME|`Update task/event name [New name]`
UPDATE BY DESCRIPTION|`Update task/event desc [Description]`
UPDATE EVENT BY LOCATION|`Update event location [Location]`
UPDATE EVENT BY TIME|`Update event time [Time]`
UPDATE EVENT BY PRIOIRITY|` Update task Priority [1...10]`
UPDATE TASK BY DEADLINE|` Update task Deadline [New Date]`
SORT TASK|`Sort taskname By Name/Priority/deadline`
SORT EVENTS|`Sort Eventname By Name/Start date`
FIND TASK/EVENT|`find KEYWORD [MORE_KEYWORDS]`
DELETE TASK/EVENT|`delete task/event name `
CLEAR EXPIRED EVENTS|`clear`
MOVE EXPIRED EVENT TO ACTIVE LIST|`Recycle Task/event name`
UNDO|`Undo`
ADD A NEW FILENAME|`New Filename`
SWITCH BETWEEN FILES|`Goto Filename`
EXIT|`exit`
HELP|`help`





* **Add**  `add NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]...` <br>
  e.g. `add James Ho p/22224444 e/jamesho@gmail.com a/123, Clementi Rd, 1234665 t/friend t/colleague`

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


