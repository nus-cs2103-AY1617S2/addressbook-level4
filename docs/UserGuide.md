# TaskCrusher - User Guide

By : `Team T15B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

1. [Introduction](#introduction)
2. [Quick Start](#quick-start)
3. [Features](#features)
4. [FAQ](#faq)
5. [Command Summary](#command-summary)
6. [Storage file format](#storage-file-format)

## 1. Introduction 
Welcome. We will take you for an enthralling journey as we bring forward our task manager `TaskCrusher`.

It can help you manage events, deadlines, add tasks with or without deadlines or view your history. This is a great amanager if you are looking to move away from clicking as the simplified scheduling is just a few short and sweet command lines away.


## 2. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `TaskCrusher.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for TaskCrusher.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`help`** : displays help documentation
   * **`add`:** `add "Cs2103 meeting" 3 Mar 17:00 to 18:00` : adds an event called "Cs 2103 meeting" on 3 March from 17:00 to 18:00
   * **`view`** `view calendar` : displays calendar
   * **`update`** update 1 Priority 1 : updates task #1 in previous search results to priority 1
6. Refer to the [Features](#features) section below for details of each command.<br>


## 3. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items surrounding `|` means we only choose one of them (OR).
> * Items with `...` after them can have multiple instances.
> * Parameters should be in the order stated. 

### 3.1. Viewing help : `help`

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

### 3.2. Adding an item: `add`

**Format** (event): 

`add "event name" START_DATE START_TIME to [END_DATE] END_TIME [t/TAG]... [//DESCRIPTION]`<br>

**Format** (tentative event. blocks multiple time slots): 

`add "event name" START_DATE START_TIME to [END_DATE] END_TIME ... [t/TAG]... [//DESCRIPTION]`<br>

**Format** (task with deadline): 

`add "task name" DEADLINE [t/TAG] [p/PRIORITY] [//DESCRIPTION]`  <br>

**Format** (task without deadline): 

`add "task name" [t/TAG] [p/PRIORITY] [//DESCRIPTION] `<br>


Examples:

* `add "Cs2103 meeting" 3 Mar 17:00 to 18:00`
* `add "Buy tickets" 3 mar 17:00`
* `add "Take GF for dinner"`

### 3.3 Viewing the active/expired list: `list`

**View the entire active list**: 

`list`

**View tasks with/without deadline**: 

`listd [DEADLINE]`

Example:

* `listd 3 March`

**View events within a time frame, or with no time frame specified** : 

`liste [START_DATE START_TIME to END_DATE END TIME]`

Example:
* `liste today 17:00 to tomorrow 18:00`

**View overdue task** : 

`listo`

**View completed task and events in the expired list**: 

`listc`

### 3.4 Viewing other material: `view`

**View command history**: 

`view history`

**View the calendar**: 

`view calendar`

**View free time during the day/week/month** : 

`view free day | week | month`

### 3.5 Updating a field of a task/event in the active list : `edit`
> User can edit the fields of the tasks and events in the active list by using their indexes specified in the most recent list. The steps are therefore as follows:

1. Use `list` command
2. Using the index, call `edit INDEX etc.`

**Updating the fields**:

`edit INDEX FIELD_NAME NEW_VALUE`

e.g.

`edit 1 priority 3`, assuming that the item with index 1 is a task.

### 3.4 Sorting : `Sort`

**Sort tasks** : `Sort taskname By Name/Priority/deadline`

**Sort events**: `Sort Eventname By Name/Start date`

### 3.5. Finding event or task by keyword: `find`

Finds task/Events whose names/decription contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`



Examples:

* `find cs2103`<br>
  Returns any task or event pertaining to cs2103

### 3.6. Deleting a task/event: `delete`


Format: `delete task/event name `

Examples:

* `delete cs2103 lab 2`<br>
  Deletes the above task.

### 3.7. Clearing all expired task/event : `clear`

Format: `clear`

### 3.8. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 3.9. Saving the data

Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

### 3.10 Recyle 

Brings back the expired events back to active list 

Format : `Recycle Task/event name`

### 3.11 Undo 

Undo the last command

Format : `Undo`

### 3.12 Files 

**Add a new file**: `New Filename`

**Switch between files**: `Goto Filename`

## 4. FAQ

**Q**: How do I create my own storage file to import into the app?<br>
**A**: Refer to [Storage file format](#storage-file-format) for instructions

**Q**: How do I import my own storage file into the app?<br>
**A**: Back up your current storage file as appropriate and overwrite it with the storage file you wish to use.

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous TaskCrusher installation.

## 5. Command Summary


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

## 6. Storage File Format

