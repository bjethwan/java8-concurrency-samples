package com.bjethwan;

import java.util.concurrent.*;


public class CFExecution {
    public static void main(String[] args) throws InterruptedException {
    	
    	ExecutorService executorService = Executors.newFixedThreadPool(10);

        CompletableFuture<String> future
                = CompletableFuture.supplyAsync(() -> delayedCallback("Hello"), executorService);

        future.thenAcceptAsync((message) -> delayedCallback(message+"1"), executorService);

        System.out.println("Hey from " + Thread.currentThread().getName());

        future.thenAcceptAsync((message) -> delayedCallback(message+"2"), executorService);
        
        executorService.shutdown();

    }

    public static String delayedCallback(String message) {
        //uncheckedSleep(2000);
        System.out.println(message + " from " + Thread.currentThread().getName());
        return message;
    }

    public static void uncheckedSleep(long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}