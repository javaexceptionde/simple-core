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

package dev.jbull.simplecore.messaging;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;
import org.bson.Document;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public class ChannelMessageProvider implements IChannelMessageProvider{
    Connection nats;

    public ChannelMessageProvider(String ip, String port){
        try {
            nats = Nats.connect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CountDownLatch latch = new CountDownLatch(1);

        Dispatcher d = nats.createDispatcher((msg) -> {
            String str = new String(msg.getData(), StandardCharsets.UTF_8);
            System.out.println(str);
            latch.countDown();
        });

        d.subscribe("core");

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendMessage(String channel, Document message) {
        nats.publish(channel, message.toJson().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void sendMessage(String channel, String message) {

    }
}
