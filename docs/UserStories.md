### Must-have features:

1. Support for events (i.e., has a start time and end time), deadlines (tasks that have to be done before a specific deadline), and floating tasks (tasks without specific times). Note that these category names are temporary names only. You should figure out what are the best names for these categories. Are these names intuitive to end-users? Should they even care about consciously categorizing tasks?
2. CRUD (i.e., Create, Read, Update, Delete) support for tasks.
3. Undo operations (at least for the most recent action)
4. Some flexibility in the command format, i.e. support a few natural variations of the command format.
5. Simple search: A simple text search for finding an item if the user remembers some keywords from the item description.
6. Some way to keep track of which items are done and which are yet to be done.
7. The ability to specify a specific folder and a file for data storage. With this feature, Jim can choose to store the data file in a local folder controlled by a cloud syncing service (e.g. dropbox), allowing him to access task data from multiple computers.

### Nice to have features : (you may implement zero or more of these features)

#### Difficulty-low

1. AutomatedTesting : Achieve a very high level of automated testing.
2. RecurringTasks: Support for managing recurring tasks. e.g. CS2103 lectures recurring from week 1 to week 13 except in recess week.


#### Difficulty-medium

1. PowerSearch : More powerful and intelligent search. e.g. search for empty slots, near-match search, auto-complete (similar to Google search box), filters for other attributes (e.g. start time).
2. FlexiCommands: High flexibility in command format. e.g., non-strict ordering of keywords, ability to specify aliases for commands, support for more 'natural' languages input, multiple undo/redo, How flexible should the format be? As flexible, intuitive, and user-friendly as you can make it.

#### Difficulty-high

1. GoodGui: A good GUI to give visual feedback to the user e.g., in-built guidance for new users, good visual feedback for actions, feedback while typing commands, hotkey to activate/hide, notifications, etc. conveying more information visually (e.g. more important/urgent tasks stand out from the rest, longer events look different from shorter events, easy to see free slots, etc.)
2. GoogleIntegration: Google Calendar integration. e.g. upload todo items to GCal, two-way sync, support for GCal quick add command format, etc. You may also use the Google Tasks API for this.


### Our user stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the App
`* * *` | user | add events | help myself make tasks more manageable
`* * *` | user | add tasks | help myself make tasks more manageable
`* * *` | user | add deadlines | help myself make tasks more manageable
`* * *` | user | CRUD events, tasks, deadlines | help myself make tasks more manageable
`* * *` | user | undo last operation | help myself make tasks more manageable
`* * *` | user | have some flexiblity in commands | help myself make tasks more manageable
`* * *` | user | simple search for finding an item | help myself make tasks more manageable
`* * *` | user | view uncompleted/completed tasks | help myself make tasks more manageable
`* * *` | user | specify which folder data is stored | help myself make tasks more manageable
`* *` | user | break my tasks into subtasks | help myself make tasks more manageable
`*` | user | have inspirational quotes reminders | feel more motivated when doing my tasks
`*` | user | see my goals | see how my tasks are in line with them
`*` | user | read productivity tips | be more productive by trying new things
`*` | user | see suggestions on how to do tasks | remove barriers that stop me
`*` | user | see how much time I spend on tasks | know where i'm spending my time
`*` | user | do tasks via pomodoro | work on my tasks in 25 minute sprints
`*` | user | have shortcuts for adding tasks | save time using the software
`*` | user | see advice from influential people | make more meaningful decisions about my day
`*` | user | see when i'm most efficient | work accordingly and get more things done
`* *` | user | have a bullet journal workflow | use my favourite workflow more effectively
`* *` | user | group my todos | arrange them by projects, assignments, classes, etc.
`* *` | user | retrieve my todos by groups | determine my focus when needed
`* *` | user | add deadlines for my todos | keep track of when tasks are due
`* *` | user | view my todos by deadlines | know which ones are more urgent
`* *` | user | set daily goals of the min no. of completed tasks | have clear targets
`* *` | user | get reminders of my goals and set their frequency | remember my goals
`* *` | user | receive meaningful rewards for completing my daily goals | stay motivated
`* *` | user | have my friends who use the app share their todos with me | keep track of tasks without adding them myself
`* *` | user | receive a weekly, monthly or annually report of my done tasks and remaining todos | regularly review my efficiency
`* *` | user | view my done tasks of up to a month | stay accountable and be proud of myself once in a while
`* *` | user | check if event falls on a local PH | to avoid inconvenience
`* *` | user | merge multiple events to become subtasks under one event | help myself make tasks more manageable
`* *` | user | display tasks by date | clearly know what to do at which day
`* *` | user | add periodic tasks | need not to add one task for many times
`* *` | user | have short yet clear command words | need not to try very hard to remember them
`* *` | user | have a shortcut for showing and hiding the main window | need not to look for it when I need it
`* *` | user | hide the overdue tasks into one single row and unfolded by a click | usually see today’s tasks
`* *` | user | search for certain task by the name or tag | locate the task easily
`* *` | user | show tasks of certain day | get an idea of what to do on that day
`* *` | user | divide multiple-days task to several tasks and show them on different days | clearly see each day’s tasks
`* *` | user | only show the days with tasks | need not to scroll through a lot of no-tasks days to find the days with tasks
`* *` | user | keep track of that are done or undone | know what I have done and what I need to do next
