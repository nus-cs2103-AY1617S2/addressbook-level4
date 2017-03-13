# Task Manager - User Guide

By : `T09B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`

---

## 1. Quick Start

- Please ensure the host has JRE 1.8 or higher installed.
(Download: http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- Unzip the zip file and run the .exe file.

## 2. Features
### Adding tasks
**Adding a new task**
Format: `add <task_name> [due <deadline>] [tag <tag1, tag2, ...>]`

> task_name : Name of your task<br>
> deadline (optional) :<br>
>   (1) Date only, `02/03/17` or `020317` (ddmmyy)<br>
>   (2) Time only which means today, `3pm` or `12noon` or `2.30am`<br>
>   (3) Time and Date, write time before date<br>
> tag1, tag2, … : tags of this task

Examples:

* `add Study for CS2106`
* `add Study for CS2106 due 3pm`
* `add Study for CS2106 due today`
* `add Study for CS2106 due 3pm today`
* `add Study for CS2106 due 02/03/17`

> !! Do not use the keywords like `due` in your `<task_name>` or the Task Manager might misunderstand you

**Adding a new event**
Format: `add <event_name> from <starting_time> to <end_time> [on <date>] [tag <tag1, tag2, ...>]`

> event_name : name of the event<br>
> starting_time : time of start of the event<br>
> end_time : time of end of the event<br>
> date: date of the event<br>
> tag1, tag2, … : tags of this event

Examples:

* `add dinner from 5pm to 5.30pm`
* `add meeting from 10am to 11am on Wednesday`

### Viewing tasks
**Listing all ongoing tasks**
Format: `list`

**Finding tasks by keyword**
Format: `find <keyword1 keyword2 ...>`
> keyword1, keyword2, … : All the parameters you want in the search.

Examples:

* `find completed schoolwork`

**Finding tasks by datetime**
Format: `find due <datetime>`
> datetime : REFER TO ADD TASK

Examples:

* `find due 21042017`


### Managing tasks
**Updating task**
Format: `update <id> [<new_task_name>] [due <new_datetime>] [tag <tag1, tag2, ...>] [remove tag <tag1, tag2, ...>]`

> id : displayed task id<br>
> new_task_name (optional) : new name to replace the old task name<br>
> new_datetime (optional) : REFER TO ADD TASK

Examples:

* `update 7 clean house`
* `update 7 due 05/06/17`
* `update 7 tag schoolwork, CS2103`
* `update 7 clean house due 04/05/17 tag schoolwork, CS2103`

**Deleting task**
Format: `delete <id1, id2, id3>`

> id : displayed task id

Examples:

* `delete 3`
* `delete 3,4,5,6`

**Marking task as complete**
Format: `done <id1, id2, ...>`

> id : displayed task id

Examples:

* `done 3`
* `done 3,4,5,6`

**Marking task as not complete**
Format: `notdone <id1, id2, ...>`

> id : displayed task id

Examples:

* `notdone 3`
* `notdone 3,4,5,6`

### Managing Tags

**Renaming tag**
Rename an existing tag<br>
Format: `rename tag from <tag_name> to <new_tag_name>`

> tag_name : existing tag name<br>
> new_tag_name : new tag name

### Undo/Redo Commands

**Undo Command**
Undo up to 5 commands that have been made by the user<br>
Format: `undo`

**Redo Command**
Redo a command that a user has previously undone<br>
Format: `redo`

### Help
Format: `help [<command>]`


### Exit
Format: `exit`


### Advanced Usage
**Export file**
Export storage file to specified directory
Format: `export to <dir_location>`

> dir_location: path to store exported file

Examples:

* `export to C:\Desktop`
* `export to ..\mySecretFolder`

**Import file**
Add tasks from file to exported task list
Format: `import from <path_to_file>`

> path_to_location: path to the exported task list

Examples:

* `import from C:\Desktop\exported.txt`
* `import from ..\mySecretFolder\exported.txt`

**Change storage location**
Set a new storage location
Format: `saveto <dir_location>`

> dir_location : path to new save location

Examples:

* `saveto C:\Desktop`
* `saveto ..\mySecretFolder`

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manager folder.

## 4. Command Usage

* **Add Task**: `add <task_name> [due <datetime>] [tag <tag1, tag2, ...>]`
* **Add Event**: `add <event_name> from <starting_time> to <end_time> [on <date>] [tag <tag1, tag2, ...>]`
* **View All Tasks**: `list`
* **Find Task**: `find <keyword1 keyword2 ...>`
* **Update Task**: `update <id> [<new_task_name>] [due <new_datetime>] [tag <tag1, tag2, ...>] [remove tag <tag1, tag2, ...>]`
* **Delete Task**: `delete <id1, id2, id3>`
* **Complete Task**: `done <id1, id2, ...>`
* **Rename Tag**: `rename tag from <tag_name> to <new_tag_name>`
* **Help**: `help [<command>]`
* **Exit**: `exit`
* **Export**: `export to <dir_location>`
* **Import**: `import from <path_to_file>`
* **Change Storage Location**: `saveto <dir_location>`
