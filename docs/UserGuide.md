# TaskBoss - User Guide

By : `Team W14-B2`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Introduction](#0-introduction)
2. [Quick Start](#1-quick-start)
3. [Features](#2-features)
4. [FAQ](#3-faq)
5. [Command Summary](#4-command-summary)
 

## TaskBoss Prototype
<img src="images/TaskBoss (all tasks).png" width="600"> <br>

## 1. Introduction
Have you ever been overwhelmed with too many tasks? Perhaps a couple of these tasks might have slipped your mind. Well, TaskBoss is here to help you out! TaskBoss is a user friendly task manager which supports a wide range of features. Be the boss of your tasks, use TaskBoss today!

## 2. Quick Start
1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   Download the latest Java version if you are unable to run TaskBoss.<br>
   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

2. Install the latest `TaskBoss.jar` from the [releases](../../../releases) tab.
3. Copy the file to the folder you want to use as the home folder for TaskBoss.
4. Double-click the file to start the app. The GUI should appear in a few seconds.
5. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
   Here are some commands you can try:
   * **`list`** : lists all tasks
   * **`add`**: `n/submit proposal p/high i/group project ed/tomorrow c/project`
     adds a task named `submit proposal` to the TaskBoss
   * **`delete`**` 3` : deletes the third task shown in the current list
   * **`exit`** : exits the app <br>


## 3. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

<br>

|Command    |Description                                                          |
|:---------:|:-------------------------------------------------------------------:|
|[**help**](#31-viewing-help--help)                       |View help              |
|[**add**](#32-adding-a-task-add)                         |Add a task             |
|[**list**](#33-listing-all-tasks--list)                  |List the tasks         |
|[**edit**](#34-editing-a-task--edit)                     |Edit a task            |
|[**find**](#35-finding-all-tasks-by-name-or-by-datetime--find)           |Find tasks by name or by datetime             |
|[**delete**](#36-deleting-a-task--delete)                |Delete a task          |
|[**new**](#37-creating-a-category--new)               |Create a category      |
|[**clear**](#38-clearing-tasks-by-category--clear)       |Clear tasks in category|
|[**view**](#39-viewing-a-task--view)                    |View a task            |
|[**name**](#310-modifying-a-category-name--name)         |Rename a category      |
|[**done**](#311-marking-a-task-done--done)               |Mark a task as done      |
|[**undo**](#312-undoing-a-command--undo)                 |Undo a task            |
|[**sort**](#313-sorting-tasks--sort)                     |Sort tasks by deadline or by priority|
|[**save**](#315-exporting-the-data--save)                |Save TaskBoss          |
|[**exit**](#316-exiting-the-program--exit)               |Exit TaskBoss          |

<br>

### 3.1. Viewing help : `help`

Format: `help`

### 3.2. Adding a task: `add`

Adds a task<br>
Format: `add n/TASK_NAME [i/INFO] [sd/START_DATE] [ed/END_DATE] [c/CATEGORY] [p/PRIORITY_LEVEL]`

> * Date can be written in any format whether using slashes, dashes, or natural language.  <br>
> * Time should be in 24-hour clock format or 12-hour format with AM or PM next to it, i.e `1700 hr:min <PM/AM>`. <br>
> * Priority level is either `1` (low priority), `2` (medium priority) or `3` (high priority).
> * Order of optional parameters does not matter.
> * All fields are optional except TASK_NAME
> * All field are case-sensitive

Examples:

* `add n/Buy groceries ed/19-02-2017 c/Home p/3`
* `add n/Dinner with Jim i/@Orchard sd/next friday ed/19-02-2017 c/Leisure p/2`
* `add n/Post-exam celebration i/@Zouk sd/tomorrow at 3 PM ed/tomorrow 20.30  c/Leisure p/1`


### 3.3. Listing all tasks : `list`

Shows a list of all tasks<br>
Format: `list` 

Shows a list of tasks under a specified category <br>
Format: `list c/CATEGORY NAME` 

Example:
* `list c/Project`<br>


### 3.4. Editing a task : `edit`

Edits an existing task<br>
Format: `edit INDEX [i/INFO] [sd/START_DATE] [ed/END_DATE] [c/CATEGORY] [p/PRIORITY_LEVEL]`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number last shown in the last task listing.<br>
    The index **must be a positive integer** (*e.g. 1, 2, 3, ...*).
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * Order of the optional parameters does not matter.

Examples:

* `edit 1 i/Use Stack ed/23:59`<br>
  Edits the task information and end time of the first task to be `Use Stack` and `23:59` respectively.
  
* `edit 3 p/3`<br>
  Edits the priority level of third task to 3. 

### 3.5. Finding all tasks by Name or by Datetime : `find`

Finds tasks whose names contain any of the given keywords<br>
Format: `find n/TASK_NAME` 

Finds tasks whose start datetime contains the given keywords<br>
Formats: `find sd/date and time` `find ed/date and time`

> * The search for name is case-insensitive. e.g `Project` will match `project`.
> * The order of the named keywords does not matter. e.g. `meeting project` will match `project meeting`.
> * For name searching, only full words will be matched e.g. `meeting` will not match `meetings`.
> * The date is searched according to the date format user enters.
> * For date searching, dates from all years will be matched if the year was not specified. e.g. `01/01` will match `01/01/17` and `01/01/18`.
> * Some examples of date format for find date command are as follows:
  (1). DD-MM-YYYY
  (2). DD/MM/YY
  (3). DD/MM/YY
  (4). MM/DD/YYYY

Examples:

* `find n/Meeting`<br>
  Returns all tasks whose name contains `Meeting`.
  
* `find n/shopping milk`<br>
  Returns all tasks whose name contains at least one of the keywords: `shopping` and `milk`.

* `find sd/04-02-2017`<br>
  Returns all tasks with the start date `04-02-2017`.
  
* `find ed/05-02-2017`<br>
  Returns all tasks with the end date `05-02-2017`.
  
### 3.6. Deleting a task : `delete`

Deletes the specified task<br>
Format: `delete INDEX`

> * Deletes the task at the specified `INDEX`. <br>
> * The index refers to the index number shown in the most recent listing. <br>
> * The index **must be a positive integer** (*e.g. 1, 2, 3, ...*).

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the second task in the TaskBoss.
  
* `find n/Meeting`<br>
  `delete 1`<br>
  Deletes the first task in the results of the `find` command.

### 3.7. Creating a category : `new`

Creates a new category<br>
Format: `new CATEGORY`

Example:

* `new Study`<br>
* `new Work`<br>

### 3.8. Clearing tasks by category : `clear`

Clears all tasks under the specified category<br>
Format: `clear CATEGORY`

> * The default categories of TaskBoss are `All Tasks` and `Done`. <br>
> * Clearing `All Tasks` will delete all data from TaskBoss.<br>

### 3.9. Viewing a task : `view`

Views a task by entering the task index<br>
Format: `view INDEX`

> * Views the task at the specified `INDEX`. <br>
> * The index refers to the index number shown in the most recent listing.<br>
> * The index **must be a positive integer** (*e.g. 1, 2, 3, ...*).

### 3.10. Modifying a category name : `name`

Modifies a category name<br>
Format: `name EXISTING_CATEGORY NEW_CATEGORY`

Example:

* `name School ModuleStudy`<br>

### 3.11. Marking a task done : `done`

Marks a task as done<br>
Format: `done INDEX`

> * Marks the task at the specified `INDEX`. <br>
> * The index refers to the index number shown in the most recent listing.<br>
> * The index **must be a positive integer** (*e.g. 1, 2, 3, ...*).

### 3.12. Undoing a command : `undo`

Undoes a most recent command<br>
Format: `undo`

### 3.13. Sorting tasks : `sort`

Sorts tasks by their deadlines<br>
Format: `sort` 

Sorts tasks by their priorities<br>
Format: `sort p` 

### 3.14. Saving the data 

TaskBoss data will automatically be saved in local hard disk after entering any command that updates the data. There is no need to save manually.

### 3.15. Exporting the data : `save`

Exports data to an existing filepath in xml format<br>
Format: `save FILE_PATH` 

Creates a new xml file and exports data to that filepath<br>
Format: `save NEW_FILE_PATH`

### 3.16. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`


## 4. FAQ

**_Q: How do I save my task data in TaskBoss?_** <br>
TaskBoss saves your data to ‘data/taskboss.xml’ by default whenever your task list is updated. There is no need to save manually. You can also change the storage location using the `save` command.


## 5. Command Summary

<br>

|Command    |Parameters                                                        |
|:---------:|:-------------------------------------------------------------------------------------------|
|[help](#31-viewing-help--help)                       |**`help`**              |
|[add](#32-adding-a-task-add)                         |**`add n/TASK_NAME [i/INFO] [sd/START_DATE] [ed/END_DATE] [c/CATEGORY [p/PRIORITY_LEVEL]`**             |
|[list](#33-listing-all-tasks--list)                  |**`list` `list c/CATEGORY NAME`**        |
|[edit](#34-editing-a-task--edit)                     |**`edit INDEX [i/INFO] [sd/START_DATE] [ed/END_DATE] [c/CATEGORY] [p/PRIORITY_LEVEL]`**|
|[find](#35-finding-all-tasks-by-name-or-by-datetime--find)|**`find n/TASK_NAME`  `find sd/date and time`  `find ed/date and time`**|
|[delete](#36-deleting-a-task--delete)                |**`delete INDEX`**         |
|[new](#37-creating-a-category--new)               |**`new CATEGORY`**      |
|[clear](#38-clearing-tasks-by-category--clear)       |**`clear CATEGORY`** |
|[view](#39-viewing-a-task--view)                    |**`view INDEX`**             |
|[name](#310-modifying-a-category-name--name)         |**`name EXISTING_CATEGORY NEW_CATEGORY`**       |
|[done](#311-marking-a-task-done--done)               |**`done INDEX`**        |
|[undo](#312-undoing-a-command--undo)                 |**`undo`**            |
|[sort](#313-sorting-tasks--sort)                     |**`sort` `sort p`**   |
|[save](#315-exporting-the-data--save)                |**`save FILE_PATH` `save NEW_FILE_PATH`**|
|[exit](#316-exiting-the-program--exit)               |**`exit`**           |

<br>
