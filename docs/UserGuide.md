# WhatsLeft - User Guide

By : `Team CS2103JAN2017-W10-B4`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `whatsleft.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your WhatsLeft.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all events, deadlines and tasks.
   * **`add task`**` read CS2103 handout c/do quiz p/2` :
     adds a floating task named read CS2103 handout with comments do quiz and priority 2 to the task list.
   * **`delete deadline`**` 3` : deletes the 3rd deadline shown in the current deadline list.
   * **`exit`** : exits the app.
6. Refer to the [Features](#features) section below for details of each command.<br>


## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 2.1. Viewing help : `help`

Format: `help [COMMAND]`

> * Command is optional.
> * When there is no command specified, help will print help messages of all commands.
> * Help is also shown if you enter an incorrect command e.g. `abcd`

### 2.2. Adding an event/deadline/task : `add`

#### 2.2.1. Adding an event : `add`

Adds an event to the event list. Undoable.<br>
Format: `add DESCRIPTION [s/START_TIME] f/FROM_DATE [e/END_TIME] [u/TO_DATE] [t/COMMENTS]... [l/LOCATION]`

> * Events must be added with description.
> * From date is required, while other fields are optional.
> * From date and To date should have format `DDMMYY`, e.g. 230117.
> * Start time and end time should have format `XXXX`, e.g. 2359.
> * If end time is entered and end date is not entered, end date is set to be start date by default.
> * Events can have any number of comments(including 0).
> * If description is not entered, help message for add will appear.

Examples:

* `add PhotoShop Workshop s/1900 f/13-02-17 t/take laptop l/CLB`
* `add Industrial Talk s/1800 f/03-05-17 e/2000 l/FoS`

#### 2.2.2. Adding a deadline : `add`

Adds a deadline to the deadline list. Undoable.<br>
Format: `add DESCRIPTION b/BY_DATE [e/END_TIME] [t/COMMENTS]... [l/LOCATION]`

> * Deadlines must be added with description.
> * By date is required, while other fields are optional.
> * By date should have format `DDMMYY`, e.g. 230117.
> * End time should have format `XXXX`, e.g. 2359.
> * Deadlines can have any number of comments(including 0).
> * If description is not entered, help message for add will appear.

Examples:

* `add Home Assignment 1 b/21-03-17 t/submit hardcopy l/general office`
* `add Project Report b/12-04-17 e/2359 t/submit softcopy c/online discussion`

#### 2.2.3. Adding a task : `add`

Adds a task to the task list. Undoable.<br>
Format: `add DESCRIPTION [c/COMMENTS]... [l/LOCATION] p/PRIORITY`

> * Tasks must be added with description.
> * Priority is required, and can only take on the values "high", "medium" or "low", while other fields are optional.
> * Other fields are optional.
> * Tasks can have any number of comments(including 0).
> * If description is not entered, help message for add will appear.

Examples:

* `add Review CS2103 c/watch webcast p/1`
* `add Buy fruits l/FairPrice p/2`

### 2.3. Listing all events/deadlines/tasks : `list`

Shows the lists of all events/deadlines/tasks in WhatsLeft.<br>
Format: `list [TYPE]`

> * Type is optional.
> * If type is not specified, 3 separated lists of all events, deadlines and tasks will appear.
> * Type should be `event`, `deadline` or `task`.

Examples:

* `list` <br>
  Shows 3 separated lists of all events, deadlines and tasks in WhatsLeft.

* `list deadline`<br>
  Shows the list of all deadlines in WhatsLeft.

### 2.4. Editing an event/deadline/task : `edit`

#### 2.4.1. Editing an event : `edit ev`

Edits an existing event in WhatsLeft. Undoable.<br>
Format: `edit ev INDEX [DESCRIPTION] [s/START_TIME] [f/START_DATE] [e/END_TIME] [t/END_DATE] [c/COMMENTS]... [l/LOCATION]`

> * Edits the event at the specified `INDEX`.
    The index refers to the index number shown in the last event ing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing comments, the existing comments of the event will be removed i.e adding of comments is not cumulative.
> * You can remove start time, end time, end date, comments or location by typing `s/`, `e/`, `t/`, `c/` or `l/` without specifying any content after it.

Examples:

* `edit ev 2 s/0900 e/1300`<br>
  Edits the start time and end time of the 2nd event to be `0900` and `1300` respectively.

* `edit ev 3 Project Discussion c/`<br>
  Edits the description of the 3rd event to be `Project Discussion` and clears all comments.


#### 2.4.2. Editing a deadline : `edit dl`

Edits an existing deadline in WhatsLeft. Undoable.<br>
Format: `edit dl INDEX [DESCRIPTION] [b/BY_DATE] [e/END_TIME] [c/COMMENTS]... [l/LOCATION]`

> * Edits the deadline at the specified `INDEX`.
    The index refers to the index number shown in the last deadline listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing comments, the existing comments of the event will be removed i.e adding of comments is not cumulative.
> * You can remove end time, comments or location by typing `e/`, `c/` or `l/` without specifying any content after it.

Examples:

* `edit dl 1 e/2200`<br>
  Edits the end time of the 1st deadline to be `2200`.

* `edit dl 3 c/submit PDF file l/`<br>
  Edits the comments of the 3rd deadline to be `submit PDF file` and clears the location.

#### 2.4.3. Editing a task : `edit ts`

Edits an existing task in WhatsLeft. Undoable.<br>
Format: `edit ts INDEX [DESCRIPTION] [c/COMMENTS]... [l/LOCATION] [p/PRIORITY]`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing comments, the existing comments of the event will be removed i.e adding of comments is not cumulative.
> * You can remove comments, location or update the priority to be 0 by typing `c/`, `l/` or `p/` without specifying any content after it.

Examples:

* `edit ts 5 l/discussion room 3`<br>
  Edits the location of the 5th task to be `discussion room 3`.

* `edit ts 3 p/3 c/`<br>
  Edits the priority of the 3rd task to be 3 and clears the comments.

### 2.5. Finding all events/deadlines/tasks containing any keyword in their description : `find`

Finds events/deadlines/tasks whose descriptions contain any of the given keywords.<br>
Format: `find [TYPE] KEYWORD [MORE_KEYWORDS]`

> * Type is optional.
> * If type is not specified, 3 separated lists of matched events, deadlines and tasks will appear.
> * Type should be `ev`, `dl` or `ts`.
> * The search is case insensitive. e.g `discussion` will match `Discussion`
> * The order of the keywords does not matter. e.g. `Project Discussion` will match `Discussion Project`
> * Only the description is searched.
> * Events/deadlines/tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Project` will match `Project Discussion`

Examples:

* `find project discussion`<br>
  Returns 3 separated lists of events/deadlines/tasks having description `project` or `discussion`.

* `find ts survey`<br>
  Returns a task list of tasks having description `survey`.

### 2.6. Finding all events/deadlines happening on a certain day : `on`

Finds events and deadlines which happen on the given date.<br>
Format: `on DATE`

> * DATE should have format `DD-MM-YY`, `DD-MM` or `DD`.
> * If `DD-MM-YY` is entered, 2 separated lists of events and deadlines on the entered date will appear.
> * If `DD-MM` is entered, 2 separated lists of events and deadlines on the entered date in any year will appear.
> * If `DD` is entered, 2 separated lists of events and deadlines on the entered date in any month and any year will appear.

Examples:

* `on 23-04`<br>
  Returns 2 separated lists of all events and deadlines happening on 23rd April in any year.

### 2.7. Viewing an event/deadline/task : `view`

Views the specified event/deadline/task form the event/deadline/task list.

Format: `view TYPE INDEX`

> * Type should be `ev`, `dl` or `ts`.
> * Views the event/deadline/task at the specified `INDEX`.
> * The index refers to the index number shown in the most recent event/deadline/task listing.
> * The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `view ev 2`<br>
  Views the 2nd event in the event list.

### 2.8. Deleting an event/deadline/task : `delete`

Deletes the specified event/deadline/task from WhatsLeft. Undoable.<br>
Format: `delete TYPE INDEX`

> * Type should be `ev`, `dl` or `ts`.
> * Deletes the event/deadline/task at the specified `INDEX`.
> * The index refers to the index number shown in the most recent event/deadline/task listing.
> * The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete ev 2`<br>
  Deletes the 2nd event in the event list.
* `find exam`<br>
  `delete ev 1`<br>
  Deletes the 1st event in the results of the `find` command.

### 2.9. Finishing an event/deadline/task : `finish`

Finishes the specified event/deadline/task in WhatsLeft. Undoable.<br>
Format: `finish TYPE INDEX`

> * Type should be `ev`, `dl` or `ts`.
> * Finishes the event at the specified `INDEX`.
> * The index refers to the index number shown in the most recent event listing.
> * The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `finish ts 2`<br>
  Finishes the 2nd task in the task list.
* `find exam`<br>
  `delete ev 1`<br>
  Finishes the 1st event in the results of the `find` command.

### 2.10. Clearing all entries : `clear`

Clears all event/deadline/task in WhatsLeft. Undoable.<br>
Format: `clear [TYPE]`

> * Type is optional.
> * If type is not specified, all events, deadlines and tasks in WhatsLeft will be removed.
> * Type should be `ev`, `dl` or `ts`.

Examples:

* `clear` <br>
  Removes all events, deadlines and tasks in WhatsLeft.

* `clear dl`<br>
  Removes all deadlines in WhatsLeft.

### 2.11. Undoing the latest command : `undo`

Undoes the latest command.<br>
Format: `undo`

> * Undoes immediately after `add`, `edit`, `delete`, `finish` and `clear` commands.
> * Cannot undo more than once consecutively.

Examples:

* `finish ts 4`<br>
  `undo`<br>
  Undoes finishing task 4.

### 2.12. Redoing the previous undo command : `redo`

Redoes the previous undo command.<br>
Format: `redo`

> * Recovers if the latest command is `undo`.
> * Cannot redo more than once consecutively.

Examples:

* `edit dl 2 b/23-04-17`<br>
  `undo`<br>
  `redo`<br>
  Reverses the previous undo command.

### 2.13. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.14. Saving the data

Task list data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous WhatsLeft folder.

## 4. Command Summary

* **Help** : `help` <br>

* **Add Event** : `add DESCRIPTION [s/START_TIME] f/FROM_DATE [e/END_TIME] [u/TO_DATE] [t/COMMENTS]... [l/LOCATION]` <br>
  e.g. `add Industrial Talk s/1800 f/03-05-17 e/2000 l/FoS`

* **Add Deadline** : `add DESCRIPTION b/BY_DATE [e/END_TIME] [c/COMMENTS]... [l/LOCATION]`<br>
  e.g. `add Home Assignment 1 b/21-03-17 c/submit hardcopy l/general office`

* **Add Task** : `add DESCRIPTION [c/COMMENTS]... [l/LOCATION] p/PRIORITY`<br>
  e.g. `add Buy fruits l/FairPrice p/2`

* **List** : `list [TYPE]` <br>
  e.g. `list dl`

* **Edit Event** : `edit ev INDEX [DESCRIPTION] [s/START_TIME] [f/START_DATE] [e/END_TIME] [t/END_DATE] [c/COMMENTS]... [l/LOCATION]`<br>
  e.g. `edit ev 3 Project Discussion c/`

* **Edit Deadline** : `edit dl INDEX [DESCRIPTION] [b/BY_DATE] [e/END_TIME] [c/COMMENTS]... [l/LOCATION]`<br>
  e.g. `edit dl 1 e/2200`

* **Edit Task** : `edit ts INDEX [DESCRIPTION] [c/COMMENTS]... [l/LOCATION] [p/PRIORITY]`<br>
  e.g. `edit ts 5 l/discussion room 3`

* **Find** : `find [TYPE] KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find exam CS2103`

* **On** : `on DATE`<br>
  e.g. `on 23-04`

* **View** : `view TYPE INDEX`<br>
  e.g. `view ev 2`

* **Delete** : `delete TYPE INDEX` <br>
  e.g. `delete ts 3`

* **Finish** : `finish TYPE INDEX`<br>
  e.g. `finish ts 2`

* **Clear** : `clear [TYPE]`<br>
  e.g. `clear dl`

* **Undo** : `undo`

* **Redo** : `redo`

* **Exit** : `exit`
