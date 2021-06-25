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

package dev.jbull.simplecore.example;

import dev.jbull.simplecore.messages.IMessageProvider;
import dev.jbull.simplecore.messages.Language;
import dev.jbull.simplecore.messages.SqlMessageProvider;

import java.util.UUID;

public class MessageExample {

    public void messageUse(UUID uuid){
        //Der MessageProvider sollte am Besten nur in der onEnable Methode initialisiert werden und nicht jedes mal
        IMessageProvider messageProvider = new SqlMessageProvider();
        //Mit dieser Methode bekommst du den String der eingetragenen Nachricht zurück in der gewählten Sprache des Users
        //Bitte bedenke das du gewöhlte placeholder replacen musst
        messageProvider.getMessage("EXAMPLE_MESSAGE_1", uuid);
    }

    public void createDefaultMessage(){
        //Der Message Key ist da um die Nachricht zu identifizieren und muss eindeutig sein
        //Die Keys sollten möglichst nach folgendem Schema sein pluginMame_key
        //Beispiel bedwars_countdown_end
        String messageKey = "Example_Message_Key_1";
        //Die Nachricht ist die Nachricht welche ausgegeben werden soll es können Placeholder genutzt werden diese müssen dann aber auch manuell replaced werden
        String message = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam ";
        //Die language stellt die Sprache da in welcher die Nachricht abgespeichert werden soll
        String language = Language.GERMAN.name();
        //Der MessageProvider sollte am Besten nur in der onEnable Methode initialisiert werden und nicht jedes mal
        IMessageProvider messageProvider = new SqlMessageProvider();
        messageProvider.createMessage(messageKey, message, language);
    }

    public void updateMessage(){
        //Der Message Key ist da um die Nachricht zu identifizieren und muss eindeutig sein
        //Die Keys sollten möglichst nach folgendem Schema sein pluginMame_key
        //Beispiel bedwars_countdown_end
        String messageKey = "Example_Message_Key_1";
        //Die Nachricht ist die Nachricht welche ausgegeben werden soll es können Placeholder genutzt werden diese müssen dann aber auch manuell replaced werden
        String message = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam ";
        //Die language stellt die Sprache da in welcher die Nachricht abgespeichert werden soll
        String language = Language.GERMAN.name();
        //Der MessageProvider sollte am Besten nur in der onEnable Methode initialisiert werden und nicht jedes mal
        IMessageProvider messageProvider = new SqlMessageProvider();
        //Hiermit updatest du die Nachricht für die angegebene Sprache
        messageProvider.updateMessage(messageKey, message, language);
    }
}
