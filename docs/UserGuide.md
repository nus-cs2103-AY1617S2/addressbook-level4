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
|[**help / h**](#31-viewing-help--help--h)                       |View help              |
|[**add / a**](#32-adding-a-task-add--a)                         |Add a task             |
|[**list / l**](#33-listing-all-tasks--list--l)                  |List the tasks         |
|[**edit / e**](#34-editing-a-task--edit--e)                     |Edit a task            |
|[**find / f**](#35-finding-all-tasks-by-keywords-or-by-datetime--find--f)           |Find tasks by keywords or by datetime             |
|[**delete / d**](#36-deleting-tasks--delete--d)                |Delete a task          |
|[**clear / c**](#37-clearing-tasks-by-category--clear--c)       |Clear tasks in category|
|[**view / v**](#38-viewing-a-task--view--v)                    |View a task            |
|[**name / n**](#39-modifying-a-category-name--name--n)         |Rename a category      |
|[**mark / m**](#310-marking-tasks-done--mark--m)               |Mark a task as done      |
|[**undo / u**](#311-undoing-a-command--undo--u)                 |Undo a task            |
|[**redo / r**](#312-redoing-a-command--redo--r)                 |Redo a task            |
|[**sort / s**](#313-sorting-tasks--sort--s)                     |Sort tasks by deadline or by priority|
|[**save / sv**](#315-exporting-the-data--save--sv)                |Save TaskBoss          |
|[**exit / x**](#316-exiting-the-program--exit--x)               |Exit TaskBoss          |

<br>

### 3.1. Viewing help : `help / h`

Format: `help`

### 3.2. Adding a task: `add / a`

Adds a task<br>
Format: `add TASK_NAME [i/INFO] [sd/START_DATE] [ed/END_DATE] [c/CATEGORY] [p/PRIORITY_LEVEL] [r/RECURRENCE]`

> * Date can be written in UK-time format with slashes, `i.e dd-mm-yyyy` or natural language, `i.e this sunday`.  <br>
> * Time should be in 24-hour clock format or 12-hour format with AM or PM next to it, i.e `1700 hr:min <PM/AM>`. <br>
> * Priority level is `no` by default, and can be either `yes` or `no` (case-insensitive).
> * Recurrence is `none` by default, and can be either `daily`, `weekly`, `monthly`, `yearly` or `none` (case-insensitive).
> * Order of optional parameters does not matter.
> * All fields are optional except TASK_NAME
> * All field are case-sensitive

Examples:

* `add Buy groceries ed/19-02-2017 c/Home p/YES r/weekly`
* `add Dinner with Jim i/@Orchard sd/next friday ed/19-02-2017 c/Leisure p/no`
* `add Post-exam celebration i/@Zouk sd/tomorrow at 3 PM ed/tomorrow 20.30  c/Leisure p/No`


### 3.3. Listing all tasks : `list / l`

Shows a list of all tasks<br>
Format: `list` 

Shows a list of tasks under a specified category <br>
Format: `list c/CATEGORY NAME` 

Example:
* `list c/Project`<br>


### 3.4. Editing a task : `edit / e`

Edits an existing task<br>
Format: `edit INDEX [TASK NAME] [i/INFO] [sd/START_DATE] [ed/END_DATE] [c/CATEGORY] [p/PRIORITY_LEVEL] [r/RECURRENCE]`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number last shown in the last task listing.<br>
    The index **must be a positive integer** (*e.g. 1, 2, 3, ...*).
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * Order of the optional parameters does not matter.

Examples:

* `edit 1 i/Use Stack ed/23:59`<br>
  Edits the task information and end time of the first task to be `Use Stack` and `23:59` respectively.
  
* `edit 3 p/yes`<br>
  Edits the priority level of third task to yes. 

### 3.5. Finding all tasks by Keywords or by Datetime : `find / f`

Finds tasks whose names or information contain any of the given keywords<br>
Format: `find KEYWORDS` 

Finds tasks whose start datetime matches the given datetime<br>
Formats: `find sd/date and time` `find ed/date and time`

> * The search for name is case-insensitive. e.g `Project` will match `project`.
> * The order of the named keywords does not matter. e.g. `meeting project` will match `project meeting`.
> * For name searching, only full words will be matched e.g. `meeting` will not match `meetings`.
> * A specific date can be searched in the UK-time format with slashes, `i.e dd-mm-yyyy` or natural language which also supports searching for a specific month, `i.e december`. 
> * Any numeral day of month or year can also be searched, `i.e 2017`.

Examples:

* `find Meeting`<br>
  Returns all tasks whose name or information contains `Meeting`.
  
* `find shopping milk`<br>
  Returns all tasks whose name or information contains at least one of the keywords: `shopping` and `milk`.

* `find sd/2 april`<br>
  Returns all tasks with start date on 2nd April of the current year.
  
* `find ed/february`<br>
  Returns all tasks with end date in February.
  
### 3.6. Deleting task(s) : `delete / d`

Deletes the specified task<br>
Format: `delete INDEX`

> * Deletes the task(s) at the specified `INDEX`. <br>
> * The index numbers refers to the index numbers shown in the most recent listing. <br>
> * The index numbers **must be a positive integer** (*e.g. 1, 2, 3, ...*).

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the second task in the TaskBoss.
  `delete 1 2`<br>
  Deletes the first and second task in the TaskBoss.
  
* `find n/Meeting`<br>
  `delete 1`<br>
  Deletes the first task in the results of the `find` command.

### 3.7. Clearing tasks by category : `clear / c`

Clears all tasks under the specified category<br>
Format: `clear c/CATEGORY`

> * Category names are case-insensitive. <br>
> * The build-in categories of TaskBoss are `Alltasks` and `Done`. <br>
> * `Alltasks` and `Done` cannot be cleared in TaskBoss.<br>

### 3.8. Viewing a task : `view / v`

Views a task by entering the task index<br>
Format: `view INDEX`

> * Views the task at the specified `INDEX`. <br>
> * The index refers to the index number shown in the most recent listing.<br>
> * The index **must be a positive integer** (*e.g. 1, 2, 3, ...*).

### 3.9. Renaming a category : `name / n`

Renames a category <br>
Format: `name EXISTING_CATEGORY NEW_CATEGORY`

Example:

* `name School ModuleStudy`<br>

### 3.10. Marking task(s) done : `mark / m`

Marks task(s) as done<br>
Format: `mark INDEX`

> * Marks the task(s) at the specified `INDEX`. <br>
> * The index numbers refers to the index numbers shown in the most recent listing.<br>
> * The index numbers **must be a positive integer** (*e.g. 1, 2, 3, ...*).

Examples:
 
 * `list`<br>
  `mark 1 2`<br>
  Marks the first and second task as done in the TaskBoss.

### 3.11. Undoing a command : `undo / u`

Undoes a most recent command and reverts TaskBoss to previous state<br>
Format: `undo`

### 3.12. Redoing a command : `redo / r`

Redoes a most recent command after it has been undone<br>
Format: `redo`

### 3.13. Sorting tasks : `sort / s`

Sorts tasks by their deadlines<br>
Format: `sort` 

Sorts tasks by their priorities<br>
Format: `sort p` 

### 3.14. Saving the data 

TaskBoss data will automatically be saved in local hard disk after entering any command that updates the data. There is no need to save manually.

### 3.15. Exporting the data : `save / sv`

Exports data to an existing filepath in xml format<br>
Format: `save FILE_PATH` 

Creates a new xml file and exports data to that filepath<br>
Format: `save NEW_FILE_PATH`

> * TaskBoss loads data from the last specified filepath every time it is re-loaded.

### 3.16. Exiting the program : `exit / x`

Exits the program.<br>
Format: `exit`


## 4. FAQ

**_Q: How do I save my task data in TaskBoss?_** <br>
TaskBoss saves your data to ‘data/taskboss.xml’ by default whenever your task list is updated. There is no need to save manually. You can also change the storage location using the `save` command.

**_Q: How do I transfer my data to another computer?_** <br>
A: Install TaskBoss in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous TaskBoss.

## 5. Command Summary

<br>

|Command    |Parameters                                                        |
|:---------:|:-------------------------------------------------------------------------------------------|
|[help / h](#31-viewing-help--help)                       |**`help`**              |
|[add / a](#32-adding-a-task-add)                         |**`add TASK_NAME [i/INFO] [sd/START_DATE] [ed/END_DATE] [c/CATEGORY] [p/PRIORITY_LEVEL] [r/RECURRENCE]`**             |
|[list / l](#33-listing-all-tasks--list)                  |**`list` `list c/CATEGORY NAME`**        |
|[edit / e](#34-editing-a-task--edit)                     |**`edit INDEX [TASK NAME] [i/INFO] [sd/START_DATE] [ed/END_DATE] [c/CATEGORY] [p/PRIORITY_LEVEL] [r/RECURRENCE]`**|
|[find / f](#35-finding-all-tasks-by-keywords-or-by-datetime--find)|**`find KEYWORDS`  `find sd/date and time`  `find ed/date and time`**|
|[delete / d](#36-deleting-a-task--delete)                |**`delete INDEX`**         |
|[clear / c](#37-clearing-tasks-by-category--clear)       |**`clear c/CATEGORY NAME`** |
|[view / v](#38-viewing-a-task--view)                    |**`view INDEX`**             |
|[name / n](#39-modifying-a-category-name--name)         |**`name EXISTING_CATEGORY NEW_CATEGORY`**       |
|[mark / m](#310--marking-a-task-done--done)               |**`mark INDEX`**        |
|[undo / u](#311-undoing-a-command--undo--u)                 |**`undo`**            |
|[redo / r](#312-redoing-a-command--redo--r)                     |**`redo`**            |
|[sort / s](#313-sorting-tasks--sort)                     |**`sort ed` `sort sd` `sort p`**   |
|[save / sv](#315-exporting-the-data--save)                |**`save FILE_PATH` `save NEW_FILE_PATH`**|
|[exit / x](#316-exiting-the-program--exit)               |**`exit`**           |

<br>
