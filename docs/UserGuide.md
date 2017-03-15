# User Guide

&nbsp;

## Table of contents

1. [Intoduction](#intoduction)
2. [Quick Start](#quick-start)
3. [Features](#features)

   3.1.  [Viewing Help](#viewing-help)
   
   3.2.  [Adding a Floating Task]()
   
   3.3.  [Adding a Deadline Task]()
   
   3.4.  [Adding an Event Task]()
   
   3.5.  [Adding a Priority Task]()
   
   3.6.  [Finding a Task]() 
   
   3.7.  [Editing a Task]()
   
   3.8.  [Deleting a Task]()
   
   3.9.  [Completing a Task]()
   
   3.10. [Listing All Tasks]()
   
   3.11. [Listing Today’s Tasks]()
   
   3.12. [Listing Priority Tasks]()
   
   3.13. [Listing Completed Tasks]()
   
   3.14. [Undoing the Latest Command]()
   
   3.15. [Saving Data to Another Folder]()
   
   3.16. [Changing Default Storage Folder]()
   
   3.17. [Using Data from Another Folder]()
   
   3.18. [Clearing all Entries]()
   
   3.19. [Exiting the Program]()
   
   3.20. [Saving your Data]()
   
   3.21. [Differentiating your Tasks’ Urgency]()
   
4. [FAQ](#faq)
5. [Command Summary](#command-summary)

&nbsp;

## 1. Introduction

TypeTask is an easy-to-use task manager which lets you schedule and manage your tasks simply with only a single line of command! With the efficacy of a calendar without its shortcomings, TypeTask lets you organise your to-dos with ease so you can focus on your actual tasks. TypeTask is especially good for:

> Users who wants to do everything through a single line of command <br>
> Users who want an application that works offline <br>
> Users who have tasks that have deadlines/start-dates <br>

&nbsp;

## 2. Quick Start

1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

2. Download the latest `TypeTask.jar` from the [releases](../../../releases) tab.

3. Copy the file to the folder you want to use as the home folder for your Task Manager.

4. Double-click the file to start the app. The GUI should appear in a few seconds.

   > <img src="images/Ui.png" width="600">
   Fig 1. TypeTask’s User Interface

5. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
   
6. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` CS2103T Meeting d/12022107 t/11:00am` :
     adds a task named `CS2103T Meeting` to the Task Manager.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
   
7. Refer to the [Features](#features) section below for details of each command.<br>

&nbsp;

## 3. Features
Let us now explore the interesting features found in TypeTask!


### 3.1. Viewing Help : `help`
Having trouble navigating the application? Simply type  help to view a summary of TypeTask’s commands. The help screen will also show if you have entered an incorrect command e.g. abcd. 

Format: `help`


### 3.2. Adding a Floating Task: `add`
The first thing you would do is to add your first task! <br>

Format: `add <TASK NAME>`

Examples:

> add CS2103T Meeting

Examples:

* `add CS2103T Meeting`
* `add Buy milk`


### 3.3. Adding a Deadline Task: `add`
The first thing you would do is to add your first task! <br>

Format: `add <TASK NAME> d/<DATE> or add <TASK NAME> d/<DATE> t/<TIME>`

```
Things To Note:
> Date must be in dd/mm/yyyy format
> Time must be in hh:mm am/pm format
> Time is optional
```

Examples:

* `add CS2103T Meeting d/13/10/2016`
* `add CS2103T Meeting d/13/10/2016 t/11:10am`


### 3.4. Adding an Event Task: `add`
The first thing you would do is to add your first task! <br>

Format: `add <EVENT NAME> from d/<DATE> t/<TIME> to d/<DATE> t/<TIME>`

```
Things To Note:
> Date must be in dd/mm/yyyy format
> Time must be in hh:mm am/pm format
> Start Date and End Date is compulsory
> End Date must not be before Start Date
> Start Time and End Time is compulsory
```

Examples:

* `add OPEN HOUSE from d/13/10/2016 t/10:00am to d/16/10/2016 t/5:00 pm`
* `add CS1010 Lecture from d/10/10/2016 t/12:00pm to d/10/4/2017 t/2:00 pm`


### 3.5. Adding a Priority Task : `add`

Format: `add <TASK NAME> p/<PRIORITY>`

```
Things To Note:
> Priority must be high or low
> Priority can be applied to other type of task or event by adding this additional field “p/<PRIORITY>”
> Priority will automatic be low if a task is added without the field “p/<PRIORITY>”
```

Examples:
* `add CS2103T Meeting p/high`
* `add Open House from d/5/11/2016 t/11:00am to d/6/11/2016 t/11:00pm p/low`


### 3.6 Finding a Task: find : `find`
You may not want to go through the struggle of searching throughout your whole list to find a certain task. You can easily find a task by using the find command, then key in any detail about your task. 

Format: `find <KEYWORD>`

```
Things To Note:
> The order of the keywords does not matter. e.g. Meeting Tutor will match Tutor Meeting.
> The input that is a partial word of a task name will be matched e.g. Meeting will match Meetings.
> The tasks that match at least one keyword will be returned (i.e. OR search). e.g. Meeting will match Meeting Tutor.
> find is not case-sensitive, i.e. Meeting Tutor will match meeting tutor
```

Examples:
* `find Meeting`
* `find Open house`


### 3.7 Editing a Task: `edit`
Sometimes you may want to change the details of a certain task you have. No need to worry! You can modify a certain task by typing the edit command. 

Format: `edit INDEX <TASK NAME> d/<DATE> t/<TIME> p/<PRIORITY>`

```
Things To Note:
> Edits the task at the specified INDEX. The index refers to the index number shown in the last task listing.
> The index must be a positive integer 1, 2, 3, …
> Optional fields are <TASK NAME>, <DATE>, <TIME>, <PRIORITY>
> At least one of the optional fields must be provided.
> Existing field(s) will be updated with the input fields.
```

Examples:
* `edit 1 d/02122017 t/11:00am`
* `edit 2 CS2013T Meeting`


### 3.8 Deleting a Task : `delete`
There are some tasks that will never be completed and are irrelevant to keep. You can delete these tasks from your list by using the delete command. 

Format: `delete <INDEX>`

```
Things To Note:
> The task at the specified INDEX will be deleted 
> The index refers to the index number shown in the most recent listing.
> The index must be a positive integer 1, 2, 3, ...
```

Examples:
* `delete 1`


### 3.9 Completing a Task : `done`
Completed a task? Good for you! You can mark the task as done by typing in the done command. This will move the task to your completed list.

Format: `done <INDEX>`

```
Things To Note:
> The task at the specified INDEX will be moved from the task list to the completed list.
> The index refers to the index number shown in the most recent listing.
> The index must be a positive integer 1, 2, 3, ...
```

Examples:
* `done 2`


### 3.10. Listing all Tasks : `list`
Want to view a list of all your tasks? Use the command list to view all your tasks in TypeTask. <br>

Format: `list`

```
Things To Note:
> This list will refresh itself and show you the latest list when you add a new task or event.
> This list will be sorted by Priority, Date and Time.
```

### 3.11. Listing Today Tasks : `listday`
By default, you will have a view of all of today’s tasks when the application first starts. However, other commands may have changed the list you’re seeing. To return to the list of today’s tasks, use the  listday command. 

Format: `listday`

```
Things To Note:
> This list will refresh itself and show you the latest list when you add a new floating task or task that is due today.
> This list will be sorted by Priority and Time.
```


### 3.12. Listing Proirity Tasks : `list*`
Want to focus on your urgent tasks only? You can use the command list* to see a list of all your important tasks. 

Format: `list*`

```
Things To Note:
> This list will refresh itself and show you the latest list when you add a new task or event with priority.
> This list will be sorted by Date and Time.
```


### 3.13. Listing Completed Tasks : `listdone`
To review what you have done (or maybe feel better about yourself), you may want to look at all your completed tasks. You can do so by using the command listdone to show all of your completed tasks in TypeTask.<br>

Format: `listdone`

```
Things To Note:
> This list will only show you the tasks that are completed. Deleted Task are not included.
```


### 3.14. Undoing the Latest Command : `undo`
Did you accidentally type in the wrong command and did an operation you did not want? Well not to worry! You can type in the undo command, as you do not have to go through the hassle of modifying your recent operation. <br>

Format: `undo`

```
Things To Note:
> Undo command will not work if there is no prior command executed.
```


### 3.15. Saving the Data to Another Folder : `save`
You may consider to save the TypeTask’s data files into another folder of your choice. To do that, you can use the save command . <br>

Format: `save <FILE_PATH>`

```
Things To Note:
> The file path provided must be valid
```

Examples:

* `save C:/Desktop/myTask`


### 3.16. Changing the Default Storage Folder : `setting`
Want to set your default storage folder to another folder? You can do that by typing in the setting command to set the TypeTask folder into a folder of your choice . <br>

Format: `setting <FILE_PATH>`

```
Things To Note:
> The file path provided must be valid
```

Examples:

* `setting C:/Desktop/myOtherTask`<br>


### 3.17. Using Data from Another Folder : `use`
After changing TypeTask’s data files, you want to use them from your reallocated folder. You can use the use command to load the data from the specified folder.  <br>

Format: `use <FILE_PATH>`

```
Things To Note:
> The file path provided must be valid
```

Examples:

* `use C:/Desktop/myTask`<br>


### 3.18. Clearing all Entries : `clear`
Want to start fresh? TypeTask offers a clear command to delete all entries from the Task Manager. <br> 
`WARNING` you will lose all your data after this command. Thus, use it wisely. <br>

Format: `clear`


### 3.19. Exiting the Program : `exit`
Have you completed to schedule your tasks? Good job! To exit the program you can type the command exit. <br>

Format: `exit`


### 3.20. Saving the data
Your data is saved to the default storage folder in the hard disk automatically after any command that changes the data. There is no need to save manually!


### 3.21. Differentiating your tasks’ urgency
TypeTask automatically assigns your tasks certain colours to help you differentiate them easily.

`Red`: this uncompleted task’s deadline has passed! Better get on to it. <br>
`Yellow`: this uncompleted task was labelled as a priority. <br>
`Green`: this uncompleted task has a deadline but is not due yet. Phew! <br>

```
> Note that all tasks that fall outside of these categories will appear normally (i.e. no additional colours).
 ```
 
&nbsp;
 
## 4. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manager folder.
 
&nbsp;

## 5. Command Summary
Command | Shortcuts | Format | Example
-------- | :-------- | :--------- | :-----------
add | a, + | add TASK d/DATE t/TIME | add Shop Shoes d/20082017 t/4:30pm
find | find, search, f | find KEYWORD | find Study Math
delete | d, remove, rm, - | delete INDEX | delete 10
done | complete, finish | done INDEX | done 2
save | s | save FILE_PATH| use C:/Desktop/myTask
setting | set | setting FILE_PATH| save C:/Desktop/myTask
use | udf | save FILE_PATH| save C:/Desktop/myOtherTask
help | help, guide | | |
list | list, l | | |
listToday | listday, lt| | |
listProirity | list*, lp| | |
listDone | listdone, ld| | |
undo | u | | |
clear | cr | | |
exit | e | | |
