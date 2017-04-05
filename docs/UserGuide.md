# Today - User Guide

By : `T09B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`

---

1. [Introduction](#1-introduction)
2. [Quick Start](#2-quick-start)
3. [Features](#3-features)<br>
    3.1. [Adding Tasks](#31-adding-tasks)<br>
    3.2. [Viewing Tasks](#32-viewing-tasks)<br>
    3.3. [Managing Tasks](#33-managing-tasks)<br>
    3.4. [Managing Tags](#34-managing-tags)<br>
    3.5. [Undo/Redo Commands](#35-undoredo-commands)<br>
    3.6. [Advanced Usage](#36-advanced-usage)<br>
    3.7. [Miscellaneous](#37-miscellaneous)
4. [FAQ](#4-faq)
5. [Command Summary](#5-command-summary)

## 1. Introduction
**Today** is a minimalistic task manager that aims to be the simplest way to organize your day.

In our far too hectic lives, there's just so much to keep track of. Trying to manage everything is a tiring and stressful affair. Instead of overwhelming you with a neverending task list, **Today** helps you focus on the things that you *can* control - the tasks you're going to tackle today.

This user guide is here to guide you through the [installation](#installation-instructions) of our application and give you a walkthrough of our [basic features](#features). We've also provided a [cheatsheet](#command-summary) with a summary of all our commands available.

## 2. Quick Start

Let's get you up and running.

### 2.1. Installation Instructions
<img src="https://github.com/CS2103JAN2017-T09-B1/main/raw/develop/docs/images/uidesc.png" height="300">

_Fig 2.1.1. Default view of the task manager_

1. Install the latest Java [here](https://java.com/en/download/). This will be required to run the application.
2. Download `today.jar` from the [releases](../../../releases) tab.
3. Copy the file to the folder you want to use as the home folder for your task manager.
4. Double-click the file to start the app and you should see an interface like the image above.

### 2.2. How to get started

As you get used to the features, you can use **Today** any way you like. But for starters, this is what we recommend.

1. At the start of every working day, go through your inbox and `add` new tasks to your tasklist.
2. Once you're done, review your list and mark out the tasks you want to focus on `today`.
3. Whenever you're `done` with a task, it's shifted out of sight, allowing you to focus on the remaining tasks on hand.

Rinse and repeat until you have no more tasks left!

## 3. Features

> **<span style="font-size:20px">What These Symbols Represent</span>**<br>
> * normal text denotes command keywords
> * `< >` denotes parameters
> * `[ ]` denotes that the parameter is optional


### 3.1. Adding Tasks

#### 3.1.1. Adding a task

The first step to organizing your tasks is to make sure you have all of them in one place. **Today** makes it easy for you to add new tasks, with the option to specify a deadline or add tags to the task, making it easier for you to manage as your list gets longer.

##### Format: `add <task_name> [due <date> at <time>] [tag <tag1 tag2 ...>]`

##### Parameters

    task_name                : Name of your task

    date (optional)          : Due date of your task in dd/mm/yy or in natural language

    time (optional)          : Time your task is due in HH:MM or HH am/pm

    tag1 tag2 ... (optional) : tags of this task

##### Examples

* `add Study for CS2106`
* `add Study for CS2106 due today`
* `add Study for CS2106 due today at 15:00`
* `add Study for CS2106 due today at 3pm`
* `add Study for CS2106 due today at 3pm tag lab`
* `add Study for CS2106 due 20/03/17`
* `add Study for CS2106 due 20/03/17 at 20:00`
* `add Study for CS2106 due 20/03/17 at 8pm`
* `add Study for CS2106 due 20/03/17 at 8pm tag school`

> Do not use keywords like `due` in your `<task_name>` or **Today** might misunderstand

#### 3.1.2. Adding a new event

Task management is all about fitting tasks into your busy schedule. The only way to do that is to have a handle on the events you have planned on any given day. **Today** understands that, and allows you to add events as well.

##### Format: `add <event_name> from <starting_date_time> to <end_date_time> [tag <tag1 tag2 ...>]`

##### Parameters

    event_name                 : name of the event

    starting_date_time         : date and time of start of the event

    end_date_time              : date and time of end of the event

    tag1 tag2 ... (optional) : tags of this event

##### Examples

* `add dinner from 22nd March 5pm to 22nd March 5.30pm`
* `add meeting from 22nd March 10am to 24th March 11am`

### 3.2. Viewing Tasks

#### 3.2.1. Finding tasks by keyword

Most of us have plenty of things to do, which means plenty of tasks on our tasklist. Manually looking through the list to find something would be like looking for a needle in a haystack. **Today** saves you the trouble of doing that with a `find` command, showing you all the tasks related to any of the keywords you provide.

##### Format: `find <keyword1 keyword2 ...>`

##### Parameters

    keyword1, keyword2, ...  : All the parameters you want in the search

##### Examples

* `find schoolwork`
* `find math science geography`

#### 3.2.2. Finding tasks by deadline

Your favourite band is coming to town and you want to get tickets for their concert. How would you know if you're free to watch that concert three Saturdays from now? Using **Today**'s `find due` command, you'll be able to find out for yourself if you have anything due that day. If you do, maybe it's time to start working on it first!

##### Format: `find <date>`

##### Parameters

    date : Due date of your task in dd/mm/yy or in natural language

##### Examples

* `find 21/04/17`
* `find tomorrow`

#### 3.2.3. Listing all uncompleted tasks

By default, **Today** already displays all your uncompleted tasks. However, if you've just used a `find` command, **Today** would have hidden all tasks irrelevant to your query. Don't worry! You can get back your full tasklist by using this command.

##### Format: `list`


#### 3.2.4. Listing all Completed Tasks

There may be times when you would wish to review the tasks you have already completed. You can easily view all your completed task through **Today**'s `listcompleted` command. This will open a drop down list that will display all the tasks you have completed. Use the previously mentioned `list` command to hide this list again.

##### Format: `listcompleted`

### 3.3. Managing Tasks

#### 3.3.1. Marking tasks as **Today**

As suggested in the [quick-start guide](#22-how-to-get-started), we recommend that users compile a list of tasks to focus on, regardless on deadline. The `today` command shifts tasks from your future list to your list of today's tasks.

##### Format: `today <id1, id2, ...>`

##### Parameters
    id1, id2, ... : displayed task ids

##### Examples

* `today F1`
* `today F1, F3, F4`

> * `T<number>` denotes a task to be done today
> * `F<number>` denotes a task to be done in the future
> * `C<number>` denotes a task that has already been completed

#### 3.3.2. Marking task as done

Once you're done with a task, mark it as `done` and we'll archive it for you. Do this often enough, and you'll have a great sense of satisfaction watching your tasklist get shorter.

##### Format: `done <id1, id2, ...>`

##### Parameters
    id1, id2, ... : displayed task ids

##### Examples

* `done T3`
* `done T3,T4,T5,T6`

#### 3.3.3. Marking task as not done

Be honest. If you know you didn't do that task properly, and you want to come back to it, use the `notdone` function to bring it back from the completed list.

##### Format: `notdone <id1, id2, ...>`
##### Parameters
    id1, id2, ... : displayed task ids

##### Examples

* `notdone 3`
* `notdone 3,4,5,6`

#### 3.3.4. Editing a task

We make mistakes all the time. When you've entered a wrong task name, or even a wrong deadline, correct it using the `edit` command.

##### Format: `edit <id> [<new_task_name>] [due <new_date> at <new_time>] [tag <tag1, tag2, ...>] [removetag <tag1 tag2 ...>]`

##### Parameters

    id                       : displayed task id

    new_task_name (optional) : new name to replace the old task name

    new_date      (optional) : Due date of your task in dd/mm/yy or in natural language

    new_time      (optional) : Time your task is due in HH:MM or HH am/pm

    tag1 tag2 ... (optional) : tags to be added or removed

##### Examples

* `edit T7 clean house`
* `edit T7 due 05/06/17 at 4pm`
* `edit T7 tag schoolwork CS2103`
* `edit T7 clean house due 04/05/17 at 6pm tag schoolwork CS2103`

#### 3.3.5. Deleting tasks

Some tasks don't get `done`. We put them aside and after several eons, we discover that we don't have to actually do them anymore. If you're feeling too guilty to mark the task as `done`, that's the perfect time to `delete` it.

##### Format: `delete <id1, id2, ...>`

##### Parameters
    id1, id2, ... : displayed task ids

##### Examples

* `delete F3`
* `delete T3,T4,F5,F6`

### 3.4. Managing Tags

#### 3.4.1. Renaming tag

You've been tagging a bunch of tasks as `work`. Now that you're quitting Google, you might want to mark them as `Google` instead. Instead of going through your tasks one by one, all of the tasks can be instantly retagged with `Google` with this function.

##### Format: `renametag <tag_name> <new_tag_name>`

##### Parameters

    tag_name     : existing tag name

    new_tag_name : new tag name

##### Examples

* `renametag work Google`
* `renametag girlfriend ex-gf`

#### 3.4.2. Deleting tag

When you reorganize your tasks into different tags, from say "work" vs "family" to something more specific like "boss jim", "boss sarah", "annoying wife jeanie", "daughter Samantha", you can delete your former tags "work" and "family", and retag your tasks.

##### Format: `deletetag <tag_name>`

##### Parameters

    tag_name : existing tag name

##### Examples

* `deletetag work`
* `deletetag family`

### 3.5. Undo/Redo Commands

#### 3.5.1. Undo Command

You accidentally marked an undone task as `done`. You can quickly `undo` what you've done and get the task back by using this command. Note that you can only undo a maximum of 5 commands.

##### Format: `undo`

#### 3.5.2. Redo Command

You used `undo` without meaning to, or you've used `undo` one too many times. Using `redo` will undo your last `undo` command.

##### Format: `redo`

### 3.6. Advanced Usage

#### 3.6.1. Change storage location

Some of you power-users want to keep your tasks synced between devices. Using this function, you can get **Today** on different devices to point at the same synced file (using a service like Dropbox. This ensures that your tasks will be kept synced when a change is performed on any of your linked devices.

##### Format: `saveto <dir_location>`

##### Parameters

    dir_location : path to new save location

##### Examples

* `saveto C:\Desktop`
* `saveto ..\mySecretFolder`

#### 3.6.2. Load different data file

Once you have saved your file to your Dropbox folder, your other computer will need to be told where this file is. Use the usethis command to load the tasks from this data file. Any changes you make to this file will also be reflected in your original file.

##### Format: `usethis <dir_location>`

##### Parameters

    dir_location : path to save location

#### 3.6.3. Export file

Don't worry if you don't have the luxury of a syncing service. If you want to transfer tasks from one computer to the next, you can `export` your tasks, and transfer the output file using a USB stick.

##### Format: `export <dir_location>`

##### Parameters

    dir_location : path to store exported file

##### Examples

* `export C:\Desktop`
* `export ..\mySecretFolder`

#### 3.6.4. Import file

In your second computer, plug in the USB stick and get the file path of the previously exported file. Tell **Today** to `import` this file and it will add these tasks to your current tasklist.

##### Format: `import <path_to_file>`

##### Parameters

    path_to_location : path to the exported task list

##### Examples

* `import C:\Desktop\exported.txt`
* `import ..\mySecretFolder\exported.txt`

### 3.7. Miscellaneous

#### 3.7.1. Getting help

To view this user guide you're currently viewing, you can use the F1 hotkey or use the `help` command.

##### Format: `help`

### 3.7.2. Exit

At the end of a long day, you deserve some time away from your task manager. You can `exit` **Today** and enjoy the rest of your day!

##### Format: `exit`

## 4. FAQ

Work-in-progress

## 5. Command Summary

| Action | Command |
| ------ | ------- |
| **Add Task** | `add <task_name> [due <date> at <time>] [tag <tag1 tag2 ...>]` |
| **Add Event** | `add <event_name> from <starting_date_time> to <end_date_time> [tag <tag1 tag2 ...>]`|
| **View All Tasks**| `list`|
| **Find Task**| `find <keyword1 keyword2 ...>`|
| **Set tasks as today**| `today <id1, id2, ...>`|
| **Complete Task**| `done <id1, id2, ...>`|
| **Un-complete Task**| `notdone <id1, id2, ...>`|
| **Update Task**| `update <id> [<new_task_name>] [due <new_date> at <new_time>] [tag <tag1 tag2 ...>] [remove tag <tag1 tag2 ...>]`|
| **Delete Task**| `delete <id1, id2, id3>`|
| **Rename Tag**| `renametag <tag_name> <new_tag_name>`|
| **Change Storage Location**| `saveto <dir_location>`|
| **Export**| `export <dir_location>`|
| **Import**| `import <path_to_file>`|
| **Help**| `help`|
| **Exit**| `exit`|
