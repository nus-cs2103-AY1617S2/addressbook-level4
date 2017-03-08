# User Guide

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## Quick Start

1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

2. Download the latest `YOLOLOL.jar` from the [releases](../../../releases) tab.
3. Copy the file to the folder you want to use as the home folder for your task manager.
4. Double-click the file to start the app. The GUI should appear in a few seconds.
    > <img src="images/Ui.png" width="600">



5. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
6. Some example commands you can try:
   * **`addTask`** ` completeUserGuide d/28-Feb-2017 t/2359 p/high #Important`: adds a task named completeUserGuide to the task manager.
   * **`delete`**` 3` : deletes the 3rd item shown in the UI
   * **`exit`** : exits the app
   * **`help`** : displays help prompt
7. Refer to the [Features](#features) section below for details of each command.<br>

## Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### Viewing help : `help`

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

### Adding a task: `addTask`

Adds a task to the task manager<br>
Format: `addTask TASKNAME d/DEADLINE DATE [t/DEADLINE TIME] [p/PRIORITY] [#TAG]...`

> Tasks can have any number of tags (including 0)
> Tasks can only have one priority
> Tasks can only have one deadline time
> Tasks must have a deadline date

Examples:

* `addTask completeUserGuide d/28-Feb-2017 t/2400 p/high #Important`

### Adding a floating task: `addFTask`

Adds a floating task to the task manager<br>
Format: `addFTask FLOATINGTASKNAME [p/PRIORITY] [#TAG]...`

> Floating Tasks can have any number of tags (including 0)
> Floating Tasks can only have one priority
> Floating Tasks cannot have any deadlines

Examples:

* `addFTask reachDiamondRank p/low #Overwatch`

### Adding an event: `addEvent`

Adds an event to the task manager<br>
Format: `addEvent EVENTNAME sd/START DATE ed/END DATE [st/START TIME] [et/END TIME] [p/PRIORITY]  [#TAG]...`

> Events can have any number of tags (including 0)
> Events can only have one priority
> Events must have a start and end date
> If no start time or end time specified, system will assume start at 0000 on start date and end at 2359 on end date

Examples:

* `addEvent finishCS3230Lab sd/01-Mar-17 ed/01-Mar-17 st/2000 et/2200 p/med #CS3230`

### Editing a task : `edit`

Edits an existing task in the task manager.<br>
Format: `edit INDEX [t/DEADLINE TIME] [p/PRIORITY] [#TAG]...`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by typing `#` without specifying any tags after it.

Examples:

* `edit 1 t/9pm p/high #CS1010`<br>
  Edits the deadline, priority and hashtag of the 1st task to be `9pm` , `high` and `#CS1010` respectively.

* `edit 2 t/9pm p/high `<br>
  Edits the name of the 2nd task's deadline to `9pm` and priority to `high` and clears all existing tags.

### Finding all tasks containing any keyword in their name: `find`

Finds tasks whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case in-sensitive. e.g `cs1231` will  match `CS1231`
> * Only the task name is searched.
> * Only full words will be matched e.g. `CS2103T` will not match `CS2103`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `OP` will match `OP1 OP`

Examples:

* `find OP` <br>
  Returns `OP1`

* `find OP1 OP2 Lab1`<br>
  Returns Any task having names `Lab1`, `OP1`, or `OP2`

### Deleting specified items from the task manager: `delete`
Deletes the specified task from the task manager.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task manager.
* `find MA1521 Tutorial 1`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.


###  Listing all tasks: `filter`
Shows tasks and events that take place on specified date and hide others
Floating Task list remain unchanged
Format: `filter DATE`

Examples:

* 'filter 1-Mar-2017'
  Both task and floating task lists show items that take place on 1-Mar-2017


### Clearing all entries : `clear`

Clears all entries from the Task Manager.<br>
Format: `clear`

### Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### Saving the data

Program data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous task manager folder.

## Command Summary

* **Add Task** : `addTask TASKNAME d/DEADLINE DATE [t/DEADLINE TIME] [p/PRIORITY] [#TAG]...`<br>
  e.g. `addTask completeUserGuide d/28-Feb-2017 t/2400 p/high #Important`

* **Add Floating Task** : `addFTask FLOATINGTASKNAME [p/PRIORITY] [#TAG]...`<br>
  e.g. `addFTask reachDiamondRank p/low #Overwatch`

* **Add Event** : `addEvent EVENTNAME sd/START DATE ed/END DATE [st/START TIME] [et/END TIME] [p/PRIORITY]  [#TAG]...`<br>
  e.g. `addEvent finishCS3230Lab sd/01-Mar-17 ed/01-Mar-17 st/2000 et/2200 p/med #CS3230`

* **Edit**  `edit INDEX [t/DEADLINE TIME] [p/PRIORITY] [#TAG]...`<br>
* e.g. `edit 1 t/9pm p/high #CS1010`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find MA1101R assignment`

* **Filter** : `filter DATE` <br>
  e.g. `filter 01-Mar-2017`

* **Help** : `help` <br>
  e.g. `help`

