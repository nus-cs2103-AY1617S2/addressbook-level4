## Find command

To find tasks, start the command with the keyword `find`.

> Find command accepts keywords and attributes. For keywords, the program searches through name, note and tags content and return tasks that contain either of the keywords specified. 

### 1. Find a task with a keyword

`find swimming`

#### Result

Feedback shows:

> 1 tasks listed!

The task with the name `Go swimming` is listed.

### 2. Find a task an attribute

`find p/hi`

#### Result

Feedback shows:

> 13 tasks listed!

The tasks with high priorities are listed.

### 3. Find a task with keywords and attributes

`find study consultation e/04/20/2017`

#### Result

Feedback shows:

> 3 tasks listed!

The tasks that contain either of the keywords `study` or `consultation` and end before 20th April, 2017 are listed.

## Schedule command

To schedule an existing task, start the command with the keyword `schedule`.

### 1. Schedule an event

`schedule 18 thursday 6pm to thursday 9pm`

#### Result

Feedback shows:

> Edited Task: Wash my clothes
> Status: incomplete
> Start Time: 13 Apr 17 06:00PM
> End Time: 13 Apr 17 09:00PM

The floating task at index 18 is scheduled for Thursday of the current week from 6pm to 9pm.

### 2. Schedule a deadline

`schedule 19 thursday 9pm`

#### Result

Feedback shows:

> Edited Task: Feed the dog
> Status: incomplete
> End Time: 13 Apr 17 09:00PM

The deadline of the floating task at index 19 is scheduled for Thursday 9pm of the current week.

## Help command

To see the help section of Opus, enter the command `help`.

#### Result

The help window that contains the user guide appears.

## Mark command

To toggle the status of the task to either `complete` or `incomplete`, start the command with the keyword `mark`.

### 1. Mark a task as complete

`mark 1`

#### Result

Feedback shows:

> Edited Task: Study for CS2010 Written Quiz
> Priority: hi
> Status: complete
> Note: Die liao
> Start Time: 01 Apr 17 10:00AM
> End Time: 02 Apr 17 11:59PM
> Tags: [6%]

The status of the incomplete task at the index 1 is changed to `complete`. 

### 2. Mark a task as incomplete

`mark 54`

#### Result

Feedback shows:

> Edited Task: Buy groceries for Mum
> Priority: low
> Status: incomplete

The status of the completed task at the index 54 is changed to `incomplete`. 

## Select command

To select a task, start the command with the keyword `select`.

### Select a task

`select 13`

#### Result

Feedback shows:

> Selected Task: 13

The task at index 13 is set to focus.

