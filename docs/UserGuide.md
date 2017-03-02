# Dueue - User Guide

By : `Team SE-EDU`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

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
6. Refer to the [Features](#features) section below for details of each command.<br>

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

### 2.2. Adding a task: `add`

Adds a task to Dueue<br>
Format: `add [n/TASKNAME] [due/DUEDATE] [t/TIME] [#LISTNAME] [d/DESCRIPTION] [@VENUE] [p/PRIORITYLEVEL] [*f]`

> Tasks cannot have multiple lists<br>
> Due dates of tasks can repeating, e.g. every sunday<br>
> Time of each task can only be one time point, e.g. 4pm<br>
> Specifications of fields for task can be entered in any order<br>
> If no fields are entered, help message for add command will appear.

Examples:

* `add n/laundry due/every sunday #personal d/wash clothes @B1 *f`
* `add n/2103lecture due/every friday t/4pm #study d/go for lecture p/IMPORTANT @iCube`

### 2.3. Listing all tasks : `list`

Shows a list of all tasks in Dueue/ in a specific list<br>
Format: `list [favourite]`<br>
Format: `list list LISTINDEX [favourite]`<br>
Format: `list list LISTNAME [favourite]`

Shows a list of all list names in Dueue<br>
Format: Format: `list lists`

> Do not need to specify whether the parameter is LISTINDEX or LISTNAME<br>
> Returns only favorite tasks by adding keyword "favorite" 

Examples:

* `list`
* `list work`
* `list 1 favorite`
* `list lists`

### 2.4. Creating a list : `create`

Creates a new list in Dueue<br>
Format: `create LISTNAME`

> Listname cannot be the same as other existing lists<br>
> Listname cannot begin with digits

Examples:

* `create school`

### 2.5. Updating a list : `update list`

Update a list in Dueue with new name<br>
Format: `update list LISTNAME NEW_LISTNAME`
Format: `update list LISTINDEX NEW_LISTNAME`

> Listname cannot be the same as other existing lists<br>
> Listname cannot begin with digits
> Do not need to specify whether the parameter is LISTINDEX or LISTNAME<br>

Examples:

* `update list school newSchool`
* `update list 1 newList`

### 2.6. Updating task(s) : `update`

Updates existing task(s) in Dueue.<br>
Format: `update TASKINDEX ... [n/NAME] [due/DUEDATE] [t/TIME] [#LISTNAME] [d/DESCRIPTION] [@VENUE] [p/PRIORITYLEVEL] [FAVOURITE]`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last person listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * You can remove details by typing `` after the `/` or `@`without specifying any tags after it eg,`due/`.
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

> * The date has to be specified in a certain format.
> * User will be able to see repeating tasks due on/by that date also.

Examples:

* `view dueon/today`<br>
  Returns a list of tasks due today
* `view dueby/next Friday`<br>
  Returns a list of tasks due by next Friday

### 2.8. Deleting a person : `delete`

Deletes the specified person from the address book. Irreversible.<br>
Format: `delete INDEX`

> Deletes the person at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd person in the address book.
* `find Betsy`<br>
  `delete 1`<br>
  Deletes the 1st person in the results of the `find` command.

### 2.9. Switch to list mode : `switch`

Selects the person identified by the index number used in the last person listing.<br>
Format: `select INDEX`

> Selects the person and loads the Google search page the person at the specified `INDEX`.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `select 2`<br>
  Selects the 2nd person in the address book.
* `find Betsy` <br>
  `select 1`<br>
  Selects the 1st person in the results of the `find` command.

### 2.10. Clearing all entries : `clear`

Clears all entries from the address book.<br>
Format: `clear`

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

* **Add**  `add [n/TASKNAME] [due/DUEDATE] [#LISTNAME] [d/DESCRIPTION] [@VENUE] [FAVOURITE]` <br>
  e.g. `add n/laundry due/every sunday #personal d/wash clothes @B1 *f`

* **Clear** : `clear`

* **Delete** : `delete TASKINDEX` <br>
   e.g. `delete 3`

* **List** : `list [lists/LISTINDEX/LISTNAME] [favorite]` <br>
  e.g. `list work favorite`

* **Help** : `help` <br>
  e.g. `help`

* **Update List** : `udpate list LISTNAME/LISTINDEX NEW_LISTNAME` <br>
  e.g. `update list school nus`

* **Update** : `update TASKINDEX ... [n/NAME] [due/DUEDATE] [t/TIME] [#LISTNAME] [d/DESCRIPTION] [@VENUE] [p/PRIORITYLEVEL] [FAVOURITE]` <br>
  e.g.`update 1 Laundry due/every sunday @`
  
* **View** : `view DUETYPE/DUEDATE`<br>
  e.g. `view dueon/today`