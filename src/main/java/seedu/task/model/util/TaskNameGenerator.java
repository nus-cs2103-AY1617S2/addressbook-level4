package seedu.task.model.util;

import java.util.Random;

import seedu.task.commons.util.CollectionUtil;

//@@author A0163848R
/**
 * Builder for randomly-generating task names
 */
public class TaskNameGenerator {
    private static final String[] ACTIONS = {
        "Walk",
        "Study",
        "Talk",
        "Chat",
        "Take photos",
        "Eat",
        "Parkour",
        "Makan",
        "Cram study",
        "Take test",
        "Meet",
        "Exercise",
        "Shop",
        "Play Minecraft",
        "Figure out travel plans",
        "Draw art",
        "Do calligraphy",
        "Play videogames",
        "Hike",
    };

    private static final String[] NAMES = {
        "George P. Burdell",
        "Tony",
        "Malik",
        "Cheuk Ting",
        "Ali",
        "Rabab",
        "Alia",
        "Mohsin",
        "Ellie",
        "Sarah",
        "Satoshi",
        "Mars",
        "Elon Musk",
        "Jaden",
        "Chris",
        "Cristina",
        "Husain",
        "class",
        "project group",
        "fam",
        "Layla",
        "Jake",
        "John",
        "Jon",
        "Allison",
        "Bill",
        "Robert",
        "Mr. Anderson",
        "Hannah",
        "Gabe",
        "squad",
    };

    private static final String[] PLACES = {
        "Woodys",
        "Tech Tower",
        "MBS",
        "PGP",
        "Techno Edge",
        "japanese restaurant",
        "hotpot restaurant",
        "Central Library",
        "Yishun",
        "Punggol",
        "Sentosa",
        "Changi Airport",
        "golf course",
        "hawker centre",
        "NUH",
        "NUS",
        "Pasir Panjang Rd.",
        "Raffles Place",
    };

    private static Random r;

    private TaskNameGenerator previous;
    private String state;

    private TaskNameGenerator(TaskNameGenerator previous, String state) {
        this.previous = previous;
        this.state = state;
    }

    @Override
    public String toString() {
        return (previous != null ? previous.toString() + " " : "") + state;
    }

    /**
     * @return Set a random action
     * @param Random object to use
     */
    public static TaskNameGenerator doAction(Random r) {
        return doAction(r, CollectionUtil.getRandom(ACTIONS, r));
    }

    /**
     * @return Set a given action
     * @param Random object to use
     */
    public static TaskNameGenerator doAction(Random r, String action) {
        TaskNameGenerator.r = r;
        return new TaskNameGenerator(null, action);
    }

    /**
     * @return Set a random person to do with
     */
    public TaskNameGenerator with() {
        return with(CollectionUtil.getRandom(NAMES, r));
    }

    /**
     * @return Set a given person to do with
     */
    public TaskNameGenerator with(String name) {
        return new TaskNameGenerator(this, "with " + name);
    }

    /**
     * @return Set a random place to do in
     */
    public TaskNameGenerator in() {
        return in(CollectionUtil.getRandom(PLACES, r));
    }

    /**
     * @return Set a given place to do in
     */
    public TaskNameGenerator in(String place) {
        return new TaskNameGenerator(this, "in " + place);
    }

    /**
     * @return Randomly unset the previous call (frequency: 0.5)
     */
    public TaskNameGenerator maybe() {
        return maybe(0.5f);
    }

    /**
     * @return Randomly unset the previous call with given frequency
     */
    public TaskNameGenerator maybe(float chance) {
        if (r.nextFloat() > chance) {
            if (previous == null) {
                state = "";
            } else {
                return previous;
            }
        }
        return this;
    }

}
