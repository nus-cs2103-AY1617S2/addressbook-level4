# Opus - User Guide

By : `Team W15-B3`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

Opus is the ideal task manager for the keyboard loving *(hint: Vim/Emacs)* users. Why spend that extra 1 second to move your hand to the mouse, when you could do everything with the keyboard? Opus is developed with the mission to make command line interfaces less intimidating. Our mission is for you to stay happy while keeping track of your tasks with your trusty keyboard. \*clickity clackity tack\*

## Table of Contents

---

1. [Quick Start](#1-quick-start)
2. [Features](#2-features)
3. [FAQ](#3-faq)
4. [Command Summary](#4-command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `opus.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Let's have a look at how Jim, an avid Opus fan, utilises the task manager:
   * **`add`**` Do laundry p/low b/31/12/2017 12:00` : Jim wants to do laundry at noon, so he adds it to the task manager.
   * **`list`** : Jim wants to get back to the overview of his list of tasks.
   * **`sort`**` priority` : Jim wants to see the most urgent tasks at the top of his list.
   * **`mark`**` 1 2` : Jim realises he has completed the 1st and 2nd task so he marks them as completed.
   * **`delete`**` 3` : Jim realises he does not have to complete the 3rd task so he deletes it from his current list.
   * **`find`**` laundry` : Jim gets a call from his mum that the laundry must be completed by 3pm so he does a search for it.
   * **`edit`**` 1 p/hi` : Jim updates the priority of the found laundry task to high.
   * **`exit`** : Jim is done for the day so he decides to exit the application!
6. Refer to the [Features](#2-features) section below for details of each command.<br>


## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 2.1. Viewing help : `help`

Format: `help`

> Help is shown only if you enter `help`

### 2.2. Adding a task: `add`

Adds a task to the task manager.<br>
Format: `add NAME [n/NOTES] [b/STARTIME] [e/ENDTIME] [p/PRIORITY] [t/TAG]...`

> * NOTES : Can contain any alphanumeric inputs, or left empty.
> * STARTTIME : In the format of mm/dd/yyyy hh:mm, eg. 12/31/2017 23:59, or left empty.
> * ENDTIME : Format same as STARTTIME, but input has to be later than that of STARTTIME's, or left empty.
> * PRIORITY : Can have a ranking of hi, mid, low, or left empty.
> * TAG: Can have any number of tags, or left empty.

Examples:

* `add Do laundry`
* `add Finish v0.0 documentation n/Urgent b/02/28/2017 12:00 e/02/29/2017 23:59 p/hi t/CS2103T t/CS2101`

### 2.3 List tasks: `list`

Lists all tasks that are currently in storage.

### 2.4. Sort tasks : `sort`

Sorts the list of tasks currently being shown according to the keyword.<br>
Format: `sort KEYWORD`

> Keywords
> * `status` : Sorts the list according to status, with incomplete tasks at the top.
> * `start` : Sorts the list according to start dates, with the earlier start dates at the top.
> * `end` : Sorts the list according to end dates, with the earlier end dates at the top.
> * `priority` : Sorts the list according to priorities, with the highest priorities at the top
> * `all` : Sorts the list firstly according to status, then start date, then end date, then priority.

Examples:

* `sort status` <br>
Sorts the list of tasks according to status

### 2.5. Editing a task : `edit`

Edits an existing task in Opus.<br>
Format: `edit INDEX [NAME] [n/NOTES] [b/STARTIME] [e/ENDTIME] [p/PRIORITY] [t/TAG]...`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by typing `t/` without specifying any tags after it.

Examples:

* `edit 1 Finish tutorial exercises`<br>
Edits the name of the 1st task to `Finish tutorial exercises`.

### 2.6. Mark task : `mark`

Marks task as complete.<br>
Format: `mark [INDEX]...`

> * Marks the task at all the specified `INDEX` as complete.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `mark 3 4 5`<br>
Marks tasks at index 3, 4 and 5 as complete.

### 2.7. Unmark task

Marks task as incomplete.<br>
Format: `unmark INDEX...`

> * Marks the task at all the specified `INDEX` as incomplete.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `unmark 3 4 5`<br>
Marks tasks at index 3, 4 and 5 as incomplete.

### 2.8. Schedule: `schedule`

Set an event or a deadline.<br>
Format: `schedule INDEX [STARTIME to] ENDTIME`

> * Set the start time and the end time of the task.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * If only one datetime is specified, opus will automatically schedule for `ENDTIME`

Examples:

* `schedule 5 12/05/2017 15:00`<br>
Set the deadline/end time as 3pm on December 5, 2017.

* `schedule 6 12/05/2017 13:00 to 12/05/2017 15:00`<br>
Set the start time as 1pm on December 5, 2017 and the end time as 3pm on December 5, 2017.

### 2.9. Delete : `delete`

Deletes an event or task.<br>
Format: `delete INDEX`

Examples:

* `delete 3`<br>
Deletes task or event at index 3.

### 2.10. Clear : `clear`

Erases all data from Opus.<br>
Format: `clear`

> * Clears the entire user data including all tasks, events and user-defined tags.

### 2.11. Undo : `undo`

Undo the latest command.<br>
Format: `undo`

> * Reverts the change done by the previous command.

### 2.12. Redo : `redo`

Reverts the previous undo action.<br>
Format: `redo`

> * Reverts the change done by the previous undo action.

### 2.13. Find : `find`

Displays a list of tasks based on keywords, status, start time, end time and priority.<br>
Format: `find [KEYWORD]... [s/STATUS] [b/STARTIME] [e/ENDTIME] [p/PRIORITY]`

> * Displays the list of tasks matching the search parameters
> * `find` without parameters will display the help section for the command
> * `find` requires minimal of one parameter
> * `[KEYWORD]` can be a text from either name, note or tags
> * Specifying `[STARTTIME]` will show the tasks before the specified time and specifying `[ENDTIME]` would do the same.

Examples:

* `find do homework`<br>
Find tasks that contain `do homework` in either name, note or tag.

* `find do homework p/hi e/03/04/2017`<br>
Find tasks that contain `do homework` in either name, note or tag and due before `March 4, 2017` and the priority being set to `high`.

### 2.14. Saving and loading data

Opus automatically saves all data that you have created. Every single change that you have made is tracked by Opus and you do not have to do anything to enable auto-saving. Your data will be loaded upon launching Opus, so you do not have to go through the saving-and-loading loop as with a normal text document.

By default, your user data is saved as `opus.xml` in the `/data` directory where Opus is located.

Should you wish to, you can specify the directory of the saved data using the following command below.

### 2.15. Specify data file directory : `save`

Saves all user data into a file in the specifed directory.
Format: `save DIRECTORY`

Example:

* `save C:/Users/Documents`
Saves data into the specified directory.

### 2.16. Autocomplete

Autocomplete the user's command on the CLI. This applies to the following commands:

* `add`
* `delete`
* `edit`
* `mark`
* `schedule`
* `list`
* `help`
* `undo`
* `redo`
* `clear`

> * Example:
    User enters `f` and presses `TAB`, `find` command will appear in the input box.

### 2.17. Exit : `exit`

Closes Opus.
> * All data is automatically saved by Opus upon quitting.

## 3. FAQ

**Q**: Where do I download the latest under development version of Opus?
**A**: You may download the latest unstable release of our application [here](../../../releases), but we strongly recommend using the stable build for your own sanity.

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Address Book folder.

## 4. Command Summary

* **Add**  `add NAME [n/NOTES] [p/PRIORITY] [b/STARTIME] [e/ENDTIME] [t/TAG]...` <br>
  e.g. `add Do laundry n/from the blue basket p/hi b/23/3/2018 12:00 e/23/3/2018 13:00 t/friend t/colleague`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Edit**  `edit INDEX NAME [n/NOTES] [p/PRIORITY] [b/STARTIME] [e/ENDTIME] [t/TAG]...` <br>
  e.g. `edit 2 Prepare dinner n/for 4 pax p/hi b/23/4/2017 12:00 e/23/3/2018 13:00 t/friend t/colleague`

* **Exit** `exit`

* **Find** : `find [KEYWORD]... [s/STATUS] [b/STARTIME] [e/ENDTIME] [p/PRIORITY]` <br>
  e.g. `find do homework p/hi e/04/04/2017`

* **Help** : `help` <br>

* **List** : `list` <br>

* **Mark** : `mark [INDEX]` <br>
  e.g. `mark 1`

* **Redo** : `redo` <br>

* **Save** : `save DIRECTORY` <br>

* **Schedule** : `schedule INDEX [STARTTIME to] ENDTIME` <br>
  e.g. `schedule 6 03/15/2017 11:00 to 03/15/2017 12:00`

* **Select** : `select INDEX` <br>
  e.g.`select 2`

* **Sort** : `sort KEYWORD` <br>
  e.g. `sort priority`

* **Unmark** : `unmark [INDEX]` <br>
  e.g. `unmark 1`

* **Undo** : `undo` <br>
