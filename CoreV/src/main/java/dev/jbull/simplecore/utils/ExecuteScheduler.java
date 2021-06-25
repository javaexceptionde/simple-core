/*
 * Copyright  (c) 2021.  Jonathan Bull Contact at jonathan@jbull.dev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.jbull.simplecore.utils;

import java.util.concurrent.*;

public class ExecuteScheduler {
    ExecutorService executor = Executors.newCachedThreadPool();
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public <T> ScheduledFuture<?> schedule(Runnable runnable, long delay) {
        return scheduledExecutorService.schedule(runnable, delay, TimeUnit.MILLISECONDS);
    }

    public ScheduledFuture<?> schedule(Callback<Runnable> handler, long delay){
        try {
            return scheduledExecutorService.schedule(new Runnable() {
                @Override
                public void run() {
                    handler.call(this);
                }
            }, delay, TimeUnit.MILLISECONDS);
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }
        return null;
    }

    public <T> Future<?> schedule(Runnable runnable, long delay, long interval) {
        return scheduledExecutorService.scheduleAtFixedRate(runnable, delay, interval, TimeUnit.MILLISECONDS);
    }


    public ExecutorService getExecutor() {
        return executor;
    }
}
