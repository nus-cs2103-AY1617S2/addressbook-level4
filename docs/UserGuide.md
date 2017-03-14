# Today - User Guide

By : `T09B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`

---

1. [Introduction](#introduction)
2. [Quick Start](#quick-start)
3. [Features](#features)<br>
    3.1. [Adding Tasks](#adding-tasks)<br>
    3.2. [Viewing Tasks](#viewing-tasks)<br>
    3.3. [Managing Tasks](#managing-tasks)<br>
    3.4. [Managing Tags](#managing-tags)<br>
    3.5. [Undo/Redo Commands](#undoredo-commands)<br>
    3.6. [Miscellaneous](#miscellaneous)<br>
    3.6. [Advanced Usage](#advanced-usage)<br>
4. [FAQ](#faq)
5. [Command Summary](#command-summary)

## Introduction
**Today** is a minimalistic task manager that aims to be the simplest way to organize your day.

In our far too hectic lives, there's just so much to keep track of. Trying to manage everything is a tiring and stressful affair. Instead of overwhelming you with a neverending task list, **Today** helps you focus on the things that you *can* control - the tasks you're going to tackle today.


## Quick Start

Let's get you up and running.

### Installation Instructions
<img align="middle" src="https://github.com/CS2103JAN2017-T09-B1/main/raw/develop/docs/images/ui.png" height="300" style="float:right">

1. Install the latest Java [here](https://java.com/en/download/). This will be required to run the application.
2. Download `today.jar` from the [releases](../../../releases) tab.
3. Copy the file to the folder you want to use as the home folder for your task manager.
4. Double-click the file to start the app and you should see an interface like the image on the right.

5. You can start managing your tasks now:
   * `add Do my homework due today` creates a new entry `Do my homework` with a deadline `today`
   * `done 1, 3, 4` marks the 1st, 3rd and 4th task as complete and whisks them away to your completed list
   * `find math` helps you locate all tasks with 'math' in either its title or tag
6. To find out more about each command, refer to the [Features](#3-features) section below!

### How to get started

As you get used to the features, you can use **Today** any way you like. But for starters, this is what we recommend.

At the start of every working day, go through your inbox and `add` new tasks to your tasklist. Once you're done, review your list and mark out the tasks you want to focus on `today`. Whenever you're `done` with a task, it's shifted out of sight, allowing you to focus on the remaining tasks on hand.

Rinse and repeat until you've no more tasks left for the day!

## Features

> **<span style="font-size:20px">Input Format</span>**
<br>
>- normal text denotes command keywords
>- `< >` denotes parameters
>- `[ ]` denotes that the parameter is optional
### Adding Tasks

#### Adding a task

The first step to organizing your tasks is to make sure you have all of them in one place. **Today** makes it easy for you to add new tasks, with the option to specify a deadline or add tags to the task, making it easier for you to manage as your list gets longer.
##### Format: `add <task_name> [due <deadline>] [tag <tag1, tag2, ...>]`
##### Parameters
    task_name : Name of your task
    deadline (optional) :
      - Date only, `02/03/17` or `020317` (ddmmyy)
      - Time only which means today, `3pm` or `12noon` or `2.30am`
      - Time and Date, in any order
    tag1, tag2, ... (optional) : tags of this task
##### Examples

* `add Study for CS2106`
* `add Study for CS2106 due 3pm`
* `add Study for CS2106 due today`
* `add Study for CS2106 due 3pm today`
* `add Study for CS2106 due 02/03/17`

>**Note**: Do not use the keywords like `due` in your `<task_name>` or **Today** might misunderstand

#### Adding a new event

Task management is all about fitting tasks into your busy schedule. The only way to do that is to have a handle on the events you have planned on any given day. **Today** understands that, and allows you to add events as well.

##### Format: `add <event_name> from <starting_time> to <end_time> [on <date>] [tag <tag1, tag2, ...>]`
##### Parameters
    event_name : name of the event<br>
    starting_time : time of start of the event<br>
    end_time : time of end of the event<br>
    date (optional) : date of the event (ddmmyy)<br>
    tag1, tag2, ... (optional): tags of this event
##### Examples

* `add dinner from 5pm to 5.30pm`
* `add meeting from 10am to 11am on Wednesday`

### Viewing Tasks
#### Listing all ongoing tasks

By default, **Today** will display all the tasks you have for today. If you performed a `find` command that filters the irrelevant tasks, you can get back your full tasklist by using this command.

##### Format: `list`
##### Parameters
    no parameters taken

#### Finding tasks by keyword

Most of us have plenty of things to do, which means plenty of tasks on our tasklist. Manually looking through the list to find something would be like looking for a needle in a haystack. **Today** saves you the trouble of doing that with a `find` command.

##### Format: `find <keyword1 keyword2 ...>`
##### Parameters
    keyword1, keyword2, ... : All the parameters you want in the search.
##### Examples

* `find schoolwork`
* `find math science geography`

#### Finding tasks by deadline

Your favourite band is coming to town and you want to get tickets for their concert. How would you know if you're free to watch that concert three Saturdays from now? Using **Today**'s `find due` command, you'll be able to find out for yourself if you have anything due that day. If you do, maybe it's time to start working on it first!

##### Format: `find due <deadline>`
##### Parameters
    deadline :
      - Date only, `02/03/17` or `020317` (ddmmyy)
      - Time only which means today, `3pm` or `12noon` or `2.30am`
      - Time and Date, in any order

##### Examples

* `find due 21042017`


### Managing Tasks
#### Marking task as done

Once you're done with a task, mark it as `done`and we'll archive it for you. Do this often enough, and you'll have a great sense of satisfaction watching your tasklist get shorter.

##### Format: `done <id1, id2, ...>`
##### Parameters
    id1, id2, ... : displayed task ids

##### Examples

* `done 3`
* `done 3,4,5,6`

#### Marking task as not done

Be honest. If you know you didn't do that task properly, and you want to come back to it, use the `notdone` function to bring it back from the completed list.

##### Format: `notdone <id1, id2, ...>`
##### Parameters
    id1, id2, ... : displayed task ids

##### Examples

* `notdone 3`
* `notdone 3,4,5,6`

#### Updating task

We make mistakes all the time. When you've entered a wrong task name, or even a wrong deadline, correct it using the `update` command.

##### Format: `update <id> [<new_task_name>] [due <new_deadline>] [tag <tag1, tag2, ...>] [remove tag <tag1, tag2, ...>]`
##### Parameters
    id : displayed task id
    new_task_name (optional) : new name to replace the old task name
    new_deadline (optional) :
      - Date only, `02/03/17` or `020317` (ddmmyy)
      - Time only which means today, `3pm` or `12noon` or `2.30am`
      - Time and Date, in any order
    tag1, tag2, ... (optional): tags to be added or removed

##### Examples

* `update 7 clean house`
* `update 7 due 05/06/17`
* `update 7 tag schoolwork, CS2103`
* `update 7 clean house due 04/05/17 tag schoolwork, CS2103`

#### Deleting task

Some tasks don't get `done`. We put them aside and after several eons, we discover that we don't have to actually do them anymore. If you're feeling too guilty to mark the task as `done`, that's the perfect time to `delete` it.

##### Format: `delete <id1, id2, ...>`
##### Parameters
    id1, id2, ... : displayed task ids

##### Examples

* `delete 3`
* `delete 3,4,5,6`

### Managing Tags

#### Renaming tag

You've been tagging a bunch of tasks as `work`. Now that you're quitting Google, you might want to mark them as `Google` instead. Instead of going through your tasks one by one, all of the tasks can be instantly retagged with `Google` with this function.

##### Format: `rename tag from <tag_name> to <new_tag_name>`
##### Parameters
    tag_name : existing tag name
    new_tag_name : new tag name

##### Examples

* `rename work to Google`
* `rename girlfriend to ex-gf`

### Undo/Redo Commands

#### Undo Command

You accidentally marked an undone task as `done`. You can quickly `undo` what you've done and get the task back by using this command. Note that you can only undo a maximum of 5 commands.

##### Format: `undo`
##### Parameters
    No parameters taken

#### Redo Command

You used `undo` without meaning to, or you've used `undo` one too many times. Using `redo` will undo your last `undo` command.

##### Format: `redo`
##### Parameters
    No parameters taken

### Advanced Usage
#### Change storage location

Some of you power-users want to keep your tasks synced between devices. Using this function, you can get **Today** on different devices to point at the same synced file (using a service like Dropbox. This ensures that your tasks will be kept synced when a change is performed on any of your linked devices.

##### Format: `saveto <dir_location>`
##### Parameters
    dir_location : path to new save location
##### Examples

* `saveto C:\Desktop`
* `saveto ..\mySecretFolder`

#### Export file

Don't worry if you don't have the luxury of a syncing service. If you want to transfer tasks from one computer to the next, you can `export` your tasks, and transfer the output file using a USB stick.

##### Format: `export <dir_location>`
##### Parameters
    dir_location: path to store exported file
##### Examples

* `export to C:\Desktop`
* `export to ..\mySecretFolder`

#### Import file

In your second computer, plug in the USB stick and get the file path of the previously exported file. Tell **Today** to `import` this file and it will add these tasks to your current tasklist.

##### Format: `import from <path_to_file>`
##### Parameters
    path_to_location: path to the exported task list

##### Examples

* `import from C:\Desktop\exported.txt`
* `import from ..\mySecretFolder\exported.txt`

### Miscellaneous
#### Getting help

To view this user guide you're currently viewing, you can use the F1 hotkey or use the `help` command.

##### Format: `help`
##### Parameters
    No parameters taken

### Exit

At the end of a long day, you deserve some time away from your task manager. You can `exit` **Today** and enjoy the rest of your day!

##### Format: `exit`
##### Parameters
    No parameters taken

## FAQ

Work-in-progress

## Command Usage

* **Add Task**: `add <task_name> [due <deadline>] [tag <tag1, tag2, ...>]`
* **Add Event**: `add <event_name> from <starting_time> to <end_time> [on <date>] [tag <tag1, tag2, ...>]`
* **View All Tasks**: `list`
* **Find Task**: `find <keyword1 keyword2 ...>`
* **Complete Task**: `done <id1, id2, ...>`
* **Un-complete Task**: `undone <id1, id2, ...>`
* **Update Task**: `update <id> [<new_task_name>] [due <new_deadline>] [tag <tag1, tag2, ...>] [remove tag <tag1, tag2, ...>]`
* **Delete Task**: `delete <id1, id2, id3>`
* **Rename Tag**: `rename tag from <tag_name> to <new_tag_name>`
* **Change Storage Location**: `saveto <dir_location>`
* **Export**: `export to <dir_location>`
* **Import**: `import from <path_to_file>`
* **Help**: `help [<command>]`
* **Exit**: `exit`
