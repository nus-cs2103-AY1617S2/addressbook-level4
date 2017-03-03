# AddressBook Level 4 - User Guide

By : `Team SE-EDU`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jun 2016`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

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

> Help is also shown if you enter an incorrect command e.g. `hellpppppp!`

### 2.2. Adding a task: `add`

Adds a task to the task manager<br>
Format: `add TASK_NAME [s/START_DATE] [e/END_DATE] [d/DESCRIPTION] [p/PRIORITY_LEVEL] [t/TAG]...` <br>

> Task can have any number of tags (including 0)

Examples:

* `add Complete assignment s/13 Mar 2017 e/15 Mar 2017 p/3 t/2103 t/work`

### 2.3. Deleting a task : `delete`

Deletes the specified task from the task manager. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task manager.
* `find Assignment`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 2.4. Editing a task : `edit`

Edits an existing task in the task manager.<br>
Format: `edit INDEX [TASK_NAME] [s/START_DATE] [d/END_DATE] [d/DESCRIPTION] [p/PRIORITY_LEVEL] [t/TAG]...`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by typing `t/` without specifying any tags after it. 

Examples:

* `edit 1 p/2 t/`<br>
  Edits the priority level of task to be 2 and remove all its tags.

* `edit 2 e/16 Dec 2017 t/project t/2103 `<br>
  Edits the end date of task to 16 Dec 2017 and update tags to "project" and "2103".

### 2.5. Finding all tasks containing any keyword in their name or tags: `find`

Find tasks whose names or tags contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `work` will not match `Work`
> * The order of the keywords does not matter. e.g. `project work` will match `work project`
> * Only the name and tags are searched.
> * Only full words will be matched e.g. `Han` will not match `Hans`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Project` will match `Project Work`

Examples:

* `find Assignment`<br>
  Returns `Assignment` but not `assignment`
* `find Project Tutorial Assignment`<br>
  Returns Any task having names `Project`, `Tutorial`, or `Assignment`

### 2.6. Listing all tasks : `list`

Shows a list of all tasks in the task manager.<br>
Format: `list [e/END_DATE]`

> * If no end date is given, all task will be listed out.
> 
Examples:

* `list`<br>
  List all tasks.

* `list e/03 Mar 2017`<br>
  List out all the tasks that have specified end date.


### 2.7. View description : `view`

Exits the program.<br>
Format: `view INDEX`

Examples:

* `view 2`<br>
  View the 2nd task's description.

### 2.8. Saving the data

Task manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.


### 2.9. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Address Book folder.

## 4. Command Summary

* **Help**  `help` <br>
  
* **Add**  `add NAME [s/START_DATE] [e/END_DATE] [d/DESCRIPTION] [p/PRIORITY_LEVEL] [t/TAG]...` <br>
  
  e.g. `add Complete Assignment  e/02 Feb 2017 p/1 t/CS2103T`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 1`

 * **Edit**  `edit INDEX [TASK_NAME] [s/START_DATE] [e/END_DATE] [d/DESCRIPTION] [p/PRIORITY_LEVEL] [t/TAG]...` <br>
  
    e.g. `edit 1 d/bring pen and paper p/3`

 * **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. find Assignment Meeting Tutorial

* **Undo** : `undo` <br>

* **List** : `list` <br>

* **List** : `list [e/END_DATE]` <br>
  e.g. list 31 Dec 2017

* **View Description** : `view INDEX` <br>
   e.g. `view 1`
  
* **Exit** : `exit` <br>



