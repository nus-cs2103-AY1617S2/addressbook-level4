# Doist - User Guide

By : `W13-B4`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jan 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Introduction](#1-introduction)
2. [Quick Start](#2-quick-start)
3. [Features](#3-features)
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
4. [FAQ](#4-faq)
5. [Command Summary](#5-command-summary)

## 1. Introduction
We all love that adrenaline rush when we place a tick on our checklist, mark our tasks as done and actually accomplish what we sought to do. But as our to-do tasks pile up, we tend to forget about them. Everyone barely has time to write our tasks down in a list.

Be a Doist today, one line at a time! Your hands don't even have to leave the keyboard with our command-line input interface.
Doist allows you to key in, sort, tag, search and even set reminders for your tasks and events so that you can focus on actually doing things.

Doist, rediscover your love for doing things.


## 2. Quick Start

Are you ready to be a do-ist?

0. Ensure you have Java version `1.8.0_60` or later installed in your computer.

>    Having any Java 8 version is not enough.
>    This app will not work with earlier versions of Java 8.

1. Download the latest `.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for Doist. Note that you would be able to edit Doist's storage location of its data later.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
<br> <br>
 <img src="images/Ui.png" width="450"> <br>
 Figure 1: Doist Application Window <br>

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.

### Getting started with commands :

Type the following commands and press <kbd>Enter</kbd> after each command.
1. **`add Buy the milk \from 3pm \to 5.00`**.
Adds an event for today with a description of "Buy the milk" from 3pm to 5am the next day.
2. **`add Buy the vegetables \by 6pm 25th Oct`**.
Adds a task with a description of "Buy the vegetables" which has a 6pm deadline.
3. **`finish 1`**
Marks the first task in the list as finished.
4. **`list pending`**
Lists all the pending tasks which are not finished yet.
5. **`delete 1`**
Deletes the 1st task shown in the current list.
6. **`exit`**
Exits the application.

Try out different date and time formats for the add command!

Refer to the [Features](#features) section below for details of each command.<br>

## 3. Features

**Command Format**

Take note of some general information for our commands :
> * All commands begin with a command word, followed by parameters.
> * All parameters are key-value pairs except the first parameter, whose key must be omitted.
> *e.g.* `add Buy the milk \from 3pm \to 5pm`
> * All keys start with the backslash `\`.
> *e.g.* `\from`, `\every`.
> * If you want to use backslash in the value, use `\\`.
> * Command words and keys consisting of multiple words will not contain spaces. Underscores are used instead.
> *e.g.* `list_tag`, `\remind_at`
> * Words in `UPPER_CASE` are the values.
> * `(A|B)` means A and B can be used interchangeably.
> * Items in `[]` are optional.
> * Items with `...` after them can have multiple instances, separated by space.
> * Key-value pairs can be entered in any order after the command word.

### Viewing help : `help`

Simply type "help" to view the help page! <br>
`help`

### Adding a new task : `add`, `do`

Add a new task with a description using just the `add` command word. <br>
`(add|do) TASK_DESCRIPTION`
> **Examples:** <br>
> `add buy milk` <br>
> **Tips:** <br>
> To use \ in your description, type \\\

Add a task with a start time and end time for events that occur over a period of time with `\from` and `\to` keys. <br>
`(add|do) TASK_DESCRIPTION [\from START_TIME] [\to END_TIME]`
> **Examples:** <br>
>`add buy milk \from 3pm \to 4pm`<br>
>`add buy milk \from 12 Oct 3pm \to 4pm` <br>
> **Tips:** <br>
> See the table of acceptable date and time formats below

Add a task that has a deadline with the `\by` key.
`(add|do) TASK_DESCRIPTION [\by TIME]`
> **Examples:** <br>
> `add buy milk \by 3pm` <br>
> `add buy milk \by 28th Feb` <br>
> **Tips:** <br>
> Start time and end time will both be set to `TIME`
> That is, `add ... by 8pm` is equivalent to `add ... from 8pm to 8pm`
> See the table of acceptable date and time formats below

Add a task with higher priorities with the `\as` key. By default, tasks are of normal priority. Since we all have certain tasks that are more important than others, you can indicate that they are important or very important! <br>
`[\as PRIORITY]`
> **Examples:** <br>
>`add buy milk \as important`<br>
>`add buy milk \as very important` <br>
> **Tips:** <br>
> `Priority` can be `normal`, `important`, `very important`

Add a task with a reminder timing with the `\remind` key. State the number of hours before the task's end time that you want to be reminded. A pop-up would appear to remind you about the task at that specified time! <br>
` [\remind REMINDER_RELATIVE_TIME]`
> **Examples:** <br>
> `add buy milk \remind 3` <br>
> `add buy milk \remind 5 hrs before` <br>

Add a recurring task with the `\every` key. If you specify a recurrence interval, every time a task ends or is finished, the start time, end time and reminder time of the task will be updated to its next occurrence. <br>
` [\every RECURRENCE_INTERVAL]`
> **Examples:** <br>
> `add buy milk \every 4 hours` <br>
> `add buy milk \every week` <br>
> `add buy milk \every 3 weeks` <br>
> `add buy milk \every year` <br>

Add a task with tags with the `\under` key. Separate multiple tags with spaces.<br>
` [\under TAG...]`
> **Examples:** <br>
> `add buy milk \under shopping friends` <br>
> `add buy milk \under for_myself` <br>
> **Tips:** <br>
> Tasks can have any number of tags (including 0)


**Acceptable Date Formats** <br>
| Date Format  | Example(s) |
| ------------ | ------------ |
| Day Month  | 1st Oct, 1 Oct  |
| Month Day  | Oct 1st, Oct  |

> **Tips:** <br>
> - If you do not explicitly state the time, the date will be assumed to be within the next 24 hours <br>
> - If you do not explicitly state the date, the time will be assumed to be the current time of the day
> - The start time and remind time must be earlier or equal to the end time

**More examples:**

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
> * Tasks matching at least one search key will be returned (i.e. `OR` search).
    e.g. `Hans` will match `Hans Bo`

Examples:

* `find 'hiking'`
* `find 'CS2103T' 'group meeting' 'project'`

### Deleting tasks : `delete`

Deletes the specified tasks from to-do list. It is irreversible.<br>
You can delete more than one task
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

> * Tags cannot have space.

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

## 4. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Doist folder.

## 5. Command Summary

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
