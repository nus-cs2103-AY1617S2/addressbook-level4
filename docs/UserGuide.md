# DoOrDie - User Guide

Please refer to the [Setting up](DeveloperGuide.md#setting-up) section to learn how to set up DoOrDie.

---

1. [Start Project](#start-project)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Start Project

1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br />

   > Having any Java 8 version is not enough. <br />
   > This app will not work with earlier versions of Java 8.

2. Download the latest `doordie.jar` from the [releases](../../../releases) tab.
3. Navigate to the location of the `doordie.jar` and double click the jar.
4. The GUI should appear in a few seconds.
<img src="images/Ui.png" width="600">

5. Refer to the [Features](#features) section below for details of each command.<br />
6. Pressing the up or down key will allow you to iterate through previous commands executed (if any).
7. Pressing the tab key will auto complete the word at the current cursor if there is a match, otherwise a list of suggestions will be displayed.

## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 2.1. Viewing help : `help`

Format: `help`

> * Help is also shown if you enter an incorrect command e.g. `abcd`

### 2.2. Adding a task: `add`

Adds a task to DoOrDie<br />
Format: `add TASKNAME [# LABEL...] ([by DEADLINE] | [from START_DATE to END_DATE]) [repeat (hourly|daily|weekly|monthly|yearly)]`

> * Tasks can have a deadline, or can do without one as well.
>   * Tasks added without specifying a deadline will be displayed under "No Deadline".
>   * Date formats can be flexible. The application is able to parse commonly-used human-readable date formats.
>     * e.g. `Monday`, `next wed`, `tomorrow`, `5 days after`, `4 Apr` etc.
> * Dates can include time as well.
>   * If only time is specified, it will default to 2359.
>   * If time is not specified, it will default to the current time of the particular date.
>   * Time formats are flexible as well. The application supports 24 hour format and AM/PM format.
>     * e.g. `Monday 3pm`, `today 1930`, `5:30pm`, `10.00 am`
> * Recurring task will have the same deadline if `daily/weekly/monthly/yearly` is used
> * Tasks can have any number of label name. (including 0).
> * The `add` command adds task without specifying `deadline`.
> * Tasks can be created for a date that is already passed.

Examples:

 * `add CS2106 Mid terms`
 * `add CS2103 V0.0 by tmr #module`
 * `add Make baby by next wednesday`
 * `add go to school repeat daily`

### 2.3 Listing all tasks : `list`

Shows a list of all tasks in DoOrDie. Able to filter by type of task (task), or based on status of task such as completed or outstanding.

Format: `list [TYPE]`

> Valid parameters for TYPE:
> * `tasks` / `task`
> * `completed`
> * `incomplete`
> * `overdue` / `over`
> * `by DATE`
> * `from STARTDATE to ENDDATE`
> * `bookings` / `booking`

Examples:

* `list`<br />
 Lists all tasks.

* `list Overdue`<br />
 Lists all overdue tasks

* `list Outstanding tasks`<br /> 
* `list Incomplete tasks`<br />
 Lists all outstanding tasks

* `list Completed`<br />
 Lists all completed tasks

* `list by today`<br />
 Lists all tasks due by today

* `list from monday to friday`<br />
 Lists all tasks due within Monday-Friday

* `list booking`<br />
 Lists all unconfirmed tasks with their respective bookings
 
### 2.4. Editing a task : `edit`

Edits the specified task's details.

Format:`edit [TASK_ID] [TASKNAME] [label LABEL] ([(by) DATE] | [from START_DATE to END_DATE] | [clear dates]) [COMPLETED | INCOMPLETE]`

> * Edits the task with the `TASK_ID`
    The index refers to the id of the task.<br />
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields excluding `TASK_ID` must be provided.
> * Existing values will be updated to the input values.
> * When editing labels, the existing labels of the task will be removed i.e adding of labels is not cumulative.
> * You can remove all the task's tags by typing `#` without specifying any tags after it.
> * You can mark a task completed or incomplete by specifying 'completed' or 'incomplete' respective.
> * Using 'clear dates' allows the user to remove the existing dates associated with the task.


Examples:

* `edit CS2106Assignment label`<br />
  Edits the name of the currently selected task to be `CS2106Assignment` and clears all existing labels.

* `edit 1 label tedious work by thursday`<br />
  Edits the label and deadline of the task with id 1 to be `tedious work` and deadline to `Thursday` respectively.

### 2.5. Finding all task containing any keyword in task name and labels : `find`

Finds tasks whose name and labels containing any of the specified keywords.

Format: `find [TYPE] KEYWORD [MORE_KEYWORDS]...`

> Valid parameters:
> * `TaskName`
> * `LabelName`
> * `Complete` / `Completed`
> * `Incomplete` / `Outstanding`
> * `by DATE`
> * `DATE`
> * `from STARTDATE to ENDDATE`

> * The search is case insensitive and the order of the keywords does not matter.
> * Task names, label names will be searched, and tasks with at least one keyword match will be return and display to user.

Examples:

* `find CS2103`<br />
Returns all task containing the keyword or label containing `CS2103` & `cs2103`.

* `find project`<br />
Returns all task with the name containing `project` & `Project`.

* `find glocery`<br />
Returns all task with the label name containing `glocery` & `Glocery`.

* `find project glocery`<br />
Returns all tasks having name or label name containing `project`, `Project`,  `glocery`, `Glocery`.

### 2.6. Deleting a task : `delete`

Deletes the specified task from the task manager. Reversible via undo command.

Format: `delete [INDEX|LABEL]`

> * Deletes the task at the specified `INDEX` or all task with `LABEL`. <br />
> * The index refers to the index of the task in the display list.
> * The index **must be a positive integer** 1, 2, 3, ...
> * If the label does not exist, command will still be executed but no change will occur

Examples:

* `delete`<br />
  Deletes the currently selected task in DoOrDie.
* `delete 2`<br />
  Deletes the task with the id `2` in the DoOrDie.
* `delete school`<br />
  Deletes all task with the label `school`.

### 2.7. Select a Task : `select`

Selects the task identified by its `id`<br />
Format: `select TASK_ID`

> * Selects the task and loads the saved links/attachments/details of `TASK_ID`.<br />
> * The index refers to the id of the task.<br />
> * The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `select 2`<br />
  Selects the task of id 2

### 2.8. Add a booking : `book`

Reserve time slots for a certain task that has not been confirmed yet.<br />
Format: `book TASKNAME [#LABEL...] on DATE, DATE, [MORE_DATES]...`

> * Date formats can be flexible. The application is able to parse commonly-used human-readable date formats.
>   * e.g. `Monday`, `next wed`, `tomorrow`, `5 days after`, `4 Apr` etc.
> * Dates can include time as well.
>   * If only time is specified, it will default to today's date.
>   * If time is not specified, it will default to the current time of the particular date.
>   * Time formats are flexible as well. The application supports 24 hour format and AM/PM format.
>     * e.g. `Monday 3pm`, `today 1930`, `5:30pm`, `10.00 am`
> * Tasks can have any number of label name. (including 0).
> * DATES and MORE_DATES should be prefixed with a comma if there are multiple dates.

Examples:

* `book CS2103 Meeting on 1/1/2017 4pm, 2/1/2017 8pm`<br />
  Reserves time slots on the 1st January 2017 4pm and 2nd January 8pm for CS2103 Meeting

### 2.9. Confirm a booking : `confirm`

Confirm booking of a task and releases other bookings for the confirmed task.<br />
Format: `confirm TASK_ID (SLOT_NUMBER|DATE)`

> * DATE specified should be one of the bookings that has been made
> * SLOT_NUMBER will be respective to the dates added in that order
> * The index refers to the id of the task.<br />
> * The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `book CS2103 Meeting on 1/1/2017 4pm, 2/1/2017 8pm`<br />
  `confirm 1 1/1/2017 4pm`<br />
  Confirms the task CS2103 Meeting for 1st January 2017 4pm and releases 2nd January 2017 8pm slot for other tasks
* `book CS2103 Meeting 1/1/2017 4pm, 2/1/2017 8pm`<br />
  `confirm 1 1`<br />
  Confirms the task CS2103 Meeting for 1st January 2017 4pm and releases 2nd January 2017 8pm slot for other tasks

### 2.10. Edit a label : `editlabel`

Renames a specific label to another label<br />
Format: `editlabel OLD_LABEL NEW_LABEL`

> * Label names must be alphanumberic

Examples:

* `editlabel friends oldfriends`<br />
  Renames all task with the tag `friends` to `oldfriends`.

### 2.11. Undo the previously executed command : `undo`

Revert results of a previously executed command. If the previously executed command does not modify the data of DoOrDie, nothing will be reverted.<br />
Format: `undo`

### 2.12. Clearing all entries : `clear`

Clears all entries from DoOrDie.<br />
Format: `clear`

### 2.13. Push task changes to Google Calendar : `push`

Updates `Google Calendar` with newly added/modified tasks. Priority goes to `DoOrDie` if there is a conflict.<br />
Format: `push`

### 2.14. Pull task changes from Google Calendar : `pull`

Downloads data from Google Calendar. Priority goes to `Google Calendar` if there is a conflict.<br />
Format: `pull`

### 2.15. Export agenda to PDF file : `export`

Saves a PDF format with all tasks and details to the same directory as `doordie.jar`.<br />
Format: `export ([DATE] | [from START_DATE to END_DATE])`

> * If no date is specified, the default date will be today's date
> * Date formats can be flexible. The application is able to parse commonly-used human-readable date formats.
>   * e.g. `Monday`, `next wed`, `tomorrow`, `5 days after`, `4 Apr` etc.
> * Dates can include time as well.
>   * If only time is specified, it will default to today's date.
>   * If time is not specified, it will default to the current time of the particular date.
>   * Time formats are flexible as well. The application supports 24 hour format and AM/PM format.
>     * e.g. `Monday 3pm`, `today 1930`, `5:30pm`, `10.00 am`

Examples:

* `export today`<br />
  Saves a PDF with tasks and details of today
* `export from 2nd Feb to 9th Feb`<br />
  Saves a PDF with tasks and details from 2nd February to 9th February of the current year

### 2.16. Exiting the program : `exit`

Exits DoOrDie<br />
Format: `exit`

### 2.17. Saving the data

DoOrDie data are saved in the hard disk automatically after any command that changes the data.<br />
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br />
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Task Mangager folder.

## 4. Command Summary

* **Help** `help`

* **Add** `add TASKNAME [#LABEL...] [by DEADLINE] [repeat (hourly|daily|weekly|monthly|yearly)]`<br />
  e.g. `add CS2106 Mid terms by tmr 13:00 #school`

* **List** `list [TYPE]`<br />
  e.g. `list outstanding tasks`

* **Update** `update [TASK_ID] [TASKNAME] [#LABEL...] ([by DATE] | [from START_DATE to END_DATE])`<br />
  e.g. `update 1 #label tedious work by thursday #work`

* **Find** `find [TYPE] KEYWORD [MORE_KEYWORDS]...`<br />
  e.g. `find CS2103`

* **Delete** `delete [TASK_ID|LABEL]`<br />
  e.g. `delete 1`

* **Select** `select TASK_ID`<br />
  e.g. `select 2`

* **Book** `book TASKNAME [#LABEL...] on DATE, [MORE_DATES]...`<br />
  e.g. `book CS2103 Meeting on 1/1/2017 4pm, 2/1/2017 8pm`

* **Confirm** `confirm TASK_ID (SLOT_NUMBER|DATE)`<br />
  e.g. `confirm 1 1`

* **Edit Label** `editlabel OLD_LABEL NEW_LABEL`<br />
  e.g. `editlabel friends oldFriends`

* **Undo** `undo`

* **Clear** `clear`

* **Push** `push`

* **Pull** `pull`

* **Export** `export ([by DATE] | [from START_DATE to END_DATE])`<br />
  e.g. `export today`

* **Exit** `exit`
