# DoMe! User Guide

By : `Team CS2103-W15-B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jan 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Introduction](#1-introduction)
2. [Quick Start](#2-quick-start)
3. [Features](#3-features)
4. [FAQ](#4-faq)
5. [Command Summary](#5-command-summary)
6. [Appendix](#6-appendix)

## 1. Introduction

Ever felt overwhelmed by the multitude of tasks you have to complete and have no idea where to start? Are you looking for an easy to work with application to help you track all your activities? Well, look no further! Your very own task manager - *DoMe!* is here to assist you!
*DoMe!* is your personal assistant that tracks all your activities and displays them in am easy-to-read display. It saves you the hassle of remembering what needs to be done and helps you prioritise your tasks.
Unlike other software, *DoMe!* is simple and intuitive. All you need is your keyboard to type in a single line of command, removing the inconvenience of clicking and navigating through blundersome menus. Let's get started in being productive and organised with *DoMe!*

## 2. Quick Start

1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
> Please note that the app is incompatible with earlier versions of Java 8.
2. Download the latest `taskmanager.jar` from the [releases](../../../releases) tab.
3. Copy the file to the folder you want to use as the home folder for your Address Book.
4. Double-click the file to start the app. The GUI should appear in a few seconds.
> <img src="images/Ui.png" width="600">

## 3. Features

> **Command Format**
> `Square brackets [ ]` denote a required field
> `Curved brackets ( )` denote an optional field'
> `...` denotes that you can have multiple instances

## 3.1 Functional features
This is a list of commands you can give to the application

## 3.1.1 View help: `help`
If you are unsure of the command formats, you can simply type `help` and view all the available commands and how to use them.

_Format:_
`help`

## 3.1.2 Add task: `add`
It's time to start adding tasks to your todo list! You can add events (tasks with start and end date/time), deadlined tasks (tasks with a due date) and also just tasks with names. You can also tag these tasks!

_Format:_
`add [Name-of-Task] (Deadline) (t/Tag-1) (t/Tag-2)`
`add [Name-of-Task] (s/Start-Time) (e/End-Time) (t/Tag-1) (t/Tag-2)`
`add [Name-of-Task]`

_Example:_
`add send TPS report to Bill e/31-02-2017 6PM t/TPS t/report`

## 3.1.3 Delete task: `delete`
If you no longer need to do a task, you can simply delete it from your todo list

_Format:_
`delete [Task-Number]`

## 3.1.4 Edit task: `edit`
You can update the details of your task by editing it

_Format:_
`edit [Task-Number]`

## 3.1.5 Complete task: `complete`
You can mark a task as completed to update its progress

_Format:_
`complete [Task-Number]`

_Example:_
`complete 2`

## 3.1.6 Search: `search`

You can find a task to by simply searching for tasks with matching keywords in their names and/or tags

> The search is case insensitive.

_Format:_
`search (name) (t/tag)`

_Example:_
`search report t/report`
Returns a list of tasks (if any) with the phrase report in its name or tag

## 3.1.7 List: `list`
You can view a specific type of the tasks you want to view in your todo list

_Format:_
`list`
> Lists all tasks

`list completed`
> Lists tasks marked as completed

`list incomplete`
> Lists tasks marked as incomplete

`list overdue`
> Lists incomplete tasks with deadlines that have already passed

## 3.1.8 View tasks by deadline: `due`

Keep track of which are the tasks with upcoming deadlines to stay one step ahead and prioritise your work

> You can type in the actual date after the command word due or you relative dates like today, tomorrow  and this week.

_Format:_
`due 23/1`
`due today`
`due this week`

_Example:_
`due tomorrow`
Returns a list of tasks due by tomorrow

## 3.1.9 Undo previous command: `undo`

You can easily undo your last command given

> This will undo the most previous command that mutated the data such as add, edit & delete.

_Format:_
`undo`

_Example:_
`undo`
Returns the undoing of the previous command that mutated the data, e.g. Undone: add send TPS report to Bill by Friday 6pm.

## 3.1.10 Add tags to created task: `tag`

You can organise your tasks by adding tags to your them
> Add tags to the task at the specified index. The index refers to the index number shown in the last person listing.

_Format:_
`tag [Task-Number] [t/Tag-1] (t/Tag-2) ...`

_Example:_
`tag 1 t/urgent t/for mom`
Returns the task name with the changed tags

## 3.1.11 Select a task: `select`
You can select a task to view more details about it

_Format:_
`select [Task-Number]`

_Example:_
`select 1`

## 3.1.12 Repeat tasks: `repeat`

You can also put a task on repeat, by setting a task at a fixed periodic time
> Add tags to the task at the specified index. The index refers to the index number shown in the last person listing.
> The periodic time specified must start with the word "every" and must be followed by one of the seven days of the week.

_Format:_
`repeat [Task-Number] [periodic time]`

_Example:_
`repeat 2 every friday`
Returns the task name that was put on repeat


## 3.1.13 Attach links: `addlink`
You can attach relevant link(s) to your task so you can retrieve the link easily and can immediately start on your task!

> The Task_Number refers to the index number shown in the most recent listing of the task.
> This command works for all types of tasks: completed, uncompleted, overdue, etc.

_Format:_
`addlink [Relevant_Link] [Task_Number]`

_Examples:_
`addlink https://www.google.com/drive/presentation-to-boss 5`
Attach the link `https://www.google.com/drive/presentation-to-boss` to task number 5 in the list of task.

`addlink https://mail.google.com/my-mail-box/important-email 10`
Attach the link `https://mail.google.com/my-mail-box/important-email` to task number 10 in the list of tasks.

## 3.1.14 Progress report: `report`
You can see all your completed tasks and overdue tasks in the past week, and incomplete tasks for the coming week

_Format:_
`report`

## 3.1.15 Calendar view: `calendarview`
You can view the current month and upcoming month's tasks in the visual form of a calendar

_Format:_
`calendarview`


## 3.1.16 Scroll through previous commands
You can use the arrow keys to scroll through your previous commands from the most recent to the earliest task.

_Format:_ 
`up arrow key`
> Previous command

`down arrow key`
> Next command

## 3.1.17 Clear the data : `clear`
You can clear your entire to-do list

_Format:_
`clear`



## 3.2 Settings: `settings`
> Enter the command `settings` to access these features

## 3.2.1 Change font size
You can adjust the font size to fit your needs accordingly
> You can specify how much you want to increase or decrease the font size by adding a "by" followed by an integer from 1-10.
> If no integer is specified, the app will change the font size by 1.

_Format:_
`increase font (by 1)`
`decrease font (by 1)`

_Examples:_
`increase font by 3`
 The app will increase the font size by 3.

`decrease font`
 The app will decrease the font size by 1.


## 3.2.2 Message of the Day
If you need extra help to get motiavted, you can set/change a message of the day. 
> Display a motivational message or some ascii art which will be shown when the todo list first starts up

_Format:_
`motd`

_Example:_
```
motd
I can do it! - Aaron Tan
```

## 3.2.3 Sync with Google Calendar: `sync`
You can sync the list of tasks with your own Google Calendar. You will be prompted to enter your password after entering the command.

_Format:_ 
`sync USER_EMAIL`

## 3.2.4 Customize file storing: `store`
You can change the storage location of the data to transfer your current todo list to your own storage system with ease
> Store all the data of the task manager in the file located at PATH_TO_STORAGE_FILE. It is required that this file be a .txt file located in StorageFile/a folder rooted at StorageFile, and that it is created before the command is called.

_Format:_
`store PATH_TO_STORAGE_FILE`

Examples:
`store StorageFile/StoreHereInstead/MyStorage.txt`
The task manager will store its data in MyStorage.txt located at StorageFile/StoreHereInstead/MyStorage.txt, provided that this file exists before calling the command.

`store StorageFile/AnotherStorage.txt`
The task manager will store its data in AnotherStorage.txt located under StorageFile instead of the default storage location, provided that this file exists before calling the command.

## 3.2.5 Reminder mode: `reminder`
You can set reminders so you can be reminded when it is
> a) 1 hour before the deadline of the most urgent task
> b) 30 minutes before the deadline of the most urgent task.
> You can also turn these reminders off.
> 
> The task(s) has to be **incomplete**, **not overdue** task(s).
> By **default**, reminder mode is switched **on** for all **uncompleted**, **not overdue** tasks.

_Format:_
`reminder on TASK_NAME [MORE_TASKS]` to turn **on** the reminder mode for the specified tasks.
`reminder off TASK_NAME [MORE_TASKS]` to turn **off** the reminder mode for the specified tasks.
`reminder on/off all` to turn **on**/**off** the reminder mode for all tasks.


_Examples:_
`reminder off [do laundry] [buy grocery]`
Tell the task manager not to remind the user for the tasks `do laundry` and `buy grocery`.

`reminder on [reply boss email]`
Tell the task manager to remind the user 1 hour and 30 minutes before the task `reply boss email` is due.

### Saving the data
Data is saved in the hard disk automatically after any command that changes the data.
There is no need to save manually.

## 4. FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Copy the app to the other computer and overwrite the empty data file it creates with the file that contains the data of your previous *DoMe!* folder.

## 5. Command Summary

* **Help** : `help` 

* **Add**  `add [Name-of-Task] s/(Start-Time) e/(End-Time) t/[Tag]...` 
  e.g. `add attend seminar s/21-03-2017 9.00 AM e/23-03-2017 9.00 PM t/work`
  
* **Edit** : `edit [Task-Number] [Edited-Details] (t/Edited-Tag)`

* **Delete** : `delete [Task-Number]`
   e.g. `delete 3`
   
* **Undo** : `undo`
   
* **Search** : `search [Keyword] (t/TagKeyword)...`
  e.g. `find email t/urgent`
  
* **List** : `list`,`list incomplete`,`list complete`,`list overdue`

* **Select** : `select [Task-Number]` 
  e.g.`select 2`

* **Tag** : `tag [Task-Number] [t/newTag]`
 e.g. `tag 1 t/urgent`

* **Repeat** : `repeat [Task-Number] [periodic-time]`
 e.g. `repeat 1 every friday`
 
* **CalendarView** : `calendarview`

* **Report** : `report`

* **Clear** : `clear`

* **Scroll** : `up/down arrowkey`

* **Change Font Size** : `increase font (by 1)`
`decrease font (by 1)`
 e.g. `increase font by 3`

* **MessageOfTheDay** : `motd`

* **Store** : `store [PATH_TO_STORAGE_FILE]`
 e.g. `store StorageFile/StoreHereInstead/MyStorage.txt`

* **Sync** : `sync USER_EMAIL`
 e.g. `sync git@mail.google.com`
 
* **Reminder** : `reminder on TASK_NAME [MORE_TASKS]` `reminder off TASK_NAME [MORE_TASKS]` `reminder on/off all`



---
  
## 6. Appendix
  
  | Word | Definition |
|-----|-----|
|[GUI](#GUI)|Graphic User Interface. The interface presented to users to interact with *DoMe!*.|
|[Storage Path](#storage-path)|This is the directory where your data will be saved.|
