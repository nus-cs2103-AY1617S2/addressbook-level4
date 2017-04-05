# TaskManager - User Guide

By : `Team CS2103JAN2017-T11-B3`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jan 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `NUS`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

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
Format: `add TASK_NAME p/PRIORITY_LEVEL sd/DATETIME ed/DATETIME [t/TAG]...`
Date Format: HH:mm dd/MM/yyyy
Note: HH:MM is optional

> Tasks can have any number of tags (including 0).

Examples:

* `add Study for midterm p/1 ed/04/03/2017 t/study t/midterm`
* `add Attend CS2103 tutorial p/1 ed/02/03/2017 t/lesson t/school t/tutorial`

### 2.2.1. Adding a recurring task

Adds a recurring task to the task list<br>
Format: same as 'add' but specifcy the frequency by r/#_
where '#' is an integer and '_' is either 'h' (hour), 'd' (day), or 'm' (month) 

Examples:

* `add Attend CS2103 tutorial p/1 sd/11:00 19/01/2017 ed/12:00 19/01/2017 r/7d`
* `add Clean fish tank p/2 sd/01/01/2017 ed/03/01/2017 r/2m`

### 2.3. Listing tasks : `list`

Shows a list of all tasks in the task list.<br>
Format: `list`<br>

> The list of tasks will be sorted accoding to priority level from 1 to 3

Examples:
* `list`<br>
  Shows a list of all tasks in the task list.

### 2.4. Editing a task : `edit`

Edits an existing task in the task list.<br>
Format: `edit INDEX [NAME] p/PRIORITY sd/START_DATE ed/END_DATE [t/TAG]...`
Editing a recurring task in this way will edit all instances 

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by typing `t/` without specifying any tags after it.

Examples:

* `edit 1 sd/03/03/17` <br>
  Edits the start date of task 1 as 03/03/17.

* `edit 2 Do Algorithm Assignment t/`<br>
  Edits the name of the 2nd task to be `Do Algorithm Assignment` and clears all existing tags.

  ### 2.4.1. Editing a specific instance of a recurring task : `editthis`
  Format: `editthis INDEX [NAME] p/PRIORITY sd/START_DATE ed/END_DATE...`

  > * Edits a specific instance of a recurring task
  > * After editing this instance, the edited task will no longer be a part of the recurring sequence 

  Examples:

  * `edithis 2 sd/01/01/2017`<br>
  Edits the start date of task 2 (which is reccuring) 

  * `edithis 2 Go to 2103 Lecture`<br>
  Edits the description of task 2 (which is reccuring) 


### 2.5. Finding all tasks containing any keyword in their name: `find`

Finds tasks whose names contain any of the given keywords or tag names.<br>
Format 1: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case insensitive. e.g `assignment` will match `AssIGNmEnt`
> * The order of the keywords does not matter. e.g. `do assignment to` will match `assignment to do`
> * Only full words will be matched e.g. `assign` will not match `assignment`
> * Task matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `assignemnt` will match `do algorightm assignment` 

Examples:

* `find midterm`<br>
  Returns `Study for midterm`


### 2.5.1. Finding an instance of a reccuring task
  > * Execute 'find' with the same syntax as above
  > * However, only one instance will show up in the list after executing 'list'
  > * The instance in 'list' will be updated to match the parameters after executing a valid 'find'

  Example:
  * `add feed cat sd/10/05/2017 ed/10/05/2017 r/1d`<br>
    The task is displayed in the list with the above parameters 
  * `find 11/05/2017`<br>
    Returns `feed cat sd/11/05/2017 ed/11/05/2017`
  * `list`<br>
    Returns all of the tasks with 'feed cat's' instance with start: 11/05/2017 and end: 11/05/2017     

### 2.6. Deleting a task : `delete`

Deletes the specified task from the task list. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index  number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task list.
* `find tutorial`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

  ### 2.6.1. Deleting a specific instance of a reccuring task : `deletethis`

  Deletes the specific occurrence of the reccuring task from the list
  Format: `deletethis INDEX`

  > Deletes the specific occurrence of a reccuring task at the specified INDEX
  > The index refers to the index  number shown in the most recent listing.<br>
  > The index **must be a positive integer** 1, 2, 3, ...
  > Upon deleting an instance, the task list will be updated with the next recent occurrence.
    If there is no more occurrences, then the entire reccuring task will be removed from the list.
  > **Note:** Calling `deletethis` on a non-recurring task is supported - functionality is equivalent to 
  calling `delete` on the same task.

  Exmaple:

* `find 05/01/2017`<br>
  `deletethis 1`<br>
  Executes a search to find tasks on January 5th the deletes the 1st task (which is a recurring instance).


### 2.7. Complete a task : `complete`

Marks the specified task as `Completed`. The task is automatically added with a `complete` tag.<br>
Format: `complete INDEX`

> Mark the task at the specified `INDEX` as `Completed`.
> The index refers to the index number shown in the most recent listing.
> The index **must be a positive integer** 1, 2, 3, ...
> To complete a specific instance of a recurring task, follow the same syntax.
> **Note:** `complete` ALL instances of a recurring task is not a practical application of this command and thus is not supported.   

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

> Allocates a priority leve of `PRIORITY_LEVEL` to the task at the specified `INDEX`.
> The index **must be a positive interger** 1, 2, 3,...
> The priority level **must be a positive integer from 1 to 3**, 1 being the highest priority and 3 being the least.
> Using `prioritize` on a recurring task will change the priority of ALL occurrences.  
> **Note:** To prioritize a specific instance of a recrring task, use `editthis INDEX p/#` where '#' represents the edited 
priority. 

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

### 2.11. Saving the data

task list data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous task list folder.

## 4. Command Summary

* **Add**  `add TASK_NAME p/1 sd/START_DATE ed/DUE_DATE [t/TAG]...` <br>
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

* **Exit ** : `exit` <br>
   e.g. `exit`
