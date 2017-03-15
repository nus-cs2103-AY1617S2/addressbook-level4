# TaskBoss - User Guide

By : `Team W14-B2`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [Command Summary](#command-summary)
 

## TaskBoss Prototype
<img src="images/TaskBoss (all tasks).png" width="600"> <br>

## 1. Quick Start
0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `TaskBoss.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Address Book.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**: `n/submit proposal p/high i/group project ed/tomorrow c/project`
     adds a task named `submit proposal` to the TaskBoss.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
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

### 2.2. Adding a task: `add`

Adds a task to the TaskBoss<br>
Format: `add n/TASK_NAME [i/INFO] [sd/START_DATE] [ed/END_DATE] [c/CATEGORY] [p/PRIORITY_LEVEL]`

> * Date can be written in any format whether using slashes, dashes, or natural language  <br>
> * Time should be in 24-hour clock format or 12 hour format with AM or PM next to it, i.e `hr:min <PM/AM>` <br>
> * Priority level is either `1` (low priority), `2` (medium priority) or `3` (high priority).
> * Order of optional parameters does not matter.
> * All fields are optional except TASK_NAME.

Examples:

* `add n/Buy groceries ed/19-02-2017 c/Home p/3`
* `add n/Dinner with Jim i/@Orchard v0.0 sd/next friday ed/19-02-2017 c/Leisure p/2`
* `add n/Post-exam celebration i/@Zouk sd/tomorrow at 3 PM ed/tomorrow 20.30  c/Leisure p/1`


### 2.3. Listing all tasks : `list`

Shows a list of all tasks in the TaskBoss.<br>
Format: `list`

### 2.4. Editing a task : `edit`

Edits an existing task in the TaskBoss.<br>
Format: `edit INDEX [i/INFO] [sd/START_DATE] [ed/END_DATE] [c/CATEGORY] [p/PRIORITY_LEVEL]`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * Order of the optional parameters does not matter.

Examples:

* `edit 1 i/Use Stack ed/23:59`<br>
  Edits the task information and end time of the 1st task to be `Use Stack` and `23:59` respectively.
  
* `edit 3 p/3`<br>
  Edits the priority level of 3rd task to be 3. 

### 2.5. Finding all tasks by Name: `find`

Finds tasks whose names contain any of the given keywords.<br>
Format: `find n/TASK_NAME` 

> * The search is case-sensitive. e.g `Project` will not match `project`
> * The order of the keywords does not matter. e.g. `meeting project` will match `project meeting`
> * The name/date/information is searched according to the command user enters.
> * Only full words will be matched e.g. `meeting` will not match `meetings`

Examples:

* `find n/Meeting`<br>
  Returns all tasks whose name contains `Meeting`
  
* `find ed/04-02-2017`<br>
  Returns all tasks with the end day `04-02-2017`

### 2.5. Finding all tasks by Deadline: `find`

Finds tasks whose deadlines contain any of the given keywords.<br>
Format: `find sd/date and time` `find ed/date and time`

> * The date is searched according to the date format user enters.
> * The date format for find date command is restricted either DD-MM-YYYY or DD/MM/YY formats 
or MM-DD-YYYY or MM/DD/YYYY, depending on your system's settings.

Examples:

* `find sd/04-02-2017`<br>
  Returns all tasks with the start date `04-02-2017`
  
* `find ed/05-02-2017`<br>
  Returns all tasks with the end date `05-02-2017`
  
### 2.6. Deleting a task : `delete`

Deletes the specified task from the TaskBoss.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the TaskBoss.
  
* `find n/Meeting`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 2.7. Creating a category : `new`

Creates a new category in TaskBoss.<br>
Format: `new CATEGORY`

Example:

* `new Study`<br>

### 2.8. Clearing tasks by category : `clear`

Clears all tasks under the specified category from TaskBoss.<br>
Format: `clear CATEGORY`

> The default categories of TaskBoss are `All Tasks` and `Done`. <br>

### 2.9. Viewing a task : `view`

Views a task by entering the task index from TaskBoss.<br>
Format: `view INDEX`

> Views the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

### 2.10. Modifying a category name : `name`

Modifies a category name in TaskBoss.<br>
Format: `name EXISTING_CATEGORY NEW_CATEGORY`

Example:

* `name School ModuleStudy`<br>

### 2.11. Listing tasks by category : `listcategory`

Lists all tasks under a specified category.<br>
Format: `listcategory CATEGORY`

Example:

* `listcategory School`<br>

### 2.12. Marking a task done : `done`

Marks a task done in TaskBoss.<br>
Format: `done INDEX`

> Marks the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

### 2.13. Undoing a command : `undo`

Undoes a most recent command.<br>
Format: `undo`

### 2.14. Sorting tasks by deadline : `sort`

Sorts tasks in TaskBoss by their deadlines.<br>
Format: `sort`

> Tasks are sorted by nearest end date and time

### 2.15. Sorting tasks by priority : `sort p`

Sorts tasks in the TaskBoss by their priorities.<br>
Format: `sort p`

> Tasks are sorted by descending order of priority levels

### 2.16. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.17. Saving the data 

TaskBoss data will automatically be saved in local hard disk after any command that mutates the data.<br>
There is no need to save manually.

### 2.18. Exporting data to an existing file : `save e/`

Format: `save e/FILE_PATH`

### 2.19. Exporting the data to a new file : `save n/`

Format: `save n/FILE_PATH NEW_FILE_NAME`

## 3. Command Summary

* **Add**  `add n/TASK_NAME [i/INFO] [sd/START_DATE] [ed/END_DATE] [c/CATEGORY] [p/PRIORITY_LEVEL]` <br>
  e.g. `add n/Post-exam celebration i/@Zouk sd/10-03-2017 ed/11-03-2017 c/Leisure p/1`

* **Clear by Category** : `clear CATEGORY`
  e.g. `clear School`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Find by name** : `find n/TASK_NAME` <br>
  e.g. `find n/Meeting`

* **Find by end date** : `find sd/END_DATE` <br>
  e.g. `find d/03-01-2017`

* **Find by task information** : `find i/INFORMATION` <br>
  e.g. `find i/Meeting room 3`
  
* **Create category** : `new CATEGORY` <br>

* **Edit category** : `name EXISTING_CATEGORY NEW_CATEGORY` <br>

* **List** : `list` <br>

* **List by category** : `listcategory` <br>
  e.g. `listcategory study`

* **Help** : `help` <br>

* **Edit** : `edit INDEX [i/INFO] [sd/START_DATE] [ed/END_DATE] [c/CATEGORY] [p/PRIORITY_LEVEL]` <br>
  e.g.`edit 1 i/Use Stack et/23:59`

* **Undo** : `undo` <br>

* **Sort by deadline** : `sort` <br>

* **Sort by priority** : `sort p` <br>

* **Mark done** : `done INDEX` <br>

* **View** : `view INDEX` <br>

* **Export to a existing file** : `save e/FILE_PATH` <br>

* **Export to a new file** : `save n/FILE_PATH NEW_FILE_NAME` <br>







