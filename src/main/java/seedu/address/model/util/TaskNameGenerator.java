package seedu.address.model.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import seedu.address.commons.util.CollectionUtil;

//@@author A0163848R
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
    
    public static TaskNameGenerator doAction(Random r) {
        return doAction(r, CollectionUtil.getRandom(ACTIONS, r));
    }
    
    public static TaskNameGenerator doAction(Random r, String action) {
        TaskNameGenerator.r = r;
        return new TaskNameGenerator(null, action);
    }
    
    public TaskNameGenerator with() {
        return with(CollectionUtil.getRandom(NAMES, r));
    }
    
    public TaskNameGenerator with(String name) {
        return new TaskNameGenerator(this, "with " + name);
    }
    
    public TaskNameGenerator in() {
        return in(CollectionUtil.getRandom(PLACES, r));
    }
    
    public TaskNameGenerator in(String place) {
        return new TaskNameGenerator(this, "in " + place);
    }
    
    public TaskNameGenerator maybe() {
        return maybe(0.5f);
    }
    
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
