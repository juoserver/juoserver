package net.sf.juoserver.model;

import net.sf.juoserver.api.ConcurrentManagerExecutor;
import net.sf.juoserver.api.SubSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class UOConcurrentManagerExecutor implements ConcurrentManagerExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(UOConcurrentManagerExecutor.class);
    private static final int INITIAL_DELAY = 500;
    private final Task[] tasks;
    private final ScheduledExecutorService executorService;

    public UOConcurrentManagerExecutor(Task... tasks) {
        this.tasks = tasks;
        this.executorService = Executors.newScheduledThreadPool(2);
    }

    @Override
    public void start() {
        for (Task task : tasks) {
            final var uptime = new AtomicLong(INITIAL_DELAY);
            executorService.scheduleWithFixedDelay(()->{
                try {
                    task.manager.execute(uptime.getAndAdd(task.delay));
                } catch (RuntimeException exception) {
                    LOGGER.error("Error executing task {}", task, exception);
                }
            }, INITIAL_DELAY, task.delay, TimeUnit.MILLISECONDS);
        }
    }

    public static class Task {
        private final SubSystem manager;
        private final long delay;

        private Task(SubSystem manager, long delay) {
            this.manager = Objects.requireNonNull(manager);
            this.delay = delay;
        }

        public static Task from(SubSystem manager, long delay) {
            return new Task(manager, delay);
        }
    }
}
