# User Guide

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## Quick Start

1. Ensure you have Java version `1.8.0_60` or later installed in your computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

2. Download the latest `YOLOLOL.jar` from the [releases](../../../releases) tab.
3. Copy the file to the folder you want to use as the home folder for your task manager.
4. Double-click the file to start the app. The GUI should appear in a few seconds.
    > <img src="images/Ui.png" width="600">



5. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
6. Some example commands you can try:
   * **`add`** ` completeUserGuide d/28-Feb-2017 t/2359 p/high #Important`: adds a task named completeUserGuide to the task manager.
   * **`delete`**` 3` : deletes the 3rd item shown in the UI
   * **`exit`** : exits the app
   * **`help`** : displays help prompt
7. Refer to the [Features](#features) section below for details of each command.<br>

## Terminologies used

* Task: A job to be done that has a deadline.
* Event: An event to be participated that has a start and end time.
* Floating Task: A job to be done that has no deadline.
* Items: A generic term used to describe a collection of tasks, events and floating tasks.



## Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Words in `SQUARE_BRACKETS` are optional parameters.
> * Words with `...` after them can have multiple instances.
> * Parameters & optional parameters can be in any order.


### Viewing help : `help`

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`


### Adding an item: `add`

Adds an item to the task manager<br>
Format: `add NAME [st/STARTTIME] [et/ENDTIME] [d/DESCRIPTION] [p/PRIORITY] [#TAG]...`

> * Items without both `STARTTIME` and `ENDTIME` specified are considered as floating tasks. They will be auto-tagged as floating tasks.
> * Items with `ENDTIME` specified without specifying `STARTTIME` are considered tasks. They will be auto-tagged as tasks.
> * Items with both `STARTTIME` and `ENDTIME` or only `STARTTIME` specified are considered as events. They will be auto-tagged as events.
> * Priority only has 3 types: low, med, high.
> * You will be allocated a default `ENDTIME` for events without specified `ENDTIME`. 
>   * Case 1: There is no more later events for the day. The default `ENDTIME` will be the end of the day.
>   * Case 2: There is another event later during the same day. The default `ENDTIME` will be the start of the next event.

Examples:

* `add floatingtask d/description p/low #NotImportant`
* `add task st/28-Feb-2017 d/description p/med #NotSoImportant`
* `add event st/28-Feb-2017 et/31-Mar-2017 p/high #Important`


### Undoing the previous command : `undo`

Undos the previous command that is not the undo command.<br>
Format: `undo`

> * You can undo <strong>N</strong> commands that is not the undo command by typing undo <strong>N</strong> number of times.<br>
> * You can only revert up to 1 command that is not the undo command.<br>

### Editing an item : `edit`

Edits an existing item in the task manager.<br>
Format: `edit INDEX [st/STARTTIME] [et/ENDTIME] [d/DESCRIPTION] [p/PRIORITY] [#TAG]...`

> * Edits the item at the specified `INDEX`.
    The index refers to the index number shown in the last item listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the item will be removed i.e adding of tags is not cumulative.
> * You can delete the field of a parameter by specifing the parameter syntax  without anything behind it.
> * You can remove all the item's tags by typing `#` without specifying any tags after it.

Examples:

* `edit 1 et/9pm p/high #CS1010 d/`<br>
  Edits the end time ,priority and hashtag of the 1st item to be `9pm` , `high` and `#CS1010` respectively while deleting the description.

* `edit 2 et/9pm p/high #`<br>
  Edits the name of the 2nd item's deadline to `9pm` and priority to `high` and clears all existing tags.


### Finding all items containing any keywords in their names, tags and timings: `find`

Finds items whose names, tags and timings contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case in-sensitive. e.g `cs1231` will  match `CS1231`
> * Only the task name is searched.
> * Only full words will be matched e.g. `CS2103T` will not match `CS2103`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `OP` will match `OP1 OP`
> * `KEYWORD` can be dates, times, names or tags.

Examples:

* `find 1-Mar-2017`
  events, tasks and floating tasks lists show items that take place on 1-Mar-2017

* `find OP` <br>
  Returns `OP1`

* `find OP1 OP2 Lab1`<br>
  Returns Any task having names `Lab1`, `OP1`, or `OP2`


### Deleting specified items from the task manager: `delete`
Deletes the specified item from the task manager.<br>
Format: `delete INDEX`

> Deletes the item at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `delete 2`<br>
  Deletes the 2nd item in the task manager.
* `find MA1521 Tutorial 1`<br>
  `delete 1`<br>
  Deletes the 1st item in the results of the `find` command.


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

* **Help** : `help` <br>
  e.g.
  * `help`
  
* **Add Task** : `add TASKNAME d/DEADLINE DATE [t/DEADLINE TIME] [p/PRIORITY] [#TAG]...`<br>
  e.g.
  * `add completeUserGuide d/28-Feb-2017 t/2400 p/high #Important`
* **Add Floating Task** : `add FLOATINGTASKNAME [p/PRIORITY] [#TAG]...`<br>
  e.g. 
  * `add reachDiamondRank p/low #Overwatch`

* **Add Event** : `add EVENTNAME sd/START DATE ed/END DATE [st/START TIME] [et/END TIME] [p/PRIORITY]  [#TAG]...`<br>
  e.g. 
  * `add finishCS3230Lab sd/01-Mar-17 ed/01-Mar-17 st/2000 et/2200 p/med #CS3230`
  * `add finishCS3230Lab sd/01-Mar-17 st/2000 p/med #CS3230`
* **Undo** : `undo` <br>
  e.g.
  * `undo`
* **Edit**  `edit INDEX [t/DEADLINE TIME] [p/PRIORITY] [#TAG]...`<br>
  e.g.
  * `edit 1 t/9pm p/high #CS1010`

* **Clear** : `clear`
  e.g.
  * `clear`
* **Delete** : `delete INDEX` <br>
  e.g.
  * `delete 3`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g.
  * `find MA1101R assignment`
  * `find 01-Mar-2017`