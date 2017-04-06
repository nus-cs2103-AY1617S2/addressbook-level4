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

1. Install the latest Java [here](https://java.com/en/download/). This will be required to run the application.
2. Download `today.jar` from the [releases](../../../releases) tab.
3. Copy the file to the folder you want to use as the home folder for your task manager.
4. Double-click the file to start the app and you should see an interface like the image below.

<img src="https://github.com/CS2103JAN2017-T09-B1/main/raw/develop/docs/images/211defaultview.PNG" height="300">

_Fig 2.1.1. Default view of the task manager_

### 2.2. How to get started

As you get used to the features, you can use **Today** any way you like. But for starters, this is what we recommend.


1. At the start of every working day, go through your inbox and `add` new tasks to your tasklist.

<img style="display: block; margin: auto" src="https://github.com/CS2103JAN2017-T09-B1/main/raw/develop/docs/images/221addtask.PNG" height="200" align="center">

2. Once you're done, review your list and mark out the tasks you want to focus on `today`.

<img style="display: block; margin: auto"  src="https://github.com/CS2103JAN2017-T09-B1/main/raw/develop/docs/images/222today.PNG" height="200">

3. Whenever you're `done` with a task, it's shifted out of sight, allowing you to focus on the remaining tasks on hand.

<img style="display: block; margin: auto"  src="https://github.com/CS2103JAN2017-T09-B1/main/raw/develop/docs/images/223done.PNG" height="200">

Rinse and repeat until you have no more tasks left!

<img style="display: block; margin: auto"  src="https://github.com/CS2103JAN2017-T09-B1/main/raw/develop/docs/images/224result.PNG" height="200">

## 3. Features

> **<span style="font-size:20px">What These Symbols Represent</span>**<br>
> * normal text denotes command keywords
> * `< >` denotes parameters
> * `[ ]` denotes that the parameter is optional
>
> **<span style="font-size:20px">DateTime Inputs</span>**
>
> **Today** supports datetime inputs in natural language. This means that you are free to specify the deadline using words like today, tomorrow, next week and so on. You can also use a dd/mm/yyyy format if you wish. If only time is provided, we assume you mean today. If only date is provided, we assume you mean 11:59pm.


### 3.1. Adding Tasks

#### 3.1.1. Adding a task

The first step to organizing your tasks is to make sure you have all of them in one place. **Today** makes it easy for you to add new tasks, with the option to specify a deadline or add tags to the task, making it easier for you to manage as your list gets longer.

##### Format: `add <task_name> [due <deadline>] [#<tag1> #<tag2> ...>]`

##### Parameters

    task_name                : Name of your task

    deadline (optional)      : Deadline of your task

    tag1 tag2 ... (optional) : Tags of this task

##### Examples

* `add Study for CS2106`
* `add Study for CS2106 due today`
* `add Study for CS2106 due today 3pm`
* `add Study for CS2106 due 20/03/17 3pm`
* `add Study for CS2106 due 20/03/17 3pm #lab #cs2106`

#### 3.1.2. Adding a new event

Task management is all about fitting tasks into your busy schedule. The only way to do that is to have a handle on the events you have planned on any given day. **Today** understands that, and allows you to add events as well.

##### Format: `add <event_name> from <starting_date_time> to <end_date_time> [#<tag1> #<tag2> ...>]`

##### Parameters

    event_name                 : Name of the event

    starting_date_time         : Starting date and time of the event

    end_date_time              : Date and time of end of the event.
                                 Note that if date is provided in end_date_time but not in
                                 starting_date_time, we assume that you mean to use the same
                                 date for both start and end date times.
                                 i.e. 2pm to 3pm Friday --> 2pm Friday to 3pm Friday

    tag1 tag2 ... (optional)   : Tags of this event

##### Examples

* `add dinner from 5pm to 5.30pm`
* `add meeting from 10am to 24th March 11am #work`

### 3.2. Viewing Tasks

#### 3.2.1. Finding tasks by keyword

Most of us have plenty of things to do, which means plenty of tasks on our tasklist. Manually looking through the list to find something would be like looking for a needle in a haystack. **Today** saves you the trouble of doing that with a `find` command, showing you all the tasks related to any of the keywords you provide.

Note that **Today** returns everything related to any of the specified keywords. For example, `find math science` returns tasks or events that contain math or science in its title or tags.

<img src="https://github.com/CS2103JAN2017-T09-B1/main/raw/develop/docs/images/322taskfound.PNG" height="300">

##### Format: `find <keyword1 keyword2 ...>`

##### Parameters

    keyword1 keyword2 ...  : All the parameters you want in the search

##### Examples

* `find schoolwork`
* `find math science`

#### 3.2.2. Finding tasks by deadline

Your favourite band is coming to town and you want to get tickets for their concert. How would you know if you're free to watch that concert three Saturdays from now? Using **Today**'s `find due` command, you'll be able to find out for yourself if you have anything due that day. If you do, maybe it's time to start working on it first!

##### Format: `find due <date>`

##### Parameters

    date : Due date of your task in dd/mm/yyyy or in natural language

##### Examples

* `find due 21/04/17`
* `find due tomorrow`

#### 3.2.3. Listing all uncompleted tasks

By default, **Today** already displays all your uncompleted tasks. However, if you've just used a `find` command, **Today** would have hidden all tasks irrelevant to your query. Don't worry! You can get back your full tasklist by using this command.

##### Format: `list`

#### 3.2.4. Listing all Completed Tasks

There may be times when you would wish to review the tasks you have already completed. You can easily view all your completed task through **Today**'s `listcompleted` command. This will open a drop down list that will display all the tasks you have completed. Use the previously mentioned `list` command to hide this list again.

##### Format: `listcompleted`

<img src="https://github.com/CS2103JAN2017-T09-B1/main/raw/develop/docs/images/324completed.PNG" height="300">

### 3.3. Managing Tasks

#### 3.3.1. Marking a task as today

As suggested in the [quick-start guide](#22-how-to-get-started), we recommend that users compile a list of tasks to focus on, regardless of deadline. The `today` command shifts tasks from your future list to your list of today's tasks.

In the screenshot below, we use the command `today F4` to shift a task initially due tomorrow to today's task list.

<img src="https://github.com/CS2103JAN2017-T09-B1/main/raw/develop/docs/images/331today.PNG" height="300">

##### Format: `today <id>`

##### Parameters
    id : Displayed task id

##### Examples

* `today F4`

#### 3.3.2. Marking a task as not today

If you've added a task to the today list, but later decide that you already have enough on your plate, feel free to shift it back to the future list using the `nottoday` command. Note that if a task is already due today, using `nottoday` will not shift it to the future list.

##### Format: `nottoday <id>`

##### Parameters
    id : Displayed task id

##### Examples

* `nottoday F4`

#### 3.3.3. Marking a task as done

Once you're done with a task, mark it as `done` and we'll archive it for you. Do this often enough, and you'll have a great sense of satisfaction watching your tasklist get shorter.

##### Format: `done <id>`

##### Parameters
    id : Displayed task id

##### Examples

* `done T3`
* `done F3`

#### 3.3.4. Marking a task as not done

Be honest. If you know you didn't do that task properly, and you want to come back to it, use the `notdone` function to bring it back from the completed list.

##### Format: `notdone <id>`
##### Parameters
    id : Displayed task id

##### Examples

* `notdone 3`
* `notdone 3`

#### 3.3.5. Editing a task

We make mistakes all the time. When you've entered a wrong task name, or even a wrong deadline, correct it using the `edit` command.

##### Format: `edit <id> [<new_task_name>] [due <new_date_time>] [#<tag1> #<tag2> ...]`

##### Parameters

    id                       : Displayed task id

    new_task_name (optional) : New name to replace the old task name

    new_date_time (optional) : New deadline of your task

    tag1 tag2 ... (optional) : Tags to replace the current tags

##### Examples

* `edit T7 clean house`
* `edit T7 due 05/06/17 4pm`
* `edit T7 #schoolwork #CS2103`
* `edit T7 clean house due 04/05/17 6pm #schoolwork #CS2103`

#### 3.3.6. Deleting a task

Some tasks don't get `done`. We put them aside and after several eons, we discover that we don't have to actually do them anymore. If you're feeling too guilty to mark the task as `done`, that's the perfect time to `delete` it.

##### Format: `delete <id>`

##### Parameters

    id : displayed task id

##### Examples

* `delete F3`
* `delete T3`

### 3.4. Managing Tags

#### 3.4.1. Renaming tag

You've been tagging a bunch of tasks as `work`. Now that you're quitting your current workplace, you might want to mark them as `oldwork` instead. Instead of going through your tasks one by one, all of the tasks can be instantly retagged with `oldwork` with this function.

##### Format: `renametag <tag_name> <new_tag_name>`

##### Parameters

    tag_name     : Existing tag name

    new_tag_name : New tag name

##### Examples

* `renametag work oldwork`
* `renametag girlfriend ex-gf`

#### 3.4.2. Deleting tag

When you reorganize your tasks into different tags, from say "work" vs "family" to something more specific like "boss jim", "boss sarah", "annoying wife jeanie", "daughter Samantha", you can delete your former tags "work" and "family", and retag your tasks.

##### Format: `deletetag <tag_name>`

##### Parameters

    tag_name : Existing tag name

##### Examples

* `deletetag work`
* `deletetag family`

### 3.5. Undo/Redo Commands

#### 3.5.1. Undo Command

You accidentally marked an undone task as `done`. You can quickly `undo` what you've done and get the task back by using this command. Note that you can undo all commands you've run since you opened the application. Once you close the application, all undo data is lost and you won't be able to undo previous commands upon restarting the application.

##### Format: `undo`

#### 3.5.2. Redo Command

You used `undo` without meaning to, or you've used `undo` one too many times. Using `redo` will undo your last `undo` command.

##### Format: `redo`

### 3.6. Advanced Usage

#### 3.6.1. Change storage location

Some of you power-users want to keep your tasks synced between devices. Using this function, you can get **Today** on different devices to point at the same synced file (using a service like Dropbox. This ensures that your tasks will be kept synced when a change is performed on any of your linked devices.

##### Format: `saveto <dir_location>`

##### Parameters

    dir_location : Path to folder of new savefile location

##### Examples

* `saveto C:\Users\<YourName>\Desktop`
* `saveto ../mySecretFolder`

#### 3.6.2. Load different data file

Once you have saved your file to your Dropbox folder, your other computer will need to be told where this file is. Use the usethis command to load the tasks from this data file. Any changes you make to this file will also be reflected in your original file.

##### Format: `usethis <dir_location>`

##### Examples

* `usethis C:\Users\<YourName>\Desktop`
* `usethis ../mySecretFolder`

##### Parameters

    dir_location : Path to folder of existing savefile location

#### 3.6.3. Export file

Don't worry if you don't have the luxury of a syncing service. If you want to transfer tasks from one computer to the next, you can `export` your tasks, and transfer the output file using a USB stick.

##### Format: `export <dir_location>`

##### Parameters

    dir_location : Path of the folder to be used to store exported file

##### Examples

* `export C:\Users\<YourName>\Desktop`
* `export ../mySecretFolder`

#### 3.6.4. Import file

In your second computer, plug in the USB stick and get the file path of the previously exported file. Tell **Today** to `import` this file and it will add these tasks to your current tasklist.

##### Format: `import <dir_location>`

##### Parameters

    dir_location : Path to folder of file to be imported

##### Examples

* `import C:\Users\<YourName>\Desktop`
* `import ../mySecretFolder`

### 3.7. Miscellaneous

#### 3.7.1. Getting help

To view this user guide you're currently viewing, you can use the F1 hotkey or use the `help` command.

##### Format: `help`

### 3.7.2. Exit

At the end of a long day, you deserve some time away from your task manager. You can `exit` **Today** and enjoy the rest of your day!

##### Format: `exit`

## 4. FAQ

Q1. How do I remove all tags from a task or event?
A1. You can do this by editing the task/event! Use the following command `edit <index of task/event> #` by using a single `#` sign with nothing following it deletes all tags for that task.

Q2. If I use the saveto command and specify a folder with an existing data file, what happens?
A2. We will overwrite the file at the specified location with the latest data in your task manager. If you want to use data from that file, consider the `import` function instead. If you want changes to be saved to that file, consider the `usethis` function.

## 5. Command Summary

| Action | Command |Example |
| ------ | ------- | ------ |
| **Add Task** | `add <task_name> [due <deadline>] [#<tag> #<tag> ...]` |`add Study for CS2106 due 20/03/17 3pm #lab #cs2106` |
| **Add Event** | `add <event_name> from <starting_date_time> to <end_date_time> [#<tag1> #<tag2> ...>]`| `add meeting from 10am to 24th March 11am #work` |
| **Find Task**| `find <keyword1 keyword2 ...>` | `find math science` |
| **Find Task by Deadline** | `find due <deadline>` | `find due tomorrow` |
| **View All Tasks** | `list`| `list` |
| **View Completed Tasks** | `listcompleted`| `listcompleted`|
| **Set tasks as today**| `today <id>`| `today F2` |
| **Set tasks as not today** | `nottoday <id>`| `nottoday T2` |
| **Complete Task**| `done <id>`| `done T1` |
| **Un-complete Task**| `notdone <id>`| `notdone T1`|
| **Update Task**| `update <id> [<new_task_name>] [due <new_date> at <new_time>] [#<tag1> #<tag2> ...]` | `edit T7 clean house due 04/05/17 6pm #schoolwork #CS2103` |
| **Delete Task**| `delete <id>`| `delete T2` |
| **Rename Tag**| `renametag <tag_name> <new_tag_name>`| `renametag work oldwork`|
| **Delete Tag**| `deletetag <tag_name>` | `deletetag math` |
| **Undo**| `undo`| `undo` |
| **Redo**| `redo`| `redo` |
| **Change Storage Location**| `saveto <dir_location>`| `saveto ../mySecretFolder` |
| **Use Existing Storage File**| `usethis <dir_location>`| `usethis ../mySecretFolder`|
| **Export**| `export <dir_location>`|`export ../mySecretFolder`|
| **Import**| `import <dir_location>`|`import ../mySecretFolder`|
| **Help**| `help`| `help` |
| **Exit**| `exit`| `exit` |
