# TaskCrusher - User Guide

By : `Team T15B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

1. [Introduction](#introduction)
2. [Quick Start](#quick-start)
3. [Features](#features)
4. [FAQ](#faq)
5. [Storage file format](#storage-file-format)

## 1. Introduction 
Welcome. We will take you for an enthralling journey as we bring forward our task manager `TaskCrusher`.

It can help you manage events, deadlines, add tasks with or without deadlines or view your history. This is a great manager if you are looking to move away from clicking as the simplified scheduling is just a few short and sweet command lines away.

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
   * **`adde`:**  `add "Cs2103 meeting" 3 Mar 17:00 to 18:00` adds an event called "Cs2103 meeting" on 3 March from 17:00 to 18:00
   * **`view`**: `view calendar` displays the calendar with tasks and events you have added

6. Refer to the [Features](#features) section below for details of each command.<br>

## 3. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items surrounding `|` means we only choose one of them (OR).
> * Items with `...` after them can have multiple instances.
> * Parameters should be in the order stated (at least for now).

### 3.1. Viewing help : `help`

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

### 3.2. Adding an item: `adde` or `addt`

Format (event):

`adde "event name" START_DATE START_TIME to [END_DATE] END_TIME [t/TAG]... [//DESCRIPTION]`<br>

Format (tentative event. blocks multiple time slots): 

`adde "event name" START_DATE START_TIME to [END_DATE] END_TIME or ... [t/TAG]... [//DESCRIPTION]`<br>

Format (task with deadline): 

`addt "task name" DEADLINE [t/TAG] [p/PRIORITY] [//DESCRIPTION]`  <br>

Format (task without deadline): 

`addt "task name" [t/TAG] [p/PRIORITY] [//DESCRIPTION] `<br>

Examples:

* `adde "Cs2103 meeting" 3 Mar 17:00 to 18:00`
* `addt "Buy tickets" 3 mar 17:00`
* `addt "Take GF for dinner"`

### 3.3 Viewing the active/expired list: `list`

**View the entire active list**: 

Format: `list`

**View tasks with/without deadline**: 

Format: `listd [DEADLINE] [SORT_FIELD]`

Example:

* `listd 3 March`

**View events within a time frame, or with no time frame specified** : 

Format: `liste [START_DATE START_TIME to END_DATE END TIME] [SORT_FIELD]`

Example:

* `liste today 17:00 to tomorrow 18:00`

**View overdue task** : 

Format: `listo`

**View tasks and events in the expired list**: 

Format: `listc`

NOTE:
> note that tasks and events have different fields, and therefore the parameter SORT_FIELD may vary between events and tasks.

### 3.4 Viewing other material: `view`

**View command history**: 

Format: `view history`

**View the calendar**: 

Format: `view calendar`

**View free time during the day/week/month** : 

Format: `view free day | week | month`

### 3.5 Updating a field of a task/event in the active list : `edit`

> User can edit the fields of the tasks and events in the active list by using their indexes specified in the most recent list. The steps are therefore as follows:

1. Use `list` command
2. Using the index, call `edit INDEX etc.`

**Updating the fields**:

Format: `edit INDEX FIELD_NAME NEW_VALUE`

Examples: 

`edit 1 priority 3`, assuming that the item with index 1 is a task.

### 3.6. Finding event or task by keyword: `find`

> Finds tasks or events whose field(s) contain any of the given keywords.<br>

Format: `find KEYWORD [MORE_KEYWORDS]`

Examples:

* `find cs2103`<br>
  Returns any task or event pertaining to cs2103

### 3.7. Deleting a task/event: `delete`
> Just like edit, user can delete tasks and events in the active list by using their indexes specified in the most recent list.

Format: `delete INDEX`

### 3.8. Clearing all expired tasks and events : `clear`

Format: `clear`

### 3.9. Exiting the program : `exit`

Format: `exit`

### 3.10 Undoing the last command: `undo`

Format: `undo`

### 3.11 Recycle 

Brings back the expired tasks/events back to active list, with the deadline/event date altered.The index is as shown by `listc` command.

Format : `Recycle INDEX DEADLINE | START_DATE START_TIME to [END_DATE] END_TIME`

### 3.12 Files

**Switch between files**: `switchf FILEPATH`
> if the file given by FILEPATH does not exist, `TaskCrusher` creates a new file

## 4. FAQ

**Q**: How do I create my own storage file to import into the app?<br>
**A**: Refer to [Storage file format](#storage-file-format) for instructions

**Q**: How do I import my own storage file into the app?<br>
**A**: Back up your current storage file as appropriate and overwrite it with the storage file you wish to use.

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous TaskCrusher installation.

## 6. Storage File Format

