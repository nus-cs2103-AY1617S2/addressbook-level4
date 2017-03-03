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

> **Common Options**
> * `PREFIX_INDEX`
> -- Refers to the index number shown in the most recent listing.
> -- Comprises of a category prefix (`e`, `d`, or `t`, for `Event`, `Deadline` and `To-Do` categories) and category index (a positive integer, e.g. `1`, `2`, `3`..)
> 
> * `START_DATE`
> -- Represents start date and time entered
> -- Defaults to time of 0000 hrs if no time is indicated
>
> * `END_DATE`
> -- Represents end date and time entered
> -- Defaults to time of 2359 hrs if no time is indicated 

### 2.1. Viewing help : `help`

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `hellpppppp!`

### 2.2. Adding a task: `add`

Adds a task to the task manager<br>
Format: `add NAME [s/START_DATE] [e/END_DATE] [t/TAG]...` <br>

> * Task can have any number of tags
> * Tasks cannot have `START_DATE` without `END_DATE`
> * A Task with both `START_DATE` and `END_DATE` is an `Event`
> * A Task with only `END_DATE` is a `Deadline`
> * A Task with no `START_DATE` and no `END_DATE` is a `To-Do`

Examples:

* `add Exercise s/tomorrow 0900 e/tomorrow 1330 t/workout t/daily t/habits`

### 2.3. Deleting a task : `delete`

Deletes the specified task from the task manager.<br>
Format: `delete PREFIX_INDEX`

>* Deletes the task at the specified `PREFIX_INDEX`. <br>

Examples:

* `list`<br>
  `delete e2`<br>
  Deletes the 2nd task under `EVENT` category in the task manager.
* `find Assignment`<br>
  `delete t1`<br>
  Deletes the 1st task under `To-Do` category in the task manager.

### 2.4. Editing a task : `edit`

Edits an existing task in the task manager.<br>
Format: `edit PREFIX_INDEX [TASK_NAME] [s/START_DATE] [e/END_DATE] [t/TAG]...`

> * Edits the task at the specified `PREFIX_INDEX`.
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by typing `t/` without specifying any tags after it. 

Examples:

* `edit d1 t/`<br>
  Edits the 1st task in `DEADLINE` category to remove all its tags.

* `edit t2 e/16 Dec 2017 t/project t/2103 `<br>
  Edits the 2nd task in `To-Do` category to update `END_DATE` of task to 16 Dec 2017 and update tags to "project" and "2103".


### 2.5. Delete all tasks: `Clear`
Delete all tasks in the to-do list.<br>
Format: `clear`

### 2.6. Finding all tasks containing a keyword in their name or tag: `find`

Find tasks which have names and/or tags containing any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case-insensitive. e.g `work` will match `Work`
> * The order of the keywords does not matter. e.g. `project work` will match `work project`
> * Only the name and/or tag are searched.
> * Only full words will be matched e.g. `Han` will not match `Hans`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Project` will match `Project Work`

Examples:

* `find Project Tutorial Assignment`<br>
  Returns Any task having names or tags containing `Project`, `Tutorial`, or `Assignment`


### 2.7. Listing all tasks : `list`

Shows a list of all tasks in the task manager.<br>
Format: `list CATEGORY`

> * If no input is given, all uncompleted tasks will be listed out.
> * `CATEGORY` can represent `END_DATE`, `TAG`, or natural language categories like `completed` or `uncompleted`.

Examples:

* `list`<br>
  List all uncompleted tasks.

* `list 03 Mar 2017`<br>
  List out all the tasks that has specified `END_DATE` in `DEADLINE` category and has date that falls within `EVENT` category.

### 2.8. Marking a task as completed: `done`
Marks a task as completed.<br>
Format: `done PREFIX_INDEX`
> * Marks a task at the specified PREFIX_INDEX as done


Examples:

* `done e1`<br>
  Mark event 1 as completed.

### 2.9. Marking a task as uncompleted: `undone`
Marks a task as uncompleted.<br>
Format: `undone PREFIX_INDEX`
> * Marks a task at the specified PREFIX_INDEX as not done

Examples:

* `undone e1`<br>
  Mark event 1 as uncompleted.

### 2.10. Undoing actions: `undo`
Undo actions taken previously.<br>
Format: `undo`

### 2.11. Redoing actions: `redo`
Redo actions undone previously.<br>
Format: `redo`

### 2.12. Saving the data: `save`

By default, to-do list data are saved in a file called `todo.txt` in the data folder. You can 
change the file location by provideing a new file name as the parameter.
Note that changes made to the to-do list is automatically saved. You do not need to manually save it each time. 

Format: `save NEW_FILE_NAME`

Examples:

* `save newFile.txt`<br>
  Save the to-do list in a new file entitled newFile.txt. 

### 2.13. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Address Book folder.

## 4. Command Summary

* **Help**  `help` <br>


* **Add**  `add NAME [s/START_DATE] [e/END_DATE] [t/TAG]...` <br>
  
  e.g. `add Complete Assignment  e/02 Feb 2017 t/CS2103T`


* **Delete** : `delete PREFIX_INDEX` <br>
   e.g. `delete e2`


* **Edit**  `edit PREFIX_INDEX [NAME] [s/START_DATE] [e/END_DATE] [t/TAG]...`<br>
  
    e.g. `edit d1 e/31 Dec 2017`

* **Clear** : `clear` <br>

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find Assignment Meeting Tutorial`


* **List** : `list CATEGORY` <br>
  e.g. `list 31 Dec 2017`


* **Done** : `done RPEFIX_INDEX` <br>
  e.g. `done d2`
  
* **Undone** : `undone RPEFIX_INDEX` <br>
  e.g. `undone t3`

* **Undo** : `undo` <br>

* **Redo** : `redo` <br>

* **Save** : `save` <br>

* **Exit** : `exit` <br>
