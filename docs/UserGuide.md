# TaskCrusher - User Guide

By : `Team T15B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

1. [Introduction](#introduction)
2. [Quick Start](#quick-start)
3. [Features](#features)
4. [Command Summary](#command-summary)
5. [FAQ](#faq)
6. [Storage file format](#storage-file-format)

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
   * **`add e`:**  `add e Cs2103 meeting 3 Mar 17:00 to 18:00` adds an event named "Cs2103 meeting" on 3 March from 17:00 to 18:00
6. Refer to the [Features](#features) section below for details of each command.<br>

## 3. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items surrounding `|` means we only choose one of them (OR).
> * Items with `...` after them can have multiple instances.
> * Parameters with a prefix can be entered in any order.

### 3.1. Viewing help : `help`

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

### 3.2. Adding an item: `add e` or `add t`

> * DATE format: DD-MM-YY
> * TIME format: 00:00 (24 hour)
> * User will be prompted for events that clash

**Add event**:

Format: `add e EVENT_NAME d/START_DATE START_TIME to [END_DATE] END_TIME [t/TAG]... [//DESCRIPTION]`<br>

**Add tentative event, blocks multiple time slots**:

Format: `add e EVENT_NAME d/START_DATE START_TIME to [END_DATE] END_TIME or [dt/START_DATE] ... [t/TAG]... [//DESCRIPTION]`<br>

**Add task with deadline**:

Format: `add t TASK_NAME d/DEADLINE [t/TAG] [p/PRIORITY] [//DESCRIPTION]`<br>

**Add task without deadline**:

Format: `add t TASK_NAME [t/TAG] [p/PRIORITY] [//DESCRIPTION] `<br>

Examples:

* `add e CS2103 Meeting d/03-03-17 17:00 to 19:00 t/school`
* `add t Buy tickets d/03-05-17 //ensure it's front row`
* `add t Go out for dinner`

### 3.3 Viewing the active/expired list: `list`

**View the entire active list**:

Format: `list`<br>

**View active task list**:

Format: `list t`<br>

**View active event list**:

Format: `list e`<br>

**View active list within specific time frame**:

> All tasks and events that have times that overlap in part or whole with this time frame will be displayed

Format: `list d/START_DATE START_TIME to [END_DATE] END_TIME`<br>

**View active task or event list within specific time frame**:

Format: `list [t | e] d/START_DATE START_TIME to [END_DATE] END_TIME`<br>

**View overdue tasks** :

Format: `list o`<br>

**View tasks and events in the expired list**:

Format: `list c`<br>

Examples:

* `list c`
* `list e d/5-5-18 00:00 to 15:00`
* `list t d/8-7-17 16:00`

### 3.4 Updating a field of a task/event in the active list : `edit`

> User can edit the fields of the tasks and events in the active list by using their indexes specified in the most recent list. The steps are therefore as follows:

1. Use `list` command
2. Using the index, call `edit INDEX etc.`

**Updating the fields**:

> Use the appropriate prefix for the field

Format: `edit INDEX [NEW_NAME] prefix/NEW_VALUE [prefix/NEW_VALUE]...`<br>

Examples:

`edit 1 Read Harry Potter p/3`, assuming that the item with index 1 is a task.

### 3.5 Mark as `done` tasks and events:

> Just like the `edit` command, the user makes use of the index provided by the `list`-type commands. Either way, the task/event gets stored in the expired list.

**Marking a task or event as done**:

Format: `done e | t INDEX`<br>

Example:

`done e 2`

### 3.6. Finding event or task by keyword: `find`

> Finds tasks or events whose field(s) contain any of the given keywords.<br>

Format: `find KEYWORD [MORE_KEYWORDS]`<br>

Examples:

* `find cs2103`<br>
  Returns any task or event pertaining to cs2103

### 3.7. Deleting a task/event without moving it to the expired list: `delete`

> Just like edit, user can delete tasks and events in the active list by using their indexes specified in the most recent list. This option can also be used for tasks or events already in the expired list from which the user wants to get rid of.

Format: `delete INDEX`<br>

### 3.8. Clearing all expired tasks and events : `clear`

Format: `clear`<br>

### 3.9 Exiting the program : `exit`

Format: `exit`<br>

### 3.10 Undoing the last command: `undo`

Format: `undo`<br>

### 3.11 Recycle

Brings back the expired tasks/events back to active list, with the deadline/event date altered. The index is as shown by `list c` command.

Format : `recycle INDEX DEADLINE | START_DATE START_TIME to [END_DATE] END_TIME`<br>

### 3.12 Changing storage files : `load`

Format: `load FILEPATH`<br>

> If the file given by FILEPATH does not exist, `TaskCrusher` creates a new file

### 3.13 History

**View entire command history for the current session**:

> Includes invalid commands

Format: `history`<br>

## 4. Command Summary

* **Help** : `help`<br>

* **Add event** : `add e event name d/START_DATE START_TIME to [END_DATE] END_TIME [or d/START_DATE...] ... [t/TAG]... [//DESCRIPTION]`<br>

* **Add task** : `add t task name [d/DEADLINE] [t/TAG] [p/PRIORITY] [//DESCRIPTION]`<br>

* **List active tasks/events/all** : `list [t or e] d/START_DATE START_TIME to [END_DATE] END_TIME`<br>

* **List overdue** : `list o`<br>

* **List completed** : `list c`<br>

* **Edit** : `edit INDEX [NEW_NAME] prefix/NEW_VALUE [prefix/NEW_VALUE]...`<br>

* **Done** : `done INDEX`<br>

* **Find** : `find KEYWORD [MORE_KEYWORDS]`<br>

* **Delete** : `delete INDEX`<br>

* **Clear** : `clear`<br>

* **Exit** : `exit`<br>

* **Undo** : `undo`<br>

* **Recycle** : `recycle INDEX DEADLINE | START_DATE START_TIME to [END_DATE] END_TIME`<br>

## 5. FAQ

**Q**: How do I create my own storage file to import into the app?<br>
**A**: Refer to [Storage file format](#storage-file-format) for instructions

**Q**: How do I import my own storage file into the app?<br>
**A**: Back up your current storage file as appropriate and overwrite it with the storage file you wish to use.

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous TaskCrusher installation.

## 6. Storage File Format
- User inbox data is stored in `.xml` format
- User configurations are stored in `.json` format
