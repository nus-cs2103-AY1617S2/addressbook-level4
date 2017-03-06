# Dueue - User Guide

By : `Team CS2103JAN2017-W10-B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#1-quick-start)
2. [Features](#2-features)
3. [FAQ](#3-faq)
4. [Command Summary](#4-command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `dueue.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Dueue.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**`  add laundry due/every sunday #personal d/wash clothes @B1 *f` :
     adds a task to Dueue named `laundry` which is repeating `every sunday` under list `personal` with description `wash clothes` at venue `B1` and star it as `favourite`.
   * **`delete`**` 3` : deletes the task with index 3 as shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#2-features) section below for details of each command.<br>

## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 2.1. Viewing help : `help`

Format: `help [COMMAND]`

> * When there is no command specified, `help` will print help messages of all commands.
> * Help is also shown if you enter an incorrect command e.g. `abcd`

### 2.2. Adding a task: `add`

Adds a task to Dueue<br>
Format: `add n/TASKNAME [due/DUEDATE] [t/TIME] [#LISTNAME] [d/DESCRIPTION] [@VENUE] [p/PRIORITYLEVEL] [*f]`

> * Tasks cannot have multiple lists.
> * Tasks must be added with task name.
> * The required field for a task is TASKNAME, while other fields are optional.
> * By default, task will be added to current viewing list, unless [#LISTNAME] is specified.
> * Due dates of tasks can repeating, e.g. every sunday.
> * Time of each task can only be one time point, e.g. 4pm.
> * Specifications of fields for task can be entered in any order.
> * If no fields are entered, help message for add command will appear.
> * User can choose to replace the priority levels `IMPORTANT`, `NORMAL` and `TRIVIAL` by 3, 2 and 1.

Examples:

* `add n/laundry due/every sunday #personal d/wash clothes @B1 *f`<br>
  Add a task with taskname `laundry` due `every sunday` under the list `personal` with description `wash clothes` at the venue `B1` and mark it as favourite.
* `add n/2103lecture due/every friday t/4pm #study d/go for lecture p/3 @iCube`<br>
  Add a task with taskname `2103lecture` due `every friday` at time `4pm` under the list `study` with description `go for lecture` at the venue `iCube` with priority level `IMPORTANT`

### 2.3. Listing all tasks : `list`

Shows a list of all tasks in Dueue in a specific list and/or favourite and switch to that list and/or favourite<br>
Format: `list [favourite]`<br>
Format: `list list LISTINDEX [favourite]`<br>
Format: `list list LISTNAME [favourite]`

Shows all unfinished tasks in Dueue and unswitch<br>
Format: `list`

Shows all tasks in Dueue (finished and unfinished) and unswitch<br>
Format: `list all`

Shows a list of all list names (together with list indices) in Dueue and switch to list of lists<br>
Format: `list lists`

> * Do not need to specify whether the parameter is LISTINDEX or LISTNAME
> * Returns only favorite tasks by adding keyword "favorite"
> * Definition of **switch** : all the tasks added after switching will be automatically added to the current list (favourite) unless otherwise stated
> * Definition of **unswitch** : switch back to main, the tasks added will not be automatically added to this list (favourite)

Examples:

* `list`<br>
  Lists all unfinished tasks
* `list all`<br>
  Lists all unfinished and finished tasks
* `list work`<br>
  List all unfinished tasks in list `work`
* `list study favourite`<br>
  List all favourite tasks in list `study`
* `list lists`<br>
  List all listname with list indices

### 2.4. Adding a new list : `add list`

Add a new list in Dueue<br>
Format: `add list LISTNAME`

> * Listname cannot be the same as other existing lists
> * Listname cannot begin with numbers

Examples:

* `add list school`

### 2.5. Updating a list : `update list`

Update a list in Dueue with new name<br>
Format: `update list LISTNAME NEW_LISTNAME`
Format: `update list LISTINDEX NEW_LISTNAME`

> * Listname cannot be the same as other existing lists
> * Listname cannot begin with digits
> * Listname cannot contain whitespaces
> * Do not need to specify whether the parameter is LISTINDEX or LISTNAME

Examples:

* `update list school newSchool`<br>
  Update the name of list `school` to `newSchool`
* `update list oldList newList`<br>
  Update the name of list `oldList` to `newList`

### 2.6. Updating task(s) : `update`

Updates existing task(s) in Dueue<br>
Format: `update TASKINDEX ... [n/NAME] [due/DUEDATE] [t/TIME] [#LISTNAME] [d/DESCRIPTION] [@VENUE] [p/PRIORITYLEVEL] [FAVOURITE]`

> * Edits the task at the specified `INDEX`.
> * The index refers to the index number shown in the last listing.<br>
> * The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * You can remove details by typing nothing after the `/` or `@`without specifying any tags after it eg,`due/`.
> * To update multiple entries, indices must be seperated by whitespaces.
> * Specifications of fields for task can be entered in any order.

Examples:

* `update 1 Laundry due/every sunday @`<br>
  Updates the name, due date and the venue of the 1st task to be `Laundry`, `every sunday` and remove the venue respectively.

* `update 2 due/ &f`<br>
  Remove the due date of the task with `INDEX` 2 and make it not favourite.

### 2.7. View certain tasks: `view`

View tasks due on a specified date.<br>
Format: `view dueon/DATE`

View tasks due by a specified date.<br>
Format: `view dueby/DATE`

Views a list of all tasks in Dueue in a specific list and/or favourite and/or finished and unswitch<br>
Format: `view finished`<br>
Format: `view [favourite] [finished]`<br>
Format: `view list LISTINDEX [favourite] [finished]`<br>
Format: `view list LISTNAME [favourite] [finished]`

Views all tasks in Dueue (finished and unfinished) and unswitch<br>
Format: `list all`

> * The date has to be specified in a certain format.<br>
> * User will be able to see repeating tasks due on/by that date also.

Examples:

* `view dueon/today`<br>
  Returns a list of tasks due today
* `view dueby/next Friday`<br>
  Returns a list of tasks due by next Friday

### 2.8. Deleting task(s) or list(s) : `delete`

Deletes the specified task(s) from Dueue.<br>
Format: `delete TASKINDEX...`

Deletes the specified list(s) from Dueue.<br>
Format: `delete LISTINDEX...`

> * Deletes the task at the specified `TASKINDEX` or `LISTINDEX`. <br>
> * The index refers to the index number shown in the most recent listing.<br>
> * To delete multiple entries, indices must be seperated by whitespaces.
> * Each index **must be a positive integer** 1, 2, 3, ...
> * When a repeated task is deleted, user will need to confirm whether it should be deleted for once or deleted forever (stop repeating).

Examples:

* `delete 2`<br>
  Deletes the 2nd task/list in the most recent listing.
* `delete 1 2 3`<br>
  Deletes the 1st, 2nd and 3rd tasks/lists in the most recent listing.

### 2.9 Finishing task(s) : `finish`

Finishes the specified task(s) from Dueue.<br>
Format: `finish TASKINDEX...`

> * Finishes the task(s) at the specified `TASKINDEX`. <br>
> * The index refers to the index number shown in the most recent listing.<br>
> * To finish multiple tasks, indices must be seperated by whitespaces.
> * Each index **must be a positive integer** 1, 2, 3, ...
> * When a repeating task is finished, user will need to confirm whether it should be finished for once or finished forever (stop repeating).

### 2.10. Undo latest command: `undo`

Undo the immediately preceding command.<br>
Format: `undo`

> * Undo the latest mutating command, including `add`,`add list`, `delete`, `update` and `update list`.
> * Definition of **mutating** : making changes to the tasks/lists
> * Cannot undo more than once in succession.

Examples:

* `undo`<br>
  Latest command is reversed.

### 2.11. Reverse previous `undo` command: `redo`

Reverse the immediately preceding undo command.<br>
Format: `redo`

> * Reverse `undo` command if user made a mistake.
> * Can only redo right after an `undo` command.
> * Cannot redo more than once.

Examples:

* `redo`<br>
  Previous `undo` command is reversed.

### 2.12. Clearing all entries : `clear`

Clears all tasks from Dueue.<br>
Format: `clear`

> * User will be asked to confirm before clearing everything.

### 2.13. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.14. Saving the data

Dueue data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Dueue folder.

## 4. Command Summary

* **Add**  `add n/TASKNAME [due/DUEDATE] [t/TIME] [#LISTNAME] [d/DESCRIPTION] [@VENUE] [p/PRIORITYLEVEL] [FAVOURITE]` <br>
  e.g. `add n/laundry due/every sunday #personal d/wash clothes @B1 p/IMPORTANT *f`

* **Clear** : `clear`
  e.g. `clear`

* **Delete** : `delete TASKINDEX` <br>
   e.g. `delete 3`

* **Finish** : `finish TASKINDEX` <br>
   e.g. `finish 5`

* **Help** : `help` <br>
  e.g. `help`

* **List** : `list [lists/LISTINDEX/LISTNAME/finished/all] [favorite]` <br>
  e.g. `list work favorite`

* **Redo** : `redo` <br>
  e.g. `redo`

* **Undo** : `undo` <br>
  e.g. `undo`

* **Update** : `update TASKINDEX ... [n/NAME] [due/DUEDATE] [t/TIME] [#LISTNAME] [d/DESCRIPTION] [@VENUE] [p/PRIORITYLEVEL] [FAVOURITE]` <br>
  e.g.`update 1 Laundry due/every sunday @`

* **Update List** : `update list LISTNAME/LISTINDEX NEW_LISTNAME` <br>
  e.g. `update list school nus`

* **View** : `view DUETYPE/DUEDATE`<br>
  e.g. `view dueon/today`
