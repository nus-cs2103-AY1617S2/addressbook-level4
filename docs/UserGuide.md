# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.

1. Download the latest `ezDo.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your ToDo List.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will display help.
5. Some example commands you can try:
   * **`add`** : adds a task (e.g. `add buy some milk  d/12/12/2015 p/1`)
   * **`edit`** : edits an existing task (e.g. `edit 1 buy some milk and cheese p/2`)
   * **`kill`** : deletes a task in the current list (e.g. `kill 1`)
   * **`quit`** : quits the app (e.g. `quit`)
6. Refer to the [Features](#features) section below for details of each command.<br>

## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.


#### Viewing help : `help / h`
Brings up the help guide.<br>
Format: **`help`**

> Help is also shown if you enter an incorrect command e.g. **`abcd`**

#### Adding a task: `add / a`
Adds a task to ezDo.<br>
Format: **`add TASKNAME [p/PRIORITY] [s/STARTDATE] [d/DUEDATE] [t/TAGNAME]...`**

> Tasks can have any number of tags (including 0).<br>
> Tasks only need a task name. The other fields are optional, and can be typed in any order as long as the prefix is there.<br>
> The task name MUST come first.<br>
> The format for the date should be DD/MM/YY <br>
> Priority takes in a value from 1-3, with 3 being the highest priority.

Examples:
* **`add Buy donuts`**
* **`add Buy milk d/12/12/2017 p/3 t/calories`**
* **`add Read a book p/1 s/05/05/2017 t/knowledge t/bookworm`**

#### Editing a task: `edit / e`
Edits a particular task according to the index.<br>
Format: **`edit INDEX [NEWTASKNAME] [p/NEWPRIORITY] [s/NEWSTARTDATE] [d/NEWDUEDATE] [t/NEWTAGNAME...]`**

> Tasks only need a task name. The other fields are optional, and can be typed in any order as long as the prefix is there.<br>
> The task name MUST come first.<br>
> The format for the date should be DD/MM/YY <br>
> Priority takes in a value from 1-3, with 3 being the highest priority.<br>
> To clear a field (except task name which cannot be cleared), type out the prefix but leave it blank.<br>

Examples:
* **`edit 1 Sell donuts p/3 t/money s/12/12/2017`**
* **`edit 1 p/ t/ s/`**
* **`edit 1 Buy more donuts p/3 t/nomoney s/15/12/2017`**

#### Finding all tasks containing any keyword in their name or by dates/priority/tags: `find / f`
Finds tasks whose names contain any of the given keywords.<br>
Format: **`find [KEYWORD] [MORE_KEYWORDS] [p/PRIORITY] [s/STARTDATE] [d/DUEDATE] [t/TAGNAME]`**

> At least one field is needed to search.<br>
> The search is case insensitive. e.g `milk` will match `Milk`<br>
> The order of the keywords does not matter. e.g. `Buy Milk` will match `Milk Buy`<br>
> Only full words will be matched e.g. `Milk` will not match `Milks`<br>
> Tasks matching all keywords will be returned (i.e. `AND` search).
    e.g. `Sell Milk` will not match `Buy Milk`
    e.g. `donuts milk` will match `Buy donuts and milk`<br>
> To go back to the default view after having found your task, type **list**

Examples:
* **`find d/20/03/2017`**<br>
  Returns all tasks that have a due date on `20/03/2017`
* **`find Buy The Milk`**<br>
  Returns only tasks containing `Buy`, `The`, and `Milk`
* **`find School p/1`**<br>
  Returns only tasks containing `School` with a priority of 1.

#### Deleting a task : `kill / k`
Deletes the specified task from the todo list.<br>
Format: `kill INDEX`

> Deletes the todo at the specified `INDEX`.
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples:
* `kill 2`
  Deletes the 2nd task in the most recent view of the todo list.

* `kill 1`
  Deletes the 1st task in the most recent view of the todo list.

#### Sort a list of task `sort / s`
> Sort the list of tasks in a certain order (p - priority, d - deadline)
> Format: `sort ORDERTYPE`

Examples: `sort p'

#### Marking a task as done : `done / d`
Marks the specified task from the todo list as done. <br>
Format: `done INDEX`

> Marks the todo at the specified `INDEX` as done.
 The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples:
* `done 3`<br>

#### Clearing all entries : `clear / c`
Clears all entries from the todo list.<br>
Format: `clear`
* user will be prompted with clear confirmation



#### Undoing the last action : `undo / u`
Undos the last action.<br>
Format: `undo`

#### Exiting the program : `quit / q`
Quits the program.<br>
Format: `quit`

#### Moving the save file : `save`
Moves the save file of ezDo.<br>
Format: `save PATH`
* `save C:\Desktop\savefile.txt`

#### Saving the data
Todo list data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous ezDo folder.

## Command Summary

* **Add**  `add TASK [d/DATELINE p/PRIORITY t/TAG]...` <br>
  e.g. `add Get rich d/2019 p/high t/impossible`

* **Clear** : `clear`

* **Kill** : `kill INDEX` <br>
   e.g. `kill 3`

* **Done** : `done INDEX` <br>
   e.g. `done 3`

* **Edit** : `edit INDEX [TASK NAME] [d/DEADLINE] [p/PRIORITY] [t/TAGNAME]` <br>
   e.g. `edit 3 Buy the milk p/high`

* **Find** : `find KEYWORD [MORE_KEYWORDS] [t/TAGNAME] [d/DEADLINE]` <br>
  e.g. `find buy milk t/groceries`

* **Sort** : `sort ORDERTYPE` <br>
  e.g. `sort d`


* **Help** : `help` <br>
  e.g.

* **Undo** : `undo` <br>
  e.g.`undo`

* **Save** : `save PATH` <br>
  e.g. `save C:\Users\BestKorea\Documents\tasks.txt`

* **Quit** : `quit` <br>
  e.g. `quit`
