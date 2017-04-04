# Burdens: User Guide

By : `TEAM W09-B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp;

1. [Introduction](#1-introduction)
2. [Quick Start](#2-quick-start)
3. [Features](#3-features)
   > 3.1. [Add tasks](#32-adding-a-task--add)<br/>
     3.2. [Delete tasks](#36-deleting-a-task--delete)<br/>
     3.3. [Edit tasks](#34-editing-a-task--edit)<br/>
     3.4. [List tasks](#33-listing-all-tasks--list)<br/>
     3.5. [Find tasks](#35-finding-all-tasks-containing-any-keyword-in-their-title--find)<br/>
     3.6. [Select tasks](#311-selecting-a-task--select)<br/>
     3.7. [Mark tasks as complete](#37-marking-a-task-as-complete--complete)<br/>
     3.8. [Mark tasks as incomplete](#38-marking-a-task-as-incomplete--incomplete)<br/>
     3.9. [Undo](#39-undo-the-previous-command--undo)<br/>
     3.10. [Redo](#310-redo-a-command--redo)<br/>
     3.11. [Revert](#310-redo-a-command--redo)<br/>
     3.12. [Unrevert](#310-redo-a-command--redo)<br/>
     3.13. [Specifying the data storage location](#39-undo-the-previous-command--undo)<br/>
     3.14. [Import as Google Calender file](#314-importing-files-into-the-program--import)<br/>
     3.15. [Export as Google Calender file](#315-exporting-files-from-the-program--export)<br/>
     3.16. [Save](#316-saving-the-data)<br/>
     3.17. [Reset](#312-clearing-all-tasks--reset)<br/>
     3.18. [Help](#31-viewing-help--help)<br/>
     3.19. [Exit](#313-exiting-the-program--exit)<br/>
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
using your keyboard commands with a beautiful interface.<br/>
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

Each square has the own respective lists and indexes.

Type a command in the command box and press <kbd>Enter</kbd> to execute it.

e.g. typing `help` and pressing <kbd>Enter</kbd> will open the Help window.

Refer to the [Features](https://github.com/CS2103JAN2017-W09-B1/main/blob/master/docs/UserGuide.md#features) section below for details of each command.

# 3. Features

To use Burdens effectively, we need to master the following commands.

> ### Commands
> **Please note the following:**
> * Commands words are case-sensitive, please follow the command summary.
> * Words with a colon `:` at the end are keywords.
> * Items in `<Angle Brackets>` are parameters.
> * Items in `[Square Brackets]` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.
> * The following section heads show a description of the command and the command to be used.

### 3.1. Adding a task : `add`

Let's begin to add a task to Burdens.

Here are the **allowed formats**:

* `add <title>` - adds a floating task with the title to Burdens.
* `add <title> for: <deadline>` - adds a task which have to be done by the specified deadline. Please use the keyword `for: `.
* `add <title> for: <start of deadline> to <end of deadline>` - adds an event which will take place between start and end of the deadline. Note the keyword `for:` and `to`.
* `add <title> priority: <1 to 5>` - adds a floating task with a priority level.
* `add <title> note: <instructions>` - adds a floating task with instructions.
* `add <title> #<tag>` - adds a floating task with a tag. Note that you can have multiple tags.
For full details of each parameter, please view their formats in their respective sections below.

The full allowed command format is therefore:

* `add <title> for:<deadline> priority:<priority level> note:<instructions> #<tag>`

#### Date Time Format
Let us show you the parameters to fill for `<start of deadline>` and `<end of deadline>`.
Firstly, type the "for: " tag after the add command.<br>
Burdens employs natural date formats.<br>
You are free to use any of the date formats and time formats below.<br>

| Deadline Format | Example(s)                |
|-----------------|---------------------------|
| Floating        | `nothing`/floating        |
| Month/day       | 1/23                      |
| Month/day time  | 1/23 6pm                  |
| Day Month       | 1 Oct                     |
| Month Day       | Oct 1                     |
| Day of the Week | Wed, Wednesday            |
| Relative date   | today, tomorrow, next fri |

**Tip** : You can use holidays like Christmas as well!<br>

#### Priority Format
Let us show you the parameters to fill for `<priority level>`.<br>
Remember to put the `priority:` keyword.<br>
Burdens will assign this numbers as a unique tag.<br>
The priority parameter only accepts levels 1 - 5.<br>

| Priority Format | Tag                       |
|-----------------|---------------------------|
| 1               | lame                      |
| 2               | decent                    |
| 3               | moderate                  |
| 4               | forreal                   |
| 5               | urgent                    |

#### Instruction Format
Let us show you the parameters to fill for `<instructions>`.<br>
Remember to put the `note:` keyword.<br>
You can put any instruction you like, similar to `<title>`.<br>

#### Tag Format
Let us show you the parameters to fill for `tag`.<br>
Remember to put the `#` keyword before all tags.<br>
Tasks can have any number of tags, including 0.<br>

Let us show you some *examples*:
```
I want to buy apples but I don't wish to have a deadline.
> Command: add Buy Appples
Result: Burdens will ask this task to the "Floating" square.

I want to buy oranges by the end of this week.
> Command: add Buy Oranges for: end of this week
Result: Burdens will add this task to the "Ongoing" square and assign it to the Friday of that week.

I want to buy pears which will happen on Christmas and eat them from 6pm to 12am.
> Command: add Buy Pears for: Christmas from 6pm to 12am
Result: Burdens will add this task to the "Ongoing" square and assign it to the Christmas of that year and add an event tag `E`.

I want to buy mangoes, it's extremely decent and I want to add an instruction that it's due on Friday.
> Command: add Buy Mangoes for: Friday priority: 2 note: I must buy it on Friday.
Result: Burdens will add this task to the "Ongoing" square and assign it to a priority level of 5.

Now it's time for a full command.
I want to buy Burdens today, it's of the utmost importance, I want to note that I have to get it and I want to tag it as cool and awesome!
> Command: add Buy Burdens for: today priority: 5 note: I have to get it #cool #awesome
```

### 3.2. Deleting a task : `delete`

CLeared a burden? You can eliminate it by using the `delete` a command.

Let's begin to delete a task from Burdens.

Here are the **allowed formats**:

* `delete <index>` - deletes an ongoing task from Burdens at the specified index.
* `delete floating <index>` - deletes a floating task from Burdens at the specified index.
* `delete completed <index>` - deletes a completed task from Burdens at the specified index.

Let us show you some *examples*:
```
I no longer want to buy my apples and wish to remove the task from Burdens. It is in the ongoing list at index 3.
> Command: delete 3
Result: Burdens will delete this task from the "Ongoing" square.

I bought my oranges and completed the task. It is now at index 2 of the completed list. Now I wish to delete it from Burdens.
> Command: delete completed 2
Result: Burdens will delete this task from the "Completed" square.
```

### 3.3. Editing a task : `edit`

Want to rectify something that was not right? Changed dates?

Burdens allows you to edit your tasks at any field, at any square.

Let's begin to edit a task from Burdens.

Here are the **allowed formats**:

*  You can add any of these fields after the `<index>` of all the commands below to edit them, <br>
   `<title> <for: deadline> <priority: priority> <note: instructions> <#tag>`
* `edit <index>` - edits an ongoing task from Burdens at the specified index.
* `edit floating <index>` - deletes a floating task from Burdens at the specified index.
* `edit completed <index>` - deletes a completed task from Burdens at the specified index.

Note:

* At least one of the optional fields apart from the task list name must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
* You can remove all tags by typing `#` and nothing after that.

Let us show you some *examples*:
```
I want to buy oranges tomorrow instead of apples today. My task is in the ongoing list at index 3.
> Command: edit 3 Buy Oranges for: tomorrow
Result: Burdens will edit this task and change the title to Buy Oranges and deadline to tomorrow at the "Ongoing" square.

I want to change the instructions of task 2 at the floating list from fruits to vegetables.
> Command: edit floating 2 note: vegetables
Result: Burdens will edit the instructions of the second task to vegetables at the "Floating" square.
```

### 3.4. Listing all tasks : `list`

Shows a list of all tasks.

Format: `list`

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

### 3.18. Viewing help : `help`

Prompts a help page with all the existing commands.

Format: `help`

> Help is also shown if you enter an incorrect command e.g. abcd

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
