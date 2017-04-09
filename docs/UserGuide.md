# YATS Yet Another Task Scheduler - User Guide

By : `Team T16-B4`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#1-quick-start)
2. [Features](#2-features)
3. [FAQ](#3-faq)
4. [Command Summary](#4-command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `YATS.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Scheduler.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks that have not been completed
   * **`add`**` My task -s July 23 -T Tag1` :
     adds a task named `My Task` to the Task Scheduler.
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

> Help is also shown if you enter an incorrect command e.g. `abcd`

### 2.2. Adding a floating task: `add`

Adds a task with only a title <br>

Format: `add TASK_NAME`
> Tasks can have any number of tags (including 0)

Examples:

* `add task math homework`
* `add task pay bill`

### 2.3 Adding a event task : `add`

Adds an task with start and/or end time to the task scheduler. <br>

Format: `add  EVENT_NAME -s START_TIME -e END_TIME [-l LOCATION  -d DESCRIPTION -T TAGS]`
or
Format: `add  EVENT_NAME -t TIME [-l LOCATION -d DESCRIPTION -T TAGS]`
> - All paramters must not contain any of the prefixes(-l, -r, -s, -e, -by, -d, -T)
> - TIME will be automatically resolved to start/end time or deadline
> - START_TIME/END_TIME cannot exist together with TIME
> - Events can have any number of tags (including 0)
> - If only start or end time is specified, the other will be automatically filled with a duration of 2 hours
> - Any time parameter(START_TIME/END_TIME/TIME) allows input of natural language, but it has certain limitations regarding format of input, ssee [Q&A](#timeInput) for more information.

Examples:

* `add meeting with boss -l office -s 7:00pm  -e 9:00pm  -d prepare progress report -T work`

### 2.4 Adding a deadline task : `add`

Adds a task that has to be done by a deadline. <br>

Format: `add  EVENT_NAME -by DEADLINE [-l LOCATION -d  DESCRIPTION -T TAGS]`
or
Format: `add  EVENT_NAME -t TIME [-l LOCATION -d DESCRIPTION -T TAGS]`
> - All paramters must not contain any of the prefixes(-l, -r, -s, -e, -by, -d, -T)
> - TIME will be automatically resolved to start/end time or deadline
> - START_TIME/END_TIME/DEADLINE cannot exist together with TIME
> - Events can have any number of tags (including 0)
> - Any time parameter(DEADLINE/TIME) allows input of natural language, but it has certain limitations regarding format of input, see [Q&A](#timeInput) for more information.

Examples:

* `add buy milk -l FairPrice -by 7pm tomorrow  -d get meiji banana mik -T food`

### 2.5 Adding recurring tasks : `add`

Adds tasks that repeats periodically. <br>

Format: `add  EVENT_NAME -r PERIODICITY [-l LOCATION -s START_TIME -e END_TIME -d  DESCRIPTION -T TAGS]`
or
Format: `add  EVENT_NAME -r PERIODICITY [-l LOCATION -by DEADLINE -d DESCRIPTION -T TAGS]`
or
Format: `add  EVENT_NAME -r PERIODICITY [-l LOCATION -t TIME-d DESCRIPTION -T TAGS]`
> - All paramters must not contain any of the prefixes(-l, -r, -s, -e, -by, -d, -T)
> - TIME will be automatically resolved to start/end time or deadline
> - DEADLINE cannot exist together with TIME
> - Events can have any number of tags (including 0)
> - Accepted PERIODICITY can be daily/weekly/monthly/yearly
> - Recurring tasks can be recurring deadline tasks or recurring event tasks
> - If only start or end time is specified, the other will be automatically filled with a duration of 2 hours
> - Any time parameter(START_TIME/END_TIME/DEADLINE/TIME) allows input of natural language, but it has certain limitations regarding format of input, see [Q&A](#timeInput) for more information.

Examples:

* `add meeting with boss -l office -r daily -s 7:00pm  -e 9:00pm  -d prepare progress report -T work`

### 2.6. Listing all tasks : `list`

Shows a list of all tasks in the task scheduler.<br>
Format: `list`

### 2.7. Listing tasks containing any keyword in attributes: `list by`

Shows the tasks that are linked to a specific string or a specific date.<br>
Format: `list by ATTRIBUTE KEYWORD [MORE_KEYWORDS]...`

Where ATTRIBUTE can be location, start, end, deadline, tag
Where KEYWORD can be the following:
1. LOCATION and TAG can be any string
2. START, END and DEADLINE can only be of the following format: 
> 2.1 TIME: hh:MM in the 12 hour format, for example, 09:00PM, 10:00AM <br>
> 2.2 DATE: dd/MM/yyyy <br>

> * When typing an invalid attribute after `list by`, the application defaults to `list`
> * The list search is not case sensitive. e.g `School` will match `school`
> * The order of the keywords does not matter. e.g. `singapore Work` will match `Work singapore`
> * Only location will be searched.
> * Only full words will be matched e.g. `Singa` will not match `Singapore`
> * Tasks matching at least one keyword will be returned e.g. `meeting` will match `meeting MarinaSands`

Examples:

* `list by location My School`<br>
  Returns `School`, `My`
* `list by tag date1 tag2 Task3`<br>
  Returns any task that has the tags of the keywords `date1` , `tag2`  or `Task3`

### 2.8 Finding tasks containing any keyword in title and description: `find`

Shows the tasks that are linked to a specific string. <br>
Format: `find KEYWORD [MORE KEYWORD]`

Where KEYWORD can be a title or part of a description
> * The find search is not case sensitive. e.g `School` will match `school`
> * The order of the keywords does not matter. e.g. `calling Bob` will match `Bob calling`
> * Titles and description will be searched.
> * Only full words will be matched e.g. `home` will not match `homework`
> * Tasks matching at least one keyword will be returned e.g. `meeting` will match `meeting Bob`

Examples:

* `find My Task`<br>
  Returns `My Task` but not `task`
* `find date1 tag2 Task3`<br>
  Returns any task having any of the keywords `date1` , `tag2`  or `Task3`

### 2.9. Editing a task : `edit`

Edits an existing task in the task scheduler.<br>
Format: `edit INDEX [-l LOCATION -s START_TIME -e END_TIME -by DEADLINE -d DESCRIPTION -T TAGS]`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * You can remove all the task's tags by typing `-T` without specifying any tags after it.
> * You can add on the the tags by specifying new tags.
> * PERIODICITY is not editable
> * The date time information of a recurring task cannot be edited.

Examples:

* `edit 1-by 2 Feb 2017 -T school`<br>
  Edits the deadline of the 1st task to be 02-02-2017 and adds on the tag "school".

* `edit 2 Math Homework -T`<br>
  Edits the name of the 2nd task to be Math Homework and clears all existing tags.

### 2.10. Deleting a task : `delete`

Deletes the specified task from the task scheduler.<br>
Format: `delete INDEX`

> When deleting tasks of the same name, the task that ends earlier regardless of date will be deleted first
> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...
> The index must be within the specified list of indexes available
> Can be performed in batches by specifying more than one valid integer separated by spaces

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the undone list of the task scheduler.
* `list Task2`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `list` command.
* `list`<br>
  `delete 1 2 3`<br>
  Deletes the 1st, 2nd and 3rd tasks in the undone list of the task scheduler.

### 2.11. Mark task as done : `mark`

Marks the specified task from the task scheduler as done.
Format: `mark INDEX`

> Marks the task at the specified `INDEX` as done. <br>
> The index refers to the index number shown in the undone tasks listing.<br>
> The index **must be a positive interger** 1, 2, 3, ...
> The index must be within the specified list of indexes available
> Can be performed in batches by specifying more than one valid integer separated by spaces
> Marking a recurring task as done will display the next undone occurence of the task

Examples:

* `mark 1`<br>
  Marks the 1st task in the task scheduler as done.
  
* `mark 1 2 3`<br>
  Marks the 1st, 2nd and 3rd tasks in the done task list of the task scheduler as done.

### 2.12. Mark task as not done : `unmark`

Marks the specified task from the task scheduler as done.
Format: `unmark INDEX`

> Marks the task at the specified `INDEX` as not done. <br>
> The index refers to the index number shown in the most done tasks listing.<br>
> The index **must be a positive interger** 1, 2, 3, ...
> The index must be within the specified list of indexes available
> Can be performed in batches by specifing more than one valid integer separated by spaces
> Marking a recurring task as undone will display the next undone occurence of the task

Examples:

* `unmark 1`<br>
  Marks the 1st task in the done task list of the task scheduler as not done.
  
* `unmark 1 2 3`<br>
  Marks the 1st, 2nd and 3rd tasks in the done task list of the task scheduler as not done.

### 2.13. Clearing all done tasks : `clear`

Clears all done tasks from TaskManager <br>
Format: `clear`

### 2.14. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.15. Saving the data

Task Scheduler data are saved in the hard disk automatically after any command that changes the data.<br>

There is no need to save manually.

### 2.16. Changing save location : `save`

Changes the file directory of the save location to a file directory specified by the user.<br>
Format: `save`

> File directory specified must be exisiting file directory in disk
> File directory specified cannot be same as current save location file directory

Example:

* `save default`
   Changes save location to the default file directory
* `save /Users/main/Desktop/data`
   Saves the data file of the task scheduler to /Users/main/Desktop/data/YATS.xml


### 2.17. Undo : `undo`

Undo the last command that was performed in the task scheduler
Format: `undo`

> Maximum of 5 `undo` can be performed after last save

Example:

* `delete 2`
   Deletes the second task in the task scheduler
* `undo`
   Undoes the delete that was just done

### 2.18. Redo : `redo`

Redo an undone command in the task scheduler
Format: `redo`

> Maximum of 5 `redo` can be performed after 5 `undo`

Example:

* `delete 2`
   Deletes the second task in the task scheduler
* `undo`
   Undoes the delete that was just done
* `redo`
   Redoes the previous undone delete

### 2.19. Clear all tasks : `reset`

Clears all tasks from TaskManager <br>
Format: `reset`

### GUI

## 2.20 Done List View

The Done List View displays all the done tasks the user has marked done

## 2.21 Calendar View

The Calendar List View displays all undone tasks on the selected day and does not display
deadline tasks. 
The selected day is scrollable using the the 2 arrow buttons, and can be reset to current day
by pressing on the `Today` button.

## 3. FAQ

**Q**: How do I transfer my data to another Computer? <br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Scheduler folder.
<a name="timeInput"/></a>
**Q**: What date time format is allowed? <br>
**A**: The input of date time can be done using natural language. Input such as "9am next monday" or "25 of next month" 
can be recognized. However, when you want to specify 2 dates in TIME for a event, you must use "to" to separate 2 dates.
Besides, formatted input with ambiguity of month and day can cause an error in time stored, so input such as "02/06/2017" or "08.06.1997"
should not be used. It will be safe to always use name of the month, e.g. "January" instead of "01".


## 4. Command Summary

* **Add Floating Task** : `add task NAME ` <br>
   e.g. `add task math homework`

* **Add Event Task** : `add  EVENT_NAME -s START_TIME -e END_TIME [-l LOCATION  -d DESCRIPTION -T TAGS]` <br>
   e.g. `add meeting with boss -l office -s 7:00pm  -e 9:00pm  -d prepare progress report -T work`

* **Add Deadline Task** : `add  EVENT_NAME -by DEADLINE [-l LOCATION -d  DESCRIPTION -T TAGS]` <br>
   e.g. `add buy milk -l FairPrice -by 7pm tomorrow  -d get meiji banana mik -T food`
   
* **Add Recurring Task** : `add  EVENT_NAME -r PERIODICITY [-l LOCATION -t TIME-d DESCRIPTION -T TAGS]` <br>
   e.g. `add meeting with boss -l office -r daily -s 7:00pm  -e 9:00pm  -d prepare progress report -T work`

* **Mark as Done** : `mark INDEX` <br>
   e.g. `mark 1`
   e.g. `mark 1 4 6`

* **Mark as Not Done** : `unmark INDEX` <br>
   e.g. `unmark 1`
   e.g. `unmark 2 3 4`

* **Clear Done Task** : `clear` <br>
   e.g. `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`
   e.g. `delete 2 4 6`

* **Find** : `list ATTRIBUTE KEYWORD [MORE_KEYWORDS]` <br>
   e.g. `list by tag homework`
   e.g. `list by timing 9:00pm`

* **List** : `list` <br>
   e.g. `list`

* **Change Save Location** : `save` <br>
   e.g. `save default`
   e.g. `save /Users/main/Desktop/data`

* **Help** : `help` <br>
   e.g. `help`

* **Undo** : `undo` <br>
   e.g. `undo`

* **Redo** : `redo` <br>
   e.g. `redo`
   
* **Clear all tasks** : `reset` <br>
   e.g. `reset`
