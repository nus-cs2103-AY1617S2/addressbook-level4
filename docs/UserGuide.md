# Doist - User Guide

By : `W13-B4`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jan 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Introduction](#1-introduction)  
2. [Quick Start](#2-quick-start)  
3. [Features](#3-features)  
  3.1. [Viewing help](#31-viewing-help--help)  
  3.2. [Adding a new task](#32-adding-a-new-task--add-do)  
  3.3. [Listing tasks](#33-listing-tasks--list-ls)  
  3.4. [Finding a task](#34-finding-all-tasks-containing-any-search-key-in-their-description--find)  
  3.5. [Deleting tasks](#35-deleting-tasks--delete)  
  3.6. [Marking tasks as finished](#36-marking-tasks-as-finished--finish)  
  3.7. [Editing an existing task](#37-editing-an-existing-task--edit)  
  3.8. [Listing all the tags](#38-listing-all-the-tags--list-tag-ls-tag)  
  3.9. [Undoing the previous commands](#39-undoing-the-previous-commands--undo)  
  3.10. [Clearing all entries](#310-clearing-all-entries--clear)  
  3.11. [Setting an alias for a command word](#311-setting-an-alias-for-a-command-word--alias)  
  3.12. [Changing the storage destination](#313-changing-the-storage-destination--save-at)  
  3.13. [Saving the data](#314-saving-the-data)  
  3.14. [Exiting the program](#312-exiting-the-program--exit)  
4. [FAQ](#4-faq)
5. [Command Summary](#5-command-summary)

## 1. Introduction

Do you love the adrenaline rush you get when you check an item off your to-do list, but are you too busy to actually make one? Use Doist today, be a Doist and feel more organised than ever before!

Doist is the comprehensive tool to help you in all your planning. It allows you to key in, sort, tag, set reminders and even search for your tasks and events so that you can focus on getting things done. Your hands never need to leave the keyboard with our command-line input interface, and our easy-to-use UI will keep you coming back for more. Once you are a Doist, there's no looking back.

Doist. Rediscover your love for doing things, one line at a time!

## 2. Quick Start

Are you ready to be a Doist?

0. Ensure you have Java version `1.8.0_60` or later installed in your computer.

>    Having any Java 8 version is not enough.
>    This app will not work with earlier versions of Java 8.

1. Download the latest `.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for Doist. Note that you change Doist's storage location of its data in the future.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
<br> <br>
 <img src="images/Ui.png" width="450"> <br>
 Figure 1: Doist Application Window <br>

4. Voila, you are ready to be more productive! Type a command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.

### Getting started with commands :

Type the following commands and press <kbd>Enter</kbd> after each command.
1. **`add Attend group meeting \from 3pm \to 5pm`**. <br>
Adds an event for today with a description of "Attend group meeting" from 3pm to 5pm today
2. **`add Go grocery shopping \by 6pm 25th Oct`**. <br>
Adds a task with a description of "Go grocery shopping" which has a 6pm deadline on the 25th of October.
3. **`finish 1`** <br>
Marks the first task in the list as finished.
4. **`list pending`** <br>
Lists all the pending tasks.
5. **`delete 1`** <br>
Deletes the 1st task shown in the current list.
6. **`exit`** <br>
Exits the application.

Go ahead and try out different date and time formats for the add command!

Refer to the [Features](#features) section below for details of each command.<br>

## 3. Features

**Command Format**

Take note of some general information for our commands :
> * All commands begin with a command word. <br>
> *e.g.* `add`, `edit` <br>
> * All keys start with the backslash `\`. <br>
> *e.g.* `\from`, `\to` are the keys in `add Buy the milk \from 3pm \to 5pm` <br>
> * Command words and keys that consist of multiple words will not contain spaces. Underscores are used instead. <br>
> *e.g.* `list_tag`, `\remind_at` <br>
> * Words in `UPPER_CASE` are the values. You can enter whatever you want for them!<br>
> * If you want to use backslash in the value, use `\\`. <br>
> *e.g.* `add Remember to check file at C:\\users\\! \by 3pm` would appear as Remember to check file at C:\users\. <br>
> * `(A|B)` means A and B can be used interchangeably. <br>
> * Items in `[]` are optional. <br>
> * Items with `...` after them can have multiple instances, separated by space. <br>
> *e.g.* `[INDEX...]` means you can specify multiple indices for that command. <br>
> * The keys can be entered in any order after the command word. <br>
> *e.g.* `add Buy the milk \to 3pm \from 5pm` works too! <br>


### 3.1 Viewing help : `help`

Feeling a bit lost? Simply type "help" to view the handy help page! <br>
`help`

### 3.2 Adding a new task : `add`, `do`

You can use the `add` command to add tasks. Tracking your tasks is what Doist does best! <br>
`do` is an alternative command word.

**Description** <br>
Add a new task with a description by using just the `add` command word. <br>
<br>
Format: `(add|do) TASK_DESCRIPTION`
> **Examples:** <br>
> - `add buy milk` <br>

> **Remarks:** <br>
> - To use \ in your description, type \\\

**Start time and End time** <br>
Add a task with a start time and end time for events that occur over a period of time with `\from` and `\to` keys. <br>
<br>
Format: `(add|do) TASK_DESCRIPTION [\from START_TIME] [\to END_TIME]`
> **Examples:** <br>
> - `add buy milk \from 3pm \to 4pm`<br>
> - `add buy milk \from 12 Oct 3pm \to 4pm` <br>

> **Remarks:** <br>
> - See Fig 1. for acceptable date and time formats.

**Deadline** <br>
Add a task that has a deadline with the `\by` key.
<br>
Format: `(add|do) TASK_DESCRIPTION [\by TIME]`

> **Examples:** <br>
> - `add buy milk \by 3pm` <br>
> - `add buy milk \by 28th Feb` <br>

**Fig 1. Acceptable date and time formats** <br>

Date Format |Examples
----------- | :------- |
Day Month | 1st Oct
Day Month Year | 1st Oct 2017
Month Day | Oct 1st
Day / Month | 1/10
Day / Month / Year | 1/10/17
Relative | today, tomorrow, tmr, two days later

Time Format | Examples
----------- | :------- |
Hour:Min | 03:00
Hour.Min | 03.00, 3.00
Hour am/pm | 3pm, 3am
Hour | 3

> **Remarks:** <br>
> - If you do not explicitly state the date, the date will be assumed to be within the next 24 hours <br>
> - If you do not explicitly state the time, the time will be assumed to be the current time of the day
> - If you do not explicitly state the year, the year will be assumed to be the current year
> - Note that the start time and remind time must be earlier or equal to the end time

**Priority** <br>
Add a task with higher priorities with the `\as` key. By default, tasks are of normal priority. Since we all have certain tasks that are more important than others, you can indicate that they are important or very important! <br>
<br>
Format: `[\as PRIORITY]`
> **Examples:** <br>
> - `add buy milk \as important`<br>
> - `add buy milk \as very important` <br>

> **Remarks:** <br>
> - `Priority` can be `normal`, `important`, `very important`

**Reminder** <br>
Add a task with a reminder timing with the `\remind_at` key. State the number of hours before the task's end time that you want to be reminded. A pop-up will appear to remind you about the task at that specified time! <br>
<br>
Format: `[\remind_at REMINDER_RELATIVE_TIME]`
> **Examples:** <br>
> - `add buy milk \remind_at 3` <br>
> - `add buy milk \remind_at 5 hrs before` <br>

**Recurrence Interval** <br>
Add a recurring task with the `\every` key. If you specify a recurrence interval, every time a task ends or is finished, the start time, end time and reminder time of the task will be updated to its next occurrence. <br>
<br>
Format: `[\every RECURRENCE_INTERVAL]`
> **Examples:** <br>
> - `add buy milk \every 4 hours` <br>
> - `add buy milk \every week` <br>

**Tags** <br>
Add a task with tags with the `\under` key. Separate multiple tags with spaces.<br>
<br>
Format: `[\under TAG...]`
> **Examples:** <br>
> - `add buy milk \under shopping friends` <br>
> - `add buy milk \under for_myself` <br>

> **Remarks:** <br>
> - Tasks can have any number of tags (including 0)


**Sample Commands:**

* `do group meeting \from 4pm today \to 6pm today \remind_at 3pm \as important \under school_work CS2103T`
* `add submit pre-tutorial activities \by 6pm this Wednesday \remind_at 5pm this Wednesday \every week`

### 3.3 Listing tasks : `list`, `ls`

You can use the `list` command to list different types of tasks! Doist knows that you have many tasks and will need to filter them in some way to concentrate on doing those tasks. <br>
`ls` is an alternative command word.

**List pending, overdue, finished, all tasks** <br>
List `pending`, `overdue`, `finished` or `all` tasks by simply using the `list` command. <br>
<br>
Format: `(list|ls) [TYPE]`
> **Examples:** <br>
> - `list` <br>
> - `list all` <br>
> - `list finished` <br>

> **Remarks:** <br>
> - `list` will by default show both pending and overdue tasks.

**List tasks during a time period** <br>
List tasks occurring during a time period by using the `\from` and `\to` keys. <br>
<br>
Format: `[\from TIME] [\to TIME] `
You can also use the `\in` key for tasks occurring `today`, `this week`, `this month` and so on.
`[\in STRING_REPRESENTING_TIME_INTERVAL]`
> **Examples:** <br>
> - `list \from 3pm \to 5pm` <br>
> - `list \in this week` <br>

> **Remarks:** <br>
> - See Fig 1. for acceptable date and time formats

**List tasks under tags** <br>
List tasks that are under certain tags by using the `\under` key. <br>
<br>
Format: `[\under TAG]`
> **Examples:** <br>
> `list \under shopping` <br>

**Sample Commmands**
* `list pending \under school_work`
* `list pending \in today`
* `list \in this week`
* `list finished \from 1st March \to 20th March \under internship`

### 3.4 Finding a task : `find`

You can use the `find` command to find tasks whose description contains any of the given search queries. You just need to remember any of the words in the description of a task to find it in your long list of tasks! <br>
<br>
Format: `find 'QUERY' ['QUERY'...]`
> **Examples:** <br>
> - `list \from 3pm \to 5pm` <br>
> - `list \in this week` <br>

> **Remarks:** <br>
> - Spaces are allowed in search query <br>
> - The search is case sensitive. *e.g* `hans` will not match `Hans` <br>
> - The order of the search keys does not matter. *e.g.* `'Hans' 'Bo'` will match `'Bo Hans'` <br>
> - Only the task description is searched. <br>
> - Tasks matching at least one search query will be returned (in other words, it is an `OR` search).
    *e.g.* A task with a description of `Hans` will match search query `Hans Bo` <br>

**Sample Commmands**

* `find 'hiking'`
* `find 'CS2103T' 'group meeting' 'project'`

### 3.5 Deleting a task : `delete`

You can use the `delete` command to delete the task specifed by an index. The index refers to the index number of the task shown in the most recent listing. <br>
<br>
Format: `delete INDEX [INDEX...]`
> **Examples:** <br>
> - `delete 3` <br>

> **Remarks:** <br>
> - Deletes the tasks at the specified `INDEX`.<br>
> - You can delete more than one task by specifying multiple indices.<br>


**Sample Commands**
* `list finished`<br>
  `delete 1 2`<br>
  Deletes the 1st and the 2nd tasks in the result of `list` command.
* `find 'party'`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 3.6 Marking tasks as finished : `finish`, `fin`, `finished`, `unfinish`

You can use the `finish` command to mark the specified tasks as finished.
We feel your excitement to finish tasks, so we've even provided two alternative command words `fin` and `finished`!<br>
<br>
Format: `finish INDEX [INDEX...]`

Use the `unfinish` command to change the status of an already finished task to not finished.
`unfinish INDEX [INDEX...]`

> **Examples:** <br>
> - `finish 3` <br>
> - `unfinish 3` <br>

> **Remarks:** <br>
> - The index refers to the index number of the task shown in the most recent listing.<br>
> - If the task(s) at the specified `INDEX` is/are already finished, there will be no changes made.

**Sample Commands**
* `list pending \in this week`<br>
  `finish 1 2`<br>
  Marks the 1st and the 2nd tasks in the result of `list` command as finished.
* `find 'project'`<br>
  `finish 1`<br>
  Marks the 1st task in the results of the `find` command as finished.

### 3.7 Editing an existing task : `edit`

You can use the `edit` command to edit the specified task. Feel free to edit whatever you want in one line. Be assured that other properties of the task will not change! <br>
<br>
Format: `edit INDEX [TASK_DESCRIPTION] [\from START_TIME] [\to END_TIME] [\remind_at REMIND_TIME] [\every RECURRENCE_INTERVAL] [\as PRIORITY] [\under TAG...]`

Just like the add command, `[\by TIME]` can be used in place of `\from` and `to`
> **Examples:** <br>
> - `edit 3 edited description of task` <br>

> **Remarks:** <br>
> - Refer to the section about the `add` command to know how to use the keys of the `edit` command because they are used in the exact same way

**Sample Commands**
* `list`<br>
  `edit 1 \desc watch NBA \remind_at this Sunday 8am`<br>
  Suppose that the description of this task is originally `watch nba`,
  the new description will be changed to `watch NBA`.
  The reminder time will also be updated to the specified one which is `this Sunday 8am`.

### 3.8 Listing all the tags : `list_tag`, `ls_tag`
You can use `list_tag` to display a list of all existing tags. <br>
<br>
Format: `(list_tag|ls_tag)`  

### 3.9 Undoing the previous commands : `undo`, `redo`

You can use `undo` to undo previous commands. Undo as many times as you wish! <br>
<br>
Format: `undo`
If you regret undoing a command, you can use `redo` to restore your changes. However if you undo a command and then you enter a new command, you will not be able to redo that command anymore.

### 3.10 Clearing all tasks: `clear`

Done with all your tasks? Need an empty to-do list?
You can simply use `clear` to clear all tasks.
<br>
Format: `clear`  

### 3.11 Setting an alias for a command word : `alias`

Do you feel that our default command words are too lengthy and will like to set your own command words? <br>
You can use `alias` to set an alias for an existing command word. You will then be able to use the alias to trigger the command! <br>
<br>
Format: `alias ALIAS \for COMMAND_WORD`
> **Examples:** <br>
> - `alias a \for add` <br>

> **Remarks:** <br>
> - `ALIAS` should have no spaces, we suggest you use underscores to replace spaces. <br>
> - If you are unable to alias, you have either used that alias for another command word already or your alias is an existing command word.
You can then use `view_alias` to view the list of aliases!
<br>
Format: `view_alias`

### 3.12 Changing the storage destination : `save_at`

You can use `save_at` to change the storage path of Doist. <br>
<br>
Format: `save_at PATH`

> **Examples:** <br>
> - `save_at C:\Users\admin\Desktop\todolist.xml` <br>
> - `save_at Desktop\todolist.xml` <br>

> **Remarks:** <br>
> - You can provide a relative path for this command.

### 3.13 Saving the data

Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

### 3.14 Exiting the program : `exit`

Use `exit` to exit Doist. <br>
<br>
Format: `exit`  

## 4. FAQs

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Doist folder.

**Q**: Do I need to save my data before I ext Doist? <br>
**A**: You do not need to explicitly save your data, as Doist automatically does this for you!

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
