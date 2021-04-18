package dev.jbull.corev.utils;

import jdk.nashorn.internal.codegen.CompilerConstants;

import java.sql.Connection;
import java.util.concurrent.*;

public class ExecuteScheduler {
    ExecutorService executor = Executors.newCachedThreadPool();
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public void schedule(Runnable runnable) {
        try {
            executor.submit(runnable);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

    public void schedule(Callback<Runnable> handler){
        try {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    handler.call(this);
                }
            });
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }
    }

    public <T> Future<?> schedule(Runnable runnable, long delay) {
        return scheduledExecutorService.schedule(runnable, delay, TimeUnit.MILLISECONDS);
    }

    public void schedule(Callback<Runnable> handler, long delay){
        try {
            scheduledExecutorService.schedule(new Runnable() {
                @Override
                public void run() {
                    handler.call(this);
                }
            }, delay, TimeUnit.MILLISECONDS);
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }
    }

    public <T> Future<?> schedule(Runnable runnable, long delay, long interval) {
        return scheduledExecutorService.scheduleAtFixedRate(runnable, delay, interval, TimeUnit.MILLISECONDS);
    }

    public <T> Future<?> schedule(Callable<T> callable, long delay, long interval) {
        try {
            //return scheduledExecutorService.submit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
