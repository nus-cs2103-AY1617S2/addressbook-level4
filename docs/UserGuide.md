# Doist - User Guide

By : `W13-B4`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jan 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#1-quick-start)
2. [Features](#2-features)
    - [Viewing help](#viewing-help--help)
    - [Adding a new task](#adding-a-new-task--add-do)
    - [Listing tasks](#listing-tasks--list-ls)
    - [Finding all tasks containing any search key in their description](#finding-all-tasks-containing-any-search-key-in-their-description--find)
    - [Deleting tasks](#deleting-tasks--delete)
    - [Marking tasks as finished](#marking-tasks-as-finished--finish)
    - [Editing an existing task](#editing-an-existing-task--edit)
    - [Listing all the tags](#listing-all-the-tags--list-tag-ls-tag)
    - [Undoing the previous commands](#undoing-the-previous-commands--undo)
    - [Clearing all entries](#clearing-all-entries--clear)
    - [Set an alias for a command word](#set-an-alias-for-a-command-word--alias)
    - [Exiting the program](#exiting-the-program--exit)
    - [Changing the storage destination](#changing-the-storage-destination--save-at)
    - [Saving the data](#saving-the-data)
3. [FAQ](#3-faq)
4. [Command Summary](#4-command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.

>    Having any Java 8 version is not enough.
>    This app will not work with earlier versions of Java 8.

1. Download the latest `.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for Doist.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img>

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add Buy the milk \from 3pm \to 5pm`**:  
     adds an event with a description of "Buy the milk" from upcoming 3pm to 5pm
   * **`delete 3`** : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>

## 2. Features

> **Command Format**
> * All commands begin with a command word, following by parameters.
> * All parameters are key-value pairs except the first first parameter, whose key must be omitted.
> * All keys start with the backslash `\`.
> e.g. `\from`, `\every`.
> * If users want to use backslash in the value, use `\\`.
> * Both command word and keys are strings without space. Underscore is used separete different words.
> e.g. `list_tag`, `\remind_at`
> * Words in `UPPER_CASE` are the values.
> * (A|B) means A and B can be used interchangeably.
> * Items in `[]` are optional.
> * Items with `...` after them can have multiple instances, seperated by space.
> * Key-value pairs can be in any order.
> * Tags cannot have space.

### Viewing help : `help`

Format: `help`

> Help is also shown if the command format is invalid. e.g. `abcd`

### Adding a new task : `add`, `do`

Adds a new task into the to-do list<br>
Format 1: `(add|do) TASK_DESCRIPTION [\from START_TIME] [\to END_TIME] [\remind_at REMIND_TIME] [\every RECURRENCE_INTERVAL] [\as PRIORITY] [\under TAG...]`  
Format 2: `(add|do) TASK_DESCRIPTION [\by TIME] [\remind_at REMIND_TIME] [\every RECURRENCE_INTERVAL] [\as PRIORITY] [\under TAG...]`

> - Tasks can have any number of tags (including 0)
> - Tasks can have neither start time and end time. They are floating tasks
> - Tasks can have start time and end time being the same. They are deadlines
> - Tasks can have start time and end time being different. They are events
> - If the user does not specified the priority, it will be set to a default value
> - The start time and remind time must be earlier or equal to the end time
> - If the user specify the recurrence interval, every time the end time passes, start time, end time and remind time will be updated to the next occurrence.
> - For format 2, start time and end time will both be set to TIME.
> That is, `add ... by 8pm` is equivalent to `add ... from 8pm to 8pm`

Examples:

* `do group meeting \from 4pm today \to 6pm today \remind_at 3pm \as important \under school_work CS2103T`
* `add submit pre-tutorial activities \by 6pm this Wednesday \remind_at 5pm this Wednesday \every week`

### Listing tasks : `list`, `ls`

Displays a list of tasks.<br>
Format 1: `(list|ls) [TYPE] [\from TIME] [\to TIME] [\under TAG]`  
Format 2: `(list|ls) [TYPE] [\in STRING_REPRESENTING_TIME_INTERVAL] [\under TAG]`

> - `TYPE` can be `pending`, `overdue`, `finished`
> - If `TYPE` is not specified, all the tasks will be returned
> - `STRING_REPRESENTING_TIME_INTERVAL` can be `today`, `this week`, `this month` and so on.

Examples:

* list pending \under school_work
* list pending \in today
* list \in this week
* list finished \from 1st March \to 20th March \under internship

### Finding all tasks containing any search key in their description : `find`

Finds tasks whose description contain any of the given search keys.<br>
Format: `find 'KEY' ['KEY'...]`

> * Search key is a string with space allowed
> * The search is case sensitive. e.g `hans` will not match `Hans`
> * The order of the search keys does not matter. e.g. `'Hans' 'Bo'` will match `'Bo Hans'`
> * Only the task description is searched.
> * Persons matching at least one search key will be returned (i.e. `OR` search).
    e.g. `Hans` will match `Hans Bo`

Examples:

* `find 'hiking'`
* `find 'CS2103T' 'group meeting' 'project'`

### Deleting tasks : `delete`

Deletes the specified tasks from to-do list. Irreversible.<br>
Format: `delete INDEX [INDEX...]`

> Deletes the tasks at the specified `INDEX`.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index must be a **positive integer** 1, 2, 3, ...

Examples:

* `list finished`<br>
  `delete 1 2`<br>
  Deletes the 1st and the 2nd tasks in the result of `list` command.
* `find 'party'`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### Marking tasks as finished : `finish`

Mark the specified tasks as finished.<br>
Format: `finish INDEX [INDEX...]`

> Mark the tasks at the specified `INDEX` as finished.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index must be a **positive integer** 1, 2, 3, ...<br>
> If the tasks at the specified `INDEX` are already finished, no change made.

Examples:

* `list pending \in this week`<br>
  `finish 1 2`<br>
  Marks the 1st and the 2nd tasks in the result of `list` command as finished.
* `find 'project'`<br>
  `finish 1`<br>
  Mark the 1st task in the results of the `find` command as finished.

### Editing an existing task : `edit`

Edit the specified task.<br>
Format 1: `edit INDEX [\desc TASK_DESCRIPTION] [\from START_TIME] [\to END_TIME] [\remind_at REMIND_TIME] [\every RECURRENCE_INTERVAL] [\as PRIORITY] [\under TAG...]`  
Format 2: `edit INDEX [\desc TASK_DESCRIPTION] [\by TIME] [\remind_at REMIND_TIME] [\every RECURRENCE_INTERVAL] [\as PRIORITY] [\under TAG...]`

> The rule for `edit` command is the same as `add` command

Examples:
* `list`<br>
  `edit 1 \desc watch NBA \remind_at this Sunday 8am`<br>
  Suppose originally, the description of this task is `watch nba`,
  it will be change to `watch NBA`.
  The reminder time will also be updated to the specified one: `this Sunday 8am`.

### Listing all the tags : `list_tag`, `ls_tag`

Displays a list of all tags.<br>
Format: `(list_tag|ls_tag)`  

### Undoing the previous commands : `undo`

Undo the command previous to the current command.<br>
Format: `undo`  

### Clearing all entries : `clear`

Clears all entries from the to-do list.<br>
Format: `clear`  

### Set an alias for a command word : `alias`

Set an alias for an existing command word so that the alias can also be used to trigger the command.<br>
Format: `alias ALIAS \for COMMAND_WORD`

> `ALIAS` should be a string without spaces, underscores are suggested to replace spaces.

Examples:
* `alias list_tags \for list_tag`

### Exiting the program : `exit`

Exits the program.<br>
Format: `exit`  

### Changing the storage destination : `save_at`

Change the storage path to the specified one.<br>
Format: `save_at PATH`

Examples:
* `save_at C:\Users\admin\Desktop\todolist.xml`

### Saving the data

Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Doist folder.

## 4. Command Summary

* **Add** :  
  `(add|do) TASK_DESCRIPTION [\from START_TIME] [\to END_TIME] [\remind_at REMIND_TIME] [\every RECURRENCE_INTERVAL] [\as PRIORITY] [\under TAG...]` <br>
  `(add|do) TASK_DESCRIPTION [\by TIME] [\remind_at REMIND_TIME] [\every RECURRENCE_INTERVAL] [\as PRIORITY] [\under TAG...]` <br>
  e.g. `do group meeting \from 4pm today \to 6pm today \remind_at 3pm \as important \under school_work CS2103T`

* **Alias** :  
  `alias ALIAS \for COMMAND_WORD` <br>
  e.g. `alias set_alias \for alias`

* **Clear** :  
  `clear` <br>

* **Change storage path** :  
  `save_at PATH`  
  e.g. `save_at C:\Users\admin\Desktop\todolist.xml`

* **Delete** :  
  `delete INDEX [INDEX...]` <br>
   e.g. `delete 3 4 5`

* **Edit** :  
  `edit INDEX [\desc TASK_DESCRIPTION] [\from START_TIME] [\to END_TIME] [\remind_at REMIND_TIME] [\every RECURRENCE_INTERVAL] [\as PRIORITY] [\under TAG...]` <br>
  `edit INDEX [\desc TASK_DESCRIPTION] [\by TIME] [\remind_at REMIND_TIME] [\every RECURRENCE_INTERVAL] [\as PRIORITY] [\under TAG...]` <br>
  e.g. `edit 1 \desc watch NBA \remind_at this Sunday 8am`

* **Exit** :  
  `exit`

* **Find** :  
  `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find 'test' 'midterm'`

* **List** :  
  `(list|ls) [TYPE] [\from TIME] [\to TIME] [\under TAG]` <br>
  `(list|ls) [TYPE] [\in STRING_REPRESENTING_TIME_INTERVAL] [\under TAG]` <br>
  e.g. `ls pending \in this month \under internship`

* **List tags** :  
  `(list_tag|ls_tag)`

* **Help** :  
  `help`

* **Undo** :  
  `undo`

* **Mark as finished** :  
  `finish INDEX [INDEX...]`  
  e.g. `finish 1 8`
