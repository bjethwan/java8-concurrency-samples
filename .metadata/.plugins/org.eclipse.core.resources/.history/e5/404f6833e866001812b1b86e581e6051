package com.bjethwan;

import java.util.concurrent.*;

/**
 * This is still not good as underlying it would be using JVM common ForkJoin pool
 * The # of threads in there are as many as # of CPU cores and if we perform some blocking operation.
 * Then that pool would look undersized. 
 * @author KH1783
 *
 */
public class CFExecution {
    public static void main(String[] args) throws InterruptedException {

        CompletableFuture<String> future
                = CompletableFuture.supplyAsync(() -> delayedCallback("Hello"));

        future.thenAcceptAsync((message) -> delayedCallback(message+"1"));

        System.out.println("Hey from " + Thread.currentThread().getName());

        future.thenAcceptAsync((message) -> delayedCallback(message+"2"));

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