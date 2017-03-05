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
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all contacts
   * **`add`**` John Doe p/98765432 e/johnd@gmail.com a/John street, block 123, #01-01` :
     adds a contact named `John Doe` to the Address Book.
   * **`delete`**` 3` : deletes the 3rd contact shown in the current list
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

### 2.2. Adding a new Task: `add`

There are 4 forms of task that ProcrastiNomore supports:

#### 2.2.1. Events

Format: `add TASK on DATE/DAY`
		`add TASK from STARTTIME to ENDTIME`
		`add TASK on DATE/DAY from STARTTIME to ENDTIME`
		
#### 2.2.2. Deadlines

Format: `add TASK by DATE/TIME/DATE+TIME

#### 2.2.3. Untimed

Format:	`add TASK`

#### 2.2.4. Unconfirmed events

Format: `add TASK on DATE1 or DATE2 or DATE3`

> TIME input can be in 24hrs format or 12hrs format with am/pm

Examples:

* `add eat breakfast on 3/3/17`
* `add eat lunch on THURSDAY`
* `add eat dinner from 530pm to 730pm`
* `add eat breakfast from 1730 to 1930`
* `add eat lunch on 3/3/17 from 530pm to 730pm`
* `add eat dinner by 3/3/17`
* `add eat breakfast by 730pm`
* `add eat lunch by 3/3/17 by 330pm`
* `add eat dinner on 3/3/17 or 4/3/17 or 5/3/17`
* `add eat breakfast`

### 2.3. Update an existing task : `update`

There are 3 types of edit that ProcrastiNomore supports:

#### 2.3.1. Update task name

Format: `update TASK to NEWTASK`

#### 2.3.2. Update task time

Format: `update TASK to STARTTIME to ENDTIME`
		`update TASK to TIME`
		
#### 2.3.3. Update the entire task index

Format: `update TASKINDEX NEWTASKNAME/DATE/TIME

> For 2.3.1. and 2.3.2., ProcrastiNomore will show a list of task with the 
> same task name and user will be required to put the TASKINDEX of the 
> TASK user wants to change

Examples:
*`update eat breakfast to eat lunch`
 `1`
*`update eat dinner to 730pm`
 `1`

### 2.4. Delete an existing task: `delete`

Format: `delete TASKNAME`
		`delete TASKINDEX`
		
> In the event of delete TASKNAME, ProcrastiNomore will show a list of tasks
> with the same TASKNAME and user will be required to input the TASKINDEX of 
> the TASK user wants to delete

Examples:
*`delete breakfast`
 `1`
*`list`
 `delete 1`

### 2.5. Wipe out history of all or specified tasks: clear

There are 3 types of clear commands.

#### 2.5.1. Delete all tasks

Format: `clear all`

#### 2.5.2. Delete all tasks on date specified

Format: `clear DATE`

#### 2.5.3. Delete all previously marked uncomplete/complete tasks

Format: `clear completed`
		`clear uncompleted`

Examples:
*`Clear 03/03/17`

### 2.6. Deleting a person : `delete`

Deletes the specified person from the address book. Irreversible.<br>
Format: `delete INDEX`

> Deletes the person at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd person in the address book.
* `find Betsy`<br>
  `delete 1`<br>
  Deletes the 1st person in the results of the `find` command.

### 2.7. Select a person : `select`

Selects the person identified by the index number used in the last person listing.<br>
Format: `select INDEX`

> Selects the person and loads the Google search page the person at the specified `INDEX`.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `select 2`<br>
  Selects the 2nd person in the address book.
* `find Betsy` <br>
  `select 1`<br>
  Selects the 1st person in the results of the `find` command.

### 2.8. Clearing all entries : `clear`

Clears all entries from the address book.<br>
Format: `clear`

### 2.9. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.10. Saving the data

Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Address Book folder.

## 4. Command Summary

* **Add**  `add NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]...` <br>
  e.g. `add James Ho p/22224444 e/jamesho@gmail.com a/123, Clementi Rd, 1234665 t/friend t/colleague`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find James Jake`

* **List** : `list` <br>
  e.g.

* **Help** : `help` <br>
  e.g.

* **Select** : `select INDEX` <br>
  e.g.`select 2`