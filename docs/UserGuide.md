# Burdens: User Guide

By : `TEAM W09-B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp;

1. [Introduction](#1-introduction)
2. [Quick Start](#2-quick-start)
3. [Features](#3-features)
   > 3.1. [Help](#31-viewing-help--help)<br/>
     3.2. [Add tasks](#32-adding-a-task--add)<br/>
     3.3. [List tasks](#33-listing-all-tasks--list)<br/>
     3.4. [Edit tasks](#34-editing-a-task--edit)<br/>
     3.5. [Find tasks](#35-finding-all-tasks-containing-any-keyword-in-their-title--find)<br/>
     3.6. [Delete tasks](#36-deleting-a-task--delete)<br/>
     3.7. [Mark tasks as complete](#37-marking-a-task-as-complete--complete)<br/>
     3.8. [Mark tasks as incomplete](#38-marking-a-task-as-incomplete--incomplete)<br/>
     3.9. [Undo](#39-undo-the-previous-command--undo)<br/>
     3.10. [Redo](#310-redo-a-command--redo)<br/>
     3.11. [Select tasks](#311-selecting-a-task--select)<br/>
     3.12. [Reset](#312-clearing-all-tasks--reset)<br/>
     3.13. [Exit](#313-exiting-the-program--exit)<br/>
     3.14. [Import](#314-importing-files-into-the-program--import)<br/>
     3.15. [Export](#315-exporting-files-from-the-program--export)<br/>
     3.16. [Save](#316-saving-the-data)<br/>
4. [FAQ](#4-faq)
5. [Command Summary](#5-command-summary)

# 1. Introduction

Hello!<br/>
<br/>
Are you overwhelmed by your daily tasks and deadlines in life?<br/>
<br/>
Enter **Burdens**.<br/>
<br/>
A task manager that structures your tasks and deadlines<br/>
using your keyboard commands with a beautiful customisable interface.<br/>
<br/>
Read on and discover how to install and use **Burdens**<br/>
to make sense of your daily life activities.<br/>
<br/>
No more **burdens**.

# 2. Quick Start
**Download**
Ensure you have Java version `1.8.0_60` or later installed.

1. Download the latest Burdens.jar from the [releases](https://github.com/CS2103JAN2017-W09-B1/main/releases) tab.
2. Copy the jar file to the folder you want to use as the home folder for Burdens.
**Launch**
To start Burdens, double click on **Burdens.jar**. Hello!<br/>
Here is the greeting window at the very first startup.
   > <img src="images/Ui.png" width="600">
As shown, there will be some sample tasks to give you a sense of what your tasks will look like.<br/>

You will see four sections:

- Ongoing: Shows a list of ongoing tasks with deadlines. Recurring tasks will be in red.
- Floating: Shows a list of tasks without deadlines.
- Completed: Shows a list of completed tasks.
- Command: Displays the results of a command and the contains the command box.

Type a command in the command box and press <kbd>Enter</kbd> to execute it.

e.g. typing `help` and pressing <kbd>Enter</kbd> will open the Help window.

Here are some example commands you can try:

  - `list` : lists all tasks.
  - `add Buy groceries for: tomorrow priority: 5 note: eggs x10, milk x2, bread x2 #home #errand` : adds a task titled Buy groceries to the list.
  - `delete 3` : deletes the 3rd task shown in the current list.
  - `exit` : exits the app.

Refer to the [Features](https://github.com/CS2103JAN2017-W09-B1/main/blob/master/docs/UserGuide.md#features) section below for details of each command.

# 3. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 3.1. Viewing help : `help`

Prompts a help page with all the existing commands.

Format: `help`

> Help is also shown if you enter an incorrect command e.g. abcd

### 3.2. Adding a task : `add`

Adds a task to the list.

Format: `add TITLE [for: DEADLINE] [priority: PRIORITY] [note: INSTRUCTION] [t/TAG]...`

> The deadline parameter can be entered in natural language, e.g. &quot;today&quot;, &quot;tomorrow at noon&quot;, &quot;this Sunday&quot;, &quot;every Tuesday&quot;. If deadline is not specified, it is assumed that the task is floating, which means the task can be performed earlier or later in the schedule without any form of urgency.<br><br>
The priority parameter only accepts levels 1 - 5.<br><br>
Tasks can have any number of tags, including 0.

Examples:

- `add Buy groceries priority: 3 note: eggs x10, milk x2, bread x2 #home #errand`
- `add Watch webcast #CS2103 #school`

### 3.3. Listing all tasks : `list`

Shows a list of all tasks.

Format: `list`

### 3.4. Editing a task : `edit`

Edits an existing task in the list.

Format: `edit [TASK_LIST] INDEX [TITLE] [for: DATE] [priority: PRIORITY] [note: INSTRUCTION] [#TAG]...`

> Edits the task at the specified TASK\_LIST and INDEX. The task list refers to the name of the task list e.g. &quot;floating&quot;, &quot;ongoing&quot; and &quot;completed&quot;. If no task list name specified, it is assumed to be &quot;ongoing&quot;. The index refers to the index number shown in the last task listing.<br><br>
The index must be a positive integer 1, 2, 3, ...<br><br>
At least one of the optional fields apart from the task list name must be provided.<br><br>
Existing values will be updated to the input values.<br><br>
When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.<br><br>
You can remove all the task&#39;s tags by typing t/ without specifying any tags after it.

Examples:

- `edit 1 priority: 1 for: 07032017`
   - Edits the priority and date of the 1st task to be 1 and 07032017 respectively.
- `edit floating 2 Drink Coffee #`
   - Edits the title of the 2nd task in the floating task list to be Drink Coffee and clears all existing tags.

### 3.5. Finding all tasks containing any keyword in their title : `find`

Finds tasks with titles containing any of the given keywords. This command requires at least 3 letters e.g. `find Han` will be accepted, but `find Ha` is an invalid command format.

Format: `find KEYWORD [MORE_KEYWORDS]`

> The search is simultaneously applied to all task lists.<br><br>
The search is case sensitive. e.g hans will not match<br><br>
The order of the keywords does not matter. e.g. Hans Bo will match Bo Hans<br><br>
Only the title is searched.<br><br>
Tasks matching at least one keyword will be returned (i.e. OR search). e.g. Hans will match Hans Bo.<br><br>
Fuzzy Find is implemented such that e.g. Hns will match Hans.
> - Fuzzy Find works by returning a list of results based on likely relevance even though search argument words and spellings may not exactly match

Examples:

- `find groceries Buy`
   - Returns Buy groceries but not Groceries
- `find buy webcast`
   - Returns any tasks having titles buy or webcast

### 3.6. Deleting a task : `delete`

Deletes the task identified by the index number used in the last task listing.

Format: `delete [TASK_LIST] INDEX`

> Deletes the task at the specified TASK\_LIST and INDEX. The task list refers to the name of the task list e.g. &quot;floating&quot;, &quot;ongoing&quot; and &quot;completed&quot;. If no task list name specified, it is assumed to be &quot;ongoing&quot;.
The index refers to the index number shown in the most recent listing.<br><br>
The index must be a positive integer 1, 2, 3, ...

Examples:

- `list`
- `delete 2`
   - Deletes the 2nd task in the list.
- `find 2103T`
- `delete 1`
   - Deletes the 1st task in the results of the `find` command.

### 3.7. Marking a task as complete : `complete`

Marks a task as completed and moves it to the list of completed tasks.

Format: `complete [TASK_LIST] INDEX`

> Marks the task at the specified TASK\_LIST and INDEX as completed. The task list refers to the name of the task list e.g. &quot;floating&quot;, &quot;ongoing&quot;; &quot;completed&quot; is invalid as task list name. If no task list name specified, it is assumed to be &quot;ongoing&quot;.<br><br>
The index refers to the index number shown in the most recent listing.<br><br>
The index must be a positive integer 1, 2, 3, …

### 3.8. Marking a task as incomplete : `incomplete`

Marks a task as incomplete and moves it back to the list of either floating or ongoing tasks.

Format: `incomplete INDEX`

> Resets the task priority from complete to its old priority level at the specified INDEX, and places the task at its original column.<br><br>
The index refers to the index number shown in the most recent listing of the completed tasks.<br><br>
The index must be a positive integer 1, 2, 3, ...

### 3.9. Undo the previous command : `undo`

Undo the command previously entered.

Format: `undo`

> If there&#39;s a previous command that changed the state of any of the task lists e.g. add, edit or delete, it is undone and the respective task list is reverted to its prior state before the add, edit or delete command was entered.

### 3.10. Redo a command : `redo`

Redo the command previously undone.

Format: `redo`

> If there&#39;s a previous command that changed the state of any of the task lists e.g. add, edit or delete, and was undone by the undo command afterwards, it is redone and the respective task list is reverted to its prior state after the add, edit or delete command was entered.<br><br>
If the undo command was followed by another state-changing command, the command that was previously undone would not be redone.

### 3.11. Selecting a task : `select`

Selects the task identified by the index number used in the last task listing.

Format: `select [TASK_LIST] INDEX`

> Selects the task and loads the Google search page the title at the specified INDEX.<br><br>
The index refers to the index number shown in the most recent listing.<br><br>
The index must be a positive integer 1, 2, 3, ...

Examples:

- `list`
- `select 2`
   - Selects the 2nd task in the list.
- `find Eat Chips and Pizza`
- `select 1`
   - Selects the 1st task in the results of the find command.

### 3.12. Clearing all tasks : `reset`

Clears all tasks from the list.

Format: `clear`

### 3.13. Exiting the program : `exit`

Exits the program.

Format: `exit`

### 3.14. Importing files into the program : `import`

Import an ICal-formatted file into the program.

Format: `import path/to/file.ics`

### 3.15. Exporting files from the program : `export`

Export the current task list into an ICal-formatted file.

Format: `export path/to/file.ics`

### 3.16. Saving the data

All data are saved in the hard disk automatically after any command that changes the data.

There is no need to save manually.

# 4. FAQ

Q: How do I transfer my data to another Computer?

A: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous To Do Manager folder.

Q: Do I require knowledge of command line to use this program?

A: No prior command line knowledge is required to use **Burdens**. Instead, just follow the instructions under [Features](https://github.com/CS2103JAN2017-W09-B1/main/blob/master/docs/UserGuide.md#features).

# 5. Command Summary

- Help : `help`
- Add : `add TITLE [for: DEADLINE] [priority: PRIORITY] [note: INSTRUCTION] [t/TAG]...`
  - e.g. add Buy groceries for: the first Thursday of May priority: 5 note: eggs x10, milk x2, bread x2 #home #errand
- List : `list`
- Edit : `edit INDEX [TITLE] [for: DATE] [priority: PRIORITY] [note: INSTRUCTION] [#TAG]…`
   - e.g. edit Buy dinner for: everyday  p/5 note: Chicken Rice #home #food
- Find : `find KEYWORD [MORE_KEYWORDS]`
  - e.g. find Play basketball
- Delete : `delete INDEX`
  - e.g. delete 3
- Complete : `complete [TASK_LIST] INDEX`
  - e.g complete 1 2
- Incomplete : `incomplete INDEX`
  - e.g incomplete 3
- Undo : `undo`
   - e.g undo
- Redo : `redo`
   - e.g redo
- Select : `select INDEX`
  - e.g select 2
- Clear : `reset`
   - e.g reset
- Exit : `exit`
   - e.g exit
- List : `list`
   - e.g list
