package net.sf.juoserver;

import java.util.concurrent.*;

public class Teste {
    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.schedule(() -> {
            System.out.println("sd");
        }, 500, TimeUnit.MILLISECONDS);
    }
}
