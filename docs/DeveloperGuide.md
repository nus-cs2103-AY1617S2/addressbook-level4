## **Appendix A : User Stories**

Priorities: High (must have) - \* \* \*, Medium (nice to have) - \* \*, Low (unlikely to have) - \*

<table>
  <tr>
    <td>Priority</td>
    <td>As a ...</td>
    <td>I want to ...</td>
    <td>So that I can...</td>
  </tr>
  <tr>
    <td>* * *</td>
    <td>new user</td>
    <td>see usage instructions</td>
    <td>refer to instructions when I forget how to use the App</td>
  </tr>
  <tr>
    <td>* * *</td>
    <td>new user</td>
    <td>specify file location for the data storage</td>
    <td></td>
  </tr>
  <tr>
    <td>* * </td>
    <td>user</td>
    <td>change file location for the data storage</td>
    <td></td>
  </tr>
  <tr>
    <td>* * *</td>
    <td>user</td>
    <td>add a new event</td>
    <td></td>
  </tr>
  <tr>
    <td>* </td>
    <td>user</td>
    <td>add multiple time periods to an event</td>
    <td>confirm the exact start and end time for the event later</td>
  </tr>
  <tr>
    <td>* * *</td>
    <td>user</td>
    <td>add a new task</td>
    <td></td>
  </tr>
  <tr>
    <td>* * *</td>
    <td>user</td>
    <td>list unfinished tasks</td>
    <td>see all the tasks I have yet to complete</td>
  </tr>
  <tr>
    <td>* * *</td>
    <td>user</td>
    <td>list unfinished events</td>
    <td>see all the events yet to come to pass</td>
  </tr>
  <tr>
    <td>* * </td>
    <td>user</td>
    <td>list upcoming events for the week</td>
    <td>see all the upcoming events from now till the end of the week</td>
  </tr>
  <tr>
    <td>* * *</td>
    <td>user</td>
    <td>update an event</td>
    <td>change the event name, start and end time in case of a mistake or change of plans</td>
  </tr>
  <tr>
    <td>* * *</td>
    <td>user</td>
    <td>update a task name</td>
    <td>change the task name</td>
  </tr>
  <tr>
    <td>* * *</td>
    <td>user</td>
    <td>add a ending time to a task</td>
    <td>dynamically change a task into a deadline</td>
  </tr>
  <tr>
    <td>* * *</td>
    <td>user</td>
    <td>update the ending time for a task</td>
    <td>postpone an unfinished task</td>
  </tr>
  <tr>
    <td>* * *</td>
    <td>user</td>
    <td>mark a task as done or undone</td>
    <td></td>
  </tr>
  <tr>
    <td>* * </td>
    <td>user</td>
    <td>automatically mark an event as done</td>
    <td>mark an event as done when it is over</td>
  </tr>
  <tr>
    <td>* * *</td>
    <td>user</td>
    <td>delete a task</td>
    <td></td>
  </tr>
  <tr>
    <td>* * *</td>
    <td>user</td>
    <td>delete an event</td>
    <td></td>
  </tr>
  <tr>
    <td>* * *</td>
    <td>user</td>
    <td>undo previous command</td>
    <td>revert the most recent command</td>
  </tr>
  <tr>
    <td>* *</td>
    <td>user</td>
    <td>redo last undo command</td>
    <td>revert the most recent undo command</td>
  </tr>
  <tr>
    <td>* *</td>
    <td>advanced user</td>
    <td>use short versions of commands</td>
    <td>type a command faster</td>
  </tr>
  <tr>
    <td>* * *</td>
    <td>user with many events and tasks</td>
    <td>search the events and tasks by name</td>
    <td>find a specific event or task without listing all of them</td>
  </tr>
  <tr>
    <td>* * </td>
    <td>user</td>
    <td>make an event recurring</td>
    <td>have the same event repeat itself over a specified duration without manually adding all of them</td>
  </tr>
  <tr>
    <td>* * </td>
    <td>user</td>
    <td>make a task recurring</td>
    <td>have the same task repeat itself over a specified duration without manually adding all of them</td>
  </tr>
  <tr>
    <td>* * </td>
    <td>user with many events and tasks</td>
    <td>list all events and tasks within a time period</td>
    <td>find events and tasks within a time period without listing all of them</td>
  </tr>
  <tr>
    <td>* * </td>
    <td>user </td>
    <td>check all events and tasks for the day</td>
    <td>mark them as done, postpone or delete as necessary</td>
  </tr>
  <tr>
    <td>*</td>
    <td>user</td>
    <td>add description to a task</td>
    <td>have miscellaneous details not clutter the task title</td>
  </tr>
  <tr>
    <td>* </td>
    <td>user</td>
    <td>add venue to an event</td>
    <td>see location that event takes place</td>
  </tr>
  <tr>
    <td>* </td>
    <td>user with many events and tasks</td>
    <td>list all upcoming events at a particular location</td>
    <td>see events and tasks to be completed at a particular location</td>
  </tr>
  <tr>
    <td>* </td>
    <td>user who also uses Google Calendar</td>
    <td>sync tasks to Google Calendar</td>
    <td>see existing tasks and add local tasks to Google Calendar</td>
  </tr>
</table>


## **Appendix B : Use Cases**

(For all use cases below, the System is the TaskManager and the Actor is the user, unless specified otherwise)

#### **Use case: Reschedule an event**

MSS

1. User requests to search or list events

2. TaskManager shows an indexed list of persons

3. User requests to update a specific event and the details in the list

4. TaskManager updates new detail value

5. TaskManager shows the updated event

6.  Use case ends.

Extensions

2a. The event does not exist

Use case ends

3a. The given index is invalid

3a1. TaskManager shows an error message

Use case resumes at step 2

3b. The given details to update is invalid

3b1. TaskManager shows an error message

Use case resumes at step 3

3c. The given detail value format is invalid

3c1. TaskManager shows an error message

Use case resumes at step 3

#### **Use case: Check events and tasks for the day**

MSS

1. User requests to check events and tasks for the day

2. TaskManager shows a list of all events and then a list of all tasks for the day

3. TaskManager marks all events already past as completed

4. TaskManager prompts user for Task 1 to mark as done, postpone or delete

5. User requests the desired action

6. TaskManager executes the desired action

7. TaskManager shows the updated task

8. Repeat step 4-7 for all the tasks on the day

9. Use case ends.

Extensions

2a. There are no events or tasks for the day

Use case ends

6a. User wants to mark task as done

6a1. TaskManager mark task as done

6a2. TaskManager shows the updated task

Use case resume at step 4

6b. User wants to postpone task

6b1. TaskManager prompts user for new task deadline

6b2. User input new task deadline

6b3. TaskManager shows the updated task

Use case resume at step 4

6c. User wants to delete task

6c1. TaskManager prompts user to confirm deletion

6c2. User confirm deletion

6c3. TaskManager deletes the task

Use case resume at step 4

## **Appendix C : Non Functional Requirements**

1. Should work on Windows 7 or later as long as it has Java 1.8.0_60 or higher installed.

2. Should not require any installation apart from Java.

3. Core features should be available and work even when offline.

4. Data storage should be in plain text and be easily readable and editable.

5. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

6. Should be free and come with open sourced code.

7. Users should be able to execute all commands by using the CLI alone

## **Appendix D : Glossary**

##### **Mainstream OS**

Windows, Linux, Unix, OS-X

##### **CLI**

Command Line Interface

##### **GUI**

Graphical User Interface

##### **Event**

An event has a starting date and time and ending date and time

E.g. Meeting on 28 February 2017 1300H to 28 February 2017 1400H

##### **Task**

A task may or may not have a specific ending time

E.g. Complete Progress Report

##### **Deadline**

A task with a specified ending time is a deadline

E.g. Complete Progress Report by 20 March 2017 2359H

## **Appendix E : Product Survey**

Google Keep

Author: Goh Yi Rui

Pros:

* Support for lists, plain text, pictures and voice recording

* Color codes tasks

* Easily search through all tasks by name, label, color or category

* Automatically identify tasks that lie within a same category

* Add personalised labels or taggings

* Autocompletion and suggestion for tasks

* Synced to Google Account

Cons:

* Reliance on GUI for all operations

* No support for events, only tasks and notes

* Require user to be online

* No project managements features

Google Calendar

Author: Zhang Hanming

Pros:

* Automatically parsing email and add event to calendar

* Reminder before events

* Intuitive UI

* Categorization of events

* Allow importing and exporting of calendars

Cons:

* Requires a Google account

* Unable to check off completed tasks

* Mainly a calendar app rather than a task managing one

Gmail Tasks

Author: Liu Ziyang

Pros:

+ Allows to mark a task as done

+ Allows to postpone a task

+ Has shortcuts for editing tasks

+ Allows self-defined categorizing

+ Integrates with Google Calendar

Cons:

+ Only works online

+ Does not support  command line inputs

+ Does not support events

+ Does not support undoing an action

+ Urgent events cannot stand out
