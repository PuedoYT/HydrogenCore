package net.harmoniamc.hydrogen.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.harmoniamc.hydrogen.Hydrogen;

import java.util.concurrent.ThreadFactory;

public class Tasks {

    public static ThreadFactory newThreadFactory(String name) {
        return new ThreadFactoryBuilder().setNameFormat(name).build();
    }

    public static void run(Callable callable) {
        Hydrogen.getInstance().getServer().getScheduler().runTask(Hydrogen.getInstance(), callable::call);
    }

    public static void runLater(Callable callable, long delay) {
        Hydrogen.getInstance().getServer().getScheduler().runTaskLater(Hydrogen.getInstance(), callable::call, delay);
    }

    public interface Callable {
        void call();
    }
}
