package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Instruction;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Title("Buy groceries"), new Deadline("today"), new Priority("1"),
                    new Instruction("eggs x2, bread x1, milk x4"),
                    new UniqueTagList("chores")),
                new Task(new Title("Fill ice tray"), new Deadline("tonight at 9pm"), new Priority("2"),
                    new Instruction("or tomorrow no coffee, no coffee no work, no work no life"),
                    new UniqueTagList("everyday", "trivialButCanKill")),
                new Task(new Title("Finish React Native course"), new Deadline("floating"), new Priority("3"),
                    new Instruction("clear section 7 and final test"),
                    new UniqueTagList("selfLearn")),
                new Task(new Title("Revise CS3243"), new Deadline("sunday"), new Priority("3"),
                    new Instruction("lecture 9"),
                    new UniqueTagList("school")),
                new Task(new Title("Finish CS2103T issues 23"), new Deadline("tomorrow"), new Priority("5"),
                    new Instruction("clear up for Travis"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Apples"), new Deadline("today"), new Priority("1"),
                    new Instruction("Apples"),
                    new UniqueTagList("fruits")),
                new Task(new Title("Buy Oranges"), new Deadline("tomorrow"), new Priority("2"),
                    new Instruction("Oranges"),
                    new UniqueTagList("fruits")),
                new Task(new Title("Buy Watermelons"), new Deadline("the next day"), new Priority("3"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Mangoes"), new Deadline("monday"), new Priority("4"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Fruits"), new Deadline("tuesday"), new Priority("5"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Grapefruits"), new Deadline("wednesday"), new Priority("1"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Kiwis"), new Deadline("thursday"), new Priority("2"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Grapes"), new Deadline("friday"), new Priority("3"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Strawberries"), new Deadline("saturday"), new Priority("4"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Mangosteens"), new Deadline("sunday"), new Priority("5"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Peaches"), new Deadline("monday"), new Priority("1"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Plums"), new Deadline("tuesday"), new Priority("2"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Cherries"), new Deadline("wednesday"), new Priority("3"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Olives"), new Deadline("thursday"), new Priority("5"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Lemons"), new Deadline("friday"), new Priority("4"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Bananas"), new Deadline("saturday"), new Priority("1"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Pineapples"), new Deadline("sunday"), new Priority("2"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Pears"), new Deadline("monday"), new Priority("3"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Blueberries"), new Deadline("tuesday"), new Priority("4"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Raspberries"), new Deadline("wednesday"), new Priority("5"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Jackfruits"), new Deadline("thursday"), new Priority("1"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Blackcurrants"), new Deadline("friday"), new Priority("2"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Durians"), new Deadline("saturday"), new Priority("3"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Guavas"), new Deadline("sunday"), new Priority("4"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Soursops"), new Deadline("floating"), new Priority("5"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Starfruits"), new Deadline("floating"), new Priority("1"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Rambutans"), new Deadline("floating"), new Priority("2"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Lemons"), new Deadline("floating"), new Priority("3"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Aloe Veras"), new Deadline("floating"), new Priority("4"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Tomatoes"), new Deadline("floating"), new Priority("5"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Papayas"), new Deadline("floating"), new Priority("1"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Pomelos"), new Deadline("floating"), new Priority("2"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Avocados"), new Deadline("floating"), new Priority("3"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Buy Longans"), new Deadline("floating"), new Priority("4"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Prepare for dry run"), new Deadline("floating"), new Priority("3"),
                        new Instruction("demo of software"),
                        new UniqueTagList("project", "school"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static Task[] getSampleRandomisedTasks() {
        int numberOfTasks = 10;
        Task[] sampleTasks = new Task[numberOfTasks];
        for (int i = 0; i < numberOfTasks; i++) {
            try {
                sampleTasks[i] = new Task(new Title("Tests and Exam" + i), new Deadline("next sunday"),
                        new Priority("-5"),
                        new Instruction("done"),
                        new UniqueTagList("project", "school"));
            } catch (IllegalValueException e) {
                throw new AssertionError("sample data cannot be invalid", e);
            }
        }
        return sampleTasks;
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAB = new AddressBook();
            for (Task sampleTask : getSampleRandomisedTasks()) {
                sampleAB.addTask(sampleTask);
            }
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
