# TaskManager - User Guide

By : `Team CS2103JAN2017-T11-B3`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jan 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [Notes on Recurring Tasks](#notes-on-recurring-tasks)
4. [FAQ](#faq)
5. [Command Summary](#command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `TaskManager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your task list.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`** : add Study for midterm sd/02/03/17 ed/04/03/17 t/study t/midterm :
     adds the task Study for Midterm, starting from 02/03/17 to 04/03/17 with tags "study" and "midterm" to the task list.
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

Opens a side window that displaying all commands and syntax.<br>
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

### 2.2. Adding a task: `add`

Adds a task to the task list<br>
Format: `add TASK_NAME [p/PRIORITY_LEVEL] [sd/START_TIMEDATE] [ed/END_TIMEDATE] [t/TAG]...`

> * The TASK_NAME must be **2 or more characters** long. It can begin with any alphabet, number, or special character.
> * The PRIORITY_LEVEL must be an integer between **1-3**. 1 is the HIGHEST priority level and 3 is the LOWEST (default) priority level.
> * **TIMEDATE Format: HH:mm dd/MM/yyyy** (HH:MM is optional). The start/end timing parameters (sd/ed respectively) must be in the date >   format specified above. Note that "sd/2:15 1/1/2017 is invalid; the correct format is "sd/02:15 01/01/2017".
> * To add floating tasks, simply do not specify the START_TIMEDATE and END_TIMEDATE paramters.
> * To add tasks with deadlines, simply specify the END_TIMEDATE.
> * To add recurring tasks, see section 2.2.1.
> * Any of these 3 types of tasks can have 0 or more tags.
  > * To add a tag, specify it after `t/`. If there are multiple tags, you must use a `t` for each one (see example for more details)
  > * A single tag cannot be deleted or edited if a task has multiple tags
> * All fields are optional, except for TASK_NAME

Examples:

* `add Watch Friends season 2`
* `add Submit assignment for MA1101R P/1 sd/09/04/2017 ed/16:00 13/04/2017`
* `add Study for midterm p/1 ed/04/03/2017 t/study t/midterm`
* `add Attend CS2103 tutorial p/1 ed/02/03/2017 t/lesson t/school t/tutorial`

  ### 2.2.1. Adding a recurring task

  > * Adds a recurring task to the task list
  > * Format: same as 'add' but specifcy the frequency by r/#_
  > where '#' is an integer and '_' is either 'd' (day), 'm' (month), or 'y' (year)
  > * **Note:** Tags are meant to be a commonality for recurring tasks by design; thus they cannot be set differently for a specific instance of a recurring task

  Examples:

  * `add Attend CS2103 tutorial p/1 sd/11:00 19/01/2017 ed/12:00 19/01/2017 r/7d`
  * `add Clean fish tank p/2 sd/01/01/2017 ed/03/01/2017 r/2m`

### 2.3. Listing tasks : `list`

Shows a list of all tasks in the task list.<br>
Format: `list`<br>

> All tasks will appear in the task list, and tasks will appear in the calendar as well.

Example:

* `list`<br>
  Shows a list of all tasks in the task list.

  ### 2.3.1 Natural ordering of tasks

  Sorting occurs automatically in Task Manager after every action. First, completed tasks will be sent to the bottom of the list. Then, tasks are sorted by priority, with highest priority tasks at the top of the list. Afterwards, tasks are sorted by their end time, then start time, then alphabetized by description.

### 2.4. Editing a task : `edit`

Edits an existing task in the task list.<br>
Format: `edit INDEX [TASK_NAME] [p/PRIORITY] [sd/START_TIMEDATE] [ed/END_TIMEDATE] [t/TAG]...`
Editing a recurring task in this way will edit all instances

> * The TASK_NAME must be **2 or more characters** long. It can begin with any alphabet, number, or special character.
> * The PRIORITY_LEVEL must be an integer between **1-3**. 1 is the HIGHEST priority level and 3 is the LOWEST (default) priority level.
> * **TIMEDATE Format: HH:mm dd/MM/yyyy** (HH:MM is optional). The start/end timing parameters (sd/ed respectively) must be in the date > format specified above. Note that "sd/2:15 1/1/2017 is invalid; the correct format is "sd/02:15 01/01/2017".
> * Edits the task at the specified `INDEX`.
> The index refers to the index number shown in the last task listing.<br>
> The index **must be a positive integer** 1, 2, 3, etc.
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by typing `t/` without specifying any tags after it.
> * If you want to clear the START_TIMEDATE or END_TIMEDATE of a task, use `sd/floating` and `ed/floating` respectively
> in the edit command (see example below).
> * You can edit the frequency of a recurring task following the same syntax. Note that all occurrences will be changed
> according to the frequency specified.
> * Once a non-recurring task is created, it cannot be `edit`ed and made into a recurring task. You must create a new task using
> * `add` and specifiy a frequency with `r/` if you want to make a non-recurring task into a recurring one.

Examples:

* `edit 1 sd/03/03/17` <br>
  Edits the start date of task 1 to 03/03/17.

* `edit 2 Do Algorithm Assignment t/`<br>
  Edits the name of the 2nd task to be `Do Algorithm Assignment` and clears all existing tags.

* `edit 4 ed/floating p/1`<br>
   Removes the end timing for task 4 and updates its priority to 1.

* `edit 1 r/1y` <br>
  Edits the frequency of task 1 (assuming it is a recurring task) and changes it to 1 year.

  ### 2.4.1. Editing a specific instance of a recurring task : `editthis`

  Format: `editthis INDEX [NAME] p/PRIORITY sd/START_TIMEDATE ed/END_TIMEDATE...`

  > * Edits a specific instance of a recurring task
  > * After editing this instance, the edited task will no longer be a part of the recurring sequence

  Examples:

  * `edithis 2 sd/01/01/2017`<br>
  Edits the start date of task 2 (which is recurring)

  * `edithis 2 Go to 2103 Lecture`<br>
  Edits the description of task 2 (which is recurring)

### 2.5. Finding all tasks containing any keyword: `find`

Finds tasks whose TASK_NAME, PRIORITY, START_TIMEDATE, or END_TIMEDATE contain any of the given keywords.<br>
Format 1: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case insensitive. e.g `assignment` will match `AssIGNmEnt`
> * The order of the keywords does not matter. e.g. `do assignment to` will match `assignment to do`
> * Only full words will be matched e.g. `assign` will not match `assignment`
> * Task matching at least one keyword will be returned (i.e. `OR` search) e.g. `assignment` will match `do algorithm assignment`
> * Note: `find` does not search on a task's tags 

Examples:

* `find midterm`<br>
  Returns `Study for midterm`

  * `find 11/01/2017`<br>
  Returns all tasks with start or end timings on January 11, 2017.

  * `find 1`<br>
  Returns tasks with a priority of 1 (i.e. a HIGH priority).

### 2.5.1. Finding an instance of a recurring task
  > * Execute 'find' with the same syntax as above
  > * However, only one instance will show up in the list after executing 'list'
  > * The instance in 'list' will be updated to match the parameters after executing a valid 'find'

  Examples:

  * `add feed cat sd/10/05/2017 ed/10/05/2017 r/1d`<br>
    The task is displayed in the list with the above parameters
  * `find 11/05/2017`<br>
    Returns `feed cat sd/11/05/2017 ed/11/05/2017`
  * `list`<br>
    Returns all of the tasks with 'feed cat's' instance with START_TIMEDATE: 11/05/2017 and END_TIMEDATE: 11/05/2017

### 2.6. Deleting a task : `delete`

Deletes the specified task from the task list.<br>
Format: `delete INDEX`

> * Deletes the task at the specified `INDEX`. <br>
> * The index refers to the index  number shown in the most recent listing.<br>
> * The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task list.

* `find tutorial`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

  ### 2.6.1. Deleting a specific instance of a recurring task : `deletethis`

  Deletes the specific occurrence of the recurring task from the list
  Format: `deletethis INDEX`

  > * Deletes the specific occurrence of a recurring task at the specified INDEX
  > * The index refers to the index  number shown in the most recent listing.<br>
  > * The index **must be a positive integer** 1, 2, 3, ...
  > * Upon deleting an instance, the task list will be updated with the next recent occurrence.
    If there is no more occurrences, then the entire recurring task will be removed from the list.
  > * **Note:** Calling `deletethis` on a non-recurring task is supported - functionality is equivalent to
  calling `delete` on the same task.

  Example:

* `find 05/01/2017`<br>
  `deletethis 1`<br>
  Executes a search to find tasks on January 5th the deletes the 1st task (which is a recurring instance).

### 2.7. Complete a task : `complete`

Marks the specified task as `Completed`. The task is automatically added with a `complete` tag.<br>
Format: `complete INDEX`

> * Mark the task at the specified `INDEX` as `Completed`.
> * The index refers to the index number shown in the most recent listing.
> * The index **must be a positive integer** 1, 2, 3, ...
> * To complete a specific instance of a recurring task, follow the same syntax.
> * **Note:** `complete` ALL instances of a recurring task is not a practical application of this command and thus is not supported.

Examples:

* `list`<br>
  `complete 2`<br>
  Completes the 2nd task in the task list.
* `find tutorial` <br>
  `complete 1`<br>
  Completes the 1st task in the results of the `find` command.

### 2.8. Allocate priority to a task: `prioritize`

Puts a priority level to a task.<br>
Format: `prioritize INDEX PRIORITY_LEVEL`

> * Allocates a priority leve of `PRIORITY_LEVEL` to the task at the specified `INDEX`.
> * The index **must be a positive interger** 1, 2, 3,...
> * The priority level **must be a positive integer from 1 to 3**, 1 being the highest priority and 3 being the least.
> * Using `prioritize` on a recurring task will change the priority of ALL occurrences.
> * **Note:** To prioritize a specific instance of a recrring task, use `editthis INDEX p/#` where '#' represents the edited
> priority.

Examples:

* `list`<br>
  `prioritize 2 3`<br>
  Puts a priority level of 3 to the 2nd task in the task list. If the 2nd task is recurring, then all occurrences will have priority 3.

* `find attend 2103 lecture`<br>
`editthis 1 p/1`<br>
Puts a priority level of 1 to the 1st task in the resulting list (assuming it is recurring, only this occurrence will have priority 1).

### 2.9. Clearing all entries : `clear`

Clears all entries from the task list.<br>
Format: `clear`

### 2.10. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

Example:

* `exit`<br>
The application will shut down.

### 2.11. Saving the data : `save`

Saves task manager data in specified file location.
Format: `save PATH/TO/SAVE_LOCATION`

* Task list data is saved in the hard disk automatically after any command that changes the data.<br>
* As a result, there is no need to save manually.
* The exception is when you want to save data to a new location.

Examples:

* `save` task_manager_back_up.xml
* `save` data/taskmanager.xml

### 2.12. Loading the data : `load`

Loads task manager data from specified file location
Format: `load PATH/TO/LOAD_LOCATION`

* Loading occurs automatically when Task Manager opens.
* As a result, the load command is only necessary when Task data should be loaded from a new location.

Examples:

* `load` task_manager_old.xml
* `load` data/saved_task_manager.xml

### 2.13. Revert the previous change : `undo`

Undo the previous change made to the task manager.
Format: `undo`
> * The undo command is able to undo all changes made after the application is opened.
> * When there is nothing to undo, an error message will be shown.
> * **Note:** `undo` only works in the same session (i.e. `exit`ing the application, then restarting it, and 
> then calling `undo` will not restore changes made in the past session)

Examples:

* `undo`

### 2.14. Revert the previous undo change: `redo`

Revert the previous undo change to the task manager.
Format: `redo`
> * The redo command is able to redo multiple undos.
> * If you call `undo`, make some changes, then call `redo`, the effect is no change since there is nothing to `redo`.
> Essentially, `redo` must be called directly after an `undo`, else the logic does not make sense.

Examples:

* `undo`, `redo`
Redo's the command which was just undone
*`delete 1`, `undo`, `add floating task`, `redo`
No change (nothing to `redo`)

## 3. Notes on Recurring Tasks

Recurring tasks are those that are meant to repeat after a specified amount of time. This application supports
the implementation of such tasks. A few things to note about how to use this feature:

* The START/END TIMEDATE should be specified for one occurrence. A common misconception is specifiying these paramters
  as the start and end timings of when the overall recurring pattern should start/end respectively.
* So for example, if the recurring task you want to add is "Attend 2103 Tutorial" which begins on January 19, 2017 and occurs
  every week from 11am - 12pm, the syntax of the respective command would be as follows:
  `add Attend 2103 Tutorial sd/11:00 19/01/2017 ed/12:00 19/01/2017 r/7d`.
* The task would then automatically be generated for the next 60 days (refer to non-functional requirements).
* If this task ends before 60 days, then you can execute `delete INDEX` where INDEX specifies the index of the recurring task on
  the User Interface. This will remove all occurrences of the recursive task from the Task Manager.
* If this task runs longer then 60 days, then you will have to re-add the task following the same syntax for `add` so that it
  recurrs for another 60 days.
* Both the START_TIMEDATE and the END_TIMEDATE parameters must be specified for initiating a recurring task.

## 4. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous task list folder.<br><br>
**Q**: How do I change the calendar to view a different month?<br>
**A**: Enter a date in the text field and click enter. The calendar will be updated with the new view.<br><br>
**Q**: How do I undo changes made in a previous session?<br>
**A**: Command history is cleared upon exiting Task Manager. Thus, does changes cannot be undone.<br><br> 

## 5. Command Summary

* **Add**  `add TASK_NAME p/1 sd/START_TIMEDATE ed/END_TIMEDATE [t/TAG]...` <br>
   e.g. `add Study for midterm p/1 sd/04/03/2017 ed/04/04/2017 t/study t/midterm`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Deletethis** : `deletethis INDEX` <br>
   e.g. `deletethis 3`

* **Edit** : `edit INDEX` <br>
   e.g. `edit 1 ed/03/03/2017`

* **Editthis** : `editthis INDEX` <br>
   e.g. `editthis 1 ed/03/03/2017`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
   e.g. `find assignment` <br>
        `find tutorial`

* **List** : `list` <br>
   e.g. `list`

* **Help** : `help` <br>

* **Complete** : `complete INDEX` <br>
   e.g. `complete 2`

* **Prioritze** : `prioritize INDEX PRIORITY_LEVEL` <br>
   e.g. `priority 2 3`

* **Exit** : `exit` <br>
   e.g. `exit`

* **Load** : `load PATH/TO/LOAD_FILE` <br>
   e.g. `load /Documents/task/tasklist.xml`

* **Save** : `save PATH/TO/SAVE_FILE` <br>
   e.g. `save /Documents/task/tasklist.xml`

* **Redo** : `redo` <br>
   e.g. `redo`

* **Undo** : `undo` <br>
   e.g. `undo`
