### Must-have features:

Support for events (i.e., has a start time and end time), deadlines (tasks that have to be done before a specific deadline), and floating tasks (tasks without specific times). Note that these category names are temporary names only. You should figure out what are the best names for these categories. Are these names intuitive to end-users? Should they even care about consciously categorizing tasks?
CRUD (i.e., Create, Read, Update, Delete) support for tasks.
Undo operations (at least for the most recent action)
Some flexibility in the command format, i.e. support a few natural variations of the command format.
Simple search: A simple text search for finding an item if the user remembers some keywords from the item description.
Some way to keep track of which items are done and which are yet to be done.
The ability to specify a specific folder and a file for data storage. With this feature, Jim can choose to store the data file in a local folder controlled by a cloud syncing service (e.g. dropbox), allowing him to access task data from multiple computers.

### Nice to have features : (you may implement zero or more of these features)

#### Difficulty-low

AutomatedTesting : Achieve a very high level of automated testing.
RecurringTasks: Support for managing recurring tasks. e.g. CS2103 lectures recurring from week 1 to week 13 except in recess week.


#### Difficulty-medium

PowerSearch : More powerful and intelligent search. e.g. search for empty slots, near-match search, auto-complete (similar to Google search box), filters for other attributes (e.g. start time).
FlexiCommands: High flexibility in command format. e.g., non-strict ordering of keywords, ability to specify aliases for commands, support for more ‘natural’ language input, multiple undo/redo, How flexible should the format be? As flexible, intuitive, and user-friendly as you can make it.

#### Difficulty-high

GoodGui: A good GUI to give visual feedback to the user e.g., in-built guidance for new users, good visual feedback for actions, feedback while typing commands, hotkey to activate/hide, notifications, etc. conveying more information visually (e.g. more important/urgent tasks stand out from the rest, longer events look different from shorter events, easy to see free slots, etc.)
GoogleIntegration: Google Calendar integration. e.g. upload todo items to GCal, two-way sync, support for GCal quick add command format, etc. You may also use the Google Tasks API for this.


### Our user stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the App
