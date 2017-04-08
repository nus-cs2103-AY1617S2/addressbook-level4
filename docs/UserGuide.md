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
   * **`list`** : lists all events and tasks.
   * **`add`**` read CS2103 handout p/high` :
     adds a floating task with description read CS2103 handout and priority high to WhatsLeft.
   * **`delete ev`**` 3` : deletes the 3rd event shown in the current event list.
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
Shows help message.<br>
Format: `help [COMMAND]`

> * `COMMAND` is optional.Shows help message.
> * `COMMAND` should have format `add`, `recur`, `edit`, `find`, `select`, `delete`, `finish`, `redo`, `save` or `read`.
> * When there is no command specified or incorrect command e.g.`abcd`, a help window with all commands will be shown.
> * When the command is specified, the format and examples of the command will be shown.

### 2.2. Adding an event or task : `add`

#### 2.2.1. Adding an event : `add`

Adds an event to the event list. Undoable.<br>
Format: `add DESCRIPTION [st/START_TIME] sd/START_DATE [et/END_TIME] [ed/END_DATE] [l/LOCATION] [ta/TAG]...`

> * Events must be added with `DESCRIPTION` and `START_DATE` is required, while other fields are optional.
> * `START_DATE` and `END_DATE` can be easily input with `today`, `tmr` (tomorrow), `mon`, `tue`, `wed`, `thurs`, `fri`, `sat`, `sun`. `next` and `following` can be used in conjunction with the 7 days.
> * Otherwise, Start date, and end date should have format `DDMMYY`, e.g. `230117`
> * Start time and end time should have format `MMHH`, e.g. `2359`
> * Default value for `END_DATE` is set to be the same as `START_DATE`
> * Default value for `START_DATE` is set to be 00:01
> * Default value for `END_TIME` is set to be 23:59
> * `TAG` can contain only one word without space.
> * Events can have any number of tags (including 0).
> * Clashing events are allowed and will display as normal.
> * Events that are occuring on present day will be indicated with a badge.
> * If `DESCRIPTION` or `START_DATE` is not entered, help message for add will appear.

Examples:

* `add PhotoShop Workshop st/1900 sd/130717 ed/130717 l/CLB`
* `add Industrial Talk st/1800 sd/030717 et/2000 ed/030717 l/FoS ta/formal`
* `add Visit Museum st/1800 sd/next fri et/2000 l/SAM`

#### 2.2.2. Adding a recurring event : `recur`

Adds recurring instances of the same event. <br>
Format: `recur EVENT_INDEX FREQUENCY NUMBER_OF_TIMES`

> * Event must be chosen from the list of existing events.
> * Acceptable parameters for `FREQEUNCY` are `daily` and `weekly`.
> * Acceptable parameters for `NUMBER_OF_TIMES` are positive integers indicating the number days/weeks to recur the event for.

Examples:

* `recur 1 daily 4` <br>
   Automatically recur the 1st event for the next 4 days.
* `recur 4 weekly 12` <br>
   Automatically recur the 4th event for the next 12 weeks.

#### 2.2.3. Adding a task : `add`

Adds a floating task or deadline to the task list. <br>
Tasks have completion status, which is set to `pending` by default. <br>
User can mark a task as `completed` with `finish` command. (See section 2.9) <br>
Format: `add DESCRIPTION p/PRIORITY [bt/BY_TIME] [bd/BY_DATE] [l/LOCATION] [ta/TAG]...`

> * Tasks must be added with `DESCRIPTION` and `PRIORITY`, other fields are optional.
> * Priority can only take on the values `high`. `medium`, `low`, `h`, `m` or `l`.
> * `BY_DATE` can be easily input with `today`, `tmr` (tomorrow), `mon`, `tue`, `wed`, `thurs`, `fri`, `sat`, `sun`. `next` and `following` can be used in conjunction with the 7 days.
> * Otherwise, `BY_DATE` should have format `DDMMYY`, e.g. `230117`
> * `BY_TIME` should have format `MMHH`, e.g. `2359`
> * If only `BY_TIME` is specified, `BY_DATE` would be set as the current day by default.
> * If only `BY_DATE` is specified, `BY_TIME` would be set as `23:59` by default.
> * Tags can contain only one word without space.
> * Tasks can have any number of tags (including 0).
> * Tasks that have past the current date and time, or are happening today, will be marked with a badge.
> * If `DESCRIPTION` or `PRIORITY` is not entered, help message for add will appear.

Examples:

* `add Review CS2103 p/high ta/review`
* `add Buy groceries p/medium`
* `add Home Assignment 1 p/high bd/210317 l/general office ta/hardcopy`
* `add Project Report p/medium bt/2300 bd/120717 ta/softcopy`
* `add MKT2411 Report due p/medium bt/2300 bd/following sat ta/softcopy`

### 2.3. Listing all events and tasks : `list`

Shows the lists of all events and tasks in WhatsLeft.<br>
Format: `list`

### 2.4. Editing an event/task : `edit`

#### 2.4.1. Editing an event : `edit ev`

Edits an existing event in WhatsLeft. Undoable.<br>
Format: `edit ev INDEX [DESCRIPTION] [st/START_TIME] [sd/START_DATE] [et/END_TIME] [ed/END_DATE] [l/LOCATION] [ta/TAG]...`

> * Edits the event at the specified `INDEX`.
    The index refers to the index number shown in the last event list.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the event will be removed i.e adding of tags is not cumulative.
> * You can remove tags by typing `ta/` without specifying any content after it.
> * Removal of other attributes are not allowed.

Examples:

* `edit ev 2 st/0900 et/1300`<br>
  Edits the start time and end time of the 2nd event to be `09:00` and `13:00` respectively.

* `edit ev 3 Project Discussion ta/`<br>
  Edits the description of the 3rd event to be `Project Discussion` and clears all tags.

#### 2.4.2. Editing a task : `edit ts`

Edits an existing task in WhatsLeft. Undoable.<br>
Format: `edit ts INDEX [DESCRIPTION] [p/PRIORITY] [bt/BY_TIME] [bd\BY_DATE] [l/LOCATION] [ta/TAG]...`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove tags by typing `ta/` without specifying any content after it.

Examples:

* `edit ts 5 l/discussion room 3`<br>
  Edits the location of the 5th task to be `discussion room 3`.

* `edit ts 3 p/high ta/`<br>
  Edits the priority of the 3rd task to be high and clears the tags.

### 2.5. Finding all events and tasks containing any keyword in their description : `find`

Finds events and tasks whose description contains any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case insensitive. e.g `discussion` will match `Discussion`
> * The order of the keywords does not matter. e.g. `Project Discussion` will match `Discussion Project`
> * Only the description is searched.
> * Events/tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Project` will match `Project Discussion`

Examples:

* `find project discussion`<br>
  Returns 2 separated lists of events and tasks with description `project` or `discussion`.

### 2.6. Selecting an event or task : `select`

Views the specified event or task form the event or task list.

Format: `select TYPE INDEX`

> * Type should be `ev` or `ts`.
> * Selects the event or task at the specified `INDEX`.
> * The index refers to the index number shown in the most recent event or task listing.
> * The index **must be a positive integer** 1, 2, 3, ...
> * Event or task list and calendar will jump to the selected event or task.

Examples:

* `list`<br>
  `select ev 2`<br>
  Selects the 2nd event in the event list.

### 2.7. Deleting an event or task : `delete`

Deletes the specified event or task from WhatsLeft. Undoable.<br>
Format: `delete TYPE INDEX`

> * Type should be `ev` or `ts`.
> * Deletes the event or task at the specified `INDEX`.
> * The index refers to the index number shown in the most recent event/task listing.
> * The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete ev 2`<br>
  Deletes the 2nd event in the event list.
* `find exam`<br>
  `delete ev 1`<br>
  Deletes the 1st event in the results of the `find` command.

### 2.8. Clearing all entries : `clear`

Clears all events/tasks in WhatsLeft. Undoable.<br>
Format: `clear [TYPE]`

> * Type is optional.
> * If type is not specified, all events and tasks in WhatsLeft will be removed.
> * Type should be `ev` or `ts`.

Examples:

* `clear` <br>
  Removes all events, deadlines and tasks in WhatsLeft.

* `clear ev`<br>
  Removes all events in WhatsLeft.

### 2.9. Finishing/Redoing a task:
#### 2.9.1. Finishing a task:

Finishes the specified task in task list. Undoable.<br>
Format: `finish TASK_INDEX`

> * Finishes the task at the specified `TASK_INDEX`.
> * The index refers to the index number shown in the most recent task listing.
> * The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `finish 2`<br>
  Finishes the 2nd task in the task list.
* `find Assignment`<br>
  `finish 1`<br>
  Finishes the 1st task in the results of the `find` command.

#### 2.9.2. Redoing a completed task: `redo`

Redoes a completed task.<br>
Format: `redo INDEX`

> * Changes the status of a task from `completed` to `pending`.

Examples:

* `finish 2`<br>
  `show com`<br>
  `redo 1`<br>
  Changes the status of a completed task back to pending.

### 2.10. Task display preference: `show`

  Sets the display status preference.<br>
  User is able to view all, completed or pending tasks as well as past or future events.
  Format: `show [DISPLAY_PREFERENCE]`

> * Display preference should be empty, `com` or `pend` representing all activities, completed activities and pending activities respectively.

Examples:

* `show`<br>
   Display all events and tasks
* `show com`<br>
   Display all past events and completed tasks
* `show pend`<br>
   Display future events and pending tasks

### 2.11. Undoing the latest command : `undo`

Undoes the latest command.<br>
Format: `undo`

> * Undoes immediately after `add`, `edit`, `delete`, `clear` and `finish` commands.
> * Cannot undo more than once consecutively.

Examples:

* `finish 4`<br>
  `undo`<br>
  Undoes finishing task 4.

### 2.12. Save/Read WhatsLeft to/from new location: `save`
#### 2.12.1. Save WhatsLeft to new location: `save`
Changes the location of storage file to designated directory.<br>
Format: `save DIRECTORY`

> * Valid `DIRECTORY` should be entered.
> * WhatsLeft application will automatically load from the new storage location when started in the future.

Examples:

* `save /User/Andy/Documents`
  Saves the current WhatsLeft content to /User/Andy/Documents.

#### 2.12.2. Read WhatsLeft from new location
Loads WhatsLeft from storage file stored in the designated directory.<br>
Format: `read DIRECTORY`

> * Valid `DIRECTORY` should be entered.
> * WhatsLeft application will automatically load from the new storage location when started in the future.
> * If no WhatsLeft storage file exists in the designated directory, a blank task book will be created and loaded.

Examples:

* `read /User/Andy/Documents`
  Loads WhatsLeft from the WhatsLeft storage file stored in /User/Andy/Documents.

### 2.13. Changing Calendar Week: `next`
Changes the weekly view on calendar according to specified weeks ahead.<br>
Format: 'next [WEEKS_AHEAD]'

> * `WEEKS_AHEAD` is an optional field
> * `WEEKS_AHEAD` must be an integer.

Examples:

* `next 2`<br>
  Changes the displayed calendar schedule to 2 weeks ahead.

### 2.14. Refreshing the Calendar: `refresh`
Refresh the calendar to show current week.<br>
Format: `refresh`

Examples:

* `refresh`<br>
  Changes the displayed calendar to show current week schedule.

### 2.15. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous WhatsLeft folder.

## 4. Command Summary

Feature | Command Format | Example |
-------- | :-------- | :--------- |
Show help message | `help [COMMAND]` | `help add`
Add an event | `add DESCRIPTION [st/START_TIME] sd/START_DATE [et/END_TIME] [ed/END_DATE] [l/LOCATION] [ta/TAG]...` | `add Industrial Talk st/1600 sd/030717 et/2000 l/FoS`
Add a recurring event | `recur EVENT_INDEX FREQUENCY NUMBER_OF_TIMES` |
Add a task | `add DESCRIPTION p/PRIORITY [bt/BY_TIME] [bd/BY_DATE] [l/LOCATION] [ta/TAG]...` | `add Home Assignment 1 bd/tmr l/general office ta/hardcopy`
List | `list` | `list`
Edits an event | `edit ev INDEX [DESCRIPTION] [st/START_TIME] [sd/START_DATE] [et/END_TIME] [ed/END_DATE] [l/LOCATION] [ta/TAG]...` | `edit ev 3 Project Discussion et/2300`
Edits a task | `edit ts INDEX [DESCRIPTION] [p/PRIORITY] [bt/BY_TIME] [bd\BYDATE] [l/LOCATION] [ta/TAG]...` | `edit ts 5 bd/next tue`
Find | `find KEYWORD [MORE_KEYWORDS]` | `find discussion`
Select | `select TYPE INDEX` | `select ev 2`
Delete | `delete TYPE INDEX` | `delete ts 3`
Clear | `clear [TYPE]` | `clear ev`
Finish a task | `finish TASK_INDEX` | `finish 3`
Change status preference | `show [DISPLAY_PREFERENCE]` | `show com`
Redo a task | `redo TASK_INDEX` | ``
Undo last operation | `undo` | `undo`
Change storage file location | `save DIRECTORY` | `save Desktop/Data`
Read data from location | `read DIRECTORY` | `read Desktop/MyData`
Changes week in calendar | `next WEEKS_AHEAD` | `next 2`
Refresh calendar | `refresh` | `refresh`
Exit WhatsLeft | `exit` | `exit`
