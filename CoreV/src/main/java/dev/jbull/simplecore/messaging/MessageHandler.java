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

import dev.jbull.simplecore.Core;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;
import io.nats.client.Subscription;
import org.bson.Document;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public class MessageHandler {

    Connection nats;

    public MessageHandler(String ip, String port){
        try {
            nats = Nats.connect("nats://" + ip + ":" + port + "");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        subscribe();
    }


    public void sendMessage(String channel, Document message) {
        message.put("channel", channel);
        nats.publish("core", message.toJson().getBytes(StandardCharsets.UTF_8));
    }

    public void sendMessage(String channel, String message) {
        Document messageDoc = new Document();
        messageDoc.put("channel", channel);
        messageDoc.put("message", message);
        nats.publish("core", messageDoc.toJson().getBytes(StandardCharsets.UTF_8));
    }

    private void subscribe(){
        CountDownLatch latch = new CountDownLatch(1);

        Dispatcher d = nats.createDispatcher((msg) -> {
            String str = new String(msg.getData(), StandardCharsets.UTF_8);
            Document document = Document.parse(str);
            String channel = document.getString("channel");
            document.remove("channel");
            ChannelMessageEvent event = new ChannelMessageEvent(document, channel);
            Core.getInstance().getListenerList().forEach(listener -> {
                listener.onChannelMessageReceived(event);
            });
            latch.countDown();
        });

        d.subscribe("core");
    }
}
