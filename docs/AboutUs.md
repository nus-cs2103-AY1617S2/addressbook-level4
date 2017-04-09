# About Us

We are a team based in the [School of Computing, National University of Singapore](http://www.comp.nus.edu.sg).

## Project Team

#### [Jay Gajendra Kabra](https://github.com/jay500s)
<img src="images/jay500s.PNG" width="150"><br>
* Components in Charge of: [Model](https://github.com/CS2103JAN2017-T11-B3/main/blob/master/docs/DeveloperGuide.md#24-model-component) <br>
* Aspects/tools in charge of: Code Quality
* Features implemented:
    * [Recurring Tasks](https://github.com/CS2103JAN2017-T11-B3/main/blob/master/docs/UserGuide.md#3-notes-on-recurring-tasks) - Full stack implementation
    * [Find on any parameter](https://github.com/CS2103JAN2017-T11-B3/main/blob/master/docs/UserGuide.md#25-finding-all-tasks-containing-any-keyword-in-their-name-find)
    * [Prioritize Command](https://github.com/CS2103JAN2017-T11-B3/main/blob/master/docs/UserGuide.md#28-allocate-priority-to-a-task-prioritize) - Worked with Heyang to implement logic and model functionality
* Code written:
    * [Functional code](https://github.com/CS2103JAN2017-T11-B3/main/blob/master/collated/main/A0164212U.md)
    * [Test code](https://github.com/CS2103JAN2017-T11-B3/main/blob/master/collated/test/A0164212U.md)
* Other major contributions:
    * Initial refactoring from AddressBook to Task Manager
    * Created Description, Priority, and Timing classes and added respective functionality to validate respective parameters
    * Colored Priorities for better UI experience
    * Debugged test code classes when testing recurring tasks
    * Significant contributions to User Guide for almost all commands, descriptions, and examples

-----

#### [Tyler Austin Rocha](https://github.com/tylerrocha)
<img src="images/tylerrocha.jpg" width="150"><br>
Role: Developer <br>
Responsibilities: Storage, GitHub
* Components in Charge of: [Storage](https://github.com/CS2103JAN2017-T11-B3/main/blob/master/docs/DeveloperGuide.md#25-storage-component) <br>
* Features implemented:
    * [Comparable Tasks](https://github.com/CS2103JAN2017-T11-B3/main/blob/master/docs/UserGuide.md#231-natural-ordering-of-tasks)
    * [Save Command](https://github.com/CS2103JAN2017-T11-B3/main/blob/master/docs/UserGuide.md#211-saving-the-data--save)
    * [Load Command](https://github.com/CS2103JAN2017-T11-B3/main/blob/master/docs/UserGuide.md#212-loading-the-data--load)
* Code written: [[Functional code](https://github.com/CS2103JAN2017-T11-B3/main/blob/master/collated/main/A0163559U.md)][[Test code](https://github.com/CS2103JAN2017-T11-B3/main/blob/master/collated/test/A0163559U.md)]
* Other major contributions:
    * Maintained storage, comparable, save/load, logic manager and XMLUtil tests
    * Updated testutil files, e.g. TestTask and TaskBuilder
    * Tracked down and fixed general bugs such as null pointer on exit command
    * Maintained git repo, git page; set up Travis, Appveyor, Codacy, Coveralls
    * Provided assistance with Eclipse, Sublime Text features


-----

#### [Wu Heyang](https://github.com/whyCaiJi)
<img src="images/whycaiji.jpg" width="150"><br>
Role: Developer <br>
Responsibilities: Logic and Scheduling and Tracking
* Component in Charge of: [Logic](https://github.com/CS2103JAN2017-T11-B3/main/blob/master/docs/DeveloperGuide.md#23-logic-component)<br>
* Features implemented:
    * [Adding floating task](https://github.com/CS2103JAN2017-T11-B3/main/blob/master/docs/UserGuide.md#22-adding-a-task-add)
    * [Complete Command](https://github.com/CS2103JAN2017-T11-B3/main/blob/master/docs/UserGuide.md#27-complete-a-task--complete)
    * [Prioritize Command](https://github.com/CS2103JAN2017-T11-B3/main/blob/master/docs/UserGuide.md#28-allocate-priority-to-a-task-prioritize)
    * [Undo Command](https://github.com/CS2103JAN2017-T11-B3/main/blob/final_stuff/docs/UserGuide.md#213-revert-the-previous-change--undo)
    * [Redo Command](https://github.com/CS2103JAN2017-T11-B3/main/blob/final_stuff/docs/UserGuide.md#214-revert-the-previous-undo-change-redo)
* Code written: [[Functional code](https://github.com/CS2103JAN2017-T11-B3/main/blob/master/collated/main/A0113795Y.md)][[Test code](https://github.com/CS2103JAN2017-T11-B3/main/blob/master/collated/test/A0113795Y.md)]
* Other major contributions:
    * Refactored commands class files to align with Task Manager context
    * Wrote test cases for GUI tests
    * Implemented edit and delete command on single occurrence of a recurring task
    * Modified ModelManager.java to align with features

-----

#### [Yu Cheng-Liang](https://github.com/nuslarry)
<img src="images/chengliang.jpg" width="150"><br><br>
*Components in Charge of: [UI](https://github.com/CS2103JAN2017-T11-B3/main/blob/master/docs/DeveloperGuide.md#model-component) <br><br>
Role: Developer <br><br>
Responsibilities: UI, Testing and Integeration
* Features implemented:
    * [Dynamic Calender](images/Ui.png)  :Show all the tasks on the calender accroding to their ending timing
    * Change Calender Date
    * complete tag coloring
* Other major contributions:
    * fix some guitests,eg: addCommandTest,deleteCommandTest,listCommandTes,
     to make the task manager pass travis test
    * User Interface Styling



-----

# Contributors

We welcome contributions. See [Contact Us](ContactUs.md) page for more info.
