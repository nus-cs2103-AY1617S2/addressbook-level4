# AddressBook Level 4 - User Guide

By : `Team SE-EDU`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jun 2016`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `addressbook.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Address Book.
3. Double-click the file to start the app. The GUI should appear in a few seconds.

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : Returns a numbered list of all tasks/events yet undone in the tasks manager
   * **`add`**` Study for midterms dl/2017-3-2-2359 des/CS2103 at LT7` :
        Add a Task named “Study for midterms” with deadline “2017 2nd of March 23:59pm” with 
        task description of “CS2103 at LT7”

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

### 2.2. Adding a task/event : `add`

Adds a task/event to the address book<br>

Task: 

Format: `add task TASK_NAME [dl/DEADLINE] [des/DESCRIPTION] [t/TAG]...`

> Tasks can have a deadline
> Tasks can have a description
> Tasks can have any number of tags (including 0)


Examples:
* `add Do laundry dl/2017-3-1-2359 `
* `add Study for midterms dl/2017-3-2-2359 des/CS2103 at lt7 `
* `add Buy milk for baby dl/2017-3-3-2100 des/yaas milk t/family`
* `add Create user story dl/2017-4-1-1300 t/work t/computing` 

Event: 

Format: `add event EVENT_NAME st/START_TIME et/END_TIME [des/DESCRIPTION] [t/TAG]...`

> * Event can have description
> * Events can have any number of tags (including 0)


Examples:
* `Add Midterms st/2017-3-1-1300 et/2017-3-1-1500 des/CS2103 t/school`
* `Add Kaili Birthday st/2017-8-30-0000 et/2017-8-31-2359 t/friends`
* `Add Gym legs day st/2017-3-4-0600 et/2017-3-4-0700 t/health`

### 2.3. Finding all tasks/events containing any keyword in their name or tag : `find`


Finds tasks/events whose names contain any of the given keywords.<br>

Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g homework will not match Homework
> * The order of the keywords does not matter. e.g. homework due will match due homework
> * Only names and tags are searched.
> * Persons matching at least one keyword will be returned (i.e. OR search). e.g. Hans will match Hans Bo

Examples:
* find Milk
* Returns a numbered list of undone tasks/events whose names/ tags contain buy Milk but not milk
* find work family friends
* Returns a numbered list of undone tasks/events whose names/ tags contain work, family or friends


### 2.4. Listing all tasks/events : `list`


Shows a list of all tasks/events in the tasks manager.<br>
Format: `list`

Examples:

* `list`
* `Returns a numbered list of all tasks/events yet undone in the tasks manager`


### Show tasks/events that are done : `archive`

Shows a list of all task that are marked as done and events that has passed in the task manager.<br>
Format: `archive [/task] [/event]`

> * Tasks/events is arranged by deadline, with the most recent dateline first
> * Only the past one month worth of event/task can be done

Examples:

* `archive`<br>
  Returns a list of tasks that have been marked as done and or events that have passed
* `archive task`<br>
  Returns a list of tasks that have been marked as done 
* `archive event`<br>
  Returns a list of events that have passed
* `archive task event`<br>
  Returns a list of tasks that have been marked as done and or events that have passed

### 2.6. Editing a Task/event : `edit`

Edits an existing task/event in the address book.<br>
Format: `edit INDEX [n/NAME] [dl/DEADLINE] [st/START_TIME] [et/END_TIME] [des/DESCRIPTION] [t/TAG]...`

>Edits the task/event at the specified INDEX. The index refers to the index number shown in the last task listing printed out when the search or list function was called.<br>

> * The index must be a positive integer 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * If the selected index is a task, there should not be entries that edit end time or start time.
> * If the selected index is an event, there should not be entries that edit deadlines
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task/event will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by typing t/ without specifying any tags after it.

Examples:

* `edit 1 n/buy milk dl/2017-3-2-2359`<br>
  Edits the name and deadline of the task ( numbered 1 on the list) to be buy milk and 2017-3-2-2359 respectively.
* `edit 2 n/midterm exam st/2017-3-3-1000`<br>
  Edits the name and start time of the event(numbered 2 on the list) to be midterm exam and st/2017-3-3-1000 respectively


### 2.7.  Deleting task(s): `delete`


Deletes the specified task(s) from the task manager.<br>
Format: `delete INDEX,[INDEX]...`

> * Deletes the task(s) at the specified INDEX. <br>
> * The index refers to the index number shown in the most recent listing.<br>
> * The index **must be a positive integer** 1, 2, 3, ...<br>

Examples:

* `list`<br>
  `delete 2,3`<br>
  Deletes the 2nd and 3rd task in the list of task.
* `find meeting` <br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 2.8. Mark task(s) as done : `done`

Mark the specified task(s) as done.<br>
Format: `done INDEX,[INDEX]...`

> * Mark the task(s) as done at the specified INDEX. 
> * The index refers to the index number shown in the most recent listing.
> * The index must be a positive integer 1, 2, 3, ...

Examples:

* `list`<br>
   `done 2,3`<br>
   Mark the 2nd and 3rd task in the list as done.

### 2.9. Clearing all entries : `clear`

Clears all entries from the task manager.<br>
Format: `clear`

### 2.10. Undo the last command : `undo`

Undo the last command input by the user.<br>
Format: `undo`

### 2.11. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.12. Saving the data

Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Address Book folder.

## 4. Command Summary

* **Add :** <br>
> * **Task :**<br>
> *`add task TASK_NAME [dl/DEADLINE] [des/DESCRIPTION] [t/TAG]...` <br>
> * **Event :**<br>
> *`add event EVENT_NAME st/START_TIME et/END_TIME [des/DESCRIPTION] [t/TAG]...` <br>

* **Clear** : `clear`<br>

* **Delete** : `delete INDEX [INDEX]...` <br>
   e.g. `delete 3`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find milk`

* **List** : `list` <br>
  e.g. `list`

* **List** : `undo` <br>
  e.g. `undo`
 
* **Done** : `done` <br>
  e.g. `done 2`

* **Archive** : `archive [/task][/event]` <br>
  e.g. `archive /task`

* **edit** : `edit TASK_ID[n/NAME] [dl/DEADLINE] [st/START_TIME] [et/END_TIME] [des/DESCRIPTION] [t/TAG]...` <br>

* **Help** : `help` <br>
 



